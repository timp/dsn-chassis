/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;

import com.google.gwt.user.client.ui.Composite;


/**
 * @author raok
 *
 */
public class ViewSubmissionWidget extends Composite {
	
	
	
	
	final private ViewSubmissionWidgetModel model;
	final private ViewSubmissionWidgetController controller;
	final private ViewSubmissionWidgetDefaultRenderer renderer;
	private Set<ViewSubmissionWidgetPubSubAPI> listeners = new HashSet<ViewSubmissionWidgetPubSubAPI>(); 

	
	
	
	/**
	 * Construct a widget, allowing the widget to create its own canvas.
	 * 
	 */
	public ViewSubmissionWidget() {

		model = new ViewSubmissionWidgetModel();
		
		controller = new ViewSubmissionWidgetController(model, this);
						
		renderer = new ViewSubmissionWidgetDefaultRenderer(controller);
		
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
	

	
	

}
