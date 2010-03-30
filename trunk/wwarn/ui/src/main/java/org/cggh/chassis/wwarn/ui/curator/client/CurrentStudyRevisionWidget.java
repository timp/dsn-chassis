package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.ObservableProperty;

import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.WidgetEvent;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;

import com.google.gwt.xml.client.Element;

import org.cggh.chassis.generic.widget.client.WidgetEventChannel;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 * @author timp
 *
 */
public class CurrentStudyRevisionWidget 
	 	extends ChassisWidget {

	private static final Log log = LogFactory.getLog(CurrentStudyRevisionWidget.class);
	

	@UiTemplate("CurrentStudyRevisionWidget.ui.xml")
	interface CurrentStudyRevisionWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, CurrentStudyRevisionWidget> {
	}
	private static CurrentStudyRevisionWidgetRendererUiBinder uiBinder = 
		GWT.create(CurrentStudyRevisionWidgetRendererUiBinder.class);

	@UiField HTMLPanel mainPanel;
	@UiField HTMLPanel contentPanel;
	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;

	@UiField FlowPanel currentStudyRevisionPanel;
	

	@Override
	protected void renderUI() {

		log.enter("renderUI");
		
		this.clear();
		this.add(uiBinder.createAndBindUi(this));
		errorPanel.setVisible(false);	
		

		log.leave();
	}


	@Override
	protected void bindUI() {
		super.bindUI();
		
	}


	public final ObservableProperty<Status> status = new ObservableProperty<Status>();
	public final ObservableProperty<String> message = new ObservableProperty<String>();
	public final WidgetEventChannel currentStudyRevisionViewCurrentStudyNavigationEventChannel = new WidgetEventChannel(this);


	
	@Override
	public void refresh() {
		log.enter("refresh");
		
		// TODO refresh this
		log.leave();	
	}
	
	
	
	protected void syncUIWithMessage(String message) {
		log.enter("syncUIWithMessage");

		if (message != null) {
			contentPanel.setVisible(false);
			errorMessage.clear();
			errorMessage.add(new HTML(message));
			errorPanel.setVisible(true);
		}

		log.leave();
	}
	

}
