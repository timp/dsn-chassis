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

declare variable $testdata {
<atom:feed xmlns:atom="http://www.w3.org/2005/Atom" xmlns:atombeat="http://purl.org/atombeat/xmlns" atombeat:enable-versioning="true" atombeat:exclude-entry-content="true" atombeat:recursive="false">
    <atom:id>http://localhost:8081/repository/service/content/drafts</atom:id>
    <atom:updated>2011-02-11T10:29:17.667Z</atom:updated>
    <atom:link rel="self" href="http://localhost:8081/repository/service/content/drafts" type="application/atom+xml;type=feed"/>
    <atom:link rel="edit" href="http://localhost:8081/repository/service/content/drafts" type="application/atom+xml;type=feed"/>
    <atom:author>
        <atom:email>adam@example.org</atom:email>
    </atom:author>
    <atom:title type="text">Drafts</atom:title>
    <app:collection xmlns:app="http://www.w3.org/2007/app" href="http://localhost:8081/repository/service/content/drafts">
        <atom:title type="text">Drafts</atom:title>
    </app:collection>
</atom:feed>
};


declare function local:do-get() as item()*
{
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content('')
};



declare function local:content($content) as item()*
{
    let $drafts-feed-config := local:get-content()

    let $testing := request:get-parameter("testing", "no")

    return
    
        <html>
            <head>
                <title>Data Migration - Drafts Feed Config v1.0.1 to v1.1</title>
            </head>
            <body>
                <h1>Data Migration - Drafts Feed Config v1.0.1 to v1.1</h1>
                <p>This script should change the Drafts Feed configs so that...</p>
                <ul>
                    <li>The atombeat:enable-tombstone attribute is present and set to true.
                    </li>
                    <li>The atombeat:config-tombstone elements are present and the ghost-atom-elements options set.
                    </li>
                </ul>
                <p>
                    <form method="post" action="">
                        Test <input type="checkbox" name="testing" checked="checked" />
                        <input type="submit" value="Update Configs"></input>
                    </form>
                    <form method="get" action="">
                        <input type="submit" value="Refresh"></input>
                    </form>
                </p>
                <p>Output:</p>
                <pre>
                {$content}
                </pre>
                <p>Content (Testing: {$testing}):</p>
                <textarea cols="120" rows="20" wrap="off">
                {$drafts-feed-config}
                </textarea>
                
                <p>Original test data:</p>
                <textarea cols="120" rows="20" wrap="off">
                {$testdata}
                </textarea>
            </body>
        </html>
};

declare function local:get-content() as item()* {
  let $testing := request:get-parameter("testing", "no")
    let $content := if ($testing = "no") then
        let $ret := xmldb:document( "/db/atombeat/content/drafts/.feed" )/atom:feed
        return $ret
    else
        let $ret := xmldb:xcollection( 'test-drafts-feed-config' )/atom:feed (: not recursive :)
        return $ret
     
   return $content
};

declare function local:do-modifications($drafts-feed-config-v1-0-1) as item()*
{
    let $new := local:modify-nodes($drafts-feed-config-v1-0-1)
     
    let $content := local:get-content()

    return $content
};

declare function local:save-testdata($testdata)
{
        let $descriptor-doc-db-path := xmldb:store( 'test-drafts-feed-config' , ".feed" , $testdata , $CONSTANT:MEDIA-TYPE-XML )
        return $descriptor-doc-db-path     
};

declare function local:do-post($content) as item()*
{
(: SEND RESPONSE :)        
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content($content)
};

(: update works directly against the database - not on in memory fragments so this doesn't behave as expected :( :)
declare function local:modify-nodes($drafts-feed-config-v1-0-1) as item()*
{
   let $drafts-feed-config-v1-1 := 
        for $v1-0-1 in $drafts-feed-config-v1-0-1
            
            
			let $ins := update insert <atombeat:config-tombstones><atombeat:config><atombeat:param name="ghost-atom-elements" value="id published updated author title link"/></atombeat:config></atombeat:config-tombstones> into $v1-0-1
			return update insert attribute atombeat:enable-tombstones {'true'} into $v1-0-1

    return $drafts-feed-config-v1-1
    
};


declare function local:check-changes() as item() *{
  let $all := local:get-content()
 let $ret := for $m in $all 
 
         let $out := if (count($m//atombeat:config-tombstones) != 1) then 
                        let $msg := "Failed to insert atombeat:config-tombstones into atom:feed"
                        return $msg
                     else
                        let $msg := "Inserted atombeat:config-tombstones into atom:feed"
                        return $msg

         let $out2:= if (count($m//atom:feed[@atombeat:enable-tombstones = 'true']) != 1) then 
                        let $msg2 := "Failed to insert @atombeat:enable-tombstone=true into atom:feed"
                        return $msg2
                    else
                        let $msg2 := "Inserted @atombeat:enable-tombstone=true into atom:feed"
                        return $msg2

         return concat($out, '&#xD;', $out2)
         
    return $ret
};
declare function local:do-migration() {
  
    let $all := local:get-content()

    let $new-content := local:do-modifications($all)

    let $testing := request:get-parameter("testing", "no")
      
    let $saved := if ($testing != "no") then 
        let $ret := local:check-changes()
        return $ret
      else
        let $ret := ''
        return $ret
        
    return local:do-post($saved)
};

let $login := xmldb:login( "/" , $config:exist-user , $config:exist-password )

let $testing := request:get-parameter("testing", "no")
    let $content := if ($testing = "no") then
        let $ret := ''
        return $ret
    else

let $collection := xmldb:create-collection("xmldb:exist:///db", "test-drafts-feed-config"),
    $doc := local:save-testdata($testdata)
    return $collection
return 

    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get()
    
    else if ( request:get-method() = $CONSTANT:METHOD-POST ) 
    
    then local:do-migration()
    
    else common-protocol:do-method-not-allowed( "/admin/update-drafts-feed-config-1.0.1-to-1.1.xql" ,"/admin/update-drafts-feed-config-1.0.1-to-1.1.xql" , ( "GET" , "POST" ) )
    
    
