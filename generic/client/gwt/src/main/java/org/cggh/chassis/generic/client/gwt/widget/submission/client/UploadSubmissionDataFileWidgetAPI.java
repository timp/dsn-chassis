/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;


/**
 * @author raok
 *
 */
public interface UploadSubmissionDataFileWidgetAPI {

	public void setUpNewSubmissionDataFile(String submissionLink);
	
	public void addSubmissionDataFileWidget(UploadSubmissionDataFileWidgetPubSubAPI listener);
	
}
