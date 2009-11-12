/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;


import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class MySubmissionsWidget extends Composite {

	
	
	
	final private MySubmissionsWidgetModel model;
	final private MySubmissionsWidgetController controller;
	final private MySubmissionsWidgetDefaultRenderer renderer;
	private Set<MySubmissionsWidgetPubSubAPI> listeners = new HashSet<MySubmissionsWidgetPubSubAPI>();
	
	
	
	
	
	/**
	 * 
	 * @param canvas
	 */
	public MySubmissionsWidget(Panel canvas) {
		
		model = new MySubmissionsWidgetModel();
		
		controller = new MySubmissionsWidgetController(model, this);
		
		renderer = new MySubmissionsWidgetDefaultRenderer(canvas, controller);
		
		// register renderer as listener to model
		model.addListener(renderer);

		this.initWidget(this.renderer.getCanvas());
		
	}
	
	
	
	
	
	/**
	 * 
	 */
	public MySubmissionsWidget() {

		model = new MySubmissionsWidgetModel();
		
		controller = new MySubmissionsWidgetController(model, this);
		
		renderer = new MySubmissionsWidgetDefaultRenderer(controller);
		
		// register renderer as listener to model
		model.addListener(renderer);

		this.initWidget(this.renderer.getCanvas());

	}


	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.viewall.client.ViewAllSubmissionsWidgetAPI#loadSubmissionsByCollectionUrl(java.lang.String)
	 */
	public void loadSubmissions() {
		controller.loadSubmissionsByCollectionUrl();
	}

	
	
	
	void onUserSelectSubmission(SubmissionEntry submissionEntry) {
		for (MySubmissionsWidgetPubSubAPI listener : listeners) {
			listener.onUserActionSelectSubmission(submissionEntry);
		}
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.viewall.client.ViewAllSubmissionsWidgetAPI#addViewAllSubmissionsWidgetListener(org.cggh.chassis.generic.client.gwt.widget.submission.viewall.client.ViewAllSubmissionsWidgetPubSubAPI)
	 */
	public void addViewAllSubmissionsWidgetListener(MySubmissionsWidgetPubSubAPI listener) {
		listeners.add(listener);
	}

	
	
	
	public void loadSubmissions(Set<String> submissionEntryUrlsToLoad) {
		controller.loadSubmissionsByEntryUrls(submissionEntryUrlsToLoad);
	}

	
	

	public void loadSubmissionsByAuthorEmail(String authorEmail) {
		controller.loadSubmissionsByAuthorEmail(authorEmail);
	}
	
	
	
	
}
