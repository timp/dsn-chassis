/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author raok
 *
 */
public class SubmissionManagementWidgetController {

	final private SubmissionManagementWidgetModel model;
	private Log log = LogFactory.getLog(this.getClass());

	public SubmissionManagementWidgetController(SubmissionManagementWidgetModel model) {
		this.model = model;
	}

	public void displayCreateSubmissionWidget() {
		log.enter("displayCreateSubmissionWidget");
		
		model.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_CREATE_STUDY);
		
		log.leave();
	}

	public void displayEditSubmissionWidget() {
		log.enter("displayEditSubmissionWidget");

		model.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_EDIT_STUDY);
		
		log.leave();
	}

	public void displayViewSubmissionWidget() {
		log.enter("displayViewSubmissionWidget");

		model.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_VIEW_STUDY);
		
		log.leave();
	}

	public void displayViewAllSubmissionsWidget() {
		log.enter("displayViewAllSubmissionsWidget");
		
		model.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES);
		
		log.leave();		
	}

	
	
	
	
}
