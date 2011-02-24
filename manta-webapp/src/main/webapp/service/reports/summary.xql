declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;
declare namespace at = "http://purl.org/atompub/tombstones/1.0";

import module namespace response = "http://exist-db.org/xquery/response" ;

import module namespace config = "http://purl.org/atombeat/xquery/config" at "../config/shared.xqm" ;
import module namespace CONSTANT = "http://purl.org/atombeat/xquery/constants" at "../lib/constants.xqm" ;
import module namespace xutil = "http://purl.org/atombeat/xquery/xutil" at "../lib/xutil.xqm" ;
import module namespace atomsec = "http://purl.org/atombeat/xquery/atom-security" at "../lib/atom-security.xqm" ;
import module namespace atomdb = "http://purl.org/atombeat/xquery/atomdb" at "../lib/atomdb.xqm" ;
import module namespace ap = "http://purl.org/atombeat/xquery/atom-protocol" at "../lib/atom-protocol.xqm" ;
import module namespace common-protocol = "http://purl.org/atombeat/xquery/common-protocol" at "../lib/common-protocol.xqm" ;
import module namespace tombstone-db = "http://purl.org/atombeat/xquery/tombstone-db" at "../lib/tombstone-db.xqm" ;

declare function local:do-get() as item()*
{
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content('')
};



declare function local:content($content) as item()*
{
    let $studies := local:get-content()
    let $study-info := local:get-study-info()
    let $submitted := local:get-submitted()
    let $graves := local:get-submitted-tombstones()  
    return
    
        <html>
            <head>
                <title>Summary of information held in Chassis</title>
            </head>
            <body>
                <h1>Summary of information held in Chassis</h1>
                <p>Total number of Studies { count( $studies ) }</p>
                <p>Total number of Clinical Studies { count( $studies//modules[contains(text(),'clinical')] ) }</p>
                <p>Total number of Molecular Studies { count( $studies//modules[contains(text(),'molecular')] ) }</p>
                <p>Total number of In vitro Studies { count( $studies//modules[contains(text(),'invitro')] ) }</p>
                <p>Total number of Pharmacology Studies { count( $studies//modules[contains(text(),'pharmacology')] ) }</p>
               
                <p>Total number of submitted files { count( $submitted//atom:entry ) + count($graves//at:deleted-entry) }</p>
                <p>Total number of replaced files { count($graves//at:deleted-entry) }</p>
                <pre>
               
                </pre>
            </body>
        </html>
};

declare function local:get-content() {
        let $ret := atomdb:retrieve-members( "/studies" , false() )
        return $ret
};

declare function local:get-study-info() {
        let $ret := atomdb:retrieve-members( "/study-info" , false() )
        return $ret
};
declare function local:get-submitted() {
        let $ret := atomdb:retrieve-feed( "/media/submitted" )
        return $ret
};
declare function local:get-submitted-tombstones() {
    let $ret := tombstone-db:retrieve-tombstones( "/media/submitted" , true() )
    return $ret
};

let $login := xmldb:login( "/" , $config:exist-user , $config:exist-password )

return 

    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get()
       
    else common-protocol:do-method-not-allowed( "/admin/migrate-study-info-1.0-to-1.0.1.xql" ,"/admin/migrate-study-info-1.0-to-1.0.1.xql" , ( "GET" , "POST" ) )
    
    
