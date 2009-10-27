/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;


/**
 * @author raok
 *
 */
public class SubmissionDataFileWidget extends Composite implements SubmissionDataFileWidgetAPI {

	final private SubmissionDataFileWidgetModel model;
	final private SubmissionDataFileWidgetController controller;
	final private SubmissionDataFileWidgetDefaultRenderer renderer;
	private Set<SubmissionDataFileWidgetPubSubAPI> listeners = new HashSet<SubmissionDataFileWidgetPubSubAPI>(); 
	
	String authorEmail;
	
	/**
	 * Use this constructor to give the widget a panel to renderer itself on. 
	 *  
	 * @param givenCanvas
	 */
	public SubmissionDataFileWidget(Panel givenCanvas, String authorEmail) {
		
		this.authorEmail = authorEmail;
		
		model = new SubmissionDataFileWidgetModel();
		
		controller = new SubmissionDataFileWidgetController(model, this);
		
		renderer = new SubmissionDataFileWidgetDefaultRenderer(givenCanvas, controller);

		// register renderer as listener to model
		model.addListener(renderer);

		this.initWidget(this.renderer.getCanvas());
		
	}
	
	/**
	 * Use this constructor to let the widget create it's own canvas
	 */
	public SubmissionDataFileWidget(String authorEmail) {

		this.authorEmail = authorEmail;
		
		model = new SubmissionDataFileWidgetModel();
		
		controller = new SubmissionDataFileWidgetController(model, this);
		
		renderer = new SubmissionDataFileWidgetDefaultRenderer(controller);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
		this.initWidget(this.renderer.getCanvas());
		
	}
	
	
	public void addSubmissionDataFileWidget(SubmissionDataFileWidgetPubSubAPI listener) {
		listeners.add(listener);
	}

	public void setUpNewSubmissionDataFile(String submissionLink) {
		controller.setUpUploadSubmissionDataFile(submissionLink, authorEmail);
	}
	
	void fireOnUserActionSubmissionDataFileUploadCancelled() {

		for (SubmissionDataFileWidgetPubSubAPI listener : listeners) {
			listener.onSubmissionDataFileCancelled();
		}		
	}
	
	void fireOnUserActionSubmissionDataFileUploaded(String submissionLink) {

		for (SubmissionDataFileWidgetPubSubAPI listener : listeners) {
			listener.onSubmissionDataFileUploaded(submissionLink);
		}		
	}

}
