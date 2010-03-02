xquery version "1.0";

module namespace ap = "http://www.cggh.org/2010/xquery/atom-protocol";

declare namespace atom = "http://www.w3.org/2005/Atom" ;

import module namespace request = "http://exist-db.org/xquery/request" ;
import module namespace response = "http://exist-db.org/xquery/response" ;
import module namespace text = "http://exist-db.org/xquery/text" ;
import module namespace util = "http://exist-db.org/xquery/util" ;

import module namespace http = "http://www.cggh.org/2010/xquery/http" at "http.xqm" ;
import module namespace config = "http://www.cggh.org/2010/xquery/atom-config" at "atom-config.xqm" ;
import module namespace af = "http://www.cggh.org/2010/xquery/atom-format" at "atom-format.xqm" ;
import module namespace adb = "http://www.cggh.org/2010/xquery/atom-db" at "atom-db.xqm" ;

declare variable $ap:atom-mimetype := "application/atom+xml" ; 
declare variable $ap:param-request-path-info := "request-path-info" ; 




declare function ap:do-service()
as item()*
{

	let $request-path-info := request:get-parameter( $ap:param-request-path-info , "" )
	let $request-method := request:get-method()
	let $request-content-type := request:get-header( $http:header-content-type )
	let $request-data := request:get-data()

	return

		if (
			$request-method = $http:method-put and 
			starts-with( $request-content-type, $ap:atom-mimetype ) and
			local-name( $request-data ) = $af:feed and 
			namespace-uri( $request-data ) = $af:nsuri
		) 
		
		then ap:do-put-feed( $request-path-info , $request-data )

		else

			<debug>
				<path-info>{$request-path-info}</path-info>
				<service-url>{$config:service-url}</service-url>
			</debug>
};




declare function ap:do-put-feed(
	$request-path-info as xs:string ,
	$request-data as element(atom:feed)
) as item()*
{

	(: 
	 : We need to know whether an atom collection already exists at the 
	 : request path, in which case the request will update the feed metadata,
	 : or whether no atom collection exists at the request path, in which case
	 : the request will create a new atom collection and initialise the atom
	 : feed document with the given feed metadata.
	 :)
	 
	let $create := not( adb:collection-available( $request-path-info ) )
	
	let $feed-doc-db-path := 
		if ( $create ) then adb:create-collection( $request-path-info , $request-data )
		else adb:update-collection( $request-path-info , $request-data )

	let $feed-doc := doc( $feed-doc-db-path )
            
	let $header-content-type := response:set-header( $http:header-content-type , $ap:atom-mimetype )
	
    let $status-code :=
        if ( $create ) then response:set-status-code( $http:status-success-created )
        else response:set-status-code( $http:status-success-ok )	
        
    let $location := $feed-doc/atom:feed/atom:link[@rel="self"]/@href
        	
	let $header-location := 
	    if ( $create ) then response:set-header( $http:header-location, $location )
	    else ()
    
	return $feed-doc
	
};
