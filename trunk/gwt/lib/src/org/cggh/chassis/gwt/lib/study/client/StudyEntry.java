/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.study.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.gwt.lib.atom.client.AtomEntry;
import org.cggh.chassis.gwt.lib.common.client.ChassisNS;
import org.cggh.chassis.gwt.lib.xml.client.XML;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class StudyEntry extends AtomEntry {

	
	
	protected Element studyElement = null;
	
	
	
	protected static final String template = 
		"<entry xmlns=\"http://www.w3.org/2005/AtomNS\">\n" +
		"  <title></title>\n" +
		"  <content type=\"application/xml\">\n" +
		"    <study xmlns=\"http://www.cggh.org/chassis/atom/xmlns\"></study>\n" +
		"  </content>\n" +
		"</entry>";

	
	
	
	public StudyEntry() {
		super();
	}

	
	
	public StudyEntry(String entryDocXML) {
		super(entryDocXML);
	}

	

	@Override
	protected void init(Document doc) {
		super.init(doc);
		studyElement = XML.getElementByTagNameNS(entryElement, ChassisNS.NS, ChassisNS.STUDY);
	}
	
	

	public List<String> getCountries() {
		return XML.getSimpleContentsByTagNameNS(studyElement, ChassisNS.NS, ChassisNS.COUNTRY);
	}
	
	
	
	public void setCountries(List<String> countries) {

		// remove all existing country elements
		XML.removeElementsByTagNameNS(studyElement, ChassisNS.NS, ChassisNS.COUNTRY);

		// add new country elements
		for (String country : countries) {
			Element countryElement = createStudyElement(ChassisNS.COUNTRY);
			XML.setSimpleContent(countryElement, country);
		}
		
	}

	
	
	public List<StudyLocation> getLocations() {
		List<Element> locationElements = getStudyElements(ChassisNS.LOCATION);
		List<StudyLocation> locations = new ArrayList<StudyLocation>();
		for (Element locationElement : locationElements) {
			locations.add(new StudyLocation(locationElement));
		}
		return locations;
	}
	
	
	
	public void setLocations(List<StudyLocation> locations) {

		// remove all existing location elements
		XML.removeElementsByTagNameNS(studyElement, ChassisNS.NS, ChassisNS.LOCATION);

		// add new location elements
		for (StudyLocation location : locations) {
			Element locationElement = createStudyElement(ChassisNS.LOCATION);
			location.populate(locationElement);
		}
		
	}

	
	
	public String getStartYear() {
		return getSimpleContent(ChassisNS.STARTYEAR);
	}
	
	
	
	public void setStartYear(String startYear) {
		setSimpleContent(ChassisNS.STARTYEAR, startYear);
	}



	public String getEndYear() {
		return getSimpleContent(ChassisNS.ENDYEAR);
	}
	
	
	
	public void setEndYear(String endYear) {
		setSimpleContent(ChassisNS.ENDYEAR, endYear);
	}
	
	

	public int getSampleSize() {
		return Integer.parseInt(getSimpleContent(ChassisNS.SAMPLESIZE));
	}
	
	
	
	public void setSampleSize(int sampleSize) {
		setSimpleContent(ChassisNS.SAMPLESIZE, Integer.toString(sampleSize));
	}


	protected String getSimpleContent(String tagName) {
		return XML.getSimpleContentByTagNameNS(studyElement, ChassisNS.NS, tagName);
	}
	
	
	
	protected void setSimpleContent(String tagName, String content) {
		
		// try to find element as child of study element
		Element element = XML.getElementByTagNameNS(studyElement, ChassisNS.NS, tagName);

		// if no element exists, create one as child of study element
		if (element == null) {
			element = doc.createElement(tagName);
			studyElement.appendChild(element); 
		}
		
		// set string content of treatment element
		XML.setSimpleContent(element, content);

	}
	
	
	
	protected List<String> getSimpleContents(String tagName) {
		return XML.getSimpleContentsByTagNameNS(studyElement, ChassisNS.NS, tagName);
	}
	
	
	
	protected void setSimpleContents(String tagName, List<String> contents) {
		
		// remove any existing elements
		XML.removeElementsByTagNameNS(studyElement, ChassisNS.NS, tagName);
		
		// create new elements and populate with content
		for (String content : contents) {
			Element element = doc.createElement(tagName);
			studyElement.appendChild(element);
			XML.setSimpleContent(element, content);
		}

	}
	
	
	
	protected List<Element> getStudyElements(String tagName) {
		return XML.getElementsByTagNameNS(studyElement, ChassisNS.NS, tagName);
	}
	
	
	
	protected void removeStudyElements(String tagName) {
		XML.removeElementsByTagNameNS(studyElement, ChassisNS.NS, tagName);
	}
	
	
	
	protected Element createStudyElement(String tagName) {
		Element child = doc.createElement(tagName);
		studyElement.appendChild(child);
		return child;
	}
	
	

}


