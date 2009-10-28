/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;

import com.google.gwt.user.client.ui.Composite;


/**
 * @author raok
 *
 */
public class UploadSubmissionDataFileWidget extends Composite implements UploadSubmissionDataFileWidgetAPI {

	final private UploadSubmissionDataFileWidgetModel model;
	final private UploadSubmissionDataFileWidgetController controller;
	final private UploadSubmissionDataFileWidgetDefaultRenderer renderer;
	private Set<UploadSubmissionDataFileWidgetPubSubAPI> listeners = new HashSet<UploadSubmissionDataFileWidgetPubSubAPI>(); 
			
	/**
	 * Use this constructor to let the widget create it's own canvas
	 */
	public UploadSubmissionDataFileWidget() {
		
		model = new UploadSubmissionDataFileWidgetModel();
		
		controller = new UploadSubmissionDataFileWidgetController(model, this);
		
		renderer = new UploadSubmissionDataFileWidgetDefaultRenderer(controller);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
		this.initWidget(this.renderer.getCanvas());
		
	}
	
	
	public void addSubmissionDataFileWidget(UploadSubmissionDataFileWidgetPubSubAPI listener) {
		listeners.add(listener);
	}

	public void setUpNewSubmissionDataFile(String submissionLink) {
		
		renderer.render();
		
		renderer.getForm().getRenderer().authorHiddenField.setValue(ChassisUser.getCurrentUserEmail());
		renderer.getForm().getRenderer().submissionLinkHiddenField.setValue(submissionLink);
		
		controller.ready();
		
	}
	
	void fireOnUserActionSubmissionDataFileUploadCancelled() {

		for (UploadSubmissionDataFileWidgetPubSubAPI listener : listeners) {
			listener.onSubmissionDataFileCancelled();
		}		
	}
	
	void fireOnUserActionSubmissionDataFileUploaded() {

		for (UploadSubmissionDataFileWidgetPubSubAPI listener : listeners) {
			listener.onSubmissionDataFileUploaded(renderer.getForm().getRenderer().submissionLinkHiddenField.getValue());
		}		
	}

}
