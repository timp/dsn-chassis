declare namespace atom = "http://www.w3.org/2005/Atom" ;

import module namespace request = "http://exist-db.org/xquery/request" ;
import module namespace response = "http://exist-db.org/xquery/response" ;
import module namespace text = "http://exist-db.org/xquery/text" ;


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

declare variable $FEED-RESOURCE-NAME := ".feed.atom" ;





declare function local:get-or-create-collection(
	$collection-path as xs:string 
) as xs:string?
{
	
	let $available := xmldb:collection-available( $collection-path )
	
	return 
		
		if ( $available ) then $path
		
		else 
		
			let $groups := text:groups( $collection-path , "^(.*)/?([^/]+)$" )
			let $target-collection-uri := $groups[1] ;
			let $target-collection-uri := local:get-or-create-collection( $target-collection-uri );
			let $new-collection := $groups[2] ;
			return xmldb:create-collection( $target-collection-uri , $new-collection )
			
};




declare function local:do-create-atom-collection( 
	$request-path as xs:string , 
	$data as element(atom:feed) 
) as item()
{

	let $collection-path := concat( $HEAD-COLLECTION-PATH , "/" , $request-path )
	let $collection-path := local:get-or-create-collection( $collection-path )
	let $feed-doc-path := concat( $collection-path , "/" , $FEED-RESOURCE-NAME )
	
	return
	
		if ( exists( doc( $feed-doc-path ) ) )
		
		then

			let $feed-doc-path := xmldb:store( $collection-path , $FEED-RESOURCE-NAME , $data )	
			let $status-code := response:set-status-code( $STATUS-SUCCESS-OK )
			let $header-location := response:set-header( "Location", concat("/atomserver" , $path) )
			let $header-content-type := response:set-header( "Content-Type" , "application/atom+xml" )
			return $data
				
		else
		
			let $feed-doc-path := xmldb:store( $collection-path , $FEED-RESOURCE-NAME , $data )	
			let $status-code := response:set-status-code( $STATUS-SUCCESS-CREATED )
			let $header-location := response:set-header( "Location", concat("/atomserver" , $path) )
			let $header-content-type := response:set-header( "Content-Type" , "application/atom+xml" )
			return $data
			
};




(:

declare function local:do-create-atom-collection( 
	$path as xs:string , 
	$data as element(atom:feed) 
) as item()
{
	let $collection-exists := 
		xmldb:collection-exists( concat( $COLLECTION-PATH-HEAD , $path ) )
	
	return
	
		if ( $collection-exists )
		then 
			
			let $status-code := response:set-status-code( $STATUS-CLIENTERROR-BAD-REQUEST )
			let $header-content-type := response:set-header( "Content-Type" , "text/plain" )
			return
				xs:string("Atom collection already exists.")
				
		else
			
			let $new-collection-path := xmldb:create-collection( $COLLECTION-PATH-HEAD , $path ) 
			let $new-feed-resource-path := xmldb:store( $new-collection-path , $RESOURCE-NAME-FEED , $data )						
			let $status-code := response:set-status-code( $STATUS-SUCCESS-CREATED )
			let $header-location := response:set-header( "Location", concat("/atomserver" , $path) )
			let $header-content-type := response:set-header( "Content-Type" , "application/atom+xml" )
			return $data
};

:)




(:
let $head-collection :=
	if ( xmldb:collection-exists( $COLLECTION-PATH-HEAD ) )
	then collection( $COLLECTION-PATH-HEAD )
	else collection( xmldb:create-collection( $COLLECTION-PATH-ROOT , $COLLECTION-NAME-HEAD ) )
):	




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
		
	then local:do-create-atom-collection( $path , $data )
	
	else
		<debug>
			<path>{$path}</path>
			<method>{$method}</method>
			<content-type>{$content-type}</content-type>
			<data>{$data}</data>
			<local-name>{$local-name}</local-name>
			<namespace-uri>{$namespace-uri}</namespace-uri>
		</debug>