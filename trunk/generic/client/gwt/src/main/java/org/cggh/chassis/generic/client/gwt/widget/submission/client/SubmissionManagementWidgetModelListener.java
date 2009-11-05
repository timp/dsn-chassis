/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

/**
 * @author raok
 *
 */
public interface SubmissionManagementWidgetModelListener {

	void onDisplayStatusChanged(Integer before, Integer after);

	void userMightLoseChanges(Integer userRequestedView);

}
