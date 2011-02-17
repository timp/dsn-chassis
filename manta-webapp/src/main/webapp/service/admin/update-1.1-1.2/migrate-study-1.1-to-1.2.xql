declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;
declare namespace ar = "http://purl.org/atompub/revision/1.0";
declare namespace app = "http://www.w3.org/2007/app";

import module namespace response = "http://exist-db.org/xquery/response" ;

import module namespace config = "http://purl.org/atombeat/xquery/config" at "../../config/shared.xqm" ;
import module namespace CONSTANT = "http://purl.org/atombeat/xquery/constants" at "../../lib/constants.xqm" ;
import module namespace atomdb = "http://purl.org/atombeat/xquery/atomdb" at "../../lib/atomdb.xqm" ;
import module namespace common-protocol = "http://purl.org/atombeat/xquery/common-protocol" at "../../lib/common-protocol.xqm" ;
import module namespace config-collections = "http://purl.org/atombeat/xquery/config-collections" at "../collections.xqm" ;
import module namespace atom-protocol = "http://purl.org/atombeat/xquery/atom-protocol" at "../../lib/atom-protocol.xqm" ;

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
            <study-status>in</study-status>
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
    let $old-studies := local:get-old-versioned-content-studies($studies) 
    let $new-studies := local:get-new-versioned-content-studies($studies)

    return
    
        <html>
            <head>
                <title>Data Migration - Study v1.1 to v1.2</title>
            </head>
            <body>
                <h1>Data Migration - Study v1.1 to v1.2</h1>
                
                <p>This script will:</p>
                
                <ul>
                    <li>Move module selection to study (from study-info).</li>
                    <li>Add an instituational acknowledgement field.</li>
                    <li>Allow for non-pubmed publications.</li>
                    <li>Add a field to capture the type of display in explorer.</li>
                    <li>Move drafts to studies</li>
                </ul>
                
                <p>Total number of entries in <a href="../content/studies">Study</a> collection: <strong>{ count( $studies ) }</strong></p>
                <p>Number of entries in <a href="../content/studies">Study</a> collection using v1.1 profile: <strong>{ count( $old-studies ) }</strong></p>
                <p>Number of entries in <a href="../content/studies">Study</a> collection using v1.2 profile: <strong>{ count( $new-studies ) }</strong></p>
                
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
    
    let $ret := $studies[atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/1.2']
    return $ret
};

declare function local:get-old-versioned-content-studies($studies) as element( atom:entry )* {
    
    let $ret := $studies[atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/1.1']
    return $ret
};

declare function local:do-modifications-studies($old-studies, $collection-name) as element( atom:entry )*
{
    let $new := local:modify-nodes($old-studies, $collection-name)
     (:
    let $ret := local:save-changes("studies", $new)
      :)
    (: Need to get the updated data from the db :)
    let $all := local:get-content("studies")
    let $content := local:get-old-versioned-content-studies($all)

    
    return $content
};


declare function local:save-changes($collection-name, $collection-new)
{
let $testing := request:get-parameter("testing", "no")
    let $content := if ($testing = "no") then
      let $migrated :=     
        for $new in $collection-new
        let $path-info := atomdb:edit-path-info( $new )
        return atomdb:update-member( $path-info , $new )
       return $migrated
    else 
    for $entry in $collection-new 
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

declare function local:add-modules-to-study($modules, $study) as element( atom:entry )* 
{
    let $study := atomdb:retrieve-members( $study , false() )
    let $new := update insert $modules following $study//study-status
    return $study
};

declare function local:move-module-info($collection-old) as element( atom:entry )*
{

    let $study-infos := atomdb:retrieve-members( "/study-info" , false() )
    let $old-study-infos := $study-infos[atom:content/study-info/@profile='http://www.cggh.org/2010/chassis/manta/1.0.1']

   let $collection-new := 
       for $old in $old-study-infos
           let $mods := $old//modules
           let $study := $old//atom:link[@rel="http://www.cggh.org/2010/chassis/terms/originStudy"]/@href
           let $study-ref := replace($study,concat($config:self-link-uri-base,'/content'),'')
           let $move := local:add-modules-to-study($mods,$study-ref)
           let $del1 := update delete $old//modules
       return update replace $old//study-info/@profile with "http://www.cggh.org/2010/chassis/manta/1.2"
       
    return $collection-new

    
};

declare function local:create-study($old as element(atom:entry), $published as element(atom:published), $author as element(atom:author)*, $comment as element(ar:comment)) as element(atom:entry)*
{
   let $create := atom-protocol:do-post-atom-entry('/studies', $old)
    
    let $new := local:replace-atom-content($create//body/atom:entry, $create//atom:content, $published, $author, $comment)
    let $path := atomdb:edit-path-info( $new )
    let $update := atomdb:store-member('/studies', concat($path,'.atom'), $new)
    return $create//body/atom:entry
};

declare function local:replace-atom-content( $entry as element(atom:entry) , $groups as item(), $published as item(), $author as item()*, $comment as item()* ) as element(atom:entry)
{
<atom:entry xmlns:atom="http://www.w3.org/2005/Atom">
    {
        $entry/atom:id ,
        $published,
        $entry/atom:updated
    }
    <atom:link rel="self" type="application/atom+xml;type=entry" href="{$entry/atom:link[@rel='self']/@href}"/>
    <atom:link rel="edit" type="application/atom+xml;type=entry" href="{$entry/atom:link[@rel='edit']/@href}"/>    
    {
        $author,
        $entry/atom:title,
        $entry/app:control
    }
    {$groups}
    {
        $comment
    }
</atom:entry>

};
declare function local:update-permissions($href, $members) as element(atom:entry)*
{
    let $uri := substring-after($href,$config:self-link-uri-base)
    let $group := atom-protocol:do-get-member($uri)
    let $content := <atom:content type="application/vnd.chassis-manta+xml">
        {$members}
        </atom:content>
    let $new := local:replace-atom-content($group//body/atom:entry, $content, $group//body/atom:entry/atom:published, $group//body/atom:entry/atom:author,$group//body/atom:entry/ar:comment)
    let $update := atom-protocol:do-put-atom-entry($uri,$new)
    return $update
};

declare function local:move-files($src-draft, $target) as element(atom:entry)*
{
    let $id := substring-after($src-draft,concat($config:self-link-uri-base,'/drafts/'))
    let $collection-uri := concat('/media/draft/',$id)
    let $files := atom-protocol:do-get-collection($collection-uri)
    let $new-feed-location := substring-after($target,$config:self-link-uri-base)
    let $update := atom-protocol:do-post-atom-feed($new-feed-location,$files//body/atom:feed)
    return $update
};

declare function local:move-draft-nodes() as element( atom:entry )*
{

    let $drafts := atomdb:retrieve-members( "/drafts" , false() )
    
   let $collection-new := 
       for $old in $drafts
           let $draft-atom := update insert <app:control xmlns:app="http://www.w3.org/2007/app"><draft>yes</draft></app:control> following $old//atom:content/draft/study-entry-container/atom:entry/atom:title
            let $where := update insert <ui-info>{$old//wizard-pane-to-show}</ui-info> preceding $old//study-is-published
           return update insert $old//registrant-has-agreed-to-the-terms preceding $old//study-is-published
          
   let $drafts-modified := atomdb:retrieve-members( "/drafts" , false() )   
   let $new-studies := 
       for $old in $drafts-modified
           (: Create as study :) 
           let $move := local:create-study($old/atom:content/draft/study-entry-container/atom:entry, $old/atom:published, $old/atom:author, $old//ar:comment)
           (: Now the media :)
           let $files := local:move-files($old//atom:id, $move//atom:link[@rel='http://www.cggh.org/2010/chassis/terms/submittedMedia']/@href)
           (: Need to move permissions :)
           let $perm := local:update-permissions($move//atom:link[@rel='http://www.cggh.org/2010/chassis/terms/groups']/@href,$old/atom:content/draft//atombeat:security-descriptor/atombeat:groups)
       return $move
       
    return $new-studies

};

(: update applies directly to the database, not upon in-memory fragments :)
declare function local:modify-old-study-nodes($collection-old) as element( atom:entry )*
{

   let $collection-new := 
       for $old in $collection-old
       return update insert <registrant-has-agreed-to-the-terms>yes</registrant-has-agreed-to-the-terms> preceding $old//study-is-published
       
    return $collection-new

    
};
(: update applies directly to the database, not upon in-memory fragments :)
declare function local:modify-study-nodes($collection-old) as element( atom:entry )*
{

   let $collection-new := 
       for $old in $collection-old
       let $rep := update replace $old//study/@profile with "http://www.cggh.org/2010/chassis/manta/1.2"
       return $rep
       
       
    return $collection-new

    
};

declare function local:modify-nodes($collection-old, $collection-name) as element( atom:entry )*
{
    let $drafts := local:move-draft-nodes()
    let $old := local:modify-old-study-nodes($collection-old)
    (:Need to refresh the collection as it will have changed:)
    let $collection-interim := local:get-old-versioned-content-studies(local:get-content($collection-name))
    let $new := local:modify-study-nodes($collection-interim)
    return $new
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
    let $new-collection := local:do-modifications-studies($collection, $collection-name)
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
    
    
