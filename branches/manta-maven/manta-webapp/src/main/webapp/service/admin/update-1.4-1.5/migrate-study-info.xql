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
(: Adds "co2Other", "o2" and "o2Other" into the "culture" element in "invitro", after "co2". :)
(: Adds "platePreparationMethod" and "platesPreparationDate" into "invitro", after "drugs". :)
(: If there are batches in the "plateBatches" element of "invitro", then gives the content "batches" to the "platePreparationMethod" element. :)
(: See Milestone 1.0.x :)

declare variable $testdata {
<atom:entry xmlns:atom="http://www.w3.org/2005/Atom">
    <atom:id>https://www.wwarn.org/repository/atombeat/content/study-info/MGEXQ</atom:id>
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
                    <duration>63</duration>
					<feverMeasurement>axial</feverMeasurement>
					<haemoglobinRecording>
						<haemoglobinRecordingType>hct</haemoglobinRecordingType>
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
                        <markerDiscriminant>agarroseGelVisual</markerDiscriminant>
                        <markerDiscriminantOther>other text</markerDiscriminantOther>
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
                        <sampleSource>communitySurvey</sampleSource>
                        <sampleSourceOther/>
                    </sampleSourceOpen>
                    <malariaStatus>asymptomatic</malariaStatus>
                </criteria>
                <sample>
                    <sampleTypeOpen>
                        <sampleType>filterPaper</sampleType>
                        <sampleTypeOther/>
                        <wholeBloodSource/>
                        <dateCultureIsolated/>
                    </sampleTypeOpen>
                </sample>
                <genotypedMarkers/>
                <mixedResistanceAlleles>
                    <mixedResistanceAllelesInclusion>included</mixedResistanceAllelesInclusion>
                    <mixedResistanceAllelesDesignation>mixed</mixedResistanceAllelesDesignation>
                </mixedResistanceAlleles>
                <additionalGenotypicInformation>
                    <sequencedLoci>
                        <wholeGenomesSequenced>false</wholeGenomesSequenced>
                        <resistanceLociSequenced>false</resistanceLociSequenced>
                        <resistanceLoci>
                            <resistanceLocus>
                                <locusTypeOpen>
                                    <locusType/>
                                    <locusTypeOther/>
                                </locusTypeOpen>
                                <locusName/>
                            </resistanceLocus>
                        </resistanceLoci>
                        <otherLociGenotyped>true</otherLociGenotyped>
                        <otherLoci>
                            <otherLocus>
                                <locusTypeOpen>
                                    <locusType>other</locusType>
                                    <locusTypeOther>K76T, Mdr86 and mdr copy number</locusTypeOther>
                                </locusTypeOpen>
                                <locusName/>
                            </otherLocus>
                        </otherLoci>
                    </sequencedLoci>
                    <infectionComplexityEstimated>false</infectionComplexityEstimated>
                    <infectionComplexityEstimationlociOpen>
                        <infectionComplexityEstimationloci/>
                        <infectionComplexityEstimationlociOther/>
                    </infectionComplexityEstimationlociOpen>
                </additionalGenotypicInformation>
            </molecular>
            <invitro>
                <inclusionCriteria/>
                <analysisSite>France, Paris, Bichat hospital</analysisSite>
                <culture>
                    <incubatorSystem>incubationchamber</incubatorSystem>
                    <co2/>
                    <healthyErythrocytesSource>bloodbank</healthyErythrocytesSource>
                    <hematocrit>1.5</hematocrit>
                    <bloodGroup>O</bloodGroup>
                </culture>
                <drugSusceptibilityMedium>
                    <medium>RPMI1640</medium>
                    <mediumOther/>
                    <preparation>aliquots</preparation>
                    <serum>humanserum</serum>
                    <serum-finalConcentration>10%</serum-finalConcentration>
                    <NaHCO3-finalConcentration>2.1</NaHCO3-finalConcentration>
                    <hypoxantine>
                        <hypoxantine-added>true</hypoxantine-added>
                        <hypoxantine-finalConcentration>0.05</hypoxantine-finalConcentration>
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
                    <timeOfIncubation>42h</timeOfIncubation>
                    <susceptibilityMethod>isotopic</susceptibilityMethod>
                </susceptibility>
                <drugs>
                    <drug>
                        <molecule>CQ</molecule>
                        <solvent>distilled water</solvent>
                        <solventFinalConcentration>0.132 drug conc in RSS</solventFinalConcentration>
                        <providedByWwarn>false</providedByWwarn>
                        <provider>IMTSSA</provider>
                    </drug>
                    <drug>
                        <molecule>MDA</molecule>
                        <solvent>methanol</solvent>
                        <solventFinalConcentration>0.061 drug conc in RSS</solventFinalConcentration>
                        <providedByWwarn>false</providedByWwarn>
                        <provider>IMTSSA</provider>
                    </drug>
                    <drug>
                        <molecule>LUM</molecule>
                        <solvent>methanol</solvent>
                        <solventFinalConcentration>0.0131 drug conc in RSS ethanol!</solventFinalConcentration>
                        <providedByWwarn/>
                        <provider/>
                    </drug>
                    <drug>
                        <molecule>DHA</molecule>
                        <solvent>methanol</solvent>
                        <solventFinalConcentration>0.057 drug in RSS</solventFinalConcentration>
                        <providedByWwarn>false</providedByWwarn>
                        <provider>IMTSSA</provider>
                    </drug>
                    <drug>
                        <molecule>QN</molecule>
                        <solvent>methanol</solvent>
                        <solventFinalConcentration>0.102 drug in RSS</solventFinalConcentration>
                        <providedByWwarn>false</providedByWwarn>
                        <provider>IMTSSA</provider>
                    </drug>
                    <drug>
                        <molecule>PPQ</molecule>
                        <solvent>methanol</solvent>
                        <solventFinalConcentration>0.145 drug in RSS</solventFinalConcentration>
                        <providedByWwarn>false</providedByWwarn>
                        <provider>IMTSSA</provider>
                    </drug>
                    <drug>
                        <molecule>MQ</molecule>
                        <solvent>methanol</solvent>
                        <solventFinalConcentration>0.133 drug in RSS</solventFinalConcentration>
                        <providedByWwarn>false</providedByWwarn>
                        <provider>IMTSSA</provider>
                    </drug>
                    <drug>
                        <molecule>SP</molecule>
                        <solvent>methanol</solvent>
                        <solventFinalConcentration>2.05 drug in RSS</solventFinalConcentration>
                        <providedByWwarn>false</providedByWwarn>
                        <provider>IMTSSA</provider>
                    </drug>
                </drugs>
                <plateBatches>
                    <batch>
                        <clone3D7ProvidedByWwarn>false</clone3D7ProvidedByWwarn>
                        <clone3D7Provider>MR4</clone3D7Provider>
                        <batchesIncludedInRawData>true</batchesIncludedInRawData>
                        <preparationDate/>
                        <parasitaemia3D7/>
                        <ringForms/>
                    </batch>
                </plateBatches>
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
                        <drugMeasured>SP</drugMeasured>
                        <lowerLoQ>1</lowerLoQ>
                        <sampleMatrixType>wholeCapillaryBlood capillaryPlasma</sampleMatrixType>
                    </analyte>
                    <analyte>
                        <drugMeasured>PPQ</drugMeasured>
                        <lowerLoQ>1</lowerLoQ>
                        <sampleMatrixType>wholeCapillaryBlood capillaryPlasma</sampleMatrixType>
                    </analyte>
                    <analyte>
                        <drugMeasured>AL</drugMeasured>
                        <lowerLoQ>1</lowerLoQ>
                        <sampleMatrixType>wholeCapillaryBlood capillaryPlasma</sampleMatrixType>
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
                <title>Data Migration - Study Info v1.0 to v1.0.1</title>
            </head>
            <body>
                <h1>Data Migration - Study Info v1.0 to v1.0.1</h1>
                <p>Total number of entries in <a href="../content/studies">Study Info</a> collection: <strong>{ count( $study-infos ) }</strong></p>
                <p>Number of entries in <a href="../content/study-info">Study Info</a> collection using v1.0 profile: <strong>{ count( $old-study-infos ) }</strong></p>
                <p>Number of entries in <a href="../content/study-info">Study Info</a> collection using v1.0.1 profile: <strong>{ count( $new-study-infos ) }</strong></p>
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
    
    let $ret := $study-infos[atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/1.5.1']
    return $ret
};

declare function local:get-old-versioned-content($study-infos) as element( atom:entry )* {
    
    let $ret := $study-infos[atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/1.5']
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
   let $new-drugs := 
       for $drug in $old-study-infos//regimen/drugs/drug
           return
               update replace $drug/batchNumber with
                   <batches>
                       <drugBatch>
                           {$drug/batchNumber}
                           <manufactureDate/>
                           {$drug/expiryDate}
                       </drugBatch>
                   </batches>
                   
   let $new-study-infos := 
        for $old in $old-study-infos
            let $del := update delete $old//regimen/drugs/drug/expiryDate
            let $profile := update replace $old//study/@profile with "http://www.cggh.org/2010/chassis/manta/1.5.1"
            let $pop := update insert <population/> following $old//longitude
            let $instn := update insert <tradeName/> following $old//manufacturer
        return $instn

    
     
    return $new-study-infos
    
};



declare function local:check-changes() as item() *{
  let $all := local:get-content()
 let $old := local:get-old-versioned-content($all)
 let $new :=  local:get-new-versioned-content($all)
 let $summary := concat('Old studies:',count($old),' New studies:',count($new),'&#xD;')
 let $ret := for $m in $new 
         let $out := if (count($m//co2) > 0 or count($m//co2percentage) = 0) then 
             let $msg := "Failed to change co2"
             return $msg
          else
             let $msg := "Changed co2"
             return $msg
         let $new := concat($summary,$out,'&#xD;')
         let $out := if (count($m//wGroupDosing) > 0 or count($m//weightGroupDosing) = 0) then 
             let $msg := "Failed to change wGroupDosing"
             return $msg
          else
             let $msg := "Changed wGroupDosing"
             return $msg
         let $msg := concat($new,$out,'&#xD;')
         
          let $out := if (count($m//ringForms) > 0 or count($m//ringFormingPercentage) = 0) then 
             let $msg := "Failed to change ringForms"
             return $msg
          else
             let $msg := "Changed ringForms"
             return $msg
         let $new := concat($msg,$out,'&#xD;')
         
         let $out := if (count($m//wGroupDosingSchedule) > 0 or count($m//weightGroupDosingSchedule) = 0) then 
             let $msg := "Failed to change wGroupDosingSchedule"
             return $msg
          else
             let $msg := "Changed wGroupDosingSchedule"
             return $msg
         let $msg := concat($new,$out,'&#xD;')

          let $out := if (count($m//wGroupFrom) > 0 or count($m//weightGroupFrom) = 0) then 
             let $msg := "Failed to change wGroupFrom"
             return $msg
          else
             let $msg := "Changed wGroupFrom"
             return $msg
         let $new := concat($msg,$out,'&#xD;')
         let $out := if (count($m//wGroupTo) > 0 or count($m//weightGroupTo) = 0) then 
             let $msg := "Failed to change wGroupTo"
             return $msg
          else
             let $msg := "Changed wGroupTo"
             return $msg
         let $msg := concat($new,$out,'&#xD;')
         
         let $out := if (count($m//hematocrit) > 0 or count($m//hematocritpercentage) = 0) then 
             let $msg := "Failed to change hematocrit"
             return $msg
          else
             let $msg := "Changed hematocrit"
             return $msg
         let $new := concat($msg,$out,'&#xD;')
 (:
             
           
         
           
           
            let $tmp := update delete $v1-0//studyInfoStatus
            return update insert <studyInfoStatus>new</studyInfoStatus> preceding $v1-0//start
            :)
            let $out := if (count($m//parasitaemia3D7) > 0 or count($m//parasitaemia3D7percentage) = 0) then 
             let $msg := "Failed to change parasitaemia3D7"
             return $msg
          else
             let $msg := "Changed parasitaemia3D7"
             return $msg
         let $msg := concat($new,$out,'&#xD;')
         let $out := if (count($m//clinical/recrudescenceAndReinfection) > 0) then 
             let $msg := "Failed to delete //clinical/recrudescenceAndReinfection"
             return $msg
          else
             let $msg := "Deleted //clinical/recrudescenceAndReinfection"
             return $msg
         let $new := concat($msg,$out,'&#xD;')
         let $out := if (count($m//invitro/inclusionCriteria) > 0) then 
             let $msg := "Failed to delete //invitro/inclusionCriteria"
             return $msg
          else
             let $msg := "Deleted //invitro/inclusionCriteria"
             return $msg
         let $msg := concat($new,$out,'&#xD;')
         let $out := if (count($m//pharmacology/samples/sample/numberPlanned) > 0) then 
             let $msg := "Failed to delete //pharmacology/samples/sample/numberPlanned"
             return $msg
          else
             let $msg := "Deleted //pharmacology/samples/sample/numberPlanned"
             return $msg
         let $new := concat($msg,$out,'&#xD;')
         let $out := if (count($m//invitro/drugs/drug[./molecule/text()="AL"]) > 0) then 
             let $msg := "Failed to delete //invitro/drugs/drug[./molecule/text()=AL]"
             return $msg
          else
             let $msg := "Deleted //invitro/drugs/drug[./molecule/text()=AL]"
             return $msg
         let $msg := concat($new,$out,'&#xD;')
         let $out := if (count($m//pharmacology/analytes/analyte[./drugMeasured/text()='AL']) > 0) then 
             let $msg := "Failed to delete //pharmacology/analytes/analyte[./drugMeasured/text()='AL']"
             return $msg
          else
             let $msg := "Deleted //pharmacology/analytes/analyte[./drugMeasured/text()='AL']"
             return $msg
         let $new := concat($msg,$out,'&#xD;')
          let $out := if (count($m//molecule[. = "SP"]) > 0 or count($m//molecule[. = "SX"]) = 0) then 
             let $msg := "Failed to change SP to SX"
             return $msg
          else
             let $msg := "Changed SP to SX"
             return $msg
         let $msg := concat($new,$out,'&#xD;')
         let $out := if (count($m//molecule[. = "PPQ"]) > 0 or count($m//molecule[. = "PQ"]) = 0) then 
             let $msg := "Failed to change PPQ to PQ"
             return $msg
          else
             let $msg := "Changed PPQ to PQ"
             return $msg
         let $new := concat($msg,$out,'&#xD;')
         let $out := if (count($m//drugMeasured[. = "SP"]) > 0 or count($m//drugMeasured[. = "SX"]) = 0) then 
             let $msg := "Failed to change SP to SX"
             return $msg
          else
             let $msg := "Changed SP to SX"
             return $msg
         let $msg := concat($new,$out,'&#xD;')
         let $out := if (count($m//drugMeasured[. = "PPQ"]) > 0 or count($m//drugMeasured[. = "PQ"]) = 0) then 
             let $msg := "Failed to change PPQ to PQ"
             return $msg
          else
             let $msg := "Changed PPQ to PQ"
             return $msg
         let $new := concat($msg,$out,'&#xD;')
         let $out := if (count($m//studyInfoStatus) != 1) then 
             let $msg := "Failed to add studyInfoStatus"
             return $msg
          else
             let $msg := "Added studyInfoStatus"
             return $msg
         let $msg := concat($new,$out,'&#xD;')
         let $out := if (count($m//haemoglobinRecordingType[. = "hct"]) > 0 or count($m//haemoglobinRecordingType[. = "htc"]) = 0) then 
              let $msg := "Failed to change haemoglobinRecordingType hct to htc"
             return $msg
          else
             let $msg := "Changed haemoglobinRecordingType hct to htc"
             return $msg
         let $new := concat($msg,$out,'&#xD;')
         return $new
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
    
    
