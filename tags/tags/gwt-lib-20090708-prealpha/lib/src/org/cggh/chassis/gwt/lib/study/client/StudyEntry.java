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

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class StudyEntry extends AtomEntry {

	
	
	protected String startYear = null, endYear = null;
	protected Integer sampleSize = null;
	protected List<String> countries = new ArrayList<String>();
	protected List<StudyLocation> locations = new ArrayList<StudyLocation>();
	
	
	
	public StudyEntry() {
		super();
	}

	
	
	public StudyEntry(String entryDocXML) {
		super(entryDocXML);
	}

	

	/**
	 * @param entryElement
	 */
	public StudyEntry(Element entryElement) {
		super(entryElement);
	}



	@Override
	protected void init(Element entryElement) {
		super.init(entryElement);
		
		Element studyElement = XML.getElementByTagNameNS(entryElement, ChassisNS.NS, ChassisNS.STUDY);

		// init startyear
		this.startYear = XML.getSimpleContentByTagNameNS(studyElement, ChassisNS.NS, ChassisNS.STARTYEAR);
		
		// init endyear
		this.endYear = XML.getSimpleContentByTagNameNS(studyElement, ChassisNS.NS, ChassisNS.ENDYEAR);

		// init samplesize
		String sampleSizeString = XML.getSimpleContentByTagNameNS(studyElement, ChassisNS.NS, ChassisNS.SAMPLESIZE);
		if (sampleSizeString != null) {
			this.sampleSize = Integer.parseInt(sampleSizeString);
		}
		
		// init countries
		this.countries = XML.getSimpleContentsByTagNameNS(studyElement, ChassisNS.NS, ChassisNS.COUNTRY);
		
		// init locations
		List<Element> locationElements = XML.getElementsByTagNameNS(studyElement, ChassisNS.NS, ChassisNS.LOCATION);
		for (Element locationElement : locationElements ) {
			locations.add(new StudyLocation(locationElement));
		}
		
		// init content
		initContent(studyElement);

	}

	
	
	protected void initContent(Element studyElement) {
		this.content = new StudyContent(studyElement);		
	}
	
	
	public List<String> getCountries() {
		return this.countries;
	}
	
	
	
	public void setCountries(List<String> countries) {
		this.countries = countries;
	}

	
	
	public List<StudyLocation> getLocations() {
		return this.locations;
	}
	
	
	
	public void setLocations(List<StudyLocation> locations) {
		this.locations = locations;		
	}

	
	
	public String getStartYear() {
		return this.startYear;
	}
	
	
	
	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}



	public String getEndYear() {
		return this.endYear;
	}
	
	
	
	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}
	
	

	public int getSampleSize() {
		return this.sampleSize;
	}
	
	
	
	public void setSampleSize(int sampleSize) {
		this.sampleSize = sampleSize;
	}


	protected static String getSimpleContent(Element studyElement, String tagName) {
		return XML.getSimpleContentByTagNameNS(studyElement, ChassisNS.NS, tagName);
	}
	
	
	
}


