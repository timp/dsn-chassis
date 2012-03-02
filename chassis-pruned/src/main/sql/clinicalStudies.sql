select 
   Entry.StudyId, 
   Entry.PublishedItem as DateReceived,
   Study.Modules, 
   StudyInfo.StartDate, 
   StudyInfo.EndDate,
   Study.StudyTitle,
   Study.StudyStatus,
   StudyInfo.StudyInfoStatus,
   StudyInfo.Sites_StudyInfo_Hjid
from 
Entry, Content, Study, StudyInfo
where
 Entry.Content_Entry_Hjid = Content.Hjid and
 Content.Study_Content_Hjid = Study.Hjid and
 Study.StudyInfo_Study_Hjid = StudyInfo.Hjid 
and Study.Modules like "%clinical%"
ORDER BY Entry.StudyId
;
