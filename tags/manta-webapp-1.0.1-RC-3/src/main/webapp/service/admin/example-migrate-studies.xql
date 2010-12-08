declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;

import module namespace response = "http://exist-db.org/xquery/response" ;

import module namespace config = "http://purl.org/atombeat/xquery/config" at "../config/shared.xqm" ;
import module namespace CONSTANT = "http://purl.org/atombeat/xquery/constants" at "../lib/constants.xqm" ;
import module namespace xutil = "http://purl.org/atombeat/xquery/xutil" at "../lib/xutil.xqm" ;
import module namespace atomsec = "http://purl.org/atombeat/xquery/atom-security" at "../lib/atom-security.xqm" ;
import module namespace atomdb = "http://purl.org/atombeat/xquery/atomdb" at "../lib/atomdb.xqm" ;
import module namespace ap = "http://purl.org/atombeat/xquery/atom-protocol" at "../lib/atom-protocol.xqm" ;
import module namespace common-protocol = "http://purl.org/atombeat/xquery/common-protocol" at "../lib/common-protocol.xqm" ;
import module namespace config-collections = "http://purl.org/atombeat/xquery/config-collections" at "collections.xqm" ;





declare function local:do-get() as item()*
{
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content()
};



declare function local:content() as item()*
{
    let $studies := atomdb:retrieve-members( "/studies" , false() )
    let $studies-v1 := $studies[atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/1.0']
    let $studies-v2 := $studies[atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/2.0']
    
    return
    
        <html>
            <head>
                <title>Example Data Migration</title>
            </head>
            <body>
                <h1>Example Data Migration</h1>
                <p>Total number of entries in <a href="../content/studies">Studies</a> collection: <strong>{ count( $studies ) }</strong></p>
                <p>Number of entries in <a href="../content/studies">Studies</a> collection using v1 profile: <strong>{ count( $studies-v1 ) }</strong></p>
                <p>Number of entries in <a href="../content/studies">Studies</a> collection using v2 profile: <strong>{ count( $studies-v2 ) }</strong></p>
                <p>
                    <form method="post" action="">
                        <input type="submit" value="Migrate Data"></input>
                    </form>
                    <form method="get" action="">
                        <input type="submit" value="Refresh"></input>
                    </form>
                </p>
            </body>
        </html>
};



declare function local:do-post() as item()*
{

    let $studies := atomdb:retrieve-members( "/studies" , false() )
    let $studies-v1 := $studies[atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/1.0']
    
    let $studies-v2 := 
        for $v1 in $studies-v1
        return 
            <atom:entry>
            {
                $v1/attribute::* ,
                for $e in $v1/child::* 
                return
                    if (
                        local-name( $e ) = $CONSTANT:ATOM-CONTENT
                        and namespace-uri( $e ) = $CONSTANT:ATOM-NSURI
                    )
                    then 
                        <atom:content>
                        {
                            $e/attribute::* ,
                            local:migrate-study( $e/study )
                        }
                        </atom:content>
                    else $e
            }
            </atom:entry>
            
    let $migrated :=     
        for $v2 in $studies-v2 
        let $path-info := atomdb:edit-path-info( $v2 )
        return atomdb:update-member( $path-info , $v2 )
    
    (: SEND RESPONSE :)        
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content()
};




declare function local:migrate-study( $study as element( study ) ) as element( study )
{
    <study profile="http://www.cggh.org/2010/chassis/manta/2.0">
    {
        for $e in $study/child::* 
        return
            if ( local-name( $e ) = "publications" ) 
            then (: rename "publications" to "citations" :)
                <citations> 
                { 
                    $e/attribute::* , 
                    for $p in $e/publication
                    return (: rename "publication" to "citation" :)
                        <citation> 
                            <url>{ $p/web-link/text() (: rename "web-link" to "url" :) }</url> 
                        </citation>
                }
                </citations>
            else $e (: leave everything else as is :)             
    }
    </study>
};



let $login := xmldb:login( "/" , "admin" , "" )

return 

    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get()
    
    else if ( request:get-method() = $CONSTANT:METHOD-POST ) 
    
    then local:do-post()
    
    else common-protocol:do-method-not-allowed( "/admin/example-migrate-studies.xql" , ( "GET" , "POST" ) )
    
    

