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

(: TODO: Remove comments in the descriptor. :)

declare variable $testdata {
<atombeat:security-descriptor xmlns:atombeat="http://purl.org/atombeat/xmlns">
    <atombeat:groups>
        <atombeat:group id="GROUP_ADMINISTRATORS">
            <atombeat:member>colin@example.org</atombeat:member>
        </atombeat:group>
    </atombeat:groups>
    <atombeat:acl>
        <atombeat:ace>
            <atombeat:type>ALLOW</atombeat:type>
            <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
            <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
        </atombeat:ace>
        <atombeat:ace>
            <atombeat:type>ALLOW</atombeat:type>
            <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
            <atombeat:permission>UPDATE_MEMBER</atombeat:permission>
        </atombeat:ace>
        <atombeat:ace>
            <atombeat:type>ALLOW</atombeat:type>
            <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
            <atombeat:permission>RETRIEVE_MEMBER_ACL</atombeat:permission>
        </atombeat:ace>
        <atombeat:ace>
            <atombeat:type>ALLOW</atombeat:type>
            <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
            <atombeat:permission>UPDATE_MEMBER_ACL</atombeat:permission>
        </atombeat:ace>
    </atombeat:acl>
</atombeat:security-descriptor>
};


declare function local:do-get() as item()*
{
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content('')
};



declare function local:content($content) as item()*
{
    let $study-member-securities := local:get-content()
    
    let $testing := request:get-parameter("testing", "no")
    
    return
    
        <html>
            <head>
                <title>Data Migration - Study Member Security v1.0.1 to v1.1</title>
            </head>
            <body>
                <h1>Data Migration - Study Member Security v1.0.1 to v1.1</h1>
                <p>This script should change the study member ACL so that...</p>
                <ul>
                    <li>Contributors can no longer update any study (member). This has the effect of locking everything against contributor edits (until unlocked again by a curator).
                    </li>
                
                </ul>
                <p>
                    <form method="post" action="">
                        Test <input type="checkbox" name="testing" checked="checked" />
                        <input type="submit" value="Update Security"></input>
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
                {$study-member-securities}
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
        let $ret := xmldb:xcollection( "/db/atombeat/security/db/atombeat/content/studies" )
        return $ret
    else
        let $ret := xmldb:xcollection( 'test-study-member-security' )/atombeat:security-descriptor (: not recursive :)
        return $ret
     
   return $content
};

declare function local:do-modifications($study-member-securities-v1-0-1) as item()*
{
    let $new := local:modify-nodes($study-member-securities-v1-0-1)
    
    let $content := local:get-content()

    return $content
};

declare function local:save-testdata($testdata)
{
        let $descriptor-doc-db-path := xmldb:store( 'test-study-member-security' , "TEST.atom.descriptor" , $testdata , $CONSTANT:MEDIA-TYPE-XML )
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
declare function local:modify-nodes($study-member-securities-v1-0-1) as item()*
{
   let $study-member-securities-v1-1 := 
        for $v1-0-1 in $study-member-securities-v1-0-1
            
            return update delete $v1-0-1//atombeat:ace[atombeat:recipient/@type = 'group' and atombeat:recipient = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'UPDATE_MEMBER']

    return $study-member-securities-v1-1
    
};


declare function local:check-changes() as item() *{
  let $all := local:get-content()
 let $ret := for $m in $all 

         let $out := if (count($m//atombeat:ace[atombeat:recipient/@type = 'group' and atombeat:recipient = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'UPDATE_MEMBER']) > 0) then 
                        let $msg := "Failed to delete //atombeat:ace[atombeat:recipient/@type = 'group' and atombeat:recipient = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'UPDATE_MEMBER']"
                        return $msg
                     else
                        let $msg := "Deleted //atombeat:ace[atombeat:recipient/@type = 'group' and atombeat:recipient = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'UPDATE_MEMBER']"
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

let $collection := xmldb:create-collection("xmldb:exist:///db", "test-study-member-security"),
    $doc := local:save-testdata($testdata)
    return $collection
return 

    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get()
    
    else if ( request:get-method() = $CONSTANT:METHOD-POST ) 
    
    then local:do-migration()
    
    else common-protocol:do-method-not-allowed( "/admin/update-study-member-security-1.0.1-to-1.1.xql" ,"/admin/update-study-member-security-1.0.1-to-1.1.xql" , ( "GET" , "POST" ) )
    
    
