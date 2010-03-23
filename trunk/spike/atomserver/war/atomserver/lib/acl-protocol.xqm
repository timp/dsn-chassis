xquery version "1.0";

module namespace acl-protocol = "http://www.cggh.org/2010/atombeat/xquery/acl-protocol";

declare namespace atom = "http://www.w3.org/2005/Atom" ;

import module namespace request = "http://exist-db.org/xquery/request" ;
import module namespace response = "http://exist-db.org/xquery/response" ;
import module namespace text = "http://exist-db.org/xquery/text" ;
import module namespace util = "http://exist-db.org/xquery/util" ;

import module namespace CONSTANT = "http://www.cggh.org/2010/atombeat/xquery/constants" at "constants.xqm" ;

import module namespace xutil = "http://www.cggh.org/2010/atombeat/xquery/xutil" at "xutil.xqm" ;
import module namespace mime = "http://www.cggh.org/2010/atombeat/xquery/mime" at "mime.xqm" ;
import module namespace atomdb = "http://www.cggh.org/2010/atombeat/xquery/atomdb" at "atomdb.xqm" ;
import module namespace atomsec = "http://www.cggh.org/2010/atombeat/xquery/atom-security" at "atom-security.xqm" ;
import module namespace ap = "http://www.cggh.org/2010/xquery/atom-protocol" at "atom-protocol.xqm" ;

import module namespace config = "http://www.cggh.org/2010/atombeat/xquery/config" at "../config/shared.xqm" ;
 


(:
 : TODO doc me
 :)
declare function acl-protocol:do-service()
as item()*
{

	let $request-path-info := request:get-attribute( $ap:param-request-path-info )
	let $request-method := request:get-method()
	
	return
	
		if ( $request-method = $CONSTANT:METHOD-GET )
		
		then acl-protocol:do-get( $request-path-info )
		
		else ap:do-method-not-allowed( $request-path-info , ( "GET" ) )

};




(:
 : TODO doc me 
 :)
declare function acl-protocol:do-get(
	$request-path-info as xs:string 
) as item()*
{
    
    if ( $request-path-info = "/" )
    
    then acl-protocol:do-get-global-acl()
    
    else "TODO"
	
};




declare function acl-protocol:do-get-global-acl() as item()*
{
    (: 
     : We will only allow retrieval of global ACL if user is allowed
     : to update the global ACL.
     :)
     
    let $user := request:get-attribute( $config:user-name-request-attribute-key )
    let $roles := request:get-attribute( $config:user-roles-request-attribute-key )
    let $allowed as xs:boolean :=
        ( atomsec:decide( $user , $roles , "/" , $CONSTANT:OP-UPDATE-ACL ) = $atomsec:decision-allow )
    
    return
    
        if ( not( $allowed ) )
        
        then ap:do-forbidden( "/" ) (: TODO factor these utility methods out :)
        
        else
        
            let $acl := atomsec:retrieve-global-acl()
            let $response-header-set := response:set-header( "Content-Type" , "application/atom+xml" )
            return
                <atom:entry>
                    <atom:content type="application/vnd.atombeat+xml">
                        { $acl }
                    </atom:content>
                </atom:entry>

};


