/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.create.client;

/**
 * @author raok
 *
 */
public interface CreateSubmissionWidgetAPI {

	public void setUpNewSubmission(String feedURL);
	
	public void addCreateSubmissionWidgetListener(CreateSubmissionWidgetPubSubAPI listener);
	
}
