declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;
declare namespace at = "http://purl.org/atompub/tombstones/1.0";

import module namespace response = "http://exist-db.org/xquery/response" ;

import module namespace config = "http://purl.org/atombeat/xquery/config" at "../config/shared.xqm" ;
import module namespace CONSTANT = "http://purl.org/atombeat/xquery/constants" at "../lib/constants.xqm" ;
import module namespace xutil = "http://purl.org/atombeat/xquery/xutil" at "../lib/xutil.xqm" ;
import module namespace atomsec = "http://purl.org/atombeat/xquery/atom-security" at "../lib/atom-security.xqm" ;
import module namespace atomdb = "http://purl.org/atombeat/xquery/atomdb" at "../lib/atomdb.xqm" ;
import module namespace ap = "http://purl.org/atombeat/xquery/atom-protocol" at "../lib/atom-protocol.xqm" ;
import module namespace common-protocol = "http://purl.org/atombeat/xquery/common-protocol" at "../lib/common-protocol.xqm" ;
import module namespace config-collections = "http://purl.org/atombeat/xquery/config-collections" at "collections.xqm" ;
import module namespace security-config = "http://purl.org/atombeat/xquery/security-config" at "../config/security.xqm" ;



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
                <title>Initialize config collection contents</title>
            </head>
            <body>
                <h1>Create config entries</h1>
                
                <p>
                    <form method="post" action="">
                        <input type="submit" value="Create Entries"></input>
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
	<statusType deprecated="n">
		<label>Newly created study</label>
		<value>new</value>
	</statusType>
	<statusType deprecated="n">
		<label>In curation</label>
		<value>in</value>
	</statusType>
	<statusType deprecated="n">
		<label>Awaiting response (curator)</label>
		<value>wait-internal</value>
	</statusType>
	<statusType deprecated="n">
		<label>Awaiting response (contributor)</label>
		<value>wait-external</value>
	</statusType>
	<statusType deprecated="n">
		<label>Complete</label>
		<value>complete</value>
	</statusType>
	<statusType deprecated="n">
		<label>Published to WWARN Explorer</label>
		<value>exp</value>
	</statusType>
</statusTypes>
    
    let $entry1 := local:create-config-entry('Study Status','status',$status-config)

let $values2 :=
<terms xmlns="">
    <term deprecated="n" filter="submit wwarn">
        <label>Data file</label>
        <value>http://www.cggh.org/2010/chassis/terms/DataFile</value>
    </term>
    <term deprecated="n" filter="submit wwarn">
        <label>Data dictionary</label>
        <value>http://www.cggh.org/2010/chassis/terms/DataDictionary</value>
    </term>
    <term deprecated="n" filter="submit wwarn">
        <label>Protocol</label>
        <value>http://www.cggh.org/2010/chassis/terms/Protocol</value>
    </term>
    <term deprecated="n" filter="submit wwarn">
        <label>Publication</label>
        <value>http://www.cggh.org/2010/chassis/terms/Publication</value>
    </term>
    <term deprecated="n" filter="submit wwarn">
        <label>Other</label>
        <value>http://www.cggh.org/2010/chassis/terms/Other</value>
    </term>
    <term deprecated="n" filter="wwarn">
        <label>Explorer Input</label>
        <value>http://www.cggh.org/2010/chassis/terms/Explorer</value>
    </term>
</terms>
   let $entry2 := local:create-config-entry('File data types','file-terms',$values2)
   
   let $values3 :=
<displayTypes xmlns="">
	<displayType deprecated="n">
		<label></label>
		<value>notset</value>
	</displayType>
	<displayType deprecated="n">
		<label>Not displayed</label>
		<value>none</value>
	</displayType>
	<displayType deprecated="n">
		<label>Display</label>
		<value>full</value>
	</displayType>
</displayTypes>

   let $entry3 := local:create-config-entry('Explorer display options','explorer-display-types',$values3)
    return concat($entry1, '&#xD;', $entry2,'&#xD;', $entry3)
};

declare function local:create-atom-entry($title, $groups, $id, $author) as element(atom:entry) {
<atom:entry xmlns:atom="http://www.w3.org/2005/Atom">
    <atom:id>{$id}</atom:id>
    <atom:published>2010-12-08T15:47:58.242Z</atom:published>
    <atom:updated>2010-12-13T12:57:32.002Z</atom:updated>
    <atom:author>
        <atom:email>{$author}</atom:email>
    </atom:author>
    <atom:title type="text">{$title}</atom:title>
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

declare function local:create-config-entry($title as xs:string, $new-name as xs:string, $atom-content) as xs:string
{
    
    let $new-collection := '/config'
    let $id := concat($config:self-link-uri-base,$new-collection,'/',$new-name)
    
    let $user-name := request:get-attribute( $config:user-name-request-attribute-key )
    let $content := local:create-atom-entry($title, $atom-content, $id, $user-name)
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
    
    
