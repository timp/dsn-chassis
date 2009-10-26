/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client;

/**
 * @author raok
 *
 */
public interface SubmissionManagementWidgetPubSubAPI {
	
	public void onSubmissionManagmentDisplayStatusChanged(Boolean couldStatusContainUnsavedData);

	public void onSubmissionManagementMenuAction(SubmissionManagementWidget source);
	
}
