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
   
   
	let $values4 :=   
	<fieldLabelMappings>
			<fieldLabelMapping deprecated="n" filter="ignore">
				<label>/atom:entry\[(\d+)\]/atom:id\[(\d+)\]</label>
				<value>AtomID</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n" filter="ignore">
				<label>/atom:entry\[(\d+)\]/app:control\[(\d+)\]/draft\[(\d+)\]</label>
				<value>DraftStatus</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n" filter="ignore">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/ui-info\[(\d+)\]/wizard-pane-to-show\[(\d+)\]</label>
				<value>WizardPaneToShow</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n" filter="ignore">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/atombeat:group\[(\d+)\]/atombeat:member\[(\d+)\]</label>
				<value>AtombeatGroupMember</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n" filter="ignore">
				<label>/atom:entry\[(\d+)\]/ar:comment\[(\d+)\]/atom:author\[(\d+)\]/atom:email\[(\d+)\]</label>
				<value>AuthorEmail</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n" filter="ignore">
				<label>/atom:entry\[(\d+)\]/ar:comment\[(\d+)\]/atom:updated\[(\d+)\]</label>
				<value>UpdatedTimestamp</value>
			</fieldLabelMapping>
			
			
			<fieldLabelMapping deprecated="n" filter="ignore">
				<label>/atom:entry\[(\d+)\]/atom:author\[(\d+)\]/atom:email\[(\d+)\]</label>
				<value>AuthorEmail</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n" filter="ignore">
				<label>/atom:entry\[(\d+)\]/atom:title\[(\d+)\]</label>
				<value>AtomTitle</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/studyTitle\[(\d+)\]</label>
				<value>Title</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/manta:id\[(\d+)\]</label>
				<value>StudyCode</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n" filter="ignore">
				<label>/atom:entry\[(\d+)\]/atom:published\[(\d+)\]</label>
				<value>CompletionDate</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n" filter="ignore">
				<label>/atom:entry\[(\d+)\]/atom:updated\[(\d+)\]</label>
				<value>DateLastUpdated</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n" filter="ignore">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/registrant-has-agreed-to-the-terms\[(\d+)\]</label>
				<value>TermsOfSubmission</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-is-published\[(\d+)\]</label>
				<value>StudyPublished</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n" filter="ignore">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/explorer-display\[(\d+)\]</label>
				<value>ExplorerDisplay</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/publications\[(\d+)\]/publication\[(\d+)\]/publication-title\[(\d+)\]</label>
				<value>Publication$5Title</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/publications\[(\d+)\]/publication\[(\d+)\]/pmid\[(\d+)\]</label>
				<value>Publication$5PubMedID</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/publications\[(\d+)\]/publication\[(\d+)\]/publication-references\[(\d+)\]/publication-reference\[(\d+)\]</label>
				<value>Publication$5URL$7</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/acknowledgements\[(\d+)\]/institution-ack\[(\d+)\]/institution-name\[(\d+)\]</label>
				<value>ackInstitute$5</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/acknowledgements\[(\d+)\]/institution-ack\[(\d+)\]/institution-websites\[(\d+)\]/institution-url\[(\d+)\]</label>
				<value>ackInstitute$5URL</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/acknowledgements\[(\d+)\]/person\[(\d+)\]/first-name\[(\d+)\]</label>
				<value>ackAuthor$5Name</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/acknowledgements\[(\d+)\]/person\[(\d+)\]/middle-name\[(\d+)\]</label>
				<value>ackAuthor$5MiddleName</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/acknowledgements\[(\d+)\]/person\[(\d+)\]/family-name\[(\d+)\]</label>
				<value>ackAuthor$5Surname</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/acknowledgements\[(\d+)\]/person\[(\d+)\]/email-address\[(\d+)\]</label>
				<value>ackAuthor$5Email</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/acknowledgements\[(\d+)\]/person\[(\d+)\]/institution\[(\d+)\]</label>
				<value>ackAuthor$5Institute</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/acknowledgements\[(\d+)\]/person\[(\d+)\]/person-is-contactable\[(\d+)\]</label>
				<value>ackAuthor$5Contactable</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-status\[(\d+)\]</label>
				<value>StudyStatus</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/modules\[(\d+)\]</label>
				<value>WWARNmodules</value>
			</fieldLabelMapping>
			
			
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/studyInfoStatus\[(\d+)\]</label>
				<value>StudyInfoStatus</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/start\[(\d+)\]</label>
				<value>StudyStartDate</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/end\[(\d+)\]</label>
				<value>StudyEndDate</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/country\[(\d+)\]</label>
				<value>Site$6Country</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/region\[(\d+)\]</label>
				<value>Site$6Region</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/district\[(\d+)\]</label>
				<value>Site$6District</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/locality\[(\d+)\]</label>
				<value>Site$6Locality</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/lookupAddress\[(\d+)\]</label>
				<value>Site$6Address</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/siteCode\[(\d+)\]</label>
				<value>Site$6Code</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/testingDelay\[(\d+)\]</label>
				<value>site$6testing_delay</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/anticoagulant\[(\d+)\]</label>
				<value>site$6anticoagulant</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/transportAndStorageTemperature\[(\d+)\]</label>
				<value>site$6transportstorage_temp</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/latitude\[(\d+)\]</label>
				<value>Site$6Latitude</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/longitude\[(\d+)\]</label>
				<value>Site$6Longitude</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/population\[(\d+)\]</label>
				<value>Site$6Population</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/transmissionIntensity\[(\d+)\]/annualParasitologicalIncidence\[(\d+)\]</label>
				<value>Site$6API</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/transmissionIntensity\[(\d+)\]/annualParasitologicalIncidenceYear\[(\d+)\]</label>
				<value>Site$6APIyear</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/transmissionIntensity\[(\d+)\]/parasitePrevalence\[(\d+)\]</label>
				<value>Site$6ParasitePrev</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/transmissionIntensity\[(\d+)\]/parasitePrevalenceYear\[(\d+)\]</label>
				<value>Site$6ParasitePrevYear</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/transmissionIntensity\[(\d+)\]/transmissionIntensityAgeFrom\[(\d+)\]</label>
				<value>Site$6APILowerAge</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/transmissionIntensity\[(\d+)\]/transmissionIntensityAgeFromUnits\[(\d+)\]</label>
				<value>Site$6APILowerAgeUnits</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/transmissionIntensity\[(\d+)\]/transmissionIntensityAgeTo\[(\d+)\]</label>
				<value>Site$6APIUpperAge</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/transmissionIntensity\[(\d+)\]/transmissionIntensityAgeToUnits\[(\d+)\]</label>
				<value>Site$6APIAgeUnits</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/transmissionIntensity\[(\d+)\]/entomologicalInoculationRate\[(\d+)\]</label>
				<value>Site$6EIR</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/transmissionIntensity\[(\d+)\]/entomologicalInoculationRateYear\[(\d+)\]</label>
				<value>Site$6EIRyear</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/transmissionIntensity\[(\d+)\]/seasonalTransmission\[(\d+)\]</label>
				<value>Site$6SeasonalTrans</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/sites\[(\d+)\]/site\[(\d+)\]/transmissionIntensity\[(\d+)\]/transmissionMonths\[(\d+)\]</label>
				<value>Site$6TransSeasons</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pathogens\[(\d+)\]</label>
				<value>PathogenSpecies</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/inclusionExclusionCriteria\[(\d+)\]/age\[(\d+)\]/maxAge\[(\d+)\]</label>
				<value>MaxAge</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/inclusionExclusionCriteria\[(\d+)\]/age\[(\d+)\]/maxAgeUnits\[(\d+)\]</label>
				<value>MaxAgeUnits</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/inclusionExclusionCriteria\[(\d+)\]/age\[(\d+)\]/minAge\[(\d+)\]</label>
				<value>MinAge</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/inclusionExclusionCriteria\[(\d+)\]/age\[(\d+)\]/minAgeUnits\[(\d+)\]</label>
				<value>MinAgeUnits</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/inclusionExclusionCriteria\[(\d+)\]/parasitaemia\[(\d+)\]/minParasitaemia\[(\d+)\]</label>
				<value>MinPara</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/inclusionExclusionCriteria\[(\d+)\]/parasitaemia\[(\d+)\]/maxParasitaemia\[(\d+)\]</label>
				<value>MaxPara</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/inclusionExclusionCriteria\[(\d+)\]/includeMixedInfections\[(\d+)\]</label>
				<value>MixedInfections</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/inclusionExclusionCriteria\[(\d+)\]/excludeIfPriorAntimalarials\[(\d+)\]</label>
				<value>PriorAntimalarial</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/inclusionExclusionCriteria\[(\d+)\]/priorAntimalarialsExclusion\[(\d+)\]/priorAntimalarials\[(\d+)\]</label>
				<value>PriorAntimalarialList</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/inclusionExclusionCriteria\[(\d+)\]/priorAntimalarialsExclusion\[(\d+)\]/priorAntimalarialsDetermination\[(\d+)\]</label>
				<value>PriorAntimalarialDetermin</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/inclusionExclusionCriteria\[(\d+)\]/priorAntimalarialsExclusion\[(\d+)\]/priorAntimalarialsHistoryWeeks\[(\d+)\]</label>
				<value>PriorAntimalarialHistWeeks</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/inclusionExclusionCriteria\[(\d+)\]/pregnancy\[(\d+)\]</label>
				<value>Pregnancy</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/inclusionExclusionCriteria\[(\d+)\]/treatmentReason\[(\d+)\]</label>
				<value>TreatmentReason</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/inclusionExclusionCriteria\[(\d+)\]/otherCriteria\[(\d+)\]</label>
				<value>OtherCriteria</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/regimenName\[(\d+)\]</label>
				<value>Reg$8Name</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/regimenSupervision\[(\d+)\]</label>
				<value>Reg$8Supervision</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/regimenUrl\[(\d+)\]</label>
				<value>Reg$8URL</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/name\[(\d+)\]</label>
				<value>Reg$8Drug$10Name</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/activeIngredients\[(\d+)\]/activeIngredient\[(\d+)\]/activeIngredientName\[(\d+)\]</label>
				<value>Reg$8Drug$10Act$12Name</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/activeIngredients\[(\d+)\]/activeIngredient\[(\d+)\]/activeIngredientMgPerDose\[(\d+)\]</label>
				<value>Reg$8Drug$10Act$12Dose</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/administrationRoute\[(\d+)\]</label>
				<value>Reg$8Drug$10AdmRoute</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/manufacturer\[(\d+)\]</label>
				<value>Reg$8Drug$10Manufacturer</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/tradeName\[(\d+)\]</label>
				<value>Reg$8Drug$10TradeName</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/batches\[(\d+)\]/drugBatch\[(\d+)\]/batchNumber\[(\d+)\]</label>
				<value>Reg$8Drug$10Bat$12BatchNo</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/batches\[(\d+)\]/drugBatch\[(\d+)\]/manufactureDate\[(\d+)\]</label>
				<value>Reg$8Drug$10Bat$12ManfDate</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/batches\[(\d+)\]/drugBatch\[(\d+)\]/expiryDate\[(\d+)\]</label>
				<value>Reg$8Drug$10Bat$12ExpDate</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/drugStorage\[(\d+)\]</label>
				<value>Reg$8Drug$10Storage</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/drugDosingDeterminant\[(\d+)\]</label>
				<value>Reg$8Drug$10Dosing</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/ageDosing\[(\d+)\]/ageDosingSchedule\[(\d+)\]/day\[(\d+)\]</label>
				<value>Reg$8Drug$10AgeSch$12Day</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/ageDosing\[(\d+)\]/ageDosingSchedule\[(\d+)\]/hour\[(\d+)\]</label>
				<value>Reg$8Drug$10AgeSch$12Hour</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/ageDosing\[(\d+)\]/ageDosingSchedule\[(\d+)\]/ageFrom\[(\d+)\]</label>
				<value>Reg$8Drug$10AgeSch$12Agemin</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/ageDosing\[(\d+)\]/ageDosingSchedule\[(\d+)\]/ageTo\[(\d+)\]</label>
				<value>Reg$8Drug$10AgeSch$12Agemax</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/ageDosing\[(\d+)\]/ageDosingSchedule\[(\d+)\]/dose\[(\d+)\]</label>
				<value>Reg$8Drug$10AgeSch$12Dose</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/ageDosing\[(\d+)\]/ageDosingSchedule\[(\d+)\]/doseUnit\[(\d+)\]</label>
				<value>Reg$8Drug$10AgeSch$12DoseUnit</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/weightGroupDosing\[(\d+)\]/weightGroupDosingSchedule\[(\d+)\]/day\[(\d+)\]</label>
				<value>Reg$8Drug$10WgtGrpSch$12Day</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/weightGroupDosing\[(\d+)\]/weightGroupDosingSchedule\[(\d+)\]/hour\[(\d+)\]</label>
				<value>Reg$8Drug$10WgtGrpSch$12Hour</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/weightGroupDosing\[(\d+)\]/weightGroupDosingSchedule\[(\d+)\]/weightGroupFrom\[(\d+)\]</label>
				<value>Reg$8Drug$10WgtGrpSch$12WgtMin</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/weightGroupDosing\[(\d+)\]/weightGroupDosingSchedule\[(\d+)\]/weightGroupTo\[(\d+)\]</label>
				<value>Reg$8Drug$10WgtGrpSch$12WgtMax</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/weightGroupDosing\[(\d+)\]/weightGroupDosingSchedule\[(\d+)\]/dose\[(\d+)\]</label>
				<value>Reg$8Drug$10WgtGrpSch$12Dose</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/weightGroupDosing\[(\d+)\]/weightGroupDosingSchedule\[(\d+)\]/doseUnit\[(\d+)\]</label>
				<value>Reg$8Drug$10WgtGrpSch$12DoseUnit</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/weightDosing\[(\d+)\]/weightDosingSchedule\[(\d+)\]/day\[(\d+)\]</label>
				<value>Reg$8Drug$10WgtSch$12Day</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/weightDosing\[(\d+)\]/weightDosingSchedule\[(\d+)\]/hour\[(\d+)\]</label>
				<value>Reg$8Drug$10WgtSch$12Hour</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/weightDosing\[(\d+)\]/weightDosingSchedule\[(\d+)\]/dose\[(\d+)\]</label>
				<value>Reg$8Drug$10WgtSch$12WeightMin</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/feeding\[(\d+)\]</label>
				<value>Reg$8Drug$10Foodintake</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/feedingOther\[(\d+)\]</label>
				<value>Reg$8Drug$10FoodintakeOther</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/fatPerMeal\[(\d+)\]</label>
				<value>Reg$8Drug$10FatPerMeal</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/readministeredOnVomitting\[(\d+)\]</label>
				<value>Reg$8Drug$10Readmin</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimens\[(\d+)\]/regimen\[(\d+)\]/drugs\[(\d+)\]/drug\[(\d+)\]/comments\[(\d+)\]</label>
				<value>Reg$8Drug$10Comments</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimenAllocation\[(\d+)\]/regimenAllocationMethod\[(\d+)\]</label>
				<value>RegAllocation</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/treatment\[(\d+)\]/regimenAllocation\[(\d+)\]/blinding\[(\d+)\]</label>
				<value>Blinding</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/followup\[(\d+)\]/duration\[(\d+)\]</label>
				<value>DayOfFollowup</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/followup\[(\d+)\]/feverMeasurement\[(\d+)\]</label>
				<value>FeverMeasurement</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/followup\[(\d+)\]/haemoglobinRecording\[(\d+)\]/haemoglobinRecordingType\[(\d+)\]</label>
				<value>HbRecording</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/microscopyStain\[(\d+)\]</label>
				<value>MicroStain</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/microscopyStainOther\[(\d+)\]</label>
				<value>MicroStainOther</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/asexualParasitemia\[(\d+)\]/asexualParasitemiaNegativeCount\[(\d+)\]</label>
				<value>AsexParaNegFields</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/asexualParasitemia\[(\d+)\]/asexualParasitemiaPositiveThickUnit\[(\d+)\]</label>
				<value>AsexParaThickUnits</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/asexualParasitemia\[(\d+)\]/asexualParasitemiaPositiveThickUnitOther\[(\d+)\]</label>
				<value>AsexParaThickUnitsOther</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/asexualParasitemia\[(\d+)\]/asexualParasitemiaPositiveThinUnit\[(\d+)\]</label>
				<value>AsexParaThinUnits</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/asexualParasitemia\[(\d+)\]/asexualParasitemiaPositiveThinUnitOther\[(\d+)\]</label>
				<value>AsexParaThinUnitsOther</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/sexualParasitemia\[(\d+)\]/sexualParasitemiaNegativeCount\[(\d+)\]</label>
				<value>SexParaNegFields</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/sexualParasitemia\[(\d+)\]/sexualParasitemiaPositiveThickUnit\[(\d+)\]</label>
				<value>SexParaThickUnits</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/sexualParasitemia\[(\d+)\]/sexualParasitemiaPositiveThickUnitOther\[(\d+)\]</label>
				<value>SexParaThickUnitsOther</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/sexualParasitemia\[(\d+)\]/sexualParasitemiaPositiveThinUnit\[(\d+)\]</label>
				<value>SexParaThinUnits</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/sexualParasitemia\[(\d+)\]/sexualParasitemiaPositiveThinUnitOther\[(\d+)\]</label>
				<value>SexParaThinUnitsOther</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/thickFilmCalculationOfParasitemia\[(\d+)\]/thickFilmFormula\[(\d+)\]</label>
				<value>ThickFilmFormula</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/thickFilmCalculationOfParasitemia\[(\d+)\]/thickFilmFormulaOther\[(\d+)\]</label>
				<value>ThinFilmFormula</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/thinFilmCalculationOfParasitemia\[(\d+)\]/thinFilmFormula\[(\d+)\]</label>
				<value>ThickFilmFormulaOther</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/thinFilmCalculationOfParasitemia\[(\d+)\]/thinFilmFormulaOther\[(\d+)\]</label>
				<value>ThinFilmFormulaOther</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/qualityControl\[(\d+)\]/internal\[(\d+)\]/percentageRereadBySecondMicroscopist\[(\d+)\]</label>
				<value>PercentSlidesRereadInt</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/qualityControl\[(\d+)\]/internal\[(\d+)\]/rereadSlideSelectionMechanism\[(\d+)\]</label>
				<value>SlidesRereadIntMech</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/qualityControl\[(\d+)\]/internal\[(\d+)\]/rereadSlideSelectionMechanismOther\[(\d+)\]</label>
				<value>SlidesRereadIntMechOther</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/qualityControl\[(\d+)\]/external\[(\d+)\]/percentageRereadBySecondMicroscopist\[(\d+)\]</label>
				<value>PercentSlidesRereadEx</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/qualityControl\[(\d+)\]/external\[(\d+)\]/rereadSlideSelectionMechanism\[(\d+)\]</label>
				<value>SlidesRereadExMech</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/microscopy\[(\d+)\]/qualityControl\[(\d+)\]/external\[(\d+)\]/rereadSlideSelectionMechanismOther\[(\d+)\]</label>
				<value>SlidesRereadExMechOther</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\[(\d+)\]/applicability\[(\d+)\]</label>
				<value>GenotypingRecrudReinfe</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\[(\d+)\]/applicable\[(\d+)\]/markers\[(\d+)\]/recrudescenceMarker\[(\d+)\]/numberOfMicroSatellites\[(\d+)\]</label>
				<value>GenotypMarker$9MicroSatels</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\[(\d+)\]/applicable\[(\d+)\]/markers\[(\d+)\]/recrudescenceMarker\[(\d+)\]/markerName\[(\d+)\]</label>
				<value>GenotypingMarker$9</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\[(\d+)\]/applicable\[(\d+)\]/markers\[(\d+)\]/recrudescenceMarker\[(\d+)\]/markerOther\[(\d+)\]</label>
				<value>GenotypingMarker$9Other</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\[(\d+)\]/applicable\[(\d+)\]/genotypingLaboratory\[(\d+)\]</label>
				<value>GenotypingLab</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\[(\d+)\]/applicable\[(\d+)\]/markerDiscriminantOpen\[(\d+)\]/markerDiscriminant\[(\d+)\]</label>
				<value>GenotypingMethod</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\[(\d+)\]/applicable\[(\d+)\]/markerDiscriminantOpen\[(\d+)\]/markerDiscriminantOther\[(\d+)\]</label>
				<value>GenotypingMethodOther</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\[(\d+)\]/applicable\[(\d+)\]/analysisProtocol\[(\d+)\]/mixedAllelesOpen\[(\d+)\]/mixedAlleles\[(\d+)\]</label>
				<value>MixedAllelesDefinition</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\[(\d+)\]/applicable\[(\d+)\]/analysisProtocol\[(\d+)\]/mixedAllelesOpen\[(\d+)\]/mixedAllelesOther\[(\d+)\]</label>
				<value>MixedAllelesDefinitionOther</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\[(\d+)\]/applicable\[(\d+)\]/analysisProtocol\[(\d+)\]/mixedAllelesOpen\[(\d+)\]/recrudescence\[(\d+)\]</label>
				<value>RecudesenceDefinition</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/clinical\[(\d+)\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\[(\d+)\]/applicable\[(\d+)\]/analysisProtocol\[(\d+)\]/mixedAllelesOpen\[(\d+)\]/reinfection\[(\d+)\]</label>
				<value>ReinfectionDefinition</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/criteria\[(\d+)\]/sampleSourceOpen\[(\d+)\]/sampleSource\[(\d+)\]</label>
				<value>MolSampleSource</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/criteria\[(\d+)\]/sampleSourceOpen\[(\d+)\]/sampleSourceOther\[(\d+)\]</label>
				<value>MolSampleSourceOther</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/criteria\[(\d+)\]/malariaStatus\[(\d+)\]</label>
				<value>MalariaDiseaseStatus</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/sample\[(\d+)\]/sampleTypeOpen\[(\d+)\]/sampleType\[(\d+)\]</label>
				<value>MolSampleType</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/sample\[(\d+)\]/sampleTypeOpen\[(\d+)\]/sampleTypeOther\[(\d+)\]</label>
				<value>MolSampleTypeOther</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/genotypedMarkers\[(\d+)\]/genotypedMarker\[(\d+)\]/genotypingMethodOpen\[(\d+)\]/genotypingMethod\[(\d+)\]</label>
				<value>MolMarker$7GenotypingMethod</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/genotypedMarkers\[(\d+)\]/genotypedMarker\[(\d+)\]/genotypingMethodOpen\[(\d+)\]/genotypingMethodOther\[(\d+)\]</label>
				<value>MolMarker$7GenotypMethodOther</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/genotypedMarkers\[(\d+)\]/genotypedMarker\[(\d+)\]/molecularMarkerFalciparumOpen\[(\d+)\]/molecularMarkerFalciparum\[(\d+)\]</label>
				<value>MolMarker$7Falciparum</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/genotypedMarkers\[(\d+)\]/genotypedMarker\[(\d+)\]/molecularMarkerFalciparumOpen\[(\d+)\]/molecularMarkerFalciparumOther\[(\d+)\]</label>
				<value>MolMarker$7FalciparumOther</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/genotypedMarkers\[(\d+)\]/genotypedMarker\[(\d+)\]/molecularMarkerVivaxOpen\[(\d+)\]/molecularMarkerVivax\[(\d+)\]</label>
				<value>MolMarker$7Vivax</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/genotypedMarkers\[(\d+)\]/genotypedMarker\[(\d+)\]/molecularMarkerVivaxOpen\[(\d+)\]/molecularMarkerVivaxOther\[(\d+)\]</label>
				<value>MolMarker$7VivaxOther</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/genotypedMarkers\[(\d+)\]/genotypedMarker\[(\d+)\]/molecularMarkerOvaleOpen\[(\d+)\]/molecularMarkerOvale\[(\d+)\]</label>
				<value>MolMarker$7Ovale</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/genotypedMarkers\[(\d+)\]/genotypedMarker\[(\d+)\]/molecularMarkerOvaleOpen\[(\d+)\]/molecularMarkerOvaleOther\[(\d+)\]</label>
				<value>MolMarker$7OvaleFill</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/genotypedMarkers\[(\d+)\]/genotypedMarker\[(\d+)\]/molecularMarkerMalariaeOpen\[(\d+)\]/molecularMarkerMalariae\[(\d+)\]</label>
				<value>MolMarker$7Malariae</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/genotypedMarkers\[(\d+)\]/genotypedMarker\[(\d+)\]/molecularMarkerMalariaeOpen\[(\d+)\]/molecularMarkerMalariaeOther\[(\d+)\]</label>
				<value>MolMarker$7MalariaeOther</value>
			</fieldLabelMapping>			
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/genotypedMarkers\[(\d+)\]/genotypedMarker\[(\d+)\]/molecularMarkerKnowlesiOpen\[(\d+)\]/molecularMarkerKnowlesi\[(\d+)\]</label>
				<value>MolMarker$7Knowlesi</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/genotypedMarkers\[(\d+)\]/genotypedMarker\[(\d+)\]/molecularMarkerKnowlesiOpen\[(\d+)\]/molecularMarkerKnowlesiOther\[(\d+)\]</label>
				<value>MolMarker$7KnowlesiOther</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/genotypedMarkers\[(\d+)\]/genotypedMarker\[(\d+)\]/sampleSelectionMethodOpen\[(\d+)\]/sampleSelectionMethod\[(\d+)\]</label>
				<value>Mol$7SampleSelectionMethod</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/genotypedMarkers\[(\d+)\]/genotypedMarker\[(\d+)\]/sampleSelectionMethodOpen\[(\d+)\]/sampleSelectionMethodOther\[(\d+)\]</label>
				<value>Mol$7SampleSelectnMethodOther</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/genotypedMarkers\[(\d+)\]/genotypedMarker\[(\d+)\]/sampleSelectionMethodOpen\[(\d+)\]/sampleSelectionProportion\[(\d+)\]</label>
				<value>Mol$7SampleSelectionProport</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/mixedResistanceAlleles\[(\d+)\]/mixedResistanceAllelesInclusion\[(\d+)\]</label>
				<value>MixedResistanceAllelesInclu</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/mixedResistanceAlleles\[(\d+)\]/mixedResistanceAllelesDesignation\[(\d+)\]</label>
				<value>MixedResistanceAllelesDesig</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/additionalGenotypicInformation\[(\d+)\]/sequencedLoci\[(\d+)\]/wholeGenomesSequenced\[(\d+)\]</label>
				<value>GenomeSeq</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/additionalGenotypicInformation\[(\d+)\]/sequencedLoci\[(\d+)\]/resistanceLociSequenced\[(\d+)\]</label>
				<value>ResistanceLociSeq</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/additionalGenotypicInformation\[(\d+)\]/sequencedLoci\[(\d+)\]/resistanceLoci\[(\d+)\]/resistanceLocus\[(\d+)\]/locusName\[(\d+)\]</label>
				<value>ResistanceLociSeq$9Name</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/additionalGenotypicInformation\[(\d+)\]/sequencedLoci\[(\d+)\]/otherLociGenotyped\[(\d+)\]</label>
				<value>OtherLociGenotyped</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/additionalGenotypicInformation\[(\d+)\]/sequencedLoci\[(\d+)\]/otherLoci\[(\d+)\]/otherLocus\[(\d+)\]/locusTypeOpen\[(\d+)\]/locusType\[(\d+)\]</label>
				<value>OtherLoci$9Type</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/additionalGenotypicInformation\[(\d+)\]/sequencedLoci\[(\d+)\]/otherLoci\[(\d+)\]/otherLocus\[(\d+)\]/locusTypeOpen\[(\d+)\]/locusTypeOther\[(\d+)\]</label>
				<value>OtherLoci$9TypeOther</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/additionalGenotypicInformation\[(\d+)\]/sequencedLoci\[(\d+)\]/otherLoci\[(\d+)\]/otherLocus\[(\d+)\]/locusName\[(\d+)\]</label>
				<value>OtherLoci$9Name</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/molecular\[(\d+)\]/additionalGenotypicInformation\[(\d+)\]/infectionComplexityEstimated\[(\d+)\]</label>
				<value>COIEstimated</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/analysisSite\[(\d+)\]</label>
				<value>analysis_site1</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/culture\[(\d+)\]/incubatorSystem\[(\d+)\]</label>
				<value>incubator_system</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/culture\[(\d+)\]/co2percentage\[(\d+)\]</label>
				<value>co2percentage</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/culture\[(\d+)\]/co2Other\[(\d+)\]</label>
				<value>co2other</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/culture\[(\d+)\]/o2percentage\[(\d+)\]</label>
				<value>o2percentage</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/culture\[(\d+)\]/o2Other\[(\d+)\]</label>
				<value>o2other</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/culture\[(\d+)\]/healthyErythrocytesSource\[(\d+)\]</label>
				<value>healthyErythocytes</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/culture\[(\d+)\]/hematocritpercent\[(\d+)\]</label>
				<value>hematocrit</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/culture\[(\d+)\]/bloodGroup\[(\d+)\]</label>
				<value>bloodgroup</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/drugSusceptibilityMedium\[(\d+)\]/medium\[(\d+)\]</label>
				<value>drug1susmedium</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/drugSusceptibilityMedium\[(\d+)\]/mediumOther\[(\d+)\]</label>
				<value>drug1susmedium_other</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/drugSusceptibilityMedium\[(\d+)\]/preparation\[(\d+)\]</label>
				<value>drug1susmedium_prepa</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/drugSusceptibilityMedium\[(\d+)\]/serum\[(\d+)\]</label>
				<value>drug1susmedium_serum</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/drugSusceptibilityMedium\[(\d+)\]/serum-finalConcentration\[(\d+)\]</label>
				<value>drug1susmedium_serumfinal</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/drugSusceptibilityMedium\[(\d+)\]/NaHCO3-finalConcentration\[(\d+)\]</label>
				<value>drug1susmedium_NaHCO3</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/drugSusceptibilityMedium\[(\d+)\]/hypoxantine\[(\d+)\]/hypoxantine-added\[(\d+)\]</label>
				<value>drug1susmedium_hypox</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/drugSusceptibilityMedium\[(\d+)\]/hypoxantine\[(\d+)\]/hypoxantine-finalConcentration\[(\d+)\]</label>
				<value>fconcentration_hypox</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/drugSusceptibilityMedium\[(\d+)\]/oroticAcid\[(\d+)\]/oroticAcid-added\[(\d+)\]</label>
				<value>drug1susmedium_oroticacid</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/drugSusceptibilityMedium\[(\d+)\]/oroticAcid\[(\d+)\]/oroticAcid-finalConcentration\[(\d+)\]</label>
				<value>fconcentration_orocticid</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/drugSusceptibilityMedium\[(\d+)\]/glucose\[(\d+)\]/glucose-added\[(\d+)\]</label>
				<value>drug1susmedium_gluc</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/drugSusceptibilityMedium\[(\d+)\]/glucose\[(\d+)\]/glucose-finalConcentration\[(\d+)\]</label>
				<value>fconcentration_gluc</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/drugSusceptibilityMedium\[(\d+)\]/antibioticTreatments\[(\d+)\]/antibioticTreatment\[(\d+)\]/antibioticOpen\[(\d+)\]/antibiotic\[(\d+)\]</label>
				<value>drug1susmedium_atb$8</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/drugSusceptibilityMedium\[(\d+)\]/antibioticTreatments\[(\d+)\]/antibioticTreatment\[(\d+)\]/antibioticOpen\[(\d+)\]/antibioticOther\[(\d+)\]</label>
				<value>drug1susmedium_atb$8other</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/drugSusceptibilityMedium\[(\d+)\]/antibioticTreatments\[(\d+)\]/antibioticTreatment\[(\d+)\]/antibioticFinalConcentration\[(\d+)\]</label>
				<value>fconcentration_atb$8</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/susceptibility\[(\d+)\]/timeOfIncubation\[(\d+)\]</label>
				<value>incubation_time</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/susceptibility\[(\d+)\]/susceptibilityMethod\[(\d+)\]</label>
				<value>drugsus_method</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/invitroDrugs\[(\d+)\]/invitroDrug\[(\d+)\]/molecule\[(\d+)\]</label>
				<value>drug$7</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/invitroDrugs\[(\d+)\]/invitroDrug\[(\d+)\]/solvent\[(\d+)\]</label>
				<value>solvent_drug$7</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/invitroDrugs\[(\d+)\]/invitroDrug\[(\d+)\]/solventFinalConcentration\[(\d+)\]</label>
				<value>drug$7_fconcentration_solvent</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/invitroDrugs\[(\d+)\]/invitroDrug\[(\d+)\]/providedByWwarn\[(\d+)\]</label>
				<value>drug$7_fromWWARN</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/invitroDrugs\[(\d+)\]/invitroDrug\[(\d+)\]/provider\[(\d+)\]</label>
				<value>drug$7_provider</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/platePreparationMethod\[(\d+)\]</label>
				<value>plateprepa_method</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/platesPreparationDate\[(\d+)\]</label>
				<value>plateprepa_date</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/plateBatches\[(\d+)\]/batch\[(\d+)\]/clone3D7ProvidedByWwarn\[(\d+)\]</label>
				<value>batch$7_clone3D7_fromWWARN</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/plateBatches\[(\d+)\]/batch\[(\d+)\]/clone3D7Provider\[(\d+)\]</label>
				<value>batch$7_clone3D7_provider</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/plateBatches\[(\d+)\]/batch\[(\d+)\]/batchesIncludedInRawData\[(\d+)\]</label>
				<value>batches$7in_rawdata</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/plateBatches\[(\d+)\]/batch\[(\d+)\]/preparationDate\[(\d+)\]</label>
				<value>batch$7_prepa_date</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/plateBatches\[(\d+)\]/batch\[(\d+)\]/parasitaemia3D7percentage\[(\d+)\]</label>
				<value>batch$7_parsitemia_%</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/invitro\[(\d+)\]/plateBatches\[(\d+)\]/batch\[(\d+)\]/ringFormingPercentage\[(\d+)\]</label>
				<value>batch$7_ringforming%</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/samples\[(\d+)\]/sample\[(\d+)\]/anticoagulent\[(\d+)\]</label>
				<value>Anticoagulant$7</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/samples\[(\d+)\]/sample\[(\d+)\]/centrifugeTime\[(\d+)\]</label>
				<value>TimeToCentrifuge$7</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/samples\[(\d+)\]/sample\[(\d+)\]/storages\[(\d+)\]/storage\[(\d+)\]/storageTemperature\[(\d+)\]</label>
				<value>Smpl$7TemperatureStorage$9</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/samples\[(\d+)\]/sample\[(\d+)\]/storages\[(\d+)\]/storage\[(\d+)\]/storageDuration\[(\d+)\]</label>
				<value>Smpl$7DuratnTemperatrStorage$9</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/samples\[(\d+)\]/sample\[(\d+)\]/storages\[(\d+)\]/storage\[(\d+)\]/storageDurationUnit\[(\d+)\]</label>
				<value>Smpl$7UnitStorageDuration$9</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/analytes\[(\d+)\]/analyte\[(\d+)\]/drugMeasured\[(\d+)\]</label>
				<value>NameDrugMetoboliteMeasured$7</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/analytes\[(\d+)\]/analyte\[(\d+)\]/lowerLoQ\[(\d+)\]</label>
				<value>LLOQ$7</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/analytes\[(\d+)\]/analyte\[(\d+)\]/sampleMatrixType\[(\d+)\]</label>
				<value>SampleMatrix$7</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/assayReferences\[(\d+)\]/assayReference\[(\d+)\]/referenceType\[(\d+)\]</label>
				<value>AssayMethodsReference$7Type</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/assayReferences\[(\d+)\]/assayReference\[(\d+)\]/url\[(\d+)\]</label>
				<value>AssayMethodsReference$7url</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/assayReferences\[(\d+)\]/assayReference\[(\d+)\]/upload\[(\d+)\]/uploadedUrl\[(\d+)\]</label>
				<value>AssayMethodsReference$7file</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/assayReferences\[(\d+)\]/assayReference\[(\d+)\]/doi\[(\d+)\]</label>
				<value>AssayMethodsReference$7doi</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/assayReferences\[(\d+)\]/assayReference\[(\d+)\]/note\[(\d+)\]</label>
				<value>AssayMethod$7Description</value>
			</fieldLabelMapping>
			<fieldLabelMapping deprecated="n">
				<label>/atom:entry\[(\d+)\]/atom:content\[(\d+)\]/study\[(\d+)\]/study-info\[(\d+)\]/pharmacology\[(\d+)\]/assayReferences\[(\d+)\]/assayReference\[(\d+)\]/assayValidated\[(\d+)\]</label>
				<value>AssayMethodsReference$7valid</value>
			</fieldLabelMapping>
	</fieldLabelMappings>

	let $entry4 := local:create-config-entry('Field label mappings','field-label-mappings',$values4)   
   
   
    return concat($entry1, '&#xD;', $entry2,'&#xD;', $entry3, '&#xD;', $entry4)
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
    
    
