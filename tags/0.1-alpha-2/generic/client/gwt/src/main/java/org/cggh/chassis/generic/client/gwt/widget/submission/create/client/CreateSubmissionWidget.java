/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.create.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionController;
import org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerCreateAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerPubSubCreateAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.model.client.SubmissionModel;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class CreateSubmissionWidget implements CreateSubmissionWidgetAPI, SubmissionControllerPubSubCreateAPI {
	
	final private SubmissionModel model;
	final private SubmissionControllerCreateAPI controller;
	final private CreateSubmissionWidgetDefaultRenderer renderer;
	private Set<CreateSubmissionWidgetPubSubAPI> listeners = new HashSet<CreateSubmissionWidgetPubSubAPI>(); 

	public CreateSubmissionWidget(Panel canvas, AtomService service) {
		
		model = new SubmissionModel();
		
		controller = new SubmissionController(model, service, this);
		
		renderer = new CreateSubmissionWidgetDefaultRenderer(canvas, controller);
		
		// register renderer as listener to model
		model.addListener(renderer);		
	}
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidgetAPI#setUpNewSubmission(java.lang.String)
	 */
	public void setUpNewSubmission(String feedURL) {
		controller.setUpNewSubmission(feedURL);
	}

	public void createSubmissionEntryCancelled() {
		for (CreateSubmissionWidgetPubSubAPI listener : listeners) {
			listener.onCreateSubmissionCancelled();
		}
	}

	public void newSubmissionSaved(SubmissionEntry submissionEntry) {
		for (CreateSubmissionWidgetPubSubAPI listener : listeners) {
			listener.onNewSubmissionCreated(submissionEntry);
		}
	}

	public void addCreateSubmissionWidgetListener(CreateSubmissionWidgetPubSubAPI listener) {
		listeners.add(listener);				
	}


}