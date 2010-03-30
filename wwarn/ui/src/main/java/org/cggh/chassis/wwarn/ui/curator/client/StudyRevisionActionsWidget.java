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
public class StudyRevisionActionsWidget 
	 	extends ChassisWidget {

	private static final Log log = LogFactory.getLog(StudyRevisionActionsWidget.class);
	

	@UiTemplate("StudyRevisionActionsWidget.ui.xml")
	interface StudyRevisionActionsWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, StudyRevisionActionsWidget> {
	}
	private static StudyRevisionActionsWidgetRendererUiBinder uiBinder = 
		GWT.create(StudyRevisionActionsWidgetRendererUiBinder.class);

	@UiField HTMLPanel mainPanel;
	@UiField HTMLPanel contentPanel;

	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;

	@UiField FlowPanel studyRevisionActionsPanel;
	

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
	public final WidgetEventChannel studyRevisionActionsListStudiesNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyRevisionActionsEditStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyRevisionActionsNextRevisionNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyRevisionActionsViewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyRevisionActionsPreviousRevisionNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyRevisionActionsListStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);


	
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
