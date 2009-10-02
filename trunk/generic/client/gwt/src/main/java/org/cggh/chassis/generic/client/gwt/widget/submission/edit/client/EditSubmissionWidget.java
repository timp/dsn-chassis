/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.edit.client;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.study.client.format.impl.StudyFactoryImpl;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.AtomServiceImpl;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
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

	public EditSubmissionWidget(Panel canvas, AtomService service, String feedURL, Map<String, String> modulesConfig) {
		
		model = new SubmissionModel();
		
		controller = new SubmissionController(model, service, this, feedURL);
		
		String studyFeedURL = Configuration.getStudyFeedURL();
		
		//TODO use constructor parameter service when switched to use real service
		StudyFactory studyFactory = new StudyFactoryImpl();
		AtomService realService = new AtomServiceImpl(studyFactory);
		
		renderer = new EditSubmissionWidgetDefaultRenderer(canvas, controller, modulesConfig, realService, studyFeedURL);
		
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
