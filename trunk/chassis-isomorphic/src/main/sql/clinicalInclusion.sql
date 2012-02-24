select 
   s.StudyId, 
   s.PublishedItem as DateReceived, 
   s.Modules, 
   s.CuratorNotes,
   s.StartDate, 
   s.EndDate, 
   Site.LookupAddress as Site,
   Site.Population,
   Site.Latitude,
   Site.Longitude,
   s.StudyTitle,
   Publication.PublicationTitle, 
   Age.MinAge, 
   Age.MinAgeUnits,
   Age.MaxAge, 
   Age.MaxAgeUnits, 
   Followup.Duration
 from 
( select 
   Entry.StudyId, 
   Entry.PublishedItem, 
   Study.Modules, 
   Study.CuratorNotes,
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
left join Sites on s.Sites_StudyInfo_Hjid = Sites.Hjid 
left join Site on Sites.Hjid = Site.Site_Sites_Hjid 
left join Publications on  s.Publications_Study_Hjid = Publications.Hjid 
left join Publication on Publication.Publication_Publications_Hjid = Publications.Hjid 
left join InclusionExclusionCriteria on s.InclusionExclusionCriteria_StudyInfo_Hjid = InclusionExclusionCriteria.Hjid 
left join Age on Age.Hjid = InclusionExclusionCriteria.Age_InclusionExclusionCriteria_Hjid
left join Clinical on s.Clinical_StudyInfo_Hjid = Clinical.Hjid
left join Treatment on Clinical.Treatment_Clinical_Hjid = Treatment.Hjid
left join Regimens on Treatment.Regimens_Treatment_Hjid = Regimens.Hjid
left join Regimen on Regimens.Hjid = Regimen.Regimen_Regimens_Hjid
left join Drugs on Regimen.Drugs_Regimen_Hjid = Drugs.Hjid
left join Drug on Drug.Drug_Drugs_Hjid = Drugs.Hjid
left join Followup on Followup.Hjid = Clinical.Followup_Clinical_Hjid
ORDER BY s.StudyId
