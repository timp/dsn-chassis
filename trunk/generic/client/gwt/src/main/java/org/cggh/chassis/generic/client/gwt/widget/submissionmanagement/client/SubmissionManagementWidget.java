/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client;

import java.util.Map;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.edit.client.EditSubmissionWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.view.client.ViewSubmissionWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client.ViewSubmissionsWidgetPubSubAPI;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class SubmissionManagementWidget implements CreateSubmissionWidgetPubSubAPI,
											  EditSubmissionWidgetPubSubAPI,
											  ViewSubmissionWidgetPubSubAPI,
											  ViewSubmissionsWidgetPubSubAPI {
	
	private Log log = LogFactory.getLog(this.getClass());

	
	private SubmissionManagementWidgetModel model;
	private SubmissionManagementWidgetController controller;
	private SubmissionManagementWidgetDefaultRenderer renderer;

	public SubmissionManagementWidget(Panel menuCanvas, Panel displayCanvas, AtomService service, String feedURL, Map<String, String> modulesMap) {
		
		model = new SubmissionManagementWidgetModel();
		
		controller = new SubmissionManagementWidgetController(model);
		
		renderer = new SubmissionManagementWidgetDefaultRenderer(menuCanvas, displayCanvas, controller, feedURL, service, modulesMap);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
		//register this widget as a listener to child widgets.
		renderer.viewSubmissionWidget.addViewSubmissionWidgetListener(this);
		renderer.createSubmissionWidget.addCreateSubmissionWidgetListener(this);
		renderer.editSubmissionWidget.addEditSubmissionWidgetListener(this);
		renderer.viewAllSubmissionsWidget.addViewAllSubmissionsWidgetListener(this);
	}

	public void onNewSubmissionCreated(SubmissionEntry newSubmissionEntry) {
		log.enter("onNewSubmissionCreated");
		
		renderer.viewSubmissionWidget.loadSubmissionEntry(newSubmissionEntry);
		controller.displayViewSubmissionWidget();
		
		log.leave();
	}

	public void onSubmissionUpdateSuccess(SubmissionEntry updatedSubmissionEntry) {
		log.enter("onSubmissionUpdateSuccess");
		
		renderer.viewSubmissionWidget.loadSubmissionEntry(updatedSubmissionEntry);
		controller.displayViewSubmissionWidget();
		
		log.leave();
	}

	public void onUserActionEditSubmission(SubmissionEntry submissionEntryToEdit) {
		log.enter("onUserActionEditSubmission");
		
		renderer.editSubmissionWidget.editSubmissionEntry(submissionEntryToEdit);
		controller.displayEditSubmissionWidget();
		
		log.leave();
	}

	public void onUserActionSelectSubmission(SubmissionEntry submissionEntry) {
		log.enter("onUserActionSelectSubmission");
		
		renderer.viewSubmissionWidget.loadSubmissionEntry(submissionEntry);
		controller.displayViewSubmissionWidget();
		
		log.leave();
	}

	public void onUserActionUpdateSubmissionCancelled() {
		// TODO Auto-generated method stub
		
	}

	public void onUserActionCreateNewSubmissionCancelled() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
