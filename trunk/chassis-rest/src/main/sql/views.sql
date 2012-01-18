                                                                     
                                             

CREATE VIEW `v_studydetails` AS 

	select `e`.`StudyID` AS `StudyID`
			,`s`.`StudyTitle` AS `studyTitle`
			,`s`.`StudyStatus` AS `studyStatus`
			,`s`.`CuratorNotes` AS `curatorNotes`
			,`s`.`StudyIsPublished` AS `studyIsPublished`
			,`s`.`ExplorerDisplay` AS `explorerDisplay`
			,`s`.`Modules` AS `modules` from `entry` `e` left join `content` `c` on`e`.`Content_Entry_Hjid` = `c`.`Hjid`
		 left join `study` `s` on`c`.`Study_Content_Hjid` = `s`.`Hjid`;
;

CREATE VIEW `v_sites` AS 
	select `e`.`StudyID` AS `StudyID`
			,`site`.`Hjid` AS `Hjid`
			,`site`.`DTYPE` AS `DTYPE`
			,`site`.`Region` AS `Region`
			,`site`.`LookupAddress` AS `LookupAddress`
			,`site`.`TestingDelay` AS `TestingDelay`
			,`site`.`SiteCode` AS `SiteCode`
			,`site`.`Country` AS `Country`
			,`site`.`Anticoagulant` AS `Anticoagulant`
			,`site`.`Locality` AS `Locality`
			,`site`.`Longitude` AS `Longitude`
			,`site`.`Latitude` AS `Latitude`
			,`site`.`TransportAndStorageTemperature` AS `TransportAndStorageTemperature`
			,`site`.`District` AS `District`
			,`site`.`Population` AS `Population`
			,`site`.`TransmissionIntensity_Site_Hjid` AS `TransmissionIntensity_Site_Hjid`
			,`site`.`Site_Sites_Hjid` AS `Site_Sites_Hjid` from `site` left join `sites` on`sites`.`Hjid` = `site`.`Site_Sites_Hjid`
		 left join `studyinfo` `si` on`si`.`Sites_StudyInfo_Hjid` = `sites`.`Hjid`
		 left join `study` `s` on`s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
		 left join `content` `c` on`c`.`Study_Content_Hjid` = `s`.`Hjid`
		 left join `entry` `e` on`e`.`Content_Entry_Hjid` = `c`.`Hjid`;
;

CREATE VIEW `v_clinicaldrugs` AS 
	select `e`.`StudyID` AS `StudyID`
			,`drug`.`Hjid` AS `Hjid`
			,`drug`.`DTYPE` AS `DTYPE`
			,`drug`.`FeedingOther` AS `FeedingOther`
			,`drug`.`DrugName` AS `DrugName`
			,`drug`.`Feeding` AS `Feeding`
			,`drug`.`TradeName` AS `TradeName`
			,`drug`.`DrugDosingDeterminant` AS `DrugDosingDeterminant`
			,`drug`.`ManufacturerOther` AS `ManufacturerOther`
			,`drug`.`TradeNameOther` AS `TradeNameOther`
			,`drug`.`Manufacturer` AS `Manufacturer`
			,`drug`.`FatPerMeal` AS `FatPerMeal`
			,`drug`.`DrugStorage` AS `DrugStorage`
			,`drug`.`DrugNameOther` AS `DrugNameOther`
			,`drug`.`Comments` AS `Comments`
			,`drug`.`AdministrationRoute` AS `AdministrationRoute`
			,`drug`.`ReadministeredOnVomitting` AS `ReadministeredOnVomitting`
			,`drug`.`ActiveIngredients_Drug_Hjid` AS `ActiveIngredients_Drug_Hjid`
			,`drug`.`WeightGroupDosing_Drug_Hjid` AS `WeightGroupDosing_Drug_Hjid`
			,`drug`.`AgeDosing_Drug_Hjid` AS `AgeDosing_Drug_Hjid`
			,`drug`.`Batches_Drug_Hjid` AS `Batches_Drug_Hjid`
			,`drug`.`WeightDosing_Drug_Hjid` AS `WeightDosing_Drug_Hjid`
			,`drug`.`Drug_Drugs_Hjid` AS `Drug_Drugs_Hjid` from `drug` left join `drugs` on`drug`.`Drug_Drugs_Hjid` = `drugs`.`Hjid`
		 left join `regimen` `r` on`r`.`Drugs_Regimen_Hjid` = `drugs`.`Hjid`
		 left join `regimens` `rs` on`r`.`Regimen_Regimens_Hjid` = `rs`.`Hjid`
		 left join `treatment` `t` on`t`.`Regimens_Treatment_Hjid` = `rs`.`Hjid`
		 left join `clinical` on`clinical`.`Treatment_Clinical_Hjid` = `t`.`Hjid`
		 left join `studyinfo` `si` on`si`.`Clinical_StudyInfo_Hjid` = `clinical`.`Hjid`
		 left join `study` `s` on`s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
		 left join `content` `c` on`c`.`Study_Content_Hjid` = `s`.`Hjid`
		 left join `entry` `e` on`e`.`Content_Entry_Hjid` = `c`.`Hjid`;
;



CREATE VIEW v_pkAnalytes AS

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
  `analyte` `a`
LEFT JOIN `analytes` `ans` ON `a`.`Analyte_Analytes_Hjid` = `ans`.`Hjid`
LEFT JOIN `pharmacology` `p` ON `p`.`Analytes_Pharmacology_Hjid` = `ans`.`Hjid`
LEFT JOIN `studyinfo` `si` ON `si`.`Pharmacology_StudyInfo_Hjid` = `p`.`Hjid`
LEFT JOIN `study` `s` ON `s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
LEFT JOIN `content` `c` ON `c`.`Study_Content_Hjid` = `s`.`Hjid`
LEFT JOIN `entry` `e` ON `e`.`Content_Entry_Hjid` = `c`.`Hjid`;

;

CREATE VIEW v_pkSamples AS

SELECT `e`.`StudyID` AS `StudyID`
     ,sam.*
FROM
  `sample` `sam`
LEFT JOIN `samples` `sams` ON `sam`.`Sample_Samples_Hjid` = `sams`.`Hjid`
LEFT JOIN `pharmacology` `p` ON `p`.`Samples_Pharmacology_Hjid` = `sams`.`Hjid`
LEFT JOIN `studyinfo` `si` ON `si`.`Pharmacology_StudyInfo_Hjid` = `p`.`Hjid`
LEFT JOIN `study` `s` ON `s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
LEFT JOIN `content` `c` ON `c`.`Study_Content_Hjid` = `s`.`Hjid`
LEFT JOIN `entry` `e` ON `e`.`Content_Entry_Hjid` = `c`.`Hjid`;

;

CREATE VIEW v_pkdetails AS

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
  `pharmacology` `p`
LEFT JOIN `studyinfo` `si` ON `si`.`Pharmacology_StudyInfo_Hjid` = `p`.`Hjid`
LEFT JOIN `study` `s` ON `s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
LEFT JOIN `content` `c` ON `c`.`Study_Content_Hjid` = `s`.`Hjid`
LEFT JOIN `entry` `e` ON `e`.`Content_Entry_Hjid` = `c`.`Hjid`;

;