/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.edit.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionController;
import org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerPubSubEditAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidgetController;
import org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidgetDefaultRenderer;
import org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidgetModel;
import org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.model.client.SubmissionModel;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class EditSubmissionWidget extends Composite {
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private EditSubmissionWidgetModel model;
	private EditSubmissionWidgetController controller;
	private EditSubmissionWidgetDefaultRenderer renderer;
	private Set<EditSubmissionWidgetPubSubAPI> listeners = new HashSet<EditSubmissionWidgetPubSubAPI>(); 

	
	
	
	
	
	/**
	 * 
	 */
	public EditSubmissionWidget() {
		log.enter("<constructor>");
		
		this.model = new EditSubmissionWidgetModel();
		
		this.controller = new EditSubmissionWidgetController(this, this.model);
						
		this.renderer = new EditSubmissionWidgetDefaultRenderer(this.controller);
		
		this.model.addListener(this.renderer);
		
		this.initWidget(this.renderer.getCanvas());
		
		this.controller.ready();
		
		log.leave();
		
	}


	
	
	public void addEditSubmissionWidgetListener(EditSubmissionWidgetPubSubAPI listener) {
		listeners.add(listener);				
	}

	
	
	
	public void editSubmissionEntry(SubmissionEntry submissionEntryToEdit) {
		log.enter("editSubmissionEntry");
//		controller.loadSubmissionEntry(submissionEntryToEdit);
		
		log.debug("setting model on submission form with entry: "+submissionEntryToEdit.getTitle());

		this.renderer.getForm().setModel(submissionEntryToEdit);

		log.leave();
	}

	
	
	
	public void fireOnUserActionEditNewSubmissionCancelled() {
		for (EditSubmissionWidgetPubSubAPI listener : listeners) {
			listener.onUserActionCreateNewSubmissionCancelled();
		}
	}
	
	
	

	public void fireOnNewSubmissionSaveSuccess(SubmissionEntry submissionEntry) {
		for (EditSubmissionWidgetPubSubAPI listener : listeners) {
			listener.onNewSubmissionSaveSuccess(submissionEntry);
		}
	}

	
	
	
	/**
	 * @param error
	 */
	public void fireOnNewSubmissionSaveError(Throwable error) {
		for (EditSubmissionWidgetPubSubAPI listener : listeners) {
			listener.onNewSubmissionSaveError(error);
		}
	}





	
}
