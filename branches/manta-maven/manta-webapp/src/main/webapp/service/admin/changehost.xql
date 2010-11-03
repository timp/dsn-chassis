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
import module namespace config-collections = "http://purl.org/atombeat/xquery/config-collections" at "collections.xqm" ;




declare function local:do-get($current-host, $new-host) as item()*
{
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content($current-host, $new-host)
};



declare function local:content($current-host, $new-host) as item()*
{
    let $x := ''
    return
    
        <html>
            <head>
                <title>Change host</title>
            </head>
            <body>
                <h1>Change host</h1>
                
                <p>
                    <form method="post" action="">
                        Current Repository Path <input name="oldhost" size="90" value="{$current-host}" /> <br/>
                        New Repository Path <input name="newhost" size="90" value="{$new-host}" /><br/>
                        <input type="submit" value="Migrate Data"></input>
                    </form>
                    <form method="get" action="">
                        <input type="submit" value="Refresh"></input>
                    </form>
                </p>
            </body>
        </html>
};

declare function local:after-content($current-host, $new-host) as item()*
{
let $x := ''
return

<html>
    <head>
        <title>Change host</title>
    </head>
    <body>
        <h1>Change host</h1>
        
        <p>
            Repository path changed from {$current-host} to {$new-host}
        </p>
    </body>
</html>
};

declare function local:do-modifications($oldhost,$newhost) as element( atom:entry )*
{
	
    let $mod := for $link in //atom:link[contains(@href,$oldhost)]
    	return update value $link/@href with concat($newhost,substring-after($link/@href,$oldhost))
    
    let $mod := for $link in //atom:id[contains(text(),$oldhost)]
    return update value $link/text() with concat($newhost,substring-after($link/text(),$oldhost))
    
    let $mod := for $link in //at:deleted-entry[contains(@ref,$oldhost)]
    return update value $link/@ref with concat($newhost,substring-after($link/@ref,$oldhost))
    
    return $mod
};


declare function local:do-post($old-host,$new-host) as item()*
{
(: SEND RESPONSE :)        
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:after-content($old-host,$new-host)
};


declare function local:do-migration() {
let $oldhost := request:get-parameter("oldhost",'http://localhost:8080/repository')
let $newhost := request:get-parameter("newhost",'http://localhost:8091/repository')

    let $new-content := local:do-modifications($oldhost,$newhost)
        
    return local:do-post($oldhost,$newhost)
};

let $login := xmldb:login( "/" , $config:exist-user , $config:exist-password )
let $host := doc("atombeat/content/studies/.feed")//atom:link[@rel="self"]/@href
let $current-host := substring-before($host,"/content")
let $new-host := $config:service-url-base

return 

    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get($current-host, $new-host)
    
    else if ( request:get-method() = $CONSTANT:METHOD-POST ) 
    
    then local:do-migration()
    
    else common-protocol:do-method-not-allowed( "/admin/changehost.xql" ,"/admin/changehost.xql" , ( "GET" , "POST" ) )
    
    
