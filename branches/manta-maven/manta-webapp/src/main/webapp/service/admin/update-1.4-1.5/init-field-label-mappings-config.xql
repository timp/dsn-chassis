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



declare function local:do-get() as item()*
{
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content()
};



declare function local:content() as item()*
{
    let $x := ''
    return
    
        <html>
            <head>
                <title>Initialize field label mappings config collection contents</title>
            </head>
            <body>
                <h1>Create field label mappings config entries</h1>
                
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


declare function local:do-modifications() as xs:string*
{
  
    
   let $values :=
<fieldLabelMappings xmlns="">
	<fieldLabelMapping deprecated="n">
		<label>/atom:entry\[(\d+)\]/atom:id\[(\d+)\]</label>
		<value>AtomID</value>
	</fieldLabelMapping>
	<fieldLabelMapping deprecated="n">
		<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/publications\[(\d+)\]/publication\[(\d+)\]/publication-title\[(\d+)\]</label>
		<value>Publication$5Title</value>
	</fieldLabelMapping>
</fieldLabelMappings>

   let $entry := local:create-config-entry('Field label mappings','field-label-mappings',$values)
   
   
    (: return concat($entry, '&#xD;', $entry2,'&#xD;', $entry3,'&#xD;', $entry4) :)
    return $entry    
};

declare function local:after-content($new-content) as item()*
{
let $x := ''
return

<html>
    <head>
        <title>Created field label mappings config collection</title>
    </head>
    <body>
        <h1>Created field label mappings config collection</h1>
        
        <p>
            {$new-content}
        </p>
    </body>
</html>
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

return 

    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get()
    
    else if ( request:get-method() = $CONSTANT:METHOD-POST ) 
    
    then local:do-migration()
    
    else ()
    
    
