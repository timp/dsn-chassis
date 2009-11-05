/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;


/**
 * @author raok
 *
 */
public interface EditSubmissionWidgetPubSubAPI {

	public void onUserActionCreateSubmissionCancelled();

	public void onEditSubmissionSuccess(SubmissionEntry submissionEntry);
	
	public void onEditSubmissionError(Throwable error);

}
