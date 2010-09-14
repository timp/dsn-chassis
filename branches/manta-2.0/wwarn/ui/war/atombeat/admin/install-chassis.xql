declare namespace atom = "http://www.w3.org/2005/Atom" ;

import module namespace response = "http://exist-db.org/xquery/response" ;

import module namespace config = "http://www.cggh.org/2010/atombeat/xquery/config" at "../config/shared.xqm" ;
import module namespace CONSTANT = "http://www.cggh.org/2010/atombeat/xquery/constants" at "../lib/constants.xqm" ;
import module namespace xutil = "http://www.cggh.org/2010/atombeat/xquery/xutil" at "../lib/xutil.xqm" ;
import module namespace atomsec = "http://www.cggh.org/2010/atombeat/xquery/atom-security" at "../lib/atom-security.xqm" ;
import module namespace atomdb = "http://www.cggh.org/2010/atombeat/xquery/atomdb" at "../lib/atomdb.xqm" ;
import module namespace ap = "http://www.cggh.org/2010/xquery/atom-protocol" at "../lib/atom-protocol.xqm" ;



declare variable $collection-spec :=
    <spec>
        <collection>
            <title>Studies</title>
            <path-info>/studies</path-info>
            <enable-history>true</enable-history>
        </collection>   
        <collection>
            <title>Media</title>
            <path-info>/media</path-info>
            <enable-history>false</enable-history>
        </collection>   
        <collection>
            <title>Submissions</title>
            <path-info>/submissions</path-info>
            <enable-history>false</enable-history>
        </collection>   
        <collection>
            <title>Reviews</title>
            <path-info>/reviews</path-info>
            <enable-history>false</enable-history>
        </collection>   
        <collection>
            <title>Derivations</title>
            <path-info>/derivations</path-info>
            <enable-history>false</enable-history>
        </collection>   
    </spec>
;


declare function local:do-get() as item()*
{
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content()
};



declare function local:content() as item()*
{
    <html>
        <head>
            <title>Chassis Installation</title>
            <style type="text/css">
                th {{
                    text-align: left;
                }}
            </style>
        </head>
        <body>
            <h1>Chassis Installation</h1>
            <h2>Atom Collections</h2>
                <table>
                    <tr>
                        <th>Title</th>
                        <th>Path</th>
                        <th>Available</th>
                    </tr>
                    {
                        for $collection in $collection-spec/collection
                        return
                            <tr>
                                <td>{$collection/title/text()}</td>
                                <td>{$collection/path-info/text()}</td>
                                <td>{atomdb:collection-available($collection/path-info/text())}</td>
                            </tr>
                    }
                </table>
                <form method="post" action="">
                    <p><input type="submit" value="Install"></input></p>
                </form>
                <form method="get" action="">
                    <p><input type="submit" value="Refresh"></input></p>
                </form>
        </body>
    </html>
};



declare function local:do-post() as item()*
{

    let $global-acl-installed := atomsec:store-global-acl( $config:default-global-acl )
    let $collections-installed :=
        for $collection in $collection-spec/collection
        let $title := $collection/title/text()
        let $path-info := $collection/path-info/text()
        let $enable-history := xs:boolean($collection/enable-history/text())
        return 
            if ( not( atomdb:collection-available( $path-info ) ) )
            then 
                let $feed-doc := 
                    <atom:feed>
                        <atom:title>{$title}</atom:title>
                    </atom:feed>
                let $collection-created := atomdb:create-collection( $path-info , $feed-doc )
                let $collection-db-path := atomdb:request-path-info-to-db-path( $path-info )
                let $history-enabled :=
                    if ( $enable-history )
                    then xutil:enable-versioning( $collection-db-path )
                    else false()
                return $collection-db-path
            else ()
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content()
};




let $login := xmldb:login( "/" , "admin" , "" )

return 

    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get()
    
    else if ( request:get-method() = $CONSTANT:METHOD-POST ) 
    
    then local:do-post()
    
    else ap:do-method-not-allowed( "/admin/install-chassis.xql" , ( "GET" , "POST" ) )
    
    

