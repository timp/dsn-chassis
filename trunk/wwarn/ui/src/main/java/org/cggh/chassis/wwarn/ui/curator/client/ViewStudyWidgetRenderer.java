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
 */
public class ViewStudyWidgetRenderer extends
		ChassisWidgetRenderer<ViewStudyWidgetModel> {

	private Log log = LogFactory.getLog(ViewStudyWidgetRenderer.class);
		StudySummaryWidget studySummaryWidget;

	ViewStudyMetadataWidget viewStudyMetadataWidget;

	ListSubmissionsWidget listSubmissionsWidget;

	ListCurationsWidget listCurationsWidget;



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

	@UiField StudySummaryWidget studySummaryWidgetUiField;

	@UiField ViewStudyMetadataWidget viewStudyMetadataWidgetUiField;

	@UiField ListSubmissionsWidget listSubmissionsWidgetUiField;

	@UiField ListCurationsWidget listCurationsWidgetUiField;




	private ViewStudyWidget owner;
	
	public ViewStudyWidgetRenderer(ViewStudyWidget owner) {
		this.owner = owner;
	}

	private ViewStudyWidgetController controller;

	public void setController(ViewStudyWidgetController controller) {
		this.controller = controller;
	}



	@Override
	protected void registerHandlersForModelChanges() {
		
		this.modelChangeHandlerRegistrations.add(model.status.addChangeHandler(new PropertyChangeHandler<Status>() {
			public void onChange(PropertyChangeEvent<Status> e) {
				log.enter("onChange<Status>");		
				syncUIWithStatus(e.getAfter());
				log.leave();
			}
		}));

		this.modelChangeHandlerRegistrations.add(model.message.addChangeHandler(new PropertyChangeHandler<String>() {
			public void onChange(PropertyChangeEvent<String> e) {
				log.enter("onChange<String>");		
				syncUIWithMessage(e.getAfter());
				log.leave();
			}
		}));

	
		this.modelChangeHandlerRegistrations.add(model.studyEntry.addChangeHandler(new PropertyChangeHandler<Element>() {
			public void onChange(PropertyChangeEvent<Element> e) {
				log.enter("onchange(studyEntry)");
				syncUIWithStudyEntry(e.getAfter());
				log.leave();
			}
		}));

	
		this.modelChangeHandlerRegistrations.add(model.studyUrl.addChangeHandler(new PropertyChangeHandler<String>() {
			public void onChange(PropertyChangeEvent<String> e) {
				log.enter("onchange(studyUrl)");
				syncUIWithStudyUrl(e.getAfter());
				log.leave();
			}
		}));


	}
	
	
	@Override
	protected void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");
		
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
				studySummaryWidgetUiField.studyActionsUploadDataFilesWizardNavigationEventChannel.addHandler(new WidgetEventHandler<WidgetEvent>() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(studySummaryWidgetUiField.studyActionsUploadDataFilesWizardNavigationEventChannel)");
				owner.studyActionsUploadDataFilesWizardNavigationEventChannel.fireEvent(e);
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
				studySummaryWidgetUiField.studyActionsEditStudyQuestionnaireNavigationEventChannel.addHandler(new WidgetEventHandler<WidgetEvent>() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(studySummaryWidgetUiField.studyActionsEditStudyQuestionnaireNavigation)");
				owner.studyActionsEditStudyQuestionnaireNavigationEventChannel.fireEvent(e);
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
 		
		log.leave();
	}



	@Override
	protected void renderUI() {

		this.canvas.clear();
		this.canvas.add(uiBinder.createAndBindUi(this));
		
		pendingPanel.setVisible(true);	
	}
	

	@Override
	protected void bindUI(ViewStudyWidgetModel model) {
		super.bindUI(model);
		errorPanel.setVisible(false);	
	}

	@Override
	protected void syncUI() {
		syncUIWithStatus(model.status.get());
	}

	protected void syncUIWithStatus(Status status) {
		log.enter("syncUIWithStatus");		
		log.debug("status:");
		
		errorPanel.setVisible(false);	
		if (status == null) {
			// null before being set
			log.debug("Called with null status");
		}
		else if (status instanceof AsyncWidgetModel.InitialStatus) {
			pendingPanel.setVisible(true);	
		}
		
		//TODO Widget specific statii
		
		else if (status instanceof AsyncWidgetModel.ReadyStatus) {
			pendingPanel.setVisible(false);	
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
			pendingPanel.setVisible(false);	
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
		if (studyEntry != null) {

			log.debug("got study...." + studyEntry);
			
			model.status.set(ViewStudyWidgetModel.STATUS_READY_FOR_INTERACTION);
			studySummaryWidgetUiField.studyEntry.set(studyEntry);
			studySummaryWidgetUiField.studyActionsWidgetUiField.studyEntry.set(studyEntry);
			
		} else {
			model.message.set("study was null");
		}
		log.leave();
	}

	protected void syncStudyEntryElementWithStudyID(String studyID) {
		
		log.enter("syncStudyEntryElementWithStudyID");

		errorPanel.setVisible(false);	
		
		
		if (studyID != null) {

			log.debug("getting Entry Element from ID....");
			
			//FIXME: This can't be right. It's just setting studyEntryElement to null.
			
			Element studyEntryElement = null;
			
			model.studyEntry.set(studyEntryElement);
			
		} else {

			model.message.set("studyID was null");
		}
	
		
		log.leave();
	}		
	
	protected void syncUIWithStudyUrl(String studyUrl) {
		log.enter("syncUIWithStudyUrl");
		// TODO needs to be a method in an extension
		log.leave();
	}


}

