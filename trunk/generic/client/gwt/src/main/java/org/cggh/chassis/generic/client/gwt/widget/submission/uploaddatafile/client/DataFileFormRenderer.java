/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client;

import org.cggh.chassis.generic.atom.chassis.base.constants.ChassisConstants;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.client.gwt.common.client.CSS;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
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
public class DataFileFormRenderer {

	private Panel canvas;
	private AtomEntry model;
	final FormPanel htmlFormPanel = new FormPanel();
	final FileUpload fileUI = new FileUpload();
	final TextBoxBase commentUI = new TextArea();
	final Hidden submissionLinkHiddenField = new Hidden(ChassisConstants.FIELD_SUBMISSION);
	final Hidden authorHiddenField = new Hidden(ChassisConstants.FIELD_AUTHOREMAIL);

	public DataFileFormRenderer(Panel canvas, AtomEntry model) {
		this.canvas = canvas;
		this.model = model;
	}
	
	public void bind(AtomEntry model) {
		this.model = model;
	}
	
	public void render() {
		
		//prepare canvas
		canvas.clear();
		canvas.addStyleName(CSS.UPLOADSUBMISSIONDATAFILE_BASE);
		canvas.add(new HTML("<h2>Upload Submission Data File</h2>"));		
		canvas.add(new HTML("<p>Use this form to upload a data file for this submission.</p>"));
		
		//prepare htmlFormPanel
		htmlFormPanel.setAction(ConfigurationBean.getDataFileUploadServiceURL());
		htmlFormPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		htmlFormPanel.setMethod(FormPanel.METHOD_POST);
		canvas.add(htmlFormPanel);
						
		/* create form UI */
		
		FlowPanel submissionDataFileForm = new FlowPanel();
		htmlFormPanel.add(submissionDataFileForm);
		
		//add hidden fields to be submitted
		submissionDataFileForm.add(authorHiddenField);
		submissionDataFileForm.add(submissionLinkHiddenField);
		
		submissionDataFileForm.add(new HTML("<h3>Data File and Comment</h3>"));
		
		//add fileBrowser section
		FlowPanel fileBrowserSection = new FlowPanel();
		fileBrowserSection.addStyleName(CSS.COMMON_QUESTION);
		InlineLabel fileBrowserLabel = new InlineLabel("Please select the file you wish to upload:");
		fileBrowserSection.add(fileBrowserLabel);
		fileUI.setName(ChassisConstants.FIELD_DATAFILE);
		fileUI.addStyleName(CSS.UPLOADSUBMISSIONDATAFILE_FILEINPUT);
		fileBrowserSection.add(fileUI);
		submissionDataFileForm.add(fileBrowserSection);
		
		//Comment section
		FlowPanel commentSection = new FlowPanel();
		commentSection.addStyleName(CSS.COMMON_QUESTION);
		Label commentLabel = new Label("Please add a comment about the file...");
		commentSection.add(commentLabel);
		commentUI.setName(ChassisConstants.FIELD_SUMMARY);
		commentUI.addStyleName(CSS.UPLOADSUBMISSIONDATAFILE_COMMENTINPUT);
		commentUI.addValueChangeHandler(new CommentValueChangeHandler());
		commentSection.add(commentUI);
		submissionDataFileForm.add(commentSection);
				
	}	
	
	//Workaround method because GWT FileUpload widget does not fire events
	//call before submitting the form.
	void validateFileUI() {
		
		model.setTitle(fileUI.getFilename());
		
	}
	
	
	
	private class CommentValueChangeHandler implements ValueChangeHandler<String> {

		public void onValueChange(ValueChangeEvent<String> title) {
			model.setSummary(title.getValue());			
		}
	}
	

}
