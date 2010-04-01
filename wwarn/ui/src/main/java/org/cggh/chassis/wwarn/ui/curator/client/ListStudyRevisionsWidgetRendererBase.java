package org.cggh.chassis.wwarn.ui.curator.client;

import static org.cggh.chassis.generic.widget.client.HtmlElements.strongWidget;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.miniatom.client.ext.ChassisHelper;

import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeHandler;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.PropertyChangeEvent;
import org.cggh.chassis.generic.widget.client.PropertyChangeHandler;
import org.cggh.chassis.generic.widget.client.WidgetEvent;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;
import org.cggh.chassis.wwarn.ui.common.client.RenderUtils;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;


import com.google.gwt.xml.client.Element;
 

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 * @author timp
 */
public abstract class ListStudyRevisionsWidgetRendererBase extends
		ChassisWidgetRenderer<ListStudyRevisionsWidgetModel> {

	private Log log = LogFactory.getLog(ListStudyRevisionsWidgetRenderer.class);
		StudySummaryWidget studySummaryWidget;

	StudyRevisionListWidget studyRevisionListWidget;



	@UiTemplate("ListStudyRevisionsWidget.ui.xml")
	interface ListStudyRevisionsWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, ListStudyRevisionsWidgetRendererBase> {
	}
	private static ListStudyRevisionsWidgetRendererUiBinder uiBinder = 
		GWT.create(ListStudyRevisionsWidgetRendererUiBinder.class);


	@UiField HTMLPanel mainPanel;
	@UiField HTMLPanel contentPanel;
	@UiField HTMLPanel pendingPanel;

	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;

	@UiField FlowPanel listStudyRevisionsPanel;
	
	@UiField StudySummaryWidget studySummaryWidgetUiField;

	@UiField StudyRevisionListWidget studyRevisionListWidgetUiField;




	protected ListStudyRevisionsWidget owner;
	
	public ListStudyRevisionsWidgetRendererBase() {
		super();
	}

	@Override
	protected void registerHandlersForModelChanges() {
		log.enter("registerHandlersForModelChanges");

		this.modelChangeHandlerRegistrations.add(
				model.status.addChangeHandler(new PropertyChangeHandler<Status>() {
			public void onChange(PropertyChangeEvent<Status> e) {
				log.enter("onChange<Status>");		
				syncUIWithStatus(e.getAfter());
				log.leave();
			}
		}));

		this.modelChangeHandlerRegistrations.add(
				model.message.addChangeHandler(new PropertyChangeHandler<String>() {
			public void onChange(PropertyChangeEvent<String> e) {
				log.enter("onChange<Message>");		
				syncUIWithMessage(e.getAfter());
				log.leave();
			}
		}));


		this.modelChangeHandlerRegistrations.add(
				model.studyEntry.addChangeHandler(new PropertyChangeHandler<Element>() {
			public void onChange(PropertyChangeEvent<Element> e) {
				log.enter("onchange(studyEntry)");
				syncUIWithStudyEntry(e.getAfter());
				log.leave();
			}
		}));

		log.leave();
	}
		
	@Override
	protected void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");

		// Our children's events
		this.childWidgetEventHandlerRegistrations.add(
				studySummaryWidgetUiField.studyActionsListStudiesNavigationEventChannel.addHandler(new WidgetEventHandler<WidgetEvent>() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(studySummaryWidgetUiField.studyActionsListStudiesNavigation)");
				owner.studyActionsListStudiesNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
 		this.childWidgetEventHandlerRegistrations.add(
				studySummaryWidgetUiField.studyActionsViewStudyNavigationEventChannel.addHandler(new WidgetEventHandler<WidgetEvent>() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(studySummaryWidgetUiField.studyActionsViewStudyNavigation)");
				owner.studyActionsViewStudyNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
 		this.childWidgetEventHandlerRegistrations.add(
				studySummaryWidgetUiField.studyActionsViewStudyQuestionnaireNavigationEventChannel.addHandler(new WidgetEventHandler<WidgetEvent>() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(studySummaryWidgetUiField.studyActionsViewStudyQuestionnaireNavigation)");
				owner.studyActionsViewStudyQuestionnaireNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
 		this.childWidgetEventHandlerRegistrations.add(
				studySummaryWidgetUiField.studyActionsListStudyRevisionsNavigationEventChannel.addHandler(new WidgetEventHandler<WidgetEvent>() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(studySummaryWidgetUiField.studyActionsListStudyRevisionsNavigation)");
				owner.studyActionsListStudyRevisionsNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
 		this.childWidgetEventHandlerRegistrations.add(
				studySummaryWidgetUiField.studyActionsEditStudyQuestionnaireNavigationEventChannel.addHandler(new WidgetEventHandler<WidgetEvent>() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(studySummaryWidgetUiField.studyActionsEditStudyQuestionnaireNavigation)");
				owner.studyActionsEditStudyQuestionnaireNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
 		this.childWidgetEventHandlerRegistrations.add(
				studySummaryWidgetUiField.studyActionsUploadDataFilesWizardNavigationEventChannel.addHandler(new WidgetEventHandler<WidgetEvent>() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(studySummaryWidgetUiField.studyActionsUploadDataFilesWizardNavigation)");
				owner.studyActionsUploadDataFilesWizardNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
 		this.childWidgetEventHandlerRegistrations.add(
				studyRevisionListWidgetUiField.currentStudyRevisionViewCurrentStudyNavigationEventChannel.addHandler(new WidgetEventHandler<WidgetEvent>() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(studyRevisionListWidgetUiField.currentStudyRevisionViewCurrentStudyNavigation)");
				owner.currentStudyRevisionViewCurrentStudyNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
 		this.childWidgetEventHandlerRegistrations.add(
				studyRevisionListWidgetUiField.priorStudyRevisionsListViewRevisionNavigationEventChannel.addHandler(new WidgetEventHandler<WidgetEvent>() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(studyRevisionListWidgetUiField.priorStudyRevisionsListViewRevisionNavigation)");
				owner.priorStudyRevisionsListViewRevisionNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
 
		log.leave();
	}



	@Override
	protected void renderUI() {

		this.canvas.clear();
		this.canvas.add(uiBinder.createAndBindUi(this));

	}
	

	@Override
	protected void bindUI(ListStudyRevisionsWidgetModel model) {
		super.bindUI(model);

		this.pendingPanel.setVisible(true);	
		this.errorPanel.setVisible(false);	
		this.contentPanel.setVisible(true);
	}


	@Override
	protected void syncUI() {
		log.enter("syncUI");

		syncUIWithStatus(model.status.get());

		syncUIWithStudyEntry(model.studyEntry.get());

		log.leave();
	}
	
	protected void syncUIWithStudyEntry(Element studyEntry) {
		log.enter("syncUIWithStudyEntry");

		if (studyEntry == null) 
			log.debug("studyEntry null");
		else {
			log.debug("studyEntry :"+studyEntry);


			studySummaryWidgetUiField.studyEntry.set(studyEntry);
 

			studyRevisionListWidgetUiField.studyEntry.set(studyEntry);
 
			this.listStudyRevisionsPanel.clear();
			this.listStudyRevisionsPanel.add(renderStudyEntry(studyEntry));
			pendingPanel.setVisible(false);

 		}
		
		log.leave();
	}
	
  /* Override this in a separate class, as the Base class can be regenerated. */
	abstract Widget renderStudyEntry(Element studyEntry);

	

	protected void syncUIWithStatus(Status status) {
		log.enter("syncUIWithStatus");		
		
		errorPanel.setVisible(false);	
		pendingPanel.setVisible(true);	
		contentPanel.setVisible(true);
		if (status == null) {
			// null before being set
			log.debug("Called with null status");
		}
		else if (status instanceof AsyncWidgetModel.InitialStatus) {
		}
		else if (status instanceof AsyncWidgetModel.AsyncRequestPendingStatus) {
		}
		else if (status instanceof AsyncWidgetModel.NotFoundStatus) {
			model.message.set("Not found. url " + model.studyUrl.get());
		}			
		else if (status instanceof AsyncWidgetModel.ReadyStatus) {
			pendingPanel.setVisible(false);
			contentPanel.setVisible(true);
		}			
		else if (status instanceof AsyncWidgetModel.ErrorStatus) {
			model.message.set("Error status given on asynchronous call.");
		}			
		else {
			model.message.set("Unhandled status:" + status);
		}

		log.leave();
	}



	protected void syncUIWithMessage(String message) {
		log.enter("syncUIWithMessage");

		if (message != null) {
			log.debug("Setting message to :" + message + ":");
			pendingPanel.setVisible(false);	
			contentPanel.setVisible(false);
			errorMessage.clear();
			errorMessage.add(new HTML(message));
			errorPanel.setVisible(true);
		}

		log.leave();
	}



}

