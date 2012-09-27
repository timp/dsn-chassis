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
                <h1>Data Migration - Study field mappings v2.1 to v3.0</h1>
                
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
            			
			let $new1 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/studyLabel\[(\d+)\]</label>
				<value>studyLabel</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			let $new2 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/transmissionIntensity\[(\d+)\]/transmissionIntensityLevel\[(\d+)\]</label>
				<value>Site$6IntensityLevel</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			let $rm1 := update delete $old//fieldLabelMapping[/value = 'SampleMatrix$7']
			let $new3 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/sampleMatrixType\[(\d+)\]</label>
				<value>SampleMatrix$5</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			let $new4 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/susceptibility\[(\d+)\]/precursorAdded\[(\d+)\]</label>
				<value>precursorAdded</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			let $new5 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/susceptibility\[(\d+)\]/precursorAddedOther\[(\d+)\]</label>
				<value>precursorOther</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			let $new6 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/referenceClone\[(\d+)\]</label>
				<value>refClone</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			let $new7 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/referenceCloneOther\[(\d+)\]</label>
				<value>refCloneOther</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			let $new8 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/susceptibility\[(\d+)\]/timeOfIncubationOther\[(\d+)\]</label>
				<value>incTimeOther</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings

            let $new9 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/PKInclusionCriteria\[(\d+)\]</label>
				<value>PKIncCrit</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings

            let $new10 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/analytes\[(\d+)\]/analyte\[(\d+)\]/PKtitle\[(\d+)\]</label>
				<value>PKtitle$7</value>
			</fieldLabelMapping>
            into $old//fieldLabelMappings

            let $new11 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/analytes\[(\d+)\]/analyte\[(\d+)\]/PKnumOfSamples\[(\d+)\]</label>
				<value>PKnoOfSamps$7</value>
			</fieldLabelMapping>
            into $old//fieldLabelMappings

            let $new12 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/analytes\[(\d+)\]/analyte\[(\d+)\]/PKregimen\[(\d+)\]</label>
				<value>PKregi$7</value>
			</fieldLabelMapping>
            into $old//fieldLabelMappings

            let $new13 := update insert
            <fieldLabelMapping deprecated="n" filter="ignore">
				<label>/atom:entry\[(\d+)\]/app:control\[(\d+)\]/app:draft\[(\d+)\]</label>
				<value>DraftStatus</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings			           			

	        
	        let $new14 := update insert
			<fieldLabelMapping deprecated="n" filter="ignore">
				<label>/atom:entry\[(\d+)\]/app:control\[(\d+)\]/app:draft\[(\d+)\]</label>
				<value>DraftStatus</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			
			
			let $new15 := update insert
			<fieldLabelMapping deprecated="n" filter="ignore">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/derived-study-id\[(\d+)\]</label>
				<value>DerivedFrom</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			
			let $new16 := update insert
			<fieldLabelMapping deprecated="n" filter="ignore">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/proxy-for-institution\[(\d+)\]</label>
				<value>ProxyInst</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			
			let $new17 := update insert
			<fieldLabelMapping deprecated="n" filter="ignore">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/proxy-for-name\[(\d+)\]</label>
				<value>ProxyName</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			
			let $rm2 := update delete $old//fieldLabelMapping[/value = 'GroupID']
			let $new18 := update insert
			<fieldLabelMapping deprecated="n" filter="ignore">
				<label>/atom:entry\[1\]/atom:content\[1\]/study\[1\]/atombeat:group\[1\]/@id</label>
				<value>GroupID</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			
			let $rm3 := update delete $old//fieldLabelMapping[/value = 'SSQVersion']
			let $new19 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[1\]/atom:content\[1\]/study\[1\]/@profile</label>
				<value>SSQVersion</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings

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
    
    
