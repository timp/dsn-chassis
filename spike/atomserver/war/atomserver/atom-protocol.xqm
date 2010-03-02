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
		
		else if (
			$request-method = $http:method-post and 
			starts-with( $request-content-type, $ap:atom-mimetype ) and
			local-name( $request-data ) = $af:feed and 
			namespace-uri( $request-data ) = $af:nsuri
		)
		
		then ap:do-post-feed( $request-path-info , $request-data )

		else if (
			$request-method = $http:method-post and 
			starts-with( $request-content-type, $ap:atom-mimetype ) and
			local-name( $request-data ) = $af:entry and 
			namespace-uri( $request-data ) = $af:nsuri
		)
		
		then ap:do-post-entry( $request-path-info , $request-data )
		
		else if (
			$request-method = $http:method-put and 
			starts-with( $request-content-type, $ap:atom-mimetype ) and
			local-name( $request-data ) = $af:entry and 
			namespace-uri( $request-data ) = $af:nsuri
		)
		
		then ap:do-put-entry( $request-path-info , $request-data )

		else if (
			$request-method = $http:method-post and 
			not( starts-with( $request-content-type, $ap:atom-mimetype ) )
		)
		
		then ap:do-post-media( $request-path-info , $request-data , $request-content-type )

		else if (
			$request-method = $http:method-put and 
			not( starts-with( $request-content-type, $ap:atom-mimetype ) )
		)
		
		then ap:do-put-media( $request-path-info , $request-data , $request-content-type )

		else ap:do-bad-request( $request-path-info , "Unknown operation." , $request-data )

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




declare function ap:do-post-feed(
	$request-path-info as xs:string ,
	$request-data as element(atom:feed)
) as item()*
{

	(: 
	 : We need to know whether an atom collection already exists at the 
	 : request path, in which case the request will be treated as an error,
	 : or whether no atom collection exists at the request path, in which case
	 : the request will create a new atom collection and initialise the atom
	 : feed document with the given feed metadata.
	 :)
	 
	let $create := not( adb:collection-available( $request-path-info ) )
	
	return 
	
		if ( $create ) 

		then
		
			let $feed-doc-db-path := adb:create-collection( $request-path-info , $request-data )
		
			let $feed-doc := doc( $feed-doc-db-path )
		            
			let $header-content-type := response:set-header( $http:header-content-type , $ap:atom-mimetype )
			
		    let $status-code := response:set-status-code( $http:status-success-no-content )
		        
		    let $location := $feed-doc/atom:feed/atom:link[@rel="self"]/@href
		        	
			let $header-location := response:set-header( $http:header-location, $location )
		    
			return ()
		
		else
		
			ap:do-bad-request( $request-path-info , "A collection already exists at the given location." , $request-data )
	
};




declare function ap:do-post-entry(
	$request-path-info as xs:string ,
	$request-data as element(atom:entry)
) as item()*
{

	(: 
	 : First we need to know whether an atom collection exists at the 
	 : request path.
	 :)
	 
	let $collection-available := adb:collection-available( $request-path-info )
	
	return 
	
		if ( not( $collection-available ) ) 

		then ap:do-not-found( $request-path-info )
		
		else
		
			let $entry-doc-db-path := adb:create-member( $request-path-info , $request-data )
		
			let $entry-doc := doc( $entry-doc-db-path )
		            
			let $header-content-type := response:set-header( $http:header-content-type , $ap:atom-mimetype )
			
		    let $status-code := response:set-status-code( $http:status-success-created )
		        
		    let $location := $entry-doc/atom:entry/atom:link[@rel="self"]/@href
		        	
			let $header-location := response:set-header( $http:header-location, $location )
					    
			return $entry-doc
			
};




declare function ap:do-put-entry(
	$request-path-info as xs:string ,
	$request-data as element(atom:entry)
) as item()*
{

	(: 
	 : First we need to know whether an atom collection exists at the 
	 : request path.
	 :)
	 
	let $member-available := adb:member-available( $request-path-info )
	
	return 
	
		if ( not( $member-available ) ) 

		then ap:do-not-found( $request-path-info )
		
		else
		
			let $entry-doc-db-path := adb:update-member( $request-path-info , $request-data )
		
			let $entry-doc := doc( $entry-doc-db-path )
		            
			let $header-content-type := response:set-header( $http:header-content-type , $ap:atom-mimetype )
			
		    let $status-code := response:set-status-code( $http:status-success-ok )
		        
			return $entry-doc

};



declare function ap:do-post-media(
	$request-path-info as xs:string ,
	$request-data as xs:base64Binary ,
	$request-content-type
) as item()*
{

	(: 
	 : First we need to know whether an atom collection exists at the 
	 : request path.
	 :)
	 
	let $collection-available := adb:collection-available( $request-path-info )
	
	return 
	
		if ( not( $collection-available ) ) 

		then ap:do-not-found( $request-path-info )
		
		else
		
			let $media-link-doc-db-path := adb:create-media-resource( $request-path-info , $request-data , $request-content-type )
			
			let $media-link-doc := doc( $media-link-doc-db-path )
		            
			let $header-content-type := response:set-header( $http:header-content-type , $ap:atom-mimetype )
			
		    let $status-code := response:set-status-code( $http:status-success-created )
		        
		    let $location := $media-link-doc/atom:entry/atom:link[@rel="self"]/@href
		        	
			let $header-location := response:set-header( $http:header-location, $location )
					    
			return $media-link-doc
			
};





declare function ap:do-put-media(
	$request-path-info as xs:string ,
	$request-data as xs:base64Binary ,
	$request-content-type
) as item()*
{

	(: 
	 : First we need to know whether a media resource exists at the 
	 : request path.
	 :)
	 
	let $found := adb:media-resource-available( $request-path-info )
	
	return 
	
		if ( not( $found ) ) 

		then ap:do-not-found( $request-path-info )
		
		else
		
			let $media-doc-db-path := adb:update-media-resource( $request-path-info , $request-data , $request-content-type )
			
			let $media-doc := util:binary-doc( $media-doc-db-path )
			
		    let $status-code := response:set-status-code( $http:status-success-ok )
		    
		    let $header-content-type := response:set-header( $http:header-content-type , $request-content-type )
		    
			return $media-doc

};




declare function ap:do-not-found(
	$request-path-info
) as item()?
{

    let $status-code := response:set-status-code( $http:status-client-error-not-found )
	let $header-content-type := response:set-header( $http:header-content-type , "application/xml" )
	let $response := 
	
		<response>
			<message>The server has not found anything matching the Request-URI.</message>
			<path-info>{$request-path-info}</path-info>
			<service-url>{$config:service-url}</service-url>
		</response>

	return response
		
};



declare function ap:do-bad-request(
	$request-path-info ,
	$message as xs:string ,
	$request-data
) as item()?
{

    let $status-code := response:set-status-code( $http:status-client-error-bad-request )
	let $header-content-type := response:set-header( $http:header-content-type , "application/xml" )
	let $response := 
	
		<response>
			<message>{$message} The request could not be understood by the server due to malformed syntax. The client SHOULD NOT repeat the request without modifications.</message>
			<service-url>{$config:service-url}</service-url>
			<method>{request:get-method()}</method>
			<path-info>{$request-path-info}</path-info>
			<headers>
			{
				for $header-name in request:get-header-names()
				return
					element  { lower-case( $header-name ) } 
					{
						request:get-header( $header-name )						
					}
			}
			</headers>
			<data>{$request-data}</data>
		</response>
			
	return $response
    
};

