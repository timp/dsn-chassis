package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import com.google.gwt.xml.client.Element;

import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ObservableProperty;
import org.cggh.chassis.generic.widget.client.WidgetEvent;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;


import org.cggh.chassis.generic.widget.client.WidgetEventChannel;


/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 * @author timp
 *
 */
public class StudyRevisionListWidget 
	 	extends ChassisWidget {

	private static final Log log = LogFactory.getLog(StudyRevisionListWidget.class);
	

	public final WidgetEventChannel currentStudyRevisionViewCurrentStudyNavigationEventChannel = new WidgetEventChannel(this);
	

	public final WidgetEventChannel priorStudyRevisionsListViewRevisionNavigationEventChannel = new WidgetEventChannel(this);
	

	@UiTemplate("StudyRevisionListWidget.ui.xml")
	interface StudyRevisionListWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, StudyRevisionListWidget> {
	}
	private static StudyRevisionListWidgetRendererUiBinder uiBinder = 
		GWT.create(StudyRevisionListWidgetRendererUiBinder.class);

	@UiField HTMLPanel mainPanel;
	@UiField HTMLPanel contentPanel;
	@UiField HTMLPanel pendingPanel;
	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;

	@UiField FlowPanel studyRevisionListPanel;
	
    @UiField CurrentStudyRevisionWidget currentStudyRevisionWidgetUiField;


    @UiField PriorStudyRevisionsListWidget priorStudyRevisionsListWidgetUiField;



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
		this.childWidgetEventHandlerRegistrations.add(
				currentStudyRevisionWidgetUiField.currentStudyRevisionViewCurrentStudyNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(currentStudyRevision)");
				currentStudyRevisionViewCurrentStudyNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
		this.childWidgetEventHandlerRegistrations.add(
				priorStudyRevisionsListWidgetUiField.priorStudyRevisionsListViewRevisionNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(priorStudyRevisionsList)");
				priorStudyRevisionsListViewRevisionNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
		
}



	public final ObservableProperty<Status> status = new ObservableProperty<Status>();
	public final ObservableProperty<String> message = new ObservableProperty<String>();

	public final WidgetEventChannel studyRevisionListViewCurrentStudyNavigationEventChannel = new WidgetEventChannel(this);


	public final WidgetEventChannel studyRevisionListViewRevisionNavigationEventChannel = new WidgetEventChannel(this);



	
	@Override
	public void refresh() {
		log.enter("refresh");
		
		// TODO refresh this

		currentStudyRevisionWidgetUiField.refresh();


		priorStudyRevisionsListWidgetUiField.refresh();

		log.leave();	
	}
	
	
	
    protected void syncUIWithStatus(Status status) {
		log.enter("syncUIWithStatus");		
		log.debug("status:" + status);
		
		if (status == null) {
			// nothing to do yet
		}
		else if (status instanceof AsyncWidgetModel.InitialStatus) {
			pendingPanel.setVisible(true);	
		}
		else if (status == ListStudiesWidgetModel.STATUS_RETRIEVE_STUDY_FEED_PENDING) {
			// still pending
		}			
		else if (status == ListStudiesWidgetModel.STATUS_READY_FOR_INTERACTION) {
			pendingPanel.setVisible(false);	
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
