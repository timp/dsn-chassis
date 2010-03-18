package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.xml.client.Document;

/**
 * @author timp
 */
public class ViewStudyQuestionnaireWidgetRenderer extends
		ChassisWidgetRenderer<ViewStudyQuestionnaireWidgetModel> {

	private Log log = LogFactory.getLog(ViewStudyQuestionnaireWidgetRenderer.class);
	
	@UiTemplate("ViewStudyQuestionnaireWidget.ui.xml")
	interface ViewStudyQuestionnaireWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, ViewStudyQuestionnaireWidgetRenderer> {
	}
	private static ViewStudyQuestionnaireWidgetRendererUiBinder uiBinder = 
		GWT.create(ViewStudyQuestionnaireWidgetRendererUiBinder.class);

	@UiField HTMLPanel mainPanel;
	@UiField HTMLPanel contentPanel;
	@UiField HTMLPanel pendingPanel;
	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;

	private ViewStudyQuestionnaireWidget owner;
    public ViewStudyQuestionnaireWidgetRenderer(ViewStudyQuestionnaireWidget owner) {
		this.owner = owner;
	}
	private ViewStudyQuestionnaireWidgetController controller;

	public void setController(ViewStudyQuestionnaireWidgetController controller) {
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
		
		log.leave();
	}
	
	/** Clear and re-initialise, setting selected id. 
	 * @param studyFeedDoc */
	void syncUiWithStudyFeedDoc(Document studyFeedDoc) {
		
		log.enter("syncUiWithStudyFeedDoc");
		
		// Turn everything off (that is made visible/enabled here) first, then show/enable as required.
		
		log.leave();
	}
	
	
	public void error(String err) {

		errorMessage.clear();
		errorMessage.add(new HTML(err));
	}
	
	
}

