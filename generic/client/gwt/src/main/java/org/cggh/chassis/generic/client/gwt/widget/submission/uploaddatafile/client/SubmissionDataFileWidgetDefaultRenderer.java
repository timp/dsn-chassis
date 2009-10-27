/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client;

import org.cggh.chassis.generic.client.gwt.common.client.CSS;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBoxBase;

/**
 * @author raok
 *
 */
public class SubmissionDataFileWidgetDefaultRenderer implements SubmissionDataFileWidgetModelListener {

	
	//Expose view elements for testing purposes.
	final FormPanel htmlFormPanel = new FormPanel();
	final FileUpload fileUI = new FileUpload();
	final TextBoxBase commentUI = new TextArea();
	final Hidden submissionLinkHiddenField = new Hidden("submission");
	final Hidden authorHiddenField = new Hidden("authoremail");
	final Button saveUploadSubmissionDataFileUI = new Button("Upload File", new SaveUploadClickHandler());
	final Button cancelUploadSubmissionDataFileUI = new Button("Cancel", new CancelUploadClickHandler());
	

	private Boolean isFormComplete = false;
	final private Panel canvas;
	private SubmissionDataFileWidgetController controller;

	public SubmissionDataFileWidgetDefaultRenderer(Panel givenCanvas, SubmissionDataFileWidgetController controller) {
		
		this.canvas = new FlowPanel();
		givenCanvas.add(canvas);
		
		this.controller = controller;
		
		//initialise view
		initCanvas();
	}

	public SubmissionDataFileWidgetDefaultRenderer(SubmissionDataFileWidgetController controller) {

		this.canvas = new FlowPanel();
		
		this.controller = controller;
		
		//initialise view
		initCanvas();
	}

	private void initCanvas() {
		
		//prepare the FormPanel for datafile submission
		htmlFormPanel.setAction(ConfigurationBean.getDataFileFeedURL());
		htmlFormPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		htmlFormPanel.setMethod(FormPanel.METHOD_POST);
		
		this.canvas.addStyleName(CSS.UPLOADSUBMISSIONDATAFILE_BASE);
		
		this.canvas.add(new HTML("<h2>Upload Submission Data File</h2>"));
		
		this.canvas.add(new HTML("<p>Use this form to upload a data file for this submission.</p>"));
		
		//create form
		FlowPanel submissionDataFileForm = new FlowPanel();
		
		//add hidden fields to be submitted
		submissionDataFileForm.add(authorHiddenField);
		submissionDataFileForm.add(submissionLinkHiddenField);
		
		submissionDataFileForm.add(new HTML("<h3>Data File and Comment</h3>"));
		
		//fileBrowser section
		FlowPanel fileBrowserSection = new FlowPanel();
		InlineLabel fileBrowserLabel = new InlineLabel("Please select the file you wish to upload:");
		fileBrowserSection.add(fileBrowserLabel);
		fileBrowserSection.add(fileUI);
		fileBrowserSection.addStyleName(CSS.COMMON_QUESTION);
		
		fileUI.addStyleName(CSS.UPLOADSUBMISSIONDATAFILE_FILEINPUT);

		submissionDataFileForm.add(fileBrowserSection);
		
		//Comment section
		FlowPanel commentSection = new FlowPanel();
		InlineLabel commentLabel = new InlineLabel("Please add a comment about the file...");
		fileBrowserSection.add(commentLabel);
		commentUI.setName("summary");
		commentUI.addValueChangeHandler(new CommentValueChangeHandler());
		fileBrowserSection.add(commentUI);
		fileBrowserSection.addStyleName(CSS.COMMON_QUESTION);
		
		fileUI.addStyleName(CSS.UPLOADSUBMISSIONDATAFILE_COMMENTINPUT);

		submissionDataFileForm.add(commentSection);
		
		//Add form actions
		FlowPanel buttonsPanel = new FlowPanel();
		buttonsPanel.addStyleName(CSS.UPLOADSUBMISSIONDATAFILE_ACTIONS);
		buttonsPanel.add(saveUploadSubmissionDataFileUI);
		buttonsPanel.add(cancelUploadSubmissionDataFileUI);
		
		submissionDataFileForm.add(buttonsPanel);
		
		//enclose in a form panel to allow submission of File
		htmlFormPanel.add(submissionDataFileForm);
		
		canvas.add(htmlFormPanel);
		
	}
	
	class CommentValueChangeHandler implements ValueChangeHandler<String> {

		public void onValueChange(ValueChangeEvent<String> arg0) {
			controller.updateComment(commentUI.getValue());			
		}
	}
		

	public void onCommentChanged(String before, String after, Boolean isValid) {
		this.commentUI.setValue(after, false);
	}

	public void onFileNameChanged(String before, String after, Boolean isValid) {
		// not interested
		
	}

	public void onStatusChanged(Integer before, Integer after) {
		// TODO Auto-generated method stub
		
	}

	public void onSubmissionLinkChanged(String before, String after) {
		this.submissionLinkHiddenField.setValue(after);
	}

	public void onSubmittedDataFileFormChanged(Boolean isValid) {
		isFormComplete = isValid;
	}

	class SaveUploadClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			if (isFormComplete) {
				htmlFormPanel.submit();
				htmlFormPanel.addSubmitCompleteHandler(controller.getFormSubmitCompleteHandler());
			} else {
				//TODO move to management widget?
				DecoratedPopupPanel errorPopUp = new DecoratedPopupPanel(true);
				errorPopUp.add(new Label("Form invalid."));
				errorPopUp.center();
				errorPopUp.show();
			}
		}
		
	}

	class CancelUploadClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			controller.cancelUploadSubmissionDataFile();
		}
				
	}

	public void onAuthorChanged(String before, String after) {
		authorHiddenField.setValue(after);
	}

	Panel getCanvas() {
		return canvas;
	}
	
}
