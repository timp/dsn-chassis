package org.cggh.chassis.wwarn.ui.curator.client;


import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.PropertyChangeEvent;
import org.cggh.chassis.generic.widget.client.PropertyChangeHandler;
import org.cggh.chassis.generic.widget.client.WidgetEvent;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;


/**
 * @author timp
 */
public class CuratorHomeWidgetRenderer extends
		ChassisWidgetRenderer<CuratorHomeWidgetModel> {

	private Log log = LogFactory.getLog(CuratorHomeWidgetRenderer.class);



	@UiTemplate("CuratorHomeWidget.ui.xml")
	interface CuratorHomeWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, CuratorHomeWidgetRenderer> {
	}

	private static CuratorHomeWidgetRendererUiBinder uiBinder =
			GWT.create(CuratorHomeWidgetRendererUiBinder.class);

	@UiField HTMLPanel mainPanel;
	@UiField HTMLPanel contentPanel;
	@UiField HTMLPanel pendingPanel;

	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;

	@UiField ListStudiesWidget listStudiesWidgetUiField;
	
	private CuratorHomeWidget owner;

	public CuratorHomeWidgetRenderer(CuratorHomeWidget owner) {
		this.owner = owner;
		this.listStudiesWidgetUiField = owner.listStudiesWidget;
	}

	@Override
	protected void renderUI() {

		this.canvas.clear();
		this.canvas.add(uiBinder.createAndBindUi(this));
		
		pendingPanel.setVisible(true);	

	}


	@Override
	protected void syncUI() {
		syncUIWithStatus(model.getStatus());
	}
	
	@Override
	protected void registerHandlersForModelChanges() {
		super.registerHandlersForModelChanges();
		model.status.addChangeHandler(new PropertyChangeHandler<Status>() {
		
			public void onChange(PropertyChangeEvent<Status> e) {
				log.enter("onChange");
			
				syncUIWithStatus(e.getAfter());
			
				log.leave();
			}
		});
	}

	protected void syncUIWithStatus(Status status) {

		log.enter("syncUIWithStatus");
        log.debug("Status:"+status);
        
		errorPanel.setVisible(false);	
		if (status instanceof AsyncWidgetModel.InitialStatus) {
			pendingPanel.setVisible(true);	
		}
		
		else if (status == CuratorHomeWidgetModel.STATUS_READY_FOR_INTERACTION) {
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

	@Override
	public void registerHandlersForChildWidgetEvents() {
		super.registerHandlersForChildWidgetEvents();	
		
		HandlerRegistration a = listStudiesWidgetUiField.viewStudyNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				// just bubble
				owner.viewStudyNavigationEventChannel.fireEvent(e);
			}
		});
		this.childWidgetEventHandlerRegistrations.add(a);		
		
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
