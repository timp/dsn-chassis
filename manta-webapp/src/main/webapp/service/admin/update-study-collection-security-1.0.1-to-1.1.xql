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

(: TODO: Change the study collection .descriptor so that DENY * user DELETE_MEMBER becomes DENY ROLE_CHASSIS_CONTRIBUTOR DELETE_MEMBER :)

(: TODO: Remove comments in the descriptor. :)

declare variable $testdata {
<atombeat:security-descriptor xmlns:atombeat="http://purl.org/atombeat/xmlns">
    <atombeat:acl><!--
                Contributors can list the collection and create members.
            -->
        <atombeat:ace>
            <atombeat:type>ALLOW</atombeat:type>
            <atombeat:recipient type="role">ROLE_CHASSIS_CONTRIBUTOR</atombeat:recipient>
            <atombeat:permission>LIST_COLLECTION</atombeat:permission>
        </atombeat:ace>
        <atombeat:ace>
            <atombeat:type>ALLOW</atombeat:type>
            <atombeat:recipient type="role">ROLE_CHASSIS_CONTRIBUTOR</atombeat:recipient>
            <atombeat:permission>CREATE_MEMBER</atombeat:permission>
        </atombeat:ace><!-- Because study administrators can UPDATE_MEMBER_ACL, make sure they can't
            give themselves permission to do things we don't want them to do -->
        <atombeat:ace>
            <atombeat:type>DENY</atombeat:type>
            <atombeat:recipient type="user">*</atombeat:recipient>
            <atombeat:permission>DELETE_MEMBER</atombeat:permission>
        </atombeat:ace><!--
                Personal data reviewers can list the collection and can retrieve any member or security descriptor.
            -->
        <atombeat:ace>
            <atombeat:type>ALLOW</atombeat:type>
            <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
            <atombeat:permission>LIST_COLLECTION</atombeat:permission>
        </atombeat:ace>
        <atombeat:ace>
            <atombeat:type>ALLOW</atombeat:type>
            <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
            <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
        </atombeat:ace>
        <atombeat:ace>
            <atombeat:type>ALLOW</atombeat:type>
            <atombeat:recipient type="role">ROLE_CHASSIS_PERSONAL_DATA_REVIEWER</atombeat:recipient>
            <atombeat:permission>RETRIEVE_MEMBER_ACL</atombeat:permission>
        </atombeat:ace><!--
                Curators can list the collection, and can retrieve any member, and can retrieve any security descriptor.
            -->
        <atombeat:ace>
            <atombeat:type>ALLOW</atombeat:type>
            <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
            <atombeat:permission>LIST_COLLECTION</atombeat:permission>
        </atombeat:ace>
        <atombeat:ace>
            <atombeat:type>ALLOW</atombeat:type>
            <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
            <atombeat:permission>RETRIEVE_MEMBER</atombeat:permission>
        </atombeat:ace>
        <atombeat:ace>
            <atombeat:type>ALLOW</atombeat:type>
            <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
            <atombeat:permission>RETRIEVE_HISTORY</atombeat:permission>
        </atombeat:ace>
        <atombeat:ace>
            <atombeat:type>ALLOW</atombeat:type>
            <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
            <atombeat:permission>RETRIEVE_REVISION</atombeat:permission>
        </atombeat:ace>
        <atombeat:ace>
            <atombeat:type>ALLOW</atombeat:type>
            <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
            <atombeat:permission>RETRIEVE_MEMBER_ACL</atombeat:permission>
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
    let $study-collection-securities := local:get-content()
    
    let $testing := request:get-parameter("testing", "no")
    
    return

        <html>
            <head>
                <title>Data Migration - Study Collection Security v1.0 to v1.0.1</title>
            </head>
            <body>
                <h1>Data Migration - Study Collection Security v1.0 to v1.0.1</h1>
                <p>This script should change the study collection ACL so that...</p>
                <ul>
                    <li>Curators should now be able to update any study (member) ACL in the collection.
                    </li>
                </ul>         
                
                <p><a href="../security/studies/">study collection security entry</a>
                </p>
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
                {$study-collection-securities}
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
        let $ret := xmldb:document( '/db/atombeat/security/db/atombeat/content/studies/.descriptor' )
        return $ret
    else
        let $ret := xmldb:document( 'test-study-collection-security/.descriptor' )
        return $ret
     
   return $content
};

declare function local:do-modifications($study-collection-securities-v1-0-1) as item()*
{
    let $new := local:modify-nodes($study-collection-securities-v1-0-1)

    let $content := local:get-content()

    return $content
};

declare function local:save-testdata($testdata)
{
    let $descriptor-doc-db-path := xmldb:store( 'test-study-collection-security' , ".descriptor" , $testdata , $CONSTANT:MEDIA-TYPE-XML )        
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
declare function local:modify-nodes($study-collection-securities-v1-0-1) as element( atombeat:security-descriptor )*
{
   let $study-collection-securities-v1-1 := 
        for $v1-0-1 in $study-collection-securities-v1-0-1
            
            return update insert <atombeat:ace><atombeat:type>ALLOW</atombeat:type><atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient><atombeat:permission>UPDATE_MEMBER_ACL</atombeat:permission></atombeat:ace> into $v1-0-1//atombeat:acl

    return $study-collection-securities-v1-1
    
};


declare function local:check-changes() as item() *{
  let $all := local:get-content()
 let $ret := for $m in $all 

       
         let $out2:= if (count($m//atombeat:ace[atombeat:recipient/@type = 'role' and atombeat:recipient = 'ROLE_CHASSIS_CURATOR' and atombeat:permission = 'UPDATE_MEMBER_ACL']) != 1) then 
                        let $msg2 := "Failed to add ALLOW ROLE_CHASSIS_CURATOR UPDATE_MEMBER_ACL "
                        return $msg2
                    else
                        let $msg2 := "Added ALLOW ROLE_CHASSIS_CURATOR UPDATE_MEMBER_ACL"
                        return $msg2

         return $out2
         
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

let $collection := xmldb:create-collection("xmldb:exist:///db", "test-study-collection-security"),
    $doc := local:save-testdata($testdata)
    return $collection
return 

    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get()
    
    else if ( request:get-method() = $CONSTANT:METHOD-POST ) 
    
    then local:do-migration()
    
    else common-protocol:do-method-not-allowed( "/admin/update-study-collection-security-1.0.1-to-1.1.xql" ,"/admin/update-study-collection-security-1.0.1-to-1.1.xql" , ( "GET" , "POST" ) )
    
    
