package org.cggh.chassis.wwarn.ui.curator.client;

import static org.cggh.chassis.generic.widget.client.HtmlElements.strongWidget;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.ext.ChassisHelper;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.miniatom.client.ext.ChassisHelper;

import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;

import org.cggh.chassis.generic.miniatom.client.ext.ChassisHelper;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ObservableProperty;
import org.cggh.chassis.generic.widget.client.PropertyChangeEvent;
import org.cggh.chassis.generic.widget.client.PropertyChangeHandler;

import org.cggh.chassis.generic.widget.client.WidgetEvent;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;
import org.cggh.chassis.generic.widget.client.WidgetEventChannel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;


/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 * @author timp
 *
 */
public class PriorStudyRevisionsListWidget 
	 	extends ChassisWidget {

	private static final Log log = LogFactory.getLog(PriorStudyRevisionsListWidget.class);


	@UiTemplate("PriorStudyRevisionsListWidget.ui.xml")
	interface PriorStudyRevisionsListWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, PriorStudyRevisionsListWidget> {
	}
	private static PriorStudyRevisionsListWidgetRendererUiBinder uiBinder = 
		GWT.create(PriorStudyRevisionsListWidgetRendererUiBinder.class);

	@UiField HTMLPanel mainPanel;
	@UiField HTMLPanel contentPanel;

	@UiField HTMLPanel pendingPanel;

	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;

	@UiField FlowPanel priorStudyRevisionsListPanel;
	
	public final ObservableProperty<Status> status = new ObservableProperty<Status>();

	public final ObservableProperty<String> message = new ObservableProperty<String>();

	public final WidgetEventChannel priorStudyRevisionsListViewRevisionNavigationEventChannel = new WidgetEventChannel(this);



	
	@Override
	public void refresh() {
		log.enter("refresh");
		
		log.leave();	
	}
	

	@Override
	protected void renderUI() {
		log.enter("renderUI");
		
		this.clear();
		this.add(uiBinder.createAndBindUi(this));
		

		log.leave();
	}


	@Override
	protected void bindUI() {
		super.bindUI();

		// Much the same as registerHandlersForChildWidgetEvents in ChassisWidgetRenderer


		// Model changes

		status.addChangeHandler(new PropertyChangeHandler<Status>() {
			public void onChange(PropertyChangeEvent<Status> e) {
				log.enter("onChange<Status>");		
				syncUIWithStatus(e.getAfter());
				log.leave();
			}
		});

		message.addChangeHandler(new PropertyChangeHandler<String>() {
			public void onChange(PropertyChangeEvent<String> e) {
				log.enter("onChange<String>");		
				syncUIWithMessage(e.getAfter());
				log.leave();
			}
		});


	
	}



	
	

	
	@Override
	protected void syncUI() {
		syncUIWithStatus(status.get());
	}

	protected void syncUIWithStatus(Status status) {
		log.enter("syncUIWithStatus");		
		log.debug("status:" + status);
		
		if (status == null) {
			// nothing to do yet
		}
		else if (status == AsyncWidgetModel.STATUS_INITIAL) {
			pendingPanel.setVisible(true);	
		}
		else if (status == AsyncWidgetModel.STATUS_ASYNC_REQUEST_PENDING) {
			// still pending
		}			
		else if (status == AsyncWidgetModel.STATUS_READY) {
			pendingPanel.setVisible(false);	
		}			
		else if (status instanceof AsyncWidgetModel.ErrorStatus) {
			message.set("Error status given on asynchronous call.");
		}			
		else {
			message.set("Unhandled status:" + status);
		}
		
		log.leave();
	}

	protected void syncUIWithMessage(String message) {
		log.enter("syncUIWithMessage");

		if (message != null) {
			pendingPanel.setVisible(false);	
			contentPanel.setVisible(false);
			errorMessage.clear();
			errorMessage.add(new HTML(message));
			errorPanel.setVisible(true);
		}

		log.leave();
	}
	
}
