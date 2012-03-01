select 
   s.StudyId, 
   s.PublishedItem as DateReceived, 
   s.Modules, 
   s.StartDate, 
   s.EndDate, 
   Site.LookupAddress as Site,
   Site.Population,
   Site.Longitude,
   Site.Latitude,
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
 Study.StudyInfo_Study_Hjid = StudyInfo.Hjid and 
 Entry.StudyId in 
 (
"KGUJT",
"ZNBUC",
"KPEAT",
"KXPEY",
"PGBUJ",
"ZZSPY",
"DXYHK",
"XDZPA",
"BFSYS",
"AYCTT",
"CNAEH",
"ZFSQZ",
"DXHNK",
"CXHXA",
"JXPFB",
"DMCYR",
"KGJXQ",
"CJCMZ",
"NGKRX",
"YYHRH",
"BXYJX",
"JAGHH",
"DRSRU",
"CXZEM",
"ZYTBB",
"UJAGF",
"RMTGP",
"DEDSE",
"RCJJN",
"CMZMY",
"PMRZC",
"ZRMXD",
"ZSBZX",
"BXAPK",
"TMCZE",
"QMJDM",
"YJUXZ",
"UUCZG",
"UTZZB",
"EFNSJ",
"DSBTF",
"GBKPB",
"UGGKH",
"RZEKT",
"MHABC",
"NPCGQ",
"EDUXU",
"QTQDJ",
"AJTMH",
"HXXHM",
"CJRCS",
"MXMNJ",
"GDSEZ",
"TDBGN"
)
 ) s
left join Sites on s.Sites_StudyInfo_Hjid = Sites.Hjid 
left join Site on Sites.Hjid = Site.Site_Sites_Hjid 
left join Publications on  s.Publications_Study_Hjid = Publications.Hjid 
left join Publication on Publication.Publication_Publications_Hjid = Publications.Hjid 
ORDER BY s.StudyId


