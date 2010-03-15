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
 * @author timp
 * @since 13 Jan 2010
 */
public class EditStudyQuestionnaireWidgetRenderer extends
		ChassisWidgetRenderer<EditStudyQuestionnaireWidgetModel> {

	private Log log = LogFactory.getLog(EditStudyQuestionnaireWidgetRenderer.class);
	
	@UiTemplate("EditStudyQuestionnaireWidget.ui.xml")
	interface EditStudyQuestionnaireWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, EditStudyQuestionnaireWidgetRenderer> {
	}
	private static EditStudyQuestionnaireWidgetRendererUiBinder uiBinder = 
		GWT.create(EditStudyQuestionnaireWidgetRendererUiBinder.class);

	//@UiField HTMLPanel bodyPanel;
	//@UiField FlowPanel mainActionsPanel;
	@UiField HTMLPanel pendingPanel;
	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;
	
	


	private EditStudyQuestionnaireWidget owner;
	private EditStudyQuestionnaireWidgetController controller;

	public EditStudyQuestionnaireWidgetRenderer(EditStudyQuestionnaireWidget owner) {
		this.owner = owner;
	}

	public void setController(EditStudyQuestionnaireWidgetController controller) {
		this.controller = controller;
	}

	@Override
	protected void renderUI() {

		this.canvas.clear();
		this.canvas.add(uiBinder.createAndBindUi(this));
		
	}
	
	@Override
	protected void registerHandlersForModelChanges() {
		
	}

	@Override
	protected void syncUI() {
	}

	protected void syncUIWithStatus(Status status) {

		log.enter("syncUIWithStatus");		
		
		// Hide everything (that is made visible here) first, then show as required.
		pendingPanel.setVisible(false);
		errorPanel.setVisible(false);
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {
			
		}
		else if (status instanceof AsyncWidgetModel.ErrorStatus) {
			
			error("Error status: " + status + " " + model.message);
			errorPanel.setVisible(true);
		}
		else { 
			error("Unexpected status: " + status);
			errorPanel.setVisible(true);
		}
		log.leave();
	}
	
	/** Clear and re-initialise, setting selected id. 
	 * @param studyFeedDoc */
	void syncUiWithStudyFeedDoc(Document studyFeedDoc) {
		
		log.enter("syncUiWithStudyFeedDoc");
		
		
		log.leave();
	}
	
	
	@Override
	public void registerHandlersForChildWidgetEvents() {
		
		ChangeHandler studySelectedChangeHandler = new ChangeHandler() {

			public void onChange(ChangeEvent event) {
			}

		};
		
		
	}

	public void error(String err) {

		errorMessage.clear();
		errorMessage.add(new HTML(err));
	}
	
	
}

