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
import org.cggh.chassis.generic.client.gwt.widget.submission.model.client.SubmissionModel;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class EditSubmissionWidget implements EditSubmissionWidgetAPI, SubmissionControllerPubSubEditAPI {
	
	final private SubmissionModel model;
	final private SubmissionControllerEditAPI controller;
	final private EditSubmissionWidgetDefaultRenderer renderer;
	private Set<EditSubmissionWidgetPubSubAPI> listeners = new HashSet<EditSubmissionWidgetPubSubAPI>(); 

	public EditSubmissionWidget(Panel canvas, String authorEmail) {
		
		model = new SubmissionModel();
		
		controller = new SubmissionController(model, this);
						
		renderer = new EditSubmissionWidgetDefaultRenderer(canvas, controller, authorEmail);
		
		// register renderer as listener to model
		model.addListener(renderer);		
	}
	
	
	public void addEditSubmissionWidgetListener(EditSubmissionWidgetPubSubAPI listener) {
		listeners.add(listener);				
	}

	public void editSubmissionEntry(SubmissionEntry submissionEntryToEdit) {
		controller.loadSubmissionEntry(submissionEntryToEdit);
	}

	public void onSubmissionEntryUpdated(SubmissionEntry updatedSubmissionEntry) {

		for (EditSubmissionWidgetPubSubAPI listener : listeners) {
			listener.onSubmissionUpdateSuccess(updatedSubmissionEntry);
		}
	}

	public void onUserActionEditSubmissionEntryCancelled() {
		
		for (EditSubmissionWidgetPubSubAPI listener : listeners) {
			listener.onUserActionUpdateSubmissionCancelled();
		}
	}


}
