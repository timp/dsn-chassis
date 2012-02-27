select 
   s.StudyId, 
   s.PublishedItem as DateReceived, 
   s.Modules, 
   s.StartDate, 
   s.EndDate, 
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
ORDER BY s.StudyId
