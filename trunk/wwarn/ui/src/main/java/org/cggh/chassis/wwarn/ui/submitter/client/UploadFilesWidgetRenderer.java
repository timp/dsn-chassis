/**
 * 
 */
package org.cggh.chassis.wwarn.ui.submitter.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.ErrorStatus;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeHandler;
import org.cggh.chassis.wwarn.ui.common.client.RenderUtils;
import org.cggh.chassis.wwarn.ui.submitter.client.UploadFilesWidgetModel.StudyEntryChangeEvent;
import org.cggh.chassis.wwarn.ui.submitter.client.UploadFilesWidgetModel.StudyEntryChangeHandler;
import org.cggh.chassis.wwarn.ui.submitter.client.UploadFilesWidgetModel.UploadFeedChangeEvent;
import org.cggh.chassis.wwarn.ui.submitter.client.UploadFilesWidgetModel.UploadFeedChangeHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class UploadFilesWidgetRenderer extends ChassisWidgetRenderer<UploadFilesWidgetModel> {

	
	
	
	@UiTemplate("UploadFilesWidget.ui.xml")
	interface MyUiBinder extends UiBinder<HTMLPanel, UploadFilesWidgetRenderer> {}
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	
	
	@UiField HTMLPanel studyPanel;
	@UiField Label studyNotSelectedLabel;
	@UiField Label studyPendingLabel;
	@UiField Label studyNotFoundLabel;
	@UiField Label studyTitleLabel;
	@UiField Anchor stepBackLink;
	@UiField HTMLPanel mainInteractionPanel;
	@UiField Label filesUploadedLabel;
	@UiField Label filesPendingLabel;
	@UiField Label noFilesUploadedLabel;
	@UiField FlowPanel filesTableContainer;
	@UiField FlowPanel mainActionsPanel;
	@UiField Button cancelSubmissionButton;
	@UiField Button proceedButton;
	@UiField Label uploadAFileTitleLabel;
	@UiField Label uploadAnotherFileTitleLabel;
	@UiField HTMLPanel errorPanel;




	private UploadFilesWidget owner;
	
	
	
	
	public UploadFilesWidgetRenderer(UploadFilesWidget owner) {
		this.owner = owner;
	}
	
	
	
	
	@Override
	protected void renderUI() {

		this.canvas.clear();
		this.canvas.add(uiBinder.createAndBindUi(this));
		
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
		
	}
	
	
	
	

	/**
	 * @param status
	 */
	protected void syncUIWithStatus(Status status) {
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {

			studyNotSelectedLabel.setVisible(true);
			studyPendingLabel.setVisible(false);
			studyNotFoundLabel.setVisible(false);
			studyTitleLabel.setVisible(false);
			
			studyPanel.setVisible(true);
			mainInteractionPanel.setVisible(false);
			errorPanel.setVisible(false);
		}
		
		else if (status instanceof UploadFilesWidgetModel.RetrieveStudyPendingStatus) {

			studyNotSelectedLabel.setVisible(false);
			studyPendingLabel.setVisible(true);
			studyNotFoundLabel.setVisible(false);
			studyTitleLabel.setVisible(false);
			
			studyPanel.setVisible(true);
			mainInteractionPanel.setVisible(false);
			errorPanel.setVisible(false);

		}
		
		else if (status instanceof UploadFilesWidgetModel.StudyNotFoundStatus) {

			studyNotSelectedLabel.setVisible(false);
			studyPendingLabel.setVisible(false);
			studyNotFoundLabel.setVisible(true);
			studyTitleLabel.setVisible(false);
			
			studyPanel.setVisible(true);
			mainInteractionPanel.setVisible(false);
			errorPanel.setVisible(false);

		}

		else if (status instanceof UploadFilesWidgetModel.RetrieveUploadedFilesPendingStatus) {

			studyNotSelectedLabel.setVisible(false);
			studyPendingLabel.setVisible(false);
			studyNotFoundLabel.setVisible(false);
			studyTitleLabel.setVisible(true);
			
			filesUploadedLabel.setVisible(true);
			filesPendingLabel.setVisible(true);
			noFilesUploadedLabel.setVisible(false);
			
			studyPanel.setVisible(true);
			mainInteractionPanel.setVisible(true);
			errorPanel.setVisible(false);

		}
		
		else if (status instanceof UploadFilesWidgetModel.ReadyForInteractionStatus) {

			studyNotSelectedLabel.setVisible(false);
			studyPendingLabel.setVisible(false);
			studyNotFoundLabel.setVisible(false);
			studyTitleLabel.setVisible(true);
			
			filesPendingLabel.setVisible(false);
			
			studyPanel.setVisible(true);
			mainInteractionPanel.setVisible(true);
			errorPanel.setVisible(false);

		}
		
		else if (status instanceof ErrorStatus) {
			
			studyPanel.setVisible(false);
			mainInteractionPanel.setVisible(false);
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




	private static FlexTable renderUploadsTable(List<Element> entries) {
		
		List<Widget[]> rows = new ArrayList<Widget[]>();

		Widget[] headerRow = {
			new InlineLabel("File Name"),	
			new InlineLabel("Type"),	
			new InlineLabel("Summary"),	
			new InlineLabel("Uploaded"),	
			new InlineLabel("Actions")	
		};
		rows.add(headerRow);
		
		for (Element entry : entries) {
			
			Widget[] row = {
				new InlineLabel("TODO"),	
				new InlineLabel("TODO"),	
				new InlineLabel("TODO"),	
				new InlineLabel("TODO"),	
				new InlineLabel("TODO")	
			};
			rows.add(row);
			
		}
		
		return RenderUtils.renderResultsTable(rows);
	}




	@Override
	protected void syncUI() {
		syncUIWithStatus(model.getStatus());
		syncUIWithStudyEntryElement(model.getStudyEntryElement());
		syncUIWithUploadFeedDoc(model.getUploadFeedDoc());
	}
	
	
	

	@UiHandler("stepBackLink")
	void handleStepBackLinkClick(ClickEvent e) {
		this.owner.fireEvent(new StepBackNavigationEvent());
	}
	
	
	
	
	@UiHandler("proceedButton")
	void handleProceedButtonClick(ClickEvent e) {
		this.owner.fireEvent(new ProceedActionEvent());
	}
	
	
	
	

	
	
	
}
