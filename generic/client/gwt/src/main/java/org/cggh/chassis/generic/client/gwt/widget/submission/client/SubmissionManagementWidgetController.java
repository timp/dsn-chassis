/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author raok
 *
 */
public class SubmissionManagementWidgetController {

	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	final private SubmissionManagementWidgetModel model;
	final private SubmissionManagementWidget owner;

	
	
	
	public SubmissionManagementWidgetController(SubmissionManagementWidget owner, SubmissionManagementWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}

	
	
	
	public void displayCreateSubmissionWidget() {
		displayCreateSubmissionWidget(false);
	}

	public void displayCreateSubmissionWidget(Boolean userConfirmed) {
		log.enter("displayCreateSubmissionWidget");
		log.debug("userConfirmed: " + userConfirmed);
		
//		model.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_CREATE_SUBMISSION, userConfirmed);
		model.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_CREATE_SUBMISSION, true);
		
		log.leave();
	}

	public void displayEditSubmissionWidget() {
		log.enter("displayEditSubmissionWidget");

		model.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_EDIT_SUBMISSION, true);
		
		log.leave();
	}

	public void displayViewSubmissionWidget() {
		log.enter("displayViewSubmissionWidget");

		model.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_VIEW_SUBMISSION, true);
		
		log.leave();
	}

	public void displayViewAllSubmissionsWidget() {
		displayViewAllSubmissionsWidget(false);
	}

	public void displayViewAllSubmissionsWidget(Boolean userConfirmed) {
		log.enter("displayViewAllSubmissionsWidget");
		log.debug("userConfirmed: " + userConfirmed);
		
//		model.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_VIEW_ALL_SUBMISSIONS, userConfirmed);
		model.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_VIEW_ALL_SUBMISSIONS, true);
		
		log.leave();		
	}

	public void displaySubmissionDataFileUploadWidget() {
		log.enter("displaySubmissionDataFileUploadWidget");
		
		model.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_SUBMISSION_DATA_FILE_UPLOAD_WIDGET, true);
		
		log.leave();
	}

	public void reset() {
		log.enter("reset");

		model.reset();
		
		log.leave();
	}

	
	
	
	
}
