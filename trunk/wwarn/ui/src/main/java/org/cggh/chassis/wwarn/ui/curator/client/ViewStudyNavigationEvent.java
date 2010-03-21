package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.WidgetEvent;

import com.google.gwt.xml.client.Element;

public class ViewStudyNavigationEvent extends WidgetEvent {

	private static final Log log = LogFactory.getLog(ViewStudyNavigationEvent.class);	
	
	// TODO Decide which mechanism to use
	private Element study;
	private String studyId;


	public void setStudy(Element study) {

		log.enter("setStudy");		
		
		log.debug("Setting study " + study);
		
		this.study = study;
		
		log.leave();
	}
	
	public Element getStudy() {

		log.enter("getStudy");		
		
		log.debug("Getting study " + this.study);

		log.leave();
		
		return this.study;

	}

	public void setStudyId(String id) {

		log.enter("setStudyId");		
		
		log.debug("Setting study id " + id);
		
		this.studyId = id;
		
		log.leave();
	}
	
	public String getStudyId() {

		log.enter("getStudyId");		
		
		log.debug("Getting study id " + this.studyId);

		log.leave();
		
		return this.studyId;

	}

	
}
