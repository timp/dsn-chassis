use chassisDb;
select Entry.StudyId, Entry.PublishedItem, Study.Modules, Study.StudyTitle,
   StudyInfo.StartDate, StudyInfo.EndDate, Site.Population,
   TransmissionIntensity.TransmissionIntensityAgeFrom,
   TransmissionIntensity.EntomologicalInoculationRateYear,
   TransmissionIntensity.TransmissionIntensityAgeToUnits,
   TransmissionIntensity.AnnualParasitologicalIncidence,
   TransmissionIntensity.AnnualParasitologicalIncidence,
   TransmissionIntensity.EntomologicalInoculationRate,
   TransmissionIntensity.SeasonalTransmission,
   TransmissionIntensity.TransmissionIntensityAgeTo,
   TransmissionIntensity.ParaSitePrevalenceYear,
   TransmissionIntensity.ParaSitePrevalence,
   TransmissionIntensity.TransmissionIntensityAgeFromUnits,
   TransmissionIntensity.TransmissionMonths
 from Entry, Content, Study, StudyInfo, Sites, Site, TransmissionIntensity
 where
 Entry.Content_Entry_Hjid = Content.Hjid and
 Content.Study_Content_Hjid = Study.Hjid and
 Study.StudyInfo_Study_Hjid = StudyInfo.Hjid and
 StudyInfo.Sites_StudyInfo_Hjid = Sites.Hjid and
 Sites.Hjid  = Site.Site_Sites_Hjid and
 Site.TransmissionIntensity_Site_Hjid = TransmissionIntensity.Hjid
 order by Study.Modules

