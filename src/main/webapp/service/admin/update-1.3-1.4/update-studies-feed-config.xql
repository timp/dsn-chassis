declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;

import module namespace response = "http://exist-db.org/xquery/response" ;

import module namespace config = "http://purl.org/atombeat/xquery/config" at "../../config/shared.xqm" ;
import module namespace CONSTANT = "http://purl.org/atombeat/xquery/constants" at "../../lib/constants.xqm" ;
import module namespace xutil = "http://purl.org/atombeat/xquery/xutil" at "../../lib/xutil.xqm" ;
import module namespace atomsec = "http://purl.org/atombeat/xquery/atom-security" at "../../lib/atom-security.xqm" ;
import module namespace atomdb = "http://purl.org/atombeat/xquery/atomdb" at "../../lib/atomdb.xqm" ;
import module namespace ap = "http://purl.org/atombeat/xquery/atom-protocol" at "../../lib/atom-protocol.xqm" ;
import module namespace common-protocol = "http://purl.org/atombeat/xquery/common-protocol" at "../../lib/common-protocol.xqm" ;
import module namespace config-collections = "http://purl.org/atombeat/xquery/config-collections" at "../collections.xqm" ;


declare function local:do-get() as item()*
{
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content('')
};



declare function local:content($content) as item()*
{
    let $studies-feed-config := local:get-content()

    let $testing := request:get-parameter("testing", "no")

    return
    
        <html>
            <head>
                <title>Data Migration - Studies Feed Config</title>
            </head>
            <body>
                <h1>Data Migration - Studies Feed Config</h1>
                <p>This script:</p>
                <ul>
                    <li>Deletes the config-link-expansion element, which expands the group link in study feed entries. Instead, the group info is copied on-the-fly to the study entry (this improves performance by avoiding link expansion).
                    </li>                    
                </ul>
                
                <p>Note: This script has no test mode.</p>
                
                <p>
                    <form method="post" action="">
                        <input type="submit" value="Update Config"></input>
                    </form>
                    <form method="get" action="">
                        <input type="submit" value="Refresh"></input>
                    </form>
                </p>
                <p>Content:</p>
                <textarea cols="120" rows="20" wrap="off">
                {$studies-feed-config}
                </textarea>

            </body>
        </html>
};

declare function local:get-content() as item()* {

    let $content := xmldb:document( "/db/atombeat/content/studies/.feed" )/atom:feed
	return $content
};

declare function local:do-modifications($studies-feed-config-v1-0-1) as item()*
{
    let $new := local:modify-nodes($studies-feed-config-v1-0-1)
     
    let $content := local:get-content()

    return $content
};



declare function local:do-post($content) as item()*
{
(: SEND RESPONSE :)        
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content($content)
};

(: update works directly against the database - not on in memory fragments so this doesn't behave as expected :( :)
declare function local:modify-nodes($studies-feed-config-v1-0-1) as item()*
{
   let $studies-feed-config-v1-1 := 
        for $v1-0-1 in $studies-feed-config-v1-0-1
            
			return update delete $v1-0-1/atombeat:config-link-expansion 
			

    return $studies-feed-config-v1-1
    
};



declare function local:do-migration() {
  
    let $all := local:get-content()

    let $new-content := local:do-modifications($all)

    let $saved := ''
        
    return local:do-post($saved)
};

let $login := xmldb:login( "/" , $config:exist-user , $config:exist-password )

let $content := ''

return 

    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get()
    
    else if ( request:get-method() = $CONSTANT:METHOD-POST ) 
    
    then local:do-migration()
    
    else ()
    
    
