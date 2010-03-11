declare namespace atom = "http://www.w3.org/2005/Atom" ;

import module namespace request = "http://exist-db.org/xquery/request" ;
import module namespace response = "http://exist-db.org/xquery/response" ;
import module namespace text = "http://exist-db.org/xquery/text" ;
import module namespace util = "http://exist-db.org/xquery/util" ;

import module namespace http = "http://www.cggh.org/2010/xquery/http" at "../http.xqm" ;
import module namespace mime = "http://www.cggh.org/2010/xquery/mime" at "../mime-types.xqm" ;
import module namespace config = "http://www.cggh.org/2010/xquery/atom-config" at "../atom-config.xqm" ;
import module namespace af = "http://www.cggh.org/2010/xquery/atom-format" at "../atom-format.xqm" ;
import module namespace atomdb = "http://www.cggh.org/2010/xquery/atom-db" at "../atom-db.xqm" ;
import module namespace atomsec = "http://www.cggh.org/2010/xquery/atom-security" at "../atom-security.xqm" ;

let $login := xmldb:login( "/" , "admin" , "" )

let $global-acl-installed := atomsec:store-global-acl( $config:default-global-acl )

let $status-set := response:set-status-code( 200 )

let $response-content-type-set := response:set-header( "Content-Type" , "text/plain" )

return "OK"