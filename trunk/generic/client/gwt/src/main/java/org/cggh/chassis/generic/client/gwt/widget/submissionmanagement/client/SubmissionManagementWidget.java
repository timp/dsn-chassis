/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.edit.client.EditSubmissionWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client.UploadSubmissionDataFileWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.view.client.EditSubmissionActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.submission.view.client.EditSubmissionActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.submission.view.client.UploadDataFileActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.submission.view.client.UploadDataFileActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client.ViewSubmissionsWidgetPubSubAPI;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
public class SubmissionManagementWidget extends Composite implements CreateSubmissionWidgetPubSubAPI,
																  	 EditSubmissionWidgetPubSubAPI,
																  	 /* ViewSubmissionWidgetPubSubAPI, */
																  	 ViewSubmissionsWidgetPubSubAPI,
																  	 UploadSubmissionDataFileWidgetPubSubAPI, 
																  	 EditSubmissionActionHandler, 
																  	 UploadDataFileActionHandler {
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
		
		// TODO move from old pattern
		renderer.createSubmissionWidget.addCreateSubmissionWidgetListener(this);
		renderer.editSubmissionWidget.addEditSubmissionWidgetListener(this);
		renderer.viewAllSubmissionsWidget.addViewAllSubmissionsWidgetListener(this);
		renderer.submissionDataFileWidget.addSubmissionDataFileWidget(this);
	}
	
	
	
	
	
	public void onCreateSubmissionSuccess(SubmissionEntry newSubmissionEntry) {
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

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client.SubmissionManagementWidgetAPI#getMenuCanvas()
	 */
	public Panel getMenuCanvas() {
		return menuCanvas;
	}

	
	
	
	public void onSubmissionDataFileCancelled() {
		// TODO Auto-generated method stub
		
	}

	public void onSubmissionDataFileUploaded(String submissionLink) {
		log.enter("onSubmissionDataFileUploaded");
		
		renderer.viewSubmissionWidget.retrieveSubmissionEntry(submissionLink);
		
		controller.displayViewSubmissionWidget();
		
		log.leave();
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





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidgetPubSubAPI#onNewSubmissionSaveError(java.lang.Throwable)
	 */
	public void onCreateSubmissionError(Throwable error) {
		// TODO Auto-generated method stub
		
	}





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
	
	
	
}
