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

(: TODO: Remove comments in the descriptors. :)

declare variable $testdata {
<atombeat:security-descriptor xmlns:atombeat="http://purl.org/atombeat/xmlns">
    <atombeat:groups>
        <atombeat:group id="GROUP_ADMINISTRATORS" src="http://localhost:8081/repository/service/content/studies/REZKY"/>
    </atombeat:groups>
    <atombeat:acl><!--
                        Curators can create media resources, list the collection, retrieve media resources, and retrieve and update members.
                -->
        <atombeat:ace>
            <atombeat:type>ALLOW</atombeat:type>
            <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
            <atombeat:permission>CREATE_MEDIA</atombeat:permission>
        </atombeat:ace>
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
            <atombeat:permission>UPDATE_MEMBER</atombeat:permission>
        </atombeat:ace>
        <atombeat:ace>
            <atombeat:type>ALLOW</atombeat:type>
            <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
            <atombeat:permission>RETRIEVE_MEDIA</atombeat:permission>
        </atombeat:ace><!-- N.B. by default, study administrators cannot see curated media -->
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
    let $curatedMedia-member-securities := local:get-content()

    let $testing := request:get-parameter("testing", "no")

    return
    
        <html>
            <head>
                <title>Data Migration - Curated Media Feed Security v1.1 to v1.2</title>
            </head>
            <body>
                <h1>Data Migration - Curated Media Feed Security v1.1 to v1.2</h1>
                <p>This script should change the curatedMedia ACL so that...</p>
                <ul>
                    <li>Study admins can list the collection
                    </li>
                    <li>Curators can retrieve and update the ACLs</li>
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
                {$curatedMedia-member-securities}
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
        let $ret := for $child-collection-name in xmldb:get-child-collections( "/db/atombeat/security/db/atombeat/content/media/curated/" )
                    return xmldb:document(concat("/db/atombeat/security/db/atombeat/content/media/curated/", $child-collection-name, "/.descriptor"))
        return $ret
    else
        let $ret := xmldb:xcollection( 'test-curatedMedia-member-security' )/atombeat:security-descriptor (: not recursive :)
        return $ret
     
   return $content
};

declare function local:do-modifications($curatedMedia-member-securities-old) as item()*
{
    let $new := local:modify-nodes($curatedMedia-member-securities-old)
     
    let $content := local:get-content()

    return $content
};

declare function local:save-testdata($testdata)
{
        let $descriptor-doc-db-path := xmldb:store( 'test-curatedMedia-member-security' , "TEST.atom.descriptor" , $testdata , $CONSTANT:MEDIA-TYPE-XML )
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
declare function local:modify-nodes($curatedMedia-member-securities-old) as item()*
{
   let $curatedMedia-member-securities-new := 
        for $old in $curatedMedia-member-securities-old    		
            let $get-acl := update insert <atombeat:ace><atombeat:type>ALLOW</atombeat:type><atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient><atombeat:permission>UPDATE_MEDIA_ACL</atombeat:permission></atombeat:ace> following $old//atombeat:ace[atombeat:recipient/@type = 'role' and atombeat:recipient = 'ROLE_CHASSIS_CURATOR' and atombeat:permission = 'LIST_COLLECTION']
            let $put-acl := update insert <atombeat:ace><atombeat:type>ALLOW</atombeat:type><atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient><atombeat:permission>RETRIEVE_MEDIA_ACL</atombeat:permission></atombeat:ace> following $old//atombeat:ace[atombeat:recipient/@type = 'role' and atombeat:recipient = 'ROLE_CHASSIS_CURATOR' and atombeat:permission = 'LIST_COLLECTION']
			return update insert <atombeat:ace><atombeat:type>ALLOW</atombeat:type><atombeat:recipient type="group">GROUP_ADMINISTRATORS</atombeat:recipient><atombeat:permission>LIST_COLLECTION</atombeat:permission></atombeat:ace> following $old//atombeat:ace[atombeat:recipient/@type = 'role' and atombeat:recipient = 'ROLE_CHASSIS_CURATOR' and atombeat:permission = 'LIST_COLLECTION']


    return $curatedMedia-member-securities-new
    
};


declare function local:check-changes() as item() *{
  let $all := local:get-content()
 let $ret := for $m in $all 
 
                        
         let $out := if (count($m//atombeat:ace[atombeat:type = 'ALLOW' and atombeat:recipient[@type = 'group']/text() = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'LIST_COLLECTION']) != 1) then 
                        let $msg := "Failed to insert //atombeat:ace[atombeat:recipient[@type = 'group']/text() = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'LIST_COLLECTION']"
                        return $msg
                     else
                        let $msg := "Inserted //atombeat:ace[atombeat:recipient/@type = 'role' and atombeat:recipient = 'GROUP_ADMINISTRATORS' and atombeat:permission = 'LIST_COLLECTION']"
                        return $msg 

         let $out1 := if (count($m//atombeat:ace[atombeat:type = 'ALLOW' and atombeat:recipient[@type = 'role']/text() = 'ROLE_CHASSIS_CURATOR' and atombeat:permission = 'UPDATE_MEDIA_ACL']) != 1) then 
                        let $msg := "Failed to insert //atombeat:ace[atombeat:recipient[@type = 'group']/text() = 'ROLE_CHASSIS_CURATOR' and atombeat:permission = 'UPDATE_MEDIA_ACL']"
                        return $msg
                     else
                        let $msg := "Inserted //atombeat:ace[atombeat:recipient/@type = 'role' and atombeat:recipient = 'ROLE_CHASSIS_CURATOR' and atombeat:permission = 'UPDATE_MEDIA_ACL']"
                        return $msg 
         let $out2 := if (count($m//atombeat:ace[atombeat:type = 'ALLOW' and atombeat:recipient[@type = 'role']/text() = 'ROLE_CHASSIS_CURATOR' and atombeat:permission = 'RETRIEVE_MEDIA_ACL']) != 1) then 
                        let $msg := "Failed to insert //atombeat:ace[atombeat:recipient[@type = 'role']/text() = 'ROLE_CHASSIS_CURATOR' and atombeat:permission = 'RETRIEVE_MEDIA_ACL']"
                        return $msg
                     else
                        let $msg := "Inserted //atombeat:ace[atombeat:recipient/@type = 'role' and atombeat:recipient = 'ROLE_CHASSIS_CURATOR' and atombeat:permission = 'RETRIEVE_MEDIA_ACL']"
                        return $msg 


         return concat($out,'&#xD;', $out1, '&#xD;',$out2)
         
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

let $collection := xmldb:create-collection("xmldb:exist:///db", "test-curatedMedia-member-security"),
    $doc := local:save-testdata($testdata)
    return $collection
return 

    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get()
    
    else if ( request:get-method() = $CONSTANT:METHOD-POST ) 
    
    then local:do-migration()
    
    else ()
    
    
