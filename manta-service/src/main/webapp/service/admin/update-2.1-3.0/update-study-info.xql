declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;
declare namespace app = "http://www.w3.org/2007/app" ;

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


declare variable $testdata {
<atom:entry xmlns:atom="http://www.w3.org/2005/Atom">
    <atom:id>https://kwiat33/repository/service/content/studies/BKPQY</atom:id>
    <atom:published>2012-09-07T13:25:36.96+01:00</atom:published>
    <atom:updated>2012-09-07T14:10:08.045+01:00</atom:updated>
    <atom:author>
        <atom:email>cora@example.org</atom:email>
    </atom:author>
    <atom:title type="text">BKPQY</atom:title>
    <app:control xmlns:app="http://www.w3.org/2007/app">
        <draft>no</draft>
    </app:control>
    <atom:content type="application/vnd.chassis-manta+xml">
        <study profile="http://www.cggh.org/2010/chassis/manta/2.1">
            <studyTitle>s1-changed</studyTitle>
            <ui-info>
                <wizard-pane-to-show>confirmation</wizard-pane-to-show>
            </ui-info>
            <registrant-has-agreed-to-the-terms>yes</registrant-has-agreed-to-the-terms>
            <study-is-published>Yes</study-is-published>
            <explorer-display>full</explorer-display>
            <publications>
                <publication>
                    <publication-title>My title</publication-title>
                    <pmid>15388456</pmid>
                    <publication-references>
                        <publication-reference type="url">gggggggggg</publication-reference>
                        <publication-reference type="doi">10.1128/AAC.48.10.3940-3943.2004</publication-reference>
                    </publication-references>
                </publication>
                <publication>
                    <publication-title>my title 2</publication-title>
                    <pmid>12345678</pmid>
                    <publication-references/>
                </publication>
            </publications>
            <acknowledgements>
                <institutions>
                    <institution-ack>
                        <institution-name>Department of Infectious and Tropical Diseases</institution-name>
                        <institution-websites>
                            <institution-url/>
                        </institution-websites>
                    </institution-ack>
                </institutions>
                <people>
                    <person>
                        <first-name>Rachel L</first-name>
                        <middle-name/>
                        <family-name>Hallett</family-name>
                        <email-address/>
                        <institution/>
                        <person-is-contactable/>
                    </person>
                    <person>
                        <first-name>Colin J</first-name>
                        <middle-name/>
                        <family-name>Sutherland</family-name>
                        <email-address/>
                        <institution/>
                        <person-is-contactable/>
                    </person>
                    <person>
                        <first-name>Neal</first-name>
                        <middle-name/>
                        <family-name>Alexander</family-name>
                        <email-address/>
                        <institution/>
                        <person-is-contactable/>
                    </person>
                    <person>
                        <first-name>Rosalynn</first-name>
                        <middle-name/>
                        <family-name>Ord</family-name>
                        <email-address/>
                        <institution/>
                        <person-is-contactable/>
                    </person>
                    <person>
                        <first-name>Musa</first-name>
                        <middle-name/>
                        <family-name>Jawara</family-name>
                        <email-address/>
                        <institution/>
                        <person-is-contactable/>
                    </person>
                    <person>
                        <first-name>Chris J</first-name>
                        <middle-name/>
                        <family-name>Drakeley</family-name>
                        <email-address/>
                        <institution/>
                        <person-is-contactable/>
                    </person>
                    <person>
                        <first-name>Margaret</first-name>
                        <middle-name/>
                        <family-name>Pinder</family-name>
                        <email-address/>
                        <institution/>
                        <person-is-contactable/>
                    </person>
                    <person>
                        <first-name>Gijs</first-name>
                        <middle-name/>
                        <family-name>Walraven</family-name>
                        <email-address/>
                        <institution/>
                        <person-is-contactable/>
                    </person>
                    <person>
                        <first-name>Geoffrey A T</first-name>
                        <middle-name/>
                        <family-name>Targett</family-name>
                        <email-address/>
                        <institution/>
                        <person-is-contactable/>
                    </person>
                    <person>
                        <first-name>Ali</first-name>
                        <middle-name/>
                        <family-name>Alloueche</family-name>
                        <email-address/>
                        <institution/>
                        <person-is-contactable/>
                    </person>
                </people>
            </acknowledgements>
            <curator-notes>Putting into curation</curator-notes>
            <study-status>in</study-status>
            <modules>clinical molecular pharmacology invitro</modules>
            <study-info>
                <studyInfoStatus/>
                <start>2005-03-02</start>
                <end>2006-02-03</end>
                <sites>
                    <site>
                        <country/>
                        <region>south east</region>
                        <district>oxfordshire</district>
                        <locality>oxford</locality>
                        <lookupAddress>oxford oxfordshire south east</lookupAddress>
                        <siteCode>site1</siteCode>
                        <testingDelay>gt72h</testingDelay>
                        <anticoagulant>ACD</anticoagulant>
                        <transportAndStorageTemperature>ambient</transportAndStorageTemperature>
                        <latitude>51.75629</latitude>
                        <longitude>-1.25951</longitude>
                        <population/>
                        <transmissionIntensity>
                            <annualParasitologicalIncidence>5</annualParasitologicalIncidence>
                            <annualParasitologicalIncidenceYear>1</annualParasitologicalIncidenceYear>
                            <parasitePrevalence>4</parasitePrevalence>
                            <parasitePrevalenceYear>6</parasitePrevalenceYear>
                            <transmissionIntensityAgeFrom>4</transmissionIntensityAgeFrom>
                            <transmissionIntensityAgeFromUnits>years</transmissionIntensityAgeFromUnits>
                            <transmissionIntensityAgeTo>4</transmissionIntensityAgeTo>
                            <transmissionIntensityAgeToUnits>months</transmissionIntensityAgeToUnits>
                            <entomologicalInoculationRate>6</entomologicalInoculationRate>
                            <entomologicalInoculationRateYear>2</entomologicalInoculationRateYear>
                            <seasonalTransmission>true</seasonalTransmission>
                            <transmissionMonths>jan apr</transmissionMonths>
                        </transmissionIntensity>
                    </site>
                    <site>
                        <country/>
                        <region/>
                        <district/>
                        <locality/>
                        <lookupAddress/>
                        <siteCode>site2</siteCode>
                        <testingDelay/>
                        <anticoagulant/>
                        <transportAndStorageTemperature/>
                        <latitude/>
                        <longitude/>
                        <population/>
                        <transmissionIntensity>
                            <annualParasitologicalIncidence/>
                            <annualParasitologicalIncidenceYear/>
                            <parasitePrevalence/>
                            <parasitePrevalenceYear/>
                            <transmissionIntensityLevel/>
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
                <pathogens>pfalciparum pvivax povale pmalariae pknowlesi</pathogens>
                <inclusionExclusionCriteria>
                    <age>
                        <maxAge>80</maxAge>
                        <maxAgeUnits/>
                        <minAge>2</minAge>
                        <minAgeUnits/>
                    </age>
                    <parasitaemia>
                        <minParasitaemia>4</minParasitaemia>
                        <maxParasitaemia>99</maxParasitaemia>
                    </parasitaemia>
                    <includeMixedInfections>include</includeMixedInfections>
                    <excludeIfPriorAntimalarials>exclude</excludeIfPriorAntimalarials>
                    <priorAntimalarialsExclusion>
                        <priorAntimalarials selected="ART AS">
                            <drugTaken>ART</drugTaken>
                            <drugTaken>AS</drugTaken>
                        </priorAntimalarials>
                        <priorAntimalarialsDetermination/>
                        <priorAntimalarialsHistoryWeeks/>
                    </priorAntimalarialsExclusion>
                    <pregnancy>include</pregnancy>
                    <treatmentReason>healthyViolunteer</treatmentReason>
                    <otherCriteria>other</otherCriteria>
                </inclusionExclusionCriteria>
                <clinical>
                    <treatment>
                        <regimens>
                            <regimen>
                                <regimenName>regimen 1</regimenName>
                                <regimenSupervision/>
                                <regimenUrl/>
                                <drugs>
                                    <drug>
                                        <name/>
                                        <drugNameOther/>
                                        <activeIngredients>
                                            <activeIngredient>
                                                <activeIngredientName/>
                                                <activeIngredientNameOther/>
                                                <activeIngredientMgPerDose/>
                                            </activeIngredient>
                                            <activeIngredient>
                                                <activeIngredientName/>
                                                <activeIngredientNameOther/>
                                                <activeIngredientMgPerDose/>
                                            </activeIngredient>
                                        </activeIngredients>
                                        <administrationRoute>intravenous</administrationRoute>
                                        <manufacturer/>
                                        <manufacturerOther/>
                                        <tradeName/>
                                        <tradeNameOther/>
                                        <batches>
                                            <drugBatch>
                                                <batchNumber>batch1</batchNumber>
                                                <manufactureDate/>
                                                <expiryDate>2012-03-29</expiryDate>
                                            </drugBatch>
                                        </batches>
                                        <drugStorage>none</drugStorage>
                                        <drugDosingDeterminant>weight</drugDosingDeterminant>
                                        <ageDosing>
                                            <ageDosingSchedule>
                                                <day>1</day>
                                                <hour>1</hour>
                                                <ageFrom>2</ageFrom>
                                                <ageTo>3</ageTo>
                                                <dose>4</dose>
                                                <doseUnit>tablets</doseUnit>
                                            </ageDosingSchedule>
                                        </ageDosing>
                                        <weightGroupDosing>
                                            <weightGroupDosingSchedule>
                                                <day>1</day>
                                                <hour>1</hour>
                                                <weightGroupFrom>2</weightGroupFrom>
                                                <weightGroupTo>3</weightGroupTo>
                                                <dose>4</dose>
                                                <doseUnit>tablets</doseUnit>
                                            </weightGroupDosingSchedule>
                                        </weightGroupDosing>
                                        <weightDosing>
                                            <weightDosingSchedule>
                                                <day>1</day>
                                                <hour>1</hour>
                                                <dose>4</dose>
                                            </weightDosingSchedule>
                                            <weightDosingSchedule>
                                                <day>1111</day>
                                                <hour>1</hour>
                                                <dose>4</dose>
                                            </weightDosingSchedule>
                                        </weightDosing>
                                        <feeding>meal</feeding>
                                        <fatPerMeal/>
                                        <feedingOther/>
                                        <readministeredOnVomitting>no</readministeredOnVomitting>
                                        <comments>comments</comments>
                                    </drug>
                                    <drug>
                                        <name/>
                                        <drugNameOther/>
                                        <activeIngredients>
                                            <activeIngredient>
                                                <activeIngredientName/>
                                                <activeIngredientNameOther/>
                                                <activeIngredientMgPerDose/>
                                            </activeIngredient>
                                        </activeIngredients>
                                        <administrationRoute/>
                                        <manufacturer/>
                                        <manufacturerOther/>
                                        <tradeName/>
                                        <tradeNameOther/>
                                        <batches/>
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
                                        <weightGroupDosing>
                                            <weightGroupDosingSchedule>
                                                <day/>
                                                <hour/>
                                                <weightGroupFrom/>
                                                <weightGroupTo/>
                                                <dose/>
                                                <doseUnit/>
                                            </weightGroupDosingSchedule>
                                        </weightGroupDosing>
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
                            <regimen>
                                <regimenName>regimen 2</regimenName>
                                <regimenSupervision/>
                                <regimenUrl/>
                                <drugs/>
                            </regimen>
                        </regimens>
                        <regimenAllocation>
                            <regimenAllocationMethod>quasirandomised</regimenAllocationMethod>
                            <blinding>singleClinician</blinding>
                            <randomisationProportion/>
                        </regimenAllocation>
                    </treatment>
                    <followup>
                        <duration>45</duration>
                        <feverMeasurement>tympanic</feverMeasurement>
                        <haemoglobinRecording>
                            <haemoglobinRecordingType>both</haemoglobinRecordingType>
                        </haemoglobinRecording>
                    </followup>
                    <microscopy>
                        <microscopyStain>other</microscopyStain>
                        <microscopyStainOther>other micro</microscopyStainOther>
                        <asexualParasitemia>
                            <asexualParasitemiaNegativeCount>55</asexualParasitemiaNegativeCount>
                            <asexualParasitemiaPositiveThickUnit>500wbc</asexualParasitemiaPositiveThickUnit>
                            <asexualParasitemiaPositiveThickUnitOther/>
                            <asexualParasitemiaPositiveThinUnit>other</asexualParasitemiaPositiveThinUnit>
                            <asexualParasitemiaPositiveThinUnitOther>other thin films</asexualParasitemiaPositiveThinUnitOther>
                        </asexualParasitemia>
                        <sexualParasitemia>
                            <sexualParasitemiaNegativeCount/>
                            <sexualParasitemiaPositiveThickUnit>500wbc</sexualParasitemiaPositiveThickUnit>
                            <sexualParasitemiaPositiveThickUnitOther/>
                            <sexualParasitemiaPositiveThinUnit>other</sexualParasitemiaPositiveThinUnit>
                            <sexualParasitemiaPositiveThinUnitOther>other thin films (sexual)</sexualParasitemiaPositiveThinUnitOther>
                        </sexualParasitemia>
                        <thickFilmCalculationOfParasitemia>
                            <thickFilmFormula>other</thickFilmFormula>
                            <thickFilmFormulaOther>other thick film</thickFilmFormulaOther>
                        </thickFilmCalculationOfParasitemia>
                        <thinFilmCalculationOfParasitemia>
                            <thinFilmFormula>other</thinFilmFormula>
                            <thinFilmFormulaOther>other thin film</thinFilmFormulaOther>
                        </thinFilmCalculationOfParasitemia>
                        <qualityControl>
                            <internal>
                                <percentageRereadBySecondMicroscopist>56</percentageRereadBySecondMicroscopist>
                                <rereadSlideSelectionMechanism>random</rereadSlideSelectionMechanism>
                                <rereadSlideSelectionMechanismOther/>
                            </internal>
                            <external>
                                <percentageRereadBySecondMicroscopist>45</percentageRereadBySecondMicroscopist>
                                <rereadSlideSelectionMechanism>other</rereadSlideSelectionMechanism>
                                <rereadSlideSelectionMechanismOther>other ext reread</rereadSlideSelectionMechanismOther>
                            </external>
                        </qualityControl>
                    </microscopy>
                    <geneotypingToDistinguishBetweenRecrudescenceAndReinfection>
                        <applicability>true</applicability>
                        <applicable>
                            <markers>
                                <recrudescenceMarker>
                                    <markerName>other</markerName>
                                    <markerOther>other marker</markerOther>
                                    <numberOfMicroSatellites/>
                                </recrudescenceMarker>
                            </markers>
                            <genotypingLaboratory>lab1</genotypingLaboratory>
                            <markerDiscriminantOpen>
                                <markerDiscriminant>capElectrophoresis</markerDiscriminant>
                                <markerDiscriminantOther/>
                            </markerDiscriminantOpen>
                            <analysisProtocol>
                                <mixedAllelesOpen>
                                    <mixedAlleles>reinfection</mixedAlleles>
                                    <mixedAllelesOther/>
                                    <recrudescence/>
                                    <reinfection>new</reinfection>
                                </mixedAllelesOpen>
                            </analysisProtocol>
                        </applicable>
                    </geneotypingToDistinguishBetweenRecrudescenceAndReinfection>
                </clinical>
                <molecular>
                    <criteria>
                        <sampleSourceOpen>
                            <sampleSource>clinic</sampleSource>
                            <sampleSourceOther/>
                        </sampleSourceOpen>
                        <malariaStatus>asymptomatic</malariaStatus>
                    </criteria>
                    <sample>
                        <sampleTypeOpen>
                            <sampleType>exVivoParasite</sampleType>
                            <sampleTypeOther/>
                            <wholeBloodSource/>
                            <dateCultureIsolated/>
                        </sampleTypeOpen>
                    </sample>
                    <genotypedMarkers>
                        <genotypedMarker>
                            <genotypingMethodOpen>
                                <genotypingMethod>pyrosequencing</genotypingMethod>
                                <genotypingMethodOther/>
                            </genotypingMethodOpen>
                            <molecularMarkerFalciparumOpen>
                                <molecularMarkerFalciparum>pfcrt72</molecularMarkerFalciparum>
                                <molecularMarkerFalciparumOther/>
                            </molecularMarkerFalciparumOpen>
                            <molecularMarkerVivaxOpen>
                                <molecularMarkerVivax>pvmdr1-976</molecularMarkerVivax>
                                <molecularMarkerVivaxOther/>
                            </molecularMarkerVivaxOpen>
                            <molecularMarkerOvaleOpen>
                                <molecularMarkerOvale>other</molecularMarkerOvale>
                                <molecularMarkerOvaleOther>other ovale</molecularMarkerOvaleOther>
                            </molecularMarkerOvaleOpen>
                            <molecularMarkerMalariaeOpen>
                                <molecularMarkerMalariae/>
                                <molecularMarkerMalariaeOther/>
                            </molecularMarkerMalariaeOpen>
                            <molecularMarkerKnowlesiOpen>
                                <molecularMarkerKnowlesi/>
                                <molecularMarkerKnowlesiOther/>
                            </molecularMarkerKnowlesiOpen>
                            <sampleSelectionMethodOpen>
                                <sampleSelectionMethod>randomGenotyped</sampleSelectionMethod>
                                <sampleSelectionMethodOther/>
                                <sampleSelectionProportion>45</sampleSelectionProportion>
                            </sampleSelectionMethodOpen>
                        </genotypedMarker>
                        <genotypedMarker>
                            <genotypingMethodOpen>
                                <genotypingMethod/>
                                <genotypingMethodOther/>
                            </genotypingMethodOpen>
                            <molecularMarkerFalciparumOpen>
                                <molecularMarkerFalciparum/>
                                <molecularMarkerFalciparumOther/>
                            </molecularMarkerFalciparumOpen>
                            <molecularMarkerVivaxOpen>
                                <molecularMarkerVivax/>
                                <molecularMarkerVivaxOther/>
                            </molecularMarkerVivaxOpen>
                            <molecularMarkerOvaleOpen>
                                <molecularMarkerOvale/>
                                <molecularMarkerOvaleOther/>
                            </molecularMarkerOvaleOpen>
                            <molecularMarkerMalariaeOpen>
                                <molecularMarkerMalariae/>
                                <molecularMarkerMalariaeOther/>
                            </molecularMarkerMalariaeOpen>
                            <molecularMarkerKnowlesiOpen>
                                <molecularMarkerKnowlesi/>
                                <molecularMarkerKnowlesiOther/>
                            </molecularMarkerKnowlesiOpen>
                            <sampleSelectionMethodOpen>
                                <sampleSelectionMethod/>
                                <sampleSelectionMethodOther/>
                                <sampleSelectionProportion/>
                            </sampleSelectionMethodOpen>
                        </genotypedMarker>
                    </genotypedMarkers>
                    <mixedResistanceAlleles>
                        <mixedResistanceAllelesInclusion>included</mixedResistanceAllelesInclusion>
                        <mixedResistanceAllelesDesignation>mixed</mixedResistanceAllelesDesignation>
                    </mixedResistanceAlleles>
                    <additionalGenotypicInformation>
                        <sequencedLoci>
                            <wholeGenomesSequenced>false</wholeGenomesSequenced>
                            <resistanceLociSequenced>true</resistanceLociSequenced>
                            <resistanceLoci>
                                <resistanceLocus>
                                    <locusTypeOpen>
                                        <locusType/>
                                        <locusTypeOther/>
                                    </locusTypeOpen>
                                    <locusName>locus1</locusName>
                                </resistanceLocus>
                            </resistanceLoci>
                            <otherLociGenotyped>true</otherLociGenotyped>
                            <otherLoci>
                                <otherLocus>
                                    <locusTypeOpen>
                                        <locusType>gene</locusType>
                                        <locusTypeOther/>
                                    </locusTypeOpen>
                                    <locusName>locus2</locusName>
                                </otherLocus>
                            </otherLoci>
                        </sequencedLoci>
                        <infectionComplexityEstimated>true</infectionComplexityEstimated>
                        <infectionComplexityEstimationlociOpen>
                            <infectionComplexityEstimationloci/>
                            <infectionComplexityEstimationlociOther/>
                        </infectionComplexityEstimationlociOpen>
                    </additionalGenotypicInformation>
                </molecular>
                <invitro>
                    <analysisSite>somewhere</analysisSite>
                    <culture>
                        <incubatorSystem>co2incubator</incubatorSystem>
                        <co2percentage>10</co2percentage>
                        <co2Other/>
                        <o2percentage/>
                        <o2Other/>
                        <healthyErythrocytesSource>bloodbank</healthyErythrocytesSource>
                        <hematocritpercent>45</hematocritpercent>
                        <bloodGroup>O</bloodGroup>
                    </culture>
                    <drugSusceptibilityMedium>
                        <medium>RPMI1640</medium>
                        <mediumOther/>
                        <preparation>extemporaneous</preparation>
                        <serum>Albumax</serum>
                        <serum-finalConcentration>10%</serum-finalConcentration>
                        <NaHCO3-finalConcentration>10 mM</NaHCO3-finalConcentration>
                        <hypoxantine>
                            <hypoxantine-added>true</hypoxantine-added>
                            <hypoxantine-finalConcentration>0.0005</hypoxantine-finalConcentration>
                        </hypoxantine>
                        <oroticAcid>
                            <oroticAcid-added>true</oroticAcid-added>
                            <oroticAcid-finalConcentration>0.00025</oroticAcid-finalConcentration>
                        </oroticAcid>
                        <glucose>
                            <glucose-added>true</glucose-added>
                            <glucose-finalConcentration>8</glucose-finalConcentration>
                        </glucose>
                        <antibioticTreatments>
                            <antibioticTreatment>
                                <antibioticOpen>
                                    <antibiotic>gentamycin</antibiotic>
                                    <antibioticOther/>
                                </antibioticOpen>
                                <antibioticFinalConcentration>9</antibioticFinalConcentration>
                            </antibioticTreatment>
                        </antibioticTreatments>
                    </drugSusceptibilityMedium>
                    <susceptibility>
                        <timeOfIncubation>48h</timeOfIncubation>
                        <susceptibilityMethod>HRP2ELISA</susceptibilityMethod>
                    </susceptibility>
                    <invitroDrugs>
                        <invitroDrug>
                            <molecule>AV</molecule>
                            <solvent>methanol</solvent>
                            <solventFinalConcentration>10</solventFinalConcentration>
                            <providedByWwarn>false</providedByWwarn>
                            <provider>somebody</provider>
                        </invitroDrug>
                    </invitroDrugs>
                    <platePreparationMethod>batches</platePreparationMethod>
                    <platesPreparationDate>2011-03-25</platesPreparationDate>
                    <plateBatches>
                        <batch>
                            <clone3D7ProvidedByWwarn>true</clone3D7ProvidedByWwarn>
                            <clone3D7Provider/>
                            <batchesIncludedInRawData>false</batchesIncludedInRawData>
                            <preparationDate>2011-03-26</preparationDate>
                            <parasitaemia3D7percentage>99</parasitaemia3D7percentage>
                            <ringFormingPercentage>23</ringFormingPercentage>
                        </batch>
                    </plateBatches>
                </invitro>
                <pharmacology>
                    <studyDesign/>
                    <samplingTimes/>
                    <PKcomments/>
                    <samples>
                        <sample>
                            <anticoagulent>anticoag</anticoagulent>
                            <centrifugeTime>5</centrifugeTime>
                            <storages>
                                <storage>
                                    <storageTemperature>55</storageTemperature>
                                    <storageDuration>55</storageDuration>
                                    <storageDurationUnit>minutes</storageDurationUnit>
                                </storage>
                            </storages>
                        </sample>
                    </samples>
                    <analytes>
                        <analyte>
                            <drugMeasured>PQP</drugMeasured>
                            <targetDose>55</targetDose>
                            <unitsOfMeasure/>
                            <unitsOfMeasureOther/>
                            <fatAmount/>
                            <lowerLoQ/>
                            <sampleMatrixType>venousPlasma</sampleMatrixType>
                        </analyte>
                        <analyte>
                            <drugMeasured>AZ</drugMeasured>
                            <targetDose/>
                            <unitsOfMeasure/>
                            <unitsOfMeasureOther/>
                            <fatAmount/>
                            <lowerLoQ/>
                            <sampleMatrixType>venous</sampleMatrixType>
                        </analyte>
                    </analytes>
                    <assayReferences>
                        <assayReference>
                            <referenceType>doi</referenceType>
                            <url/>
                            <doi>10.1128/AAC.48.10.3940-3943.2004</doi>
                            <upload>
                                <uploadedUrl/>
                            </upload>
                            <note>notes on a refe</note>
                            <assayValidated/>
                        </assayReference>
                        <assayReference>
                            <referenceType>url</referenceType>
                            <url>http://localhost:8080/repository</url>
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
            <atombeat:group xmlns:atombeat="http://purl.org/atombeat/xmlns" id="GROUP_ADMINISTRATORS">
                <atombeat:member>cora@example.org</atombeat:member>
                <atombeat:member>colin@example.org</atombeat:member>
            </atombeat:group>
        </study>
    </atom:content>
    <ar:comment xmlns:ar="http://purl.org/atompub/revision/1.0">
        <atom:author>
            <atom:email>cora@example.org</atom:email>
        </atom:author>
        <atom:updated>2012-09-07T14:10:08.045+01:00</atom:updated>
        <atom:summary/>
    </ar:comment>
    <manta:id xmlns:manta="http://www.cggh.org/2010/chassis/manta/xmlns">BKPQY</manta:id>
    <atom:link xmlns:atombeat="http://purl.org/atombeat/xmlns" atombeat:allow="GET,PUT,DELETE" rel="self" type="application/atom+xml;type=entry" href="https://kwiat33/repository/service/content/studies/BKPQY"/>
    <atom:link xmlns:atombeat="http://purl.org/atombeat/xmlns" atombeat:allow="GET,PUT,DELETE" rel="edit" type="application/atom+xml;type=entry" href="https://kwiat33/repository/service/content/studies/BKPQY"/>
    <atom:link xmlns:atombeat="http://purl.org/atombeat/xmlns" atombeat:allow="" rel="service" type="application/atomsvc+xml" href="https://kwiat33/repository/service/"/>
    <atom:link xmlns:atombeat="http://purl.org/atombeat/xmlns" atombeat:allow="GET,PUT" rel="http://purl.org/atombeat/rel/security-descriptor" href="https://kwiat33/repository/service/security/studies/BKPQY" type="application/atom+xml;type=entry"/>
    <atom:link xmlns:atombeat="http://purl.org/atombeat/xmlns" atombeat:allow="GET" rel="history" href="https://kwiat33/repository/service/history/studies/BKPQY" type="application/atom+xml;type=feed"/>
    <atom:link xmlns:atombeat="http://purl.org/atombeat/xmlns" atombeat:allow="GET" rel="http://www.cggh.org/2010/chassis/terms/submittedMedia" href="https://kwiat33/repository/service/content/media/submitted/BKPQY" type="application/atom+xml;type=feed"/>
    <atom:link xmlns:atombeat="http://purl.org/atombeat/xmlns" atombeat:allow="GET,POST" rel="http://www.cggh.org/2010/chassis/terms/curatedMedia" href="https://kwiat33/repository/service/content/media/curated/BKPQY" type="application/atom+xml;type=feed"/>
    <atom:link xmlns:atombeat="http://purl.org/atombeat/xmlns" atombeat:allow="GET,POST" rel="http://www.cggh.org/2010/chassis/terms/derivations" href="https://kwiat33/repository/service/content/derivations/BKPQY" type="application/atom+xml;type=feed"/>
    <atom:link xmlns:atombeat="http://purl.org/atombeat/xmlns" atombeat:allow="" rel="http://www.cggh.org/2010/chassis/terms/personalDataReviews" href="https://kwiat33/repository/service/content/reviews/personal-data/BKPQY" type="application/atom+xml;type=feed"/>
    <atom:link xmlns:atombeat="http://purl.org/atombeat/xmlns" atombeat:allow="GET,PUT" rel="http://www.cggh.org/2010/chassis/terms/studyInfo" href="https://kwiat33/repository/service/content/study-info/BKPQY" type="application/atom+xml;type=entry"/>
    <atom:link xmlns:atombeat="http://purl.org/atombeat/xmlns" atombeat:allow="GET,PUT" rel="http://www.cggh.org/2010/chassis/terms/groups" href="https://kwiat33/repository/service/content/groups/BKPQY" type="application/atom+xml;type=entry"/>
    <atom:link xmlns:atombeat="http://purl.org/atombeat/xmlns" atombeat:allow="" rel="http://www.cggh.org/2010/chassis/terms/link" href="https://kwiat33/repository/service/content/link?study=https://kwiat33/repository/service/content/studies/BKPQY" type="application/atom+xml;type=entry"/>
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
                <title>Data Migration - Study Info v2.1 to v3.0</title>
            </head>
            <body>
                <h1>Data Migration - Study v2.1 to v3.0</h1>
                <p>Total number of entries in <a href="../../content/studies">Studies</a> collection: <strong>{ count( $study-infos ) }</strong></p>
                <p>Number of entries in <a href="../../content/studies">Studies</a> collection using v2.1 profile: <strong>{ count( $old-study-infos ) }</strong></p>
                <p>Number of entries in <a href="../../content/studies">Studies</a> collection using v3.0 profile: <strong>{ count( $new-study-infos ) }</strong></p>
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
    
    let $ret := $study-infos[atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/3.0']
    return $ret
};

declare function local:get-old-versioned-content($study-infos) as element( atom:entry )* {
    
    let $ret := $study-infos[atom:content/study/@profile='http://www.cggh.org/2010/chassis/manta/2.1']
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
            let $profile := update replace $old//study/@profile with "http://www.cggh.org/2010/chassis/manta/3.0"
			let $sl := update insert <studyLabel/> following $old//studyTitle
			let $pa := update insert <precursorAddedOther/> following $old//susceptibilityMethod
			let $po := update insert <precursorAdded/> following $old//susceptibilityMethod
			let $rco := update insert <referenceCloneOther/> following $old//plateBatches
			let $rc := update insert <referenceClone/> following $old//plateBatches
			let $ti := update insert <timeOfIncubationOther/> following $old//timeOfIncubation
			let $til := update insert <transmissionIntensityLevel /> following $old//parasitePrevalenceYear
			let $d := update replace $old//draft[. = 'no'] with <app:draft>no</app:draft>
			let $d1 := update replace $old//draft[. = 'yes'] with <app:draft>yes</app:draft>
			let $sfc := replace($old//serum-finalConcentration/text(),'%','')
			let $rsfc := update replace $old//serum-finalConcentration/text() with $sfc
			let $hfc := $old//hypoxantine-finalConcentration/text() * 1000
			let $rhfc := update replace $old//hypoxantine-finalConcentration/text() with $hfc
			let $ofc := $old//oroticAcid-finalConcentration/text() * 1000
			let $rofc := update replace $old//oroticAcid-finalConcentration/text() with $ofc
			let $nfc := normalize-space(replace($old//NaHCO3-finalConcentration/text(),'mM',''))
			let $rnfc := update replace $old//NaHCO3-finalConcentration/text() with $nfc
			let $matrix := if (count($old//sampleMatrixType) > 0) then
				($old//sampleMatrixType)[1]
				else <sampleMatrixType />
			
			let $matIn := update insert $matrix following $old//pharmacology/analytes
			let $matOut := update delete $old//pharmacology/analytes/analyte/sampleMatrixType
			
			let $dsi := update insert <derived-study-id/> following $old//registrant-has-agreed-to-the-terms
			let $pi := update insert <proxy-for-institution/> following $old//registrant-has-agreed-to-the-terms
			let $pn := update insert <proxy-for-name/> following $old//registrant-has-agreed-to-the-terms
			
        return $profile

     
    return $new-study-infos
    
};



declare function local:check-changes() as item() *{
  let $all := local:get-content()
 let $old := local:get-old-versioned-content($all)
 let $new :=  local:get-new-versioned-content($all)
 let $summary := concat('Old studies:',count($old),' New studies:',count($new),'&#xD;')
 let $ret := for $m in $new 
         let $out := if (count($m//serum-finalConcentration[. = '10%']) > 0 or count($m//serum-finalConcentration[. = '10']) = 0) then 
             let $msg := "Failed to change serum-finalConcentration"
             return $msg
          else
             let $msg := "Changed serum-finalConcentration"
             return $msg
          let $summary1 := concat($summary,$out,'&#xD;')
          let $out := if (count($m//NaHCO3-finalConcentration[. = '10 mM']) > 0 or count($m//NaHCO3-finalConcentration[. = '10']) = 0) then 
             let $msg := "Failed to change NaHCO3-finalConcentration"
             return $msg
          else
             let $msg := "Changed NaHCO3-finalConcentration"
             return $msg
          let $summary := concat($summary1,$out,'&#xD;')
          let $out := if (count($m//hypoxantine-finalConcentration[. = '0.0005']) > 0 or count($m//hypoxantine-finalConcentration[. = '0.5']) = 0) then 
             let $msg := "Failed to change hypoxantine-finalConcentration"
             return $msg
          else
             let $msg := "Changed hypoxantine-finalConcentration"
             return $msg
          let $summary1 := concat($summary,$out,'&#xD;')
          let $out := if (count($m//precursorAddedOther) = 0) then 
             let $msg := "Failed to add precursorAddedOther"
             return $msg
          else
             let $msg := "Added precursorAddedOther"
             return $msg
         let $summary := concat($summary1,$out,'&#xD;')
         let $out := if (count($m//precursorAdded) = 0 ) then 
             let $msg := "Failed to add precursorAdded"
             return $msg
          else
             let $msg := "Added precursorAdded"
             return $msg
          let $summary1 := concat($summary,$out,'&#xD;')
          let $out := if (count($m//referenceCloneOther) = 0) then 
             let $msg := "Failed to add referenceCloneOther"
             return $msg
          else
             let $msg := "Added referenceCloneOther"
             return $msg
         let $summary := concat($summary1,$out,'&#xD;')
         let $out := if (count($m//referenceClone) = 0 ) then 
             let $msg := "Failed to add referenceClone"
             return $msg
          else
             let $msg := "Added referenceClone"
             return $msg
          let $summary1 := concat($summary,$out,'&#xD;')
          let $out := if (count($m//timeOfIncubationOther) = 0) then 
             let $msg := "Failed to add timeOfIncubationOther"
             return $msg
          else
             let $msg := "Added timeOfIncubationOther"
             return $msg
         let $summary := concat($summary1,$out,'&#xD;')
         let $out := if (count($m//transmissionIntensityLevel) = 0 ) then 
             let $msg := "Failed to add transmissionIntensityLevel"
             return $msg
          else
             let $msg := "Added transmissionIntensityLevel"
             return $msg
         let $summary1 := concat($summary,$out,'&#xD;')
         let $out := if (count($m//pharmacology/sampleMatrixType) != 1 or count($m//pharmacology/analytes/analyte/sampleMatrixType) > 0) then 
             let $msg := "Failed to move sampleMatrixType"
             return $msg
          else
             let $msg := "Moved sampleMatrixType"
             return $msg
          let $summary := concat($summary1,$out,'&#xD;')
          let $out := if (count($m//proxy-for-name) = 0) then 
             let $msg := "Failed to add proxy-for-name"
             return $msg
          else
             let $msg := "Added proxy-for-name"
             return $msg
         let $summary1 := concat($summary,$out,'&#xD;')
         let $out := if (count($m//proxy-for-institution) = 0 ) then 
             let $msg := "Failed to add proxy-for-institution"
             return $msg
          else
             let $msg := "Added proxy-for-institution"
             return $msg
         let $summary := concat($summary1,$out,'&#xD;')
        let $out := if (count($m//derived-study-id) = 0 ) then 
             let $msg := "Failed to add derived-study-id"
             return $msg
          else
             let $msg := "Added derived-study-id"
             return $msg
         
         let $output := concat($summary,$out,'&#xD;')
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
    
    
