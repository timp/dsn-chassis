/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.controller.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;

/**
 * @author raok
 *
 */
public interface SubmissionControllerPubSubViewAPI {

	public void onUserActionEditSubmission(SubmissionEntry submissionEntryToEdit);

}
