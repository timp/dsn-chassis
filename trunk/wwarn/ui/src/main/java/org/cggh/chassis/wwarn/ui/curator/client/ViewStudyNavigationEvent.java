package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.WidgetEvent;

import com.google.gwt.xml.client.Element;

public class ViewStudyNavigationEvent extends WidgetEvent {

	private static final Log log = LogFactory.getLog(ViewStudyNavigationEvent.class);	
	
	// TODO: depreciate the study element in favour of the studyUrl
	private Element study;
	private String studyUrl;

	public void setStudy(Element study) {
		log.enter("setStudy");		
		
		this.study = study;
		
		log.leave();
	}
	
	public Element getStudy() {
		log.enter("getStudy");		
		
		log.debug("Returning study " + this.study);

		log.leave();
		
		return this.study;
	}




	public void setStudyUrl(String studyUrl) {

		log.enter("setStudyUrl");		
		
		this.studyUrl = studyUrl;
		
		log.leave();
	}
	
	public String getStudyUrl() {

		log.enter("getStudyUrl");		
		
		log.debug("Getting study url " + this.studyUrl);

		log.leave();
		
		return this.studyUrl;

	}	
	
}
