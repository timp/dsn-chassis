package org.cggh.chassis.generic.client.gwt.widget.submission.client;

public interface SubmissionControllerCreateAPI extends AbstractSubmissionControllerCreateEditAPI {

	public void setUpNewSubmission(String authorEmail);
	
	public void saveNewSubmissionEntry();

}