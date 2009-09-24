/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.controller.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;

/**
 * @author raok
 *
 */
public interface SubmissionControllerPubSubCreateAPI extends AbstractSubmissionControllerPubSubAPI {

	public void newSubmissionSaved(SubmissionEntry submissionEntry);
	
	public void cancelCreateNewSubmissionEntry();
	
}
