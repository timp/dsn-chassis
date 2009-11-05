/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;

/**
 * @author raok
 *
 */
public interface SubmissionControllerPubSubEditAPI extends AbstractSubmissionControllerPubSubAPI {
	
	public void fireOnSubmissionEntryUpdated(SubmissionEntry updatedSubmissionEntry);

	public void fireOnUserActionEditSubmissionEntryCancelled();
	
}
