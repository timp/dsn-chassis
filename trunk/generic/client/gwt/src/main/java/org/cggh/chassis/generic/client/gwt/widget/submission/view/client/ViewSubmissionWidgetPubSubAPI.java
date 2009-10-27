/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;

/**
 * @author raok
 *
 */
public interface ViewSubmissionWidgetPubSubAPI {

	void onUserActionEditSubmission(SubmissionEntry submissionEntryToEdit);

	void onUserActionUploadDataFile(String submissionLink);

}
