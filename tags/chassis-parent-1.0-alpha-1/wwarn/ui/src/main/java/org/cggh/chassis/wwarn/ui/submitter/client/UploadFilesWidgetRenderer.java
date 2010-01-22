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
	@UiField Button cancelSubmissionButton;
	@UiField Button proceedButton;
	@UiField Label uploadAFileTitleLabel;
	@UiField Label uploadAnotherFileTitleLabel;
	@UiField Panel errorPanel;
	@UiField UploadFileForm uploadFileForm;
	@UiField Button uploadButton;
	@UiField Button cancelSubmissionButton2;
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
		
		log.debug("status: "+status);
		
		syncStudyPanelWithStatus(status);
		syncMainInteractionPanelWithStatus(status);
		syncErrorPanelWithStatus(status);
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {

			filesUploadedLabel.setVisible(false);
			filesPendingLabel.setVisible(false);
			fileDeletePendingLabel.setVisible(false);
			noFilesUploadedLabel.setVisible(false);
			uploadPendingPanel.setVisible(false);

		}
		
		else if (status instanceof UploadFilesWidgetModel.RetrieveStudyPendingStatus) {

		}
		
		else if (status instanceof UploadFilesWidgetModel.StudyNotFoundStatus) {

		}

		else if (status instanceof UploadFilesWidgetModel.RetrieveUploadedFilesPendingStatus) {

			
			filesUploadedLabel.setVisible(true);
			filesPendingLabel.setVisible(true);
			noFilesUploadedLabel.setVisible(false);
			fileDeletePendingLabel.setVisible(false);
			
			filesTableContainer.setVisible(false);
			
			mainActionsPanel.setVisible(false);

			uploadFormPanel.setVisible(false);
			uploadPendingPanel.setVisible(false);

		}
		
		else if (status instanceof UploadFilesWidgetModel.ReadyForInteractionStatus) {

			filesPendingLabel.setVisible(false);
			fileDeletePendingLabel.setVisible(false);

			filesTableContainer.setVisible(true);

//			mainActionsPanel.setVisible(true);

			uploadPendingPanel.setVisible(false);
			uploadFormPanel.setVisible(true);
			
		}
		
		else if (status instanceof UploadFilesWidgetModel.FileUploadPendingStatus) {

			mainActionsPanel.setVisible(false);

			uploadFormPanel.setVisible(false);
			uploadPendingPanel.setVisible(true);

		}

		else if (status instanceof UploadFilesWidgetModel.FileDeletePendingStatus) {

			mainActionsPanel.setVisible(false);
			filesTableContainer.setVisible(false);
			uploadFormPanel.setVisible(false);
			fileDeletePendingLabel.setVisible(true);

		}

		else if (status instanceof ErrorStatus) {
			
		}

		log.leave();
	}

	
	

	private void syncErrorPanelWithStatus(Status status) {

		if (status instanceof ErrorStatus) {
			
			errorPanel.setVisible(true);
			
		}
		else {

			errorPanel.setVisible(false);

		}
		
	}




	private void syncMainInteractionPanelWithStatus(Status status) {
		// TODO Auto-generated method stub
		
		if (
				status instanceof UploadFilesWidgetModel.RetrieveUploadedFilesPendingStatus || 
				status instanceof UploadFilesWidgetModel.ReadyForInteractionStatus || 
				status instanceof UploadFilesWidgetModel.FileUploadPendingStatus 
				
		) {

			mainInteractionPanel.setVisible(true);

		}
		else {

			mainInteractionPanel.setVisible(false);

		}
		
		
	}




	private void syncStudyPanelWithStatus(Status status) {
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {

			studyPanel.setVisible(true);
			studyNotSelectedLabel.setVisible(true);
			studyPendingLabel.setVisible(false);
			studyNotFoundLabel.setVisible(false);
			studyTitleLabel.setVisible(false);

		}
		
		else if (status instanceof UploadFilesWidgetModel.RetrieveStudyPendingStatus) {

			studyPanel.setVisible(true);
			studyNotSelectedLabel.setVisible(false);
			studyPendingLabel.setVisible(true);
			studyNotFoundLabel.setVisible(false);
			studyTitleLabel.setVisible(false);

		}
		
		else if (status instanceof UploadFilesWidgetModel.StudyNotFoundStatus) {

			studyPanel.setVisible(true);
			studyNotSelectedLabel.setVisible(false);
			studyPendingLabel.setVisible(false);
			studyNotFoundLabel.setVisible(true);
			studyTitleLabel.setVisible(false);

		}

		else if (status instanceof UploadFilesWidgetModel.RetrieveUploadedFilesPendingStatus) {

			studyPanel.setVisible(true);
			studyNotSelectedLabel.setVisible(false);
			studyPendingLabel.setVisible(false);
			studyNotFoundLabel.setVisible(false);
			studyTitleLabel.setVisible(true);

		}
		
		else if (status instanceof UploadFilesWidgetModel.ReadyForInteractionStatus) {

			studyPanel.setVisible(true);
			studyNotSelectedLabel.setVisible(false);
			studyPendingLabel.setVisible(false);
			studyNotFoundLabel.setVisible(false);
			studyTitleLabel.setVisible(true);
			
		}
		
		else if (status instanceof UploadFilesWidgetModel.FileUploadPendingStatus) {

			studyPanel.setVisible(true);
			studyNotSelectedLabel.setVisible(false);
			studyPendingLabel.setVisible(false);
			studyNotFoundLabel.setVisible(false);
			studyTitleLabel.setVisible(true);

		}
		
		else if (status instanceof ErrorStatus) {
			
			studyPanel.setVisible(false);
			
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
		
		if (uploadFeedDoc != null) {
			
			List<Element> entries = AtomHelper.getEntries(uploadFeedDoc.getDocumentElement());
			
			if (entries.size() == 0) {
				noFilesUploadedLabel.setVisible(true);
				filesUploadedLabel.setVisible(false);
				filesTableContainer.setVisible(false);
				mainActionsPanel.setVisible(false);
				uploadAFileTitleLabel.setVisible(true);
				uploadAnotherFileTitleLabel.setVisible(false);
			}
			
			else {
				noFilesUploadedLabel.setVisible(false);
				filesUploadedLabel.setVisible(true);
				filesTableContainer.setVisible(true);
				mainActionsPanel.setVisible(true);
				uploadAFileTitleLabel.setVisible(false);
				uploadAnotherFileTitleLabel.setVisible(true);
				
				FlexTable uploadsTable = renderUploadsTable(entries);
				filesTableContainer.add(uploadsTable);
			}

		}
		
	}




	private FlexTable renderUploadsTable(List<Element> entries) {
		
		List<Widget[]> rows = new ArrayList<Widget[]>();

		Widget[] headerRow = {
			new Label("File Name"), // TODO i18n
			new Label("Type"),  // TODO i18n
			new Label("Summary"), // TODO i18n
			new Label("Uploaded"),  // TODO i18n
			new Label("Actions") // TODO i18n
		};
		rows.add(headerRow);
		
		for (Element entry : entries) {
			
			String title = AtomHelper.getTitle(entry);
			final String url = AtomHelper.getHrefAttr(AtomHelper.getEditLink(entry));
			
			Element categoryElement = AtomHelper.getFirstCategory(entry, Chassis.SCHEME_FILETYPES);
			String type = getTypeLabel(categoryElement);
			
			String summary = AtomHelper.getSummary(entry);
			String created = AtomHelper.getPublished(entry);
			
			Button deleteButton = new Button("delete"); // TODO i18n
			deleteButton.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent arg0) {
					controller.deleteFile(url);
				}

			});
			
			FlowPanel actionsPanel = new FlowPanel();
			actionsPanel.add(deleteButton);
			
			Widget[] row = {
				new Label(title),	
				new Label(type),	
				new Label(summary),	
				new Label(created),	
				actionsPanel	
			};
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
//		syncUIWithStudyEntryElement(model.getStudyEntryElement());
		syncUIWithStudyEntryElement(model.studyEntryElement.get());
//		syncUIWithUploadFeedDoc(model.getUploadFeedDoc());
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
