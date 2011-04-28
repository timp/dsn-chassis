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
	
	//TODO: get study info and Add it on
	
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
	
	//TODO: get study info and Add it on
	
	return studyInfoAsAtomEntryXML;
}

function retrieveStudyCustomFieldLabelsRegExMapAsAssociativeArray () {

	var studyCustomFieldLabelsMapAsAssociativeArray = null;

	//TODO:replace with version retrieved from service
	
	//NOTE: Patterns must be in the form "atom:entry\\[(\\d+)\\]/manta:id\\[(\\d+)\\]"
	studyCustomFieldLabelsMapAsAssociativeArray = {
			
			"atom:entry\\[(\\d+)\\]/atom:id\\[(\\d+)\\]" : "AtomID",
			"atom:entry\\[(\\d+)\\]/atom:published\\[(\\d+)\\]" : "CompletionDate",
			"atom:entry\\[(\\d+)\\]/atom:updated\\[(\\d+)\\]" : "DateLastUpdated",
			"atom:entry\\[(\\d+)\\]/atom:author\\[(\\d+)\\]/atom:email\\[(\\d+)\\]" : "AuthorEmail",
			"atom:entry\\[(\\d+)\\]/atom:title\\[(\\d+)\\]" : "Title",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/registrant-has-agreed-to-the-terms\\[(\\d+)\\]" : "TermsOfSubmission",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/study-is-published\\[(\\d+)\\]" : "StudyPublished",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/explorer-display\\[(\\d+)\\]" : "ExplorerDisplay",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/publications\\[(\\d+)\\]/publication\\[(\\d+)\\]/publication-title\\[(\\d+)\\]" : "Publication$5Title",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/publications\\[(\\d+)\\]/publication\\[(\\d+)\\]/pmid\\[(\\d+)\\]" : "Publication$5PubMedID",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/publications\\[(\\d+)\\]/publication\\[(\\d+)\\]/publication-references\\[(\\d+)\\]/publication-reference\\[(\\d+)\\]" : "Publication$5URL$7",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/acknowledgements\\[(\\d+)\\]/institution-ack\\[(\\d+)\\]/institution-name\\[(\\d+)\\]" : "ackInstitute$5",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/acknowledgements\\[(\\d+)\\]/institution-ack\\[(\\d+)\\]/institution-websites\\[(\\d+)\\]/institution-url\\[(\\d+)\\]" : "ackInstitute$5URL",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/acknowledgements\\[(\\d+)\\]/person\\[(\\d+)\\]/first-name\\[(\\d+)\\]" : "ackAuthor$5Name",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/acknowledgements\\[(\\d+)\\]/person\\[(\\d+)\\]/family-name\\[(\\d+)\\]" : "ackAuthor$5Surname",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/study-status\\[(\\d+)\\]" : "StudyStatus",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study\\[(\\d+)\\]/modules\\[(\\d+)\\]" : "WWARNmodules",
			"atom:entry\\[(\\d+)\\]/manta:id\\[(\\d+)\\]" : "StudyCode"
			
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
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/region\\[(\\d+)\\]" : "Site$5Region",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/district\\[(\\d+)\\]" : "Site$5District",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/locality\\[(\\d+)\\]" : "Site$5Locality",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/lookupAddress\\[(\\d+)\\]" : "Site$5Address",
			"atom:entry\\[(\\d+)\\]/atom:content\\[(\\d+)\\]/study-info\\[(\\d+)\\]/sites\\[(\\d+)\\]/site\\[(\\d+)\\]/siteCode\\[(\\d+)\\]" : "Site$5Code"
			
	};
	
	return studyInfoCustomFieldLabelsMapAsAssociativeArray;	
}

////Functions

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

function convertXmlNodesIntoAssociativeArrayWithXPathFieldLabels (XmlNodes, parentNodeBaseXPath, nodeNamesToIgnoreAsAssociativeArray) {

	var associativeArrayWithXPathFieldLabels = null;
	
	associativeArrayWithXPathFieldLabels = {};
	
	if (parentNodeBaseXPath == null) {
		parentNodeBaseXPath = "";
	}
	

	  if (XmlNodes.length == 1 && XmlNodes[0].nodeType == 3) {		  
	  
		// node is a leaf
		
			// Ignore empty nodes
			if (trim(XmlNodes[0].nodeValue) != "") { 

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

function convertAssociativeArrayIntoAssociativeArrayUsingRegExpKeyMapAsAssociativeArray (originalAssociativeArray, regExpMapAsAssociativeArray) {

	var associativeArrayWithMappedKeys = null;
	
	//TODO

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

function convertAssociativeArrayIntoSortedKeysArray (associativeArray) {
	
	var sortedKeysArray = new Array();
	
	for (var key in associativeArray) {
		
		sortedKeysArray.push(key);
	}
	
	return sortedKeysArray.sort();
	
}

function convertStudyAsAtomEntryXmlIntoStudyInfoAtomEntryURL (studyAsAtomEntryXml) {
	
	var studyInfoAtomEntryURL = null;

	if (this.isChromeUserAgent()) {
		studyInfoAtomEntryURL = $(studyAsAtomEntryXml).find("[nodeName=atom:link][rel='http://www.cggh.org/2010/chassis/terms/studyInfo']").attr("href");
	} else {
		studyInfoAtomEntryURL = $(studyAsAtomEntryXml).find("atom\\:link[rel='http://www.cggh.org/2010/chassis/terms/studyInfo']").attr("href");
	}
	
	return studyInfoAtomEntryURL;
}

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

////Scripts
function generateStudyAsCsvColumnsWithStudyCustomFieldLabelsUsingStudyAtomEntryURL (studyAtomEntryURL) {
	
	//NOTE: This also fetches and combines the study-info data.
	
	//Broken down into variables to make the process easier to follow.
	
	var studyAsAtomEntryXml = retrieveStudyAsAtomEntryXmlUsingStudyAtomEntryURL(studyAtomEntryURL);
	
	var studyInfoAtomEntryURL = convertStudyAsAtomEntryXmlIntoStudyInfoAtomEntryURL(studyAsAtomEntryXml);
	
	var studyInfoAsAtomEntryXml = retrieveStudyInfoAsAtomEntryXmlUsingStudyInfoAtomEntryURL(studyInfoAtomEntryURL);
	
	var studyAsAssociativeArrayWithXPathFieldLabels = convertStudyAsAtomEntryXmlIntoStudyAsAssociativeArrayWithXPathFieldLabels(studyAsAtomEntryXml);
	
	var studyInfoAsAssociativeArrayWithXPathFieldLabels = convertStudyInfoAsAtomEntryXmlIntoStudyInfoAsAssociativeArrayWithXPathFieldLabels(studyInfoAsAtomEntryXml);
	
	var studyAsAssociativeArrayWithStudyCustomFieldLabels = convertAssociativeArrayIntoAssociativeArrayUsingRegExpKeyMapAsAssociativeArray(studyAsAssociativeArrayWithXPathFieldLabels, retrieveStudyCustomFieldLabelsRegExMapAsAssociativeArray());

	var studyInfoAsAssociativeArrayWithStudyCustomFieldLabels = convertAssociativeArrayIntoAssociativeArrayUsingRegExpKeyMapAsAssociativeArray(studyInfoAsAssociativeArrayWithXPathFieldLabels, retrieveStudyInfoCustomFieldLabelsRegExMapAsAssociativeArray());
	
	$.extend(studyAsAssociativeArrayWithStudyCustomFieldLabels, studyInfoAsAssociativeArrayWithStudyCustomFieldLabels);
	
	return convertStudyAsAssociativeArrayIntoStudyAsCsvColumns(studyAsAssociativeArrayWithStudyCustomFieldLabels);
	
}
function generateStudyAsCsvRowsWithStudyCustomFieldLabelsUsingStudyAtomEntryURL (studyAtomEntryURL) {
	
	//NOTE: This also fetches and combines the study-info data.
	
	//Broken down into variables to make the process easier to follow.
	
	var studyAsAtomEntryXml = retrieveStudyAsAtomEntryXmlUsingStudyAtomEntryURL(studyAtomEntryURL);
	
	var studyInfoAtomEntryURL = convertStudyAsAtomEntryXmlIntoStudyInfoAtomEntryURL(studyAsAtomEntryXml);
	
	var studyInfoAsAtomEntryXml = retrieveStudyInfoAsAtomEntryXmlUsingStudyInfoAtomEntryURL(studyInfoAtomEntryURL);
	
	var studyAsAssociativeArrayWithXPathFieldLabels = convertStudyAsAtomEntryXmlIntoStudyAsAssociativeArrayWithXPathFieldLabels(studyAsAtomEntryXml);
	
	var studyInfoAsAssociativeArrayWithXPathFieldLabels = convertStudyInfoAsAtomEntryXmlIntoStudyInfoAsAssociativeArrayWithXPathFieldLabels(studyInfoAsAtomEntryXml);
	
	var studyAsAssociativeArrayWithStudyCustomFieldLabels = convertAssociativeArrayIntoAssociativeArrayUsingRegExpKeyMapAsAssociativeArray(studyAsAssociativeArrayWithXPathFieldLabels, retrieveStudyCustomFieldLabelsRegExMapAsAssociativeArray());

	var studyInfoAsAssociativeArrayWithStudyCustomFieldLabels = convertAssociativeArrayIntoAssociativeArrayUsingRegExpKeyMapAsAssociativeArray(studyInfoAsAssociativeArrayWithXPathFieldLabels, retrieveStudyInfoCustomFieldLabelsRegExMapAsAssociativeArray());
	
	$.extend(studyAsAssociativeArrayWithStudyCustomFieldLabels, studyInfoAsAssociativeArrayWithStudyCustomFieldLabels);
	
	return convertStudyAsAssociativeArrayIntoStudyAsCsvRows(studyAsAssociativeArrayWithStudyCustomFieldLabels);
	
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