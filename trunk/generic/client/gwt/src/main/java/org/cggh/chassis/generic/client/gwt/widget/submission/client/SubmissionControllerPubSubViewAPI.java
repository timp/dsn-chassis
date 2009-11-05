/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;

/**
 * @author raok
 *
 */
public interface SubmissionControllerPubSubViewAPI extends AbstractSubmissionControllerPubSubAPI {

	public void fireOnUserActionEditSubmission(SubmissionEntry submissionEntryToEdit);

	public void onUserActionUploadDataFile(String submissionLink);

}
