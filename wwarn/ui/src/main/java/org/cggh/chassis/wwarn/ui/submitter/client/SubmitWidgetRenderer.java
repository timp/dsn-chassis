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
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.ErrorStatus;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeHandler;
import org.cggh.chassis.wwarn.ui.common.client.RenderUtils;
import org.cggh.chassis.wwarn.ui.submitter.client.SubmitWidgetModel.AgreementAcceptedChangeEvent;
import org.cggh.chassis.wwarn.ui.submitter.client.SubmitWidgetModel.AgreementAcceptedChangeHandler;
import org.cggh.chassis.wwarn.ui.submitter.client.SubmitWidgetModel.StudyEntryChangeEvent;
import org.cggh.chassis.wwarn.ui.submitter.client.SubmitWidgetModel.StudyEntryChangeHandler;
import org.cggh.chassis.wwarn.ui.submitter.client.SubmitWidgetModel.UploadFeedChangeEvent;
import org.cggh.chassis.wwarn.ui.submitter.client.SubmitWidgetModel.UploadFeedChangeHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class SubmitWidgetRenderer extends ChassisWidgetRenderer<SubmitWidgetModel> {

	
	
	private Log log = LogFactory.getLog(SubmitWidgetRenderer.class);
	
	
	@UiTemplate("SubmitWidget.ui.xml")
	interface MyUiBinder extends UiBinder<HTMLPanel, SubmitWidgetRenderer> {}
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	
	
	@UiField Panel studyPanel;
	@UiField Label studyNotSelectedLabel;
	@UiField Label studyPendingLabel;
	@UiField Label studyNotFoundLabel;
	@UiField Label studyTitleLabel;
	@UiField Panel backToSelectStudyPanel;
	@UiField Anchor backToSelectStudyLink;
	
	@UiField Panel mainInteractionPanel;
	
	@UiField Panel filesPanel;
	
	@UiField Label filesUploadedLabel;
	@UiField Label noFilesUploadedLabel;
	@UiField Label filesPendingLabel;

	@UiField Panel filesTableContainer;

	@UiField Panel backToUploadFilesPanel;
	@UiField Anchor backToUploadFilesLink;
	
	@UiField Panel submissionPanel;
	@UiField Panel mainActionsPanel;

	@UiField RadioButton notAcceptedButton;
	@UiField RadioButton acceptedButton;
	@UiField Button cancelSubmissionButton;
	@UiField Button proceedButton;
	
	@UiField Panel submissionPendingPanel;
	
	@UiField Panel errorPanel;
	@UiField Panel errorMessage;
	




	private SubmitWidgetController controller;
	
	
	
	
	public SubmitWidgetRenderer() {
	}
	
	
	
	
	@Override
	public void setCanvas(Panel canvas) {
		super.setCanvas(canvas);
		canvas.add(uiBinder.createAndBindUi(this));
	}

	@Override
	public void renderUI() {

		log.enter("renderUI");		
		
		notAcceptedButton.setValue(true);
		acceptedButton.setValue(false);
		
		proceedButton.setEnabled(false);
		
		log.leave();
		
	}
	
	
	
	@Override
	public void registerHandlersForChildWidgetEvents() {
		
		ValueChangeHandler<Boolean> acceptButtonsChangeHandler = new ValueChangeHandler<Boolean>() {
			
			public void onValueChange(ValueChangeEvent<Boolean> e) {
				log.enter("onValueChange");
				
				boolean value = acceptedButton.getValue();
				
				if (value) {
					proceedButton.setEnabled(true);
				}
				else {
					proceedButton.setEnabled(false);
				}
				
				log.leave();
			}
		};
		
		acceptedButton.addValueChangeHandler(acceptButtonsChangeHandler);
		notAcceptedButton.addValueChangeHandler(acceptButtonsChangeHandler);
		
	}
	
	
	@Override
	protected void registerHandlersForModelChanges() {
		
		model.addStatusChangeHandler(new StatusChangeHandler() {
			public void onStatusChanged(StatusChangeEvent e) {
				syncUIWithStatus(e.getAfter());
			}
		});
		
		model.addStudyEntryChangeHandler(new StudyEntryChangeHandler() {
			public void onChange(StudyEntryChangeEvent e) {
				syncUIWithStudyEntryElement(e.getAfter());
			}
		});
		
		model.addUploadFeedChangeHandler(new UploadFeedChangeHandler() {
			public void onChange(UploadFeedChangeEvent e) {
				syncUIWithUploadFeedDoc(e.getAfter());
			}
		});
		
		model.addAgreementAcceptedChangeHandler(new AgreementAcceptedChangeHandler() {
			
			public void onChange(AgreementAcceptedChangeEvent e) {
				syncUIWithAgreementAccepted(e.getAfter());
			}
		});
		
	}
	
	
	
	

	/**
	 * @param after
	 */
	protected void syncUIWithAgreementAccepted(Boolean after) {
		log.enter("syncUIWithAgreementAccepted");
		
		// TODO Auto-generated method stub
		
		log.leave();
		
	}




	/**
	 * @param status
	 */
	protected void syncUIWithStatus(Status status) {
		
		log.enter("syncUIWithStatus");
		
		log.debug("status: " + status);
		
		syncStudyPanelWithStatus(status);
		syncMainActionsPanelWithStatus(status);
		syncErrorPanelWithStatus(status);

		// Hide everything (that is made visible here) first, then show as required.		
		studyPanel.setVisible(false);
		filesUploadedLabel.setVisible(false);
		filesPendingLabel.setVisible(false);
		filesPanel.setVisible(false);
		filesTableContainer.setVisible(false);
		submissionPendingPanel.setVisible(false);
		submissionPanel.setVisible(false);
		mainActionsPanel.setVisible(false);
		
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {


		}
		
		else if (status instanceof SubmitWidgetModel.RetrieveStudyPendingStatus) {

		}
		
		else if (status instanceof SubmitWidgetModel.StudyNotFoundStatus) {

		}

		else if (status instanceof SubmitWidgetModel.RetrieveUploadedFilesPendingStatus) {

			filesUploadedLabel.setVisible(true);
			filesPendingLabel.setVisible(true);
			mainInteractionPanel.setVisible(true);

		}
		
		else if (status instanceof SubmitWidgetModel.ReadyForInteractionStatus) {

			studyPanel.setVisible(true);
			filesUploadedLabel.setVisible(true);
			filesPanel.setVisible(true);
			filesTableContainer.setVisible(true);
			submissionPanel.setVisible(true);
			mainActionsPanel.setVisible(true);
	
		}
		
		else if (status instanceof SubmitWidgetModel.SubmissionPendingStatus) {

			submissionPendingPanel.setVisible(true);

		}
		
		else if (status instanceof ErrorStatus) {
			
		}

		log.leave();
	}

	
	

	private void syncErrorPanelWithStatus(Status status) {

		if (status instanceof ErrorStatus) {
			
			studyPanel.setVisible(false);
			errorMessage.clear();
			errorMessage.add(new HTML(model.getErrorMessage()));
			errorPanel.setVisible(true);
			
		}
		else {

			errorPanel.setVisible(false);

		}
		
	}




	private void syncMainActionsPanelWithStatus(Status status) {

		log.enter("syncMainActionsPanelWithStatus");
		
		// Turn everything (that is turned on here) off to start with.
		mainActionsPanel.setVisible(false);
		notAcceptedButton.setEnabled(false);
		acceptedButton.setEnabled(false);
		proceedButton.setEnabled(false);
		
		if (status instanceof SubmitWidgetModel.ReadyForInteractionStatus) {

			notAcceptedButton.setValue(true);
			acceptedButton.setValue(false);
			notAcceptedButton.setEnabled(true);
			acceptedButton.setEnabled(true);	
			mainActionsPanel.setVisible(true);
			

		}
		
		log.leave();
		
	}




	private void syncStudyPanelWithStatus(Status status) {
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {

			studyPanel.setVisible(true);
			studyNotSelectedLabel.setVisible(true);
			studyPendingLabel.setVisible(false);
			studyNotFoundLabel.setVisible(false);
			studyTitleLabel.setVisible(false);

		}
		
		else if (status instanceof SubmitWidgetModel.RetrieveStudyPendingStatus) {

			studyPanel.setVisible(true);
			studyNotSelectedLabel.setVisible(false);
			studyPendingLabel.setVisible(true);
			studyNotFoundLabel.setVisible(false);
			studyTitleLabel.setVisible(false);

		}
		
		else if (status instanceof SubmitWidgetModel.StudyNotFoundStatus) {

			studyPanel.setVisible(true);
			studyNotSelectedLabel.setVisible(false);
			studyPendingLabel.setVisible(false);
			studyNotFoundLabel.setVisible(true);
			studyTitleLabel.setVisible(false);

		}

		else if (status instanceof SubmitWidgetModel.RetrieveUploadedFilesPendingStatus) {

			studyPanel.setVisible(true);
			studyNotSelectedLabel.setVisible(false);
			studyPendingLabel.setVisible(false);
			studyNotFoundLabel.setVisible(false);
			studyTitleLabel.setVisible(true);

		}
		
		else if (status instanceof SubmitWidgetModel.ReadyForInteractionStatus) {

			studyPanel.setVisible(true);
			studyNotSelectedLabel.setVisible(false);
			studyPendingLabel.setVisible(false);
			studyNotFoundLabel.setVisible(false);
			studyTitleLabel.setVisible(true);
			
		}
		
		else if (status instanceof SubmitWidgetModel.SubmissionPendingStatus) {

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
				submissionPanel.setVisible(false);
			}
			
			else {
				noFilesUploadedLabel.setVisible(false);
				filesUploadedLabel.setVisible(true);
				filesTableContainer.setVisible(true);
				submissionPanel.setVisible(true);
				mainActionsPanel.setVisible(true);
				
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
		headerRow.add(new Label("Uploaded"));   // i18n
		
		rows.add(headerRow);
		
		for (Element entry : entries) {
			
			String title = AtomHelper.getTitle(entry);
			
			Element categoryElement = AtomHelper.getFirstCategory(entry, Chassis.SCHEME_FILETYPES);
			String type = getTypeLabel(categoryElement);
			
			String fileSizeAsString = AtomHelper.getMediaResourceSize(entry);
			String created = AtomHelper.getPublishedAsDate(entry);
			
			List<Widget> row = new ArrayList<Widget>();
			row.add(new Label(title));	
			row.add(new Label(type));
			row.add(new Label(fileSizeAsString));	
			row.add(new Label(created));
			
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
			return "Data File"; // i18n
		}
		else if (term.equals(Chassis.TERM_DATADICTIONARY)) {
			return "Data Dictionary"; // i18n
		}
		else if (term.equals(Chassis.TERM_PROTOCOL)) {
			return "Protocol"; // i18n
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
		syncUIWithStudyEntryElement(model.getStudyEntryElement());
		syncUIWithUploadFeedDoc(model.getFilesFeedDoc());
	}
	
	
	

	@UiHandler("backToUploadFilesLink")
	void handleStepBackLinkClick(ClickEvent e) {
		controller.stepBack();
	}
	
	
	
	
	@UiHandler("backToSelectStudyLink")
	void handleBackToStartLinkClick(ClickEvent e) {
		controller.backToStart();
	}
	
	
	
	@UiHandler("proceedButton")
	void handleProceedButtonClick(ClickEvent e) {
		controller.proceed();
	}
	



	public void setController(SubmitWidgetController controller) {
		this.controller = controller;
	}

	
	
	
}
