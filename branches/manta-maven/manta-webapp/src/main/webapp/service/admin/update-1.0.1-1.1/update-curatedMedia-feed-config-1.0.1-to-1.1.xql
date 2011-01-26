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

declare variable $testdata {
<atom:feed xmlns:atom="http://www.w3.org/2005/Atom">
    <atom:id>http://localhost:8081/repository/service/content/media/curated/XYNCE</atom:id>
    <atom:updated>2011-01-10T10:17:34.576Z</atom:updated>
    <atom:link rel="self" href="http://localhost:8081/repository/service/content/media/curated/XYNCE" type="application/atom+xml;type=feed"/>
    <atom:link rel="edit" href="http://localhost:8081/repository/service/content/media/curated/XYNCE" type="application/atom+xml;type=feed"/>
    <atom:author>
        <atom:email>colin@example.org</atom:email>
    </atom:author>
    <atom:title type="text">Curated Media for Study XYNCE</atom:title>
    <atom:link rel="http://www.cggh.org/2010/chassis/terms/originStudy" href="http://localhost:8081/repository/service/content/studies/XYNCE" type="application/atom+xml;type=entry"/>
    <atombeat:config-link-extensions xmlns:atombeat="http://purl.org/atombeat/xmlns">
        <atombeat:extension-attribute name="allow" namespace="http://purl.org/atombeat/xmlns">
            <atombeat:config context="entry-in-feed">
                <atombeat:param name="match-rels" value="edit-media"/>
            </atombeat:config>
        </atombeat:extension-attribute>
    </atombeat:config-link-extensions>
    <app:collection xmlns:app="http://www.w3.org/2007/app" href="http://localhost:8081/repository/service/content/media/curated/XYNCE">
        <atom:title type="text">Curated Media for Study XYNCE</atom:title>
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
    let $curatedMedia-feed-configs := local:get-content()

    let $testing := request:get-parameter("testing", "no")

    return
    
        <html>
            <head>
                <title>Data Migration - Curated Media Feed Config v1.0.1 to v1.1</title>
            </head>
            <body>
                <h1>Data Migration - Curated Media Feed Config v1.0.1 to v1.1</h1>
                <p>This script should change the curatedMedia feed configs so that...</p>
                <ul>
                    <li>Security descriptor links have the atombeat:allow link extension attribute
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
                {$curatedMedia-feed-configs}
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
        let $ret := for $child-collection-name in xmldb:get-child-collections( "/db/atombeat/content/media/curated/" )
                    return xmldb:document(concat("/db/atombeat/content/media/curated/", $child-collection-name, "/.feed"))
        return $ret
    else
        let $ret := xmldb:xcollection( 'test-curatedMedia-feed-config' )/atom:feed (: not recursive :)
        return $ret
     
   return $content
};

declare function local:do-modifications($curatedMedia-feed-configs-v1-0-1) as item()*
{
    let $new := local:modify-nodes($curatedMedia-feed-configs-v1-0-1)
     
    let $content := local:get-content()

    return $content
};

declare function local:save-testdata($testdata)
{
        let $descriptor-doc-db-path := xmldb:store( 'test-curatedMedia-feed-config' , ".feed" , $testdata , $CONSTANT:MEDIA-TYPE-XML )
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
declare function local:modify-nodes($curatedMedia-feed-configs-v1-0-1) as item()*
{
   let $curatedMedia-feed-configs-v1-1 := 
        for $v1-0-1 in $curatedMedia-feed-configs-v1-0-1

			return update insert <atombeat:config context="feed"><atombeat:param name="match-rels" value="http://purl.org/atombeat/rel/security-descriptor"/></atombeat:config> following $v1-0-1//atombeat:config[@context = 'entry-in-feed' and atombeat:param/@name = 'match-rels' and atombeat:param/@value = 'edit-media']

    return $curatedMedia-feed-configs-v1-1
    
};


declare function local:check-changes() as item() *{
  let $all := local:get-content()
 let $ret := for $m in $all 
 
         let $out := if (count($m//atombeat:config[@context = 'feed' and atombeat:param/@name = 'match-rels' and atombeat:param/@value = 'http://purl.org/atombeat/rel/security-descriptor']) != 1) then 
                        let $msg := "Failed to insert //atombeat:config[@context = 'feed' and atombeat:param/@name = 'match-rels' and atombeat:param/@value = 'http://purl.org/atombeat/rel/security-descriptor']"
                        return $msg
                     else
                        let $msg := "Inserted //atombeat:config[@context = 'feed' and atombeat:param/@name = 'match-rels' and atombeat:param/@value = 'http://purl.org/atombeat/rel/security-descriptor']"
                        return $msg
                        
         return $out
         
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

let $collection := xmldb:create-collection("xmldb:exist:///db", "test-curatedMedia-feed-config"),
    $doc := local:save-testdata($testdata)
    return $collection
return 

    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get()
    
    else if ( request:get-method() = $CONSTANT:METHOD-POST ) 
    
    then local:do-migration()
    
    else common-protocol:do-method-not-allowed( "/admin/update-curatedMedia-feed-config-1.0.1-to-1.1.xql" ,"/admin/update-curatedMedia-feed-config-1.0.1-to-1.1.xql" , ( "GET" , "POST" ) )
    
    
