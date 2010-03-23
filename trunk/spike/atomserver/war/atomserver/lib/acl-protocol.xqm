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
		
		else if ( $request-method = $CONSTANT:METHOD-PUT )
		
		then acl-protocol:do-put( $request-path-info )
		
		else ap:do-method-not-allowed( $request-path-info , ( "GET" , "PUT" ) )

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
    
    else if ( atomdb:collection-available( $request-path-info ) )
    
    then acl-protocol:do-get-collection-acl( $request-path-info )
    
    else if ( atomdb:member-available( $request-path-info ) )
    
    then acl-protocol:do-get-member-acl( $request-path-info )
    
    else ap:do-not-found( $request-path-info )
	
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
                
            (: TODO add updated date :)   
            (: TODO add edit link :)
            (: TODO add self link :)

};





declare function acl-protocol:do-get-collection-acl(
    $request-path-info as xs:string
) as item()*
{
    (: 
     : We will only allow retrieval of collection ACL if user is allowed
     : to update the collection ACL.
     :)
     
    let $user := request:get-attribute( $config:user-name-request-attribute-key )
    let $roles := request:get-attribute( $config:user-roles-request-attribute-key )
    let $allowed as xs:boolean :=
        ( atomsec:decide( $user , $roles , $request-path-info , $CONSTANT:OP-UPDATE-ACL ) = $atomsec:decision-allow )
    
    return
    
        if ( not( $allowed ) )
        
        then ap:do-forbidden( $request-path-info ) (: TODO factor these utility methods out :)
        
        else
        
            let $acl := atomsec:retrieve-collection-acl( $request-path-info )
            let $response-header-set := response:set-header( "Content-Type" , "application/atom+xml" )
            return
                <atom:entry>
                    <atom:content type="application/vnd.atombeat+xml">
                        { $acl }
                    </atom:content>
                </atom:entry>

            (: TODO add updated date :)   
            (: TODO add edit link :)
            (: TODO add self link :)

};




declare function acl-protocol:do-get-member-acl(
    $request-path-info as xs:string
) as item()*
{
    (: 
     : We will only allow retrieval of member ACL if user is allowed
     : to update the member ACL.
     :)
     
    let $user := request:get-attribute( $config:user-name-request-attribute-key )
    let $roles := request:get-attribute( $config:user-roles-request-attribute-key )
    let $allowed as xs:boolean :=
        ( atomsec:decide( $user , $roles , $request-path-info , $CONSTANT:OP-UPDATE-ACL ) = $atomsec:decision-allow )
    
    return
    
        if ( not( $allowed ) )
        
        then ap:do-forbidden( $request-path-info ) (: TODO factor these utility methods out :)
        
        else
        
            let $acl := atomsec:retrieve-resource-acl( $request-path-info )
            let $response-header-set := response:set-header( "Content-Type" , "application/atom+xml" )
            return
                <atom:entry>
                    <atom:content type="application/vnd.atombeat+xml">
                        { $acl }
                    </atom:content>
                </atom:entry>

            (: TODO add updated date :)   
            (: TODO add edit link :)
            (: TODO add self link :)

};




(:
 : TODO doc me 
 :)
declare function acl-protocol:do-put(
	$request-path-info as xs:string 
) as item()*
{
    
    if ( $request-path-info = "/" )
    
    then acl-protocol:do-put-global-acl()
    
    else if ( atomdb:collection-available( $request-path-info ) )
    
    then acl-protocol:do-put-collection-acl( $request-path-info )
    
    else if ( atomdb:member-available( $request-path-info ) )
    
    then acl-protocol:do-put-member-acl( $request-path-info )
    
    else ap:do-not-found( $request-path-info )
	
};





declare function acl-protocol:do-put-global-acl() as item()*
{
     
    let $user := request:get-attribute( $config:user-name-request-attribute-key )
    let $roles := request:get-attribute( $config:user-roles-request-attribute-key )
    let $allowed as xs:boolean :=
        ( atomsec:decide( $user , $roles , "/" , $CONSTANT:OP-UPDATE-ACL ) = $atomsec:decision-allow )
    
    return
    
        if ( not( $allowed ) )
        
        then ap:do-forbidden( "/" ) (: TODO factor these utility methods out :)
        
        else
        
            let $acl := request:get-data()/atom:content[@type="application/vnd.atombeat+xml"]/acl
            return 
                
                if ( empty( $acl ) )
                then ap:do-bad-request( "/" , "Request entity must be Atom entry document with child <atom:content type='application/vnd.atombeat+xml'> with child <acl>." )
                
                else

                    let $acl-updated := atomsec:store-global-acl( $acl )
                    let $acl := atomsec:retrieve-global-acl()
                    let $response-header-set := response:set-header( "Content-Type" , "application/atom+xml" )
                    return
                        <atom:entry>
                            <atom:content type="application/vnd.atombeat+xml">
                                { $acl }
                            </atom:content>
                        </atom:entry>
                        
                    (: TODO add updated date :)   
                    (: TODO add edit link :)
                    (: TODO add self link :)

};

 



declare function acl-protocol:do-put-collection-acl(
    $request-path-info as xs:string
) as item()*
{
    (: 
     : We will only allow retrieval of collection ACL if user is allowed
     : to update the collection ACL.
     :)
     
    let $user := request:get-attribute( $config:user-name-request-attribute-key )
    let $roles := request:get-attribute( $config:user-roles-request-attribute-key )
    let $allowed as xs:boolean :=
        ( atomsec:decide( $user , $roles , $request-path-info , $CONSTANT:OP-UPDATE-ACL ) = $atomsec:decision-allow )
    
    return
    
        if ( not( $allowed ) )
        
        then ap:do-forbidden( $request-path-info ) (: TODO factor these utility methods out :)
        
        else
        
            let $acl := request:get-data()/atom:entry/atom:content[@type="application/vnd.atombeat+xml"]/acl
            return 
                
                if ( empty( $acl ) )
                then ap:do-bad-request( $request-path-info , "Request entity must be Atom entry document with child <atom:content type='application/vnd.atombeat+xml'> with child <acl>." )
                
                else

                    let $acl-updated := atomsec:store-collection-acl( $request-path-info , $acl )
                    let $acl := atomsec:retrieve-collection-acl( $request-path-info )
                    let $response-header-set := response:set-header( "Content-Type" , "application/atom+xml" )
                    return
                        <atom:entry>
                            <atom:content type="application/vnd.atombeat+xml">
                                { $acl }
                            </atom:content>
                        </atom:entry>
        
                    (: TODO add updated date :)   
                    (: TODO add edit link :)
                    (: TODO add self link :)

};




declare function acl-protocol:do-put-member-acl(
    $request-path-info as xs:string
) as item()*
{
    (: 
     : We will only allow retrieval of member ACL if user is allowed
     : to update the member ACL.
     :)
     
    let $user := request:get-attribute( $config:user-name-request-attribute-key )
    let $roles := request:get-attribute( $config:user-roles-request-attribute-key )
    let $allowed as xs:boolean :=
        ( atomsec:decide( $user , $roles , $request-path-info , $CONSTANT:OP-UPDATE-ACL ) = $atomsec:decision-allow )
    
    return
    
        if ( not( $allowed ) )
        
        then ap:do-forbidden( $request-path-info ) (: TODO factor these utility methods out :)
        
        else
        
            let $acl := request:get-data()/atom:entry/atom:content[@type="application/vnd.atombeat+xml"]/acl
            return 
                
                if ( empty( $acl ) )
                then ap:do-bad-request( $request-path-info , "Request entity must be Atom entry document with child <atom:content type='application/vnd.atombeat+xml'> with child <acl>." )
                
                else

                    let $acl-updated := atomsec:store-resource-acl( $request-path-info , $acl )
                    let $acl := atomsec:retrieve-resource-acl( $request-path-info )
                    let $response-header-set := response:set-header( "Content-Type" , "application/atom+xml" )
                    return
                        <atom:entry>
                            <atom:content type="application/vnd.atombeat+xml">
                                { $acl }
                            </atom:content>
                        </atom:entry>
        
                    (: TODO add updated date :)   
                    (: TODO add edit link :)
                    (: TODO add self link :)

};




