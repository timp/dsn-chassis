package org.cggh.chassis.generic.client.gwt.widget.submission.controller.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;

public interface SubmissionControllerViewAPI {

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#loadSubmissionEntry(org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry)
	 */
	public void loadSubmissionEntry(SubmissionEntry submissionEntryToLoad);

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#loadSubmissionEntryByURL(java.lang.String)
	 */
	public void loadSubmissionEntryByURL(String submissionEntryURL);

}