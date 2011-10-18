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

declare variable $testdata {
<atom:entry xmlns:atom="http://www.w3.org/2005/Atom" xmlns:manta="http://www.cggh.org/2010/chassis/manta/xmlns">
        <atom:id xmlns:atombeat="http://purl.org/atombeat/xmlns">http://localhost:8080/repository/service/content/studies/MGEXQ</atom:id>
        <atom:published xmlns:atombeat="http://purl.org/atombeat/xmlns">2010-10-08T03:30:48.996+01:00</atom:published>
        <atom:updated xmlns:atombeat="http://purl.org/atombeat/xmlns">2011-04-13T14:55:33.543+01:00</atom:updated>
        <atom:author xmlns:atombeat="http://purl.org/atombeat/xmlns">
            <atom:email>lydaosorio@cideim.org.co</atom:email>
        </atom:author>
        <atom:title xmlns:atombeat="http://purl.org/atombeat/xmlns" type="text">Efficacy, Effect on Gametocytes and Tolerability of the Addition of Artesunate to Amodiaquine in Colombia</atom:title>
        <atom:content xmlns:atombeat="http://purl.org/atombeat/xmlns" type="application/vnd.chassis-manta+xml">
            <study profile="http://www.cggh.org/2010/chassis/manta/1.5">
                <registrant-has-agreed-to-the-terms>yes</registrant-has-agreed-to-the-terms>
                <study-is-published>Yes</study-is-published>
                <explorer-display>notset</explorer-display>
                <publications>
                    <publication>
                        <publication-title/>
                        <pmid>17328806</pmid>
                        <publication-references>
                            <publication-reference type="url">http://www.malariajournal.com/content/6/1/25</publication-reference>
                        </publication-references>
                    </publication>
                </publications>
                <acknowledgements>
                    <person>
                        <first-name/>
                        <middle-name/>
                        <family-name/>
                        <email-address/>
                        <institution>COLCIENCIAS</institution>
                        <person-is-contactable/>
                    </person>
                    <person>
                        <first-name/>
                        <middle-name/>
                        <family-name/>
                        <email-address/>
                        <institution>TDR/WHO</institution>
                        <person-is-contactable/>
                    </person>
                    <person>
                        <first-name/>
                        <middle-name/>
                        <family-name/>
                        <email-address/>
                        <institution>DASALUD-CHOCO</institution>
                        <person-is-contactable/>
                    </person>
                    <person>
                        <first-name>Rubiela</first-name>
                        <middle-name/>
                        <family-name>Giraldo</family-name>
                        <email-address/>
                        <institution/>
                        <person-is-contactable/>
                    </person>
                    <person>
                        <first-name>Maria </first-name>
                        <middle-name>Helena</middle-name>
                        <family-name>Chacon</family-name>
                        <email-address/>
                        <institution/>
                        <person-is-contactable/>
                    </person>
                    <person>
                        <first-name>Diego</first-name>
                        <middle-name>Fernando</middle-name>
                        <family-name>Echeverry</family-name>
                        <email-address/>
                        <institution/>
                        <person-is-contactable/>
                    </person>
                    <institution-ack>
                        <institution-name/>
                        <institution-websites/>
                    </institution-ack>
                </acknowledgements>
                <curator-notes/>
                <study-status>wait-external</study-status>
                <modules>clinical</modules>
                <study-info>
                    <studyInfoStatus/>
                    <start/>
                    <end/>
                    <sites>
                        <site>
                            <country/>
                            <region/>
                            <district/>
                            <locality/>
                            <lookupAddress/>
                            <siteCode/>
                            <testingDelay/>
                            <anticoagulant/>
                            <transportAndStorageTemperature/>
                            <latitude/>
                            <longitude/>
                            <transmissionIntensity>
                                <annualParasitologicalIncidence/>
                                <annualParasitologicalIncidenceYear/>
                                <parasitePrevalence/>
                                <parasitePrevalenceYear/>
                                <transmissionIntensityAgeFrom/>
                                <transmissionIntensityAgeFromUnits/>
                                <transmissionIntensityAgeTo/>
                                <transmissionIntensityAgeToUnits/>
                                <entomologicalInoculationRate/>
                                <entomologicalInoculationRateYear/>
                                <seasonalTransmission/>
                                <transmissionMonths/>
                            </transmissionIntensity>
                        </site>
                    </sites>
                    <pathogens/>
                    <inclusionExclusionCriteria>
                        <age>
                            <maxAge/>
                            <maxAgeUnits/>
                            <minAge/>
                            <minAgeUnits/>
                        </age>
                        <parasitaemia>
                            <minParasitaemia/>
                            <maxParasitaemia/>
                        </parasitaemia>
                        <includeMixedInfections/>
                        <excludeIfPriorAntimalarials/>
                        <priorAntimalarialsExclusion>
                            <priorAntimalarials>PQP AQ</priorAntimalarials>
                            <priorAntimalarialsDetermination/>
                            <priorAntimalarialsHistoryWeeks/>
                        </priorAntimalarialsExclusion>
                        <pregnancy/>
                        <treatmentReason/>
                        <otherCriteria/>
                    </inclusionExclusionCriteria>
                    <clinical>
                        <treatment>
                            <regimens>
                                <regimen>
                                    <regimenName/>
                                    <regimenSupervision/>
                                    <regimenUrl/>
                                    <drugs/>
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
                            </sexualParasitemia><!--   separateSpeciesCounts / -->
                            <thickFilmCalculationOfParasitemia>
                                <thickFilmFormula/>
                                <thickFilmFormulaOther/><!-- 
                        <WBCSource />
                        <WBCAssumed />
                        <thickFilmCalculationOfParasitemiaOther />
             -->
                            </thickFilmCalculationOfParasitemia>
                            <thinFilmCalculationOfParasitemia>
                                <thinFilmFormula/>
                                <thinFilmFormulaOther/><!-- 
                        <HtcSource />
                        <HtcAssumed />
                        <thinFilmCalculationOfParasitemiaOther />
             -->
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
                                <markerDiscriminantOpen>
                                    <markerDiscriminant/>
                                    <markerDiscriminantOther/>
                                </markerDiscriminantOpen>
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
                        <analysisSite/>
                        <culture>
                            <incubatorSystem/>
                            <co2percentage/>
                            <co2Other/>
                            <o2percentage/>
                            <o2Other/>
                            <healthyErythrocytesSource/>
                            <hematocritpercent/>
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
                        <drugs>
                            <invitroDrug>
                                <molecule>PQP</molecule>
                                <solvent>distilled water</solvent>
                                <solventFinalConcentration>3200nM</solventFinalConcentration>
                                <providedByWwarn>false</providedByWwarn>
                                <provider>Sigma</provider>
                            </invitroDrug>
                        </drugs>
                        <platePreparationMethod/>
                        <platesPreparationDate/>
                        <plateBatches/>
                    </invitro>
                    <pharmacology>
                        <samples>
                            <sample>
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
                                <drugMeasured>PQP</drugMeasured>
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
                <atombeat:group id="GROUP_ADMINISTRATORS">
                    <atombeat:member>lydaosorio@cideim.org.co</atombeat:member>
                    <atombeat:member>claribel_murillo@cideim.org.co</atombeat:member>
                </atombeat:group>
            </study>
        </atom:content>
        <ar:comment xmlns:ar="http://purl.org/atompub/revision/1.0" xmlns:atombeat="http://purl.org/atombeat/xmlns">
            <atom:author>
                <atom:email>clarissa.moreira@wwarn.org</atom:email>
            </atom:author>
            <atom:updated>2011-04-13T14:55:33.543+01:00</atom:updated>
            <atom:summary/>
        </ar:comment>
        <atom:link rel="self" type="application/atom+xml;type=entry" href="https://www.wwarn.org/repository/atombeat/content/study-info/MGEXQ"/>
    <atom:link rel="edit" type="application/atom+xml;type=entry" href="https://www.wwarn.org/repository/atombeat/content/study-info/MGEXQ"/>
    <atom:link rel="http://www.cggh.org/2010/chassis/terms/originStudy" href="https://www.wwarn.org/repository/atombeat/content/studies/MGEXQ" type="application/atom+xml;type=entry"/>
    <atom:link rel="http://purl.org/atombeat/rel/security-descriptor" href="https://www.wwarn.org/repository/atombeat/security/study-info/MGEXQ" type="application/atom+xml;type=entry"/>
    <atom:link rel="history" href="https://www.wwarn.org/repository/atombeat/history/study-info/MGEXQ" type="application/atom+xml;type=feed"/>
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
    let $old-study-infos := local:get-old-versioned-content($study-infos) 
    let $new-study-infos := local:get-new-versioned-content($study-infos)
    
    return
    
        <html>
            <head>
                <title>Data Migration - Study Info v1.5.1 to v2.0</title>
            </head>
            <body>
                <h1>Data Migration - Study v1.5.1 to v2.0</h1>
                <p>Total number of entries in <a href="../content/studies">Study Info</a> collection: <strong>{ count( $study-infos ) }</strong></p>
                <p>Number of entries in <a href="../content/study-info">Study Info</a> collection using v1.5.1 profile: <strong>{ count( $old-study-infos ) }</strong></p>
                <p>Number of entries in <a href="../content/study-info">Study Info</a> collection using v2.0 profile: <strong>{ count( $new-study-infos ) }</strong></p>
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

declare function local:get-content() as element( atom:entry )* {
  let $testing := request:get-parameter("testing", "no")
    let $content := if ($testing = "no") then
        let $ret := atomdb:retrieve-members( "/studies" , false() )
        return $ret
    else
        let $ret := xmldb:xcollection( 'test' )/atom:entry (: not recursive :)
        return $ret
     
   return $content
};

declare function local:get-new-versioned-content($study-infos) as element( atom:entry )* {
    
    let $ret := $study-infos[atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/2.0']
    return $ret
};

declare function local:get-old-versioned-content($study-infos) as element( atom:entry )* {
    
    let $ret := $study-infos[atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/1.5.1']
    return $ret
};

declare function local:do-modifications($old-study-infos) as element( atom:entry )*
{
    let $new := local:modify-nodes($old-study-infos)
     
   
    return $new
};

declare function local:save-changes($new-study-infos)
{

    let $content := 
    for $entry in $new-study-infos 
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
declare function local:modify-nodes($old-study-infos) as element( atom:entry )*
{
   
   let $new-study-infos := 
        for $old in $old-study-infos
            let $profile := update replace $old//study/@profile with "http://www.cggh.org/2010/chassis/manta/2.0"
            let $drug := update replace $old//drugMeasured[. = "PQP"] with <drugMeasured>PIP</drugMeasured>
            let $drug1 := update replace $old//invitroDrug/molecule[. = "PQP"] with <molecule>PIP</molecule>
            let $ins := update insert <institutions>{$old//institution-ack}</institutions> into $old//acknowledgements
            let $del := update delete $old//acknowledgements/institution-ack
            let $prior := replace($old//priorAntimalarials, 'PQP', 'PIP')
            let $pa := update insert attribute selected {$prior} into $old//priorAntimalarials
            let $pa := update delete $old//priorAntimalarials/text()
            let $drugs := tokenize($prior,'\s+')
            for $d in $drugs
                let $dt := update insert <drugTaken>{$d}</drugTaken> into $old//priorAntimalarials
            
        return $drug

    
     
    return $new-study-infos
    
};



declare function local:check-changes() as item() *{
  let $all := local:get-content()
 let $old := local:get-old-versioned-content($all)
 let $new :=  local:get-new-versioned-content($all)
 let $summary := concat('Old studies:',count($old),' New studies:',count($new),'&#xD;')
 let $ret := for $m in $new 
         let $out := if (count($m//analytes/analyte/drugMeasured[. = 'PQP']) > 0 or count($m//analytes/analyte/drugMeasured[. = 'PIP']) = 0) then 
             let $msg := "Failed to change PQP to PIP"
             return $msg
          else
             let $msg := "Changed PQP to PIP"
             return $msg
         let $new := concat($summary,$out,'&#xD;')
          let $out := if (count($m//invitroDrug/molecule[. = 'PQP']) > 0 or count($m//invitroDrug/molecule[. = 'PIP']) = 0) then 
             let $msg := "Failed to change PQP to PIP"
             return $msg
          else
             let $msg := "Changed PQP to PIP"
             return $msg
         let $output := concat($new,$out,'&#xD;')
         return $output
    return $ret
};
declare function local:do-migration() {
  
    let $all := local:get-content()
    let $content := local:get-old-versioned-content($all)
    
    let $new-content := local:do-modifications($content)
        
    let $testing := request:get-parameter("testing", "no")
      
    let $saved := if ($testing != "no") then 
        let $ret := local:check-changes()
        return $ret
      else
        let $ret := ''
        return $ret
        
    return local:do-post($saved)
};

let $login := xmldb:login( "/" , $config:exist-user , $config:exist-password )

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
    
    else ()
    
    
