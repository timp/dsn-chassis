//requires jQuery JavaScript Library


////Model

var downloadStudyAsCsvColumnsWithStudyCustomFieldLabelsUsingStudyAtomEntryURLWindow = null;
var downloadStudyAsCsvRowsWithStudyCustomFieldLabelsUsingStudyAtomEntryURLWindow = null;
var firefoxUserAgent = null;
var chromeUserAgent = null;


function setDownloadStudyAsCsvColumnsWithStudyCustomFieldLabelsWindow (downloadStudyAsCsvColumnsWithStudyCustomFieldLabelsUsingStudyAtomEntryURLWindow) {
	this.downloadStudyAsCsvColumnsWithStudyCustomFieldLabelsUsingStudyAtomEntryURLWindow = downloadStudyAsCsvColumnsWithStudyCustomFieldLabelsUsingStudyAtomEntryURLWindow;
}
function getDownloadStudyAsCsvColumnsWithStudyCustomFieldLabelsWindow () {
	return this.downloadStudyAsCsvColumnsWithStudyCustomFieldLabelsUsingStudyAtomEntryURLWindow;
}


function setDownloadStudyAsCsvRowsWithStudyCustomFieldLabelsWindow (downloadStudyAsCsvRowsWithStudyCustomFieldLabelsUsingStudyAtomEntryURLWindow) {
	this.downloadStudyAsCsvRowsWithStudyCustomFieldLabelsUsingStudyAtomEntryURLWindow = downloadStudyAsCsvRowsWithStudyCustomFieldLabelsUsingStudyAtomEntryURLWindow;
}
function getDownloadStudyAsCsvRowsWithStudyCustomFieldLabelsWindow () {
	return this.downloadStudyAsCsvRowsWithStudyCustomFieldLabelsUsingStudyAtomEntryURLWindow;
}


function setFirefoxUserAgent(firefoxUserAgent) {
	this.firefoxUserAgent = firefoxUserAgent;
}
function isFirefoxUserAgent () {
	return this.firefoxUserAgent;
}


function setChromeUserAgent(chromeUserAgent) {
	this.chromeUserAgent = chromeUserAgent;
}
function isChromeUserAgent () {
	return this.chromeUserAgent;
}



////

function initStudyClientApp () {
	
	this.setFirefoxUserAgent(retrieveFirefoxUserAgentUsingUserAgent(navigator.userAgent));
	this.setChromeUserAgent(retrieveChromeUserAgentUsingUserAgent(navigator.userAgent));
	
	//NOTE: Could alternatively use $.browser.mozilla
}


////Data CRUD


function retrieveFirefoxUserAgentUsingUserAgent(userAgent) {
	
	var firefoxUserAgent = null;
		
	if(/firefox/i.test(userAgent) ) { 
		firefoxUserAgent = true;
	} 
	else {
		firefoxUserAgent = false;
	}

	return firefoxUserAgent;
}

function retrieveChromeUserAgentUsingUserAgent(userAgent) {
	
	var chromeUserAgent = null;
		
	if(/chrome/i.test(userAgent) ) { 
		chromeUserAgent = true;
	} 
	else {
		chromeUserAgent = false;
	}

	return chromeUserAgent;
}

function retrieveStudyAsAtomEntryXmlUsingStudyAtomEntryURL (studyAtomEntryURL) {

	var studyAsAtomEntryXML = null;
	
	$.ajax({
		  async: false,
		  url: studyAtomEntryURL,
		  type: "GET",
		  cache: false,
		  dataType: "xml",
		  success: function(data, textStatus, jqXHR){
			  studyAsAtomEntryXML = data;
		  },
		  error: function(jqXHR, textStatus, errorThrown){
		    alert("Error getting study data: " + errorThrown);
		  } 
		});

	return studyAsAtomEntryXML;
}

function retrieveStudyInfoAsAtomEntryXmlUsingStudyInfoAtomEntryURL (studyInfoAtomEntryURL) {
	
	var studyInfoAsAtomEntryXML = null;
	
	$.ajax({
		  async: false,
		  url: studyInfoAtomEntryURL,
		  type: "GET",
		  cache: false,
		  dataType: "xml",
		  success: function(data, textStatus, jqXHR){
			  studyInfoAsAtomEntryXML = data;
		  },
		  error: function(jqXHR, textStatus, errorThrown){
		    alert("Error getting study info data: " + errorThrown);
		  } 
		});

	return studyInfoAsAtomEntryXML;
}

function retrieveStudyCustomFieldLabelsRegExMapAsAssociativeArray () {

	var studyCustomFieldLabelsMapAsAssociativeArray = null;

	//TODO:replace with version retrieved from service
	
	//NOTE: Patterns must be in the form "atom:entry\\[(\\d+)\\]/manta:id\\[(\\d+)\\]"
	studyCustomFieldLabelsMapAsAssociativeArray = {
			
			"atom:entry\\[(\\d+)\\]/atom:id\\[(\\d+)\\]" : "AtomID",
			"atom:entry\\[(\\d+)\\]/atom:author\\[(\\d+)\\]/atom:email\\[(\\d+)\\]" : "AuthorEmail",
			"atom:entry\\[(\\d+)\\]/atom:title\\[(\\d+)\\]" : "Title",
			"atom:entry\\[(\\d+)\\]/manta:id\\[(\\d+)\\]" : "StudyCode",
			"atom:entry\\[(\\d+)\\]/atom:published\\[(\\d+)\\]" : "CompletionDate",
			"atom:entry\\[(\\d+)\\]/atom:updated\\[(\\d+)\\]" : "DateLastUpdated",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/registrant-has-agreed-to-the-terms\\[(\\d+)\\]" : "TermsOfSubmission",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/study-is-published\\[(\\d+)\\]" : "StudyPublished",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/explorer-display\\[(\\d+)\\]" : "ExplorerDisplay",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/publications\\[(\\d+)\\]/publication\\[(\\d+)\\]/publication-title\\[(\\d+)\\]" : "Publication$5Title",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/publications\\[(\\d+)\\]/publication\\[(\\d+)\\]/pmid\\[(\\d+)\\]" : "Publication$5PubMedID",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/publications\\[(\\d+)\\]/publication\\[(\\d+)\\]/publication-references\\[(\\d+)\\]/publication-reference\\[(\\d+)\\]" : "Publication$5URL$7",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/acknowledgements\\[(\\d+)\\]/institution-ack\\[(\\d+)\\]/institution-name\\[(\\d+)\\]" : "ackInstitute$5",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/acknowledgements\\[(\\d+)\\]/institution-ack\\[(\\d+)\\]/institution-websites\\[(\\d+)\\]/institution-url\\[(\\d+)\\]" : "ackInstitute$5URL",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/acknowledgements\\[(\\d+)\\]/person\\[(\\d+)\\]/first-name\\[(\\d+)\\]" : "ackAuthor$5Name",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/acknowledgements\\[(\\d+)\\]/person\\[(\\d+)\\]/middle-name\\[(\\d+)\\]" : "ackAuthor$5MiddleName",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/acknowledgements\\[(\\d+)\\]/person\\[(\\d+)\\]/family-name\\[(\\d+)\\]" : "ackAuthor$5Surname",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/acknowledgements\\[(\\d+)\\]/person\\[(\\d+)\\]/email-address\\[(\\d+)\\]" : "ackAuthor$5Email",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/acknowledgements\\[(\\d+)\\]/person\\[(\\d+)\\]/institution\\[(\\d+)\\]" : "ackAuthor$5Institute",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/acknowledgements\\[(\\d+)\\]/person\\[(\\d+)\\]/person-is-contactable\\[(\\d+)\\]" : "ackAuthor$5Contactable",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/study-status\\[(\\d+)\\]" : "StudyStatus",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/modules\\[(\\d+)\\]" : "WWARNmodules"
			
	};
	
	return studyCustomFieldLabelsMapAsAssociativeArray;	
}

function retrieveStudyInfoCustomFieldLabelsRegExMapAsAssociativeArray () {

	var studyInfoCustomFieldLabelsMapAsAssociativeArray = null;

	//TODO:replace with version retrieved from service
	
	//NOTE: Patterns must be in the form "atom:entry\\[(\\d+)\\]/manta:id\\[(\\d+)\\]"
	studyInfoCustomFieldLabelsMapAsAssociativeArray = {
			
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/studyInfoStatus\\[(\\d+)\\]" : "StudyInfoStatus",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/start\\[(\\d+)\\]" : "StudyStartDate",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/end\\[(\\d+)\\]" : "StudyEndDate",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/country\\[(\\d+)\\]" : "Site$5Country",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/region\\[(\\d+)\\]" : "Site$5Region",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/district\\[(\\d+)\\]" : "Site$5District",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/locality\\[(\\d+)\\]" : "Site$5Locality",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/lookupAddress\\[(\\d+)\\]" : "Site$5Address",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/siteCode\\[(\\d+)\\]" : "Site$5Code",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/testingDelay\\[(\\d+)\\]" : "site$5testing_delay",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/anticoagulant\\[(\\d+)\\]" : "site$5anticoagulant",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/transportAndStorageTemperature\\[(\\d+)\\]" : "site$5transportstorage_temp",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/latitude\\[(\\d+)\\]" : "Site$5Latitude",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/longitude\\[(\\d+)\\]" : "Site$5Longitude",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/transmissionIntensity\\[(\\d+)\\]/annualParasitologicalIncidence\\[(\\d+)\\]" : "Site$5API",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/transmissionIntensity\\[(\\d+)\\]/annualParasitologicalIncidenceYear\\[(\\d+)\\]" : "Site$5APIyear",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/transmissionIntensity\\[(\\d+)\\]/parasitePrevalence\\[(\\d+)\\]" : "Site$5ParasitePrev",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/transmissionIntensity\\[(\\d+)\\]/parasitePrevalenceYear\\[(\\d+)\\]" : "Site$5ParasitePrevYear",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/transmissionIntensity\\[(\\d+)\\]/transmissionIntensityAgeFrom\\[(\\d+)\\]" : "Site$5APILowerAge",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/transmissionIntensity\\[(\\d+)\\]/transmissionIntensityAgeFromUnits\\[(\\d+)\\]" : "Site$5APILowerAgeUnits",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/transmissionIntensity\\[(\\d+)\\]/transmissionIntensityAgeTo\\[(\\d+)\\]" : "Site$5APIUpperAge",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/transmissionIntensity\\[(\\d+)\\]/transmissionIntensityAgeToUnits\\[(\\d+)\\]" : "Site$5APIAgeUnits",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/transmissionIntensity\\[(\\d+)\\]/entomologicalInoculationRate\\[(\\d+)\\]" : "Site$5EIR",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/transmissionIntensity\\[(\\d+)\\]/entomologicalInoculationRateYear\\[(\\d+)\\]" : "Site$5EIRyear",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/transmissionIntensity\\[(\\d+)\\]/seasonalTransmission\\[(\\d+)\\]" : "Site$5SeasonalTrans",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/transmissionIntensity\\[(\\d+)\\]/transmissionMonths\\[(\\d+)\\]" : "Site$5TransSeasons",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/pathogens\\[(\\d+)\\]" : "PathogenSpecies",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/inclusionExclusionCriteria\\[(\\d+)\\]/age\\[(\\d+)\\]/maxAge\\[(\\d+)\\]" : "MaxAge",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/inclusionExclusionCriteria\\[(\\d+)\\]/age\\[(\\d+)\\]/maxAgeUnits\\[(\\d+)\\]" : "MaxAgeUnits",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/inclusionExclusionCriteria\\[(\\d+)\\]/age\\[(\\d+)\\]/minAge\\[(\\d+)\\]" : "MinAge",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/inclusionExclusionCriteria\\[(\\d+)\\]/age\\[(\\d+)\\]/minAgeUnits\\[(\\d+)\\]" : "MinAgeUnits",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/inclusionExclusionCriteria\\[(\\d+)\\]/parasitaemia\\[(\\d+)\\]/minParasitaemia\\[(\\d+)\\]" : "MinPara",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/inclusionExclusionCriteria\\[(\\d+)\\]/parasitaemia\\[(\\d+)\\]/maxParasitaemia\\[(\\d+)\\]" : "MaxPara",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/inclusionExclusionCriteria\\[(\\d+)\\]/includeMixedInfections\\[(\\d+)\\]" : "MixedInfections",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/inclusionExclusionCriteria\\[(\\d+)\\]/excludeIfPriorAntimalarials\\[(\\d+)\\]" : "PriorAntimalarial",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/inclusionExclusionCriteria\\[(\\d+)\\]/priorAntimalarialsExclusion\\[(\\d+)\\]/priorAntimalarials\\[(\\d+)\\]" : "PriorAntimalarialList",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/inclusionExclusionCriteria\\[(\\d+)\\]/priorAntimalarialsExclusion\\[(\\d+)\\]/priorAntimalarialsDetermination\\[(\\d+)\\]" : "PriorAntimalarialDetermin",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/inclusionExclusionCriteria\\[(\\d+)\\]/priorAntimalarialsExclusion\\[(\\d+)\\]/priorAntimalarialsHistoryWeeks\\[(\\d+)\\]" : "PriorAntimalarialHistWeeks",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/inclusionExclusionCriteria\\[(\\d+)\\]/pregnancy\\[(\\d+)\\]" : "Pregnancy",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/inclusionExclusionCriteria\\[(\\d+)\\]/treatmentReason\\[(\\d+)\\]" : "TreatmentReason",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/inclusionExclusionCriteria\\[(\\d+)\\]/otherCriteria\\[(\\d+)\\]" : "OtherCriteria",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/regimenName\\[(\\d+)\\]" : "Reg$7Name",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/regimenSupervision\\[(\\d+)\\]" : "Reg$7Supervision",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/regimenUrl\\[(\\d+)\\]" : "Reg$7URL",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/name\\[(\\d+)\\]" : "Reg$7Drug$9Name",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/activeIngredients\\[(\\d+)\\]/activeIngredient\\[(\\d+)\\]/activeIngredientName\\[(\\d+)\\]" : "Reg$7Drug$9Act$11Name",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/activeIngredients\\[(\\d+)\\]/activeIngredient\\[(\\d+)\\]/activeIngredientMgPerDose\\[(\\d+)\\]" : "Reg$7Drug$9Act$11Dose",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/administrationRoute\\[(\\d+)\\]" : "Reg$7Drug$9AdmRoute",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/manufacturer\\[(\\d+)\\]" : "Reg$7Drug$9Manufacturer",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/batchNumber\\[(\\d+)\\]" : "Reg$7Drug$9BatchNo",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/expiryDate\\[(\\d+)\\]" : "Reg$7Drug$9ExpiryDate",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/drugStorage\\[(\\d+)\\]" : "Reg$7Drug$9Storage",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/drugDosingDeterminant\\[(\\d+)\\]" : "Reg$7Drug$9Dosing",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/ageDosing\\[(\\d+)\\]/ageDosingSchedule\\[(\\d+)\\]/day\\[(\\d+)\\]" : "Reg$7Drug$9AgeSch$11Day",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/ageDosing\\[(\\d+)\\]/ageDosingSchedule\\[(\\d+)\\]/hour\\[(\\d+)\\]" : "Reg$7Drug$9AgeSch$11Hour",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/ageDosing\\[(\\d+)\\]/ageDosingSchedule\\[(\\d+)\\]/ageFrom\\[(\\d+)\\]" : "Reg$7Drug$9AgeSch$11Agemin",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/ageDosing\\[(\\d+)\\]/ageDosingSchedule\\[(\\d+)\\]/ageTo\\[(\\d+)\\]" : "Reg$7Drug$9AgeSch$11Agemax",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/ageDosing\\[(\\d+)\\]/ageDosingSchedule\\[(\\d+)\\]/dose\\[(\\d+)\\]" : "Reg$7Drug$9AgeSch$11Dose",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/ageDosing\\[(\\d+)\\]/ageDosingSchedule\\[(\\d+)\\]/doseUnit\\[(\\d+)\\]" : "Reg$7Drug$9AgeSch$11DoseUnit",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/weightGroupDosing\\[(\\d+)\\]/weightGroupDosingSchedule\\[(\\d+)\\]/day\\[(\\d+)\\]" : "Reg$7Drug$9WgtGrpSch$11Day",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/weightGroupDosing\\[(\\d+)\\]/weightGroupDosingSchedule\\[(\\d+)\\]/hour\\[(\\d+)\\]" : "Reg$7Drug$9WgtGrpSch$11Hour",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/weightGroupDosing\\[(\\d+)\\]/weightGroupDosingSchedule\\[(\\d+)\\]/weightGroupFrom\\[(\\d+)\\]" : "Reg$7Drug$9WgtGrpSch$11WgtMin",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/weightGroupDosing\\[(\\d+)\\]/weightGroupDosingSchedule\\[(\\d+)\\]/weightGroupTo\\[(\\d+)\\]" : "Reg$7Drug$9WgtGrpSch$11WgtMax",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/weightGroupDosing\\[(\\d+)\\]/weightGroupDosingSchedule\\[(\\d+)\\]/dose\\[(\\d+)\\]" : "Reg$7Drug$9WgtGrpSch$11Dose",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/weightGroupDosing\\[(\\d+)\\]/weightGroupDosingSchedule\\[(\\d+)\\]/doseUnit\\[(\\d+)\\]" : "Reg$7Drug$9WgtGrpSch$11DoseUnit",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/weightDosing\\[(\\d+)\\]/weightDosingSchedule\\[(\\d+)\\]/day\\[(\\d+)\\]" : "Reg$7Drug$9WgtSch$11Day",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/weightDosing\\[(\\d+)\\]/weightDosingSchedule\\[(\\d+)\\]/hour\\[(\\d+)\\]" : "Reg$7Drug$9WgtSch$11Hour",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/weightDosing\\[(\\d+)\\]/weightDosingSchedule\\[(\\d+)\\]/dose\\[(\\d+)\\]" : "Reg$7Drug$9WgtSch$11WeightMin",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/feeding\\[(\\d+)\\]" : "Reg$7Drug$9Foodintake",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/feedingOther\\[(\\d+)\\]" : "Reg$7Drug$9FoodintakeOther",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/readministeredOnVomitting\\[(\\d+)\\]" : "Reg$7Drug$9Readmin",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimens\\[(\\d+)\\]/regimen\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/comments\\[(\\d+)\\]" : "Reg$7Drug$9Comments",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimenAllocation\\[(\\d+)\\]/regimenAllocationMethod\\[(\\d+)\\]" : "RegAllocation",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/treatment\\[(\\d+)\\]/regimenAllocation\\[(\\d+)\\]/blinding\\[(\\d+)\\]" : "Blinding",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/followup\\[(\\d+)\\]/duration\\[(\\d+)\\]" : "DayOfFollowup",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/followup\\[(\\d+)\\]/feverMeasurement\\[(\\d+)\\]" : "FeverMeasurement",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/followup\\[(\\d+)\\]/haemoglobinRecording\\[(\\d+)\\]/haemoglobinRecordingType\\[(\\d+)\\]" : "HbRecording",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/microscopyStain\\[(\\d+)\\]" : "MicroStain",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/microscopyStainOther\\[(\\d+)\\]" : "MicroStainOther",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/asexualParasitemia\\[(\\d+)\\]/asexualParasitemiaNegativeCount\\[(\\d+)\\]" : "AsexParaNegFields",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/asexualParasitemia\\[(\\d+)\\]/asexualParasitemiaPositiveThickUnit\\[(\\d+)\\]" : "AsexParaThickUnits",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/asexualParasitemia\\[(\\d+)\\]/asexualParasitemiaPositiveThickUnitOther\\[(\\d+)\\]" : "AsexParaThickUnitsOther",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/asexualParasitemia\\[(\\d+)\\]/asexualParasitemiaPositiveThinUnit\\[(\\d+)\\]" : "AsexParaThinUnits",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/asexualParasitemia\\[(\\d+)\\]/asexualParasitemiaPositiveThinUnitOther\\[(\\d+)\\]" : "AsexParaThinUnitsOther",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/sexualParasitemia\\[(\\d+)\\]/sexualParasitemiaNegativeCount\\[(\\d+)\\]" : "SexParaNegFields",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/sexualParasitemia\\[(\\d+)\\]/sexualParasitemiaPositiveThickUnit\\[(\\d+)\\]" : "SexParaThickUnits",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/sexualParasitemia\\[(\\d+)\\]/sexualParasitemiaPositiveThickUnitOther\\[(\\d+)\\]" : "SexParaThickUnitsOther",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/sexualParasitemia\\[(\\d+)\\]/sexualParasitemiaPositiveThinUnit\\[(\\d+)\\]" : "SexParaThinUnits",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/sexualParasitemia\\[(\\d+)\\]/sexualParasitemiaPositiveThinUnitOther\\[(\\d+)\\]" : "SexParaThinUnitsOther",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/thickFilmCalculationOfParasitemia\\[(\\d+)\\]/thickFilmFormula\\[(\\d+)\\]" : "ThickFilmFormula",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/thickFilmCalculationOfParasitemia\\[(\\d+)\\]/thickFilmFormulaOther\\[(\\d+)\\]" : "ThinFilmFormula",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/thinFilmCalculationOfParasitemia\\[(\\d+)\\]/thinFilmFormula\\[(\\d+)\\]" : "ThickFilmFormulaOther",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/thinFilmCalculationOfParasitemia\\[(\\d+)\\]/thinFilmFormulaOther\\[(\\d+)\\]" : "ThinFilmFormulaOther",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/qualityControl\\[(\\d+)\\]/internal\\[(\\d+)\\]/percentageRereadBySecondMicroscopist\\[(\\d+)\\]" : "PercentSlidesRereadInt",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/qualityControl\\[(\\d+)\\]/internal\\[(\\d+)\\]/rereadSlideSelectionMechanism\\[(\\d+)\\]" : "SlidesRereadIntMech",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/qualityControl\\[(\\d+)\\]/internal\\[(\\d+)\\]/rereadSlideSelectionMechanismOther\\[(\\d+)\\]" : "SlidesRereadIntMechOther",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/qualityControl\\[(\\d+)\\]/external\\[(\\d+)\\]/percentageRereadBySecondMicroscopist\\[(\\d+)\\]" : "PercentSlidesRereadEx",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/qualityControl\\[(\\d+)\\]/external\\[(\\d+)\\]/rereadSlideSelectionMechanism\\[(\\d+)\\]" : "SlidesRereadExMech",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/microscopy\\[(\\d+)\\]/qualityControl\\[(\\d+)\\]/external\\[(\\d+)\\]/rereadSlideSelectionMechanismOther\\[(\\d+)\\]" : "SlidesRereadExMechOther",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\\[(\\d+)\\]/applicability\\[(\\d+)\\]" : "GenotypingRecrudReinfe",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\\[(\\d+)\\]/applicable\\[(\\d+)\\]/markers\\[(\\d+)\\]/recrudescenceMarker\\[(\\d+)\\]/numberOfMicroSatellites\\[(\\d+)\\]" : "GenotypMarker$8MicroSatels",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\\[(\\d+)\\]/applicable\\[(\\d+)\\]/markers\\[(\\d+)\\]/recrudescenceMarker\\[(\\d+)\\]/markerName\\[(\\d+)\\]" : "GenotypingMarker$8",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\\[(\\d+)\\]/applicable\\[(\\d+)\\]/markers\\[(\\d+)\\]/recrudescenceMarker\\[(\\d+)\\]/markerOther\\[(\\d+)\\]" : "GenotypingMarker$8Other",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\\[(\\d+)\\]/applicable\\[(\\d+)\\]/genotypingLaboratory\\[(\\d+)\\]" : "GenotypingLab",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\\[(\\d+)\\]/applicable\\[(\\d+)\\]/markerDiscriminantOpen\\[(\\d+)\\]/markerDiscriminant\\[(\\d+)\\]" : "GenotypingMethod",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\\[(\\d+)\\]/applicable\\[(\\d+)\\]/markerDiscriminantOpen\\[(\\d+)\\]/markerDiscriminantOther\\[(\\d+)\\]" : "GenotypingMethodOther",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\\[(\\d+)\\]/applicable\\[(\\d+)\\]/analysisProtocol\\[(\\d+)\\]/mixedAllelesOpen\\[(\\d+)\\]/mixedAlleles\\[(\\d+)\\]" : "MixedAllelesDefinition",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\\[(\\d+)\\]/applicable\\[(\\d+)\\]/analysisProtocol\\[(\\d+)\\]/mixedAllelesOpen\\[(\\d+)\\]/mixedAllelesOther\\[(\\d+)\\]" : "MixedAllelesDefinitionOther",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\\[(\\d+)\\]/applicable\\[(\\d+)\\]/analysisProtocol\\[(\\d+)\\]/mixedAllelesOpen\\[(\\d+)\\]/recrudescence\\[(\\d+)\\]" : "RecudesenceDefinition",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/clinical\\[(\\d+)\\]/geneotypingToDistinguishBetweenRecrudescenceAndReinfection\\[(\\d+)\\]/applicable\\[(\\d+)\\]/analysisProtocol\\[(\\d+)\\]/mixedAllelesOpen\\[(\\d+)\\]/reinfection\\[(\\d+)\\]" : "ReinfectionDefinition",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/criteria\\[(\\d+)\\]/sampleSourceOpen\\[(\\d+)\\]/sampleSource\\[(\\d+)\\]" : "MolSampleSource",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/criteria\\[(\\d+)\\]/sampleSourceOpen\\[(\\d+)\\]/sampleSourceOther\\[(\\d+)\\]" : "MolSampleSourceOther",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/criteria\\[(\\d+)\\]/malariaStatus\\[(\\d+)\\]" : "MalariaDiseaseStatus",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/sample\\[(\\d+)\\]/sampleTypeOpen\\[(\\d+)\\]/sampleType\\[(\\d+)\\]" : "MolSampleType",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/sample\\[(\\d+)\\]/sampleTypeOpen\\[(\\d+)\\]/sampleTypeOther\\[(\\d+)\\]" : "MolSampleTypeOther",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/genotypedMarkers\\[(\\d+)\\]/genotypedMarker\\[(\\d+)\\]/genotypingMethodOpen\\[(\\d+)\\]/genotypingMethod\\[(\\d+)\\]" : "MolMarker$6GenotypingMethod",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/genotypedMarkers\\[(\\d+)\\]/genotypedMarker\\[(\\d+)\\]/genotypingMethodOpen\\[(\\d+)\\]/genotypingMethodOther\\[(\\d+)\\]" : "MolMarker$6GenotypMethodOther",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/genotypedMarkers\\[(\\d+)\\]/genotypedMarker\\[(\\d+)\\]/molecularMarkerFalciparumOpen\\[(\\d+)\\]/molecularMarkerFalciparum\\[(\\d+)\\]" : "MolMarker$6Falciparum",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/genotypedMarkers\\[(\\d+)\\]/genotypedMarker\\[(\\d+)\\]/molecularMarkerFalciparumOpen\\[(\\d+)\\]/molecularMarkerFalciparumOther\\[(\\d+)\\]" : "MolMarker$6FalciparumOther",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/genotypedMarkers\\[(\\d+)\\]/genotypedMarker\\[(\\d+)\\]/molecularMarkerVivaxOpen\\[(\\d+)\\]/molecularMarkerVivax\\[(\\d+)\\]" : "MolMarker$6Vivax",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/genotypedMarkers\\[(\\d+)\\]/genotypedMarker\\[(\\d+)\\]/molecularMarkerVivaxOpen\\[(\\d+)\\]/molecularMarkerVivaxOther\\[(\\d+)\\]" : "MolMarker$6VivaxOther",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/genotypedMarkers\\[(\\d+)\\]/genotypedMarker\\[(\\d+)\\]/molecularMarkerOvaleOpen\\[(\\d+)\\]/molecularMarkerOvale\\[(\\d+)\\]" : "MolMarker$6Ovale",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/genotypedMarkers\\[(\\d+)\\]/genotypedMarker\\[(\\d+)\\]/molecularMarkerOvaleOpen\\[(\\d+)\\]/molecularMarkerOvaleOther\\[(\\d+)\\]" : "MolMarker$6OvaleFill",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/genotypedMarkers\\[(\\d+)\\]/genotypedMarker\\[(\\d+)\\]/molecularMarkerMalariaeOpen\\[(\\d+)\\]/molecularMarkerMalariae\\[(\\d+)\\]" : "MolMarker$6Malariae",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/genotypedMarkers\\[(\\d+)\\]/genotypedMarker\\[(\\d+)\\]/molecularMarkerMalariaeOpen\\[(\\d+)\\]/molecularMarkerMalariaeOther\\[(\\d+)\\]" : "MolMarker$6MalariaeOther",			
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/genotypedMarkers\\[(\\d+)\\]/genotypedMarker\\[(\\d+)\\]/molecularMarkerKnowlesiOpen\\[(\\d+)\\]/molecularMarkerKnowlesi\\[(\\d+)\\]" : "MolMarker$6Knowlesi",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/genotypedMarkers\\[(\\d+)\\]/genotypedMarker\\[(\\d+)\\]/molecularMarkerKnowlesiOpen\\[(\\d+)\\]/molecularMarkerKnowlesiOther\\[(\\d+)\\]" : "MolMarker$6KnowlesiOther",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/genotypedMarkers\\[(\\d+)\\]/genotypedMarker\\[(\\d+)\\]/sampleSelectionMethodOpen\\[(\\d+)\\]/sampleSelectionMethod\\[(\\d+)\\]" : "Mol$6SampleSelectionMethod",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/genotypedMarkers\\[(\\d+)\\]/genotypedMarker\\[(\\d+)\\]/sampleSelectionMethodOpen\\[(\\d+)\\]/sampleSelectionMethodOther\\[(\\d+)\\]" : "Mol$6SampleSelectnMethodOther",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/genotypedMarkers\\[(\\d+)\\]/genotypedMarker\\[(\\d+)\\]/sampleSelectionMethodOpen\\[(\\d+)\\]/sampleSelectionProportion\\[(\\d+)\\]" : "Mol$6SampleSelectionProport",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/mixedResistanceAlleles\\[(\\d+)\\]/mixedResistanceAllelesInclusion\\[(\\d+)\\]" : "MixedResistanceAllelesInclu",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/mixedResistanceAlleles\\[(\\d+)\\]/mixedResistanceAllelesDesignation\\[(\\d+)\\]" : "MixedResistanceAllelesDesig",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/additionalGenotypicInformation\\[(\\d+)\\]/sequencedLoci\\[(\\d+)\\]/wholeGenomesSequenced\\[(\\d+)\\]" : "GenomeSeq",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/additionalGenotypicInformation\\[(\\d+)\\]/sequencedLoci\\[(\\d+)\\]/resistanceLociSequenced\\[(\\d+)\\]" : "ResistanceLociSeq",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/additionalGenotypicInformation\\[(\\d+)\\]/sequencedLoci\\[(\\d+)\\]/resistanceLoci\\[(\\d+)\\]/resistanceLocus\\[(\\d+)\\]/locusName\\[(\\d+)\\]" : "ResistanceLociSeq$8Name",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/additionalGenotypicInformation\\[(\\d+)\\]/sequencedLoci\\[(\\d+)\\]/otherLociGenotyped\\[(\\d+)\\]" : "OtherLociGenotyped",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/additionalGenotypicInformation\\[(\\d+)\\]/sequencedLoci\\[(\\d+)\\]/otherLoci\\[(\\d+)\\]/otherLocus\\[(\\d+)\\]/locusTypeOpen\\[(\\d+)\\]/locusType\\[(\\d+)\\]" : "OtherLoci$8Type",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/additionalGenotypicInformation\\[(\\d+)\\]/sequencedLoci\\[(\\d+)\\]/otherLoci\\[(\\d+)\\]/otherLocus\\[(\\d+)\\]/locusTypeOpen\\[(\\d+)\\]/locusTypeOther\\[(\\d+)\\]" : "OtherLoci$8TypeOther",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/additionalGenotypicInformation\\[(\\d+)\\]/sequencedLoci\\[(\\d+)\\]/otherLoci\\[(\\d+)\\]/otherLocus\\[(\\d+)\\]/locusName\\[(\\d+)\\]" : "OtherLoci$8Name",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/molecular\\[(\\d+)\\]/additionalGenotypicInformation\\[(\\d+)\\]/infectionComplexityEstimated\\[(\\d+)\\]" : "COIEstimated",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/analysisSite\\[(\\d+)\\]" : "analysis_site1",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/culture\\[(\\d+)\\]/incubatorSystem\\[(\\d+)\\]" : "incubator_system",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/culture\\[(\\d+)\\]/co2percentage\\[(\\d+)\\]" : "co2percentage",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/culture\\[(\\d+)\\]/co2Other\\[(\\d+)\\]" : "co2other",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/culture\\[(\\d+)\\]/o2percentage\\[(\\d+)\\]" : "o2percentage",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/culture\\[(\\d+)\\]/o2Other\\[(\\d+)\\]" : "o2other",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/culture\\[(\\d+)\\]/healthyErythrocytesSource\\[(\\d+)\\]" : "healthyErythocytes",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/culture\\[(\\d+)\\]/hematocritpercent\\[(\\d+)\\]" : "hematocrit",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/culture\\[(\\d+)\\]/bloodGroup\\[(\\d+)\\]" : "bloodgroup",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/drugSusceptibilityMedium\\[(\\d+)\\]/medium\\[(\\d+)\\]" : "drug1susmedium",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/drugSusceptibilityMedium\\[(\\d+)\\]/mediumOther\\[(\\d+)\\]" : "drug1susmedium_other",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/drugSusceptibilityMedium\\[(\\d+)\\]/preparation\\[(\\d+)\\]" : "drug1susmedium_prepa",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/drugSusceptibilityMedium\\[(\\d+)\\]/serum\\[(\\d+)\\]" : "drug1susmedium_serum",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/drugSusceptibilityMedium\\[(\\d+)\\]/serum-finalConcentration\\[(\\d+)\\]" : "drug1susmedium_serumfinal",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/drugSusceptibilityMedium\\[(\\d+)\\]/NaHCO3-finalConcentration\\[(\\d+)\\]" : "drug1susmedium_NaHCO3",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/drugSusceptibilityMedium\\[(\\d+)\\]/hypoxantine\\[(\\d+)\\]/hypoxantine-added\\[(\\d+)\\]" : "drug1susmedium_hypox",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/drugSusceptibilityMedium\\[(\\d+)\\]/hypoxantine\\[(\\d+)\\]/hypoxantine-finalConcentration\\[(\\d+)\\]" : "fconcentration_hypox",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/drugSusceptibilityMedium\\[(\\d+)\\]/oroticAcid\\[(\\d+)\\]/oroticAcid-added\\[(\\d+)\\]" : "drug1susmedium_oroticacid",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/drugSusceptibilityMedium\\[(\\d+)\\]/oroticAcid\\[(\\d+)\\]/oroticAcid-finalConcentration\\[(\\d+)\\]" : "fconcentration_orocticid",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/drugSusceptibilityMedium\\[(\\d+)\\]/glucose\\[(\\d+)\\]/glucose-added\\[(\\d+)\\]" : "drug1susmedium_gluc",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/drugSusceptibilityMedium\\[(\\d+)\\]/glucose\\[(\\d+)\\]/glucose-finalConcentration\\[(\\d+)\\]" : "fconcentration_gluc",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/drugSusceptibilityMedium\\[(\\d+)\\]/antibioticTreatments\\[(\\d+)\\]/antibioticTreatment\\[(\\d+)\\]/antibioticOpen\\[(\\d+)\\]/antibiotic\\[(\\d+)\\]" : "drug1susmedium_atb$7",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/drugSusceptibilityMedium\\[(\\d+)\\]/antibioticTreatments\\[(\\d+)\\]/antibioticTreatment\\[(\\d+)\\]/antibioticOpen\\[(\\d+)\\]/antibioticOther\\[(\\d+)\\]" : "drug1susmedium_atb$7other",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/drugSusceptibilityMedium\\[(\\d+)\\]/antibioticTreatments\\[(\\d+)\\]/antibioticTreatment\\[(\\d+)\\]/antibioticFinalConcentration\\[(\\d+)\\]" : "fconcentration_atb$7",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/susceptibility\\[(\\d+)\\]/timeOfIncubation\\[(\\d+)\\]" : "incubation_time",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/susceptibility\\[(\\d+)\\]/susceptibilityMethod\\[(\\d+)\\]" : "drugsus_method",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/molecule\\[(\\d+)\\]" : "drug$6",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/solvent\\[(\\d+)\\]" : "solvent_drug$6",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/solventFinalConcentration\\[(\\d+)\\]" : "drug$6_fconcentration_solvent",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/providedByWwarn\\[(\\d+)\\]" : "drug$6_fromWWARN",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/drugs\\[(\\d+)\\]/drug\\[(\\d+)\\]/provider\\[(\\d+)\\]" : "drug$6_provider",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/platePreparationMethod\\[(\\d+)\\]" : "plateprepa_method",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/platesPreparationDate\\[(\\d+)\\]" : "plateprepa_date",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/plateBatches\\[(\\d+)\\]/batch\\[(\\d+)\\]/clone3D7ProvidedByWwarn\\[(\\d+)\\]" : "batch$6_clone3D7_fromWWARN",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/plateBatches\\[(\\d+)\\]/batch\\[(\\d+)\\]/clone3D7Provider\\[(\\d+)\\]" : "batch$6_clone3D7_provider",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/plateBatches\\[(\\d+)\\]/batch\\[(\\d+)\\]/batchesIncludedInRawData\\[(\\d+)\\]" : "batches$6in_rawdata",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/plateBatches\\[(\\d+)\\]/batch\\[(\\d+)\\]/preparationDate\\[(\\d+)\\]" : "batch$6_prepa_date",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/plateBatches\\[(\\d+)\\]/batch\\[(\\d+)\\]/parasitaemia3D7percentage\\[(\\d+)\\]" : "batch$6_parsitemia_%",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/invitro\\[(\\d+)\\]/plateBatches\\[(\\d+)\\]/batch\\[(\\d+)\\]/ringFormingPercentage\\[(\\d+)\\]" : "batch$6_ringforming%",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/pharmacology\\[(\\d+)\\]/samples\\[(\\d+)\\]/sample\\[(\\d+)\\]/anticoagulent\\[(\\d+)\\]" : "Anticoagulant$6",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/pharmacology\\[(\\d+)\\]/samples\\[(\\d+)\\]/sample\\[(\\d+)\\]/centrifugeTime\\[(\\d+)\\]" : "TimeToCentrifuge$6",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/pharmacology\\[(\\d+)\\]/samples\\[(\\d+)\\]/sample\\[(\\d+)\\]/storages\\[(\\d+)\\]/storage\\[(\\d+)\\]/storageTemperature\\[(\\d+)\\]" : "Smpl$6TemperatureStorage$8",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/pharmacology\\[(\\d+)\\]/samples\\[(\\d+)\\]/sample\\[(\\d+)\\]/storages\\[(\\d+)\\]/storage\\[(\\d+)\\]/storageDuration\\[(\\d+)\\]" : "Smpl$6DuratnTemperatrStorage$8",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/pharmacology\\[(\\d+)\\]/samples\\[(\\d+)\\]/sample\\[(\\d+)\\]/storages\\[(\\d+)\\]/storage\\[(\\d+)\\]/storageDurationUnit\\[(\\d+)\\]" : "Smpl$6UnitStorageDuration$8",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/pharmacology\\[(\\d+)\\]/analytes\\[(\\d+)\\]/analyte\\[(\\d+)\\]/drugMeasured\\[(\\d+)\\]" : "NameDrugMetoboliteMeasured$6",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/pharmacology\\[(\\d+)\\]/analytes\\[(\\d+)\\]/analyte\\[(\\d+)\\]/lowerLoQ\\[(\\d+)\\]" : "LLOQ$6",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/pharmacology\\[(\\d+)\\]/analytes\\[(\\d+)\\]/analyte\\[(\\d+)\\]/sampleMatrixType\\[(\\d+)\\]" : "SampleMatrix$6",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/pharmacology\\[(\\d+)\\]/assayReferences\\[(\\d+)\\]/assayReference\\[(\\d+)\\]/referenceType\\[(\\d+)\\]" : "AssayMethodsReference$6Type",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/pharmacology\\[(\\d+)\\]/assayReferences\\[(\\d+)\\]/assayReference\\[(\\d+)\\]/url\\[(\\d+)\\]" : "AssayMethodsReference$6url",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/pharmacology\\[(\\d+)\\]/assayReferences\\[(\\d+)\\]/assayReference\\[(\\d+)\\]/upload\\[(\\d+)\\]/uploadedUrl\\[(\\d+)\\]" : "AssayMethodsReference$6file",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/pharmacology\\[(\\d+)\\]/assayReferences\\[(\\d+)\\]/assayReference\\[(\\d+)\\]/doi\\[(\\d+)\\]" : "AssayMethodsReference$6doi",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/pharmacology\\[(\\d+)\\]/assayReferences\\[(\\d+)\\]/assayReference\\[(\\d+)\\]/note\\[(\\d+)\\]" : "AssayMethod$6Description",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/pharmacology\\[(\\d+)\\]/assayReferences\\[(\\d+)\\]/assayReference\\[(\\d+)\\]/assayValidated\\[(\\d+)\\]" : "AssayMethodsReference$6valid"

			
	};
	
	return studyInfoCustomFieldLabelsMapAsAssociativeArray;	
}

////Functions

//REM: Old method using associative arrays and alphabetic sorting
/*
function convertStudyAsAtomEntryXmlIntoStudyAsAssociativeArrayWithXPathFieldLabels (studyAsAtomEntryXml) {

	var studyAsAssociativeArrayWithXPathFieldLabels = null;

	var parentNodeBaseXPath = "atom:entry[1]";
	
	var nodeNamesToIgnoreAsAssociativeArray = {
			"#text" : true,
		    "atom:link" : true, 
		    "wizard-pane-to-show" : true, 
		    "ar:comment" : true,
		    "app:control" : true
	};
	
	studyAsAssociativeArrayWithXPathFieldLabels = convertXmlNodesIntoAssociativeArrayWithXPathFieldLabels(studyAsAtomEntryXml.documentElement.childNodes, parentNodeBaseXPath, nodeNamesToIgnoreAsAssociativeArray);

	return studyAsAssociativeArrayWithXPathFieldLabels;		
}
*/
//REM: Old method using associative arrays and alphabetic sorting
/*
function convertStudyInfoAsAtomEntryXmlIntoStudyInfoAsAssociativeArrayWithXPathFieldLabels (studyInfoAsAtomEntryXml) {

	var studyInfoAsAssociativeArrayWithXPathFieldLabels = null;

	var parentNodeBaseXPath = "atom:entry[1]";
	
	var nodeNamesToIgnoreAsAssociativeArray = {
			"#text" : true,
		    "atom:link" : true, 
		    "ar:comment" : true,
		    "app:control" : true,
		    "atom:author" : true,
		    "atom:id" : true,
		    "atom:published" : true,
		    "atom:updated" : true
	};
	
	studyInfoAsAssociativeArrayWithXPathFieldLabels = convertXmlNodesIntoAssociativeArrayWithXPathFieldLabels(studyInfoAsAtomEntryXml.documentElement.childNodes, parentNodeBaseXPath, nodeNamesToIgnoreAsAssociativeArray);

	return studyInfoAsAssociativeArrayWithXPathFieldLabels;		
}
*/


function convertStudyAsAtomEntryXmlIntoStudyAsObjectArrayWithXPathFieldLabels (studyAsAtomEntryXml) {

	var studyAsObjectArrayWithXPathFieldLabels = null;

	var parentNodeBaseXPath = "atom:entry[1]";
	
	var nodeNamesToIgnoreAsAssociativeArray = {
			"#text" : true,
		    "atom:link" : true, 
		    "wizard-pane-to-show" : true, 
		    "ar:comment" : true,
		    "app:control" : true
	};
	
	studyAsObjectArrayWithXPathFieldLabels = convertXmlNodesIntoObjectArrayWithXPathFieldLabels(studyAsAtomEntryXml.documentElement.childNodes, parentNodeBaseXPath, nodeNamesToIgnoreAsAssociativeArray);

	return studyAsObjectArrayWithXPathFieldLabels;		
}

function convertStudyInfoAsAtomEntryXmlIntoStudyInfoAsObjectArrayWithXPathFieldLabels (studyInfoAsAtomEntryXml) {

	var studyInfoAsObjectArrayWithXPathFieldLabels = null;

	var parentNodeBaseXPath = "atom:entry[1]";
	
	var nodeNamesToIgnoreAsAssociativeArray = {
			"#text" : true,
		    "atom:link" : true, 
		    "ar:comment" : true,
		    "app:control" : true,
		    "atom:author" : true,
		    "atom:id" : true,
		    "atom:published" : true,
		    "atom:updated" : true
	};
	
	studyInfoAsObjectArrayWithXPathFieldLabels = convertXmlNodesIntoObjectArrayWithXPathFieldLabels(studyInfoAsAtomEntryXml.documentElement.childNodes, parentNodeBaseXPath, nodeNamesToIgnoreAsAssociativeArray);

	return studyInfoAsObjectArrayWithXPathFieldLabels;		
}

function convertXmlNodesIntoAssociativeArrayWithXPathFieldLabels (XmlNodes, parentNodeBaseXPath, nodeNamesToIgnoreAsAssociativeArray) {

	var associativeArrayWithXPathFieldLabels = null;
	
	associativeArrayWithXPathFieldLabels = {};
	
	if (parentNodeBaseXPath == null) {
		parentNodeBaseXPath = "";
	}
	

	  if (XmlNodes.length == 1 && XmlNodes[0].nodeType == 3) {		  
	  
		// node is a leaf
		
			// Ignore empty nodes
			if ($.trim(XmlNodes[0].nodeValue) != "") { 

		    	associativeArrayWithXPathFieldLabels[parentNodeBaseXPath] = XmlNodes[0].nodeValue;
		    	
		    }
		    
	    
	  } else {
		  
	    // node is a branch
	        
	    var siblingCount = {};
	      
	    for (var i = 0; i < XmlNodes.length; i++){
	    	
	      var node = XmlNodes[i];
	
	      // Ignore certain node names
	      if (!(node.nodeName in nodeNamesToIgnoreAsAssociativeArray)) {
	              
	        if (siblingCount[node.nodeName]) {
	        	siblingCount[node.nodeName]++;
	        } else { 
	        	siblingCount[node.nodeName] = 1;
	        }
	        
	        var nodeBaseXpath = parentNodeBaseXPath + "/" + node.nodeName + "[" + siblingCount[node.nodeName] + "]";
	        
	        // add child nodes to associative array (recursive)
	        var childNodesAsAssociativeArrayWithXPathFieldLabels = convertXmlNodesIntoAssociativeArrayWithXPathFieldLabels(node.childNodes, nodeBaseXpath, nodeNamesToIgnoreAsAssociativeArray);
	        $.extend(associativeArrayWithXPathFieldLabels, childNodesAsAssociativeArrayWithXPathFieldLabels);

	        
	      }
	      
	    }
	    
	  }
	
	return associativeArrayWithXPathFieldLabels;		
}

function convertXmlNodesIntoObjectArrayWithXPathFieldLabels (XmlNodes, parentNodeBaseXPath, nodeNamesToIgnoreAsAssociativeArray) {

	var objectArrayWithXPathFieldLabels = null;
	
	objectArrayWithXPathFieldLabels = new Array();
	
	if (parentNodeBaseXPath == null) {
		parentNodeBaseXPath = "";
	}
	

	  if (XmlNodes.length == 1 && XmlNodes[0].nodeType == 3) {		  
	  
		// node is a leaf
		
			// Ignore empty nodes
			if ($.trim(XmlNodes[0].nodeValue) != "") { 

		    	objectArrayWithXPathFieldLabels.push( {"xPathFieldLabel" : parentNodeBaseXPath, "value" : XmlNodes[0].nodeValue} );
		    	
		    }
		    
	    
	  } else {
		  
	    // node is a branch
	        
	    var siblingCount = {};
	      
	    for (var i = 0; i < XmlNodes.length; i++){
	    	
	      var node = XmlNodes[i];
	
	      // Ignore certain node names
	      if (!(node.nodeName in nodeNamesToIgnoreAsAssociativeArray)) {
	              
	        if (siblingCount[node.nodeName]) {
	        	siblingCount[node.nodeName]++;
	        } else { 
	        	siblingCount[node.nodeName] = 1;
	        }
	        
	        var nodeBaseXpath = parentNodeBaseXPath + "/" + node.nodeName + "[" + siblingCount[node.nodeName] + "]";
	        
	        // add child nodes to associative array (recursive)
	        var childNodesAsObjectArrayWithXPathFieldLabels = convertXmlNodesIntoObjectArrayWithXPathFieldLabels(node.childNodes, nodeBaseXpath, nodeNamesToIgnoreAsAssociativeArray);
	        
	        objectArrayWithXPathFieldLabels = objectArrayWithXPathFieldLabels.concat(childNodesAsObjectArrayWithXPathFieldLabels);
	        

	        
	      }
	      
	    }
	    
	  }
	
	return objectArrayWithXPathFieldLabels;		
}

//REM: Old method using associative arrays and alphabetic sorting
/*
function convertAssociativeArrayIntoAssociativeArrayUsingRegExpKeyMapAsAssociativeArray (originalAssociativeArray, regExpMapAsAssociativeArray) {

	var associativeArrayWithMappedKeys = null;

	associativeArrayWithMappedKeys = {};
	
	for (var originalKey in originalAssociativeArray) {

		var matchFound = null;
		
		for (var regExpKey in regExpMapAsAssociativeArray) {

			
			var regularExpression = new RegExp(regExpKey);

			if (originalKey.match(regularExpression)) {
				
				matchFound = true;
				
				//alert(originalKey + " MATCHES " + regExpKey);
					
				var mappedKey = originalKey.replace(regularExpression, regExpMapAsAssociativeArray[regExpKey]);		
				
				associativeArrayWithMappedKeys[mappedKey] = originalAssociativeArray[originalKey];
			
				
				break;
			}
			
			
		}
		
		if (matchFound != true) {
			matchFound = false;
		}
		
		//NOTE: Keep unmatched key-value pairs, if the key doesn't already exist.
		if (matchFound == false && !(originalKey in associativeArrayWithMappedKeys) ) {
			associativeArrayWithMappedKeys[originalKey] = originalAssociativeArray[originalKey];
		}

	}

	
	return associativeArrayWithMappedKeys;			
	
}
*/

function addCustomFieldLabelPropertiesToObjectArrayWithXPathFieldLabelsUsingRegExpMapAsAssociativeArray (originalObjectArray, regExpMapAsAssociativeArray) {

	var objectArrayWithCustomFieldLabelProperties = null;

	objectArrayWithCustomFieldLabelProperties = new Array();
	
	for (var i = 0; i < originalObjectArray.length; i++) {
		
		var originalObject = originalObjectArray[i];

		var matchFound = null;
		
		for (var regExpKey in regExpMapAsAssociativeArray) {

			
			var regularExpression = new RegExp(regExpKey);

			if (originalObject.xPathFieldLabel.match(regularExpression)) {
				
				matchFound = true;
				
				//alert(originalObject.xPathFieldLabel + " MATCHES " + regExpKey);
					
				var customFieldLabel = originalObject.xPathFieldLabel.replace(regularExpression, regExpMapAsAssociativeArray[regExpKey]);		
				
				
				objectArrayWithCustomFieldLabelProperties[i] = 	{
																	"xPathFieldLabel" : originalObject.xPathFieldLabel, 
																	"value" : originalObject.value,
																	"customFieldLabel" : customFieldLabel  
																};
			
				
				break;
			}
			
			
		}
		
		if (matchFound != true) {
			matchFound = false;
		}
		
		//NOTE: Keep unmatched xPathFieldLabels
		if (matchFound == false) {
			
			objectArrayWithCustomFieldLabelProperties[i] = 	{
																"xPathFieldLabel" : originalObject.xPathFieldLabel, 
																"value" : originalObject.value,
																"customFieldLabel" : originalObject.xPathFieldLabel  
															};
			
		}

	}

	
	return objectArrayWithCustomFieldLabelProperties;			
	
}


//REM: Old method using associative arrays and alphabetic sorting
/*
function convertAssociativeArrayIntoSortedKeysArray (associativeArray) {
	
	var sortedKeysArray = new Array();
	
	for (var key in associativeArray) {
		
		sortedKeysArray.push(key);
	}
	
	return sortedKeysArray.sort();
	
}
*/

function convertStudyAsAtomEntryXmlIntoStudyInfoAtomEntryURL (studyAsAtomEntryXml) {
	
	var studyInfoAtomEntryURL = null;

	if (this.isChromeUserAgent()) {
		studyInfoAtomEntryURL = $(studyAsAtomEntryXml).find("[nodeName=atom:link][rel='http://www.cggh.org/2010/chassis/terms/studyInfo']").attr("href");
	} else {
		studyInfoAtomEntryURL = $(studyAsAtomEntryXml).find("atom\\:link[rel='http://www.cggh.org/2010/chassis/terms/studyInfo']").attr("href");
	}
	
	return studyInfoAtomEntryURL;
}

//REM: Old method using associative arrays and alphabetic sorting
/*
function convertStudyAsAssociativeArrayIntoStudyAsCsvColumns (studyAsAssociativeArray) {

	var studyAsCsvColumns = null;
	
	//Get a sorted list of the keys
	var sortedStudyKeysArray = convertAssociativeArrayIntoSortedKeysArray(studyAsAssociativeArray);

	if (sortedStudyKeysArray != null) {
		
		var headingsRow = "";
		var valuesRow = "";
		
		var regExp = new RegExp("\\n|\\r", "g");
		
		for (var i = 0; i < sortedStudyKeysArray.length; i++) {
			
			if (i != 0) {
				headingsRow += ",";
				valuesRow += ",";
			}
			
			headingsRow += sortedStudyKeysArray[i];
			
			//NOTE: Replace EOL chars with spaces. 
			valuesRow += studyAsAssociativeArray[sortedStudyKeysArray[i]].replace(regExp, " ");
			
		}
		
		//NOTE: Using Unix EOL chars.
		studyAsCsvColumns = headingsRow + "\n" + valuesRow + "\n";
	}
	
	
	return studyAsCsvColumns;
}
*/

function convertStudyAsObjectArrayIntoStudyAsCsvColumns (studyAsObjectArray) {

	var studyAsCsvColumns = null;

	var headingsRow = "";
	var valuesRow = "";

	//NOTE: Replace EOL chars with spaces. Replace double-quotes with double-double-quotes.	
	var regExp = new RegExp("\\n|\\r", "g");
	var regExp2 = new RegExp('"', "g");
	
	for (var i = 0; i < studyAsObjectArray.length; i++) {
		
		if (i != 0) {
			headingsRow += ",";
			valuesRow += ",";
		}
		
		headingsRow += "\"" + studyAsObjectArray[i].customFieldLabel + "\"";
		
		//NOTE: Replace EOL chars with spaces. Replace double-quotes with double-double-quotes.	
		valuesRow += "\"" + studyAsObjectArray[i].value.replace(regExp, " ").replace(regExp2, '""') + "\"";
		
	}
	
	//NOTE: Using Unix EOL chars.
	studyAsCsvColumns = headingsRow + "\n" + valuesRow + "\n";

	return studyAsCsvColumns;
}

//REM: Old method using associative arrays and alphabetic sorting
/*
function convertStudyAsAssociativeArrayIntoStudyAsCsvRows (studyAsAssociativeArray) {

	var studyAsCsvRows = null;
	
	//Get a sorted list of the keys
	var sortedStudyKeysArray = convertAssociativeArrayIntoSortedKeysArray(studyAsAssociativeArray);

	if (sortedStudyKeysArray != null) {
		
		var studyAsCsvRows = "";
		
		var regExp = new RegExp("\\n|\\r", "g");
		
		for (var i = 0; i < sortedStudyKeysArray.length; i++) {
			
			//NOTE: Replace EOL chars with spaces. 
			studyAsCsvRows += sortedStudyKeysArray[i] + "," + studyAsAssociativeArray[sortedStudyKeysArray[i]].replace(regExp, " ");
		
			//NOTE: Using Unix EOL chars.
			studyAsCsvRows += "\n";
		}
		
		
	}
	
	
	return studyAsCsvRows;
}
*/

function convertStudyAsObjectArrayIntoStudyAsCsvRows (studyAsObjectArray) {

	var studyAsCsvRows = null;
	
		
	var studyAsCsvRows = "";
	
	var regExp = new RegExp("\\n|\\r", "g");
	var regExp2 = new RegExp('"', "g");
	
	for (var i = 0; i < studyAsObjectArray.length; i++) {
		
		//NOTE: Replace EOL chars with spaces. 
		studyAsCsvRows += "\"" + studyAsObjectArray[i].customFieldLabel + "\"" + "," + "\"" + studyAsObjectArray[i].value.replace(regExp, " ").replace(regExp2, '""') + "\"";
	
		//NOTE: Using Unix EOL chars.
		studyAsCsvRows += "\n";
	}
	
	
	return studyAsCsvRows;
}

////Scripts
function generateStudyAsCsvColumnsWithStudyCustomFieldLabelsUsingStudyAtomEntryURL (studyAtomEntryURL) {
	
	//NOTE: This also fetches and combines the study-info data.
	
	//Broken down into variables to make the process easier to follow.
	
	var studyAsAtomEntryXml = retrieveStudyAsAtomEntryXmlUsingStudyAtomEntryURL(studyAtomEntryURL);
	
	var studyInfoAtomEntryURL = convertStudyAsAtomEntryXmlIntoStudyInfoAtomEntryURL(studyAsAtomEntryXml);
	
	var studyInfoAsAtomEntryXml = retrieveStudyInfoAsAtomEntryXmlUsingStudyInfoAtomEntryURL(studyInfoAtomEntryURL);
	
	
	//REM: Old method using associative arrays and alphabetic sorting
	//var studyAsAssociativeArrayWithXPathFieldLabels = convertStudyAsAtomEntryXmlIntoStudyAsAssociativeArrayWithXPathFieldLabels(studyAsAtomEntryXml);
	//var studyInfoAsAssociativeArrayWithXPathFieldLabels = convertStudyInfoAsAtomEntryXmlIntoStudyInfoAsAssociativeArrayWithXPathFieldLabels(studyInfoAsAtomEntryXml);
	
	//TODO: Code - done
	var studyAsObjectArrayWithXPathFieldLabels = convertStudyAsAtomEntryXmlIntoStudyAsObjectArrayWithXPathFieldLabels(studyAsAtomEntryXml);
	var studyInfoAsObjectArrayWithXPathFieldLabels = convertStudyInfoAsAtomEntryXmlIntoStudyInfoAsObjectArrayWithXPathFieldLabels(studyInfoAsAtomEntryXml);
	
	
	//REM: Old method using associative arrays and alphabetic sorting
	//var studyAsAssociativeArrayWithStudyCustomFieldLabels = convertAssociativeArrayIntoAssociativeArrayUsingRegExpKeyMapAsAssociativeArray(studyAsAssociativeArrayWithXPathFieldLabels, retrieveStudyCustomFieldLabelsRegExMapAsAssociativeArray());
	//var studyInfoAsAssociativeArrayWithStudyCustomFieldLabels = convertAssociativeArrayIntoAssociativeArrayUsingRegExpKeyMapAsAssociativeArray(studyInfoAsAssociativeArrayWithXPathFieldLabels, retrieveStudyInfoCustomFieldLabelsRegExMapAsAssociativeArray());

	
	//TODO: Code - done
	var studyAsObjectArrayWithStudyCustomFieldLabels = addCustomFieldLabelPropertiesToObjectArrayWithXPathFieldLabelsUsingRegExpMapAsAssociativeArray(studyAsObjectArrayWithXPathFieldLabels, retrieveStudyCustomFieldLabelsRegExMapAsAssociativeArray());
	var studyInfoAsObjectArrayWithStudyCustomFieldLabels = addCustomFieldLabelPropertiesToObjectArrayWithXPathFieldLabelsUsingRegExpMapAsAssociativeArray(studyInfoAsObjectArrayWithXPathFieldLabels, retrieveStudyInfoCustomFieldLabelsRegExMapAsAssociativeArray());

	
	//REM: Old method using associative arrays and alphabetic sorting
	//$.extend(studyAsAssociativeArrayWithStudyCustomFieldLabels, studyInfoAsAssociativeArrayWithStudyCustomFieldLabels);	
	
	//TODO: Combine the two object arrays
	var studyAndStudyInfoAsObjectArrayWithStudyCustomFieldLabels = studyAsObjectArrayWithStudyCustomFieldLabels.concat(studyInfoAsObjectArrayWithStudyCustomFieldLabels);

	//REM: Old method using associative arrays and alphabetic sorting
	//return convertStudyAsAssociativeArrayIntoStudyAsCsvColumns(studyAsAssociativeArrayWithStudyCustomFieldLabels);
	
	//TODO: Code
	return convertStudyAsObjectArrayIntoStudyAsCsvColumns(studyAndStudyInfoAsObjectArrayWithStudyCustomFieldLabels);
	
}
function generateStudyAsCsvRowsWithStudyCustomFieldLabelsUsingStudyAtomEntryURL (studyAtomEntryURL) {
	
	//NOTE: This also fetches and combines the study-info data.
	
	//Broken down into variables to make the process easier to follow.
	
	var studyAsAtomEntryXml = retrieveStudyAsAtomEntryXmlUsingStudyAtomEntryURL(studyAtomEntryURL);
	
	var studyInfoAtomEntryURL = convertStudyAsAtomEntryXmlIntoStudyInfoAtomEntryURL(studyAsAtomEntryXml);
	
	var studyInfoAsAtomEntryXml = retrieveStudyInfoAsAtomEntryXmlUsingStudyInfoAtomEntryURL(studyInfoAtomEntryURL);
	
	
	//REM: Old method using associative arrays and alphabetic sorting
	//var studyAsAssociativeArrayWithXPathFieldLabels = convertStudyAsAtomEntryXmlIntoStudyAsAssociativeArrayWithXPathFieldLabels(studyAsAtomEntryXml);
	//var studyInfoAsAssociativeArrayWithXPathFieldLabels = convertStudyInfoAsAtomEntryXmlIntoStudyInfoAsAssociativeArrayWithXPathFieldLabels(studyInfoAsAtomEntryXml);
	
	//TODO: Code - done
	var studyAsObjectArrayWithXPathFieldLabels = convertStudyAsAtomEntryXmlIntoStudyAsObjectArrayWithXPathFieldLabels(studyAsAtomEntryXml);
	var studyInfoAsObjectArrayWithXPathFieldLabels = convertStudyInfoAsAtomEntryXmlIntoStudyInfoAsObjectArrayWithXPathFieldLabels(studyInfoAsAtomEntryXml);
	
	
	//REM: Old method using associative arrays and alphabetic sorting
	//var studyAsAssociativeArrayWithStudyCustomFieldLabels = convertAssociativeArrayIntoAssociativeArrayUsingRegExpKeyMapAsAssociativeArray(studyAsAssociativeArrayWithXPathFieldLabels, retrieveStudyCustomFieldLabelsRegExMapAsAssociativeArray());
	//var studyInfoAsAssociativeArrayWithStudyCustomFieldLabels = convertAssociativeArrayIntoAssociativeArrayUsingRegExpKeyMapAsAssociativeArray(studyInfoAsAssociativeArrayWithXPathFieldLabels, retrieveStudyInfoCustomFieldLabelsRegExMapAsAssociativeArray());

	
	//TODO: Code - done
	var studyAsObjectArrayWithStudyCustomFieldLabels = addCustomFieldLabelPropertiesToObjectArrayWithXPathFieldLabelsUsingRegExpMapAsAssociativeArray(studyAsObjectArrayWithXPathFieldLabels, retrieveStudyCustomFieldLabelsRegExMapAsAssociativeArray());
	var studyInfoAsObjectArrayWithStudyCustomFieldLabels = addCustomFieldLabelPropertiesToObjectArrayWithXPathFieldLabelsUsingRegExpMapAsAssociativeArray(studyInfoAsObjectArrayWithXPathFieldLabels, retrieveStudyInfoCustomFieldLabelsRegExMapAsAssociativeArray());

	
	//REM: Old method using associative arrays and alphabetic sorting
	//$.extend(studyAsAssociativeArrayWithStudyCustomFieldLabels, studyInfoAsAssociativeArrayWithStudyCustomFieldLabels);	
	
	//TODO: Combine the two object arrays
	var studyAndStudyInfoAsObjectArrayWithStudyCustomFieldLabels = studyAsObjectArrayWithStudyCustomFieldLabels.concat(studyInfoAsObjectArrayWithStudyCustomFieldLabels);

	//REM: Old method using associative arrays and alphabetic sorting
	//return convertStudyAsAssociativeArrayIntoStudyAsCsvRows(studyAsAssociativeArrayWithStudyCustomFieldLabels);
	
	//TODO: Code
	return convertStudyAsObjectArrayIntoStudyAsCsvRows(studyAndStudyInfoAsObjectArrayWithStudyCustomFieldLabels);
	
}

function downloadStudyAsCsvColumnsWithCustomFieldLabelsUsingStudyAtomEntryURL (studyAtomEntryURL) {

	if (this.getDownloadStudyAsCsvColumnsWithStudyCustomFieldLabelsWindow() == null || this.getDownloadStudyAsCsvColumnsWithStudyCustomFieldLabelsWindow().closed) {
	
		if (this.isFirefoxUserAgent()) { 
	    	
	    	this.setDownloadStudyAsCsvColumnsWithStudyCustomFieldLabelsWindow(window.open("data:text/csv;charset=utf-8," + encodeURIComponent(generateStudyAsCsvColumnsWithStudyCustomFieldLabelsUsingStudyAtomEntryURL(studyAtomEntryURL))));
	    	
		}
		else {
			
			if (this.isChromeUserAgent()) {
				
				this.setDownloadStudyAsCsvColumnsWithStudyCustomFieldLabelsWindow(window.open("about:blank", "StudyAsCsvColumnsWindow", ""));
				
			} else {
				
				this.setDownloadStudyAsCsvColumnsWithStudyCustomFieldLabelsWindow(window.open("about:blank", "StudyAsCsvColumnsWindow", "menubar=yes, scrollbars=yes, resizable=yes"));
			}
	    	
	    	this.getDownloadStudyAsCsvColumnsWithStudyCustomFieldLabelsWindow().document.write("<pre>" + generateStudyAsCsvColumnsWithStudyCustomFieldLabelsUsingStudyAtomEntryURL(studyAtomEntryURL) + "</pre>");
	    	this.getDownloadStudyAsCsvColumnsWithStudyCustomFieldLabelsWindow().document.close();
	    	
		}
	    
	} else {
		
		//if focus supported, then focus
		if (this.getDownloadStudyAsCsvColumnsWithStudyCustomFieldLabelsWindow().focus) {
			this.getDownloadStudyAsCsvColumnsWithStudyCustomFieldLabelsWindow().focus();
		}
	}	    
	    
}
function downloadStudyAsCsvRowsWithCustomFieldLabelsUsingStudyAtomEntryURL (studyAtomEntryURL) {
	
	if (this.getDownloadStudyAsCsvRowsWithStudyCustomFieldLabelsWindow() == null || this.getDownloadStudyAsCsvRowsWithStudyCustomFieldLabelsWindow().closed) {
		
		if (this.isFirefoxUserAgent()) { 
	    	
	    	this.setDownloadStudyAsCsvRowsWithStudyCustomFieldLabelsWindow(window.open("data:text/csv;charset=utf-8," + encodeURIComponent(generateStudyAsCsvRowsWithStudyCustomFieldLabelsUsingStudyAtomEntryURL(studyAtomEntryURL))));
	    	
		}
		else {
			
			if (this.isChromeUserAgent()) {
				
				this.setDownloadStudyAsCsvRowsWithStudyCustomFieldLabelsWindow(window.open("about:blank", "StudyAsCsvRowsWindow", ""));
				
			} else {
				
				this.setDownloadStudyAsCsvRowsWithStudyCustomFieldLabelsWindow(window.open("about:blank", "StudyAsCsvRowsWindow", "menubar=yes, scrollbars=yes, resizable=yes"));
			}
	    	
	    	this.getDownloadStudyAsCsvRowsWithStudyCustomFieldLabelsWindow().document.write("<pre>" + generateStudyAsCsvRowsWithStudyCustomFieldLabelsUsingStudyAtomEntryURL(studyAtomEntryURL) + "</pre>");
	    	this.getDownloadStudyAsCsvRowsWithStudyCustomFieldLabelsWindow().document.close();
	    	
		}
	    
	} else {
		
		//if focus supported, then focus
		if (this.getDownloadStudyAsCsvRowsWithStudyCustomFieldLabelsWindow().focus) {
			this.getDownloadStudyAsCsvRowsWithStudyCustomFieldLabelsWindow().focus();
		}
	}
	
}