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

let $login := xmldb:login( "/" , "admin" , "" )

let $tests := ()

let $feed :=     
    <atom:feed>
        <atom:title>TEST COLLECTION</atom:title>
    </atom:feed>

let $collection-db-path := atomdb:create-collection( "/test-security", $feed , false() )

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

let $decision := atomsec:decide( "/foo" , "create-collection" , () , "alice" , () )

let $tests := ( $tests , ( $decision = "allow" ) )

let $decision := atomsec:decide( "/foo" , "create-collection" , () , "bob" , () )

let $tests := ( $tests , ( $decision = "deny" ) )

let $decision := atomsec:decide( "/foo" , "create-collection" , () , () , ( "administrator" , "user" ) )

let $tests := ( $tests , ( $decision = "allow" ) )

let $decision := atomsec:decide( "/foo" , "create-collection" , () , () , ( "user" ) )

let $tests := ( $tests , ( $decision = "deny" ) )

let $response-type := response:set-header( "Content-Type" , "text/plain" )

return $tests