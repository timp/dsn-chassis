/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.Composite;


/**
 * @author raok
 *
 */
public class ViewSubmissionWidget extends Composite implements EditSubmissionActionHandler, UploadDataFileActionHandler {
	
	
	
	
	private Log log = LogFactory.getLog(ViewSubmissionWidget.class);	
	private ViewSubmissionWidgetModel model;
	private ViewSubmissionWidgetController controller;
	private ViewSubmissionWidgetDefaultRenderer renderer;
	private Set<ViewSubmissionWidgetPubSubAPI> listeners = new HashSet<ViewSubmissionWidgetPubSubAPI>(); 

	
	
	
	/**
	 * Construct a widget, allowing the widget to create its own canvas.
	 * 
	 */
	public ViewSubmissionWidget() {

		model = new ViewSubmissionWidgetModel();
		
		controller = new ViewSubmissionWidgetController(model, this);
						
		renderer = new ViewSubmissionWidgetDefaultRenderer(this, controller);
		
		// register renderer as listener to model
		model.addListener(renderer);		

		this.initWidget(this.renderer.getCanvas());

	}

	
	
	
	
	public void addViewSubmissionWidgetListener(ViewSubmissionWidgetPubSubAPI listener) {
		listeners.add(listener);
	}

	
	
	
	public void retrieveSubmissionEntry(String entryURL) {
		controller.retrieveSubmissionEntry(entryURL);
	}

	
	
	
	public void setSubmissionEntry(SubmissionEntry submissionEntry) {
		controller.setSubmissionEntry(submissionEntry);
	}

	
	
	
	public void fireOnUserActionEditSubmission(SubmissionEntry submissionEntryToEdit) {

		for (ViewSubmissionWidgetPubSubAPI listener : listeners) {
			listener.onUserActionEditSubmission(submissionEntryToEdit);
		}
		
	}
	

	public void fireOnUserActionUploadSubmissionDataFile(String submissionLink) {

		for (ViewSubmissionWidgetPubSubAPI listener : listeners) {
			listener.onUserActionUploadDataFile(submissionLink);
		}
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.view.client.EditSubmissionActionHandler#onActionEditSubmission(org.cggh.chassis.generic.client.gwt.widget.submission.view.client.EditSubmissionActionEvent)
	 */
	public void onActionEditSubmission(EditSubmissionActionEvent editSubmissionActionEvent) {
		log.enter("onActionEditSubmission");
		
		// adapt event for now
		this.fireOnUserActionEditSubmission(this.model.getSubmissionEntry());
		
		// TODO refactor using gwt event pattern here to pass on event
		
		log.leave();
		
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.view.client.UploadDataFileActionHandler#onActionUploadDataFile(org.cggh.chassis.generic.client.gwt.widget.submission.view.client.UploadDataFileActionEvent)
	 */
	public void onActionUploadDataFile(UploadDataFileActionEvent uploadDataFileActionEvent) {
		log.enter("onActionUploadDataFile");

		// adapt event for now
		this.fireOnUserActionUploadSubmissionDataFile(this.model.getSubmissionEntry().getEditLink().getHref());

		// TODO refactor using gwt event pattern here to pass on event
		
		log.leave();
		
	}
	

	
	

}
