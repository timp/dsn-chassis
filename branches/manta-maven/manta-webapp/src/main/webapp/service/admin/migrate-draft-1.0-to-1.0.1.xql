declare namespace atom = "http://www.w3.org/2005/Atom" ;

import module namespace response = "http://exist-db.org/xquery/response" ;

import module namespace config = "http://purl.org/atombeat/xquery/config" at "../config/shared.xqm" ;
import module namespace CONSTANT = "http://purl.org/atombeat/xquery/constants" at "../lib/constants.xqm" ;
import module namespace atomdb = "http://purl.org/atombeat/xquery/atomdb" at "../lib/atomdb.xqm" ;
import module namespace common-protocol = "http://purl.org/atombeat/xquery/common-protocol" at "../lib/common-protocol.xqm" ;
import module namespace config-collections = "http://purl.org/atombeat/xquery/config-collections" at "collections.xqm" ;

(: Migration actions: :)
(: Adds "person-is-contactable" element to the "person" element of "acknowledgements", after "institution" in drafts. :)

declare variable $test-draft-entry {
<atom:entry xmlns:atom="http://www.w3.org/2005/Atom">
    <atom:id>http://localhost:8080/manta/atombeat/content/drafts/9ee0737e-18a6-4d32-9dc3-2f9b3aa80e15.atom</atom:id>
    <atom:published>2010-10-21T09:51:45.284+01:00</atom:published>
    <atom:updated>2010-10-21T10:57:52.856+01:00</atom:updated>
    <atom:link rel="self" type="application/atom+xml;type=entry" href="http://localhost:8080/manta/atombeat/content/drafts/9ee0737e-18a6-4d32-9dc3-2f9b3aa80e15.atom"/>
    <atom:link rel="edit" type="application/atom+xml;type=entry" href="http://localhost:8080/manta/atombeat/content/drafts/9ee0737e-18a6-4d32-9dc3-2f9b3aa80e15.atom"/>
    <atom:author>
        <atom:email>colin@example.org</atom:email>
    </atom:author>
    <atom:title type="text">Make me a v1.0 that needs migrating</atom:title>
    <atom:content type="application/vnd.chassis-manta+xml">
        <draft>
            <wizard-pane-to-show>acknowledgements</wizard-pane-to-show>
            <registrant-has-agreed-to-the-terms>yes</registrant-has-agreed-to-the-terms>
            <study-entry-container>
                <atom:entry>
                    <atom:title type="text">I am a v1.0 that needs migrating</atom:title>
                    <atom:content type="application/vnd.chassis-manta+xml">
                        <study profile="http://www.cggh.org/2010/chassis/manta/1.0">
                            <study-is-published/>
                            <publications/>
                            <acknowledgements>
                                <person>
                                    <first-name/>
                                    <middle-name/>
                                    <family-name/>
                                    <email-address>colin@example.org</email-address>
                                    <institution>Shouldn't have a contactable to start with</institution>
                                </person>
                            </acknowledgements>
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
        <atom:updated>2010-10-21T10:57:52.856+01:00</atom:updated>
        <atom:summary/>
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
    let $drafts-v1-0 := local:get-old-versioned-content-drafts($drafts) 
    let $drafts-v1-0-1 := local:get-new-versioned-content-drafts($drafts)    
    
    return
    
        <html>
            <head>
                <title>Data Migration - Draft v1.0 to v1.0.1</title>
            </head>
            <body>
                <h1>Data Migration - Draft v1.0 to v1.0.1</h1>
  
                <p>Total number of entries in <a href="../content/drafts">Draft</a> collection: <strong>{ count( $drafts ) }</strong></p>
                <p>Number of entries in <a href="../content/drafts">Draft</a> collection using v1.0 profile: <strong>{ count( $drafts-v1-0 ) }</strong></p>
                <p>Number of entries in <a href="../content/drafts">Draft</a> collection using v1.0.1 profile: <strong>{ count( $drafts-v1-0-1 ) }</strong></p>                
                
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
    
    let $ret := $drafts[atom:content/draft/study-entry-container/atom:entry/atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/1.0.1']
    return $ret
};

declare function local:get-old-versioned-content-drafts($drafts) as element( atom:entry )* {
    
    let $ret := $drafts[atom:content/draft/study-entry-container/atom:entry/atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/1.0']
    return $ret
};



declare function local:do-modifications-drafts($drafts-v1-0) as element( atom:entry )*
{
    let $new := local:modify-nodes($drafts-v1-0)
     
    let $ret := local:save-changes("drafts", $new)
      
    (: Need to get the updated data from the db :)
    let $all := local:get-content("drafts")
    let $content := local:get-old-versioned-content-drafts($all)

    
    return $content
};

declare function local:save-changes($collection-name, $collection-v1-0-1)
{
let $testing := request:get-parameter("testing", "no")
    let $content := if ($testing = "no") then
      let $migrated :=     
        for $v1-0-1 in $collection-v1-0-1 
        let $path-info := atomdb:edit-path-info( $v1-0-1 )
        return atomdb:update-member( $path-info , $v1-0-1 )
       return $migrated
    else 
    for $entry in $collection-v1-0-1 
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
declare function local:modify-nodes($collection-v1-0) as element( atom:entry )*
{

   let $collection-v1-0-1 := 
       for $v1-0 in $collection-v1-0
       let $rep := update replace $v1-0//study/@profile with "http://www.cggh.org/2010/chassis/manta/1.0.1"
       return update insert <person-is-contactable/> following $v1-0//study/acknowledgements/person/institution
       
    return $collection-v1-0-1

    
};


declare function local:check-changes-drafts() as item() *{
  let $all-drafts := local:get-content("drafts")
 let $new-drafts :=  local:get-new-versioned-content-drafts($all-drafts)
 let $ret := for $draft in $new-drafts 
         let $out := if (count($draft//study-entry-container//study//acknowledgements/person/person-is-contactable) = 1) then 
             let $msg := "Added person-is-contactable to draft"
             return $msg
          else
             let $msg := "Failed to add person-is-contactable to draft"
             return $msg
         return $out
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
        
    else common-protocol:do-method-not-allowed( "/admin/migrate-draft-1.0-to-1.0.1.xql" , "/admin/migrate-draft-1.0-to-1.0.1.xql" , ( "GET" , "POST" ) )
    
    
