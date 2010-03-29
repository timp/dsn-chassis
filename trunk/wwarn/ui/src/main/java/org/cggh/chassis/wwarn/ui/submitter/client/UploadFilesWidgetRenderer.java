/**
 * 
 */
package org.cggh.chassis.wwarn.ui.submitter.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.miniatom.client.ext.Chassis;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.PropertyChangeEvent;
import org.cggh.chassis.generic.widget.client.PropertyChangeHandler;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.ErrorStatus;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeHandler;
import org.cggh.chassis.wwarn.ui.common.client.RenderUtils;
//import org.cggh.chassis.wwarn.ui.submitter.client.UploadFilesWidgetModel.StudyEntryChangeEvent;
//import org.cggh.chassis.wwarn.ui.submitter.client.UploadFilesWidgetModel.StudyEntryChangeHandler;
//import org.cggh.chassis.wwarn.ui.submitter.client.UploadFilesWidgetModel.UploadFeedChangeEvent;
//import org.cggh.chassis.wwarn.ui.submitter.client.UploadFilesWidgetModel.UploadFeedChangeHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class UploadFilesWidgetRenderer extends ChassisWidgetRenderer<UploadFilesWidgetModel> {

	
	
	private Log log = LogFactory.getLog(UploadFilesWidgetRenderer.class);
	
	
	@UiTemplate("UploadFilesWidget.ui.xml")
	interface MyUiBinder extends UiBinder<HTMLPanel, UploadFilesWidgetRenderer> {}
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	
	
	@UiField Panel studyPanel;
	@UiField Label studyNotSelectedLabel;
	@UiField Label studyPendingLabel;
	@UiField Label studyNotFoundLabel;
	@UiField Label studyTitleLabel;
	@UiField Anchor stepBackLink;
	@UiField Panel mainInteractionPanel;
	@UiField Label filesUploadedLabel;
	@UiField Label filesPendingLabel;
	@UiField Label fileDeletePendingLabel;
	@UiField Label noFilesUploadedLabel;
	@UiField Panel filesTableContainer;
	@UiField Panel mainActionsPanel;
	@UiField Button proceedButton;
	@UiField Label uploadAFileTitleLabel;
	@UiField Label uploadAnotherFileTitleLabel;
	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;
	@UiField UploadFileForm uploadFileForm;
	@UiField Button uploadButton;
	@UiField Panel uploadFormPanel;
	@UiField Panel uploadPendingPanel;
	




	private UploadFilesWidgetController controller;
	
	
	
	
	public UploadFilesWidgetRenderer() {
	}
	
	
	
	
	@Override
	public void setCanvas(Panel canvas) {
		super.setCanvas(canvas);
		canvas.add(uiBinder.createAndBindUi(this));
	}
	
	
	
	
	@Override
	protected void registerHandlersForModelChanges() {
		
		model.addStatusChangeHandler(new StatusChangeHandler() {
			public void onStatusChanged(StatusChangeEvent e) {
				syncUIWithStatus(e.getAfter());
			}
		});
		
		model.studyEntryElement.addChangeHandler(new PropertyChangeHandler<Element>() {
			public void onChange(PropertyChangeEvent<Element> e) {
				syncUIWithStudyEntryElement(e.getAfter());
			}
		});
		
		model.uploadFeedDoc.addChangeHandler(new PropertyChangeHandler<Document>() {
			public void onChange(PropertyChangeEvent<Document> e) {
				syncUIWithUploadFeedDoc(e.getAfter());
			}
		});
		
	}
	
	
	
	

	/**
	 * @param status
	 */
	protected void syncUIWithStatus(Status status) {
		
		log.enter("syncUIWithStatus");
		
		log.debug("status: " + status);

		syncStudyPanelWithStatus(status);
		syncMainInteractionPanelWithStatus(status);
		syncUploadFileFormWithStatus(status);
		syncErrorPanelWithStatus(status);

		// Hide everything (that is made visible here) first, then show as required.
		filesUploadedLabel.setVisible(false);
		filesPendingLabel.setVisible(false);		
		filesTableContainer.setVisible(false);
		uploadFormPanel.setVisible(false);				
		uploadPendingPanel.setVisible(false);
		fileDeletePendingLabel.setVisible(false);		
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {
			
		}
		
		else if (status instanceof UploadFilesWidgetModel.RetrieveStudyPendingStatus) {
			
		}
		
		else if (status instanceof UploadFilesWidgetModel.StudyNotFoundStatus) {
			
		}

		else if (status instanceof UploadFilesWidgetModel.RetrieveUploadedFilesPendingStatus) {

			
			filesUploadedLabel.setVisible(true);
			filesPendingLabel.setVisible(true);
		}
		
		else if (status instanceof UploadFilesWidgetModel.ReadyForInteractionStatus) {

			filesTableContainer.setVisible(true);
			uploadFormPanel.setVisible(true);
		}
		
		else if (status instanceof UploadFilesWidgetModel.FileUploadPendingStatus) {

			uploadPendingPanel.setVisible(true);
		}

		else if (status instanceof UploadFilesWidgetModel.FileDeletePendingStatus) {

			fileDeletePendingLabel.setVisible(true);
		}

		else if (status instanceof ErrorStatus) {
				
		}

		log.leave();
	}

	
	





	private void syncErrorPanelWithStatus(Status status) {

		// Hide everything (that is made visible here) first, then show as required.
		errorPanel.setVisible(false);
		
		if (status instanceof ErrorStatus) {
			
			errorPanel.setVisible(true);
			
		}
		
	}




	private void syncMainInteractionPanelWithStatus(Status status) {

		// Hide everything (that is made visible here) first, then show as required.
		mainInteractionPanel.setVisible(false);
		
		if (
				status instanceof UploadFilesWidgetModel.RetrieveUploadedFilesPendingStatus || 
				status instanceof UploadFilesWidgetModel.ReadyForInteractionStatus || 
				status instanceof UploadFilesWidgetModel.FileUploadPendingStatus 
				
		) {

			mainInteractionPanel.setVisible(true);

		}
		
		
	}




	private void syncStudyPanelWithStatus(Status status) {
		
		// Hide everything (that is made visible here) first, then show as required.
		studyPanel.setVisible(false);
		studyNotSelectedLabel.setVisible(false);		
		studyPendingLabel.setVisible(false);
		studyNotFoundLabel.setVisible(false);
		studyTitleLabel.setVisible(false);
		errorPanel.setVisible(false);
		// The visibility of the "No Files Uploaded" message needs to be controlled based on status as well as the UploadFeedDoc, 
		// but status has the override, so it goes here.
		noFilesUploadedLabel.setVisible(false);
		
		Document uploadFeedDoc = model.uploadFeedDoc.get();
		
		Boolean hasNoFilesUploaded = false;
		
		if (uploadFeedDoc != null) {
			
			List<Element> entries = AtomHelper.getEntries(uploadFeedDoc.getDocumentElement());
			
			if (entries.size() == 0) {
				hasNoFilesUploaded = true;
			}

		}	
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {

			studyPanel.setVisible(true);
			studyNotSelectedLabel.setVisible(true);
			
		}
		
		else if (status instanceof UploadFilesWidgetModel.RetrieveStudyPendingStatus) {

			studyPanel.setVisible(true);
			studyPendingLabel.setVisible(true);
			
		}
		
		else if (status instanceof UploadFilesWidgetModel.StudyNotFoundStatus) {

			studyPanel.setVisible(true);
			studyNotFoundLabel.setVisible(true);
			
		}

		else if (status instanceof UploadFilesWidgetModel.RetrieveUploadedFilesPendingStatus) {

			studyPanel.setVisible(true);
			studyTitleLabel.setVisible(true);
			
		}
		
		else if (status instanceof UploadFilesWidgetModel.ReadyForInteractionStatus) {

			studyPanel.setVisible(true);
			studyTitleLabel.setVisible(true);
			noFilesUploadedLabel.setVisible(hasNoFilesUploaded);
		}
		
		else if (status instanceof UploadFilesWidgetModel.FileUploadPendingStatus) {

			studyPanel.setVisible(true);
			studyTitleLabel.setVisible(true);
		}
		
		else if (status instanceof ErrorStatus) {
			
			errorMessage.clear();
			errorMessage.add(new HTML(model.getErrorMessage()));
			errorPanel.setVisible(true);
		}
	}


	private void syncUploadFileFormWithStatus(Status status) {
		
		// Hide everything (that is made visible here) first, then show as required.
		uploadFileForm.setVisible(false);

		if (status instanceof AsyncWidgetModel.InitialStatus) {


		}
		
		else if (status instanceof UploadFilesWidgetModel.RetrieveStudyPendingStatus) {

			
		}
		
		else if (status instanceof UploadFilesWidgetModel.StudyNotFoundStatus) {

			
		}

		else if (status instanceof UploadFilesWidgetModel.RetrieveUploadedFilesPendingStatus) {

			
		}
		
		else if (status instanceof UploadFilesWidgetModel.ReadyForInteractionStatus) {

			uploadFileForm.formPanel.reset();
			uploadFileForm.setVisible(true);
			
			// Resetting the form panel clears the summaryInput and the fileInput.
			//uploadFileForm.summaryInput.setText(null);
		}
		
		else if (status instanceof UploadFilesWidgetModel.FileUploadPendingStatus) {

		}
		
		else if (status instanceof ErrorStatus) {
			
			errorMessage.clear();
			errorMessage.add(new HTML(model.getErrorMessage()));
			errorPanel.setVisible(true);
		}
		
	}


	protected void syncUIWithStudyEntryElement(Element studyEntryElement) {
		
		String studyTitle = "";
		
		if (studyEntryElement != null) {
			
			studyTitle = AtomHelper.getTitle(studyEntryElement);
		}
		
		studyTitleLabel.setText(studyTitle);
	}


	

	protected void syncUIWithUploadFeedDoc(Document uploadFeedDoc) {

		filesTableContainer.clear();
		
		// Hide everything (that is made visible here) first, then show as required.
		noFilesUploadedLabel.setVisible(false);
		filesUploadedLabel.setVisible(false);
		filesTableContainer.setVisible(false);
		mainActionsPanel.setVisible(false);
		uploadAFileTitleLabel.setVisible(false);
		uploadAnotherFileTitleLabel.setVisible(false);
		
		if (uploadFeedDoc != null) {
			
			List<Element> entries = AtomHelper.getEntries(uploadFeedDoc.getDocumentElement());
			
			if (entries.size() == 0) {
				noFilesUploadedLabel.setVisible(true);
				uploadAFileTitleLabel.setVisible(true);
			}
			
			else {
				filesUploadedLabel.setVisible(true);
				filesTableContainer.setVisible(true);
				mainActionsPanel.setVisible(true);
				uploadAnotherFileTitleLabel.setVisible(true);
				
				FlexTable uploadsTable = renderUploadsTable(entries);
				filesTableContainer.add(uploadsTable);
			}

		}
		
	}




	private FlexTable renderUploadsTable(List<Element> entries) {
		
		List<List<Widget>> rows = new ArrayList<List<Widget>>();

		List<Widget> headerRow = new ArrayList<Widget>();
		headerRow.add(new Label("File Name")); // i18n
		headerRow.add(new Label("Type"));      // i18n
		headerRow.add(new Label("Size"));      // i18n
		headerRow.add(new Label("Uploaded"));  // i18n
		headerRow.add(new Label("Actions"));    // i18n
		
		rows.add(headerRow);
		
		for (Element entry : entries) {
			
			String title = AtomHelper.getTitle(entry);
			final String url = AtomHelper.getHrefAttr(AtomHelper.getEditLink(entry));
			
			Element categoryElement = AtomHelper.getFirstCategory(entry, Chassis.SCHEME_FILETYPES);
			String type = getTypeLabel(categoryElement);
			
			String fileSizeAsString = AtomHelper.getMediaResourceSize(entry);
			String created = AtomHelper.getPublishedAsDate(entry);
			
			Button deleteButton = new Button("delete"); // TODO i18n
			deleteButton.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent arg0) {
					controller.deleteFile(url);
				}

			});
			
			FlowPanel actionsPanel = new FlowPanel();
			actionsPanel.add(deleteButton);
			
			List<Widget> row = new ArrayList<Widget>();
			row.add(new Label(title));	
			row.add(new Label(type));	
			row.add(new Label(fileSizeAsString));	
			row.add(new Label(created));
			row.add(actionsPanel);
			
			rows.add(row);
			
		}
		
		return RenderUtils.renderResultsTable(rows);
	}

	
	
	private static String getTypeLabel(Element categoryElement) {

		String term = AtomHelper.getTermAttr(categoryElement);

		if (term == null) {
			return "";
		}
		else if (term.equals(Chassis.TERM_DATAFILE)) {
			return "Data File"; // TODO i18n
		}
		else if (term.equals(Chassis.TERM_DATADICTIONARY)) {
			return "Data Dictionary"; // TODO i18n
		}
		else if (term.equals(Chassis.TERM_PROTOCOL)) {
			return "Protocol"; // TODO i18n
		}
		else if (term.equals(Chassis.TERM_OTHER)) {
			String labelAttrValue = AtomHelper.getLabelAttr(categoryElement);
			return (labelAttrValue != null) ? labelAttrValue : "";
		}
		else {
			return "";
		}

	}



	@Override
	protected void syncUI() {
		syncUIWithStatus(model.getStatus());
		syncUIWithStudyEntryElement(model.studyEntryElement.get());
		syncUIWithUploadFeedDoc(model.uploadFeedDoc.get());
	}
	
	
	

	@UiHandler("stepBackLink")
	void handleStepBackLinkClick(ClickEvent e) {
		controller.stepBack();
	}
	
	
	
	
	@UiHandler("proceedButton")
	void handleProceedButtonClick(ClickEvent e) {
		controller.proceed();
	}
	
	
	
	
	@UiHandler("uploadButton")
	void handleUploadFormSubmitButtonClick(ClickEvent e) {
		controller.submitUploadFileForm(uploadFileForm);
	}
	
	
	
	@UiHandler("uploadFileForm")
	void handleFileUploadFormSubmitComplete(SubmitCompleteEvent e) {
		controller.handleUploadFileFormSubmitComplete(e.getResults());
	}




	public void setController(UploadFilesWidgetController controller) {
		this.controller = controller;
	}

	
	
}
