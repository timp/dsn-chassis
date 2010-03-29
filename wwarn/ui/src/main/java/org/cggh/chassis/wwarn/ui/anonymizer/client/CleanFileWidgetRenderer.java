/**
 * 
 */
package org.cggh.chassis.wwarn.ui.anonymizer.client;




import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.miniatom.client.ext.Chassis;
import org.cggh.chassis.generic.miniatom.client.ext.ChassisHelper;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.PropertyChangeEvent;
import org.cggh.chassis.generic.widget.client.PropertyChangeHandler;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeHandler;
import org.cggh.chassis.wwarn.ui.common.client.Config;
import org.cggh.chassis.wwarn.ui.common.client.RenderUtils;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.xml.client.Element;



/**
 * @author lee
 *
 */
public class CleanFileWidgetRenderer extends ChassisWidgetRenderer<CleanFileWidgetModel> {

	private static final Log log = LogFactory.getLog(CleanFileWidgetRenderer.class);
	

	public static final String FIELD_FILE = "file";
	public static final String FIELD_SUMMARY = "summary";
	public static final String FIELD_FILENAME = "filename";
	public static final String FIELD_FILETOBECLEANEDID = "filetobecleanedid";
	public static final String FIELD_FILETOBECLEANEDTYPE = "type";
	public static final String FIELD_FILETOBECLEANEDOTHERTYPE = "othertype";
	
	private CleanFileWidgetController controller;
	
	public CleanFileWidgetRenderer(CleanFileWidget owner) {
	}
	
	
	@UiTemplate("CleanFileWidget.ui.xml")
	interface MyUiBinder extends UiBinder<HTMLPanel, CleanFileWidgetRenderer> {}
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField HTMLPanel errorPanel;
		@UiField Label errorLabel; // in errorPanel
	@UiField HTMLPanel pendingPanel;
		@UiField Label pendingLabel; // in errorPanel
	@UiField HTMLPanel cleanFilePanel;
		
	@UiField Hidden fileToBeCleanedID;
	@UiField Hidden fileToBeCleanedType;
	@UiField Hidden fileToBeCleanedOtherType;
	
	@UiField FileUpload fileInput;
	@UiField FlowPanel fileToBeCleanedTableContainer;
	
	@UiField FormPanel cleanFileFormPanel;
	
	@UiField TextArea cleanNotesInput;
	@UiField Button cleanFileFormSubmitButton;
	@UiField Button cancelCleanFileButton;
	
	@Override
	protected void renderUI() {

		HTMLPanel ui = uiBinder.createAndBindUi(this);
		
		this.canvas.clear();
		this.canvas.add(ui);
		
		
		renderCleanFileFormPanel();		
		
	}

	private void renderCleanFileFormPanel() {

		log.enter("renderFormPanel");
		
		cleanFileFormPanel.setAction(Config.get(Config.FORMHANDLER_ANONYMIZEDFILEUPLOAD_URL));
		cleanFileFormPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		cleanFileFormPanel.setMethod(FormPanel.METHOD_POST);
		
		fileInput.setName(FIELD_FILE);
		
		fileToBeCleanedID.setName(FIELD_FILETOBECLEANEDID);
		fileToBeCleanedType.setName(FIELD_FILETOBECLEANEDTYPE);
		fileToBeCleanedOtherType.setName(FIELD_FILETOBECLEANEDOTHERTYPE);
		
		cleanNotesInput.setName(FIELD_SUMMARY);
		
		log.leave();
	}	



	@Override
	protected void syncUI() {
		
		log.enter("syncUI");
		
		syncUIWithStatus(model.getStatus());
		
		log.leave();
	}	
	

	private void syncUIWithStatus(Status status) {

		log.enter("syncUIWithStatus");

		log.debug("status: " + status);
		
		
		// Hide and disabled everything at this UI level (top level) first, then show as required.
		errorPanel.setVisible(false);
		errorLabel.setVisible(false); //in errorPanel
		pendingPanel.setVisible(false);
		pendingLabel.setVisible(false); //in pendingPanel
		cleanFilePanel.setVisible(false);
			fileToBeCleanedTableContainer.setVisible(false); // in cleanFilePanel
//			cleanNotesInput.setEnabled(false); // in cleanFilePanel
			cleanFileFormSubmitButton.setEnabled(false); // in cleanFilePanel
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {

			syncUIWithFileToBeCleanedEntryElement(model.fileToBeCleanedEntryElement.get());

		}

		else if (status instanceof AsyncWidgetModel.ReadyStatus || status instanceof CleanFileWidgetModel.FileToBeCleanedEntryElementRetrievedStatus) {
			
			fileToBeCleanedTableContainer.setVisible(true);
			
			// Reset the fileInput (there is no specific GWT method). Form Reset also clears the notesInput.
			cleanFileFormPanel.reset();
			//cleanNotesInput.setText(null);
			
			cleanNotesInput.setEnabled(true);
			
			cleanFilePanel.setVisible(true);
			
		}
		
		else if (status instanceof CleanFileWidgetModel.RetrieveFileToBeCleanedEntryElementPendingStatus) {

			pendingLabel.setText("Searching for specified file...");	// TODO: i18n
			pendingLabel.setVisible(true);
			pendingPanel.setVisible(true);
		}
		
		else if (status instanceof CleanFileWidgetModel.FileToBeCleanedEntryElementNotFoundStatus) {
			
			log.error("FileToBeCleanedEntryElementNotFoundStatus");
			errorLabel.setText("Could not find the entry corresponding to the media id in memory.");	
			errorLabel.setVisible(true);	
		}
		
		else if (status instanceof CleanFileWidgetModel.CleanFileFormSubmissionPendingStatus) {
			
			pendingLabel.setText("Submitting form...");	// TODO: i18n
			pendingLabel.setVisible(true);
			pendingPanel.setVisible(true);
		}

		else if (status instanceof CleanFileWidgetModel.CleanFileFormSubmissionMalformedResultsStatus) {
			
			log.error("CleanFileWidgetModel.CleanFileFormSubmissionMalformedReturnStatus");
			errorLabel.setText("There was an error parsing the results of the form submission, i.e. a bad response from the server.");	// TODO: i18n
			errorLabel.setVisible(true);
		}		
		
		else if (status instanceof AsyncWidgetModel.ErrorStatus) {
			
			log.error("AsyncWidgetModel.ErrorStatus");
			errorLabel.setText("AsyncWidgetModel.ErrorStatus");	
			errorLabel.setVisible(true);
		}
		
		else {
			log.error("Unhandled AsyncWidgetModel status");
			errorLabel.setText("Unhandled AsyncWidgetModel status");	
			errorLabel.setVisible(true);
		}
		
		// An error may have occurred in a different syncUI process.
		if (!errorLabel.equals("")) {
			errorPanel.setVisible(true);
		}
		
		log.leave();
	}
	
	protected void syncUIWithFileToBeCleanedEntryElement(Element fileToBeCleanedEntryElement) {
		
		log.enter("syncUIWithFileToBeCleanedEntryElement");
		
		
		errorPanel.setVisible(false);
		errorLabel.setVisible(false); // in errorPanel
		fileToBeCleanedTableContainer.clear();
		fileToBeCleanedTableContainer.setVisible(false);
		
		if (fileToBeCleanedEntryElement != null) {
			
			// There needs to be an ID for the fileToBeCleaned in the form, which gets submitted.
			log.debug("setting the fileToBeCleaned in the hidden form field...");
			fileToBeCleanedID.setValue(AtomHelper.getId(fileToBeCleanedEntryElement));
			
			// Submitting the type and other type here also saves a get request on the server later.
			log.debug("setting the fileToBeCleanedType in the hidden form field...");
			fileToBeCleanedType.setValue(AtomHelper.getCategories(fileToBeCleanedEntryElement).get(0).getAttribute(Chassis.ATTRIBUTE_TERM));
			
			log.debug("setting the fileToBeCleanedOtherType in the hidden form field...");
			fileToBeCleanedOtherType.setValue(AtomHelper.getCategories(fileToBeCleanedEntryElement).get(0).getAttribute(Chassis.ATTRIBUTE_LABEL));
			
			
			log.debug("populating fileToBeCleanedTable....");
			
			FlexTable fileToBeCleanedTable = renderFileToBeCleanedTable(fileToBeCleanedEntryElement);
			fileToBeCleanedTableContainer.add(fileToBeCleanedTable);
			
			model.setStatus(AsyncWidgetModel.STATUS_READY);
			
		} else {
			// This may be a transient error, because a file has not been selected yet, but the widget has loaded.
			errorLabel.setText("fileToBeCleanedEntryElement was null");
			errorLabel.setVisible(true);
		}
		
		// An error may have occurred in a different syncUI process.
		if (!errorLabel.equals("")) {
			errorPanel.setVisible(true);
		}
		
		log.leave();
	}

	
	private FlexTable renderFileToBeCleanedTable(Element entry) {
		
		List<List<Widget>> rows = new ArrayList<List<Widget>>();
		
		String title = AtomHelper.getTitle(entry);

		String submissionPublishedDate = ChassisHelper.getSubmissionPublishedAsDate(entry);
		
		String reviewPublishedDate = ChassisHelper.getReviewPublishedAsDate(entry);
		
		String reviewSummary = ChassisHelper.getReviewSummary(entry);
		
		if (reviewSummary != null) {
			reviewSummary = reviewSummary.replaceAll("\\n", "<br/>");
		}
		
		String url = AtomHelper.getContent(entry).getAttribute("src");
		
		Anchor downloadLink = new Anchor("download"); // i18n
		
		downloadLink.setHref(url);
		downloadLink.setTarget("_blank");


		List<Widget> fileNameRow = new ArrayList<Widget>();
		fileNameRow.add(new Label("File Name:")); // i18n
		fileNameRow.add(new Label(title));
		
		rows.add(fileNameRow);
		
		List<Widget> submissionDateRow = new ArrayList<Widget>();
		submissionDateRow.add(new Label("Submission Date:")); // i18n
		submissionDateRow.add(new Label(submissionPublishedDate)); //i18n
		
		rows.add(submissionDateRow);
		
		List<Widget> reviewDateRow = new ArrayList<Widget>();
		reviewDateRow.add(new Label("Review Date:")); // i18n
		reviewDateRow.add(new Label(reviewPublishedDate));
		
		rows.add(reviewDateRow);	
		
		List<Widget>  reviewNotesRow = new ArrayList<Widget>();
		reviewNotesRow.add(new Label("Review Notes:")); // i18n
		reviewNotesRow.add(new HTML(reviewSummary));
		
		rows.add(reviewNotesRow);	
		
		List<Widget> row = new ArrayList<Widget>();
		row.add(new Label("Actions:")); // i18n
		row.add(downloadLink);	
		
		rows.add(row);
		
		return RenderUtils.renderResultItemTable(rows);
	}	
	

	@Override
	public void registerHandlersForModelChanges() {
		
		model.addStatusChangeHandler(new StatusChangeHandler() {
			public void onStatusChanged(StatusChangeEvent e) {
				syncUIWithStatus(e.getAfter());
			}
		});

		model.fileToBeCleanedEntryElement.addChangeHandler(new PropertyChangeHandler<Element>() {
			public void onChange(PropertyChangeEvent<Element> e) {
				syncUIWithFileToBeCleanedEntryElement(e.getAfter());
			}
		});
		

		
		
		
		
	}

	@Override
	public void registerHandlersForChildWidgetEvents() {
		
		ChangeHandler fileInputChangeHandler = new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent arg0) {
				
				log.enter("onChange");
				
				String value = fileInput.getFilename();
				
				if (value != "") {
					cleanFileFormSubmitButton.setEnabled(true);
				}
				else {
					cleanFileFormSubmitButton.setEnabled(false);
				}
				
				log.leave();
				
			}
		};
		
		fileInput.addChangeHandler(fileInputChangeHandler);
		
	}	
	
	@UiHandler("cleanFileFormSubmitButton")
	void handleCleanFileFormSubmitButtonClick(ClickEvent e) {
		
		log.enter("handleCleanFileButtonClick");

		controller.submitCleanFileForm(cleanFileFormPanel);	
		
		log.leave();	
	}
	
		
	@UiHandler("cleanFileFormPanel")
	void handleCleanFileFormSubmitComplete(SubmitCompleteEvent e) {
		controller.handleCleanFileFormSubmitComplete(e.getResults());
	}

	
	
	@UiHandler("cancelCleanFileButton")
	void handleCancelCleanFileButtonClick(ClickEvent e) {
		
		log.enter("handleCancelCleanFileButtonClick");
		
		controller.backToStart();
		
		log.leave();
	}
	
	public void setController(CleanFileWidgetController controller) {
		this.controller = controller;
	}	
	
}
