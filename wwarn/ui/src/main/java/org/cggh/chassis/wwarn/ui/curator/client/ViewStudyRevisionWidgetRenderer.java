package org.cggh.chassis.wwarn.ui.curator.client;

import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.PropertyChangeEvent;
import org.cggh.chassis.generic.widget.client.PropertyChangeHandler;
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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 * @author timp
 */
public class ViewStudyRevisionWidgetRenderer extends
		ChassisWidgetRenderer<ViewStudyRevisionWidgetModel> {

	private Log log = LogFactory.getLog(ViewStudyRevisionWidgetRenderer.class);
		StudyRevisionActionsWidget studyRevisionActionsWidget;

	ViewRevisionQuestionnaireWidget viewRevisionQuestionnaireWidget;

	StudyRevisionSummaryWidget studyRevisionSummaryWidget;



	@UiTemplate("ViewStudyRevisionWidget.ui.xml")
	interface ViewStudyRevisionWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, ViewStudyRevisionWidgetRenderer> {
	}
	private static ViewStudyRevisionWidgetRendererUiBinder uiBinder = 
		GWT.create(ViewStudyRevisionWidgetRendererUiBinder.class);

	@UiField HTMLPanel mainPanel;
	@UiField HTMLPanel contentPanel;
	@UiField HTMLPanel pendingPanel;
	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;

    @UiField StudyRevisionActionsWidget studyRevisionActionsWidgetUiField;


    @UiField ViewRevisionQuestionnaireWidget viewRevisionQuestionnaireWidgetUiField;


    @UiField StudyRevisionSummaryWidget studyRevisionSummaryWidgetUiField;





	private ViewStudyRevisionWidget owner;
	
    public ViewStudyRevisionWidgetRenderer(ViewStudyRevisionWidget owner) {
		this.owner = owner;
        this.studyRevisionActionsWidgetUiField = owner.studyRevisionActionsWidget;


        this.viewRevisionQuestionnaireWidgetUiField = owner.viewRevisionQuestionnaireWidget;


        this.studyRevisionSummaryWidgetUiField = owner.studyRevisionSummaryWidget;

	}
		private ViewStudyRevisionWidgetController controller;

	public void setController(ViewStudyRevisionWidgetController controller) {
		this.controller = controller;
	}

	@Override
	protected void registerHandlersForModelChanges() {
		
	}

	@Override
	protected void renderUI() {

		this.canvas.clear();
		this.canvas.add(uiBinder.createAndBindUi(this));
		
		pendingPanel.setVisible(true);	
	}
	

	@Override
	protected void syncUI() {
		syncUIWithStatus(model.status.get());
	}

	protected void syncUIWithStatus(Status status) {

		log.enter("syncUIWithStatus");		
		
		errorPanel.setVisible(false);	
		if (status == null) {
		// null before being set
		log.debug("Called with null status");
		}
		else if (status instanceof AsyncWidgetModel.InitialStatus) {

			pendingPanel.setVisible(true);	
		}
		
		//TODO Widget specific statii
		
		else if (status instanceof AsyncWidgetModel.ReadyStatus) {

			pendingPanel.setVisible(false);	
		}			
		
		else if (status instanceof AsyncWidgetModel.ErrorStatus) {

			
			error("Error status given on asynchronous call.");
		}			
		
		else {

			error("Unhandled status:" + status);
		}

		log.leave();
	}
	
	public void error(String err) {
		log.enter("error");
		log.debug("Error:" + err);
		pendingPanel.setVisible(false);	
		contentPanel.setVisible(false);
		errorMessage.clear();
		errorMessage.add(new HTML(err));
		errorPanel.setVisible(true);
		log.leave();
	}
	
	
}

