/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyManagementWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyManagementWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.SubmissionManagementWidget;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.SubmissionManagementWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.submitter.home.client.SubmitterHomeWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.Composite;

/**
 * @author aliman
 *
 */
public class SubmitterPerspective extends Perspective implements 	StudyManagementWidgetPubSubAPI, 
																	SubmissionManagementWidgetPubSubAPI {

	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private SubmitterPerspectiveRenderer renderer;
	private SubmitterPerspectiveController controller;
	private SubmitterPerspectiveModel model;
	
	
	
	public SubmitterPerspective() {
		log.enter("<constructor>");
		
		this.model = new SubmitterPerspectiveModel();
		
		this.controller = new SubmitterPerspectiveController(this.model);
		
		this.renderer = new SubmitterPerspectiveDefaultRenderer(this, this.controller);
		
		this.model.addListener(this.renderer);
		
		this.controller.show(SubmitterHomeWidget.class.getName());
		
		this.initWidget(this.renderer.getCanvas());
		
		log.leave();
	}



	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.studymanagement.client.StudyManagementWidgetPubSubAPI#onStudyManagementDisplayStatusChanged(java.lang.Boolean)
	 */
	public void onStudyManagementDisplayStatusChanged(Boolean couldStatusContainUnsavedData) {
		// TODO not interested?
	}



	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.studymanagement.client.StudyManagementWidgetPubSubAPI#onStudyManagementMenuAction()
	 */
	public void onStudyManagementMenuAction(StudyManagementWidget source) {
		log.enter("onStudyManagementMenuAction");
		
		this.controller.show(source.getClass().getName());
		
		log.leave();
	}


	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client.SubmissionManagementWidgetPubSubAPI#onSubmissionManagmentDisplayStatusChanged(java.lang.Boolean)
	 */
	public void onSubmissionManagmentDisplayStatusChanged(Boolean couldStatusContainUnsavedData) {
		// TODO not interested?
	}


	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client.SubmissionManagementWidgetPubSubAPI#onSubmissionManagementMenuAction()
	 */
	public void onSubmissionManagementMenuAction(SubmissionManagementWidget source) {
		log.enter("onSubmissionManagementMenuAction");
		
		this.controller.show(source.getClass().getName());
		
		log.leave();
	}
	
	
	
}
