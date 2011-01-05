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

(: TODO: Remove comments in the descriptors. :)

declare variable $testdata {
<atombeat:security-descriptor xmlns:atombeat="http://purl.org/atombeat/xmlns">
    <atombeat:groups>
        <atombeat:group id="GROUP_ADMINISTRATORS" src="http://localhost:8080/repository/service/content/studies/GPKHY"/>
    </atombeat:groups>
    <atombeat:acl><!--
                                The study's administrators can create media resources in the study's submitted media collection, and can list the collection and retrieve and update members (but cannot update media ever, and cannot retrieve media until allowed by personal data reviewer).
                        -->
        <atombeat:ace>
            <atombeat:type>ALLOW</atombeat:type>
            <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
            <atombeat:permission>LIST_COLLECTION</atombeat:permission>
        </atombeat:ace>
        <atombeat:ace>
            <atombeat:type>ALLOW</atombeat:type>
            <atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient>
            <atombeat:permission>CREATE_MEDIA</atombeat:permission>
        </atombeat:ace>
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
            <atombeat:permission>MULTI_CREATE</atombeat:permission>
        </atombeat:ace><!--
                                Curators can list the collection, and can retrieve any member.
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
            <atombeat:permission>DELETE_MEDIA</atombeat:permission>
        </atombeat:ace>
        <!--
                                Personal data reviewers can list the collection, and can retrieve any member or media resource. They can also modify ACLs.
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
            <atombeat:permission>RETRIEVE_MEDIA</atombeat:permission>
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
    let $submittedMedia-member-securities := local:get-content()

    let $testing := request:get-parameter("testing", "no")

    return
    
        <html>
            <head>
                <title>Data Migration - Submitted Media Member Security v1.0.1 to v1.1</title>
            </head>
            <body>
                <h1>Data Migration - Submitted Media Member Security v1.0.1 to v1.1</h1>
                <p>This script should change the submittedMedia ACL so that...</p>
                <ul>
                    <li>Contributors cannot add submittedMedia to any study.
                    </li>
                    <li>Contributors cannot update submittedMedia in any study.
                    </li>
                    <li>Curators can retrieve and update the submittedMedia ACL in any study.
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
                {$submittedMedia-member-securities}
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
        let $ret := for $child-collection-name in xmldb:get-child-collections( "/db/atombeat/security/db/atombeat/content/media/submitted/" )
                    return xmldb:document(concat("/db/atombeat/security/db/atombeat/content/media/submitted/", $child-collection-name, "/.descriptor"))
        return $ret
    else
        let $ret := xmldb:xcollection( 'test-submittedMedia-member-security' )/atombeat:security-descriptor (: not recursive :)
        return $ret
     
   return $content
};

declare function local:do-modifications($submittedMedia-member-securities-v1-0-1) as item()*
{
    let $new := local:modify-nodes($submittedMedia-member-securities-v1-0-1)
     
    let $content := local:get-content()

    return $content
};

declare function local:save-testdata($testdata)
{
        let $descriptor-doc-db-path := xmldb:store( 'test-submittedMedia-member-security' , "TEST.atom.descriptor" , $testdata , $CONSTANT:MEDIA-TYPE-XML )
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
declare function local:modify-nodes($submittedMedia-member-securities-v1-0-1) as item()*
{
   let $submittedMedia-member-securities-v1-1 := 
        for $v1-0-1 in $submittedMedia-member-securities-v1-0-1
        
            let $del := update delete $v1-0-1//atombeat:ace[atombeat:recipient/@type = 'group' and atombeat:recipient = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'MULTI_CREATE']
            let $del2 := update delete $v1-0-1//atombeat:ace[atombeat:recipient/@type = 'group' and atombeat:recipient = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'CREATE_MEMBER']
            let $del3 := update delete $v1-0-1//atombeat:ace[atombeat:recipient/@type = 'group' and atombeat:recipient = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'UPDATE_MEMBER']   
                     
			let $del4 := update delete $v1-0-1//atombeat:ace[atombeat:recipient/@type = 'group' and atombeat:recipient = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'CREATE_MEDIA']
			
			let $ins := update insert <atombeat:ace><atombeat:type>ALLOW</atombeat:type><atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient><atombeat:permission>RETRIEVE_COLLECTION_ACL</atombeat:permission></atombeat:ace> following $v1-0-1//atombeat:ace[atombeat:recipient/@type = 'role' and atombeat:recipient = 'ROLE_CHASSIS_CURATOR' and atombeat:permission = 'DELETE_MEDIA']
			
			return update insert <atombeat:ace><atombeat:type>ALLOW</atombeat:type><atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient><atombeat:permission>UPDATE_COLLECTION_ACL</atombeat:permission></atombeat:ace> following $v1-0-1//atombeat:ace[atombeat:recipient/@type = 'role' and atombeat:recipient = 'ROLE_CHASSIS_CURATOR' and atombeat:permission = 'RETRIEVE_COLLECTION_ACL']


    return $submittedMedia-member-securities-v1-1
    
};


declare function local:check-changes() as item() *{
  let $all := local:get-content()
 let $ret := for $m in $all 
 
         let $out := if (count($m//atombeat:ace[atombeat:recipient/@type = 'group' and atombeat:recipient = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'MULTI_CREATE']) > 0) then 
                        let $msg := "Failed to delete //atombeat:ace[atombeat:recipient/@type = 'group' and atombeat:recipient = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'MULTI_CREATE']"
                        return $msg
                     else
                        let $msg := "Deleted //atombeat:ace[atombeat:recipient/@type = 'group' and atombeat:recipient = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'MULTI_CREATE']"
                        return $msg
                        
         let $out2 := if (count($m//atombeat:ace[atombeat:recipient/@type = 'group' and atombeat:recipient = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'CREATE_MEMBER']) > 0) then 
                        let $msg := "Failed to delete //atombeat:ace[atombeat:recipient/@type = 'group' and atombeat:recipient = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'CREATE_MEMBER']"
                        return $msg
                     else
                        let $msg := "Deleted //atombeat:ace[atombeat:recipient/@type = 'group' and atombeat:recipient = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'CREATE_MEMBER']"
                        return $msg
                        
         let $out3 := if (count($m//atombeat:ace[atombeat:recipient/@type = 'group' and atombeat:recipient = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'UPDATE_MEMBER']) > 0) then 
                        let $msg := "Failed to delete //atombeat:ace[atombeat:recipient/@type = 'group' and atombeat:recipient = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'UPDATE_MEMBER']"
                        return $msg
                     else
                        let $msg := "Deleted //atombeat:ace[atombeat:recipient/@type = 'group' and atombeat:recipient = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'UPDATE_MEMBER']"
                        return $msg
                        
         let $out4 := if (count($m//atombeat:ace[atombeat:recipient/@type = 'group' and atombeat:recipient = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'CREATE_MEDIA']) > 0) then 
                        let $msg := "Failed to delete //atombeat:ace[atombeat:recipient/@type = 'group' and atombeat:recipient = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'CREATE_MEDIA']"
                        return $msg
                     else
                        let $msg := "Deleted //atombeat:ace[atombeat:recipient/@type = 'group' and atombeat:recipient = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'CREATE_MEDIA']"
                        return $msg   


         let $out5 := if (count($m//atombeat:ace[atombeat:recipient/@type = 'role' and atombeat:recipient = 'ROLE_CHASSIS_CURATOR' and atombeat:permission = 'RETRIEVE_COLLECTION_ACL']) != 1) then 
                        let $msg := "Failed to insert //atombeat:ace[atombeat:recipient/@type = 'role' and atombeat:recipient = 'ROLE_CHASSIS_CURATOR' and atombeat:permission = 'RETRIEVE_COLLECTION_ACL']"
                        return $msg
                     else
                        let $msg := "Inserted //atombeat:ace[atombeat:recipient/@type = 'role' and atombeat:recipient = 'ROLE_CHASSIS_CURATOR' and atombeat:permission = 'RETRIEVE_COLLECTION_ACL']"
                        return $msg
                        
         let $out6 := if (count($m//atombeat:ace[atombeat:recipient/@type = 'role' and atombeat:recipient = 'ROLE_CHASSIS_CURATOR' and atombeat:permission = 'UPDATE_COLLECTION_ACL']) != 1) then 
                        let $msg := "Failed to insert //atombeat:ace[atombeat:recipient/@type = 'role' and atombeat:recipient = 'ROLE_CHASSIS_CURATOR' and atombeat:permission = 'UPDATE_COLLECTION_ACL']"
                        return $msg
                     else
                        let $msg := "Inserted //atombeat:ace[atombeat:recipient/@type = 'role' and atombeat:recipient = 'ROLE_CHASSIS_CURATOR' and atombeat:permission = 'UPDATE_COLLECTION_ACL']"
                        return $msg 

                        
         return concat($out, '&#xD;', $out2, '&#xD;', $out3, '&#xD;', $out4, '&#xD;', $out5, '&#xD;', $out6)
         
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

let $collection := xmldb:create-collection("xmldb:exist:///db", "test-submittedMedia-member-security"),
    $doc := local:save-testdata($testdata)
    return $collection
return 

    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get()
    
    else if ( request:get-method() = $CONSTANT:METHOD-POST ) 
    
    then local:do-migration()
    
    else common-protocol:do-method-not-allowed( "/admin/update-submittedMedia-member-security-1.0.1-to-1.1.xql" ,"/admin/update-submittedMedia-member-security-1.0.1-to-1.1.xql" , ( "GET" , "POST" ) )
    
    
