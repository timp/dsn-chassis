/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studymanagement.client;

/**
 * @author raok
 *
 */
public interface StudyManagementWidgetModelListener {

	void onDisplayStatusChanged(Integer before, Integer after);

	void onUserMightLoseChanges(Integer userRequestedView);

}
