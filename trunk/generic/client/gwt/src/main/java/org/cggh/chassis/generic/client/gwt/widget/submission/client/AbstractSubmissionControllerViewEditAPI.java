package org.cggh.chassis.generic.client.gwt.widget.submission.client;


import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;

public abstract interface AbstractSubmissionControllerViewEditAPI {

	public void loadSubmissionEntry(SubmissionEntry submissionEntryToLoad);

	public void loadSubmissionEntryByURL(String submissionEntryURL);

}