declare namespace atom = "http://www.w3.org/2005/Atom" ;

import module namespace request = "http://exist-db.org/xquery/request" ;
import module namespace response = "http://exist-db.org/xquery/response" ;
import module namespace text = "http://exist-db.org/xquery/text" ;
import module namespace xmldb = "http://exist-db.org/xquery/xmldb" ;
import module namespace util = "http://exist-db.org/xquery/util" ;

declare variable $SERVICE-URL := "http://localhost:8081/atomserver/atomserver" ;

declare variable $ATOM-NSURI := "http://www.w3.org/2005/Atom" ;

declare variable $ATOM-FEED := "feed" ;
declare variable $ATOM-ENTRY := "entry" ;
declare variable $ATOM-ID := "id" ;
declare variable $ATOM-PUBLISHED := "published" ;
declare variable $ATOM-UPDATED := "updated" ;
declare variable $ATOM-LINK := "link" ;

declare variable $ATOM-MIMETYPE := "application/atom+xml" ; 

declare variable $METHOD-GET := "GET" ;
declare variable $METHOD-POST := "POST" ;
declare variable $METHOD-PUT := "PUT" ;
declare variable $METHOD-DELETE := "DELETE" ;

declare variable $STATUS-SUCCESS-OK := 200 ;
declare variable $STATUS-SUCCESS-CREATED := 201 ;
declare variable $STATUS-SUCCESS-NO-CONTENT := 204 ;

declare variable $STATUS-CLIENT-ERROR-BAD-REQUEST := 400 ;
declare variable $STATUS-CLIENT-ERROR-NOT-FOUND := 404 ;

declare variable $ROOT-COLLECTION-PATH := "/db" ;
declare variable $HEAD-COLLECTION-NAME := ".head" ;
declare variable $HEAD-COLLECTION-PATH := concat( $ROOT-COLLECTION-PATH , "/" , $HEAD-COLLECTION-NAME ) ;

declare variable $FEED-DOC-NAME := ".feed" ;
declare variable $ENTRIES-COLLECTION-NAME := ".entries" ;






declare function local:get-or-create-collection(
	$collection-path as xs:string 
) as xs:string?
{
	
	let $log := util:log( "debug" , concat( "$collection-path: " , $collection-path ) )
	 
	let $available := xmldb:collection-available( $collection-path )
	let $log := util:log( "debug" , concat( "$available: " , $available ) )
	 
	return 
		
		if ( $available ) then $collection-path
		
		else 
		
			let $groups := text:groups( $collection-path , "^(.*)/([^/]+)$" )
			let $log := util:log( "debug" , concat( "$groups: " , count( $groups ) ) )
			
			let $target-collection-uri := $groups[2]
			let $log := util:log( "debug" , concat( "$target-collection-uri: " , $target-collection-uri ) )
			
			let $new-collection := $groups[3]
			let $log := util:log( "debug" , concat( "$new-collection: " , $new-collection ) )

			let $target-collection-uri := local:get-or-create-collection( $target-collection-uri )
			
            return xmldb:create-collection( $target-collection-uri , $new-collection )
			
};




declare function local:mutable-feed-children(
    $data as element(atom:feed)
) as element()*
{
    for $child in $data/*
    let $namespace-uri := namespace-uri($child)
    let $local-name := local-name($child)
    where
        not( $namespace-uri = $ATOM-NSURI and $local-name = $ATOM-ID ) and
        not( $namespace-uri = $ATOM-NSURI and $local-name = $ATOM-UPDATED ) and
        not( $namespace-uri = $ATOM-NSURI and $local-name = $ATOM-LINK and $child/@rel = "self" ) and
        not( $namespace-uri = $ATOM-NSURI and $local-name = $ATOM-LINK and $child/@rel = "edit" ) and
        not( $namespace-uri = $ATOM-NSURI and $local-name = $ATOM-ENTRY )
    return $child
};




declare function local:mutable-entry-children(
    $data as element(atom:entry)
) as element()*
{
    for $child in $data/*
    let $namespace-uri := namespace-uri($child)
    let $local-name := local-name($child)
    where
        not( $namespace-uri = $ATOM-NSURI and $local-name = $ATOM-ID ) and
        not( $namespace-uri = $ATOM-NSURI and $local-name = $ATOM-UPDATED ) and
        not( $namespace-uri = $ATOM-NSURI and $local-name = $ATOM-PUBLISHED ) and
        not( $namespace-uri = $ATOM-NSURI and $local-name = $ATOM-LINK and $child/@rel = "self" ) and
        not( $namespace-uri = $ATOM-NSURI and $local-name = $ATOM-LINK and $child/@rel = "edit" ) 
    return $child
};



declare function local:create-feed( 
    $request-path as xs:string ,
    $data as element(atom:feed)
) as element(atom:feed) 
{

    (: TODO validate input data :)
    
    let $id := concat( $SERVICE-URL , $request-path )
    let $updated := current-dateTime()
    let $self-uri := $id
    let $edit-uri := $id
    
    return
    
        <atom:feed>
            <atom:id>{$id}</atom:id>
            <atom:updated>{$updated}</atom:updated>
            <atom:link rel="self" href="{$self-uri}"/>
            <atom:link rel="edit" href="{$edit-uri}"/>
            {
                local:mutable-feed-children($data)
            }
        </atom:feed>  

};




declare function local:update-feed( 
    $feed-doc as element(atom:feed) ,
    $data as element(atom:feed)
) as element(atom:feed) 
{

    (: TODO validate input data :)
    
    let $updated := current-dateTime()
    let $log := util:log( "debug" , concat( "$updated: " , $updated ) )

    return
    
        <atom:feed>
            {
                $feed-doc/atom:id
            }
            <atom:updated>{$updated}</atom:updated>
            {
                $feed-doc/atom:link[@rel="self"] ,
                $feed-doc/atom:link[@rel="edit"] ,
                local:mutable-feed-children($data)
            }
        </atom:feed>  
};




declare function local:do-put-feed( 
	$request-path as xs:string , 
	$data as element(atom:feed) 
) as item()?
{

    (: TODO prevent clients from overwriting an entry with a collection :)
    
	let $collection-path := concat( $HEAD-COLLECTION-PATH , $request-path )
	let $collection-path := local:get-or-create-collection( $collection-path )
	
	let $feed-doc-path := concat( $collection-path , "/" , $FEED-DOC-NAME )
	let $feed-doc := doc( $feed-doc-path )
	
	let $create := not( exists( $feed-doc ) )
	let $log := util:log( "debug" , concat( "$create: " , $create ) )
	
	let $updated-feed := 
	    if ( $create ) then local:create-feed( $request-path , $data )
	    else local:update-feed( $feed-doc/atom:feed , $data )

    let $feed-doc-path := xmldb:store( $collection-path , $FEED-DOC-NAME , $updated-feed )	
    
    (: TODO is this necessary, or can we re-use the previous reference? :)
    let $feed-doc := doc( $feed-doc-path )
    
    let $entries-collection-path := concat( $collection-path , "/" , $ENTRIES-COLLECTION-NAME )
    
    let $entries-collection-path :=
        if ( $create ) then xmldb:create-collection( $collection-path , $ENTRIES-COLLECTION-NAME )
        else $entries-collection-path
        
	let $header-content-type := response:set-header( "Content-Type" , $ATOM-MIMETYPE )
	
    let $status-code :=
        if ( $create ) then response:set-status-code( $STATUS-SUCCESS-CREATED )
        else response:set-status-code( $STATUS-SUCCESS-OK )	
        
    let $location := $feed-doc/atom:feed/atom:link[@rel="edit"]/@href
        	
	let $header-location := 
	    if ( $create ) then response:set-header( "Location", $location )
	    else ()
    
	return $feed-doc
			
};




declare function local:create-and-store-entry(
    $request-path as xs:string ,
    $data as element(atom:entry)
) as xs:string?
{

    let $collection-path := concat( $HEAD-COLLECTION-PATH , $request-path )
    let $entries-collection-path := concat( $collection-path , "/" , $ENTRIES-COLLECTION-NAME )

    let $uuid := util:uuid()
    let $id := concat( $SERVICE-URL , $request-path , "/" , $uuid )
    let $published := current-dateTime()
    let $updated := $published
    let $self-uri := $id
    let $edit-uri := $id
    
    let $entry :=
    
        <atom:entry>
            <atom:id>{$id}</atom:id>
            <atom:published>{$published}</atom:published>
            <atom:updated>{$updated}</atom:updated>
            <atom:link rel="self" href="{$self-uri}"/>
            <atom:link rel="edit" href="{$edit-uri}"/>
            {
                local:mutable-entry-children($data)
            }
        </atom:entry>  
     
    let $entry-doc-path := xmldb:store( $entries-collection-path , $uuid , $entry )    
    
    return $entry-doc-path
    
};



declare function local:do-post-entry(
	$request-path as xs:string , 
	$data as element(atom:entry) 
) as item()?
{

    let $collection-path := concat( $HEAD-COLLECTION-PATH , $request-path )
	let $feed-doc-path := concat( $collection-path , "/" , $FEED-DOC-NAME )
	let $feed-doc := doc( $feed-doc-path )

    let $not-found := not( exists( $feed-doc ) )
    
    return 
    
        if ( $not-found ) then local:do-not-found()
        	
        else
        
            let $entry-doc-path := local:create-and-store-entry( $request-path , $data )
            
            let $entry-doc := doc( $entry-doc-path )
    
        	let $header-content-type := response:set-header( "Content-Type" , $ATOM-MIMETYPE )
    	
            let $status-code := response:set-status-code( $STATUS-SUCCESS-CREATED )
                 
            let $location := $entry-doc/atom:entry/atom:link[@rel="edit"]/@href

            let $header-location := response:set-header( "Location", $location )
            
        	return $entry-doc
    
};




declare function local:do-not-found() as item()?
{
    let $status-code := response:set-status-code( $STATUS-CLIENT-ERROR-NOT-FOUND )
    let $header-content-type := response:set-header( "Content-Type" , "text/plain" )
    let $message := "The server has not found anything matching the Request-URI."
    return $message
};




declare function local:do-bad-request() as item()?
{

    let $status-code := response:set-status-code( $STATUS-CLIENT-ERROR-BAD-REQUEST )
	let $header-content-type := response:set-header( "Content-Type" , "text/plain" )
	let $message := "The request could not be understood by the server due to malformed syntax. The client SHOULD NOT repeat the request without modifications."
	return $message
    
};



declare function local:do-get-feed(
    $collection-path as xs:string
) as item()? 
{
    
	let $feed-doc-path := concat( $collection-path , "/" , $FEED-DOC-NAME )
	let $feed-doc := doc( $feed-doc-path )
	
    let $entries-collection-path := concat( $collection-path , "/" , $ENTRIES-COLLECTION-NAME )
	
	
    let $header-content-type := response:set-header( "Content-Type" , $ATOM-MIMETYPE )

    let $status-code := response:set-status-code( $STATUS-SUCCESS-OK )

    return 
    
        <atom:feed>
        {
            $feed-doc/atom:feed/* ,
            for $entry in collection( $entries-collection-path )//atom:entry 
            order by $entry/atom:updated descending
            return $entry
        }
        </atom:feed>
	
	(: TODO add entries :)
	
	(: TODO handle If-Modified-Since :)

};




declare function local:do-get-entry( $request-path as xs:string ) as item()? 
{

	let $groups := text:groups( $request-path , "^(.*)/([^/]+)$" )
	let $log := util:log( "debug" , concat( "$groups: " , count( $groups ) ) )
	
	let $collection-path := concat( $HEAD-COLLECTION-PATH , $groups[2] )
	let $log := util:log( "debug" , concat( "$collection-path: " , $collection-path ) )
	
	let $entry-uuid := $groups[3]
	let $log := util:log( "debug" , concat( "$entry-uuid: " , $entry-uuid ) )

    let $entries-collection-path := concat( $collection-path , "/" , $ENTRIES-COLLECTION-NAME )

    let $entry-doc-path := concat( $entries-collection-path , "/" , $entry-uuid )
    
    let $entry-doc := doc( $entry-doc-path )
    
    return

        if ( exists( $entry-doc ) ) 
        
        then 
        
            let $header-content-type := response:set-header( "Content-Type" , $ATOM-MIMETYPE )
        
            let $status-code := response:set-status-code( $STATUS-SUCCESS-OK )
            
            return $entry-doc
                 
        else local:do-not-found()

	(: TODO handle If-Modified-Since :)

};




declare function local:do-get( $request-path as xs:string ) as item()?
{
     (: we need to determine whether the request path addresses a collection or an entry :)
     
    let $collection-path := concat( $HEAD-COLLECTION-PATH , $request-path )
	let $feed-doc-path := concat( $collection-path , "/" , $FEED-DOC-NAME )
	let $feed-doc := doc( $feed-doc-path )
	
	return 
	    
	    if ( exists( $feed-doc ) ) then local:do-get-feed( $collection-path )
	    
	    else local:do-get-entry( $request-path )

};




declare function local:do-put-entry( 
	$request-path as xs:string , 
	$data as element(atom:entry) 
) as item()?
{
    (: TODO :)
    ()
};




let $path := request:get-parameter("path", "")
let $method := request:get-method()
let $content-type := request:get-header("Content-Type")
let $data := request:get-data()
let $local-name := local-name($data)
let $namespace-uri := namespace-uri($data)

let $uri := request:get-uri()
let $log := util:log( "debug" , concat( "$uri: " , $uri ) )




return

	if (
		$method = $METHOD-PUT and 
		starts-with($content-type, $ATOM-MIMETYPE) and
		$local-name = $ATOM-FEED and 
		$namespace-uri = $ATOM-NSURI
	) 
	
	then local:do-put-feed( $path , $data )
	
	else if (
		$method = $METHOD-PUT and 
		starts-with($content-type, $ATOM-MIMETYPE) and
		$local-name = $ATOM-ENTRY and 
		$namespace-uri = $ATOM-NSURI
	) 
	
	then local:do-put-entry( $path , $data )

    else if (
	    $method = $METHOD-POST and
		starts-with($content-type, $ATOM-MIMETYPE) and
		$local-name = $ATOM-ENTRY and 
		$namespace-uri = $ATOM-NSURI
	)
	
    then local:do-post-entry( $path , $data )
    
    else if ( $method = $METHOD-GET )
    
    then local:do-get( $path )
    
	else local:do-bad-request()
		

