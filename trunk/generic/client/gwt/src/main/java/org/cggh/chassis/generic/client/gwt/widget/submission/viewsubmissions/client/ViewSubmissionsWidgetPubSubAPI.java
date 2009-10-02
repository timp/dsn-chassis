/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;

/**
 * @author raok
 *
 */
public interface ViewSubmissionsWidgetPubSubAPI {

	void onUserActionSelectSubmission(SubmissionEntry submissionEntry);

}
