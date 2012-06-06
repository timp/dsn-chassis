
CREATE OR REPLACE SQL SECURITY INVOKER VIEW `v_PeopleAcks` AS
	SELECT StudyID, FirstName, MiddleName, FamilyName, EmailAddress, PersonIsContactable FROM Person
        JOIN People p ON Person_People_Hjid = p.Hjid
        JOIN AcksInstitutionsOrPeopleItem ai ON ai.ItemPeople_AcksInstitutionsOrPeopleItem_Hjid = p.Hjid
        JOIN Acks a ON ai.InstitutionsOrPeopleItems_Acks_Hjid = a.Hjid
        JOIN `Study` `s` on`s`.`Acknowledgements_Study_Hjid` = `a`.`Hjid`
		JOIN `Content` `c` on`c`.`Study_Content_Hjid` = `s`.`Hjid`
		JOIN `Entry` `e` on`e`.`Content_Entry_Hjid` = `c`.`Hjid`;
		
CREATE OR REPLACE SQL SECURITY INVOKER VIEW `v_InstitutionURL` AS
	SELECT StudyID,ia.Hjid AS InstitutionId,Item AS URL FROM InstWebInstitutionUrlItem url
        JOIN InstWeb iw ON url.InstitutionUrlItems_InstWeb_Hjid = iw.Hjid
        JOIN InstitutionAck ia ON ia.InstitutionWebsites_InstitutionAck_Hjid = iw.Hjid
        JOIN Institutions i ON ia.InstitutionAck_Institutions_Hjid = i.Hjid
        JOIN AcksInstitutionsOrPeopleItem ai ON ai.ItemInstitutions_AcksInstitutionsOrPeopleItem_Hjid = i.Hjid
        JOIN Acks a ON ai.InstitutionsOrPeopleItems_Acks_Hjid = a.Hjid
        JOIN `Study` `s` on`s`.`Acknowledgements_Study_Hjid` = `a`.`Hjid`
		JOIN `Content` `c` on`c`.`Study_Content_Hjid` = `s`.`Hjid`
		JOIN `Entry` `e` on`e`.`Content_Entry_Hjid` = `c`.`Hjid`;
		
CREATE OR REPLACE SQL SECURITY INVOKER VIEW `v_StudyInstitutions` AS
	SELECT StudyID,InstitutionName,ia.Hjid AS InstitutionId FROM InstitutionAck ia
        JOIN Institutions i ON ia.InstitutionAck_Institutions_Hjid = i.Hjid
        JOIN AcksInstitutionsOrPeopleItem ai ON ai.ItemInstitutions_AcksInstitutionsOrPeopleItem_Hjid = i.Hjid
        JOIN Acks a ON ai.InstitutionsOrPeopleItems_Acks_Hjid = a.Hjid
        JOIN `Study` `s` on`s`.`Acknowledgements_Study_Hjid` = `a`.`Hjid`
		JOIN `Content` `c` on`c`.`Study_Content_Hjid` = `s`.`Hjid`
		JOIN `Entry` `e` on`e`.`Content_Entry_Hjid` = `c`.`Hjid`;

CREATE OR REPLACE SQL SECURITY INVOKER VIEW `v_LinkedStudyGroupMembers` AS
	SELECT Link_Entry_StudyID,e.StudyID FROM `Link` l
		JOIN Entry e ON l.Href = e.Id
	WHERE Rel = 'http://www.cggh.org/2010/chassis/terms/linkMember' AND Link_Entry_StudyID IS NOT NULL;

CREATE OR REPLACE SQL SECURITY INVOKER VIEW `v_LinkedStudyGroups` AS
	SELECT StudyID, t.Content FROM `Entry` e
		JOIN Title t ON e.Title_Entry_Hjid = t.Hjid
	WHERE Id LIKE '%link%';


CREATE OR REPLACE SQL SECURITY INVOKER VIEW `v_MolecularMarkers` AS
SELECT e.StudyID,GenotypingMethod,GenotypingMethodOther,SampleSelectionProportion,SampleSelectionMethod,SampleSelectionMethodOther,
MolecularMarkerVivax, MolecularMarkerVivaxOther,
MolecularMarkerOvale, MolecularMarkerOvaleOther,
MolecularMarkerKnowlesi, MolecularMarkerKnowlesiOther,
MolecularMarkerMalariae, MolecularMarkerMalariaeOther,
MolecularMarkerFalciparum, MolecularMarkerFalciparumOther
FROM `GenotypedMarker` gm
 LEFT JOIN GenotypingMethodOpen gmo ON gmo.Hjid = gm.GenotypingMethodOpen_GenotypedMarker_Hjid
 LEFT JOIN SampleSelectionMethodOpen sso ON sso.Hjid = gm.SampleSelectionMethodOpen_GenotypedMarker_Hjid
 LEFT JOIN MolecularMarkerVivaxOpen vo ON vo.Hjid = gm.MolecularMarkerVivaxOpen_GenotypedMarker_Hjid
 LEFT JOIN MolecularMarkerOvaleOpen oo ON oo.Hjid = gm.MolecularMarkerOvaleOpen_GenotypedMarker_Hjid
 LEFT JOIN MolecularMarkerKnowlesiOpen ko ON ko.Hjid = gm.MolecularMarkerKnowlesiOpen_GenotypedMarker_Hjid
 LEFT JOIN MolecularMarkerMalariaeOpen mo ON mo.Hjid = gm.MolecularMarkerMalariaeOpen_GenotypedMarker_Hjid
 LEFT JOIN MolecularMarkerFalciparumOpen fo ON fo.Hjid = gm.MolecularMarkerFalciparumOpen_GenotypedMarker_Hjid
 LEFT JOIN GenotypedMarkers gms ON gms.Hjid = gm.GenotypedMarker_GenotypedMarkers_Hjid
 JOIN Molecular m on m.GenotypedMarkers_Molecular_Hjid = gms.Hjid
  join `StudyInfo` `si` on`si`.`Molecular_StudyInfo_Hjid` = `m`.`Hjid`
		 join `Study` `s` on`s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
		 join `Content` `c` on`c`.`Study_Content_Hjid` = `s`.`Hjid`
		 join `Entry` `e` on`e`.`Content_Entry_Hjid` = `c`.`Hjid`
         WHERE Modules LIKE '%molecular%' and GenotypingMethod <> '';



CREATE OR REPLACE SQL SECURITY INVOKER VIEW `v_ClinicalMarkers` AS
SELECT e.StudyID, MarkerName, MarkerOther, NumberOfMicroSatellites FROM `RecrudescenceMarker` rm
JOIN Markers m on rm.RecrudescenceMarker_Markers_Hjid = m.Hjid
JOIN Applicable a on a.Markers_Applicable_Hjid = m.Hjid
JOIN GenotypingToDistinguish gtd on Applicable_GenotypingToDistinguish_Hjid = a.Hjid
JOIN Clinical c ON c.GenotypingForRecrudescence_Clinical_Hjid = gtd.Hjid
 JOIN `StudyInfo` `si` ON `si`.`Clinical_StudyInfo_Hjid` = `c`.`Hjid`
     JOIN `Study` `s` ON `s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
     JOIN `Content` `cont` ON `cont`.`Study_Content_Hjid` = `s`.`Hjid`
     JOIN `Entry` `e` ON `e`.`Content_Entry_Hjid` = `cont`.`Hjid`
WHERE gtd.Applicability = 'true' and
 s.Modules LIKE "%clinical%" and NOT (MarkerName = '' and MarkerOther = '');

CREATE OR REPLACE SQL SECURITY INVOKER VIEW `v_Clinical` AS
SELECT e.StudyId, s.StudyInfo_Study_Hjid,   e.PublishedItem as DateReceived,   s.Modules ,s.StudyStatus
	FROM  `Study` `s` 
     JOIN `Content` `c` ON `c`.`Study_Content_Hjid` = `s`.`Hjid`
     JOIN `Entry` `e` ON `e`.`Content_Entry_Hjid` = `c`.`Hjid`
	WHERE s.Modules LIKE "%clinical%" 
;
CREATE OR REPLACE SQL SECURITY INVOKER VIEW `v_Molecular` AS
SELECT StudyID,SampleSource,SampleSourceOther,MalariaStatus, SampleType,SampleTypeOther,WholeBloodSource,DateCultureIsolated,InfectionComplexityEstimated,OtherLociGenotyped,WholeGenomesSequenced,ResistanceLociSequenced FROM `Molecular` m 
   JOIN `Criteria` crit ON m.Criteria_Molecular_Hjid = crit.Hjid
   LEFT JOIN `Sample` sam ON m.Sample_Molecular_Hjid = sam.Hjid
    join `SampleTypeOpen` st on`sam`.`SampleTypeOpen_Sample_Hjid` = `st`.`Hjid`
    join `SampleSourceOpen` sso on`crit`.`SampleSourceOpen_Criteria_Hjid` = `sso`.`Hjid`
    LEFT JOIN GenotypicInfo gi ON m.GenotypicInfo_Molecular_Hjid = gi.Hjid
    LEFT JOIN GenotypedMarkers gm ON m.GenotypedMarkers_Molecular_Hjid = gi.Hjid
    LEFT JOIN `SequencedLoci` sl ON gi.SequencedLoci_GenotypicInfo_Hjid = sl.Hjid
		 join `StudyInfo` `si` on`si`.`Molecular_StudyInfo_Hjid` = `m`.`Hjid`
		 join `Study` `s` on`s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
		 join `Content` `c` on`c`.`Study_Content_Hjid` = `s`.`Hjid`
		 join `Entry` `e` on`e`.`Content_Entry_Hjid` = `c`.`Hjid`
         WHERE Modules LIKE '%molecular%';


CREATE OR REPLACE SQL SECURITY INVOKER VIEW `v_Followup` AS
SELECT StudyID,Duration,FeverMeasurement FROM `Followup` f
join `Clinical` clin on clin.`Followup_Clinical_Hjid` = `f`.`Hjid`
		 join `StudyInfo` `si` on `si`.`Clinical_StudyInfo_Hjid` = clin.`Hjid`
		 join v_Clinical `s` on`s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`;


CREATE OR REPLACE SQL SECURITY INVOKER VIEW `v_InclusionCriteria` AS
SELECT StudyId,
    MinAge,
    MinAgeUnits,
    MaxAge,
    MaxAgeUnits,
    MinParasitaemia,
    MaxParasitaemia,
    ExcludeIfPriorAntimalarials,
    PriorAntimalarialsDetermination,
    PriorAntimalarialsHistoryWeeks,
    Pregnancy 
FROM `InclusionExclusionCriteria` iec
JOIN PriorAntimalarialsExclusion pae ON pae.Hjid = iec.PriorAntimalarialsExclusion_InclusionExclusionCriteria_Hjid
JOIN Parasitaemia pe ON pe.Hjid = iec.Parasitaemia_InclusionExclusionCriteria_Hjid
JOIN Age ae ON ae.Hjid = iec.Age_InclusionExclusionCriteria_Hjid
join `StudyInfo` `si` on`si`.`InclusionExclusionCriteria_StudyInfo_Hjid` = `iec`.`Hjid`
join `Study` `s` on `s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
 join `Content` `c` on `c`.`Study_Content_Hjid` = `s`.`Hjid`
 join `Entry` `e` on `e`.`Content_Entry_Hjid` = `c`.`Hjid`;

CREATE OR REPLACE SQL SECURITY INVOKER VIEW `v_PublicationReferences` AS
SELECT StudyId, PublicationReferenceType,Identifier,p.Hjid as publicationId FROM PublicationReference pr
	JOIN PublicationReferences prs ON pr.PublicationReference_PublicationReferences_Hjid = prs.Hjid
	JOIN `Publication` p ON p.PublicationReferences_Publication_Hjid = prs.Hjid
	JOIN `Publications` ps on p.Publication_Publications_Hjid = ps.Hjid
    join `Study` `s` on `s`.`Publications_Study_Hjid` = `ps`.`Hjid`
	join `Content` `c` on `c`.`Study_Content_Hjid` = `s`.`Hjid`
	join `Entry` `e` on `e`.`Content_Entry_Hjid` = `c`.`Hjid`;

		 
CREATE OR REPLACE SQL SECURITY INVOKER VIEW `v_Publications` AS
SELECT StudyId,Pmid,PublicationTitle,p.Hjid as publicationId FROM `Publication` p
	JOIN `Publications` ps on p.Publication_Publications_Hjid = ps.Hjid
    join `Study` `s` on `s`.`Publications_Study_Hjid` = `ps`.`Hjid`
	join `Content` `c` on `c`.`Study_Content_Hjid` = `s`.`Hjid`
	join `Entry` `e` on `e`.`Content_Entry_Hjid` = `c`.`Hjid`;

CREATE OR REPLACE SQL SECURITY INVOKER VIEW `v_StudyInfo` AS
select `e`.`StudyID` AS `StudyID`,
			StartDate,
            EndDate,
            StudyInfoStatus,
            Pathogens
            FROM `StudyInfo` `si` 
		 join `Study` `s` on`s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
		 join `Content` `c` on`c`.`Study_Content_Hjid` = `s`.`Hjid`
		 join `Entry` `e` on`e`.`Content_Entry_Hjid` = `c`.`Hjid`;

	
CREATE OR REPLACE SQL SECURITY INVOKER VIEW `v_StudyDetails` AS 

	select `e`.`StudyID` AS `StudyID`
			,`s`.`StudyTitle` AS `studyTitle`
			,`s`.`StudyStatus` AS `studyStatus`
			,`s`.`CuratorNotes` AS `curatorNotes`
			,`s`.`StudyIsPublished` AS `studyIsPublished`
			,`s`.`ExplorerDisplay` AS `explorerDisplay`
			,`s`.`Modules` AS `modules`
			,Control.Draft
            , a.email
            ,PublishedItem
            ,UpdatedItem
            FROM `Entry` `e` 
        join `Content` `c` on`e`.`Content_Entry_Hjid` = `c`.`Hjid`
        join `Study` `s` on`c`.`Study_Content_Hjid` = `s`.`Hjid`
        join Control on e.Control_Entry_Hjid = Control.Hjid
        JOIN `Author` a ON a.Hjid = e.Author_Entry_Hjid;


CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `v_ClinicalDrugs` AS 
  SELECT `e`.`StudyID` AS `StudyID`
      ,`Drug`.`Hjid` AS `Hjid`
      ,`Drug`.`DTYPE` AS `DTYPE`
      ,`Drug`.`FeedingOther` AS `FeedingOther`
      ,`Drug`.`DrugName` AS `DrugName`
      ,`Drug`.`Feeding` AS `Feeding`
      ,`Drug`.`TradeName` AS `TradeName`
      ,`Drug`.`DrugDosingDeterminant` AS `DrugDosingDeterminant`
      ,`Drug`.`ManufacturerOther` AS `ManufacturerOther`
      ,`Drug`.`TradeNameOther` AS `TradeNameOther`
      ,`Drug`.`Manufacturer` AS `Manufacturer`
      ,`Drug`.`FatPerMeal` AS `FatPerMeal`
      ,`Drug`.`DrugStorage` AS `DrugStorage`
      ,`Drug`.`DrugNameOther` AS `DrugNameOther`
      ,`Drug`.`Comments` AS `Comments`
      ,`Drug`.`AdministrationRoute` AS `AdministrationRoute`
      ,`Drug`.`ReadministeredOnVomitting` AS `ReadministeredOnVomitting`
      ,`Drug`.`ActiveIngredients_Drug_Hjid` AS `ActiveIngredients_Drug_Hjid`
      ,`Drug`.`WeightGroupDosing_Drug_Hjid` AS `WeightGroupDosing_Drug_Hjid`
      ,`Drug`.`AgeDosing_Drug_Hjid` AS `AgeDosing_Drug_Hjid`
      ,`Drug`.`Batches_Drug_Hjid` AS `Batches_Drug_Hjid`
      ,`Drug`.`WeightDosing_Drug_Hjid` AS `WeightDosing_Drug_Hjid`
      ,`Drug`.`Drug_Drugs_Hjid` AS `Drug_Drugs_Hjid` 
      ,`r`.`Hjid` AS `Regimen_Hjid`
from `Drug` 
     JOIN `Drugs` ON `Drug`.`Drug_Drugs_Hjid` = `Drugs`.`Hjid`
     JOIN `Regimen` `r` ON `r`.`Drugs_Regimen_Hjid` = `Drugs`.`Hjid`
     JOIN `Regimens` `rs` ON `r`.`Regimen_Regimens_Hjid` = `rs`.`Hjid`
     JOIN `Treatment` `t` ON `t`.`Regimens_Treatment_Hjid` = `rs`.`Hjid`
     JOIN `Clinical` ON `Clinical`.`Treatment_Clinical_Hjid` = `t`.`Hjid`
     JOIN `StudyInfo` `si` ON `si`.`Clinical_StudyInfo_Hjid` = `Clinical`.`Hjid`
     JOIN `Study` `s` ON `s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
     JOIN `Content` `c` ON `c`.`Study_Content_Hjid` = `s`.`Hjid`
     JOIN `Entry` `e` ON `e`.`Content_Entry_Hjid` = `c`.`Hjid`
WHERE 
 s.Modules LIKE "%clinical%";
     

CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `v_ClinicalRegimen` AS
SELECT  s.studyId,
    cd1.regimen_Hjid, 
    RegimenName, 
    RegimenUrl,
    RegimenSupervision,
    cd1.drugName as drug1Name,
    cd1.Hjid as drug1_Hjid,
           ai1.ActiveIngredientName as drug1ai1Name,
           ai1.Hjid as drug1ai1_Hjid,
           ai2.ActiveIngredientName as drug1ai2Name,
           ai2.Hjid as drug1ai2_Hjid,
           ai3.ActiveIngredientName as drug1ai3Name,
           ai3.Hjid as drug1ai3_Hjid,
    cd2.drugName as drug2Name,
    cd2.Hjid as drug2_Hjid,
           d2ai1.ActiveIngredientName as drug2ai1Name,
           d2ai1.Hjid as drug2ai1_Hjid,
           d2ai2.ActiveIngredientName as drug2ai2Name,
           d2ai2.Hjid as drug2ai2_Hjid,
           d2ai3.ActiveIngredientName as drug2ai3Name,
           d2ai3.Hjid as drug2ai3_Hjid,
    cd3.drugName as drug3Name,
    cd3.Hjid as drug3_Hjid,
          d3ai1.ActiveIngredientName as drug3ai1Name,
           d3ai1.Hjid as drug3ai1_Hjid,
           d3ai2.ActiveIngredientName as drug3ai2Name,
           d3ai2.Hjid as drug3ai2_Hjid,
           d3ai3.ActiveIngredientName as drug3ai3Name,
           d3ai3.Hjid as drug3ai3_Hjid
FROM `Regimen` r
  LEFT JOIN v_ClinicalDrugs cd1 on cd1.Regimen_Hjid = r.Hjid
  LEFT JOIN ActiveIngredient ai1 on ai1.ActiveIngredient_ActiveIngredients_Hjid = cd1.ActiveIngredients_Drug_Hjid
  LEFT JOIN ActiveIngredient ai2 on (ai2.ActiveIngredient_ActiveIngredients_Hjid = cd1.ActiveIngredients_Drug_Hjid and ai2.hjid <> ai1.Hjid)
  LEFT JOIN ActiveIngredient ai3 on (ai3.ActiveIngredient_ActiveIngredients_Hjid = cd1.ActiveIngredients_Drug_Hjid and ai3.hjid <> ai2.Hjid and ai3.Hjid <> ai1.Hjid)
  LEFT JOIN v_ClinicalDrugs cd2 on (cd2.Regimen_Hjid = r.Hjid and cd2.drugName <> cd1.drugName)
  LEFT JOIN ActiveIngredient d2ai1 on d2ai1.ActiveIngredient_ActiveIngredients_Hjid = cd2.ActiveIngredients_Drug_Hjid
  LEFT JOIN ActiveIngredient d2ai2 on (d2ai2.ActiveIngredient_ActiveIngredients_Hjid = cd2.ActiveIngredients_Drug_Hjid and d2ai2.hjid <> d2ai1.Hjid)
  LEFT JOIN ActiveIngredient d2ai3 on (d2ai3.ActiveIngredient_ActiveIngredients_Hjid = cd2.ActiveIngredients_Drug_Hjid and d2ai3.hjid <> d2ai2.Hjid and d2ai3.Hjid <> d2ai1.Hjid)
  LEFT JOIN v_ClinicalDrugs cd3 on (cd3.Regimen_Hjid = r.Hjid and (cd3.drugName <> cd1.drugName and cd3.drugName <> cd2.drugName))
  LEFT JOIN ActiveIngredient d3ai1 on d3ai1.ActiveIngredient_ActiveIngredients_Hjid = cd3.ActiveIngredients_Drug_Hjid
  LEFT JOIN ActiveIngredient d3ai2 on (d3ai2.ActiveIngredient_ActiveIngredients_Hjid = cd3.ActiveIngredients_Drug_Hjid and d3ai2.hjid <> d3ai1.Hjid)
  LEFT JOIN ActiveIngredient d3ai3 on (d3ai3.ActiveIngredient_ActiveIngredients_Hjid = cd3.ActiveIngredients_Drug_Hjid and d3ai3.hjid <> d3ai2.Hjid and d3ai3.Hjid <> d3ai1.Hjid) 
;

CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `v_ClinicalStudyRegimens` AS 
  SELECT 
      `s`.`StudyID` AS `StudyID`
      ,`s`.`Modules` AS `Modules` 
      , `s`.`StudyStatus`
      , `si`.`StudyInfoStatus`
      ,`r`.`RegimenSupervision` AS `Supervision`
      ,`r`.`RegimenName` AS `RegimenName`
      ,`Drug`.`DrugName` AS `DrugName`
      ,f_otherValue(`Drug`.`FeedingOther`,`Drug`.`Feeding`) AS `Feeding`
      ,`Drug`.`TradeName` AS `TradeName`
      ,`Drug`.`DrugDosingDeterminant` AS `DrugDosingDeterminant`
      ,`Drug`.`ManufacturerOther` AS `ManufacturerOther`
      ,`Drug`.`TradeNameOther` AS `TradeNameOther`
      ,`Drug`.`Manufacturer` AS `Manufacturer`
      ,`Drug`.`FatPerMeal` AS `FatPerMeal`
      ,`Drug`.`DrugStorage` AS `DrugStorage`
      ,`Drug`.`DrugNameOther` AS `DrugNameOther`
      ,`Drug`.`Comments` AS `Comments`
      ,`Drug`.`AdministrationRoute` AS `AdministrationRoute`
      ,`Drug`.`ReadministeredOnVomitting` AS `ReadministeredOnVomitting`
from `Drug` JOIN `Drugs` ON `Drug`.`Drug_Drugs_Hjid` = `Drugs`.`Hjid`
     JOIN `Regimen` `r` ON `r`.`Drugs_Regimen_Hjid` = `Drugs`.`Hjid`
     JOIN `Regimens` `rs` ON `r`.`Regimen_Regimens_Hjid` = `rs`.`Hjid`
     JOIN `Treatment` `t` ON `t`.`Regimens_Treatment_Hjid` = `rs`.`Hjid`
     JOIN `Clinical` ON `Clinical`.`Treatment_Clinical_Hjid` = `t`.`Hjid`
     JOIN `StudyInfo` `si` ON `si`.`Clinical_StudyInfo_Hjid` = `Clinical`.`Hjid`
          JOIN v_Clinical `s` ON `s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`;
     
CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `v_ClinicalCompleteStudyRegimens` AS 
  SELECT * FROM `v_ClinicalStudyRegimens` 
WHERE 
 StudyStatus = 'complete' and
 StudyInfoStatus = 'complete'
;



CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `v_ClinicalStudies` AS 
  SELECT 
   e.StudyId, 
   e.PublishedItem as DateReceived,
   s.Modules, 
   si.StartDate, 
   si.EndDate,
   s.StudyTitle,
   s.StudyStatus,
   si.StudyInfoStatus
  FROM `Entry` `e` 
     JOIN `Content` `c` ON `e`.`Content_Entry_Hjid` = `c`.`Hjid` 
     JOIN `Study` `s` ON `c`.`Study_Content_Hjid` = `s`.`Hjid`
     JOIN `StudyInfo` `si` ON `s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
  WHERE
   s.Modules LIKE "%clinical%"
  ORDER BY e.StudyId
;


CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `ClinicalDrugIngredient` AS 
  SELECT
    ai.ActiveIngredientName,
    ai.ActiveIngredientMgPerDose,
    ai.ActiveIngredientNameOther,
    StudyId
  FROM `ActiveIngredient` ai
    JOIN v_ClinicalDrugs cd ON ai.ActiveIngredient_ActiveIngredients_Hjid = cd.ActiveIngredients_Drug_Hjid;



CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `ClinicalDrugWeightDosing` AS 
  SELECT `wds`.`Hjid`,
    `DosingDay`,`DosingHour`,
    `Dose`,
    `cd`.`StudyId`,
    `cd`. `Hjid` AS `DrugId` 
  FROM `WeightDosingSchedule` wds
    JOIN `WeightDosing` wd on `wd`.`Hjid` = `wds`.`WeightDosingSchedule_WeightDosing_Hjid`
    JOIN `v_ClinicalDrugs` cd on `cd`.`WeightDosing_Drug_Hjid` = `wd`.`Hjid`
  WHERE `cd`. `DrugDosingDeterminant`= 'weight';

CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `Studies` AS 
  SELECT `e`.`StudyID` AS `StudyID`
      ,`s`.`StudyTitle` AS `studyTitle`
      ,`s`.`StudyStatus` AS `studyStatus`
      ,`s`.`CuratorNotes` AS `curatorNotes`
      ,`s`.`StudyIsPublished` AS `studyIsPublished`
      ,`s`.`ExplorerDisplay` AS `explorerDisplay`
      ,`s`.`Modules` AS `modules` 
        FROM `Entry` `e` 
    JOIN `Content` `c` ON `e`.`Content_Entry_Hjid` = `c`.`Hjid`
    JOIN `Study` `s` ON `c`.`Study_Content_Hjid` = `s`.`Hjid`;



CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `v_Sites` AS 
  SELECT `e`.`StudyID` AS `StudyID`
      ,`Site`.`Hjid` AS `Hjid`
      ,`Site`.`DTYPE` AS `DTYPE`
      ,`Site`.`Region` AS `Region`
      ,`Site`.`LookupAddress` AS `LookupAddress`
      ,`Site`.`TestingDelay` AS `TestingDelay`
      ,`Site`.`SiteCode` AS `SiteCode`
      ,`Site`.`Country` AS `Country`
      ,`Site`.`Anticoagulant` AS `Anticoagulant`
      ,`Site`.`Locality` AS `Locality`
      ,`Site`.`Longitude` AS `Longitude`
      ,`Site`.`Latitude` AS `Latitude`
      ,`Site`.`TransportAndStorageTemperature` AS `TransportAndStorageTemperature`
      ,`Site`.`District` AS `District`
      ,`Site`.`Population` AS `Population`
      ,`Site`.`TransmissionIntensity_Site_Hjid` AS `TransmissionIntensity_Site_Hjid`
      ,`Site`.`Site_Sites_Hjid` AS `Site_Sites_Hjid` 
from `Site` JOIN `Sites` ON `Sites`.`Hjid` = `Site`.`Site_Sites_Hjid`
     JOIN `StudyInfo` `si` ON `si`.`Sites_StudyInfo_Hjid` = `Sites`.`Hjid`
     JOIN `Study` `s` ON `s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
     JOIN `Content` `c` ON `c`.`Study_Content_Hjid` = `s`.`Hjid`
     JOIN `Entry` `e` ON `e`.`Content_Entry_Hjid` = `c`.`Hjid`;


CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `v_pkAnalytes` AS
  SELECT `e`.`StudyID` AS `StudyID`
     , `a`.`Hjid` AS `Hjid`
     , `a`.`DTYPE` AS `DTYPE`
     , `a`.`LowerLoQ` AS `LowerLoQ`
     , `a`.`TargetDose` AS `TargetDose`
     , `a`.`FatAmount` AS `FatAmount`
     , `a`.`DrugMeasured` AS `DrugMeasured`
     , `a`.`SampleMatrixType` AS `SampleMatrixType`
     , `a`.`UnitsOfMeasure` AS `UnitsOfMeasure`
     , `a`.`Analyte_Analytes_Hjid` AS `Analyte_Analytes_Hjid`
  FROM `Analyte` `a`
    JOIN `Analytes`  ON `a`.`Analyte_Analytes_Hjid` = Analytes.`Hjid`
    JOIN `Pharmacology` `p` ON `p`.`Analytes_Pharmacology_Hjid` = Analytes.`Hjid`
    JOIN `StudyInfo` `si` ON `si`.`Pharmacology_StudyInfo_Hjid` = `p`.`Hjid`
    JOIN `Study` `s` ON `s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
    JOIN `Content` `c` ON `c`.`Study_Content_Hjid` = `s`.`Hjid`
    JOIN `Entry` `e` ON `e`.`Content_Entry_Hjid` = `c`.`Hjid`
;


CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `v_pkSampleStorage` AS

SELECT Sample.Hjid AS Sample_Hjid, StorageTemperature,StorageDuration,StorageDurationUnit
FROM
  `SampleStorage` 
JOIN Storages ON Storages.Hjid = SampleStorage.Storage__Storages_Hjid
JOIN Sample ON Sample.Storages_Sample_Hjid = Storages.Hjid
JOIN `Samples`  ON `Sample`.`Sample_Samples_Hjid` = `Samples`.`Hjid`
JOIN `Pharmacology` `p` ON `p`.`Samples_Pharmacology_Hjid` = `Samples`.`Hjid`
JOIN `StudyInfo` `si` ON `si`.`Pharmacology_StudyInfo_Hjid` = `p`.`Hjid`
JOIN `Study` `s` ON `s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
JOIN `Content` `c` ON `c`.`Study_Content_Hjid` = `s`.`Hjid`
JOIN `Entry` `e` ON `e`.`Content_Entry_Hjid` = `c`.`Hjid`
WHERE NOT(StorageTemperature = '' AND StorageDuration = '' AND StorageDurationUnit = '');

CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `v_pkSamples` AS

SELECT `e`.`StudyID` AS `StudyID`,
	`Sample`.`Hjid` AS Sample_Hjid,
	`Sample`.`CentrifugeTime` AS `CentrifugeTime`,
	`Sample`.`Anticoagulent` AS `Anticoagulent`,
	`Sample`.`SampleTypeOpen_Sample_Hjid` AS `SampleTypeOpen_Sample_Hjid`,
	`Sample`.`Storages_Sample_Hjid` AS `Storages_Sample_Hjid`
FROM
  `Sample` 
JOIN `Samples`  ON `Sample`.`Sample_Samples_Hjid` = `Samples`.`Hjid`
JOIN `Pharmacology` `p` ON `p`.`Samples_Pharmacology_Hjid` = `Samples`.`Hjid`
JOIN `StudyInfo` `si` ON `si`.`Pharmacology_StudyInfo_Hjid` = `p`.`Hjid`
JOIN `Study` `s` ON `s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
JOIN `Content` `c` ON `c`.`Study_Content_Hjid` = `s`.`Hjid`
JOIN `Entry` `e` ON `e`.`Content_Entry_Hjid` = `c`.`Hjid`;



CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `v_pkdetails` AS

SELECT `e`.`StudyID` AS `StudyID`
     , `p`.`Hjid` AS `Hjid`
     , `p`.`DTYPE` AS `DTYPE`
     , `p`.`PKcomments` AS `PKcomments`
     , `p`.`SamplingTimes` AS `SamplingTimes`
     , `p`.`StudyDesign` AS `StudyDesign`
     , `p`.`Samples_Pharmacology_Hjid` AS `Samples_Pharmacology_Hjid`
     , `p`.`Analytes_Pharmacology_Hjid` AS `Analytes_Pharmacology_Hjid`
     , `p`.`AssayReferences_Pharmacology_Hjid` AS `AssayReferences_Pharmacology_Hjid`
FROM
  `Pharmacology` `p`
JOIN `StudyInfo` `si` ON `si`.`Pharmacology_StudyInfo_Hjid` = `p`.`Hjid`
JOIN `Study` `s` ON `s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
JOIN `Content` `c` ON `c`.`Study_Content_Hjid` = `s`.`Hjid`
JOIN `Entry` `e` ON `e`.`Content_Entry_Hjid` = `c`.`Hjid`;

CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `v_pkAssayReferences` AS
SELECT e.StudyID,AssayValidated,ReferenceType,Url,Doi,Note,u.UploadedUrl FROM chassisPruned.AssayReference ar
    JOIN AssayReferences ars ON ar.AssayReference_AssayReferences_Hjid = ars.Hjid
    LEFT JOIN Upload u ON Upload_AssayReference_Hjid = u.Hjid
    JOIN `Pharmacology` `p` ON `p`.`AssayReferences_Pharmacology_Hjid` = ars.`Hjid`
    JOIN `StudyInfo` `si` ON `si`.`Pharmacology_StudyInfo_Hjid` = `p`.`Hjid`
    JOIN `Study` `s` ON `s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
    JOIN `Content` `c` ON `c`.`Study_Content_Hjid` = `s`.`Hjid`
    JOIN `Entry` `e` ON `e`.`Content_Entry_Hjid` = `c`.`Hjid`
    WHERE NOT ((Doi IS NULL OR Doi = '') AND (Note IS NULL OR Note = '') AND (Url IS NULL OR Url = ''));


CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `v_ackPeople` AS
SELECT e.StudyId, p.Hjid as studyContactId, FirstName, MiddleName, FamilyName, EmailAddress, PersonIsContactable, Institution FROM Person p
JOIN People ps ON ps.Hjid = p.Person_People_Hjid
JOIN AcksInstitutionsOrPeopleItem aiop on aiop.ItemPeople_AcksInstitutionsOrPeopleItem_Hjid = ps.Hjid
JOIN Acks acks on acks.Hjid = aiop.InstitutionsOrPeopleItems_Acks_Hjid
JOIN Study s on s.Acknowledgements_Study_Hjid = acks.Hjid
JOIN `Content` `c` on`c`.`Study_Content_Hjid` = `s`.`Hjid`
JOIN `Entry` `e` on`e`.`Content_Entry_Hjid` = `c`.`Hjid`;

CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `v_ackInstitutions` AS
SELECT e.StudyId, inst.Hjid as studyInstitutionId,  InstitutionName FROM InstitutionAck inst
JOIN Institutions insts ON insts.Hjid = inst.InstitutionAck_Institutions_Hjid
JOIN AcksInstitutionsOrPeopleItem aiop on aiop.ItemInstitutions_AcksInstitutionsOrPeopleItem_Hjid = insts.Hjid
JOIN Acks acks on acks.Hjid = aiop.InstitutionsOrPeopleItems_Acks_Hjid
JOIN Study s on s.Acknowledgements_Study_Hjid = acks.Hjid
JOIN `Content` `c` on`c`.`Study_Content_Hjid` = `s`.`Hjid`
JOIN `Entry` `e` on`e`.`Content_Entry_Hjid` = `c`.`Hjid`;

CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `v_ackInstitutionURLs` AS
SELECT e.StudyId, inst.Hjid as studyInstitutionId,  InstitutionName, Item as url FROM InstWebInstitutionUrlItem insturl
JOIN InstWeb iw on insturl.InstitutionUrlItems_InstWeb_Hjid = iw.Hjid
JOIN InstitutionAck inst on inst.InstitutionWebsites_InstitutionAck_Hjid = iw.Hjid
JOIN Institutions insts ON insts.Hjid = inst.InstitutionAck_Institutions_Hjid
JOIN AcksInstitutionsOrPeopleItem aiop on aiop.ItemInstitutions_AcksInstitutionsOrPeopleItem_Hjid = insts.Hjid
JOIN Acks acks on acks.Hjid = aiop.InstitutionsOrPeopleItems_Acks_Hjid
JOIN Study s on s.Acknowledgements_Study_Hjid = acks.Hjid
JOIN `Content` `c` on`c`.`Study_Content_Hjid` = `s`.`Hjid`
JOIN `Entry` `e` on`e`.`Content_Entry_Hjid` = `c`.`Hjid`;
