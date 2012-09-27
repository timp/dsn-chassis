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
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/drugNameOther\[(\d+)\]</label>
				<value>Reg$8Drug$10OtherName</value>
			</fieldLabelMapping>  into $old//fieldLabelMappings
            let $new1 := update insert
<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/manufacturerOther\[(\d+)\]</label>
				<value>Reg$8Drug$10OtherManufacturer</value>
			</fieldLabelMapping> into $old//fieldLabelMappings
             let $new2 := update insert
<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/tradeNameOther\[(\d+)\]</label>
				<value>Reg$8Drug$10OtherTradeName</value>
			</fieldLabelMapping>
            into $old//fieldLabelMappings
            let $new3 := update insert
<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/activeIngredients\[(\d+)\]/activeIngredient\[(\d+)\]/activeIngredientNameOther\[(\d+)\]</label>
				<value>Reg$8Drug$10Act$12OtherName</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			
			let $new4 := update insert
            <fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/acknowledgements\[(\d+)\]/people\[(\d+)\]/person\[(\d+)\]/first-name\[(\d+)\]</label>
				<value>ackAuthor$6Name</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			
			let $new5 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/acknowledgements\[(\d+)\]/people\[(\d+)\]/person\[(\d+)\]/middle-name\[(\d+)\]</label>
				<value>ackAuthor$6MiddleName</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			
			let $new6 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/acknowledgements\[(\d+)\]/people\[(\d+)\]/person\[(\d+)\]/family-name\[(\d+)\]</label>
				<value>ackAuthor$6Surname</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			
			let $new7 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/acknowledgements\[(\d+)\]/people\[(\d+)\]/person\[(\d+)\]/email-address\[(\d+)\]</label>
				<value>ackAuthor$6Email</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			
			let $new8 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/acknowledgements\[(\d+)\]/people\[(\d+)\]/person\[(\d+)\]/institution\[(\d+)\]</label>
				<value>ackAuthor$6Institute</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			
			let $new9 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/acknowledgements\[(\d+)\]/people\[(\d+)\]/person\[(\d+)\]/person-is-contactable\[(\d+)\]</label>
				<value>ackAuthor$6Contactable</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			
			let $new10 := update insert
<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/studyDesign\[(\d+)\]</label>
				<value>studyDesign</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			
			let $new11 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/samplingTimes\[(\d+)\]</label>
				<value>samplingTimes</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			
			let $new12 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/PKcomments\[(\d+)\]</label>
				<value>PKcomment</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			
			let $new13 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/analytes\[(\d+)\]/analyte\[(\d+)\]/targetDose\[(\d+)\]</label>
				<value>targetDose$7</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			
			let $new14 := update insert			
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/analytes\[(\d+)\]/analyte\[(\d+)\]/fatAmount\[(\d+)\]</label>
				<value>fatAmount$7</value>
			</fieldLabelMapping>
			into $old//fieldLabelMappings
			
			let $new15 := update insert
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/analytes\[(\d+)\]/analyte\[(\d+)\]/unitsOfMeasure\[(\d+)\]</label>
				<value>unitsOfMeasure$7</value>
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
    
    
