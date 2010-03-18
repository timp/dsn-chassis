package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
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
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 * @author timp
 */
public class ViewStudyWidgetRenderer extends
		ChassisWidgetRenderer<ViewStudyWidgetModel> {

	private Log log = LogFactory.getLog(ViewStudyWidgetRenderer.class);
	
	@UiTemplate("ViewStudyWidget.ui.xml")
	interface ViewStudyWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, ViewStudyWidgetRenderer> {
	}
	private static ViewStudyWidgetRendererUiBinder uiBinder = 
		GWT.create(ViewStudyWidgetRendererUiBinder.class);

	@UiField HTMLPanel mainPanel;
	@UiField HTMLPanel contentPanel;
	@UiField HTMLPanel pendingPanel;
	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;

	private ViewStudyWidget owner;
	
    public ViewStudyWidgetRenderer(ViewStudyWidget owner) {
		this.owner = owner;
	}
	private ViewStudyWidgetController controller;

	public void setController(ViewStudyWidgetController controller) {
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

		errorPanel.setVisible(false);	
		mainPanel.setVisible(false);
		pendingPanel.setVisible(true);	
		if (status instanceof AsyncWidgetModel.InitialStatus) {

			// Everything off, hidden.
		}
		
		
		else if (status instanceof AsyncWidgetModel.ErrorStatus) {

			
			error("Error status given on asynchronous call.");
			errorPanel.setVisible(true);
		}			
		
		else {

			error("Unhandled status:" + status);
			errorPanel.setVisible(true);
		}
		
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

