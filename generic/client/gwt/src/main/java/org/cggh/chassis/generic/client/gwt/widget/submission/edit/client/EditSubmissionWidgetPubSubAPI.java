/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.edit.client;

import org.cggh.chassis.generic.atom.rewrite.client.submission.SubmissionEntry;


/**
 * @author raok
 *
 */
public interface EditSubmissionWidgetPubSubAPI {

	public void onUserActionCreateSubmissionCancelled();

	public void onCreateSubmissionSuccess(SubmissionEntry submissionEntry);
	
	public void onCreateSubmissionError(Throwable error);

}
