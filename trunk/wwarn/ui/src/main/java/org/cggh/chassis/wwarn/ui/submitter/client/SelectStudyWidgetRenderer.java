package org.cggh.chassis.wwarn.ui.submitter.client;

import java.util.List;

import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.xml.client.Element;

/**
 * @author timp
 * @since 13 Jan 2010
 */
public class SelectStudyWidgetRenderer extends ChassisWidgetRenderer<SelectStudyWidgetModel> {

	@UiTemplate("SelectStudyWidget.ui.xml")
	interface SelectStudyWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, SelectStudyWidgetRenderer> {
	}
	private static SelectStudyWidgetRendererUiBinder uiBinder = 
		GWT.create(SelectStudyWidgetRendererUiBinder.class);

	@UiField HTMLPanel bodyPanel;
	@UiField FlowPanel mainActionsPanel;
	@UiField FlowPanel errorPanel;
	@UiField HTMLPanel pendingPanel;
    @UiField FlowPanel selectExistingStudyPanel;
    @UiField ListBox studySelect;
	@UiField HTMLPanel createStudyInteractionPanel;
	@UiField Button cancelSubmissionButtonOne; //TODO handle cancel
	@UiField Button cancelSubmissionButton;    // TODO Handle cancel
	@UiField Button proceedWithSelectedButton;
	@UiField Button createAndProceedButton;


	private SelectStudyWidget owner;

	public SelectStudyWidgetRenderer(SelectStudyWidget owner) {
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
		
				
	}

	@Override
	protected void syncUI() {
		super.syncUI();
		errorPanel.setVisible(false);
		proceedWithSelectedButton.setEnabled(false);
	}

	protected void syncUIWithStatus(Status status) {
		//System.err.println(status);
		if (status instanceof AsyncWidgetModel.InitialStatus) {
			pendingPanel.setVisible(true);
			proceedWithSelectedButton.setEnabled(false);
		} else if (status instanceof SelectStudyWidgetModel.RetrieveFeedPendingStatus) {
		} else if (status instanceof SelectStudyWidgetModel.StudiesRetrievedStatus) {
			syncUiWithFeed();
			pendingPanel.setVisible(false);
			if(model.getStudyCount().equals(new Integer(0))) { 
				selectExistingStudyPanel.setVisible(false);
			}
		} else if (status instanceof SelectStudyWidgetModel.CreateEntryPendingStatus) {
			// TODO 
		} else { 
			pendingPanel.setVisible(false);
			error("Unexpected status: " + status);
		}
		
	}
	
	void syncUiWithFeed() { 
		List<Element>  studyEntries = AtomHelper.getEntries(model.getStudyFeed().getDocumentElement());
		studySelect.addItem("Please select an existing Study", null);
		for (Element element : studyEntries) {
			studySelect.addItem(AtomHelper.getTitle(element), AtomHelper.getId(element));
		}
	}
	
	@UiHandler("studySelect")
	void handleStudySelection(ClickEvent e) { 
		String value = studySelect.getValue(studySelect.getSelectedIndex());
		
		this.owner.getModel().setSelectedStudy(value);
		System.err.println("Set to " + value +  "==" + this.model.getSelectedStudyId() + " so " + this.owner.getModel().isValid());
		proceedWithSelectedButton.setEnabled(this.owner.getModel().isValid());
	}

	@UiHandler("proceedWithSelectedButton")
	void handleProceedWithSelectedButtonClick(ClickEvent e) {
		this.owner.fireEvent(new ProceedActionEvent());
	}
	
	@UiHandler("createAndProceedButton")
	void handleCreateAndProceedButtonClick(ClickEvent e) {
		this.owner.fireEvent(new ProceedActionEvent());
	}
	
	public void error(String err) {
		createStudyInteractionPanel.setVisible(false);
		pendingPanel.setVisible(false);
		errorPanel.add(new HTML(err));
		errorPanel.setVisible(true);
	}
	@Override
	public void registerHandlersForChildWidgetEvents() {
		
		ChangeHandler studySelectedChangeHandler = new ChangeHandler() {

			public void onChange(ChangeEvent event) {
			}

		};
		
		studySelect.addChangeHandler(studySelectedChangeHandler);
		
	}

}
