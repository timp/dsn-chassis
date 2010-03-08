module namespace atomsec = "http://www.cggh.org/2010/xquery/atom-security";

declare namespace atom = "http://www.w3.org/2005/Atom" ;

import module namespace text = "http://exist-db.org/xquery/text" ;
import module namespace xmldb = "http://exist-db.org/xquery/xmldb" ;
import module namespace util = "http://exist-db.org/xquery/util" ;

import module namespace utilx = "http://www.cggh.org/2010/xquery/util" at "util.xqm" ;
import module namespace af = "http://www.cggh.org/2010/xquery/atom-format" at "atom-format.xqm" ;
import module namespace atomdb = "http://www.cggh.org/2010/xquery/atom-db" at "atom-db.xqm" ;
import module namespace config = "http://www.cggh.org/2010/xquery/atom-config" at "atom-config.xqm" ;




declare function atomsec:store-global-acl(
    $acl as element(acl)
) as item()*
{
    
    let $base-acl-collection-db-path := utilx:get-or-create-collection( $config:base-acl-collection-path )
    
    let $global-acl-doc-db-path := xmldb:store( $base-acl-collection-db-path , ".acl" , $acl )
    
    return $global-acl-doc-db-path
    
};




declare function atomsec:store-collection-acl(
    $request-path-info as xs:string ,
    $acl as element(acl)
) as xs:string?
{

    if ( atomdb:collection-available( $request-path-info ) )
    
    then 

        let $acl-collection-db-path := concat( $config:base-acl-collection-path , atomdb:request-path-info-to-db-path( $request-path-info ) )
        
        let $acl-collection-db-path := utilx:get-or-create-collection( $acl-collection-db-path )
        
        let $acl-doc-db-path := xmldb:store( $acl-collection-db-path , ".acl" , $acl )
        
        return $acl-doc-db-path

    else ()
};




declare function atomsec:store-resource-acl(
    $request-path-info as xs:string ,
    $acl as element(acl)
) as xs:string?
{

    if ( atomdb:media-resource-available( $request-path-info ) or atomdb:member-available( $request-path-info ) )
    
    then

    	let $groups := text:groups( $request-path-info , "^(.*)/([^/]+)$" )
    	
    	let $collection-db-path := atomdb:request-path-info-to-db-path( $groups[2] )
    	
    	let $acl-collection-db-path := concat( $config:base-acl-collection-path , $collection-db-path )
    	
        let $acl-collection-db-path := utilx:get-or-create-collection( $acl-collection-db-path )
        
    	let $resource-name := $groups[3]
    	
    	let $acl-doc-name := concat( $resource-name , ".acl" )
    	
    	let $acl-doc-db-path := xmldb:store( $acl-collection-db-path , $acl-doc-name , $acl )
    	
    	return $acl-doc-db-path
        
    else ()

};




declare function atomsec:retrieve-global-acl() as element(acl)?
{

    let $acl-doc-db-path := concat( $config:base-acl-collection-path , "/.acl" )

    let $acl-doc := doc( $acl-doc-db-path )
    
    return $acl-doc/acl
        
}




declare function atomsec:retrieve-collection-acl(
    $request-path-info as xs:string
) as element(acl)?
{

    if ( atomdb:collection-available( $request-path-info ) )
    
    then

        (: TODO what if collection path is given with trailing slash? :)
        
        let $acl-doc-db-path := concat( $config:base-acl-collection-path , atomdb:request-path-info-to-db-path( $request-path-info ) , "/.acl" )
    
        let $acl-doc := doc( $acl-doc-db-path )
        
        return $acl-doc/acl

    else if ( atomdb:media-resource-available( $request-path-info ) or atomdb:member-available( $request-path-info ) )
    
    then 
    
        let $groups := text:groups( $request-path-info , "^(.*)/([^/]+)$" )
    	
    	return atomsec:retrieve-collection-acl( $groups[2] )
    
    else
    
        ()
        
}




declare function atomsec:retrieve-resource-acl(
    $request-path-info as xs:string
) as element(acl)?
{

    if ( atomdb:media-resource-available( $request-path-info ) or atomdb:member-available( $request-path-info ) )
    
    then

        let $acl-doc-db-path := concat( $config:base-acl-collection-path , atomdb:request-path-info-to-db-path( $request-path-info ) , ".acl" )
    
        let $acl-doc := doc( $acl-doc-db-path )
        
        return $acl-doc/acl
        
    else
    
        ()
        
}



declare function atomsec:decide(
    $request-path-info as xs:string ,
    $operation as xs:string ,
    $media-type as xs:string? ,
    $user as xs:string ,
    $roles as xs:string*
) as xs:string
{

    (: first we need to find the relevant ACLs :)
    
    (: if the request path identifies a atom collection member or media resource
     : then we need to find the resource ACL first :)
     
    let $resource-acl := atomsec:retrieve-resource-acl( $request-path-info )
    
    (: we also need the collection ACL :)
    
    let $collection-acl := atomsec:retrieve-collection-acl( $request-path-info )
    
    (: we also need the global ACL :)
    
    let $global-acl := atomsec:retrieve-global-acl()
    
    (: start from default decision :)
    
    let $decision := $config:default-decision
    
    (: now process ACLs in order, starting from resource ACL, then collection ACL,
     : then global ACL. :)
    
    let $resource-decision := atomsec:apply-rules( $resource-acl , $operation , $media-type , $user , $roles )
    
    (: any resource decision overrides default decision :)
    
    let $decision := 
        if ( exists( $resource-decision ) ) 
        then $resource-decision
        else $decision
        
    let $collection-decision := atomsec:apply-rules( $collection-acl , $operation , $media-type , $user , $roles )   

    (: any collection decision overrides resource decision :)
    
    let $decision := 
        if ( exists( $collection-decision ) ) 
        then $collection-decision
        else $decision
        
    let $global-decision := atomsec:apply-rules( $global-acl , $operation , $media-type , $user , $roles )  
    
    (: any global decision overrides resource decision :)

    let $decision := 
        if ( exists( $global-decision ) ) 
        then $global-decision
        else $decision
    
    return $decision
    
};




declare function atomsec:apply-rules( 
    $acl as element(acl) ,
    $operation as xs:string ,
    $media-type as xs:string? ,
    $user as xs:string ,
    $roles as xs:string*
) as xs:string?
{

    let $matching-rules :=
    
        for $rule in $acl/rules/*
        
        return
        
            if (
            
                $operation = $rule/operation and
            
                ( exists( $rule/user ) and $rule/user = $user ) and
                
                ( exists( $rule/role ) and exists( index-of( $roles , xs:string( $rule/role ) ) ) )
                
                (: TODO match media-range :)
                
            ) 
            
            then $rule
            
            else ()
            
    let $final-rule := $matching-rules[last()]
    
    return local-name( $final-rule )
    
};