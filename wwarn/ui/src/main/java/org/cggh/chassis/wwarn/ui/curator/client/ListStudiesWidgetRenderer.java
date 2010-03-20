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
 *
 * @author timp
 */
public class ListStudiesWidgetRenderer extends
		ChassisWidgetRenderer<ListStudiesWidgetModel> {

	private Log log = LogFactory.getLog(ListStudiesWidgetRenderer.class);
	

	@UiTemplate("ListStudiesWidget.ui.xml")
	interface ListStudiesWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, ListStudiesWidgetRenderer> {
	}
	private static ListStudiesWidgetRendererUiBinder uiBinder = 
		GWT.create(ListStudiesWidgetRendererUiBinder.class);

	@UiField HTMLPanel mainPanel;
	@UiField HTMLPanel contentPanel;
	@UiField HTMLPanel pendingPanel;
	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;

	private ListStudiesWidget owner;
	
    public ListStudiesWidgetRenderer(ListStudiesWidget owner) {
		this.owner = owner;
	}
	private ListStudiesWidgetController controller;

	public void setController(ListStudiesWidgetController controller) {
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
		syncUIWithStatus(model.getStatus());
	}

	protected void syncUIWithStatus(Status status) {

		log.enter("syncUIWithStatus");		
		
		errorPanel.setVisible(false);	
		if (status instanceof AsyncWidgetModel.InitialStatus) {
			pendingPanel.setVisible(true);	
		}
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
	
	/** Clear and re-initialise, setting selected id. 
	 * @param studyFeedDoc */
	void syncUiWithStudyFeedDoc(Document studyFeedDoc) {
		
		log.enter("syncUiWithStudyFeedDoc");
		
		// Turn everything off (that is made visible/enabled here) first, then show/enable as required.
		
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

