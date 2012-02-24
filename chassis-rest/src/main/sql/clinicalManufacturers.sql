select 
   s.StudyId, 
   s.PublishedItem as DateReceived, 
   s.Modules, 
   Drug.Manufacturer
 from 
( select 
   Entry.StudyId, 
   Entry.PublishedItem, 
   Study.Modules, 
   StudyInfo.StartDate, 
   StudyInfo.EndDate,
   Study.StudyTitle,
   StudyInfo.Sites_StudyInfo_Hjid,
   Study.Publications_Study_Hjid,
   StudyInfo.Clinical_StudyInfo_Hjid,
   StudyInfo.InclusionExclusionCriteria_StudyInfo_Hjid
from 
Entry, Content, Study, StudyInfo
where
 Entry.Content_Entry_Hjid = Content.Hjid and
 Content.Study_Content_Hjid = Study.Hjid and
 Study.StudyInfo_Study_Hjid = StudyInfo.Hjid 
and  Study.Modules like "%clinical%" 
) s
left join Clinical on s.Clinical_StudyInfo_Hjid = Clinical.Hjid
left join Treatment on Clinical.Treatment_Clinical_Hjid = Treatment.Hjid
left join Regimens on Treatment.Regimens_Treatment_Hjid = Regimens.Hjid
left join Regimen on Regimens.Hjid = Regimen.Regimen_Regimens_Hjid
left join Drugs on Regimen.Drugs_Regimen_Hjid = Drugs.Hjid
left join Drug on Drug.Drug_Drugs_Hjid = Drugs.Hjid
ORDER BY s.StudyId
