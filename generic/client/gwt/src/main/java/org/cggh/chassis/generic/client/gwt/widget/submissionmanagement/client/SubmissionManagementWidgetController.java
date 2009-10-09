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
	private Log log = LogFactory.getLog(this.getClass());

	final private SubmissionManagementWidgetModel model;

	public SubmissionManagementWidgetController(SubmissionManagementWidgetModel model) {
		this.model = model;
	}

	public void displayCreateSubmissionWidget() {
		displayCreateSubmissionWidget(false);
	}

	public void displayCreateSubmissionWidget(Boolean userConfirmed) {
		log.enter("displayCreateSubmissionWidget");
		log.trace("userConfirmed: " + userConfirmed);
		
		model.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_CREATE_SUBMISSION, userConfirmed);
				
		log.leave();
	}

	public void displayEditSubmissionWidget() {
		log.enter("displayEditSubmissionWidget");

		model.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_EDIT_SUBMISSION);
		
		log.leave();
	}

	public void displayViewSubmissionWidget() {
		log.enter("displayViewSubmissionWidget");

		model.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_VIEW_SUBMISSION);
		
		log.leave();
	}

	public void displayViewAllSubmissionsWidget() {
		displayViewAllSubmissionsWidget(false);
	}

	public void displayViewAllSubmissionsWidget(Boolean userConfirmed) {
		log.enter("displayViewAllSubmissionsWidget");
		log.trace("userConfirmed: " + userConfirmed);
		
		model.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_VIEW_ALL_SUBMISSIONS, userConfirmed);
					
		log.leave();		
	}

	public void reset() {
		log.enter("reset");

		model.reset();
		
		log.leave();
	}

	
	
	
	
}
