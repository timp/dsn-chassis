select 
   s.StudyId, 
   s.PublishedItem as DateReceived, 
   s.Modules, 
   s.StartDate, 
   s.EndDate, 
   s.Published,
   Publication.Pmid,
   PublicationReference.PublicationReferenceType as Type,
   PublicationReference.Identifier,
   Publication.PublicationTitle
 from 
( select 
   Entry.StudyId, 
   Entry.PublishedItem, 
   Study.Modules, 
   StudyInfo.StartDate, 
   StudyInfo.EndDate,
   Study.StudyTitle,
   Study.StudyIsPublished as Published,
   StudyInfo.Sites_StudyInfo_Hjid,
   Study.Publications_Study_Hjid
from 
Entry, Content, Study, StudyInfo
where
 Entry.Content_Entry_Hjid = Content.Hjid and
 Content.Study_Content_Hjid = Study.Hjid and
 Study.StudyInfo_Study_Hjid = StudyInfo.Hjid 
) s
left join Publications on  s.Publications_Study_Hjid = Publications.Hjid 
left join Publication on Publication.Publication_Publications_Hjid = Publications.Hjid
left join PublicationReferences on Publication.PublicationReferences_Publication_Hjid = PublicationReferences.Hjid
left join PublicationReference on PublicationReference.PublicationReference_PublicationReferences_Hjid = PublicationReferences.Hjid
ORDER BY s.StudyId
