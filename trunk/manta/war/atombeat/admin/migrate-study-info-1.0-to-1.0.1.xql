declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;

import module namespace response = "http://exist-db.org/xquery/response" ;

import module namespace config = "http://purl.org/atombeat/xquery/config" at "../config/shared.xqm" ;
import module namespace CONSTANT = "http://purl.org/atombeat/xquery/constants" at "../lib/constants.xqm" ;
import module namespace xutil = "http://purl.org/atombeat/xquery/xutil" at "../lib/xutil.xqm" ;
import module namespace atomsec = "http://purl.org/atombeat/xquery/atom-security" at "../lib/atom-security.xqm" ;
import module namespace atomdb = "http://purl.org/atombeat/xquery/atomdb" at "../lib/atomdb.xqm" ;
import module namespace ap = "http://purl.org/atombeat/xquery/atom-protocol" at "../lib/atom-protocol.xqm" ;
import module namespace common-protocol = "http://purl.org/atombeat/xquery/common-protocol" at "../lib/common-protocol.xqm" ;
import module namespace config-collections = "http://purl.org/atombeat/xquery/config-collections" at "collections.xqm" ;

(: Migration actions: :)
(: Adds "co2Other", "o2" and "o2Other" into the "culture" element in "invitro", after "co2". :)
(: Adds "platePreparationMethod" and "platesPreparationDate" into "invitro", after "drugs". :)
(: If there are batches in the "plateBatches" element of "invitro", then gives the content "batches" to the "platePreparationMethod" element. :)
(: See Milestone 1.0.x :)

declare variable $testdata {
<atom:entry xmlns:atom="http://www.w3.org/2005/Atom">
    <atom:id>https://www.wwarn.org/repository/atombeat/content/study-info/MGEXQ.atom</atom:id>
    <atom:published>2010-10-08T03:30:48.996+01:00</atom:published>
    <atom:updated>2010-10-08T03:46:45.34+01:00</atom:updated>
    <atom:author>
        <atom:email>lydaosorio@cideim.org.co</atom:email>
    </atom:author>
    <atom:title type="text"/>
    <atom:content type="application/vnd.chassis-manta+xml">
        <study-info profile="http://www.cggh.org/2010/chassis/manta/1.0">
            <start>2000-04-27</start>
            <end>2004-02-27</end>
            <sites>
                <site>
                    <country>CO</country>
                    <region>Pacific coast</region>
                    <district>Choco</district>
                    <locality>Quibdo</locality>
                    <lookupAddress>Quibdo Choco Pacific coast COLOMBIA</lookupAddress>
                    <siteCode/>
                    <testingDelay/>
                    <anticoagulant/>
                    <transportAndStorageTemperature/>
                    <latitude>5.698323611780863</latitude>
                    <longitude>76Â°40</longitude>
                    <transmissionIntensity>
                        <annualParasitologicalIncidence>9/1000 ihn</annualParasitologicalIncidence>
                        <annualParasitologicalIncidenceYear>2005</annualParasitologicalIncidenceYear>
                        <parasitePrevalence>0</parasitePrevalence>
                        <parasitePrevalenceYear>2001</parasitePrevalenceYear>
                        <transmissionIntensityAgeFrom>0</transmissionIntensityAgeFrom>
                        <transmissionIntensityAgeFromUnits/>
                        <transmissionIntensityAgeTo>99999</transmissionIntensityAgeTo>
                        <transmissionIntensityAgeToUnits/>
                        <entomologicalInoculationRate/>
                        <entomologicalInoculationRateYear/>
                        <seasonalTransmission/>
                        <transmissionMonths/>
                    </transmissionIntensity>
                </site>
            </sites>
            <modules>clinical</modules>
            <pathogens>pfalciparum</pathogens>
            <inclusionExclusionCriteria>
                <age>
                    <maxAge>65</maxAge>
                    <maxAgeUnits/>
                    <minAge>1</minAge>
                    <minAgeUnits/>
                </age>
                <parasitaemia>
                    <minParasitaemia>250</minParasitaemia>
                    <maxParasitaemia>50000</maxParasitaemia>
                </parasitaemia>
                <includeMixedInfections>false</includeMixedInfections>
                <excludeIfPriorAntimalarials>true</excludeIfPriorAntimalarials>
                <priorAntimalarialsExclusion>
                    <priorAntimalarials>any</priorAntimalarials>
                    <priorAntimalarialsDetermination>history</priorAntimalarialsDetermination>
                    <priorAntimalarialsHistoryWeeks/>
                </priorAntimalarialsExclusion>
                <pregnancy>exclude</pregnancy>
                <treatmentReason/>
                <otherCriteria>no able to return to follow up</otherCriteria>
            </inclusionExclusionCriteria>
            <clinical>
                <treatment>
                    <regimens>
                        <regimen>
                            <regimenName>Amodiaquine plus placebo</regimenName>
                            <regimenSupervision>full</regimenSupervision>
                            <regimenUrl/>
                            <drugs>
                                <drug>
                                    <name>Camoquin</name>
                                    <activeIngredients>
                                        <activeIngredient>
                                            <activeIngredientName>amodiaquine</activeIngredientName>
                                            <activeIngredientMgPerDose>250</activeIngredientMgPerDose>
                                        </activeIngredient>
                                    </activeIngredients>
                                    <administrationRoute/>
                                    <manufacturer/>
                                    <batchNumber/>
                                    <expiryDate/>
                                    <drugStorage/>
                                    <drugDosingDeterminant/>
                                    <ageDosing>
                                        <ageDosingSchedule>
                                            <day/>
                                            <hour/>
                                            <ageFrom/>
                                            <ageTo/>
                                            <dose/>
                                            <doseUnit/>
                                        </ageDosingSchedule>
                                    </ageDosing>
                                    <wGroupDosing>
                                        <wGroupDosingSchedule>
                                            <day/>
                                            <hour/>
                                            <wGroupFrom/>
                                            <wGroupTo/>
                                            <dose/>
                                            <doseUnit/>
                                        </wGroupDosingSchedule>
                                    </wGroupDosing>
                                    <weightDosing>
                                        <weightDosingSchedule>
                                            <day/>
                                            <hour/>
                                            <dose/>
                                        </weightDosingSchedule>
                                    </weightDosing>
                                    <feeding/>
                                    <fatPerMeal/>
                                    <feedingOther/>
                                    <readministeredOnVomitting/>
                                    <comments/>
                                </drug>
                            </drugs>
                        </regimen>
                    </regimens>
                    <regimenAllocation>
                        <regimenAllocationMethod/>
                        <blinding/>
                        <randomisationProportion/>
                    </regimenAllocation>
                </treatment>
                <followup>
                    <duration/>
                    <feverMeasurement/>
                    <haemoglobinRecording>
                        <haemoglobinRecordingType/>
                    </haemoglobinRecording>
                </followup>
                <microscopy>
                    <microscopyStain/>
                    <microscopyStainOther/>
                    <asexualParasitemia>
                        <asexualParasitemiaNegativeCount/>
                        <asexualParasitemiaPositiveThickUnit/>
                        <asexualParasitemiaPositiveThickUnitOther/>
                        <asexualParasitemiaPositiveThinUnit/>
                        <asexualParasitemiaPositiveThinUnitOther/>
                    </asexualParasitemia>
                    <sexualParasitemia>
                        <sexualParasitemiaNegativeCount/>
                        <sexualParasitemiaPositiveThickUnit/>
                        <sexualParasitemiaPositiveThickUnitOther/>
                        <sexualParasitemiaPositiveThinUnit/>
                        <sexualParasitemiaPositiveThinUnitOther/>
                    </sexualParasitemia>
                    <thickFilmCalculationOfParasitemia>
                        <thickFilmFormula/>
                        <thickFilmFormulaOther/>
                    </thickFilmCalculationOfParasitemia>
                    <thinFilmCalculationOfParasitemia>
                        <thinFilmFormula/>
                        <thinFilmFormulaOther/>
                    </thinFilmCalculationOfParasitemia>
                    <qualityControl>
                        <internal>
                            <percentageRereadBySecondMicroscopist/>
                            <rereadSlideSelectionMechanism/>
                            <rereadSlideSelectionMechanismOther/>
                        </internal>
                        <external>
                            <percentageRereadBySecondMicroscopist/>
                            <rereadSlideSelectionMechanism/>
                            <rereadSlideSelectionMechanismOther/>
                        </external>
                    </qualityControl>
                </microscopy>
                <geneotypingToDistinguishBetweenRecrudescenceAndReinfection>
                    <applicability/>
                    <applicable>
                        <markers>
                            <recrudescenceMarker>
                                <markerName/>
                                <markerOther/>
                                <numberOfMicroSatellites/>
                            </recrudescenceMarker>
                        </markers>
                        <genotypingLaboratory/>
                        <markerDiscriminant/>
                        <markerDiscriminantOther/>
                        <analysisProtocol>
                            <mixedAllelesOpen>
                                <mixedAlleles/>
                                <mixedAllelesOther/>
                                <recrudescence/>
                                <reinfection/>
                            </mixedAllelesOpen>
                        </analysisProtocol>
                    </applicable>
                </geneotypingToDistinguishBetweenRecrudescenceAndReinfection>
                <recrudescenceAndReinfection/>
            </clinical>
            <molecular>
                <criteria>
                    <sampleSourceOpen>
                        <sampleSource/>
                        <sampleSourceOther/>
                    </sampleSourceOpen>
                    <malariaStatus/>
                </criteria>
                <sample>
                    <sampleTypeOpen>
                        <sampleType/>
                        <sampleTypeOther/>
                        <wholeBloodSource/>
                        <dateCultureIsolated/>
                    </sampleTypeOpen>
                </sample>
                <genotypedMarkers/>
                <mixedResistanceAlleles>
                    <mixedResistanceAllelesInclusion/>
                    <mixedResistanceAllelesDesignation/>
                </mixedResistanceAlleles>
                <additionalGenotypicInformation>
                    <sequencedLoci>
                        <wholeGenomesSequenced/>
                        <resistanceLociSequenced/>
                        <resistanceLoci>
                            <resistanceLocus>
                                <locusTypeOpen>
                                    <locusType/>
                                    <locusTypeOther/>
                                </locusTypeOpen>
                                <locusName/>
                            </resistanceLocus>
                        </resistanceLoci>
                        <otherLociGenotyped/>
                        <otherLoci>
                            <otherLocus>
                                <locusTypeOpen>
                                    <locusType/>
                                    <locusTypeOther/>
                                </locusTypeOpen>
                                <locusName/>
                            </otherLocus>
                        </otherLoci>
                    </sequencedLoci>
                    <infectionComplexityEstimated/>
                    <infectionComplexityEstimationlociOpen>
                        <infectionComplexityEstimationloci/>
                        <infectionComplexityEstimationlociOther/>
                    </infectionComplexityEstimationlociOpen>
                </additionalGenotypicInformation>
            </molecular>
            <invitro>
                <inclusionCriteria/>
                <analysisSite/>
                <culture>
                    <incubatorSystem/>
                    <co2/>
                    <healthyErythrocytesSource/>
                    <hematocrit/>
                    <bloodGroup/>
                </culture>
                <drugSusceptibilityMedium>
                    <medium/>
                    <mediumOther/>
                    <preparation/>
                    <serum/>
                    <serum-finalConcentration/>
                    <NaHCO3-finalConcentration/>
                    <hypoxantine>
                        <hypoxantine-added/>
                        <hypoxantine-finalConcentration/>
                    </hypoxantine>
                    <oroticAcid>
                        <oroticAcid-added/>
                        <oroticAcid-finalConcentration/>
                    </oroticAcid>
                    <glucose>
                        <glucose-added/>
                        <glucose-finalConcentration/>
                    </glucose>
                    <antibioticTreatments/>
                </drugSusceptibilityMedium>
                <susceptibility>
                    <timeOfIncubation/>
                    <susceptibilityMethod/>
                </susceptibility>
                <drugs/>
                <plateBatches/>
            </invitro>
            <pharmacology>
                <samples>
                    <sample>
                        <numberPlanned/>
                        <anticoagulent/>
                        <centrifugeTime/>
                        <storages>
                            <storage>
                                <storageTemperature/>
                                <storageDuration/>
                                <storageDurationUnit/>
                            </storage>
                        </storages>
                    </sample>
                </samples>
                <analytes>
                    <analyte>
                        <drugMeasured/>
                        <lowerLoQ/>
                        <sampleMatrixType/>
                    </analyte>
                </analytes>
                <assayReferences>
                    <assayReference>
                        <referenceType>url</referenceType>
                        <url/>
                        <doi/>
                        <upload>
                            <uploadedUrl/>
                        </upload>
                        <note/>
                        <assayValidated/>
                    </assayReference>
                </assayReferences>
            </pharmacology>
        </study-info>
    </atom:content>
    <ar:comment xmlns:ar="http://purl.org/atompub/revision/1.0">
        <atom:author>
            <atom:email>lydaosorio@cideim.org.co</atom:email>
        </atom:author>
        <atom:updated>2010-10-08T03:46:45.34+01:00</atom:updated>
        <atom:summary/>
    </ar:comment>
    <atom:link rel="self" type="application/atom+xml;type=entry" href="https://www.wwarn.org/repository/atombeat/content/study-info/MGEXQ.atom"/>
    <atom:link rel="edit" type="application/atom+xml;type=entry" href="https://www.wwarn.org/repository/atombeat/content/study-info/MGEXQ.atom"/>
    <atom:link rel="http://www.cggh.org/2010/chassis/terms/originStudy" href="https://www.wwarn.org/repository/atombeat/content/studies/MGEXQ.atom" type="application/atom+xml;type=entry"/>
    <atom:link rel="http://purl.org/atombeat/rel/security-descriptor" href="https://www.wwarn.org/repository/atombeat/security/study-info/MGEXQ.atom" type="application/atom+xml;type=entry"/>
    <atom:link rel="history" href="https://www.wwarn.org/repository/atombeat/history/study-info/MGEXQ.atom" type="application/atom+xml;type=feed"/>
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
    let $study-infos := local:get-content()
    let $study-infos-v1-0 := local:get-old-versioned-content($study-infos) 
    let $study-infos-v1-0-1 := local:get-new-versioned-content($study-infos)
    
    return
    
        <html>
            <head>
                <title>Data Migration - Study Info v1.0 to v1.0.1</title>
            </head>
            <body>
                <h1>Data Migration - Study Info v1.0 to v1.0.1</h1>
                <p>Total number of entries in <a href="../content/study-info">Study Info</a> collection: <strong>{ count( $study-infos ) }</strong></p>
                <p>Number of entries in <a href="../content/study-info">Study Info</a> collection using v1.0 profile: <strong>{ count( $study-infos-v1-0 ) }</strong></p>
                <p>Number of entries in <a href="../content/study-info">Study Info</a> collection using v1.0.1 profile: <strong>{ count( $study-infos-v1-0-1 ) }</strong></p>
                <p>
                    <form method="post" action="">
                        Test <input type="checkbox" name="testing" checked="checked" />
                        <input type="submit" value="Migrate Data"></input>
                    </form>
                    <form method="get" action="">
                        <input type="submit" value="Refresh"></input>
                    </form>
                </p>
                <pre><code>
                {$content}
                </code></pre>
            </body>
        </html>
};

declare function local:get-content() as element( atom:entry )* {
  let $testing := request:get-parameter("testing", "no")
    let $content := if ($testing = "no") then
        let $ret := atomdb:retrieve-members( "/study-info" , false() )
        return $ret
    else
        let $ret := xmldb:xcollection( 'test' )/atom:entry (: not recursive :)
        return $ret
     
   return $content
};

declare function local:get-new-versioned-content($study-infos) as element( atom:entry )* {
    
    let $ret := $study-infos[atom:content/study-info/@profile='http://www.cggh.org/2010/chassis/manta/1.0.1']
    return $ret
};

declare function local:get-old-versioned-content($study-infos) as element( atom:entry )* {
    
    let $ret := $study-infos[atom:content/study-info/@profile='http://www.cggh.org/2010/chassis/manta/1.0']
    return $ret
};

declare function local:do-modifications($study-infos-v1-0) as element( atom:entry )*
{
    let $new := local:modify-nodes($study-infos-v1-0)
     
    let $ret := local:save-changes($new)
      
    (: Need to get the updated data from the db :)
    let $all := local:get-content()
    let $content := local:get-old-versioned-content($all)
    
   let $new-content := 
        for $v1-0 in $content
        return 
            <atom:entry>
            {
                $v1-0/attribute::* ,
                for $e in $v1-0/child::* 
                return
                    if (
                        local-name( $e ) = $CONSTANT:ATOM-CONTENT
                        and namespace-uri( $e ) = $CONSTANT:ATOM-NSURI
                    )
                    then 
                        <atom:content>
                        {
                            $e/attribute::* ,
                            local:migrate-study-info( $e/study-info )
                        }
                        </atom:content>
                    else $e
            }
            </atom:entry>

    return $new-content
};

declare function local:save-changes($study-infos-v1-0-1)
{
let $testing := request:get-parameter("testing", "no")
    let $content := if ($testing = "no") then
      let $migrated :=     
        for $v1-0-1 in $study-infos-v1-0-1 
        let $path-info := atomdb:edit-path-info( $v1-0-1 )
        return atomdb:update-member( $path-info , $v1-0-1 )
       return $migrated
    else 
    for $entry in $study-infos-v1-0-1 
        let $member-path-info := $entry/atom:link[@rel='edit']/@href
        let $groups := text:groups( $member-path-info , "^(.*)/([^/]+)$" )
		let $collection-path-info := $groups[2]
		let $entry-resource-name := $groups[3]
        let $entry-doc-db-path := xmldb:store( 'test' , $entry-resource-name , $entry , $CONSTANT:MEDIA-TYPE-ATOM )
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

(: update works directly against the database - not on in memory fragments so this doesn't behave as expected :( :)
declare function local:modify-nodes($study-infos-v1-0) as element( atom:entry )*
{
   let $study-infos-v1-0-1 := 
        for $v1-0 in $study-infos-v1-0
            let $ren := update rename $v1-0//wGroupDosing as 'weightGroupDosing'
            let $ren1 := update rename $v1-0//wGroupDosingSchedule as 'weightGroupDosingSchedule' 
            let $ren2 := update rename $v1-0//wGroupFrom as 'weightGroupFrom' 
            let $ren3 := update rename $v1-0//wGroupTo as 'weightGroupTo'
            let $ren4 := update rename $v1-0//co2 as 'co2percentage'
            let $ren5 := update rename $v1-0//hematocrit as 'hematocritpercentage'
            let $ren6 := update rename $v1-0//parasitaemia3D7 as 'parasitaemia3D7percentage'
            let $ren7 := update rename $v1-0//ringForms as 'ringFormingPercentage'
            let $del := update delete $v1-0//clinical/recrudescenceAndReinfection
            let $del1 := update delete $v1-0//invitro/inclusionCriteria
            let $del2 := update delete $v1-0//pharmacology/samples/sample/numberPlanned
            let $del3 := update delete $v1-0//pharmacology/analytes/analyte[./drugMeasured/text()='AL']
            let $del4 := update delete $v1-0//invitro/drugs/drug[./molecule/text()="AL"]
            let $rep1 := update replace $v1-0//drugMeasured[. = "SP"] with <drugMeasured>SX</drugMeasured>
            let $rep2 := update replace $v1-0//drugMeasured[. = "PPQ"] with <drugMeasured>PQ</drugMeasured>
            let $rep3 := update replace $v1-0//molecule[. = "SP"] with <molecule>SX</molecule>
            let $rep2 := update replace $v1-0//molecule[. = "PPQ"] with <molecule>PQ</molecule>
            let $tmp := update delete $v1-0//studyInfoStatus
            return update insert <studyInfoStatus>new</studyInfoStatus> preceding $v1-0//start

     
    return $study-infos-v1-0-1
    
};

declare function local:migrate-study-info( $study-info as element( study-info ) ) as element( study-info )
{
    <study-info profile="http://www.cggh.org/2010/chassis/manta/1.0.1">
    {
        for $study-info-child in $study-info/child::*
        return
            if ( local-name( $study-info-child ) = "invitro" )
            then
                <invitro>
                {
                    $study-info-child/attribute::* ,
                    for $invitro-child in $study-info-child/child::*
                    let $platePreparationMethod-element :=
                        if ( $study-info-child/plateBatches[not(node())] )
                        then <platePreparationMethod/>
                        else <platePreparationMethod>batches</platePreparationMethod>
                    let $modified-invitro-child-insert-after-drugs := ($invitro-child, $platePreparationMethod-element, <platesPreparationDate/>)
                    return 
                        if ( local-name( $invitro-child ) = "culture" )
                        then
                            <culture>
                            {
                                $invitro-child/attribute::* ,
                                for $culture-child in $invitro-child/child::*
                                let $modified-culture-child-insert-after-co2 := ($culture-child, <co2Other/>, <o2percentage/>, <o2Other/>)
                                return
                                    if ( local-name( $culture-child ) = "co2" )
                                    then $modified-culture-child-insert-after-co2
                                    else $culture-child
                             }
                             </culture>
                        else
                            if ( local-name( $invitro-child ) = "drugs" )
                            then $modified-invitro-child-insert-after-drugs
                            else $invitro-child
                }
                </invitro>
            else $study-info-child
    }
    </study-info>
};

declare function local:check-changes() as item() *{
  let $all := local:get-content()
 let $old := local:get-old-versioned-content($all)
 let $new :=  local:get-new-versioned-content($all)
 let $summary := concat('Old studies:',count($old),' New studies:',count($new),'\n')
 let $ret := for $m in $new 
         let $out := if (count($m//co2) > 0) then 
             let $msg := "Failed to change co2"
             return $msg
          else
             let $msg := "Changed co2"
             return $msg
         let $new := concat($summary,$out)
         return $new
    return concat($summary,$ret)
};
declare function local:do-migration() {
  
    let $all := local:get-content()
    let $content := local:get-old-versioned-content($all)
    
    let $new-content := local:do-modifications($content)
     
    let $ret := local:save-changes($new-content)
        
    let $testing := request:get-parameter("testing", "no")
      
    let $saved := if ($testing != "no") then 
        let $ret := local:check-changes()
        return $ret
      else
        let $ret := ''
        return $ret
        
    return local:do-post($saved)
};

let $login := xmldb:login( "/" , "admin" , "" )

let $testing := request:get-parameter("testing", "no")
    let $content := if ($testing = "no") then
        let $ret := ''
        return $ret
    else
 (:   let $testdata :=doc('testdata-study-info-v-1.0.atom') :)
let $collection := xmldb:create-collection("xmldb:exist:///db", "test"),
    $doc := local:save-changes($testdata)
    return $collection
    
return 

    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get()
    
    else if ( request:get-method() = $CONSTANT:METHOD-POST ) 
    
    then local:do-migration()
    
    else common-protocol:do-method-not-allowed( "/admin/migrate-study-info-1.0-to-1.0.1.xql" , ( "GET" , "POST" ) )
    
    
