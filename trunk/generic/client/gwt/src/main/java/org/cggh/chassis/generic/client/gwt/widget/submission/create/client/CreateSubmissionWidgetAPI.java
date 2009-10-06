/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.create.client;

/**
 * @author raok
 *
 */
public interface CreateSubmissionWidgetAPI {

	public void setUpNewSubmission(String authorEmail);
	
	public void addCreateSubmissionWidgetListener(CreateSubmissionWidgetPubSubAPI listener);
	
}
