/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.edit.client.EditSubmissionWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.view.client.ViewSubmissionWidgetPubSubAPI;
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
public class SubmissionManagementWidget extends Composite implements SubmissionManagementWidgetAPI,
												   					CreateSubmissionWidgetPubSubAPI,
												   					EditSubmissionWidgetPubSubAPI,
												   					ViewSubmissionWidgetPubSubAPI,
												   					ViewSubmissionsWidgetPubSubAPI {
	
	
	
	
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
		renderer.viewSubmissionWidget.addViewSubmissionWidgetListener(this);
		renderer.createSubmissionWidget.addCreateSubmissionWidgetListener(this);
		renderer.editSubmissionWidget.addEditSubmissionWidgetListener(this);
		renderer.viewAllSubmissionsWidget.addViewAllSubmissionsWidgetListener(this);
	}
	
	
	
	
	
	public void onNewSubmissionSaveSuccess(SubmissionEntry newSubmissionEntry) {
		log.enter("onNewSubmissionCreated");
		
		controller.displayViewSubmissionWidget();
		renderer.viewSubmissionWidget.loadSubmissionEntry(newSubmissionEntry);
		
		log.leave();
	}

	
	
	
	public void onSubmissionUpdateSuccess(SubmissionEntry updatedSubmissionEntry) {
		log.enter("onSubmissionUpdateSuccess");
		
		controller.displayViewSubmissionWidget();
		renderer.viewSubmissionWidget.loadSubmissionEntry(updatedSubmissionEntry);
		
		log.leave();
	}

	
	
	
	public void onUserActionEditSubmission(SubmissionEntry submissionEntryToEdit) {
		log.enter("onUserActionEditSubmission");
		
		controller.displayEditSubmissionWidget();
		renderer.editSubmissionWidget.editSubmissionEntry(submissionEntryToEdit);
		
		log.leave();
	}

	
	
	
	public void onUserActionSelectSubmission(SubmissionEntry submissionEntry) {
		log.enter("onUserActionSelectSubmission");
		
		controller.displayViewSubmissionWidget();
		renderer.viewSubmissionWidget.loadSubmissionEntry(submissionEntry);
		
		log.leave();
	}

	
	
	
	public void onUserActionUpdateSubmissionCancelled() {
		// TODO Auto-generated method stub
		
	}

	
	
	
	public void onUserActionCreateNewSubmissionCancelled() {
		// TODO Auto-generated method stub
		
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





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidgetPubSubAPI#onNewSubmissionSaveError(java.lang.Throwable)
	 */
	public void onNewSubmissionSaveError(Throwable error) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
