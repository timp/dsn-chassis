/**
 * $Id$
 */
package org.wwarn.chassis.gwt.lib.study.client;

import java.util.ArrayList;
import java.util.List;

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




	private static final String TREATMENT = "treatment";
	private static final String DURATION = "duration";
	private static final String TREATMENTRESULT = "treatmentresult";



	public WwarnStudyEntry() throws AtomFormatException {
		super();
	}

	
	
	public WwarnStudyEntry(String entryDocXML) throws AtomFormatException {
		super(entryDocXML);
	}

	
	
	
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
	
	
	
	public List<TreatmentResult> getTreatmentResults() {
		
		// get list of treatment result elements from the study element
		List<Element> treatmentResultElements = getStudyElements(TREATMENTRESULT);

		// create empty list to store results
		List<TreatmentResult> results = new ArrayList<TreatmentResult>();

		// iterate over elements, creating results
		for (Element treatmentResultElement : treatmentResultElements) {
			results.add(new TreatmentResult(treatmentResultElement)); // delegate parsing of complex content in constructor
		}
		
		return results;
	}
	
	
	
	public void setTreatmentResults(List<TreatmentResult> results) {

		// remove any existing treatment result elements from the study element
		removeStudyElements(TREATMENTRESULT);
		
		// iterate over treatment results
		for (TreatmentResult result : results) {
			
			// create a new treatment result element and append to study element
			Element treatmentResultElement = createStudyElement(TREATMENTRESULT);
			
			// populate complex content
			result.populate(treatmentResultElement);
		}
	}
	
	
}
