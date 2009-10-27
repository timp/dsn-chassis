/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client;

/**
 * @author raok
 *
 */
public interface SubmissionDataFileWidgetPubSubAPI {
	
	public void onSubmissionDataFileUploaded(String submissionLink);
	
	public void onSubmissionDataFileCancelled();
	
}
