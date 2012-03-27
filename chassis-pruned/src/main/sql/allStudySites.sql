select 
   s.StudyId, 
   s.PublishedItem as DateReceived, 
   s.Modules, 
   s.StartDate, 
   s.EndDate, 
   Site.Country,
   Site.LookupAddress as Site,
   Site.Population,
   Site.Latitude,
   Site.Longitude,
   s.StudyTitle
 from 
( select 
   Entry.StudyId, 
   Entry.PublishedItem, 
   Study.Modules, 
   StudyInfo.StartDate, 
   StudyInfo.EndDate,
   Study.StudyTitle,
   StudyInfo.Sites_StudyInfo_Hjid
from 
Entry, Content, Study, StudyInfo
where
 Entry.Content_Entry_Hjid = Content.Hjid and
 Content.Study_Content_Hjid = Study.Hjid and
 Study.StudyInfo_Study_Hjid = StudyInfo.Hjid 
) s
left join Sites on s.Sites_StudyInfo_Hjid = Sites.Hjid 
left join Site on Sites.Hjid = Site.Site_Sites_Hjid 
ORDER BY s.StudyId
