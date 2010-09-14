package org.cggh.chassis.wwarn.ui.anonymizer.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.WidgetEvent;

import com.google.gwt.xml.client.Element;

public class CleanFileNavigationEvent extends WidgetEvent {

	private static final Log log = LogFactory.getLog(CleanFileNavigationEvent.class);	
	
	private Element fileToBeCleanedEntryElement;


	public void setFileToBeCleanedEntryElement(Element fileToBeCleanedEntryElement) {

		log.enter("setFileToBeCleanedEntryElement");		
		
		log.debug("Setting file to be cleaned....");
		
		this.fileToBeCleanedEntryElement = fileToBeCleanedEntryElement;
		
		log.leave();
	}
	
	public Element getFileToBeCleanedEntryElement() {

		log.enter("getFileToBeCleanedEntryElement");		
		
		log.debug("Getting file to be cleaned....");

		log.leave();
		
		return this.fileToBeCleanedEntryElement;

	}
	
}
