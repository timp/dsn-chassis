xquery version "1.0";

module namespace ap = "http://www.cggh.org/2010/xquery/atom-protocol";

declare namespace atom = "http://www.w3.org/2005/Atom" ;

import module namespace request = "http://exist-db.org/xquery/request" ;
import module namespace response = "http://exist-db.org/xquery/response" ;
import module namespace text = "http://exist-db.org/xquery/text" ;
import module namespace util = "http://exist-db.org/xquery/util" ;

import module namespace http = "http://www.cggh.org/2010/xquery/http" at "http.xqm" ;
import module namespace mime = "http://www.cggh.org/2010/xquery/mime" at "mime-types.xqm" ;
import module namespace config = "http://www.cggh.org/2010/xquery/atom-config" at "atom-config.xqm" ;
import module namespace af = "http://www.cggh.org/2010/xquery/atom-format" at "atom-format.xqm" ;
import module namespace adb = "http://www.cggh.org/2010/xquery/atom-db" at "atom-db.xqm" ;

declare variable $ap:atom-mimetype := "application/atom+xml" ; 
declare variable $ap:param-request-path-info := "request-path-info" ; 




(:
 : TODO doc me
 :)
declare function ap:do-service()
as item()*
{

	let $request-path-info := request:get-attribute( $ap:param-request-path-info )
	let $request-method := request:get-method()
	
	return
	
		if ( $request-method = $http:method-post )

		then ap:do-post( $request-path-info )
		
		else if ( $request-method = $http:method-put )
		
		then ap:do-put( $request-path-info )
		
		else if ( $request-method = $http:method-get )
		
		then ap:do-get( $request-path-info )
		
		(: TODO DELETE method :)
		
		else ap:do-method-not-allowed( $request-path-info )

};




(:
 : TODO doc me
 :)
declare function ap:do-post(
	$request-path-info as xs:string 
) as item()*
{

	let $request-content-type := request:get-header( $http:header-content-type )

	return 

		if ( starts-with( $request-content-type, $ap:atom-mimetype ) )
		
		then ap:do-post-atom( $request-path-info )
		
		else if ( starts-with( $request-content-type, $http:media-type-multipart-form-data ) )
		
		then ap:do-post-multipart( $request-path-info )
		
		else ap:do-post-media( $request-path-info , $request-content-type )

};




(:
 : TODO doc me
 :)
declare function ap:do-post-atom(
	$request-path-info as xs:string 
) as item()*
{

	let $request-data := request:get-data()

	return
	
		if (
			local-name( $request-data ) = $af:feed and 
			namespace-uri( $request-data ) = $af:nsuri
		)
		
		then ap:do-post-atom-feed( $request-path-info , $request-data )

		else if (
			local-name( $request-data ) = $af:entry and 
			namespace-uri( $request-data ) = $af:nsuri
		)
		
		then ap:do-post-atom-entry( $request-path-info , $request-data )
		
		else ap:do-bad-request( $request-path-info , "Request entity must be either atom feed or atom entry." )

};




declare function ap:do-post-atom-feed(
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
		
			let $enable-history := request:get-header( "X-Atom-Enable-History" )
			
			let $enable-history := 
				if ( $enable-history castable as xs:boolean ) then xs:boolean( $enable-history )
				else false()

			let $feed-doc-db-path := adb:create-collection( $request-path-info , $request-data , $enable-history )
		
			let $feed-doc := doc( $feed-doc-db-path )
		            
			let $header-content-type := response:set-header( $http:header-content-type , $ap:atom-mimetype )
			
		    let $status-code := response:set-status-code( $http:status-success-no-content )
		        
		    let $location := $feed-doc/atom:feed/atom:link[@rel="self"]/@href
		        	
			let $header-location := response:set-header( $http:header-location, $location )
		    
			return ()
		
		else
		
			ap:do-bad-request( $request-path-info , "A collection already exists at the given location." )
	
};




declare function ap:do-post-atom-entry(
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




declare function ap:do-post-media(
	$request-path-info as xs:string ,
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
		
			let $request-data := request:get-data()
			
			(: check for slug to use as title :)
			
			let $slug := request:get-header( $http:header-slug )
			
			(: check for summary :)
			
			let $summary := request:get-header( "X-Atom-Summary" )
			
			let $media-link-doc-db-path := adb:create-media-resource( $request-path-info , $request-data , $request-content-type , $slug , $summary )
			
			let $media-link-doc := doc( $media-link-doc-db-path )
		            
			let $header-content-type := response:set-header( $http:header-content-type , $ap:atom-mimetype )
			
		    let $status-code := response:set-status-code( $http:status-success-created )
		        
		    let $location := $media-link-doc/atom:entry/atom:link[@rel="self"]/@href
		        	
			let $header-location := response:set-header( $http:header-location, $location )
					    
			return $media-link-doc
			
};





declare function ap:do-post-multipart(
	$request-path-info as xs:string 
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

			let $request-data := request:get-uploaded-file-data( "media" )
					
			(: check for file name to use as title :)
			
			let $file-name := request:get-uploaded-file-name( "media" )
			
			(: check for summary param :)
			
			let $summary := request:get-parameter( "summary" , "" )

			(:
			 : Unfortunately eXist's function library doesn't give us any way
			 : to retrieve the content type for the uploaded file, so we'll
			 : work around by using a mapping from file name extensions to
			 : mime types.
			 :)
			 
			let $extension := text:groups( $file-name , "\.([^.]+)$" )[2]
			 
			let $media-type := $mime:mappings//mime-mapping[extension=$extension]/mime-type
			
			let $media-type := if ( empty( $media-type ) ) then "application/octet-stream" else $media-type
			
			let $media-link-doc-db-path := adb:create-media-resource( $request-path-info , $request-data , $media-type , $file-name , $summary )
			
			let $media-link-doc := doc( $media-link-doc-db-path )
		            
		    (:
		     : Although the semantics of 201 Created would be more appropriate 
		     : to the operation performed, we'll respond with 200 OK because the
		     : request is most likely to originate from an HTML form submission
		     : and I don't know how all browsers handle 201 responses.
		     :)
		     
		    let $status-code := response:set-status-code( $http:status-success-ok )
		        
		    let $location := $media-link-doc/atom:entry/atom:link[@rel="self"]/@href
		        	
			let $header-location := response:set-header( $http:header-location, $location )
					    
			let $accept := request:get-header( $http:header-accept )
			
			return 
			
				(: 
				 : Do very naive processing of accept header. If header is set
				 : and is exactly "application/atom+xml" then return the media-
				 : link entry, otherwise fall back to text/html output to 
				 : support browser applications programmatically manipulating
				 : HTML forms.
				 :)
				 
				if ( $accept = "application/atom+xml" )
				
				then 
				
					let $header-content-type := response:set-header( $http:header-content-type , $ap:atom-mimetype )
			
					return $media-link-doc
			
				else 
				
					let $header-content-type := response:set-header( $http:header-content-type , "text/html" )
			
					return 
					
						<html>
							<head>
								<title>{$http:status-success-ok}</title>
							</head>
							<body>{ comment { util:serialize( $media-link-doc/atom:entry , () ) } }</body>
						</html>

};





declare function ap:do-put (
	$request-path-info as xs:string 
) as item()*
{

	let $request-content-type := request:get-header( $http:header-content-type )

	return 

		if ( starts-with( $request-content-type, $ap:atom-mimetype ) )
		
		then ap:do-put-atom( $request-path-info )

		(:		
		else if ( starts-with( $request-content-type, $http:media-type-multipart-form-data ) )
		
		then ap:do-put-multipart( $request-path-info )
		:)
		
		else ap:do-put-media( $request-path-info , $request-content-type )

};




declare function ap:do-put-atom(
	$request-path-info as xs:string 
) as item()*
{

	let $request-data := request:get-data()

	return
	
		if (
			local-name( $request-data ) = $af:feed and 
			namespace-uri( $request-data ) = $af:nsuri
		)
		
		then ap:do-put-atom-feed( $request-path-info , $request-data )

		else if (
			local-name( $request-data ) = $af:entry and 
			namespace-uri( $request-data ) = $af:nsuri
		)
		
		then ap:do-put-atom-entry( $request-path-info , $request-data )
		
		else ap:do-bad-request( $request-path-info , "Request entity must be either atom feed or atom entry." )

};





declare function ap:do-put-atom-feed(
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
	
	(: 
	 : EXPERIMENTAL: enable versioning depending on request header.
	 :)
	 
	let $enable-history := request:get-header( "X-Atom-Enable-History" )
	
	let $enable-history := 
		if ( $enable-history castable as xs:boolean ) then xs:boolean( $enable-history )
		else false()

	let $feed-doc-db-path := 
		if ( $create ) then adb:create-collection( $request-path-info , $request-data , $enable-history )
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





declare function ap:do-put-atom-entry(
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



declare function ap:do-put-media(
	$request-path-info as xs:string ,
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
		
			let $request-data := request:get-data()
			
			let $media-doc-db-path := adb:update-media-resource( $request-path-info , $request-data , $request-content-type )
			
		    let $status-code := response:set-status-code( $http:status-success-ok )
		    
		    (:
		     : TODO review whether we really want to echo the file back to client
		     : or rather return media-link entry, or rather return nothing.
		     :)
		     
		    (: media type :)
		    
		    let $mime-type := adb:get-mime-type( $request-path-info )
		    
		    (: title as filename :)
		    
		    let $media-link := adb:get-media-link( $request-path-info )
		    let $title := $media-link/atom:title
		    let $content-disposition :=
		    	if ( $title ) then response:set-header( $http:header-content-disposition , concat( "attachment; filename=" , $title ) )
		    	else ()
		    
		    (: decoding from base 64 binary :)
		    
		    let $response-stream := response:stream-binary( adb:retrieve-media( $request-path-info ) , $mime-type )
		
			return ()

};




declare function ap:do-get(
	$request-path-info as xs:string 
) as item()*
{

	if ( adb:media-resource-available( $request-path-info ) )
	
	then ap:do-get-media( $request-path-info )
	
	else if ( adb:member-available( $request-path-info ) )
	
	then ap:do-get-entry( $request-path-info )
	
	else if ( adb:collection-available( $request-path-info ) )
	
	then ap:do-get-feed( $request-path-info )

	else ap:do-not-found( $request-path-info )
	
};




(:
 : TODO doc me
 :)
declare function ap:do-get-entry(
	$request-path-info
)
{

	let $request-header-if-modified-since := request:get-header( $http:header-if-modified-since )
	
	(: TODO handle if modified since header :)
	
    let $status-code := response:set-status-code( $http:status-success-ok )
    
    let $header-content-type := response:set-header( $http:header-content-type , $ap:atom-mimetype )
    
    return adb:retrieve-entry( $request-path-info )

};




declare function ap:do-get-media(
	$request-path-info
)
{

    let $status-code := response:set-status-code( $http:status-success-ok )
    
    (: media type :)
    
    let $mime-type := adb:get-mime-type( $request-path-info )
    
    (: title as filename :)
    
    let $media-link := adb:get-media-link( $request-path-info )
    let $title := $media-link/atom:title
    let $content-disposition :=
    	if ( $title ) then response:set-header( $http:header-content-disposition , concat( "attachment; filename=" , $title ) )
    	else ()
    
    (: decoding from base 64 binary :)
    
    let $response-stream := response:stream-binary( adb:retrieve-media( $request-path-info ) , $mime-type )

	return ()
	
};




declare function ap:do-get-feed(
	$request-path-info
)
{

    let $status-code := response:set-status-code( $http:status-success-ok )
    
    let $header-content-type := response:set-header( $http:header-content-type , $ap:atom-mimetype )
    
    return adb:retrieve-feed( $request-path-info )

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

	return $response
		
};



declare function ap:do-bad-request(
	$request-path-info as xs:string ,
	$message as xs:string 
) as item()?
{

	let $request-data := request:get-data()
	
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




declare function ap:do-method-not-allowed(
	$request-path-info
) as item()?
{

	let $request-data := request:get-data()

	let $status-code := response:set-status-code( $http:status-client-error-method-not-allowed )

	let $header-allow := response:set-header( $http:header-allow , "GET POST PUT" )

	(: 
	 : TODO DELETE method and different allows depending on resource identified 
	 :)
	
	let $header-content-type := response:set-header( $http:header-content-type , "application/xml" )

	let $response := 
	
		<response>
			<message>The method specified in the Request-Line is not allowed for the resource identified by the Request-URI.</message>
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