xquery version "1.0";

module namespace adb = "http://www.cggh.org/2010/xquery/atom-db";

declare namespace atom = "http://www.w3.org/2005/Atom" ;

import module namespace text = "http://exist-db.org/xquery/text" ;
import module namespace xmldb = "http://exist-db.org/xquery/xmldb" ;
import module namespace util = "http://exist-db.org/xquery/util" ;

import module namespace utilx = "http://www.cggh.org/2010/xquery/util" at "util.xqm" ;
import module namespace af = "http://www.cggh.org/2010/xquery/atom-format" at "atom-format.xqm" ;
import module namespace config = "http://www.cggh.org/2010/xquery/atom-config" at "atom-config.xqm" ;

declare variable $adb:feed-doc-name as xs:string := ".feed" ;
declare variable $adb:base-collection-path as xs:string := "/db/head" ;


declare function adb:collection-available(
	$request-path-info as xs:string
) as xs:boolean
{

	(:
	 : We need to know two things to determine whether an atom collection is
	 : available (i.e., exists) for the given request path info. First, does
	 : a database collection exist at the corresponding path? Second, does an
	 : atom feed document exist within that collection?
	 :)
	 
	(:
	 : Map the request path info, e.g., "/foo", to a database collection path,
	 : e.g., "/db/head/foo".
	 :)
	 
	let $db-collection-path := adb:request-path-info-to-db-path( $request-path-info )

	(:
	 : Obtain the database path for the atom feed document in the given database
	 : collection. Currently, this appends ".feed" to the database collection
	 : path.
	 :)
	 
	let $feed-doc-db-path := adb:feed-doc-db-path( $db-collection-path )
	
	let $available := 
		( xmldb:collection-available( $db-collection-path ) and exists( doc( $feed-doc-db-path ) ) )
	
	return $available
	
};




declare function adb:request-path-info-to-db-path( 
	$request-path-info as xs:string
) as xs:string
{
	concat( $adb:base-collection-path , $request-path-info )
};




declare function adb:feed-doc-db-path(
	$db-collection-path as xs:string
) as xs:string
{

	if ( ends-with( $db-collection-path , "/" ) )
	then concat( $db-collection-path , $adb:feed-doc-name )
	else concat( $db-collection-path , "/", $adb:feed-doc-name )
	
};




declare function adb:create-collection(
	$request-path-info as xs:string ,
	$request-data as element(atom:feed)
) as xs:string
{

	(:
	 : Map the request path info, e.g., "/foo", to a database collection path,
	 : e.g., "/db/head/foo".
	 :)
	 
	let $collection-db-path := adb:request-path-info-to-db-path( $request-path-info )

	(:
	 : Obtain the database path for the atom feed document in the given database
	 : collection. Currently, this appends ".feed" to the database collection
	 : path.
	 :)
	 
	let $feed-doc-db-path := adb:feed-doc-db-path( $collection-db-path )

	return
	
		if ( adb:collection-available( $request-path-info ) )
		
		then ()
		
		else
			
			let $collection-db-path := utilx:get-or-create-collection( $collection-db-path )

			let $feed := adb:create-feed( $request-path-info , $request-data )
			
			return xmldb:store( $collection-db-path , $adb:feed-doc-name , $feed )
			
};




declare function adb:update-collection(
	$request-path-info as xs:string ,
	$request-data as element(atom:feed)
) as xs:string
{

	(:
	 : Map the request path info, e.g., "/foo", to a database collection path,
	 : e.g., "/db/head/foo".
	 :)
	 
	let $collection-db-path := adb:request-path-info-to-db-path( $request-path-info )

	(:
	 : Obtain the database path for the atom feed document in the given database
	 : collection. Currently, this appends ".feed" to the database collection
	 : path.
	 :)
	 
	let $feed-doc-db-path := adb:feed-doc-db-path( $collection-db-path )

	return
	
		if ( not( adb:collection-available( $request-path-info ) ) )
		
		then ()
		
		else
			
			let $collection-db-path := utilx:get-or-create-collection( $collection-db-path )

			let $feed := adb:update-feed( doc( $feed-doc-db-path )/atom:feed , $request-data )
			
			return xmldb:store( $collection-db-path , $adb:feed-doc-name , $feed )
			
};




declare function adb:mutable-feed-children(
    $data as element(atom:feed)
) as element()*
{
    for $child in $data/*
    let $namespace-uri := namespace-uri($child)
    let $local-name := local-name($child)
    where
        not( $namespace-uri = $af:nsuri and $local-name = $af:id ) and
        not( $namespace-uri = $af:nsuri and $local-name = $af:updated ) and
        not( $namespace-uri = $af:nsuri and $local-name = $af:link and $child/@rel = "self" ) and
        not( $namespace-uri = $af:nsuri and $local-name = $af:link and $child/@rel = "edit" ) and
        not( $namespace-uri = $af:nsuri and $local-name = $af:entry )
    return $child
};




declare function adb:mutable-entry-children(
    $data as element(atom:entry)
) as element()*
{
    for $child in $data/*
    let $namespace-uri := namespace-uri($child)
    let $local-name := local-name($child)
    where
        not( $namespace-uri = $af:nsuri and $local-name = $af:id ) and
        not( $namespace-uri = $af:nsuri and $local-name = $af:updated ) and
        not( $namespace-uri = $af:nsuri and $local-name = $af:published ) and
        not( $namespace-uri = $af:nsuri and $local-name = $af:link and $child/@rel = "self" ) and
        not( $namespace-uri = $af:nsuri and $local-name = $af:link and $child/@rel = "edit" ) and
        not( $namespace-uri = $af:nsuri and $local-name = $af:link and $child/@rel = "edit-media" ) 
    return $child
};



declare function adb:create-feed( 
    $request-path-info as xs:string ,
    $data as element(atom:feed)
) as element(atom:feed) 
{

    (: TODO validate input data :)
    
    let $id := concat( $config:service-url , $request-path-info )
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
                adb:mutable-feed-children($data)
            }
        </atom:feed>  

};




declare function adb:update-feed( 
    $feed as element(atom:feed) ,
    $data as element(atom:feed)
) as element(atom:feed) 
{

    (: TODO validate input data :)
    
    let $updated := current-dateTime()
    let $log := util:log( "debug" , concat( "$updated: " , $updated ) )

    return
    
        <atom:feed>
            {
                $feed/atom:id
            }
            <atom:updated>{$updated}</atom:updated>
            {
                $feed/atom:link[@rel="self"] ,
                $feed/atom:link[@rel="edit"] ,
                adb:mutable-feed-children($data)
            }
        </atom:feed>  
};

