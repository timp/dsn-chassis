/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.create.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;

/**
 * @author raok
 *
 */
public interface CreateSubmissionWidgetPubSubAPI {

	public void onNewSubmissionSaveSuccess(SubmissionEntry submissionEntry);
	
	public void onUserActionCreateNewSubmissionCancelled();

	public void onNewSubmissionSaveError(Throwable error);
	
}
