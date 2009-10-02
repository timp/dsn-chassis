/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class ViewSubmissionsWidget implements ViewSubmissionsWidgetAPI {

	final private ViewSubmissionsWidgetModel model;
	final private ViewSubmissionsWidgetController controller;
	final private ViewSubmissionsWidgetModelListener renderer;
	private Set<ViewSubmissionsWidgetPubSubAPI> listeners = new HashSet<ViewSubmissionsWidgetPubSubAPI>();
	
	public ViewSubmissionsWidget(Panel canvas, AtomService service, String feedURL) {
		
		model = new ViewSubmissionsWidgetModel();
		
		controller = new ViewSubmissionsWidgetController(model, service, this, feedURL);
		
		renderer = new ViewSubmissionsWidgetDefaultRenderer(canvas, controller);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
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
	
	
}
