package org.cggh.chassis.wwarn.ui.curator.client;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import org.cggh.chassis.generic.widget.client.WidgetEvent;

import com.google.gwt.xml.client.Element;


/**
 * Generated event ViewStudyQuestionnaireNavigation from StudyRevisionActions to 
 * ViewStudyQuestionnaire.
 *
 * Generated, delete file to regenerate.
 *
 *
 */
public class ViewStudyQuestionnaireNavigationEvent extends WidgetEvent {

	private static final Log log = LogFactory.getLog(ViewStudyQuestionnaireNavigationEvent.class);	

	private String studyUrl;


	public void setStudyUrl(String studyUrl) {
		log.enter("setStudyUrl");		
		
		this.studyUrl = studyUrl;
		
		log.leave();
	}
	
	public String getStudyUrl() {
		log.enter("getStudyUrl");		
		
		log.debug("Returning studyUrl " + this.studyUrl);

		log.leave();
		
		return this.studyUrl;
	}

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
