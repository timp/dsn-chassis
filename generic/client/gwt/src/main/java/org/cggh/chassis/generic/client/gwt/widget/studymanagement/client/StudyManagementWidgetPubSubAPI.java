/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studymanagement.client;

/**
 * @author raok
 *
 */
public interface StudyManagementWidgetPubSubAPI {
	
	public void onStudyManagementDisplayStatusChanged(Boolean couldStatusContainUnsavedData);
	
	public void onStudyManagementMenuAction(StudyManagementWidget source);
	
}
