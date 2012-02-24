select 
   s.StudyId, 
   s.PublishedItem as DateReceived, 
   s.Modules, 
   s.StartDate, 
   s.EndDate, 
   Site.LookupAddress as Site,
   Site.Population,
   Site.Latitude,
   Site.Longitude,
   s.StudyTitle,
   Publication.PublicationTitle
 from 
( select 
   Entry.StudyId, 
   Entry.PublishedItem, 
   Study.Modules, 
   StudyInfo.StartDate, 
   StudyInfo.EndDate,
   Study.StudyTitle,
   StudyInfo.Sites_StudyInfo_Hjid,
   Study.Publications_Study_Hjid
from 
Entry, Content, Study, StudyInfo
where
 Entry.Content_Entry_Hjid = Content.Hjid and
 Content.Study_Content_Hjid = Study.Hjid and
 Study.StudyInfo_Study_Hjid = StudyInfo.Hjid 
) s
left join Sites on s.Sites_StudyInfo_Hjid = Sites.Hjid 
left join Site on Sites.Hjid = Site.Site_Sites_Hjid 
left join Publications on  s.Publications_Study_Hjid = Publications.Hjid 
left join Publication on Publication.Publication_Publications_Hjid = Publications.Hjid 
ORDER BY s.StudyId
