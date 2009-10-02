/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.edit.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;

/**
 * @author raok
 *
 */
public interface EditSubmissionWidgetPubSubAPI {

	void onSubmissionUpdateSuccess(SubmissionEntry updatedSubmissionEntry);

	void onUserActionUpdateSubmissionCancelled();

}
