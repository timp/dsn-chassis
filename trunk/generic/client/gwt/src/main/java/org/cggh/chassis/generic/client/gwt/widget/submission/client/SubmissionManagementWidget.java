/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.CancelEvent;
import org.cggh.chassis.generic.widget.client.CancelHandler;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.generic.widget.client.ErrorHandler;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
public class SubmissionManagementWidget extends Composite implements EditSubmissionWidgetPubSubAPI,
																  	 MySubmissionsWidgetPubSubAPI,
																  	 UploadSubmissionDataFileWidgetPubSubAPI, 
																  	 EditSubmissionActionHandler, 
																  	 UploadDataFileActionHandler, 
																  	 CreateSubmissionSuccessHandler, 
																  	 CancelHandler, 
																  	 ErrorHandler {
	private Log log = LogFactory.getLog(this.getClass());


	
	
	private SubmissionManagementWidgetModel model;
	private SubmissionManagementWidgetController controller;
	private SubmissionManagementWidgetDefaultRenderer renderer;
	private Panel menuCanvas = new SimplePanel();
	private Set<SubmissionManagementWidgetPubSubAPI> listeners = new HashSet<SubmissionManagementWidgetPubSubAPI>();

	
	
	
	/**
	 * 
	 */
	public SubmissionManagementWidget() {

		model = new SubmissionManagementWidgetModel(this);
		
		controller = new SubmissionManagementWidgetController(this, model);
		
		renderer = new SubmissionManagementWidgetDefaultRenderer(this, controller);
		
		// register renderer as listener to model
		model.addListener(renderer);

		this.subscribeToChildWidgetEvents();
		
		this.initWidget(this.renderer.getCanvas());

	}

	
	
	
	
	private void subscribeToChildWidgetEvents() {
		//register this widget as a listener to child widgets.

		renderer.viewSubmissionWidget.addEditSubmissionActionHandler(this);
		renderer.viewSubmissionWidget.addUploadDataFileActionHandler(this);

		renderer.createSubmissionWidget.addCreateSubmissionSuccessHandler(this);
		renderer.createSubmissionWidget.addCancelHandler(this);
		renderer.createSubmissionWidget.addErrorHandler(this);

		// TODO move these from old pattern
		renderer.editSubmissionWidget.addEditSubmissionWidgetListener(this);
		renderer.viewAllSubmissionsWidget.addViewAllSubmissionsWidgetListener(this);
		renderer.submissionDataFileWidget.addSubmissionDataFileWidget(this);
	}
	
	
	
	
	

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client.SubmissionManagementWidgetAPI#getMenuCanvas()
	 */
	public Panel getMenuCanvas() {
		return menuCanvas;
	}

	
	
	

	public void addSubmissionManagementWidgetListener(SubmissionManagementWidgetPubSubAPI listener) {

		listeners.add(listener);
		
	}

	
	
	
	public void resetWidget() {
		
		controller.reset();
		
	}




	/**
	 * @return
	 */
	public MenuBar getMenu() {
		return this.renderer.getMenu();
	}




	void fireOnDisplayStatusChanged(Boolean couldStatusContainUnsavedData) {
		
		for (SubmissionManagementWidgetPubSubAPI listener : listeners) {
			listener.onSubmissionManagmentDisplayStatusChanged(couldStatusContainUnsavedData);
		}
		
	}

	
	
	
	/**
	 * 
	 */
	public void fireOnSubmissionManagementMenuAction() {
		for (SubmissionManagementWidgetPubSubAPI listener : listeners) {
			listener.onSubmissionManagementMenuAction(this);
		}
	}

	
	
	
	/*
	 * *************************************************************************
	 * old pattern listeners, TODO refactor....
	 * *************************************************************************
	 */






	public void onSubmissionDataFileCancelled() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

	public void onSubmissionDataFileUploaded(String submissionLink) {
		log.enter("onSubmissionDataFileUploaded");
		
		controller.displayViewSubmissionWidget();
		renderer.viewSubmissionWidget.retrieveSubmissionEntry(submissionLink);
		
		log.leave();
	}


	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidgetPubSubAPI#onNewSubmissionSaveError(java.lang.Throwable)
	 */
	public void onEditSubmissionError(Throwable error) {
		// TODO Auto-generated method stub
		
	}



	public void onEditSubmissionSuccess(SubmissionEntry newSubmissionEntry) {
		log.enter("onNewSubmissionCreated");
		
		controller.displayViewSubmissionWidget();
		
		renderer.viewSubmissionWidget.setSubmissionEntry(newSubmissionEntry);
		
		log.leave();
	}

	
	
	

	public void onUserActionSelectSubmission(SubmissionEntry submissionEntry) {
		log.enter("onUserActionSelectSubmission");
		
		controller.displayViewSubmissionWidget();
		renderer.viewSubmissionWidget.setSubmissionEntry(submissionEntry);
		
		log.leave();
	}

	
	
	
	public void onUserActionUpdateSubmissionCancelled() {
		// TODO Auto-generated method stub
		
	}

	
	
	
	public void onUserActionCreateSubmissionCancelled() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	/*
	 * *************************************************************************
	 * new pattern handlers....
	 * *************************************************************************
	 */



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.view.client.EditSubmissionActionHandler#onActionEditSubmission(org.cggh.chassis.generic.client.gwt.widget.submission.view.client.EditSubmissionActionEvent)
	 */
	public void onActionEditSubmission(EditSubmissionActionEvent e) {
		log.enter("onActionEditSubmission");
		
		controller.displayEditSubmissionWidget();
		renderer.editSubmissionWidget.setSubmissionEntry(e.getSubmissionEntry());
		
		log.leave();
		
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.view.client.UploadDataFileActionHandler#onActionUploadDataFile(org.cggh.chassis.generic.client.gwt.widget.submission.view.client.UploadDataFileActionEvent)
	 */
	public void onActionUploadDataFile(UploadDataFileActionEvent e) {
		log.enter("onActionUploadDataFile");
		
		String submissionLink = Configuration.getSubmissionFeedURL() + e.getSubmissionEntry().getEditLink().getHref();

		renderer.submissionDataFileWidget.setUpNewSubmissionDataFile(submissionLink);
		
		controller.displaySubmissionDataFileUploadWidget();
		
		log.leave();
		
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionSuccessHandler#onCreateSubmissionSuccess(org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionSuccessEvent)
	 */
	public void onCreateSubmissionSuccess(CreateSubmissionSuccessEvent e) {
		log.enter("onCreateSubmissionSuccess");
		
		controller.displayViewSubmissionWidget();
		
		renderer.viewSubmissionWidget.setSubmissionEntry(e.getSubmissionEntry());
		
		log.leave();
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.CancelHandler#onCancel(org.cggh.chassis.generic.widget.client.CancelEvent)
	 */
	public void onCancel(CancelEvent e) {
		log.enter("onCancel");
		
		// TODO Auto-generated method stub
		
		log.leave();
		
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ErrorHandler#onError(org.cggh.chassis.generic.widget.client.ErrorEvent)
	 */
	public void onError(ErrorEvent e) {
		log.enter("onError");
		
		// TODO Auto-generated method stub
		
		log.leave();
		
	}






}
