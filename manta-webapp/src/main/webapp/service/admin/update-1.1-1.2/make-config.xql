declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;
declare namespace at = "http://purl.org/atompub/tombstones/1.0";

import module namespace response = "http://exist-db.org/xquery/response" ;

import module namespace config = "http://purl.org/atombeat/xquery/config" at "../../config/shared.xqm" ;
import module namespace CONSTANT = "http://purl.org/atombeat/xquery/constants" at "../../lib/constants.xqm" ;
import module namespace xutil = "http://purl.org/atombeat/xquery/xutil" at "../../lib/xutil.xqm" ;
import module namespace atomsec = "http://purl.org/atombeat/xquery/atom-security" at "../../lib/atom-security.xqm" ;
import module namespace atomdb = "http://purl.org/atombeat/xquery/atomdb" at "../../lib/atomdb.xqm" ;
import module namespace ap = "http://purl.org/atombeat/xquery/atom-protocol" at "../../lib/atom-protocol.xqm" ;
import module namespace common-protocol = "http://purl.org/atombeat/xquery/common-protocol" at "../../lib/common-protocol.xqm" ;
import module namespace config-collections = "http://purl.org/atombeat/xquery/config-collections" at "../collections.xqm" ;
import module namespace security-config = "http://purl.org/atombeat/xquery/security-config" at "../../config/security.xqm" ;



declare function local:do-get($current-host, $new-host) as item()*
{
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content($current-host, $new-host)
};



declare function local:content($current-host, $new-host) as item()*
{
    let $x := ''
    return
    
        <html>
            <head>
                <title>Change host</title>
            </head>
            <body>
                <h1>Change host</h1>
                
                <p>
                    <form method="post" action="">
                        Current Repository Path <input name="oldhost" size="90" value="{$current-host}" /> <br/>
                        New Repository Path <input name="newhost" size="90" value="{$new-host}" /><br/>
                        <input type="submit" value="Migrate Data"></input>
                    </form>
                    <form method="get" action="">
                        <input type="submit" value="Refresh"></input>
                    </form>
                </p>
            </body>
        </html>
};

declare function local:after-content($new-content) as item()*
{
let $x := ''
return

<html>
    <head>
        <title>Creating config collection</title>
    </head>
    <body>
        <h1>Creating config collection</h1>
        
        <p>
            {$new-content}
        </p>
    </body>
</html>
};

declare function local:do-modifications() as xs:string*
{
    let $status-config := <statusTypes xmlns="">
	<statusType>
		<label>Newly created study</label>
		<value>new</value>
	</statusType>
	<statusType>
		<label>In curation</label>
		<value>in</value>
	</statusType>
	<statusType>
		<label>Awaiting response (curator)</label>
		<value>wait-internal</value>
	</statusType>
	<statusType>
		<label>Awaiting response (contributor)</label>
		<value>wait-external</value>
	</statusType>
	<statusType>
		<label>Complete</label>
		<value>complete</value>
	</statusType>
	<statusType>
		<label>Published to WWARN Explorer</label>
		<value>exp</value>
	</statusType>
</statusTypes>
    
    let $entry1 := local:create-config-entry('status',$status-config)
	    
    return concat($entry1, '')
};

declare function local:create-config-collection() as xs:string {
    let $user-name := request:get-attribute( $config:user-name-request-attribute-key )
    let $collection-path-info := '/config'
    
    let $feed :=
        <atom:feed atombeat:enable-versioning="true"
                atombeat:recursive="false">
            <atom:title type="text">Config</atom:title>
        </atom:feed>
        
    let $collection-db-path := atomdb:create-collection( $collection-path-info , $feed, $user-name )
    let $collection-descriptor := $security-config:config-collection-security-descriptor
    let $descriptor-stored := atomsec:store-descriptor( $collection-path-info , $collection-descriptor )
    
    return $descriptor-stored
};

declare function local:create-atom-entry($groups, $id, $author) as element(atom:entry) {
<atom:entry xmlns:atom="http://www.w3.org/2005/Atom">
    <atom:id>{$id}</atom:id>
    <atom:published>2010-12-08T15:47:58.242Z</atom:published>
    <atom:updated>2010-12-13T12:57:32.002Z</atom:updated>
    <atom:author>
        <atom:email>{$author}</atom:email>
    </atom:author>
    <atom:title type="text">Config</atom:title>
    <atom:content type="application/vnd.chassis-manta+xml">
        {$groups}
    </atom:content>
    <ar:comment xmlns:ar="http://purl.org/atompub/revision/1.0">
        <atom:author>
            <atom:email>{$author}</atom:email>
        </atom:author>
        <atom:updated>2010-12-13T12:57:32.002Z</atom:updated>
        <atom:summary/>
    </ar:comment>
</atom:entry>
};

declare function local:create-config-entry($new-name as xs:string, $atom-content) as xs:string
{
    
    let $new-collection := '/config'
    let $id := concat($config:self-link-uri-base,$new-collection,'/',$new-name)
    
    let $user-name := request:get-attribute( $config:user-name-request-attribute-key )
    let $content := local:create-atom-entry($atom-content, $id, $user-name)
    let $member-study-info := atomdb:create-member( $new-collection , $new-name , $content, $user-name )  
    return $id
};

declare function local:do-post($old-host) as item()*
{
(: SEND RESPONSE :)        
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:after-content($old-host)
};


declare function local:do-migration() {

    let $new-collection := local:create-config-collection()
    let $new-content := local:do-modifications()
        
    return local:do-post($new-content)
};

let $login := xmldb:login( "/" , $config:exist-user , $config:exist-password )
(: If this fails with a null pointer exception then you need to reindex the database :)
let $host := doc("atombeat/content/studies/.feed")//atom:link[@rel="self"]/@href
let $current-host := substring-before($host,"/content")
let $new-host := $config:service-url-base

return 

    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get($current-host, $new-host)
    
    else if ( request:get-method() = $CONSTANT:METHOD-POST ) 
    
    then local:do-migration()
    
    else ()
    
    
