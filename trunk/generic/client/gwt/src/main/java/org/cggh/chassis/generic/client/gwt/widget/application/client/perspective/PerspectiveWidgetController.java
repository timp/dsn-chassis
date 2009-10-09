/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.application.client.perspective;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author raok
 *
 */
public class PerspectiveWidgetController {
	private Log log = LogFactory.getLog(this.getClass());

	private PerspectiveWidgetModel model;

	public PerspectiveWidgetController(PerspectiveWidgetModel model) {
		this.model = model;
	}

	public void displayStudyManagementWidget(Boolean couldStatusContainUnsavedData) {
		
		displayStudyManagementWidget(couldStatusContainUnsavedData, false);
	}

	public void displayStudyManagementWidget(Boolean couldStatusContainUnsavedData, Boolean userConfirmed) {
		log.enter("displayStudyManagementWidget");
		
		log.trace("couldStatusContainUnsavedData: " + couldStatusContainUnsavedData);
		log.trace("userConfirmed: " + userConfirmed);
		
		if (couldStatusContainUnsavedData) {
			model.setDisplayStatus(PerspectiveWidgetModel.DISPLAYING_STUDY_MANAGEMENT_WIDGET_DATA_ENTRY, userConfirmed);
		} else {
			model.setDisplayStatus(PerspectiveWidgetModel.DISPLAYING_STUDY_MANAGEMENT_WIDGET, userConfirmed);
		}
		
		log.leave();
	}

	public void displaySubmissionManagementWidget(Boolean couldStatusContainUnsavedData) {
		displaySubmissionManagementWidget(couldStatusContainUnsavedData, false);
	}

	public void displaySubmissionManagementWidget(Boolean couldStatusContainUnsavedData, Boolean userConfirmed) {
		log.enter("displaySubmissionManagementWidget");
		
		log.trace("couldStatusContainUnsavedData: " + couldStatusContainUnsavedData);
		log.trace("userConfirmed: " + userConfirmed);
		
		if (couldStatusContainUnsavedData) {
			model.setDisplayStatus(PerspectiveWidgetModel.DISPLAYING_SUBMISSION_MANAGEMENT_WIDGET_DATA_ENTRY, userConfirmed);
		} else {
			model.setDisplayStatus(PerspectiveWidgetModel.DISPLAYING_SUBMISSION_MANAGEMENT_WIDGET, userConfirmed);
		}
		
		log.leave();		
	}

}
