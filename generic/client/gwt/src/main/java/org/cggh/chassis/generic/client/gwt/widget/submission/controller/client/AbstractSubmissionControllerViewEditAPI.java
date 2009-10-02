package org.cggh.chassis.generic.client.gwt.widget.submission.controller.client;


import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;

public abstract interface AbstractSubmissionControllerViewEditAPI {

	public void loadSubmissionEntry(SubmissionEntry submissionEntryToLoad);

	public void loadSubmissionEntryByURL(String submissionEntryURL);

}