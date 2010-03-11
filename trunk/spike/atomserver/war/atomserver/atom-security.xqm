module namespace atomsec = "http://www.cggh.org/2010/xquery/atom-security";

declare namespace atom = "http://www.w3.org/2005/Atom" ;

import module namespace text = "http://exist-db.org/xquery/text" ;
import module namespace xmldb = "http://exist-db.org/xquery/xmldb" ;
import module namespace util = "http://exist-db.org/xquery/util" ;

import module namespace utilx = "http://www.cggh.org/2010/xquery/util" at "util.xqm" ;
import module namespace af = "http://www.cggh.org/2010/xquery/atom-format" at "atom-format.xqm" ;
import module namespace atomdb = "http://www.cggh.org/2010/xquery/atom-db" at "atom-db.xqm" ;
import module namespace config = "http://www.cggh.org/2010/xquery/atom-config" at "atom-config.xqm" ;



declare variable $atomsec:op-create-collection as xs:string     := "create-collection" ;
declare variable $atomsec:op-update-collection as xs:string     := "update-collection" ;
declare variable $atomsec:op-list-collection as xs:string       := "list-collection" ;
declare variable $atomsec:op-create-member as xs:string         := "create-member" ;
declare variable $atomsec:op-retrieve-member as xs:string       := "retrieve-member" ;
declare variable $atomsec:op-update-member as xs:string         := "update-member" ;
declare variable $atomsec:op-delete-member as xs:string         := "delete-member" ;
declare variable $atomsec:op-create-media as xs:string          := "create-media" ;
declare variable $atomsec:op-retrieve-media as xs:string        := "retrieve-media" ;
declare variable $atomsec:op-update-media as xs:string          := "update-media" ;
declare variable $atomsec:op-delete-media as xs:string          := "delete-media" ;
declare variable $atomsec:op-update-acl as xs:string            := "update-acl" ;



declare variable $atomsec:decision-deny as xs:string            := "deny" ;
declare variable $atomsec:decision-allow as xs:string           := "allow" ;



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
        
};




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
        
};




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
        
};




declare function atomsec:decide(
    $user as xs:string? ,
    $roles as xs:string* ,
    $request-path-info as xs:string ,
    $operation as xs:string
) as xs:string
{
    atomsec:decide( $user , $roles , $request-path-info , $operation , () )
};




declare function atomsec:decide(
    $user as xs:string? ,
    $roles as xs:string* ,
    $request-path-info as xs:string ,
    $operation as xs:string ,
    $media-type as xs:string?
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
    
    let $log-message := concat( "security decision (" , $decision , ") for user (" , $user , "), roles (" , string-join( $roles , " " ) , "), request-path-info (" , $request-path-info , "), operation(" , $operation , "), media-type (" , $media-type , ")" )
    let $log := util:log( "info" , $log-message )  
    
    return $decision
    
};




declare function atomsec:apply-rules( 
    $acl as element(acl)? ,
    $operation as xs:string ,
    $media-type as xs:string? ,
    $user as xs:string? ,
    $roles as xs:string*
) as xs:string?
{

    let $matching-rules := atomsec:match-rules($acl, $operation, $media-type, $user, $roles)
    
    let $decision := 
        if ( exists( $matching-rules ) ) then local-name( $matching-rules[last()] )
        else ()
    
    return $decision
    
};



declare function atomsec:match-rules( 
    $acl as element(acl)? ,
    $operation as xs:string ,
    $media-type as xs:string? ,
    $user as xs:string? ,
    $roles as xs:string*
) as element()*
{

    let $matching-rules :=
    
        for $rule in $acl/rules/*
        
        return
        
            if (
            
                atomsec:match-operation($rule , $operation)
            
                and ( 
                    atomsec:match-user( $rule , $user ) or    
                    atomsec:match-role( $rule , $roles ) or
                    atomsec:match-group( $rule , $user , $acl )
                ) 
                
                and atomsec:match-media-type( $rule , $media-type )
                
            ) 
            
            then $rule
            
            else ()
            
    
    return $matching-rules
    
};




declare function atomsec:match-operation(
    $rule as element() ,
    $operation as xs:string
) as xs:boolean
{
    ( xs:string( $rule/operation ) = "*" ) or ( xs:string( $rule/operation ) = $operation  ) 
};




declare function atomsec:match-user(
    $rule as element() ,
    $user as xs:string?
) as xs:boolean
{
    ( xs:string( $rule/user ) = "*" ) or ( xs:string( $rule/user ) = $user  ) 
};




declare function atomsec:match-role(
    $rule as element() ,
    $roles as xs:string*
) as xs:boolean
{
    ( xs:string( $rule/role ) = "*" ) or 
    ( exists( $rule/role) and exists( index-of( $roles , xs:string( $rule/role ) ) ) )
};




declare function atomsec:match-group(
    $rule as element() ,
    $user as xs:string? ,
    $acl as element(acl)
) as xs:boolean
{

    let $groups :=
    
        for $group in $acl/groups/group
        let $src := $group/@src
        let $name := $group/@name
        return
            if ( exists( $src) ) then atomsec:dereference-group( $name , $src )
            else $group

    let $groups-for-user := $groups[user=$user]/@name
    
    return
    
        ( xs:string( $rule/group ) = "*" )
        or ( exists( $rule/group) and exists( index-of( $groups-for-user , xs:string( $rule/group ) ) ) )
        
};




declare function atomsec:dereference-group(
    $name as xs:string ,
    $src as xs:string
) as element(group)?
{
    
    let $acl :=
        if ( $src = "/" )
        then atomsec:retrieve-global-acl()
        else if ( atomdb:collection-available( $src ) )
        then atomsec:retrieve-collection-acl( $src )
        else atomsec:retrieve-resource-acl( $src )
        
    return $acl/groups/group[@name=$name]  
    
};




declare function atomsec:match-media-type(
    $rule as element() ,
    $media-type as xs:string*
) as xs:boolean
{

    let $operation := $rule/operation
    let $expected-range := $rule/media-range
    
    return
    
        (: if operation is not on media, do not attempt to match media type :)
        if ( not( ends-with( $operation , "-media" ) ) ) then true()
         
        (: if no expectation defined, match any media type :)
        else if ( empty( $expected-range ) ) then true()

        else

            let $expected-groups := text:groups( $expected-range , "^([^/]*)/(.*)$" )
            let $expected-type := $expected-groups[2]
            let $expected-subtype := $expected-groups[3]
        
            let $actual-groups := 
                if ( exists( $media-type ) ) then text:groups( $media-type , "^([^/]*)/(.*)$" )
                else ()
                
            let $actual-type := $actual-groups[2]
            let $actual-subtype := $actual-groups[3]
            
            return
            
                ( $expected-range = "*/*" )
                or ( ( $expected-type = $actual-type )  and ( $expected-subtype = "*" ) )
                or ( ( $expected-type = $actual-type )  and ( $expected-subtype = $actual-subtype ) )
                    
};


