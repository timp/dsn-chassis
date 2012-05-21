CREATE OR REPLACE SQL SECURITY INVOKER VIEW `v_inVitro` AS
	select e.StudyID, iv.Hjid as ivHjid, AnalysisSite, PlatesPreparationDate,PlatePreparationMethod, 
	cul.IncubatorSystem, cul.Co2Percentage, cul.Co2Other, cul.O2Percentage, cul.O2Other, cul.HealthyErythrocytesSource, cul.Hematocritpercent, cul.BloodGroup,
	d.Medium,d.MediumOther, d.Preparation, d.Serum, d.SerumFinalConcentration, d.NaHCO3FinalConcentration,
	h.HypoxantineAdded, h.HypoxantineFinalConcentration,
	o.OroticAcidAdded, o.OroticAcidFinalConcentration,
	g.GlucoseAdded, g.GlucoseFinalConcentration,
	sus.SusceptibilityMethod, sus.TimeOfIncubation
	  FROM Invitro iv
		LEFT JOIN Culture cul ON iv.Culture_Invitro_Hjid = cul.Hjid
		LEFT JOIN DrugSusceptibilityMedium d ON iv.DrugSusceptibilityMedium_Invitro_Hjid = d.Hjid
		LEFT JOIN Hypoxantine h ON d.Hypoxantine_DrugSusceptibilityMedium_Hjid = h.Hjid
		LEFT JOIN OroticAcid o ON d.OroticAcid_DrugSusceptibilityMedium_Hjid = o.Hjid
		LEFT JOIN Glucose g ON d.Glucose_DrugSusceptibilityMedium_Hjid = g.Hjid
		LEFT JOIN Susceptibility sus ON iv.Susceptibility_Invitro_Hjid = sus.Hjid
		JOIN StudyInfo si ON si.Invitro_StudyInfo_Hjid = `iv`.`Hjid`
		join `Study` `s` on`s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
		join `Content` `c` on`c`.`Study_Content_Hjid` = `s`.`Hjid`
		join `Entry` `e` on`e`.`Content_Entry_Hjid` = `c`.`Hjid`
     WHERE Modules LIKE '%invitro%';
         
CREATE OR REPLACE SQL SECURITY INVOKER VIEW `v_inVitroAntibiotics` AS
	SELECT e.StudyID, at.AntibioticFinalConcentration, ao.Antibiotic, ao.AntibioticOther
		FROM AntibioticTreatment at
		JOIN AntibioticOpen ao ON at.AntibioticOpen_AntibioticTreatment_Hjid = ao.Hjid
		JOIN AntibioticTreatments ats ON ats.AntibioticTreatment_AntibioticTreatments_Hjid = at.Hjid
		JOIN DrugSusceptibilityMedium d ON d.AntibioticTreatments_DrugSusceptibilityMedium_Hjid = ats.Hjid
		JOIN Invitro iv ON iv.DrugSusceptibilityMedium_Invitro_Hjid = d.Hjid
		JOIN StudyInfo si ON si.Invitro_StudyInfo_Hjid = `iv`.`Hjid`
		JOIN `Study` `s` on`s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
		JOIN `Content` `c` on`c`.`Study_Content_Hjid` = `s`.`Hjid`
		JOIN `Entry` `e` on`e`.`Content_Entry_Hjid` = `c`.`Hjid`
	WHERE Modules LIKE '%invitro%';

CREATE OR REPLACE SQL SECURITY INVOKER VIEW `v_inVitroDrugs` AS
	select e.StudyID,id.Molecule, id.ProvidedByWwarn, id.Provider, id.Solvent, id.SolventFinalConcentration 
		FROM InvitroDrug id
			JOIN InvitroDrugs ids ON id.InvitroDrug_InvitroDrugs_Hjid = ids.Hjid
			JOIN Invitro iv ON iv.InvitroDrugs_Invitro_Hjid = ids.Hjid
			JOIN StudyInfo si ON si.Invitro_StudyInfo_Hjid = `iv`.`Hjid`
		 	join `Study` `s` on`s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
		 	join `Content` `c` on`c`.`Study_Content_Hjid` = `s`.`Hjid`
		 	join `Entry` `e` on`e`.`Content_Entry_Hjid` = `c`.`Hjid`
	WHERE Modules LIKE '%invitro%';

CREATE OR REPLACE SQL SECURITY INVOKER VIEW `v_inVitroPlates` AS
	SELECT e.StudyID,b.BatchesIncludedInRawData, b.Clone3D7ProvidedByWwarn, b.Clone3D7Provider, b.Parasitaemia3D7Percentage, b.PreparationDate, b.RingFormingPercentage
		FROM Batch b
		JOIN PlateBatches pb ON b.Batch_PlateBatches_Hjid = pb.Hjid
		JOIN Invitro iv ON iv.PlateBatches_Invitro_Hjid = pb.Hjid
		JOIN StudyInfo si ON si.Invitro_StudyInfo_Hjid = `iv`.`Hjid`
		JOIN `Study` `s` on`s`.`StudyInfo_Study_Hjid` = `si`.`Hjid`
		JOIN `Content` `c` on`c`.`Study_Content_Hjid` = `s`.`Hjid`
		JOIN `Entry` `e` on`e`.`Content_Entry_Hjid` = `c`.`Hjid`
	WHERE Modules LIKE '%invitro%';