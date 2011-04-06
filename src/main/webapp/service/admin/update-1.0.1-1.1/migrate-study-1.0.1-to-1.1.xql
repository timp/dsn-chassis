declare namespace atom = "http://www.w3.org/2005/Atom" ;

import module namespace response = "http://exist-db.org/xquery/response" ;

import module namespace config = "http://purl.org/atombeat/xquery/config" at "../config/shared.xqm" ;
import module namespace CONSTANT = "http://purl.org/atombeat/xquery/constants" at "../lib/constants.xqm" ;
import module namespace atomdb = "http://purl.org/atombeat/xquery/atomdb" at "../lib/atomdb.xqm" ;
import module namespace common-protocol = "http://purl.org/atombeat/xquery/common-protocol" at "../lib/common-protocol.xqm" ;
import module namespace config-collections = "http://purl.org/atombeat/xquery/config-collections" at "collections.xqm" ;

declare variable $test-study-entry {
<atom:entry xmlns:atom="http://www.w3.org/2005/Atom">
    <atom:id>http://localhost:8081/repository/service/content/studies/JDYEE</atom:id>
    <atom:published>2011-01-11T10:52:09.748Z</atom:published>
    <atom:updated>2011-01-11T10:52:09.748Z</atom:updated>
    <atom:link rel="self" type="application/atom+xml;type=entry" href="http://localhost:8081/repository/service/content/studies/JDYEE"/>
    <atom:link rel="edit" type="application/atom+xml;type=entry" href="http://localhost:8081/repository/service/content/studies/JDYEE"/>
    <atom:author>
        <atom:email>colin@example.org</atom:email>
    </atom:author>
    <atom:title type="text">Colin's study</atom:title>
    <atom:content type="application/vnd.chassis-manta+xml">
        <study profile="http://www.cggh.org/2010/chassis/manta/1.0.1">
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
        </study>
    </atom:content>
    <ar:comment xmlns:ar="http://purl.org/atompub/revision/1.0">
        <atom:author>
            <atom:email>colin@example.org</atom:email>
        </atom:author>
        <atom:updated>2011-01-11T10:52:09.748Z</atom:updated>
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
    let $studies := local:get-content("studies")
    let $studies-v1-0-1 := local:get-old-versioned-content-studies($studies) 
    let $studies-v1-1 := local:get-new-versioned-content-studies($studies)

    return
    
        <html>
            <head>
                <title>Data Migration - Study v1.0.1 to v1.1</title>
            </head>
            <body>
                <h1>Data Migration - Study v1.0.1 to v1.1</h1>
                
                <p>This script will:</p>
                
                <ul>
                    <li>Add a curator-notes element to all study entries, after the acknowledgements element.</li>
                    <li>Add a study-status element with the value "in" (In curation) to all study entries, after the curator-notes element.</li>
                </ul>
                
                <p>Total number of entries in <a href="../content/studies">Study</a> collection: <strong>{ count( $studies ) }</strong></p>
                <p>Number of entries in <a href="../content/studies">Study</a> collection using v1.0.1 profile: <strong>{ count( $studies-v1-0-1 ) }</strong></p>
                <p>Number of entries in <a href="../content/studies">Study</a> collection using v1.1 profile: <strong>{ count( $studies-v1-1 ) }</strong></p>
                
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

declare function local:get-new-versioned-content-studies($studies) as element( atom:entry )* {
    
    let $ret := $studies[atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/1.1']
    return $ret
};

declare function local:get-old-versioned-content-studies($studies) as element( atom:entry )* {
    
    let $ret := $studies[atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/1.0.1']
    return $ret
};

declare function local:do-modifications-studies($studies-v1-0-1) as element( atom:entry )*
{
    let $new := local:modify-nodes($studies-v1-0-1)
     
    let $ret := local:save-changes("studies", $new)
      
    (: Need to get the updated data from the db :)
    let $all := local:get-content("studies")
    let $content := local:get-old-versioned-content-studies($all)

    
    return $content
};


declare function local:save-changes($collection-name, $collection-v1-1)
{
let $testing := request:get-parameter("testing", "no")
    let $content := if ($testing = "no") then
      let $migrated :=     
        for $v1-1 in $collection-v1-1 
        let $path-info := atomdb:edit-path-info( $v1-1 )
        return atomdb:update-member( $path-info , $v1-1 )
       return $migrated
    else 
    for $entry in $collection-v1-1 
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
declare function local:modify-nodes($collection-v1-0-1) as element( atom:entry )*
{

   let $collection-v1-1 := 
       for $v1-0-1 in $collection-v1-0-1
       let $rep := update replace $v1-0-1//study/@profile with "http://www.cggh.org/2010/chassis/manta/1.1"
       let $ins := update insert <curator-notes/> following $v1-0-1//study/acknowledgements
       return update insert <study-status>in</study-status> following $v1-0-1//study/curator-notes
       
    return $collection-v1-1

    
};


declare function local:check-changes-studies() as item() *{
  let $all-studies := local:get-content("studies")
 let $new-studies :=  local:get-new-versioned-content-studies($all-studies)
 let $ret := for $study in $new-studies
 
         let $out := if (count($study//curator-notes) = 1) then 
             let $msg := "Added curator-notes to study"
             return $msg
          else
             let $msg := "Failed to add curator-notes to study"
             return $msg
             
         let $out2 := if (count($study//study-status[text() = 'in']) = 1) then 
             let $msg := "Added study-status 'in' to study"
             return $msg
          else
             let $msg := "Failed to add study-status 'new' to study"
             return $msg
             
             
         return concat($out, '&#xD;', $out2)
         
    return $ret
};


declare function local:do-migration($collection-name) {
  
    let $collection := local:get-old-versioned-content-studies(local:get-content($collection-name))
    let $new-collection := local:do-modifications-studies($collection)
    let $ret := local:save-changes($collection-name, $new-collection)

    let $testing := request:get-parameter("testing", "no")
      
    let $saved := if ($testing != "no") then 
    
        if ($collection-name = "studies") then
            let $ret2 := local:check-changes-studies()
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

        let $test-studies-collection := xmldb:create-collection("xmldb:exist:///db", "test-studies"), 
            $doc := local:save-changes("studies", $test-study-entry)
        return $test-studies-collection

    
return 

    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get()
    
    else if ( request:get-method() = $CONSTANT:METHOD-POST ) 
    
    then 
        
        let $var := local:do-migration("studies")
        return $var
        
    else common-protocol:do-method-not-allowed( "/admin/migrate-study-1.0.1-to-1.1.xql" , "/admin/migrate-study-1.0.1-to-1.1.xql" , ( "GET" , "POST" ) )
    
    
