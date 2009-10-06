package org.cggh.chassis.generic.client.gwt.widget.submission.controller.client;

public interface SubmissionControllerCreateAPI extends AbstractSubmissionControllerCreateEditAPI {

	public void setUpNewSubmission(String authorEmail);
	
	public void saveNewSubmissionEntry();

}