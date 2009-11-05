/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class UploadSubmissionDataFileWidgetDefaultRenderer implements UploadSubmissionDataFileWidgetModel.Listener {

	
	//Expose view elements for testing purposes.
	final Button saveUploadSubmissionDataFileUI = new Button("Upload File", new SaveUploadClickHandler());
	final Button cancelUploadSubmissionDataFileUI = new Button("Cancel", new CancelUploadClickHandler());
	

	private Boolean isFormComplete = false;
	final private Panel canvas = new FlowPanel();
	private UploadSubmissionDataFileWidgetController controller;
	private FlowPanel savingPanel;
	private FlowPanel mainPanel;
	private FlowPanel buttonsPanel;
	private UploadSubmissionDataFileForm form;
	

	public UploadSubmissionDataFileWidgetDefaultRenderer(UploadSubmissionDataFileWidgetController controller) {
		
		this.controller = controller;
		
		//initialise view
		render();
	}

	public void render() {
		
		canvas.clear();

		savingPanel = new FlowPanel();
		savingPanel.setVisible(false);
		savingPanel.add(new InlineLabel("saving..."));
		canvas.add(savingPanel);
		
		mainPanel = new FlowPanel();
		mainPanel.setVisible(false);
		canvas.add(mainPanel);
		
		form = new UploadSubmissionDataFileForm(); 
		form.render();
		mainPanel.add(form);
		
		initButtonsPanel();
		mainPanel.add(buttonsPanel);
		
		
		
	}
	private void initButtonsPanel() {

		buttonsPanel = new FlowPanel();
		buttonsPanel.add(saveUploadSubmissionDataFileUI);
		buttonsPanel.add(cancelUploadSubmissionDataFileUI);
		buttonsPanel.addStyleName(CommonStyles.UPLOADSUBMISSIONDATAFILE_ACTIONS);

	}
			

	class SaveUploadClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			
			//TODO handle validation			
			
			FormPanel htmlFormPanel = form.getRenderer().htmlFormPanel;
			
			htmlFormPanel.submit();
			htmlFormPanel.addSubmitCompleteHandler(controller.getFormSubmitCompleteHandler());
			
		}
		
	}

	class CancelUploadClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			controller.cancelUploadSubmissionDataFile();
		}
				
	}
	
	
	Panel getCanvas() {
		return canvas;
	}

	public void onStatusChanged(int before, int after) {

		
		if (after == UploadSubmissionDataFileWidgetModel.STATUS_READY) {

			savingPanel.setVisible(false);
			mainPanel.setVisible(true);
			
		} else if (after == UploadSubmissionDataFileWidgetModel.STATUS_CREATE_PENDING) {
			
			mainPanel.setVisible(false);
			savingPanel.setVisible(true);
			
		} else if (after == UploadSubmissionDataFileWidgetModel.STATUS_CREATE_SUCCESS) {

			// TODO anything?
			
		} else if (after == UploadSubmissionDataFileWidgetModel.STATUS_CREATE_ERROR) {

			// TODO anything?
			
		} else if (after == UploadSubmissionDataFileWidgetModel.STATUS_CANCELLED) {

			// TODO anything?
			
		} else {
			throw new Error("Unhandled state transistion");
		}

	}

	public UploadSubmissionDataFileForm getForm() {
		return form;
	}
	
}
