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
                    <institutions>
                        <institution-ack>
                            <institution-name/>
                            <institution-websites/>
                        </institution-ack>
                    </institutions>
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
                <title>Data Migration - Study Info v2.0 to v2.1</title>
            </head>
            <body>
                <h1>Data Migration - Study v2.0 to v2.1</h1>
                <p>Total number of entries in <a href="../../content/studies">Studies</a> collection: <strong>{ count( $study-infos ) }</strong></p>
                <p>Number of entries in <a href="../../content/studies">Studies</a> collection using v2.0 profile: <strong>{ count( $old-study-infos ) }</strong></p>
                <p>Number of entries in <a href="../../content/studies">Studies</a> collection using v2.1 profile: <strong>{ count( $new-study-infos ) }</strong></p>
                <p>
                    <form method="post" action="">
                        Use dummy test data <input type="checkbox" name="testing" checked="checked" />
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
    
    let $ret := $study-infos[atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/2.1']
    return $ret
};

declare function local:get-old-versioned-content($study-infos) as element( atom:entry )* {
    
    let $ret := $study-infos[atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/2.0']
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
            let $profile := update replace $old//study/@profile with "http://www.cggh.org/2010/chassis/manta/2.1"

            (: surround existing person ack with new group <people> :)
            let $ins := update insert <people>{$old//person}</people> into $old//acknowledgements
            let $del := update delete $old//acknowledgements/person
            
            (: Move space separated list into an attribute called selected, in case it is still needed :) 
            let $prior := $old//priorAntimalarials/@selected
            (: Need to clear out any inserted via UI :)
            let $del1 := update delete $old//priorAntimalarials/drugTaken
            let $sd := update insert <studyDesign/> preceding $old//pharmacology/samples
            let $st := update insert <samplingTimes/> preceding $old//pharmacology/samples
            let $mo := update insert <drugNameOther/> following $old//drug/name
            let $aino := update insert <activeIngredientNameOther/> following $old//activeIngredientName
            let $mo := update insert <manufacturerOther/> following $old//drug/manufacturer
            let $tno := update insert <tradeNameOther/> following $old//drug/tradeName
            let $pkc := update insert <PKcomments/> preceding $old//pharmacology/samples
            let $td := update insert <targetDose/> preceding $old//pharmacology/analytes/analyte/lowerLoQ
            let $tdu := update insert <unitsOfMeasure/> preceding $old//pharmacology/analytes/analyte/lowerLoQ
            let $tduo := update insert <unitsOfMeasureOther/> preceding $old//pharmacology/analytes/analyte/lowerLoQ
            let $fa := update insert <fatAmount/> preceding $old//pharmacology/analytes/analyte/lowerLoQ
            let $incInf := update replace $old//includeMixedInfections[. = 'true']/text() with 'include'
            let $exInf := update replace $old//includeMixedInfections[. = 'false']/text() with 'exclude'
            let $incPrior := update replace $old//excludeIfPriorAntimalarials[. = 'false']/text() with 'include'
            let $exPrior := update replace $old//excludeIfPriorAntimalarials[. = 'true']/text() with 'exclude'
            let $yv := update replace $old//readministeredOnVomitting[. = 'true']/text() with 'yes'
            let $nv := update replace $old//readministeredOnVomitting[. = 'false']/text() with 'no'
            let $dn := for $odn in $old//drug
                let $tn := normalize-space($odn/tradeName/text())
                let $trade-name := concat(upper-case(substring($tn,1,1)),substring($tn,2))
                let $tnc := if ($trade-name = '' or $trade-name = 'Amobin' or $trade-name = 'Amonate' or $trade-name = 'Arco' or $trade-name = 'Arsudar' 
                                or $trade-name = 'Arsumax' or $trade-name = 'Artefan' or $trade-name = 'Artekin' or $trade-name = 'Avloclor' 
                                or $trade-name = 'Basoquin' or $trade-name = 'Camoquin' or $trade-name = 'Co-artesiane' or $trade-name = 'Coartem' 
                                or $trade-name = 'Cosmoquin' or $trade-name = 'Duo-Cotecxin' or $trade-name = 'Eloquine' or $trade-name = 'Eurartesim' 
                                or $trade-name = 'Falcidin' or $trade-name = 'Fansidar' or $trade-name = 'Farenax' or $trade-name = 'Flavoquine' 
                                or $trade-name = 'Halfan' or $trade-name = 'LapDap' or $trade-name = 'Lariam' or $trade-name = 'Malaratab' 
                                or $trade-name = 'Malarine' or $trade-name = 'Malarone' or $trade-name = 'Malmed' or $trade-name = 'Mequin' 
                                or $trade-name = 'Nivaquine' or $trade-name = 'Plasmotrin' or $trade-name = 'Resunate' or $trade-name = 'Riamet') then
                                let $repo := update replace $odn/tradeName/text() with $trade-name
                                return $trade-name
                            else
                                 let $old-name := $odn/tradeName/text()
                                 let $rep := update replace $odn/tradeNameOther with <tradeNameOther>{$old-name}</tradeNameOther> 
                                 let $repo := update replace $odn/tradeName/text() with 'Other'
                                 return $rep
                let $manu-name := normalize-space($odn/manufacturer/text())
                let $manc := if ($manu-name = 'Ajanta Pharma' or $manu-name = 'Aspen Healthcare' or $manu-name = 'AstraZeneca' 
                                or $manu-name = 'Atlantic Laboratories' or $manu-name = 'Bailly-Creat Laboratory' 
                                or $manu-name = 'Boucher and Muir' or $manu-name = 'Cosmos' or $manu-name = 'Dafra Pharma' 
                                or $manu-name = 'Far-Manguinhos' or $manu-name = 'Glaxo-SmithKline' or $manu-name = 'Guilin Pharma' 
                                or $manu-name = 'Hoffman-La Roche' or $manu-name = 'Holley Pharmaceuticals' 
                                or $manu-name = 'Holley-Cotec Pharmaceuticals' or $manu-name = 'Holleykin Pharmaceuticals' 
                                or $manu-name = 'Ipca Laboratories Ltd' or $manu-name = 'Kunming Pharmaceutical Corporation' 
                                or $manu-name = 'Maphra' or $manu-name = 'Medinomics Healthcare' or $manu-name = 'Medochemie' 
                                or $manu-name = 'Mepha' or $manu-name = 'Novartis' or $manu-name = 'Parke-Davis' or $manu-name = 'Pfizer' 
                                or $manu-name = 'Pharmamed' or $manu-name = 'Regal Pharmaceuticals' or $manu-name = 'Rene Pharmaceuticals' 
                                or $manu-name = 'Rhone-Poulenc' or $manu-name = 'Roche' or $manu-name = 'Sanofi' or $manu-name = 'Sanofi Aventis' 
                                or $manu-name = 'SigmaTau' or $manu-name = 'SmithKline Beecham' or $manu-name = 'Swiss Pharma Nigeria Limited' 
                                or $manu-name = 'Zeneca') then    
                                 $manu-name
                            else if ($manu-name = 'Sanofi-Aventis') then
                                let $repo := update replace $odn/manufacturer/text() with 'Sanofi Aventis'
                                return $repo
                            else if ($manu-name = 'Sigma-Tau') then
                                let $repo := update replace $odn/manufacturer/text() with 'SigmaTau'
                                return $repo
                            else if ($manu-name = 'Glaxo-SmithKline' or $manu-name = 'GSK') then
                                let $repo := update replace $odn/manufacturer/text() with 'Glaxo-SmithKline'
                                return $repo
                            else if ($manu-name = 'Guilin Pharmaceutical factory') then
                                let $repo := update replace $odn/manufacturer/text() with 'Guilin Pharma'
                                return $repo
                            else
                                 let $old-name := $odn/manufacturer/text()
                                 let $rep := update replace $odn/manufacturerOther with <manufacturerOther>{$old-name}</manufacturerOther> 
                                 let $repo := update replace $odn/manufacturer/text() with 'Other'
                                 return $rep
                let $drug-name := lower-case(normalize-space($odn/name/text()))
                let $ndn := if ($drug-name = 'artesunate-amodiaquine') then
                                let $rep := update replace $odn/name/text() with 'AS-AQ'
                                return $rep
                            else if ($drug-name = 'artemether-lumefantrine') then
                                let $rep := update replace $odn/name/text() with 'AM-LUM'
                                return $rep
                                else if ($drug-name = 'amodiaquine') then
                                    let $rep := update replace $odn/name/text() with 'AQ'
                                    return $rep
                                else if ($drug-name = 'artemether') then
	                                let $rep := update replace $odn/name/text() with 'AM'
	                                return $rep

                                else if ($drug-name = 'artemether-lumefantrine') then
                                	let $rep := update replace $odn/name/text() with 'AM-LUM'
	                                return $rep

                                else if ($drug-name = 'artemisinin-napthoquine') then
	                                let $rep := update replace $odn/name/text() with 'AM-NQ'
	                                return $rep

                                else if ($drug-name = 'artesunate') then
	                                let $rep := update replace $odn/name/text() with 'AS'
	                                return $rep

                                else if ($drug-name = 'artesunate-amodiaquine') then
	                                let $rep := update replace $odn/name/text() with 'AS-AQ'
	                                return $rep

                                else if ($drug-name = 'artesunate-mefloquine') then
	                                let $rep := update replace $odn/name/text() with 'AS-MQ'
	                                return $rep

                                else if ($drug-name = 'atovaquone') then
	                                let $rep := update replace $odn/name/text() with 'AV'
	                                return $rep

                                else if ($drug-name = 'atovaquone-proguanil') then
	                                let $rep := update replace $odn/name/text() with 'AV-PG'
	                                return $rep

                                else if ($drug-name = 'chloroquine') then
	                                let $rep := update replace $odn/name/text() with 'CQ'
	                                return $rep

                                else if ($drug-name = 'chlorproguanil-dapsone') then
	                                let $rep := update replace $odn/name/text() with 'CP-DAP'
	                                return $rep

                                else if ($drug-name = 'dihydroartemisinin') then
	                                let $rep := update replace $odn/name/text() with 'DHA'
	                                return $rep

                                else if ($drug-name = 'dihydroartemisinin-piperaquine') then
	                                let $rep := update replace $odn/name/text() with 'DHA-PQP'
	                                return $rep

                                else if ($drug-name = 'halofantrine') then
	                                let $rep := update replace $odn/name/text() with 'HL'
	                                return $rep

                                else if ($drug-name = 'lumefantrine') then
	                                let $rep := update replace $odn/name/text() with 'LUM'
	                                return $rep

                                else if ($drug-name = 'mefloquine') then
	                                let $rep := update replace $odn/name/text() with 'MQ'
	                                return $rep

                                else if ($drug-name = 'piperaquine') then
	                                let $rep := update replace $odn/name/text() with 'PQP'
	                                return $rep

                                else if ($drug-name = 'proguanil') then
	                                let $rep := update replace $odn/name/text() with 'PG'
	                                return $rep

                                else if ($drug-name = 'pyrimethamine') then
	                                let $rep := update replace $odn/name/text() with 'PYR'
	                                return $rep

                                else if ($drug-name = 'quinine') then
	                                let $rep := update replace $odn/name/text() with 'QN'
	                                return $rep

                                else if ($drug-name = 'sulfadoxine') then
	                                let $rep := update replace $odn/name/text() with 'SX'
	                                return $rep

                                else if ($drug-name = 'sulfadoxine-pyrimethamine') then
	                                let $rep := update replace $odn/name/text() with 'SX-PYR'
	                                return $rep
                                else if ($drug-name = 'tafenoquine') then
	                                let $rep := update replace $odn/name/text() with 'TQ'
	                                return $rep
                                else if ($drug-name = 'tetracyclin') then
	                                let $rep := update replace $odn/name/text() with 'TET'
	                                return $rep
                                
                                else
                                    let $old-name := $odn/name/text()
                                    let $rep := update replace $odn/drugNameOther with <drugNameOther>{$old-name}</drugNameOther> 
                                    let $repo := update replace $odn/name/text() with 'Other'
                                    return $rep
                     return $ndn
                      let $in := for $odn in $old//activeIngredient
                                    let $ingredient := lower-case(normalize-space($odn/activeIngredientName/text()))
                                    let $logi := util:log-app("debug", "activeIngredientName", $ingredient)
                                    let $ndn := if ($ingredient = 'amodiaquine') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'AQ'
                                    	return $rep
                                    else if ($ingredient = 'artemether') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'AM'
                                    	return $rep
                                    else if ($ingredient = 'artemisinin') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'ART'
                                    	return $rep
                                    else if ($ingredient = 'artesunate') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'AS'
                                    	return $rep
                                    else if ($ingredient = 'atovaquone') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'AV'
                                    	return $rep
                                    else if ($ingredient = 'azithromycin') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'AZ'
                                    	return $rep
                                    else if ($ingredient = 'clindamycine') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'CL'
                                    	return $rep
                                    else if ($ingredient = 'chlorproguanil') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'CP'
                                    	return $rep
                                    else if ($ingredient = 'chloroquine') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'CQ'
                                    	return $rep
                                    else if ($ingredient = 'cycloguanil') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'CY'
                                    	return $rep
                                    else if ($ingredient = 'dapsone') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'DAP'
                                    	return $rep
                                    else if ($ingredient = 'dihydroartemisinin') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'DHA'
                                    	return $rep
                                    else if ($ingredient = 'doxycycline') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'DOX'
                                    	return $rep
                                    else if ($ingredient = 'halofantrine') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'HL'
                                    	return $rep
                                    else if ($ingredient = 'lumefantrine') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'LUM'
                                    	return $rep
                                    else if ($ingredient = 'mefloquine') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'MQ'
                                    	return $rep
                                    else if ($ingredient = 'naphtoquine') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'NQ'
                                    	return $rep
                                    else if ($ingredient = 'piperaquine') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'PQP'
                                    	return $rep
                                    else if ($ingredient = 'primaquine') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'PQ'
                                    	return $rep
                                    else if ($ingredient = 'proguanil') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'PG'
                                    	return $rep
                                    else if ($ingredient = 'pyrimethamine') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'PYR'
                                    	return $rep
                                    else if ($ingredient = 'pyronaridine') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'PYN'
                                    	return $rep
                                    else if ($ingredient = 'quinine') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'QN'
                                    	return $rep
                                    else if ($ingredient = 'sulfadoxine') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'SX'
                                    	return $rep
                                    else if ($ingredient = 'tafenoquine') then
	                                    let $rep := update replace $odn/activeIngredientName/text() with 'TQ'
	                                    return $rep
                                    else if ($ingredient = 'tetracyclin') then
                                    	let $rep := update replace $odn/activeIngredientName/text() with 'TET'
                                    	return $rep
                                    else
                                        let $old-name := $odn/activeIngredientName/text()
                                        let $rep := update replace $odn/activeIngredientNameOther with <activeIngredientNameOther>{$old-name}</activeIngredientNameOther> 
                                        let $repo := update replace $odn/activeIngredientName/text() with 'Other'
                                        return $rep
                                 return $ndn
            (: then set values as proper xml :)
            let $drugs := tokenize($prior,'\s+')
            let $dts := for $d in $drugs
                let $dt := update insert <drugTaken>{$d}</drugTaken> into $old//priorAntimalarials
                return $dt
             
            
        return $profile

     
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
    
    
