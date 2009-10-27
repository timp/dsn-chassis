/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client;


/**
 * @author raok
 *
 */
public interface SubmissionDataFileWidgetAPI {

	public void setUpNewSubmissionDataFile(String submissionLink);
	
	public void addSubmissionDataFileWidget(SubmissionDataFileWidgetPubSubAPI listener);
	
}
