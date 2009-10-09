/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.application.client.perspective;

import org.cggh.chassis.generic.client.gwt.widget.studymanagement.client.StudyManagementWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client.SubmissionManagementWidgetPubSubAPI;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class PerspectiveWidget implements PerspectiveWidgetAPI, 
										  StudyManagementWidgetPubSubAPI, 
										  SubmissionManagementWidgetPubSubAPI {

	
	final private PerspectiveWidgetModel model;
	final private PerspectiveWidgetController controller;
	final private PerspectiveWidgetDefaultRenderer renderer;

	public PerspectiveWidget(Panel canvas, String authorEmail) {
		
		model = new PerspectiveWidgetModel();
		
		controller = new PerspectiveWidgetController(model);
		
		renderer = new PerspectiveWidgetDefaultRenderer(canvas, controller, authorEmail);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
		//register this widget as a listener to child widgets.
		renderer.studyManagmentWidget.addStudyManagementWidgetListener(this);
		renderer.submissionManagmentWidget.addSubmissionManagementWidgetListener(this);
		
	}
	
	
	public void onStudyManagmentDisplayStatusChanged(Boolean couldStatusContainUnsavedData) {
		
		controller.displayStudyManagementWidget(couldStatusContainUnsavedData);
		
	}

	public void onSubmissionManagmentDisplayStatusChanged(Boolean couldStatusContainUnsavedData) {
		
		controller.displaySubmissionManagementWidget(couldStatusContainUnsavedData);
		
	}

}
