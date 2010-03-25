package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.WidgetEvent;

import com.google.gwt.xml.client.Element;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 */
public class ViewStudyQuestionnaireNavigationEvent extends WidgetEvent {

	private static final Log log = LogFactory.getLog(ViewStudyQuestionnaireNavigationEvent.class);	

	private Element study;
	
	public void setStudy(Element study) {
		log.enter("setStudy");		
		
		this.study = study;
		
		log.leave();
	}
	
	public Element getStudy() {
		log.enter("getStudy");		
		
		log.debug("Getting study " + this.study);

		log.leave();
		
		return this.study;
	}

}
