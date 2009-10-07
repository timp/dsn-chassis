/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client;

/**
 * @author raok
 *
 */
public interface SubmissionManagementWidgetModelListener {

	void onDisplayStatusChanged(Integer before, Integer after);

	void userMightLoseChanges(Integer userRequestedView);

}
