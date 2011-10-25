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

(: Migration actions: :)
(: Rename PQP to PIP :)
(: Allow for multiple institutions :)




declare function local:do-get() as item()*
{
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content('')
};



declare function local:content($content) as item()*
{
    let $study-infos := local:get-content()
        
    return
    
        <html>
            <head>
                <title>Data Migration - Change field mappings</title>
            </head>
            <body>
                <h1>Data Migration - Study field mappings v1.5.1 to v2.0</h1>
                
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

declare function local:get-content() as element( atom:entry )* {
  let $testing := request:get-parameter("testing", "no")
    let $content := if ($testing = "no") then
        let $ret := atomdb:retrieve-members( "/config" , false() )
        return $ret
    else
        let $ret := xmldb:xcollection( 'test' )/atom:entry (: not recursive :)
        return $ret
     
   return $content
};


declare function local:do-modifications($old-study-infos) as element( atom:entry )*
{
    let $new := local:modify-nodes($old-study-infos)
     
   
    return $new
};

declare function local:do-post($content) as item()*
{
(: SEND RESPONSE :)        
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content($content)
};

(: update works directly against the database - not on in memory fragments so this doesn't behave as expected :( :)
declare function local:modify-nodes($old-study-infos) as element( atom:entry )*
{
   
   let $new-study-infos := 
        for $old in $old-study-infos 
            let $new := update insert 
<fieldLabelMapping deprecated="n">
<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/acknowledgements\[(\d+)\]/institutions\[(\d+)\]/institution-ack\[(\d+)\]/institution-name\[(\d+)\]</label>
<value>ackInstitute$6</value>
</fieldLabelMapping>  into $old//fieldLabelMappings
            let $new1 := update insert
<fieldLabelMapping deprecated="n">
<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/acknowledgements\[(\d+)\]/institutions\[(\d+)\]/institution-ack\[(\d+)\]/institution-websites\[(\d+)\]/institution-url\[(\d+)\]</label>
<value>ackInstitute$6URL$8</value>
</fieldLabelMapping>  into $old//fieldLabelMappings
             let $new2 := update insert
<fieldLabelMapping deprecated="n">
<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/inclusionExclusionCriteria\[(\d+)\]/priorAntimalarialsExclusion\[(\d+)\]/priorAntimalarials\[(\d+)\]/drugTaken\[(\d+)\]</label>
<value>drugTaken$8</value>
</fieldLabelMapping>
            into $old//fieldLabelMappings
            let $clean1 := update delete $old//fieldLabelMapping[value/text() = 'ackInstitute$5']
            let $clean2 := update delete $old//fieldLabelMapping[value/text() = 'ackInstitute$5URL']
        return $old

    
     
    return $new-study-infos
    
};


declare function local:do-migration() {
  
    let $all := local:get-content()
    
    
    let $new-content := local:do-modifications($all)
        
    
        
    return local:do-post($new-content)
};

let $login := xmldb:login( "/" , $config:exist-user , $config:exist-password )

return
    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get()
    
    else if ( request:get-method() = $CONSTANT:METHOD-POST ) 
    
    then local:do-migration()
    
    else ()
    
    
