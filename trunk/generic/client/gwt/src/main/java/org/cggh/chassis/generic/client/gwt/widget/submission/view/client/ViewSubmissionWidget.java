/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

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
import org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerPubSubViewAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerViewAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.model.client.SubmissionModel;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class ViewSubmissionWidget implements ViewSubmissionWidgetAPI, SubmissionControllerPubSubViewAPI {
	
	final private SubmissionModel model;
	final private SubmissionControllerViewAPI controller;
	final private ViewSubmissionWidgetDefaultRenderer renderer;
	private Set<ViewSubmissionWidgetPubSubAPI> listeners = new HashSet<ViewSubmissionWidgetPubSubAPI>(); 

	public ViewSubmissionWidget(Panel canvas, AtomService service, String feedURL, Map<String, String> modulesConfig) {
		
		model = new SubmissionModel();
		
		controller = new SubmissionController(model, service, this, feedURL);
		
		String studyFeedURL = Configuration.getStudyFeedURL();
		
		//TODO use constructor parameter service when switched to use real service
		StudyFactory studyFactory = new StudyFactoryImpl();
		AtomService realService = new AtomServiceImpl(studyFactory);
		
		renderer = new ViewSubmissionWidgetDefaultRenderer(canvas, controller, modulesConfig, realService, studyFeedURL);
		
		// register renderer as listener to model
		model.addListener(renderer);		
	}

	public void addViewSubmissionWidgetListener(ViewSubmissionWidgetPubSubAPI listener) {
		listeners.add(listener);
	}

	public void loadSubmissionByEntryURL(String entryURL) {
		controller.loadSubmissionEntryByURL(entryURL);
	}

	public void loadSubmissionEntry(SubmissionEntry submissionEntry) {
		controller.loadSubmissionEntry(submissionEntry);
	}

	public void onUserActionEditSubmission(SubmissionEntry submissionEntryToEdit) {

		for (ViewSubmissionWidgetPubSubAPI listener : listeners) {
			listener.onUserActionEditSubmission(submissionEntryToEdit);
		}
		
	}
	


}
