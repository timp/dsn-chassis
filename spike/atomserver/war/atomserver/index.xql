declare namespace atom = "http://www.w3.org/2005/Atom" ;

import module namespace request = "http://exist-db.org/xquery/request" ;
import module namespace response = "http://exist-db.org/xquery/response" ;


declare variable $ATOM-NSURI := "http://www.w3.org/2005/Atom" ;
declare variable $ATOM-FEED := "feed" ;
declare variable $ATOM-MIMETYPE := "application/atom+xml" ;

declare variable $METHOD-GET := "GET" ;
declare variable $METHOD-POST := "POST" ;
declare variable $METHOD-PUT := "PUT" ;
declare variable $METHOD-DELETE := "DELETE" ;

declare variable $STATUS-SUCCESS-OK := xs:int(200) ;
declare variable $STATUS-SUCCESS-CREATED := xs:int(201) ;
declare variable $STATUS-SUCCESS-NO-CONTENT := xs:int(204) ;

declare variable $STATUS-CLIENTERROR-BAD-REQUEST := xs:int(400) ;

declare variable $COLLECTION-PATH-ROOT := "/db" ;
declare variable $COLLECTION-NAME-HEAD := ".head" ;
declare variable $COLLECTION-PATH-HEAD := concat( $COLLECTION-PATH-ROOT , "/" , $COLLECTION-NAME-HEAD ) ;

declare variable $RESOURCE-NAME-FEED := ".atom.feed" ;


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


let $head-collection :=
	if ( xmldb:collection-exists( $COLLECTION-PATH-HEAD ) )
	then collection( $COLLECTION-PATH-HEAD )
	else collection( xmldb:create-collection( $COLLECTION-PATH-ROOT , $COLLECTION-NAME-HEAD ) )
	
let $path := request:get-parameter("path", "default")
let $method := request:get-method()
let $content-type := request:get-header("Content-Type")
let $data := request:get-data()
let $local-name := local-name($data)
let $namespace-uri := namespace-uri($data)

return
	if 	(
			$method = $METHOD-POST and 
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