/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.application.client.perspective;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.client.gwt.widget.studymanagement.client.StudyManagementWidget;
import org.cggh.chassis.generic.client.gwt.widget.studymanagement.client.StudyManagementWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client.SubmissionManagementWidget;
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
	private Set<PerspectiveWidgetPubSubAPI> listeners = new HashSet<PerspectiveWidgetPubSubAPI>();

	public PerspectiveWidget(Panel canvas) {
		
		model = new PerspectiveWidgetModel();
		
		controller = new PerspectiveWidgetController(model);
		
		renderer = new PerspectiveWidgetDefaultRenderer(canvas, controller);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
		//register this widget as a listener to child widgets.
		renderer.studyManagmentWidget.addListener(this);
		renderer.submissionManagementWidget.addSubmissionManagementWidgetListener(this);
		
	}
	
	
	public void onStudyManagementDisplayStatusChanged(Boolean couldStatusContainUnsavedData) {
		
		controller.displayStudyManagementWidget(couldStatusContainUnsavedData);
		
	}

	public void onSubmissionManagmentDisplayStatusChanged(Boolean couldStatusContainUnsavedData) {
		
		controller.displaySubmissionManagementWidget(couldStatusContainUnsavedData);
		
	}


	public void addPerspectiveWidgetListener(PerspectiveWidgetPubSubAPI listener) {
		listeners.add(listener);
	}


	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.studymanagement.client.StudyManagementWidgetPubSubAPI#onStudyManagementMenuAction(org.cggh.chassis.generic.client.gwt.widget.studymanagement.client.StudyManagementWidget)
	 */
	public void onStudyManagementMenuAction(StudyManagementWidget source) {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client.SubmissionManagementWidgetPubSubAPI#onSubmissionManagementMenuAction(org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client.SubmissionManagementWidget)
	 */
	public void onSubmissionManagementMenuAction(
			SubmissionManagementWidget source) {
		// TODO Auto-generated method stub
		
	}

}
