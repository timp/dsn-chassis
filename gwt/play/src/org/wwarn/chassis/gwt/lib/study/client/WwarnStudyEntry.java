/**
 * $Id$
 */
package org.wwarn.chassis.gwt.lib.study.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomEntry;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFeed;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFormatException;
import org.cggh.chassis.gwt.lib.study.client.format.StudyEntry;

import com.google.gwt.xml.client.Element;


/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class WwarnStudyEntry extends StudyEntry {



	public WwarnStudyEntry() throws AtomFormatException {
		super();
	}

	
	
	public WwarnStudyEntry(String entryDocXML) throws AtomFormatException {
		super(entryDocXML);
	}

	
	
	public WwarnStudyEntry(Element entryElement) throws AtomFormatException {
		super(entryElement);
	}

	
	
	public static WwarnStudyEntry as(AtomEntry entry) {
		if (entry instanceof WwarnStudyEntry) {
			return (WwarnStudyEntry) entry;
		}
		else {
			return null;
		}
	}

	
	
	
	
	
	public static final String TREATMENT = "treatment";
	public static final String DURATION = "duration";
	public static final String TREATMENTRESULT = "treatmentresult";



	public String getDuration() {
		return getStudyElementContent(DURATION);
	}
	
	
	
	public void setDuration(String duration) {
		setStudyElementContent(DURATION, duration);		
	}
	
	
	
	public List<String> getTreatments() {
		return getStudyElementsContents(TREATMENT);
	}
	
	
	
	public void setTreatments(List<String> treatments) {
		setStudyElementsContents(TREATMENT, treatments);
	}
	
	
	
	public List<WwarnTreatmentResult> getTreatmentResults() {
		
		// get list of treatment result elements from the study element
		List<Element> treatmentResultElements = getStudyElements(TREATMENTRESULT);

		// create empty list to store results
		List<WwarnTreatmentResult> results = new ArrayList<WwarnTreatmentResult>();

		// iterate over elements, creating results
		for (Element treatmentResultElement : treatmentResultElements) {
			results.add(new WwarnTreatmentResult(treatmentResultElement)); // delegate parsing of complex content in constructor
		}
		
		return results;
	}
	
	
	
	public void setTreatmentResults(List<WwarnTreatmentResult> results) {

		// remove any existing treatment result elements from the study element
		removeStudyElements(TREATMENTRESULT);
		
		// iterate over treatment results
		for (WwarnTreatmentResult result : results) {
			
			// create a new treatment result element and append to study element
			Element treatmentResultElement = createStudyElement(TREATMENTRESULT);
			
			// populate complex content
			result.populate(treatmentResultElement);
		}
	}
	
	
}
