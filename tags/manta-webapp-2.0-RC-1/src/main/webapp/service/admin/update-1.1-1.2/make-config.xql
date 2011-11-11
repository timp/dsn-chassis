declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;
declare namespace at = "http://purl.org/atompub/tombstones/1.0";

import module namespace response = "http://exist-db.org/xquery/response" ;

import module namespace config = "http://purl.org/atombeat/xquery/config" at "../../config/shared.xqm" ;
import module namespace CONSTANT = "http://purl.org/atombeat/xquery/constants" at "../../lib/constants.xqm" ;
import module namespace xutil = "http://purl.org/atombeat/xquery/xutil" at "../../lib/xutil.xqm" ;
import module namespace atomsec = "http://purl.org/atombeat/xquery/atom-security" at "../../lib/atom-security.xqm" ;
import module namespace atomdb = "http://purl.org/atombeat/xquery/atomdb" at "../../lib/atomdb.xqm" ;
import module namespace ap = "http://purl.org/atombeat/xquery/atom-protocol" at "../../lib/atom-protocol.xqm" ;
import module namespace common-protocol = "http://purl.org/atombeat/xquery/common-protocol" at "../../lib/common-protocol.xqm" ;
import module namespace config-collections = "http://purl.org/atombeat/xquery/config-collections" at "../collections.xqm" ;
import module namespace security-config = "http://purl.org/atombeat/xquery/security-config" at "../../config/security.xqm" ;



declare function local:do-get($current-host, $new-host) as item()*
{
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content($current-host, $new-host)
};



declare function local:content($current-host, $new-host) as item()*
{
    let $x := atomdb:collection-available('/config')
    return
    
        <html>
            <head>
                <title>Creating config collection</title>
            </head>
            <body>
                <h1>Creating config collection</h1>
                
                <p>
                    Config collection available: {$x}<br/>
                    <form method="post" action="">
                        <input type="submit" value="Create collection"></input>
                    </form>
                    <form method="get" action="">
                        <input type="submit" value="Refresh"></input>
                    </form>
                </p>
                 <p>
                    <a href="index.xhtml">Return to index</a>
                </p>
            </body>
        </html>
};

declare function local:after-content($new-content) as item()*
{
let $x := ''
return

<html>
    <head>
        <title>Creating config collection</title>
    </head>
    <body>
        <h1>Creating config collection</h1>
        
        <p>
            {$new-content}
        </p>
        <p>
            <a href="index.xhtml">Return to index</a>
        </p>
    </body>
</html>
};

declare function local:create-config-collection() as xs:string {
    let $user-name := request:get-attribute( $config:user-name-request-attribute-key )
    let $collection-path-info := '/config'
    
    let $feed :=
        <atom:feed atombeat:enable-versioning="true"
                atombeat:recursive="false">
            <atom:title type="text">Config</atom:title>
        </atom:feed>
        
    let $collection-db-path := atomdb:create-collection( $collection-path-info , $feed, $user-name )
    let $collection-descriptor := $security-config:config-collection-security-descriptor
    let $descriptor-stored := atomsec:store-descriptor( $collection-path-info , $collection-descriptor )
    
    return $descriptor-stored
};

declare function local:do-post($old-host) as item()*
{
(: SEND RESPONSE :)        
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:after-content($old-host)
};


declare function local:do-migration() {

    let $new-collection := local:create-config-collection()
        
    return local:do-post($new-collection)
};

let $login := xmldb:login( "/" , $config:exist-user , $config:exist-password )
(: If this fails with a null pointer exception then you need to reindex the database :)
let $host := doc("atombeat/content/studies/.feed")//atom:link[@rel="self"]/@href
let $current-host := substring-before($host,"/content")
let $new-host := $config:service-url-base

return 

    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get($current-host, $new-host)
    
    else if ( request:get-method() = $CONSTANT:METHOD-POST ) 
    
    then local:do-migration()
    
    else ()
    
    
