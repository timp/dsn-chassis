xquery version "1.0";

module namespace sp = "http://www.cggh.org/2010/atombeat/xquery/security-plugin";

declare namespace atom = "http://www.w3.org/2005/Atom" ;


import module namespace request = "http://exist-db.org/xquery/request" ;
import module namespace response = "http://exist-db.org/xquery/response" ;
import module namespace text = "http://exist-db.org/xquery/text" ;
import module namespace util = "http://exist-db.org/xquery/util" ;

import module namespace CONSTANT = "http://www.cggh.org/2010/atombeat/xquery/constants" at "constants.xqm" ;

import module namespace config = "http://www.cggh.org/2010/atombeat/xquery/config" at "../config/shared.xqm" ;

import module namespace xutil = "http://www.cggh.org/2010/atombeat/xquery/xutil" at "../lib/xutil.xqm" ;
import module namespace mime = "http://www.cggh.org/2010/atombeat/xquery/mime" at "../lib/mime.xqm" ;
import module namespace atomdb = "http://www.cggh.org/2010/atombeat/xquery/atomdb" at "../lib/atomdb.xqm" ;
import module namespace atomsec = "http://www.cggh.org/2010/xquery/atom-security" at "../lib/atom-security.xqm" ;





declare function sp:before(
	$operation as xs:string ,
	$request-path-info as xs:string ,
	$request-data as item()* ,
	$request-media-type as xs:string?
) as item()*
{
	
	let $message := concat( "security plugin, before: " , $operation , ", request-path-info: " , $request-path-info ) 
	let $log := util:log( "debug" , $message )

	let $forbidden := sp:is-operation-forbidden( $operation , $request-path-info , $request-media-type )
	
	return 
	
		if ( $forbidden )
		
		then (: override request processing :)
		
			let $status-code := $CONSTANT:STATUS-CLIENT-ERROR-FORBIDDEN
		    let $response-data := "The server understood the request, but is refusing to fulfill it. Authorization will not help and the request SHOULD NOT be repeated."
			let $response-content-type := "text/plain"
			return ( $request-data , $status-code , $response-data , $response-content-type )
			
		else
		
			let $status-code := () (: leave empty because we don't want to interrupt request processing :)
			let $response-data := ()
			let $response-content-type := ()
			return ( $request-data , $status-code , $response-data , $response-content-type )

};




declare function sp:after(
	$operation as xs:string ,
	$request-path-info as xs:string ,
	$response-data as item()* ,
	$response-content-type as xs:string?
) as item()*
{

	let $message := concat( "security plugin, after: " , $operation , ", request-path-info: " , $request-path-info ) 
	let $log := util:log( "debug" , $message )

	return
		
		if ( $operation = $CONSTANT:OP-CREATE-MEMBER )
		
		then sp:after-create-member( $request-path-info , $response-data , $response-content-type )

		else if ( $operation = $CONSTANT:OP-CREATE-COLLECTION )
		
		then sp:after-create-collection( $request-path-info , $response-data , $response-content-type )

		else if ( $operation = $CONSTANT:OP-LIST-COLLECTION )
		
		then sp:after-list-collection( $request-path-info , $response-data , $response-content-type )

		else 

			(: pass response data and content type through, we don't want to modify response :)
			( $response-data , $response-content-type )
}; 




declare function sp:after-create-member(
	$request-path-info as xs:string ,
	$response-data as item()* ,
	$response-content-type as xs:string?
) as item()*
{

	let $entry-uri := $response-data/atom:link[@rel="edit"]/@href
	let $log := util:log( "debug" , concat( "$entry-uri: " , $entry-uri ) )
	
	let $entry-path-info := substring-after( $entry-uri , $config:service-url )
	let $log := util:log( "debug" , concat( "$entry-path-info: " , $entry-path-info ) )

	let $entry-doc-db-path := atomdb:request-path-info-to-db-path( $entry-path-info )
	let $log := util:log( "debug" , concat( "$entry-doc-db-path: " , $entry-doc-db-path ) )
	
	(: if security is enabled, install default resource ACL :)
	let $resource-acl-installed := sp:install-resource-acl( $request-path-info , $entry-doc-db-path )
	let $log := util:log( "debug" , concat( "$resource-acl-installed: " , $resource-acl-installed ) )

	return ( $response-data , $response-content-type )

};




declare function sp:after-create-collection(
	$request-path-info as xs:string ,
	$response-data as item()* ,
	$response-content-type as xs:string?
) as item()*
{

	(: if security is enabled, install default collection ACL :)
	let $collection-acl-installed := sp:install-collection-acl( $request-path-info )

	return ( $response-data , $response-content-type )

};




declare function sp:after-list-collection(
	$request-path-info as xs:string ,
	$response-data as item()* ,
	$response-content-type as xs:string?
) as item()*
{

	let $response-data := sp:filter-feed-by-acls( $response-data )

	return ( $response-data , $response-content-type )

};




declare function sp:install-resource-acl(
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




declare function sp:install-collection-acl( $request-path-info as xs:string ) as xs:string?
{
    if ( $config:enable-security )
    then 
        let $user := request:get-attribute( $config:user-name-request-attribute-key )
        let $acl := config:default-collection-acl( $request-path-info , $user )
        return atomsec:store-collection-acl( $request-path-info , $acl )
    else ()
};




declare function sp:filter-feed-by-acls(
    $feed as element(atom:feed)
) as element(atom:feed)
{
    if ( not( $config:enable-security ) )
    then $feed
    else
        <atom:feed>
            {
                $feed/attribute::* ,
                $feed/*[ ( local-name(.) != $CONSTANT:ATOM-ENTRY ) and ( namespace-uri(.) != $CONSTANT:ATOM-NSURI ) ] ,
                for $entry in $feed/atom:entry
                let $request-path-info := substring-after( $entry/atom:link[@rel="edit"]/@href , $config:service-url )
                let $forbidden := sp:is-operation-forbidden( $request-path-info , $CONSTANT:OP-RETRIEVE-MEMBER , () )
                return 
                    if ( not( $forbidden ) ) then $entry else ()
            }
        </atom:feed>
};







declare function sp:is-operation-forbidden(
    $operation as xs:string ,
    $request-path-info as xs:string ,
    $request-media-type as xs:string?
) as xs:boolean
{

    let $user := request:get-attribute( $config:user-name-request-attribute-key )
    let $roles := request:get-attribute( $config:user-roles-request-attribute-key )
    
    let $forbidden := 
        if ( not( $config:enable-security ) ) then false()
        else ( atomsec:decide( $user , $roles , $request-path-info, $operation , $request-media-type ) = $atomsec:decision-deny )
        
    return $forbidden 
    
};






