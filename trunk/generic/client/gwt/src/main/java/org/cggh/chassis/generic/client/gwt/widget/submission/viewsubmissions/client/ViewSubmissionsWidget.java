/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class ViewSubmissionsWidget extends Composite {

	
	
	
	final private ViewSubmissionsWidgetModel model;
	final private ViewSubmissionsWidgetController controller;
	final private ViewSubmissionsWidgetDefaultRenderer renderer;
	private Set<ViewSubmissionsWidgetPubSubAPI> listeners = new HashSet<ViewSubmissionsWidgetPubSubAPI>();
	
	
	
	
	
	/**
	 * 
	 * @param canvas
	 */
	public ViewSubmissionsWidget(Panel canvas) {
		
		model = new ViewSubmissionsWidgetModel();
		
		controller = new ViewSubmissionsWidgetController(model, this);
		
		renderer = new ViewSubmissionsWidgetDefaultRenderer(canvas, controller);
		
		// register renderer as listener to model
		model.addListener(renderer);

		this.initWidget(this.renderer.getCanvas());
		
	}
	
	
	
	
	
	/**
	 * 
	 */
	public ViewSubmissionsWidget() {

		model = new ViewSubmissionsWidgetModel();
		
		controller = new ViewSubmissionsWidgetController(model, this);
		
		renderer = new ViewSubmissionsWidgetDefaultRenderer(controller);
		
		// register renderer as listener to model
		model.addListener(renderer);

		this.initWidget(this.renderer.getCanvas());

	}


	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.viewall.client.ViewAllSubmissionsWidgetAPI#loadSubmissionsByFeedURL(java.lang.String)
	 */
	public void loadSubmissions() {
		controller.loadSubmissionsByFeedURL();
	}

	
	
	
	void onUserSelectSubmission(SubmissionEntry submissionEntry) {
		for (ViewSubmissionsWidgetPubSubAPI listener : listeners) {
			listener.onUserActionSelectSubmission(submissionEntry);
		}
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.viewall.client.ViewAllSubmissionsWidgetAPI#addViewAllSubmissionsWidgetListener(org.cggh.chassis.generic.client.gwt.widget.submission.viewall.client.ViewAllSubmissionsWidgetPubSubAPI)
	 */
	public void addViewAllSubmissionsWidgetListener(ViewSubmissionsWidgetPubSubAPI listener) {
		listeners.add(listener);
	}

	
	
	
	public void loadSubmissions(Set<String> submissionEntryURLsToLoad) {
		controller.loadSubmissionsByEntryURLs(submissionEntryURLsToLoad);
	}

	
	

	public void loadSubmissionsByAuthorEmail(String authorEmail) {
		controller.loadSubmissionsByAuthorEmail(authorEmail);
	}
	
	
	
	
}
