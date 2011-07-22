declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;
declare namespace ar = "http://purl.org/atompub/revision/1.0";
declare namespace app = "http://www.w3.org/2007/app";

import module namespace response = "http://exist-db.org/xquery/response" ;

import module namespace config = "http://purl.org/atombeat/xquery/config" at "../../config/shared.xqm" ;
import module namespace CONSTANT = "http://purl.org/atombeat/xquery/constants" at "../../lib/constants.xqm" ;
import module namespace atomdb = "http://purl.org/atombeat/xquery/atomdb" at "../../lib/atomdb.xqm" ;
import module namespace common-protocol = "http://purl.org/atombeat/xquery/common-protocol" at "../../lib/common-protocol.xqm" ;
import module namespace config-collections = "http://purl.org/atombeat/xquery/config-collections" at "../collections.xqm" ;
import module namespace atom-protocol = "http://purl.org/atombeat/xquery/atom-protocol" at "../../lib/atom-protocol.xqm" ;
import module namespace manta-plugin = "http://www.cggh.org/2010/chassis/manta/xquery/atombeat-plugin" at "../../plugins/manta-plugin.xqm";

declare variable $study-info-template {
<study-info>
            <studyInfoStatus/>
            <start />
            <end />
            <sites>
                <site>
                    <country />
                    <region />
                    <district />
                    <locality />
                    <lookupAddress />
                    <siteCode />
                    <testingDelay />
                    <anticoagulant />
                    <transportAndStorageTemperature />
                    <latitude />
                    <longitude />
                    <transmissionIntensity>
                        <annualParasitologicalIncidence />
                        <annualParasitologicalIncidenceYear />
                        <parasitePrevalence />
                        <parasitePrevalenceYear />
                        <transmissionIntensityAgeFrom />
                        <transmissionIntensityAgeFromUnits />
                        <transmissionIntensityAgeTo />
                        <transmissionIntensityAgeToUnits />
                        <entomologicalInoculationRate />
                        <entomologicalInoculationRateYear />
                        <seasonalTransmission />
                        <transmissionMonths />
                    </transmissionIntensity>
                </site>
            </sites>
            <pathogens />
            <inclusionExclusionCriteria>
                <age>
                    <maxAge />
                    <maxAgeUnits />
                    <minAge />
                    <minAgeUnits />
                </age>
                <parasitaemia>
                    <minParasitaemia />
                    <maxParasitaemia />
                </parasitaemia>
        <includeMixedInfections />
                <excludeIfPriorAntimalarials />
                <priorAntimalarialsExclusion>
                    <priorAntimalarials />
                    <priorAntimalarialsDetermination />
                    <priorAntimalarialsHistoryWeeks />
                </priorAntimalarialsExclusion>
                <pregnancy />
                <treatmentReason />
                <otherCriteria />
            </inclusionExclusionCriteria>
            <clinical>
                <treatment>
                    <regimens>
                        <regimen>
                            <regimenName />
                            <regimenSupervision />
                            <regimenUrl />
                            <drugs />
                        </regimen>
                    </regimens>
                    <regimenAllocation>
                        <regimenAllocationMethod />
                        <blinding />
                        <randomisationProportion />
                    </regimenAllocation>
                </treatment>
                <followup>
                    <duration />
                    <feverMeasurement />
                    <haemoglobinRecording>
                        <haemoglobinRecordingType />
                    </haemoglobinRecording>
                </followup>
                <microscopy>
                    <microscopyStain />
                    <microscopyStainOther />
                    <asexualParasitemia>
                     <asexualParasitemiaNegativeCount />
           <asexualParasitemiaPositiveThickUnit />
           <asexualParasitemiaPositiveThickUnitOther />
           <asexualParasitemiaPositiveThinUnit />
           <asexualParasitemiaPositiveThinUnitOther />
                    </asexualParasitemia>
                    <sexualParasitemia>
                     <sexualParasitemiaNegativeCount />
           <sexualParasitemiaPositiveThickUnit />
           <sexualParasitemiaPositiveThickUnitOther />
           <sexualParasitemiaPositiveThinUnit />
           <sexualParasitemiaPositiveThinUnitOther />
                    </sexualParasitemia>
                    <!--   separateSpeciesCounts / -->
                    <thickFilmCalculationOfParasitemia>
                        <thickFilmFormula />
                        <thickFilmFormulaOther />
            <!-- 
                        <WBCSource />
                        <WBCAssumed />
                        <thickFilmCalculationOfParasitemiaOther />
             -->
                    </thickFilmCalculationOfParasitemia>
                    <thinFilmCalculationOfParasitemia>
                        <thinFilmFormula />
                        <thinFilmFormulaOther />
            <!-- 
                        <HtcSource />
                        <HtcAssumed />
                        <thinFilmCalculationOfParasitemiaOther />
             -->
                    </thinFilmCalculationOfParasitemia>
                    <qualityControl>
                        <internal>
                            <percentageRereadBySecondMicroscopist />
                            <rereadSlideSelectionMechanism />
                            <rereadSlideSelectionMechanismOther />
                        </internal>
                        <external>
                            <percentageRereadBySecondMicroscopist />
                            <rereadSlideSelectionMechanism />
                            <rereadSlideSelectionMechanismOther />
                        </external>
                    </qualityControl>
                </microscopy>
                <geneotypingToDistinguishBetweenRecrudescenceAndReinfection>
                    <applicability />
                    <applicable>
                        <markers>
                            <recrudescenceMarker>
                                <markerName />
                                <markerOther />
                                <numberOfMicroSatellites />
                            </recrudescenceMarker>
                        </markers>
                        <genotypingLaboratory />
                        <markerDiscriminantOpen>
                            <markerDiscriminant />
                            <markerDiscriminantOther />
                        </markerDiscriminantOpen>
                        <analysisProtocol>
                            <mixedAllelesOpen>
                                <mixedAlleles />
                                <mixedAllelesOther />
                                <recrudescence />
                                <reinfection />
                            </mixedAllelesOpen>
                        </analysisProtocol>
                    </applicable>
                </geneotypingToDistinguishBetweenRecrudescenceAndReinfection>
            </clinical>







            <molecular>

                <criteria>
                    <sampleSourceOpen>
                        <sampleSource />
                        <sampleSourceOther />
                    </sampleSourceOpen>
                    <malariaStatus />
                </criteria>
                <sample>
                    <sampleTypeOpen>
                        <sampleType />
                        <sampleTypeOther />
                        <wholeBloodSource />
                        <dateCultureIsolated />
                    </sampleTypeOpen>
                </sample>

                <genotypedMarkers />


                <mixedResistanceAlleles>
                    <mixedResistanceAllelesInclusion />
                    <mixedResistanceAllelesDesignation />
                </mixedResistanceAlleles>

                <additionalGenotypicInformation>
                    <sequencedLoci>
                        <wholeGenomesSequenced />
            <resistanceLociSequenced />
                        <resistanceLoci>
                            <resistanceLocus>
                <locusTypeOpen>
                  <locusType />
                  <locusTypeOther />
                </locusTypeOpen>
                <locusName />
                            </resistanceLocus>
                        </resistanceLoci>
            <otherLociGenotyped />
                        <otherLoci>
                          <otherLocus>
                <locusTypeOpen>
                  <locusType />
                  <locusTypeOther />
                </locusTypeOpen>
                <locusName />
                          </otherLocus>
                        </otherLoci>
                    </sequencedLoci>
                    <infectionComplexityEstimated />
                    <infectionComplexityEstimationlociOpen>
                        <infectionComplexityEstimationloci />
                        <infectionComplexityEstimationlociOther />
                    </infectionComplexityEstimationlociOpen>
                </additionalGenotypicInformation>
            </molecular>
            


            <invitro>
                <analysisSite />
                <culture>
                    <incubatorSystem />
                    <co2percentage />
                    <co2Other />
                    <o2percentage />
                    <o2Other />
                    <healthyErythrocytesSource />
                    <hematocritpercent />
                    <bloodGroup />
                </culture>
                <drugSusceptibilityMedium>
                    <medium />
                    <mediumOther />
                    <preparation />
                    <serum />
                    <serum-finalConcentration />
                    <NaHCO3-finalConcentration />
                    <hypoxantine>
                        <hypoxantine-added />
                        <hypoxantine-finalConcentration />
                    </hypoxantine>
                    <oroticAcid>
                        <oroticAcid-added />
                        <oroticAcid-finalConcentration />
                    </oroticAcid>
                    <glucose>
                        <glucose-added />
                        <glucose-finalConcentration />
                    </glucose>
                    <antibioticTreatments />
                </drugSusceptibilityMedium>
                <susceptibility>
                    <timeOfIncubation />
                    <susceptibilityMethod />
                </susceptibility>
                <drugs />
                <platePreparationMethod />
                <platesPreparationDate />
                <plateBatches />
            </invitro>

            

            <pharmacology>
                <samples>
                    <sample>
                        <anticoagulent />
                        <centrifugeTime />
            <storages>
              <storage>
                              <storageTemperature />
                              <storageDuration />
                              <storageDurationUnit />
              </storage>
            </storages>
                    </sample>
                </samples>
                <analytes>
                    <analyte>
                        <drugMeasured />
                        <lowerLoQ />
                        <sampleMatrixType />
                    </analyte>
                </analytes>
                <assayReferences>
                    <assayReference>
                        <referenceType>url</referenceType>
                        <url />
                        <doi />
                        <upload>
                            <uploadedUrl />
                        </upload>
                        <note />
                        <assayValidated />
                    </assayReference>
                </assayReferences>
            </pharmacology>

        </study-info>
};

declare function local:edit-path-info( $entry as element(atom:entry) ) as xs:string?
{
    let $href := $entry/atom:link[@rel='edit']/@href
    return
        if ( exists( $href ) ) then 
            let $uri := $href cast as xs:string
            return
                if ( starts-with( $uri , $config:edit-link-uri-base ) )
                then substring-after( $uri , $config:edit-link-uri-base )
                else ()
        else ()
};

declare function local:do-get() as item()*
{
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content('')
};



declare function local:content($content) as item()*
{
    let $studies := local:get-content("studies")
    let $old-studies := local:get-old-versioned-content-studies($studies) 
    let $new-studies := local:get-new-versioned-content-studies($studies)

    return
    
        <html>
            <head>
                <title>Data Migration - Study v1.3 to v1.5</title>
            </head>
            <body>
                <h1>Data Migration - Study v1.3 to v1.5</h1>
                
                <p>This script will:</p>
                
                <ul>
                    <li>Copy the study-info to the study entry</li>
                </ul>
                
                <p>Total number of entries in <a href="../content/studies">Study</a> collection: <strong>{ count( $studies ) }</strong></p>
                <p>Number of entries in <a href="../content/studies">Study</a> collection using v1.3 profile: <strong>{ count( $old-studies ) }</strong></p>
                <p>Number of entries in <a href="../content/studies">Study</a> collection using v1.5 profile: <strong>{ count( $new-studies ) }</strong></p>
                
                <p>Note: This script has no test mode.</p>
                
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

declare function local:get-content($collection) as element( atom:entry )* {

    let $content := atomdb:retrieve-members( concat("/", $collection) , false() )
    return $content
};

declare function local:get-new-versioned-content-studies($studies) as element( atom:entry )* {
    
    let $ret := $studies[atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/1.5']
    return $ret
};

declare function local:get-old-versioned-content-studies($studies) as element( atom:entry )* {
    
    let $ret := $studies[atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/1.5.1']
    return $ret
};

declare function local:do-modifications-studies($old-studies, $collection-name) as element( atom:entry )*
{
    let $new := local:modify-nodes($old-studies, $collection-name)
 
    (: Need to get the updated data from the db :)
    let $all := local:get-content("studies")
    let $content := local:get-old-versioned-content-studies($all)

    
    return $content
};


declare function local:save-changes($collection-name, $collection-new)
{

    let $content := 

        for $new in $collection-new
        let $path-info := local:edit-path-info( $new )
        return atomdb:update-member( $path-info , $new )
 
    return $content
};

declare function local:do-post($content) as item()*
{
(: SEND RESPONSE :)        
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content($content)
};


declare function local:get-study-info($group) as element(atom:entry)*
{
 let $href := $group//atom:link[@rel='self']/@href
    let $uri := substring-after($href,$config:self-link-uri-base)
     let $logi := util:log-app("debug", "getting", $uri)
    let $request := local:prepare-request('GET', $uri)
    let $group := atom-protocol:do-get-member($request)
    
    let $logi := util:log-app("debug", "entry", $group)
 let $href := $group//atom:link[@rel='http://www.cggh.org/2010/chassis/terms/studyInfo']/@href
    let $uri := substring-after($href,$config:self-link-uri-base)
     let $logi := util:log-app("debug", "getting", $uri)
    let $request := local:prepare-request('GET', $uri)
    let $group := atom-protocol:do-get-member($request)
    let $logi := util:log-app("debug", "got", $group)
    return $group
};

declare function local:prepare-request($op, $path) {
   <request>
    <path-info>{$path}</path-info>
    <method>{$op}</method>
    <headers>
        <header>
            <name>Accept</name>
            <value>application/atom+xml</value>
        </header>
        <header>
            <name>Content-Type</name>
            <value>application/atom+xml</value>
        </header>
    </headers>
    <user>admin@wwarn.org</user>
    <roles>
        <role>ROLE_CHASSIS_ADMINISTRATOR</role>
        <role>ROLE_CHASSIS_CONTRIBUTOR</role>
        <role>ROLE_CHASSIS_CURATOR</role>
        <role>ROLE_CHASSIS_USER</role>
    </roles>
</request>
};


declare function local:copy-admin-nodes($collection) as element( atom:entry )*
{

  
   let $groups := 
       for $group in $collection
                       
              let $logi := util:log-app("debug", "group", $group)
              let $ren := if (not(exists($group//draft))) then
                (: Need to move permissions :)
                let $d1 := update delete $group//study-info
                let $i := update insert <app:control xmlns:app="http://www.w3.org/2007/app">
            <draft>no</draft>
        </app:control> following $group//atom:title
                let $perm := local:get-study-info($group)         
                 let $study-info := $perm//study-info
                 let $logi := util:log-app("debug", "si", $study-info)
               let $new := update insert $study-info following $group//modules
               (: Jump 2 versions to match Chassis version :)
               let $profile := update replace $group//study/@profile with "http://www.cggh.org/2010/chassis/manta/1.5"
               let $ver := update delete $group//study-info/@profile
               let $ren := update rename $group//hematocritpercentage as 'hematocritpercent'
               else
                let $si := $study-info-template
                return $si
                
               return $ren
           


    return $groups

};


declare function local:modify-nodes($collection-old, $collection-name) as element( atom:entry )*
{
    let $drafts := local:copy-admin-nodes($collection-old)
    return $drafts
    
};



declare function local:do-migration($collection-name) {
  
    let $collection := local:get-old-versioned-content-studies(local:get-content($collection-name))
    let $new-collection := local:do-modifications-studies($collection, $collection-name)
    let $ret := local:save-changes($collection-name, $new-collection)

    return local:do-post('')
};

let $login := xmldb:login( "/" , $config:exist-user , $config:exist-password )

let $content := ''


    
return 

    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get()
    
    else if ( request:get-method() = $CONSTANT:METHOD-POST ) 
    
    then 
        
        let $var := local:do-migration("studies")
        return $var
        
    else ()
    
    
