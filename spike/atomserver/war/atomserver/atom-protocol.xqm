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
import module namespace atomdb = "http://www.cggh.org/2010/xquery/atom-db" at "atom-db.xqm" ;
import module namespace atomsec = "http://www.cggh.org/2010/xquery/atom-security" at "atom-security.xqm" ;

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
     : Here we bottom out at the "create-collection" operation, so we need to
     : apply a security decision.
     :)
     
    let $forbidden := ap:is-operation-forbidden( $request-path-info, $atomsec:op-create-collection )
        
    return
    
        if ( $forbidden ) 

        then ap:do-forbidden( $request-path-info )

        else
    
        	(: 
        	 : We need to know whether an atom collection already exists at the 
        	 : request path, in which case the request will be treated as an error,
        	 : or whether no atom collection exists at the request path, in which case
        	 : the request will create a new atom collection and initialise the atom
        	 : feed document with the given feed metadata.
        	 :)
        	 
        	let $create := not( atomdb:collection-available( $request-path-info ) )
        	
        	return 
        	
        		if ( $create ) 
        
        		then
        		
        			let $enable-history := request:get-header( "X-Atom-Enable-History" )
        			
        			let $enable-history := 
        				if ( $enable-history castable as xs:boolean ) then xs:boolean( $enable-history )
        				else false()
        
        			let $feed-doc-db-path := atomdb:create-collection( $request-path-info , $request-data , $enable-history )
        		
        			let $feed-doc := doc( $feed-doc-db-path )
        		            
        			let $header-content-type := response:set-header( $http:header-content-type , $ap:atom-mimetype )
        			
        		    let $status-code := response:set-status-code( $http:status-success-no-content )
        		        
        		    let $location := $feed-doc/atom:feed/atom:link[@rel="self"]/@href
        		        	
        			let $header-location := response:set-header( $http:header-location, $location )
        		    
        			return ()
        		
        		else
        		
        			ap:do-bad-request( $request-path-info , "A collection already exists at the given location." )
        	
};




(:
 : TODO doc me
 :)
declare function ap:do-post-atom-entry(
	$request-path-info as xs:string ,
	$request-data as element(atom:entry)
) as item()*
{

	(: 
	 : First we need to know whether an atom collection exists at the 
	 : request path.
	 :)
	 
	let $collection-available := atomdb:collection-available( $request-path-info )
	
	return 
	
		if ( not( $collection-available ) ) 

		then ap:do-not-found( $request-path-info )
		
		else
		
            (: 
             : Here we bottom out at the "create-member" operation, so we need to
             : apply a security decision. 
             :)
             
            let $forbidden := ap:is-operation-forbidden( $request-path-info, $atomsec:op-create-member )
                
            return
            
                if ( $forbidden ) 
        
                then ap:do-forbidden( $request-path-info )
        
                else
        
                    let $comment := request:get-header("X-Atom-Revision-Comment")
        		    
        			let $entry-doc-db-path := atomdb:create-member( $request-path-info , $request-data , $comment )
        		
		    		(: if security is enabled, install default resource ACL :)
		    		let $resource-acl-installed := ap:install-resource-acl( $request-path-info , $entry-doc-db-path )
        			
        			let $entry-doc := doc( $entry-doc-db-path )
        		            
        		    let $location := $entry-doc/atom:entry/atom:link[@rel="self"]/@href
        		        	
        			let $header-location := response:set-header( $http:header-location, $location )
        					    
        			return ap:send-atom( $http:status-success-created , $entry-doc )
        			
};




(:
 : TODO doc me
 :)
declare function ap:do-post-media(
	$request-path-info as xs:string ,
	$request-content-type
) as item()*
{

	(: 
	 : First we need to know whether an atom collection exists at the 
	 : request path.
	 :)
	 
	let $collection-available := atomdb:collection-available( $request-path-info )
	
	return 
	
		if ( not( $collection-available ) ) 

		then ap:do-not-found( $request-path-info )
		
		else
		
            (: 
             : Here we bottom out at the "create-media" operation, so we need to
             : apply a security decision. 
             :)
             
        	let $media-type := text:groups( $request-content-type , "^([^;]+)" )[2]
	
            let $forbidden := ap:is-operation-forbidden( $request-path-info , $atomsec:op-create-media , $media-type )
                
            return
            
                if ( $forbidden ) 
        
                then ap:do-forbidden( $request-path-info )
        
                else
        
                    let $request-data := request:get-data()
        			
        			(: check for slug to use as title :)
        			
        			let $slug := request:get-header( $http:header-slug )
        			
        			(: check for summary :)
        			
        			let $summary := request:get-header( "X-Atom-Summary" )
        			
        		    let $comment := request:get-header("X-Atom-Revision-Comment")
        		    
        			let $media-link-doc-db-path := atomdb:create-media-resource( $request-path-info , $request-data , $media-type , $slug , $summary , $comment )
        			
        			let $media-link-doc := doc( $media-link-doc-db-path )
        		            
        		    let $location := $media-link-doc/atom:entry/atom:link[@rel="self"]/@href
        		        	
        			let $header-location := response:set-header( $http:header-location, $location )
        					    
        			return ap:send-atom( $http:status-success-created , $media-link-doc )
        			
};





declare function ap:do-post-multipart(
	$request-path-info as xs:string 
) as item()*
{

	(: 
	 : First we need to know whether an atom collection exists at the 
	 : request path.
	 :)
	 
	let $collection-available := atomdb:collection-available( $request-path-info )
	
	return 
	
		if ( not( $collection-available ) ) 

		then ap:do-not-found( $request-path-info )
		
		else

            (: TODO bad request if expected form parts are missing :)
            
			let $request-data := request:get-uploaded-file-data( "media" )
					
			(: check for file name to use as title :)
			
			let $file-name := request:get-uploaded-file-name( "media" )
			
			(: check for summary param :)
			
			let $summary := request:get-parameter( "summary" , "" )

		    let $comment := request:get-header("X-Atom-Revision-Comment")
		    
			(:
			 : Unfortunately eXist's function library doesn't give us any way
			 : to retrieve the content type for the uploaded file, so we'll
			 : work around by using a mapping from file name extensions to
			 : mime types.
			 :)
			 
			let $extension := text:groups( $file-name , "\.([^.]+)$" )[2]
			 
			let $media-type := $mime:mappings//mime-mapping[extension=$extension]/mime-type
			
			let $media-type := if ( empty( $media-type ) ) then "application/octet-stream" else $media-type
			
            (: 
             : Here we bottom out at the "create-media" operation, so we need to
             : apply a security decision. 
             :)
             
            let $forbidden := ap:is-operation-forbidden( $request-path-info , $atomsec:op-create-media , $media-type )
                
            return
            
                if ( $forbidden ) 
        
                then ap:do-forbidden( $request-path-info )
        
                else
			
        			let $media-link-doc-db-path := atomdb:create-media-resource( $request-path-info , $request-data , $media-type , $file-name , $summary , $comment )
        			
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




(:
 : TODO doc me 
 :)
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
	 
	(:
	 : TODO this function would probably read more easily if split into 
	 : separate functions for each operation, create-collection and update-
	 : collection.
	 :)
	 
	let $create := not( atomdb:collection-available( $request-path-info ) )
	
    (: 
     : Here we bottom out at either the "create-collection" operation, or the
     : "update-collection" operation, so we need to apply a security decision.
     :)
     
    let $operation :=
        if ( $create ) then $atomsec:op-create-collection
        else $atomsec:op-update-collection
        
    let $forbidden := ap:is-operation-forbidden( $request-path-info , $operation )
        
    return
    
        if ( $forbidden ) 

        then ap:do-forbidden( $request-path-info )

        else
	
        	(: 
        	 : EXPERIMENTAL: enable versioning depending on request header.
        	 :)
        	 
        	let $enable-history := request:get-header( "X-Atom-Enable-History" )
        	
        	let $enable-history := 
        		if ( $enable-history castable as xs:boolean ) then xs:boolean( $enable-history )
        		else false()
        
        	let $feed-doc-db-path := 
        		if ( $create ) then atomdb:create-collection( $request-path-info , $request-data , $enable-history )
        		else atomdb:update-collection( $request-path-info , $request-data )
        		
    		(: if we are creating a collection and security is enabled, install 
    		   default collection ACL :)
    		
    		let $collection-acl-installed :=
    		    if ( $create )
    		    then ap:install-collection-acl( $request-path-info )
    		    else ()
    		
        	let $feed-doc := doc( $feed-doc-db-path )
                    
            let $status-code :=
                if ( $create ) then $http:status-success-created 
                else $http:status-success-ok 
                
            let $location := $feed-doc/atom:feed/atom:link[@rel="self"]/@href
                	
        	let $header-location := 
        	    if ( $create ) then response:set-header( $http:header-location, $location )
        	    else ()
            
        	return ap:send-atom( $status-code , $feed-doc )
	
};




(:
 : TODO doc me
 :)
declare function ap:do-put-atom-entry(
	$request-path-info as xs:string ,
	$request-data as element(atom:entry)
) as item()*
{

	(: 
	 : First we need to know whether an atom entry exists at the 
	 : request path.
	 :)
	 
	let $member-available := atomdb:member-available( $request-path-info )
	
	return 
	
		if ( not( $member-available ) ) 

		then ap:do-not-found( $request-path-info )
		
		else
		
		    (: 
		     : Here we bottom out at the "update-member" operation, so we need
		     : to apply a security decision.
		     :)
            
            let $forbidden := ap:is-operation-forbidden( $request-path-info , $atomsec:op-update-member )
                
            return
            
                if ( $forbidden ) 
        
                then ap:do-forbidden( $request-path-info )
        
                else

        		    let $comment := request:get-header("X-Atom-Revision-Comment")
        		    
        			let $entry-doc-db-path := atomdb:update-member( $request-path-info , $request-data , $comment )
        		
        			let $entry-doc := doc( $entry-doc-db-path )
        		            
        			return ap:send-atom( $http:status-success-ok , $entry-doc )
        
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
	 
	let $found := atomdb:media-resource-available( $request-path-info )
	
	return 
	
		if ( not( $found ) ) 

		then ap:do-not-found( $request-path-info )
		
		else
		
		    (: TODO security decision :)
		    
			let $request-data := request:get-data()
			
			let $media-doc-db-path := atomdb:update-media-resource( $request-path-info , $request-data , $request-content-type )
			
		    let $status-code := response:set-status-code( $http:status-success-ok )
		    
		    (:
		     : TODO review whether we really want to echo the file back to client
		     : or rather return media-link entry, or rather return nothing.
		     :)
		     
		    (: media type :)
		    
		    let $mime-type := atomdb:get-mime-type( $request-path-info )
		    
		    (: title as filename :)
		    
		    let $media-link := atomdb:get-media-link( $request-path-info )
		    let $title := $media-link/atom:title
		    let $content-disposition :=
		    	if ( $title ) then response:set-header( $http:header-content-disposition , concat( "attachment; filename=" , $title ) )
		    	else ()
		    
		    (: decoding from base 64 binary :)
		    
		    let $response-stream := response:stream-binary( atomdb:retrieve-media( $request-path-info ) , $mime-type )
		
			return ()

};




declare function ap:do-get(
	$request-path-info as xs:string 
) as item()*
{

	if ( atomdb:media-resource-available( $request-path-info ) )
	
	then ap:do-get-media( $request-path-info )
	
	else if ( atomdb:member-available( $request-path-info ) )
	
	then ap:do-get-entry( $request-path-info )
	
	else if ( atomdb:collection-available( $request-path-info ) )
	
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
    
    (: 
     : Here we bottom out at the "retrieve-member" operation, so we need
     : to apply a security decision.
     :)

    let $forbidden := ap:is-operation-forbidden( $request-path-info , $atomsec:op-retrieve-member )
        
    return
    
        if ( $forbidden ) 

        then ap:do-forbidden( $request-path-info )

        else ap:send-atom( $http:status-success-ok , atomdb:retrieve-entry( $request-path-info ) )

};




declare function ap:do-get-media(
	$request-path-info
)
{

    (: TODO security decision :)

    let $status-code := response:set-status-code( $http:status-success-ok )
    
    (: media type :)
    
    let $mime-type := atomdb:get-mime-type( $request-path-info )
    
    (: title as filename :)
    
    let $media-link := atomdb:get-media-link( $request-path-info )
    let $title := $media-link/atom:title
    let $content-disposition :=
    	if ( $title ) then response:set-header( $http:header-content-disposition , concat( "attachment; filename=" , $title ) )
    	else ()
    
    (: decoding from base 64 binary :)
    
    let $response-stream := response:stream-binary( atomdb:retrieve-media( $request-path-info ) , $mime-type )

	return ()
	
};




declare function ap:do-get-feed(
	$request-path-info
)
{

    (: 
     : Here we bottom out at the "list-collection" operation, so we need
     : to apply a security decision.
     :)

    let $forbidden := ap:is-operation-forbidden( $request-path-info , $atomsec:op-list-collection )
        
    return
    
        if ( $forbidden ) 

        then ap:do-forbidden( $request-path-info )

        else 
            
            let $feed := atomdb:retrieve-feed( $request-path-info ) 
            
            let $feed := ap:filter-feed-by-acls( $feed )
            
            return ap:send-atom( $http:status-success-ok , $feed )
    
};




declare function ap:filter-feed-by-acls(
    $feed as element(atom:feed)
) as element(atom:feed)
{
    if ( not( $config:enable-security ) )
    then $feed
    else
        <atom:feed>
            {
                $feed/attribute::* ,
                $feed/*[ ( local-name(.) != $af:entry ) and ( namespace-uri(.) != $af:nsuri ) ] ,
                for $entry in $feed/atom:entry
                let $request-path-info := substring-after( $entry/atom:link[@rel="edit"]/@href , $config:service-url )
                let $forbidden := ap:is-operation-forbidden( $request-path-info , $atomsec:op-retrieve-member )
                return 
                    if ( not( $forbidden ) ) then $entry else ()
            }
        </atom:feed>
};



declare function ap:do-not-found(
	$request-path-info
) as item()?
{

    let $message := "The server has not found anything matching the Request-URI."
    
    return ap:send-error( $http:status-client-error-not-found , $message , $request-path-info )

};



declare function ap:do-bad-request(
	$request-path-info as xs:string ,
	$message as xs:string 
) as item()?
{

    let $message := concat( $message , " The request could not be understood by the server due to malformed syntax. The client SHOULD NOT repeat the request without modifications." )

    return ap:send-error( $http:status-client-error-bad-request , $message , $request-path-info )

};




declare function ap:do-method-not-allowed(
	$request-path-info
) as item()?
{

    let $message := "The method specified in the Request-Line is not allowed for the resource identified by the Request-URI."

	(: 
	 : TODO DELETE method and different allows depending on resource identified 
	 :)
	
	let $header-allow := response:set-header( $http:header-allow , "GET POST PUT" )

    return ap:send-error( $http:status-client-error-method-not-allowed , $message , $request-path-info )

};




declare function ap:do-forbidden(
	$request-path-info as xs:string
) as item()?
{

    let $message := "The server understood the request, but is refusing to fulfill it. Authorization will not help and the request SHOULD NOT be repeated."

    return ap:send-error( $http:status-client-error-forbidden , $message , $request-path-info )

};



declare function ap:is-operation-forbidden(
    $request-path-info as xs:string ,
    $operation as xs:string
) as xs:boolean
{

    let $user := request:get-attribute( $config:user-name-request-attribute-key )
    let $roles := request:get-attribute( $config:user-roles-request-attribute-key )
    
    let $forbidden := 
        if ( not( $config:enable-security ) ) then false()
        else ( atomsec:decide( $user , $roles , $request-path-info, $operation ) = $atomsec:decision-deny )
        
    return $forbidden 
    
};



declare function ap:is-operation-forbidden(
    $request-path-info as xs:string ,
    $operation as xs:string ,
    $media-type as xs:string
) as xs:boolean
{

    let $user := request:get-attribute( $config:user-name-request-attribute-key )
    let $roles := request:get-attribute( $config:user-roles-request-attribute-key )
    
    let $forbidden := 
        if ( not( $config:enable-security ) ) then false()
        else ( atomsec:decide( $user , $roles , $request-path-info, $operation , $media-type ) = $atomsec:decision-deny )
        
    return $forbidden 
    
};



declare function ap:install-resource-acl(
    $request-path-info as xs:string,
    $entry-doc-db-path as xs:string
) as xs:string?
{
    if ( $config:enable-security )
    then 
        let $user := request:get-attribute( $config:user-name-request-attribute-key )
        let $acl := config:default-resource-acl( $request-path-info , $user )
        let $entry-path-info := atomdb:db-path-to-request-path-info( $entry-doc-db-path )
        let $acl-db-path := atomsec:store-resource-acl( $entry-path-info , $acl )
    	return $acl-db-path
    else ()
};




declare function ap:install-collection-acl( $request-path-info as xs:string ) as xs:string?
{
    if ( $config:enable-security )
    then 
        let $user := request:get-attribute( $config:user-name-request-attribute-key )
        let $acl := config:default-collection-acl( $request-path-info , $user )
        return atomsec:store-collection-acl( $request-path-info , $acl )
    else ()
};




declare function ap:send-atom(
    $status as xs:integer ,
    $data as item()
)
{

    let $status-code := response:set-status-code( $status )
    
    let $header-content-type := response:set-header( $http:header-content-type , $ap:atom-mimetype )
    
    return $data

};



(:
 : TODO doc me
 :)
declare function ap:send-error(
    $status-code as xs:integer , 
    $message as xs:string? ,
    $request-path-info as xs:string?
) as item()*
{

	let $status-code-set := response:set-status-code( $status-code )

	let $header-content-type := response:set-header( $http:header-content-type , "application/xml" )

	let $response := 
	
		<error>
		    <status>{$status-code}</status>
			<message>{$message}</message>
			<service-url>{$config:service-url}</service-url>
			<method>{request:get-method()}</method>
			<path-info>{$request-path-info}</path-info>
			<parameters>
			{
				for $parameter-name in request:get-parameter-names()
				return
				    <parameter>
				        <name>{$parameter-name}</name>
				        <value>{request:get-parameter( $parameter-name , "" )}</value>						
					</parameter>
			}
			</parameters>
			<headers>
			{
				for $header-name in request:get-header-names()
				return
				    <header>
				        <name>{$header-name}</name>
				        <value>{request:get-header( $header-name )}</value>						
					</header>
			}
			</headers>
			<user>{request:get-attribute($config:user-name-request-attribute-key)}</user>
			<roles>{string-join(request:get-attribute($config:user-roles-request-attribute-key), " ")}</roles>
		</error>
			
	return $response

};