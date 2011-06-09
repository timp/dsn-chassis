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
import module namespace manta-plugin = "http://www.cggh.org/2010/chassis/manta/xquery/atombeat-plugin" at "../../plugins/manta-plugin.xqm";


declare function local:edit-path-info( $entry as element(atom:entry) ) as xs:string?
{
    let $href := $entry/atom:link[@rel='edit']/@href
    return
        if ( exists( $href ) ) then 
            let $uri := $href cast as xs:string
            return
                if ( starts-with( $uri , $config:edit-link-uri-base ) )
                then substring-after( $uri , $config:edit-link-uri-base )
                else ()
        else ()
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
                <title>Data Migration - Study v1.2 to v1.3</title>
            </head>
            <body>
                <h1>Data Migration - Study v1.2 to v1.3</h1>
                
                <p>This script will:</p>
                
                <ul>
                    <li>Copy the GROUP_ADMINISTRATOR group element from groups to the study entry</li>
                </ul>
                
                <p>Total number of entries in <a href="../content/studies">Study</a> collection: <strong>{ count( $studies ) }</strong></p>
                <p>Number of entries in <a href="../content/studies">Study</a> collection using v1.2 profile: <strong>{ count( $old-studies ) }</strong></p>
                <p>Number of entries in <a href="../content/studies">Study</a> collection using v1.3 profile: <strong>{ count( $new-studies ) }</strong></p>
                
                <p>Note: This script has no test mode.</p>
                
                <p>
                    <form method="post" action="">
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

    let $content := atomdb:retrieve-members( concat("/", $collection) , false() )
    return $content
};

declare function local:get-new-versioned-content-studies($studies) as element( atom:entry )* {
    
    let $ret := $studies[atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/1.3']
    return $ret
};

declare function local:get-old-versioned-content-studies($studies) as element( atom:entry )* {
    
    let $ret := $studies[atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/1.2']
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

    let $content := 

        for $new in $collection-new
        let $path-info := local:edit-path-info( $new )
        return atomdb:update-member( $path-info , $new )
 
    return $content
};

declare function local:do-post($content) as item()*
{
(: SEND RESPONSE :)        
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content($content)
};


declare function local:get-permissions($group) as element(atom:entry)*
{
 let $href := $group//atom:link[@rel='self']/@href
    let $uri := substring-after($href,$config:self-link-uri-base)
     let $logi := util:log-app("debug", "getting", $uri)
    let $request := local:prepare-request('GET', $uri)
    let $group := atom-protocol:do-get-member($request)
    
    let $logi := util:log-app("debug", "entry", $group)
 let $href := $group//atom:link[@rel='http://www.cggh.org/2010/chassis/terms/groups']/@href
    let $uri := substring-after($href,$config:self-link-uri-base)
     let $logi := util:log-app("debug", "getting", $uri)
    let $request := local:prepare-request('GET', $uri)
    let $group := atom-protocol:do-get-member($request)
    let $logi := util:log-app("debug", "got", $group)
    return $group
};

declare function local:prepare-request($op, $path) {
   <request>
    <path-info>{$path}</path-info>
    <method>{$op}</method>
    <headers>
        <header>
            <name>Accept</name>
            <value>application/atom+xml</value>
        </header>
        <header>
            <name>Content-Type</name>
            <value>application/atom+xml</value>
        </header>
    </headers>
    <user>admin@wwarn.org</user>
    <roles>
        <role>ROLE_CHASSIS_ADMINISTRATOR</role>
        <role>ROLE_CHASSIS_CONTRIBUTOR</role>
        <role>ROLE_CHASSIS_CURATOR</role>
        <role>ROLE_CHASSIS_USER</role>
    </roles>
</request>
};


declare function local:copy-admin-nodes($collection) as element( atom:entry )*
{

  
   let $groups := 
       for $group in $collection
           	           
	          let $logi := util:log-app("debug", "group", $group)
	           
	           (: Need to move permissions :)
	           let $perm := local:get-permissions($group)         
	           let $new := update insert $perm//atombeat:group[@id='GROUP_ADMINISTRATORS'] following $group//modules
	           let $profile := update replace $group//study/@profile with "http://www.cggh.org/2010/chassis/manta/1.3"
	           return $perm
           


    return $groups

};


declare function local:modify-nodes($collection-old, $collection-name) as element( atom:entry )*
{
    let $drafts := local:copy-admin-nodes($collection-old)
    return $drafts
    
};



declare function local:do-migration($collection-name) {
  
    let $collection := local:get-old-versioned-content-studies(local:get-content($collection-name))
    let $new-collection := local:do-modifications-studies($collection, $collection-name)
    let $ret := local:save-changes($collection-name, $new-collection)

    return local:do-post('')
};

let $login := xmldb:login( "/" , $config:exist-user , $config:exist-password )

let $content := ''


    
return 

    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get()
    
    else if ( request:get-method() = $CONSTANT:METHOD-POST ) 
    
    then 
        
        let $var := local:do-migration("studies")
        return $var
        
    else ()
    
    
