package org.cggh.chassis.wwarn.ui.curator.client;

import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.PropertyChangeEvent;
import org.cggh.chassis.generic.widget.client.PropertyChangeHandler;
import org.cggh.chassis.generic.widget.client.WidgetEvent;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

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
	protected void renderUI() {

		this.canvas.clear();
		this.canvas.add(uiBinder.createAndBindUi(this));
		
		pendingPanel.setVisible(true);	
	}
	

	@Override
	protected void syncUI() {
		syncUIWithStatus(model.status.get());
	}

	protected void syncUIWithStatus(Status status) {

		log.enter("syncUIWithStatus");		
		
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

			
			error("Error status given on asynchronous call.");
		}			
		
		else {

			error("Unhandled status:" + status);
		}

		log.leave();
	}
	


	@Override
	protected void registerHandlersForModelChanges() {
		
		this.modelChangeHandlerRegistrations.add(model.status.addChangeHandler(syncUIWithStatus));

		this.modelChangeHandlerRegistrations.add(model.studyEntry.addChangeHandler(new PropertyChangeHandler<Element>() {
			public void onChange(PropertyChangeEvent<Element> e) {
				log.enter("onchange(studyEntry)");
				syncUIWithStudyEntryElement(e.getAfter());
				log.leave();
			}
		}));
		
		this.modelChangeHandlerRegistrations.add(model.studyID.addChangeHandler(new PropertyChangeHandler<String>() {
			public void onChange(PropertyChangeEvent<String> e) {
				
				log.enter("onChange(studyID)");
				
				syncStudyEntryElementWithStudyID(e.getAfter());
				
				log.leave();
				
			}
		}));			
		

	}


	@Override
	protected void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");
		this.childWidgetEventHandlerRegistrations.add(
				studySummaryWidgetUiField.studyActionsListStudiesNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent");
				log.debug("fire list studies on " + owner.studyActionsListStudiesNavigationEventChannel);
				owner.studyActionsListStudiesNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
		this.childWidgetEventHandlerRegistrations.add(
				studySummaryWidgetUiField.studyActionsViewStudyNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent");
				log.debug("fire view study on " + owner.studyActionsViewStudyNavigationEventChannel);
				owner.studyActionsViewStudyNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
		log.leave();
	}
	protected final PropertyChangeHandler<Status> syncUIWithStatus = new PropertyChangeHandler<Status>() {
			
			public void onChange(PropertyChangeEvent<Status> e) {
	
			log.enter("onChange<Status>");		
			
			Status status = e.getAfter();
	
			errorPanel.setVisible(false);	
			pendingPanel.setVisible(false);	
			contentPanel.setVisible(false);
			
			if (status instanceof AsyncWidgetModel.InitialStatus) {
	
				syncUIWithStudyEntryElement(model.studyEntry.get());
				pendingPanel.setVisible(true);	
			}
			
			else if (status instanceof ViewStudyWidgetModel.StudyEntryElementRetrievedStatus) {
				
				syncUIWithStudyEntryElement(model.studyEntry.get());
				contentPanel.setVisible(true);
				
			}
			
			else if (status instanceof ViewStudyWidgetModel.RetrieveStudyPendingStatus) {
	
				pendingPanel.setVisible(true);
			}
			
			else if (status instanceof ViewStudyWidgetModel.StudyEntryElementNotFoundStatus) {
				
				log.error("StudyEntryElementNotFoundStatus");
				
				error("Could not find the entry corresponding to the study id in memory.");	
			}		
			
			else if (status instanceof AsyncWidgetModel.ErrorStatus) {
	
				error("Error status given on asynchronous call.");
				
			}			
			
			else {
	
				error("Unhandled status:" + status);
			}
			
			log.leave();
		}
			
	};
	
	/** Clear and re-initialise, setting selected id. 
	 * @param element */
	void syncUIWithStudyEntryElement(Element study) {
		
		log.enter("syncUIWithStudyEntryElement");
		
		if (study != null) {

			log.debug("got study....");
			
			model.status.set(ViewStudyWidgetModel.STATUS_STUDY_RETRIEVED);
			owner.renderer.studySummaryWidgetUiField.studyEntry.set(study);
			owner.renderer.studySummaryWidgetUiField.studyActionsWidgetUiField.studyEntry.set(study);
			
		} else {
			error("study was null");
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

			error("studyID was null");
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

