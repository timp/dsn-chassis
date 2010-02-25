declare namespace atom = "http://www.w3.org/2005/Atom" ;

import module namespace request = "http://exist-db.org/xquery/request" ;
import module namespace response = "http://exist-db.org/xquery/response" ;
import module namespace text = "http://exist-db.org/xquery/text" ;
import module namespace xmldb = "http://exist-db.org/xquery/xmldb" ;
import module namespace util = "http://exist-db.org/xquery/util" ;

declare variable $SERVICE-URL := "http://localhost:8081/atomserver/atomserver" ;

declare variable $ATOM-NSURI := "http://www.w3.org/2005/Atom" ;
declare variable $ATOM-FEED := "feed" ;
declare variable $ATOM-MIMETYPE := "application/atom+xml" ;

declare variable $METHOD-GET := "GET" ;
declare variable $METHOD-POST := "POST" ;
declare variable $METHOD-PUT := "PUT" ;
declare variable $METHOD-DELETE := "DELETE" ;

declare variable $STATUS-SUCCESS-OK := 200 ;
declare variable $STATUS-SUCCESS-CREATED := 201 ;
declare variable $STATUS-SUCCESS-NO-CONTENT := 204 ;

declare variable $STATUS-CLIENTERROR-BAD-REQUEST := 400 ;

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




declare function local:do-put-feed( 
	$request-path as xs:string , 
	$data as element(atom:feed) 
) as item()
{

	let $collection-path := concat( $HEAD-COLLECTION-PATH , $request-path )
	let $collection-path := local:get-or-create-collection( $collection-path )
	
	let $feed-doc-path := concat( $collection-path , "/" , $FEED-DOC-NAME )
	
	let $created := not( exists( doc( $feed-doc-path ) ) )

    let $feed-doc-path := xmldb:store( $collection-path , $FEED-DOC-NAME , $data )	
	
	let $header-content-type := response:set-header( "Content-Type" , "application/atom+xml" )
	
    let $status-code :=
        if ( $created ) then response:set-status-code( $STATUS-SUCCESS-CREATED )
        else response:set-status-code( $STATUS-SUCCESS-OK )	
        
	let $header-location := 
	    if ( $created ) then response:set-header( "Location", concat( $SERVICE-URL , $request-path ) )
	    else ()
    
	return doc( $feed-doc-path )
			
};



let $context-path := request:get-context-path()
let $log := util:log( "debug" , concat( "$context-path: " , $context-path ) )

let $path-info := request:get-path-info()
let $log := util:log( "debug" , concat( "$path-info: " , $path-info ) )

let $servlet-path := request:get-servlet-path()
let $log := util:log( "debug" , concat( "$servlet-path: " , $servlet-path ) )

let $uri := request:get-uri()
let $log := util:log( "debug" , concat( "$uri: " , $uri ) )

let $server-name := request:get-server-name()
let $log := util:log( "debug" , concat( "$server-name: " , $server-name ) )

let $server-port := request:get-server-port()
let $log := util:log( "debug" , concat( "$server-port: " , $server-port ) )



let $path := request:get-parameter("path", "")
let $method := request:get-method()
let $content-type := request:get-header("Content-Type")
let $data := request:get-data()
let $local-name := local-name($data)
let $namespace-uri := namespace-uri($data)




return

	if 	(
			$method = $METHOD-PUT and 
			starts-with($content-type, $ATOM-MIMETYPE) and
			$local-name = $ATOM-FEED and 
			$namespace-uri = $ATOM-NSURI
		) 
		
	then local:do-put-feed( $path , $data )
	
	else
		<debug>
			<path>{$path}</path>
			<method>{$method}</method>
			<content-type>{$content-type}</content-type>
			<data>{$data}</data>
			<local-name>{$local-name}</local-name>
			<namespace-uri>{$namespace-uri}</namespace-uri>
		</debug>

