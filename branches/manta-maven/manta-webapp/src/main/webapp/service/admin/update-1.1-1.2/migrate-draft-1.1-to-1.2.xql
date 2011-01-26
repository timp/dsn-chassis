declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;
import module namespace response = "http://exist-db.org/xquery/response" ;

import module namespace config = "http://purl.org/atombeat/xquery/config" at "../../config/shared.xqm" ;
import module namespace CONSTANT = "http://purl.org/atombeat/xquery/constants" at "../../lib/constants.xqm" ;
import module namespace atomdb = "http://purl.org/atombeat/xquery/atomdb" at "../../lib/atomdb.xqm" ;
import module namespace common-protocol = "http://purl.org/atombeat/xquery/common-protocol" at "../../lib/common-protocol.xqm" ;
import module namespace config-collections = "http://purl.org/atombeat/xquery/config-collections" at "../collections.xqm" ;

(: Migration actions: :)
(: Adds "person-is-contactable" element to the "person" element of "acknowledgements", after "institution" in drafts. :)

declare variable $test-draft-entry {
<atom:entry xmlns:atom="http://www.w3.org/2005/Atom">
    <atom:id>http://localhost:8081/repository/service/content/drafts/3d8985b5-b240-4c0a-9301-6fa3e21bf027</atom:id>
    <atom:published>2011-01-11T13:44:42.823Z</atom:published>
    <atom:updated>2011-01-11T13:44:42.823Z</atom:updated>
    <atom:link rel="self" type="application/atom+xml;type=entry" href="http://localhost:8081/repository/service/content/drafts/3d8985b5-b240-4c0a-9301-6fa3e21bf027"/>
    <atom:link rel="edit" type="application/atom+xml;type=entry" href="http://localhost:8081/repository/service/content/drafts/3d8985b5-b240-4c0a-9301-6fa3e21bf027"/>
    <atom:author>
        <atom:email>colin@example.org</atom:email>
    </atom:author>
    <atom:title type="text">study-status test 2</atom:title>
    <atom:content type="application/vnd.chassis-manta+xml">
        <draft>
            <wizard-pane-to-show>permissions</wizard-pane-to-show>
            <registrant-has-agreed-to-the-terms>yes</registrant-has-agreed-to-the-terms>
            <study-entry-container>
                <atom:entry>
                    <atom:title type="text">study-status test 2</atom:title>
                    <atom:content type="application/vnd.chassis-manta+xml">
                        <study profile="http://www.cggh.org/2010/chassis/manta/1.1">
                            <study-is-published/>
                            <publications/>
                            <acknowledgements>
                                <person>
                                    <first-name/>
                                    <middle-name/>
                                    <family-name/>
                                    <email-address>colin@example.org</email-address>
                                    <institution/>
                                    <person-is-contactable/>
                                </person>
                            </acknowledgements>
                            <curator-notes/>
                            <study-status>new</study-status>
                        </study>
                    </atom:content>
                </atom:entry>
            </study-entry-container>
            <study-permissions-entry-container>
                <atom:entry>
                    <atom:content type="application/vnd.atombeat+xml">
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
                                <atombeat:ace>
                                    <atombeat:type>ALLOW</atombeat:type>
                                    <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                                    <atombeat:permission>UPDATE_MEMBER</atombeat:permission>
                                </atombeat:ace>
                                <atombeat:ace>
                                    <atombeat:type>ALLOW</atombeat:type>
                                    <atombeat:recipient type="role">ROLE_CHASSIS_CURATOR</atombeat:recipient>
                                    <atombeat:permission>DELETE_MEMBER</atombeat:permission>
                                </atombeat:ace>
                            </atombeat:acl>
                        </atombeat:security-descriptor>
                    </atom:content>
                </atom:entry>
            </study-permissions-entry-container>
        </draft>
    </atom:content>
    <ar:comment xmlns:ar="http://purl.org/atompub/revision/1.0">
        <atom:author>
            <atom:email>colin@example.org</atom:email>
        </atom:author>
        <atom:updated>2011-01-11T13:44:42.823Z</atom:updated>
        <atom:summary>initial revision</atom:summary>
    </ar:comment>
</atom:entry>
};



declare function local:do-get() as item()*
{
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content('')
};



declare function local:content($content) as item()*
{

    let $drafts := local:get-content("drafts")
    let $old-drafts := local:get-old-versioned-content-drafts($drafts) 
    let $new-drafts := local:get-new-versioned-content-drafts($drafts)    
    
    return
    
        <html>
            <head>
                <title>Data Migration - Draft v1.1 to v1.2</title>
            </head>
            <body>
                <h1>Data Migration - Draft v1.1 to v1.2</h1>
  
                <p>This script will:</p>
                
                <ul>
                    <li>Modify the study-permissions-entry-container</li>
                </ul>
  
                <p>Total number of entries in <a href="../content/drafts">Draft</a> collection: <strong>{ count( $drafts ) }</strong></p>
                <p>Number of entries in <a href="../content/drafts">Draft</a> collection using v1.1 profile: <strong>{ count( $old-drafts ) }</strong></p>
                <p>Number of entries in <a href="../content/drafts">Draft</a> collection using v1.2 profile: <strong>{ count( $new-drafts ) }</strong></p>                
                
                <p>
                    <form method="post" action="">
                        Test <input type="checkbox" name="testing" checked="checked" />
                        <input type="submit" value="Migrate Data"></input>
                    </form>
                    <form method="get" action="">
                        <input type="submit" value="Refresh"></input>
                    </form>
                </p>
                <pre>
                {$content}
                </pre>
            </body>
        </html>
};

declare function local:get-content($collection) as element( atom:entry )* {
  let $testing := request:get-parameter("testing", "no")
    let $content := if ($testing = "no") then
        let $ret := atomdb:retrieve-members( concat("/", $collection) , false() )
        return $ret
    else
        let $ret := xmldb:xcollection( concat('test-', $collection) )/atom:entry (: not recursive :)
        return $ret
     
   return $content
};


declare function local:get-new-versioned-content-drafts($drafts) as element( atom:entry )* {
    
    let $ret := $drafts[atom:content/draft/study-entry-container/atom:entry/atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/1.2']
    return $ret
};

declare function local:get-old-versioned-content-drafts($drafts) as element( atom:entry )* {
    
    let $ret := $drafts[atom:content/draft/study-entry-container/atom:entry/atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/1.1']
    return $ret
};



declare function local:do-modifications-drafts($old-drafts) as element( atom:entry )*
{
    let $new := local:modify-nodes($old-drafts)
     
    let $ret := local:save-changes("drafts", $new)
      
    (: Need to get the updated data from the db :)
    let $all := local:get-content("drafts")
    let $content := local:get-old-versioned-content-drafts($all)

    
    return $content
};

declare function local:save-changes($collection-name, $new-collection)
{
let $testing := request:get-parameter("testing", "no")
    let $content := if ($testing = "no") then
      let $migrated :=     
        for $new-item in $new-collection 
        let $path-info := atomdb:edit-path-info( $new-item )
        return atomdb:update-member( $path-info , $new-item )
       return $migrated
    else 
    for $entry in $new-collection 
        let $member-path-info := $entry/atom:link[@rel='edit']/@href
        let $groups := text:groups( $member-path-info , "^(.*)/([^/]+)$" )
		let $collection-path-info := $groups[2]
		let $entry-resource-name := $groups[3]
        let $entry-doc-db-path := xmldb:store( concat('test-', $collection-name) , $entry-resource-name , $entry , $CONSTANT:MEDIA-TYPE-ATOM )
        return $entry-doc-db-path        
    return $content
};

declare function local:do-post($content) as item()*
{
(: SEND RESPONSE :)        
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content($content)
};

(: update applies directly to the database, not upon in-memory fragments :)
declare function local:modify-nodes($old-collection) as element( atom:entry )*
{

   let $new-collection := 
       for $old-item in $old-collection
       let $rep := update replace $old-item//study/@profile with "http://www.cggh.org/2010/chassis/manta/1.2"
       let $del := update delete $old-item//atombeat:acl
       let $mov := update insert $old-item//atombeat:security-descriptor/atombeat:groups following $old-item//atombeat:security-descriptor
       return update delete $old-item//atombeat:security-descriptor
       
    return $new-collection

    
};


declare function local:check-changes-drafts() as item() *{
  let $all-drafts := local:get-content("drafts")
 let $new-drafts :=  local:get-new-versioned-content-drafts($all-drafts)
 let $ret := for $draft in $new-drafts 

         let $out := if (count($draft//study-entry-container//study//curator-notes) = 1) then 
             let $msg := "Added curator-notes to study"
             return $msg
          else
             let $msg := "Failed to add curator-notes to draft"
             return $msg
             
         let $out2 := if (count($draft//study-entry-container//study//study-status[text() = 'new']) = 1) then 
             let $msg := "Added study-status 'new' to study"
             return $msg
          else
             let $msg := "Failed to add study-status 'new' to draft"
             return $msg
             
             
         return concat($out, '&#xD;', $out2)         
         
    return $ret
};

declare function local:do-migration($collection-name) {
  
    let $collection := local:get-old-versioned-content-drafts(local:get-content($collection-name))
    let $new-collection := local:do-modifications-drafts($collection)
    let $ret := local:save-changes($collection-name, $new-collection)

    let $testing := request:get-parameter("testing", "no")
      
    let $saved := if ($testing != "no") then 
    
        if ($collection-name = "drafts") then
            let $ret2 := local:check-changes-drafts()
            return $ret2
        else
            let $ret2 := ''
            return $ret2
      else
        let $ret2 := ''
        return $ret2
        
    return local:do-post($saved)
};

let $login := xmldb:login( "/" , $config:exist-user , $config:exist-password )

let $testing := request:get-parameter("testing", "no")

let $content := if ($testing = "no") then
        let $ret := ''
        return $ret
    else

        let $test-drafts-collection := xmldb:create-collection("xmldb:exist:///db", "test-drafts"), 
            $doc2 := local:save-changes("drafts", $test-draft-entry)
        return $test-drafts-collection

    
return 

    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get()
    
    else if ( request:get-method() = $CONSTANT:METHOD-POST ) 
    
    then 
        
        let $var2 := local:do-migration("drafts")
        return $var2
        
    else common-protocol:do-method-not-allowed( "/admin/update-1.1-1.2/migrate-draft-1.1-to-1.2.xql" , "/admin/update-1.1-1.2/migrate-draft-1.1-to-1.2.xql" , ( "GET" , "POST" ) )
    
    
