/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.create.client;

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

	public CreateSubmissionWidget(Panel canvas, AtomService service, String feedURL, Map<String, String> modulesConfig) {
		
		model = new SubmissionModel();
		
		controller = new SubmissionController(model, service, this, feedURL);
		
		String studyFeedURL = Configuration.getStudyFeedURL();
		
		//TODO use constructor parameter service when switched to use real service
		StudyFactory studyFactory = new StudyFactoryImpl();
		AtomService realService = new AtomServiceImpl(studyFactory);
		
		renderer = new CreateSubmissionWidgetDefaultRenderer(canvas, controller, modulesConfig, realService, studyFeedURL);
		
		// register renderer as listener to model
		model.addListener(renderer);		
	}
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidgetAPI#setUpNewSubmission(java.lang.String)
	 */
	public void setUpNewSubmission(String feedURL) {
		controller.setUpNewSubmission();
	}

	public void cancelCreateNewSubmissionEntry() {
		for (CreateSubmissionWidgetPubSubAPI listener : listeners) {
			listener.onUserActionCreateNewSubmissionCancelled();
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
