package org.cggh.chassis.wwarn.ui.curator.client;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import org.cggh.chassis.generic.widget.client.WidgetEvent;

import com.google.gwt.xml.client.Element;


/**
 * Generated event ProceedFromStep2 from SelectDerivationFiles to 
 * CurationSummary.
 *
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 */
public class ProceedFromStep2Event extends WidgetEvent {

	private static final Log log = LogFactory.getLog(ProceedFromStep2Event.class);	

	private Element studyEntry;


	public void setStudyEntry(Element studyEntry) {
		log.enter("setStudyEntry");		
		
		this.studyEntry = studyEntry;
		
		log.leave();
	}
	
	public Element getStudyEntry() {
		log.enter("getStudyEntry");		
		
		log.debug("Returning studyEntry " + this.studyEntry);

		log.leave();
		
		return this.studyEntry;
	}


}
