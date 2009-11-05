/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

/**
 * @author raok
 *
 */
public interface StudyManagementWidgetPubSubAPI {
	
	public void onStudyManagementDisplayStatusChanged(Boolean couldStatusContainUnsavedData);
	
	public void onStudyManagementMenuAction(StudyManagementWidget source);
	
}
