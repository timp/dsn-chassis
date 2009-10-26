/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionController;
import org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerPubSubViewAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerViewAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.model.client.SubmissionModel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class ViewSubmissionWidget extends Composite implements ViewSubmissionWidgetAPI, SubmissionControllerPubSubViewAPI {
	
	
	
	
	final private SubmissionModel model;
	final private SubmissionControllerViewAPI controller;
	final private ViewSubmissionWidgetDefaultRenderer renderer;
	private Set<ViewSubmissionWidgetPubSubAPI> listeners = new HashSet<ViewSubmissionWidgetPubSubAPI>(); 

	
	
	
	/**
	 * Construct a widget, passing in the panel to use as the widget's canvas.
	 * 
	 * @param canvas
	 */
	public ViewSubmissionWidget(Panel canvas) {
		
		model = new SubmissionModel();
		
		controller = new SubmissionController(model, this);
						
		renderer = new ViewSubmissionWidgetDefaultRenderer(canvas, controller);
		
		// register renderer as listener to model
		model.addListener(renderer);	
		
		this.initWidget(this.renderer.getCanvas());
	}

	
	
	
	/**
	 * Construct a widget, allowing the widget to create its own canvas.
	 * 
	 */
	public ViewSubmissionWidget() {

		model = new SubmissionModel();
		
		controller = new SubmissionController(model, this);
						
		renderer = new ViewSubmissionWidgetDefaultRenderer(controller);
		
		// register renderer as listener to model
		model.addListener(renderer);		

		this.initWidget(this.renderer.getCanvas());

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

	
	
	
	public void fireOnUserActionEditSubmission(SubmissionEntry submissionEntryToEdit) {

		for (ViewSubmissionWidgetPubSubAPI listener : listeners) {
			listener.onUserActionEditSubmission(submissionEntryToEdit);
		}
		
	}
	

	
	

}
