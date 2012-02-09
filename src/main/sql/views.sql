                                                                     
CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `Studies` AS 
  SELECT `e`.`StudyID` AS `StudyID`
      ,`s`.`StudyTitle` AS `studyTitle`
      ,`s`.`StudyStatus` AS `studyStatus`
      ,`s`.`CuratorNotes` AS `curatorNotes`
      ,`s`.`StudyIsPublished` AS `studyIsPublished`
      ,`s`.`ExplorerDisplay` AS `explorerDisplay`
      ,`s`.`Modules` AS `modules` 
        FROM `Entry` `e` 
    LEFT JOIN `Content` `c` ON `e`.`Content_Entry_Hjid` = `c`.`Hjid`
    LEFT JOIN `Study` `s` ON `c`.`Study_Content_Hjid` = `s`.`Hjid`;



CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `StudySites` AS 
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
from `Site` LEFT JOIN `Sites` ON `Sites`.`Hjid` = `Site`.`Site_Sites_Hjid`
     LEFT JOIN `StudyInfo` `si` ON `si`.`Sites_StudyInfo_Hjid` = `Sites`.`Hjid`
     LEFT JOIN `Study` `s` ON `s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
     LEFT JOIN `Content` `c` ON `c`.`Study_Content_Hjid` = `s`.`Hjid`
     LEFT JOIN `Entry` `e` ON `e`.`Content_Entry_Hjid` = `c`.`Hjid`;
;

CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `ClinicalDrugs` AS 
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
from `Drug` LEFT JOIN `Drugs` ON `Drug`.`Drug_Drugs_Hjid` = `Drugs`.`Hjid`
     LEFT JOIN `Regimen` `r` ON `r`.`Drugs_Regimen_Hjid` = `Drugs`.`Hjid`
     LEFT JOIN `Regimens` `rs` ON `r`.`Regimen_Regimens_Hjid` = `rs`.`Hjid`
     LEFT JOIN `Treatment` `t` ON `t`.`Regimens_Treatment_Hjid` = `rs`.`Hjid`
     LEFT JOIN `Clinical` ON `Clinical`.`Treatment_Clinical_Hjid` = `t`.`Hjid`
     LEFT JOIN `StudyInfo` `si` ON `si`.`Clinical_StudyInfo_Hjid` = `Clinical`.`Hjid`
     LEFT JOIN `Study` `s` ON `s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
     LEFT JOIN `Content` `c` ON `c`.`Study_Content_Hjid` = `s`.`Hjid`
     LEFT JOIN `Entry` `e` ON `e`.`Content_Entry_Hjid` = `c`.`Hjid`;
;

CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `pkAnalytes` AS

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
FROM
  `Analyte` `a`
LEFT JOIN `Analytes`  ON `a`.`Analyte_Analytes_Hjid` = Analytes.`Hjid`
LEFT JOIN `Pharmacology` `p` ON `p`.`Analytes_Pharmacology_Hjid` = Analytes.`Hjid`
LEFT JOIN `StudyInfo` `si` ON `si`.`Pharmacology_StudyInfo_Hjid` = `p`.`Hjid`
LEFT JOIN `Study` `s` ON `s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
LEFT JOIN `Content` `c` ON `c`.`Study_Content_Hjid` = `s`.`Hjid`
LEFT JOIN `Entry` `e` ON `e`.`Content_Entry_Hjid` = `c`.`Hjid`;



CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `pkSamples` AS

SELECT `e`.`StudyID` AS `StudyID`
     ,Sample.*
FROM
  `Sample` 
LEFT JOIN `Samples`  ON `Sample`.`Sample_Samples_Hjid` = `Samples`.`Hjid`
LEFT JOIN `Pharmacology` `p` ON `p`.`Samples_Pharmacology_Hjid` = `Samples`.`Hjid`
LEFT JOIN `StudyInfo` `si` ON `si`.`Pharmacology_StudyInfo_Hjid` = `p`.`Hjid`
LEFT JOIN `Study` `s` ON `s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
LEFT JOIN `Content` `c` ON `c`.`Study_Content_Hjid` = `s`.`Hjid`
LEFT JOIN `Entry` `e` ON `e`.`Content_Entry_Hjid` = `c`.`Hjid`;



CREATE OR REPLACE SQL SECURITY INVOKER VIEW  `pkdetails` AS

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
LEFT JOIN `StudyInfo` `si` ON `si`.`Pharmacology_StudyInfo_Hjid` = `p`.`Hjid`
LEFT JOIN `Study` `s` ON `s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
LEFT JOIN `Content` `c` ON `c`.`Study_Content_Hjid` = `s`.`Hjid`
LEFT JOIN `Entry` `e` ON `e`.`Content_Entry_Hjid` = `c`.`Hjid`;

