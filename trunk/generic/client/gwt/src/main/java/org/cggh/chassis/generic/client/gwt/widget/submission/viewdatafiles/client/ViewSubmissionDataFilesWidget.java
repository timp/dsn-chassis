/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.viewdatafiles.client;

import com.google.gwt.user.client.ui.Composite;

/**
 * @author raok
 *
 */
public class ViewSubmissionDataFilesWidget extends Composite {
	
	final private ViewSubmissionDataFilesWidgetModel model;
	final private ViewSubmissionDataFilesWidgetController controller;
	private ViewSubmissionDataFilesWidgetDefaultRenderer renderer;

	public ViewSubmissionDataFilesWidget() {
		
		model = new ViewSubmissionDataFilesWidgetModel();
		
		controller = new ViewSubmissionDataFilesWidgetController(model, this);
		
		renderer = new ViewSubmissionDataFilesWidgetDefaultRenderer(controller);
		
		// register renderer as listener to model
		model.addListener(renderer);

		this.initWidget(this.renderer.getCanvas());
		
	}
	
	public void loadDataFilesBySubmissionLink(String submissionLink) {
		
		controller.loadDataFilesBySubmissionLink(submissionLink);
	}
	
	
}
