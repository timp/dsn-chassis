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
public class StudyRevisionListWidget 
	 	extends ChassisWidget {

	private static final Log log = LogFactory.getLog(StudyRevisionListWidget.class);
	

	@UiTemplate("StudyRevisionListWidget.ui.xml")
	interface StudyRevisionListWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, StudyRevisionListWidget> {
	}
	private static StudyRevisionListWidgetRendererUiBinder uiBinder = 
		GWT.create(StudyRevisionListWidgetRendererUiBinder.class);

	@UiField HTMLPanel mainPanel;
	@UiField HTMLPanel contentPanel;

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
				log.enter("onEvent(CurrentStudyRevisionViewCurrentStudyNavigation)");
				currentStudyRevisionViewCurrentStudyNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
 		this.childWidgetEventHandlerRegistrations.add(
				priorStudyRevisionsListWidgetUiField.priorStudyRevisionsListViewRevisionNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(PriorStudyRevisionsListViewRevisionNavigation)");
				priorStudyRevisionsListViewRevisionNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
 		
	}


	public final ObservableProperty<Status> status = new ObservableProperty<Status>();
	public final ObservableProperty<String> message = new ObservableProperty<String>();
	public final WidgetEventChannel currentStudyRevisionViewCurrentStudyNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel priorStudyRevisionsListViewRevisionNavigationEventChannel = new WidgetEventChannel(this);


	
	@Override
	public void refresh() {
		log.enter("refresh");
		
		// TODO refresh this

		currentStudyRevisionWidgetUiField.refresh();


		priorStudyRevisionsListWidgetUiField.refresh();

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
