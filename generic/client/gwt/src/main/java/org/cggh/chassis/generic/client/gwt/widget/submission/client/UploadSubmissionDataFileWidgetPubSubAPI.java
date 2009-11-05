/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

/**
 * @author raok
 *
 */
public interface UploadSubmissionDataFileWidgetPubSubAPI {
	
	public void onSubmissionDataFileUploaded(String submissionLink);
	
	public void onSubmissionDataFileCancelled();
	
}
