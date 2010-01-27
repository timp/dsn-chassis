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
		
		notAcceptedButton.setValue(true);
		acceptedButton.setValue(false);
		
		proceedButton.setEnabled(false);
		
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
		
		log.debug("status: "+status);
		
		// TODO
		
		syncStudyPanelWithStatus(status);
		syncMainInteractionPanelWithStatus(status);
		syncErrorPanelWithStatus(status);
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {

			filesUploadedLabel.setVisible(false);
			filesPendingLabel.setVisible(false);
			noFilesUploadedLabel.setVisible(false);
			submissionPendingPanel.setVisible(false);

		}
		
		else if (status instanceof SubmitWidgetModel.RetrieveStudyPendingStatus) {

		}
		
		else if (status instanceof SubmitWidgetModel.StudyNotFoundStatus) {

		}

		else if (status instanceof SubmitWidgetModel.RetrieveUploadedFilesPendingStatus) {

			
			filesUploadedLabel.setVisible(true);
			filesPendingLabel.setVisible(true);
			noFilesUploadedLabel.setVisible(false);
			
			filesTableContainer.setVisible(false);
			
			mainActionsPanel.setVisible(false);


		}
		
		else if (status instanceof SubmitWidgetModel.ReadyForInteractionStatus) {

//			submissionPanel.setVisible(true);
			filesPanel.setVisible(true);
			filesTableContainer.setVisible(true);
			studyPanel.setVisible(true);

			filesPendingLabel.setVisible(false);
			submissionPendingPanel.setVisible(false);
			
		}
		
		else if (status instanceof SubmitWidgetModel.SubmissionPendingStatus) {

			submissionPanel.setVisible(false);
			filesPanel.setVisible(false);
			studyPanel.setVisible(false);

			submissionPendingPanel.setVisible(true);

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
				status instanceof SubmitWidgetModel.RetrieveUploadedFilesPendingStatus || 
				status instanceof SubmitWidgetModel.ReadyForInteractionStatus 
				
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
		
		List<Widget[]> rows = new ArrayList<Widget[]>();

		Widget[] headerRow = {
			new Label("File Name"), // TODO i18n
			new Label("Type"),  // TODO i18n
			new Label("Summary"), // TODO i18n
			new Label("Uploaded")  // TODO i18n
		};
		rows.add(headerRow);
		
		for (Element entry : entries) {
			
			String title = AtomHelper.getTitle(entry);
			
			Element categoryElement = AtomHelper.getFirstCategory(entry, Chassis.SCHEME_FILETYPES);
			String type = getTypeLabel(categoryElement);
			
			String summary = AtomHelper.getSummary(entry);
			String created = AtomHelper.getPublished(entry);
			
			Widget[] row = {
				new Label(title),	
				new Label(type),	
				new Label(summary),	
				new Label(created)
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
