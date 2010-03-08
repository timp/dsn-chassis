xquery version "1.0";

declare namespace atom = "http://www.w3.org/2005/Atom" ;

import module namespace ap = "http://www.cggh.org/2010/xquery/atom-protocol" at "atom-protocol.xqm" ;

import module namespace text = "http://exist-db.org/xquery/text" ;
import module namespace xmldb = "http://exist-db.org/xquery/xmldb" ;
import module namespace util = "http://exist-db.org/xquery/util" ;

import module namespace utilx = "http://www.cggh.org/2010/xquery/util" at "util.xqm" ;
import module namespace af = "http://www.cggh.org/2010/xquery/atom-format" at "atom-format.xqm" ;
import module namespace atomdb = "http://www.cggh.org/2010/xquery/atom-db" at "atom-db.xqm" ;
import module namespace config = "http://www.cggh.org/2010/xquery/atom-config" at "atom-config.xqm" ;
import module namespace atomsec = "http://www.cggh.org/2010/xquery/atom-security" at "atom-security.xqm" ;



declare function local:setup() as empty()
{

    let $login := xmldb:login( "/" , "admin" , "" )
    
    let $feed :=     
        <atom:feed>
            <atom:title>TEST COLLECTION</atom:title>
        </atom:feed>
    
    let $collection-db-path := atomdb:create-collection( "/test-security", $feed , false() )
    
    return ()

};



declare function local:assert-true( $value as item()? , $message as xs:string? ) as item()*
{
    if ( xs:boolean( $value ) ) then "pass"
    else concat( "fail: " , $message )
};




declare function local:assert-equals( $expected as item()? , $actual as item()? , $message as xs:string? )
{
    if ( $expected = $actual ) then "pass"
    else concat( "fail: expected (" , xs:string($expected) , "), actual (" , xs:string($actual) , "); " , $message )
};




declare function local:test-global-acl() as item()*
{

    let $output := ( "test-global-acl..." )
    
    let $global-acl :=
        <acl>
            <rules>
                <allow>
                    <user>alice</user>
                    <operation>create-collection</operation>
                </allow>
                <allow>
                    <role>administrator</role>
                    <operation>create-collection</operation>
                </allow>
            </rules>
        </acl>
        
    let $global-acl-doc-db-path := atomsec:store-global-acl($global-acl)
    
    (: test allow by user name :)
    
    let $request-path-info := "/foo"
    let $operation := "create-collection"
    let $media-type := ()
    let $user := "alice"
    let $roles := ()
    let $decision := atomsec:decide( $request-path-info , $operation , $media-type , $user , $roles )
    let $output := ( $output , local:assert-equals( "allow" , $decision , "alice should be allowed to create collections" ) )
    
    (: test deny by user name :)
    
    let $request-path-info := "/foo"
    let $operation := "create-collection"
    let $media-type := ()
    let $user := "bob"
    let $roles := ()
    let $decision := atomsec:decide( $request-path-info , $operation , $media-type , $user , $roles )
    let $output := ( $output , local:assert-equals( "deny" , $decision , "bob should not be allowed to create collections" ) )
    
    (: test allow by role :)
    
    let $request-path-info := "/foo"
    let $operation := "create-collection"
    let $media-type := ()
    let $user := ()
    let $roles := ( "administrator" , "user" )
    let $decision := atomsec:decide( $request-path-info , $operation , $media-type , $user , $roles )
    let $output := ( $output , local:assert-equals( "allow" , $decision , "administrators should be allowed to create collections" ) )
    
    (: test deny by role :)
    
    let $request-path-info := "/foo"
    let $operation := "create-collection"
    let $media-type := ()
    let $user := ()
    let $roles := ( "user" )
    let $decision := atomsec:decide( $request-path-info , $operation , $media-type , $user , $roles )
    let $output := ( $output , local:assert-equals( "deny" , $decision , "users should not be allowed to create collections" ) )
    
    return $output
    
};




declare function local:test-collection-acl() as item()*
{

    let $output := ( "test-collection-acl..." )
    
    let $global-acl :=
        <acl>
            <rules>
                <allow>
                    <role>administrator</role>
                    <operation>create-member</operation>
                </allow>
            </rules>
        </acl>
        
    let $global-acl-doc-db-path := atomsec:store-global-acl($global-acl)

    let $collection-acl :=
        <acl>
            <rules>
                <allow>
                    <user>alice</user>
                    <operation>create-member</operation>
                </allow>
                <allow>
                    <role>author</role>
                    <operation>create-member</operation>
                </allow>
            </rules>
        </acl>
        
    let $collection-acl-doc-db-path := atomsec:store-collection-acl( "/test-security" , $collection-acl )
    
    (: test allow by user name :)
    
    let $request-path-info := "/test-security"
    let $operation := "create-member"
    let $media-type := ()
    let $user := "alice"
    let $roles := ()
    let $decision := atomsec:decide( $request-path-info , $operation , $media-type , $user , $roles )
    let $output := ( $output , local:assert-equals( "allow" , $decision , "alice should be allowed to create members of the /test-security collection" ) )
  
    (: test deny by user name :)
    
    let $request-path-info := "/test-security"
    let $operation := "create-member"
    let $media-type := ()
    let $user := "bob"
    let $roles := ()
    let $decision := atomsec:decide( $request-path-info , $operation , $media-type , $user , $roles )
    let $output := ( $output , local:assert-equals( "deny" , $decision , "bob should not be allowed to create members of the /test-security collection" ) )
  
    (: test deny by path :)
    
    let $request-path-info := "/another"
    let $operation := "create-member"
    let $media-type := ()
    let $user := "alice"
    let $roles := ()
    let $decision := atomsec:decide( $request-path-info , $operation , $media-type , $user , $roles )
    let $output := ( $output , local:assert-equals( "deny" , $decision , "alice should not be allowed to create members of the /another collection" ) )
  
    (: test allow by role :)
    
    let $request-path-info := "/test-security"
    let $operation := "create-member"
    let $media-type := ()
    let $user := ()
    let $roles := ( "author" )
    let $decision := atomsec:decide( $request-path-info , $operation , $media-type , $user , $roles )
    let $output := ( $output , local:assert-equals( "allow" , $decision , "authors should be allowed to create members of the /test-security collection" ) )
  
    (: test deny by role :)
    
    let $request-path-info := "/test-security"
    let $operation := "create-member"
    let $media-type := ()
    let $user := ()
    let $roles := ( "user" )
    let $decision := atomsec:decide( $request-path-info , $operation , $media-type , $user , $roles )
    let $output := ( $output , local:assert-equals( "deny" , $decision , "users should not be allowed to create members of the /test-security collection" ) )
  
    (: test allow by role from global acl :)
    
    let $request-path-info := "/test-security"
    let $operation := "create-member"
    let $media-type := ()
    let $user := ()
    let $roles := ( "administrator" )
    let $decision := atomsec:decide( $request-path-info , $operation , $media-type , $user , $roles )
    let $output := ( $output , local:assert-equals( "allow" , $decision , "administrators should be allowed to create members of any collection" ) )
  
    return $output
    
};


declare function local:main() as item()*
{

    let $setup := local:setup()
    let $output := (
        local:test-global-acl() ,
        local:test-collection-acl()
    )
    let $response-type := response:set-header( "Content-Type" , "text/plain" )
    return $output
    
};




local:main()

