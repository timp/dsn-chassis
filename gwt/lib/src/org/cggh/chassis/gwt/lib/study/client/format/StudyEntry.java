/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.study.client.format;

import java.util.List;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomEntry;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFormatException;
import org.cggh.chassis.gwt.lib.common.client.ChassisNS;
import org.cggh.chassis.gwt.lib.xml.client.XML;
import com.google.gwt.xml.client.Element;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class StudyEntry extends AtomEntry {

	
	
	protected Element studyElement = null;
	
	
	public static final String template = 
		"<entry xmlns=\"http://www.w3.org/2005/Atom\">\n" +
		"  <content type=\"application/xml\">\n" +
		"    <study xmlns=\"http://www.cggh.org/chassis/atom/xmlns\"></study>\n" +
		"  </content>\n" +
		"</entry>";

	
	
	public StudyEntry() throws AtomFormatException {
		super(template);
		this.initStudyElement();
	}

	
	
	public StudyEntry(String entryDocXML) throws AtomFormatException {
		super(entryDocXML);
		this.initStudyElement();
	}

	

	/**
	 * @param entryElement
	 * @throws AtomFormatException 
	 */
	public StudyEntry(Element entryElement) throws AtomFormatException {
		super(entryElement);
		this.initStudyElement();
	}



	protected void initStudyElement() throws AtomFormatException {
		this.studyElement = XML.getElementByTagNameNS(this.entryElement, ChassisNS.NS, ChassisNS.STUDY);
		if (this.studyElement == null) {
			throw new StudyFormatException("study element is null");
		}
		if (!this.studyElement.getTagName().equals(ChassisNS.STUDY)) {
			throw new StudyFormatException("study element has unexpected tag name: "+this.studyElement.getTagName());
		}
		if (this.studyElement.getNamespaceURI() == null) {
			throw new StudyFormatException("study element namespace URI is null");
		}
		if (!this.studyElement.getNamespaceURI().equals(ChassisNS.NS)) {
			throw new StudyFormatException("study element has unexpected namespace URI: "+this.studyElement.getNamespaceURI());
		}
	}
	
	

	public List<String> getCountries() {
		return this.getStudyElementsContents(ChassisNS.COUNTRY);
	}
	
	
	
	public void setCountries(List<String> countries) {
		this.setStudyElementsContents(ChassisNS.COUNTRY, countries);
	}

	
	
	public List<StudyLocation> getLocations() {
		return StudyLocation.getLocations(this.studyElement);
	}
	
	
	
	public void setLocations(List<StudyLocation> locations) {
		StudyLocation.setLocations(this.studyElement, locations);
	}

	
	
	public String getStartYear() {
		return getStudyElementContent(ChassisNS.STARTYEAR);
	}
	
	
	
	public void setStartYear(String startYear) {
		setStudyElementContent(ChassisNS.STARTYEAR, startYear);
	}



	public String getEndYear() {
		return getStudyElementContent(ChassisNS.ENDYEAR);
	}
	
	
	
	public void setEndYear(String endYear) {
		setStudyElementContent(ChassisNS.ENDYEAR, endYear);
	}
	
	

	public Integer getSampleSize() throws StudyFormatException {
		String sampleSizeString = getStudyElementContent(ChassisNS.SAMPLESIZE);
		if (sampleSizeString != null) {
			try {
				return Integer.parseInt(sampleSizeString);
			} catch (Throwable ex) {
				throw new StudyFormatException("error parsing sample size as integer", ex);
			}
		}
		return null;
	}
	
	
	
	public void setSampleSize(int sampleSize) {
		setStudyElementContent(ChassisNS.SAMPLESIZE, Integer.toString(sampleSize));
	}


	protected String getStudyElementContent(String tagName) {
		return XML.getElementSimpleContentByTagName(this.studyElement, tagName);
	}
	
	
	
	protected void setStudyElementContent(String tagName, String content) {
		XML.setElementSimpleContentByTagName(this.studyElement, tagName, content);
	}
	
	
	
	protected List<String> getStudyElementsContents(String tagName) {
		return XML.getElementsSimpleContentsByTagName(this.studyElement, tagName);
	}
	
	
	
	protected void setStudyElementsContents(String tagName, List<String> contents) {
		XML.setElementsSimpleContentsByTagName(this.studyElement, tagName, contents);
	}
	
	
	
	protected List<Element> getStudyElements(String tagName) {
		return XML.getElementsByTagName(this.studyElement, tagName);
	}
	
	
	
	protected void removeStudyElements(String tagName) {
		XML.removeElementsByTagName(this.studyElement, tagName);
	}
	
	
	
	protected Element createStudyElement(String tagName) {
		return XML.createElement(this.studyElement, tagName);
	}



	/**
	 * TODO document me
	 * 
	 * @param entry
	 * @return
	 */
	public static StudyEntry as(AtomEntry entry) {
		if (entry instanceof StudyEntry) {
			return (StudyEntry) entry;
		}
		else {
			return null;
		}
	}



}


