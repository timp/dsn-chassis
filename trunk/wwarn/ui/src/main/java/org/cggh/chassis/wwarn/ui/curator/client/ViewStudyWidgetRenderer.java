package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.PropertyChangeEvent;
import org.cggh.chassis.generic.widget.client.PropertyChangeHandler;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.xml.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;

public class ViewStudyWidgetRenderer extends
		ChassisWidgetRenderer<ViewStudyWidgetModel> {

	private Log log = LogFactory.getLog(ViewStudyWidgetRenderer.class);
	
	@UiTemplate("ViewStudyWidget.ui.xml")
	interface ViewStudyWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, ViewStudyWidgetRenderer> {
	}
	private static ViewStudyWidgetRendererUiBinder uiBinder = 
		GWT.create(ViewStudyWidgetRendererUiBinder.class);

	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessages; // in errorPanel
	@UiField HTMLPanel pendingPanel;
	@UiField Label pendingMessage; // in pendingPanel
	@UiField HTMLPanel contentPanel;

	
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
		
	}
	
	@Override
	protected void registerHandlersForModelChanges() {
		
		model.status.addChangeHandler(syncUIWithStatus);

		model.studyEntryElement.addChangeHandler(new PropertyChangeHandler<Element>() {
			public void onChange(PropertyChangeEvent<Element> e) {
				syncUIWithStudyEntryElement(e.getAfter());
			}
		});
		
		model.studyID.addChangeHandler(new PropertyChangeHandler<String>() {
			public void onChange(PropertyChangeEvent<String> e) {
				
				log.enter("onChange");
				
				syncStudyEntryElementWithStudyID(e.getAfter());
				
				log.leave();
				
			}



		});			
		
	}

	@Override
	protected void syncUI() {
	}

	protected final PropertyChangeHandler<Status> syncUIWithStatus = new PropertyChangeHandler<Status>() {
			
			public void onChange(PropertyChangeEvent<Status> e) {
	
			log.enter("onChange");		
			
			Status status = e.getAfter();
	
			errorPanel.setVisible(false);	
			pendingPanel.setVisible(false);	
			contentPanel.setVisible(false);
			
			if (status instanceof AsyncWidgetModel.InitialStatus) {
	
				syncUIWithStudyEntryElement(model.studyEntryElement.get());
			}
			
			else if (status instanceof ViewStudyWidgetModel.StudyEntryElementRetrievedStatus) {
				
				contentPanel.setVisible(true);
				
			}
			
			else if (status instanceof ViewStudyWidgetModel.RetrieveStudyPendingStatus) {
	
				pendingPanel.setVisible(true);
			}
			
			else if (status instanceof ViewStudyWidgetModel.StudyEntryElementNotFoundStatus) {
				
				log.error("StudyEntryElementNotFoundStatus");
				
				showError("Could not find the entry corresponding to the study id in memory.");	
			}		
			
			else if (status instanceof AsyncWidgetModel.ErrorStatus) {
	
				showError("Error status given on asynchronous call.");
				
			}			
			
			else {
	
				showError("Unhandled status:" + status);
			}
			
			log.leave();
		}
			
	};
	
	/** Clear and re-initialise, setting selected id. 
	 * @param element */
	void syncUIWithStudyEntryElement(Element study) {
		
		log.enter("syncUiWithStudy");
		
		if (study != null) {

			log.debug("got study....");
			
			model.status.set(ViewStudyWidgetModel.STATUS_STUDY_RETRIEVED);
			
			
		} else {
			// This may be a transient error, because a file has not been selected yet, but the widget has loaded.
			showError("study was null");
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
			
			model.studyEntryElement.set(studyEntryElement);
			
		} else {

			showError("studyID was null");
		}
	
		
		log.leave();
	}		
	
	public void showError(String err) {
		errorMessages.add(new HTML(err));
		errorPanel.setVisible(true);
	}
	
	
}

