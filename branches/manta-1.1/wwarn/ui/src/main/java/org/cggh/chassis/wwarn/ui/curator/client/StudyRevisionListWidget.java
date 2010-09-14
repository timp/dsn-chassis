package org.cggh.chassis.wwarn.ui.curator.client;

import static org.cggh.chassis.generic.widget.client.HtmlElements.strongWidget;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.ext.ChassisHelper;

import java.util.ArrayList;
import java.util.List;

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


import com.google.gwt.xml.client.Element;

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


	public final ObservableProperty<Element> studyEntry = new ObservableProperty<Element>();


	public final ObservableProperty<String> message = new ObservableProperty<String>();

	public final WidgetEventChannel currentStudyRevisionViewCurrentStudyNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel priorStudyRevisionsListViewRevisionNavigationEventChannel = new WidgetEventChannel(this);



	
	@Override
	public void refresh() {
		log.enter("refresh");
		
		currentStudyRevisionWidgetUiField.refresh();


		priorStudyRevisionsListWidgetUiField.refresh();


		syncUIWithStudyEntry(studyEntry.get());

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
		// CurrentStudyRevision events

		this.childWidgetEventHandlerRegistrations.add(
				currentStudyRevisionWidgetUiField.currentStudyRevisionViewCurrentStudyNavigationEventChannel.addHandler(new WidgetEventHandler<WidgetEvent>() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(CurrentStudyRevisionViewCurrentStudyNavigation)");
				currentStudyRevisionViewCurrentStudyNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
 		// PriorStudyRevisionsList events

		this.childWidgetEventHandlerRegistrations.add(
				priorStudyRevisionsListWidgetUiField.priorStudyRevisionsListViewRevisionNavigationEventChannel.addHandler(new WidgetEventHandler<WidgetEvent>() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(PriorStudyRevisionsListViewRevisionNavigation)");
				priorStudyRevisionsListViewRevisionNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
 

		// Model changes


		message.addChangeHandler(new PropertyChangeHandler<String>() {
			public void onChange(PropertyChangeEvent<String> e) {
				log.enter("onChange<String>");		
				syncUIWithMessage(e.getAfter());
				log.leave();
			}
		});

	
		studyEntry.addChangeHandler(new PropertyChangeHandler<Element>() {
			public void onChange(PropertyChangeEvent<Element> e) {
				log.enter("onchange(studyEntry)");
				syncUIWithStudyEntry(e.getAfter());
				log.leave();
			}
		});


	
	}



	
	

	

	@Override
	protected void syncUI() {
		log.enter("syncUI");
		syncUIWithStudyEntry(studyEntry.get());
		errorPanel.setVisible(false);	
		contentPanel.setVisible(true);
		this.setVisible(true);
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
		
	protected void syncUIWithStudyEntry(Element studyEntry) {
		log.enter("syncUIWithStudyEntry");
		// TODO needs to be a method in an extension
		this.studyRevisionListPanel.clear();
		this.studyRevisionListPanel.add(strongWidget("syncUIWithStudyEntry"));
		log.leave();
	}


}
