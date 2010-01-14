package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;

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
	@UiField HTMLPanel createStudyInteractionPanel;
	@UiField Button cancelSubmissionButton;
	@UiField Button proceedButton;


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
	}

	protected void syncUIWithStatus(Status status) {
		error("Called");
		if (status instanceof AsyncWidgetModel.InitialStatus) {
			pendingPanel.setVisible(true);
		} else { 
			error("Unexpected status: " + status);
		}
		
	}
	
	@UiHandler("proceedButton")
	void handleProceedButtonClick(ClickEvent e) {
		this.owner.fireEvent(new ProceedActionEvent());
	}
	
	public void error(String err) {
		createStudyInteractionPanel.setVisible(false);
		pendingPanel.setVisible(false);
		errorPanel.add(new HTML(err));
		errorPanel.setVisible(true);
	}

}
