package org.cggh.chassis.wwarn.ui.anonymizer.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.WidgetEvent;

import com.google.gwt.xml.client.Element;

public class ReviewFileNavigationEvent extends WidgetEvent {

	private static final Log log = LogFactory.getLog(ReviewFileNavigationEvent.class);	
	
	private Element fileToBeReviewedEntryElement;


	public void setFileToBeReviewedEntryElement(Element fileToBeReviewedEntryElement) {

		log.enter("setFileToBeReviewedEntryElement");		
		
		log.debug("Setting file to be reviewed....");
		
		this.fileToBeReviewedEntryElement = fileToBeReviewedEntryElement;
		
		log.leave();
	}
	
	public Element getFileToBeReviewedEntryElement() {

		log.enter("getFileToBeReviewedEntryElement");		
		
		log.debug("Getting file to be reviewed....");

		log.leave();
		
		return this.fileToBeReviewedEntryElement;

	}
	
}
