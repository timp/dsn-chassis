package org.cggh.chassis.wwarn.ui.curator.client;

import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.PropertyChangeEvent;
import org.cggh.chassis.generic.widget.client.PropertyChangeHandler;
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

/**
 * @author timp
 * @since 13 Jan 2010
 */
public class CurrentStudyRevisionWidgetRenderer extends
		ChassisWidgetRenderer<CurrentStudyRevisionWidgetModel> {

	private Log log = LogFactory.getLog(CurrentStudyRevisionWidgetRenderer.class);
	
	@UiTemplate("CurrentStudyRevisionWidget.ui.xml")
	interface CurrentStudyRevisionWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, CurrentStudyRevisionWidgetRenderer> {
	}
	private static CurrentStudyRevisionWidgetRendererUiBinder uiBinder = 
		GWT.create(CurrentStudyRevisionWidgetRendererUiBinder.class);

	//@UiField HTMLPanel bodyPanel;
	//@UiField FlowPanel mainActionsPanel;
	@UiField HTMLPanel pendingPanel;
	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;
	

	private CurrentStudyRevisionWidget owner;
	private CurrentStudyRevisionWidgetController controller;

	public CurrentStudyRevisionWidgetRenderer(CurrentStudyRevisionWidget owner) {
		this.owner = owner;
	}

	public void setController(CurrentStudyRevisionWidgetController controller) {
		this.controller = controller;
	}

	@Override
	protected void renderUI() {

		this.canvas.clear();
		this.canvas.add(uiBinder.createAndBindUi(this));
		
	}
	
	@Override
	protected void registerHandlersForModelChanges() {
		/*
		model.addStatusChangeHandler(new StatusChangeHandler() {
			public void onStatusChanged(StatusChangeEvent e) {
				syncUIWithStatus(e.getAfter());
			}
		});
		
		model.studyFeed.addChangeHandler(new PropertyChangeHandler<Document>() {
			public void onChange(PropertyChangeEvent<Document> e) {
				syncUiWithStudyFeedDoc(e.getAfter());
			}
		});
		*/
	}

	@Override
	protected void syncUI() {
		/*
		syncUIWithStatus(model.getStatus());
		syncUiWithStudyFeedDoc(model.getStudyFeedDoc());
		*/
	}

	protected void syncUIWithStatus(Status status) {

		log.enter("syncUIWithStatus");		
		
		// Hide everything (that is made visible here) first, then show as required.
		pendingPanel.setVisible(false);
		errorPanel.setVisible(false);
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {
			
		}
		/*
		else if (status instanceof CurrentStudyRevisionWidgetModel.RetrieveFeedPendingStatus) {
			pendingPanel.setVisible(true);
		} 
		else if (status instanceof CurrentStudyRevisionWidgetModel.StudiesRetrievedStatus) {
			
			
		}
		else if (status instanceof CurrentStudyRevisionWidgetModel.CreateEntryPendingStatus) {
			pendingPanel.setVisible(true);
		}
		else if (status instanceof CurrentStudyRevisionWidgetModel.StudyCreatedStatus) {
			
			
		}
		*/
		else if (status instanceof AsyncWidgetModel.ErrorStatus) {
			
			error("Error status: " + status + " " + model.message);
			errorPanel.setVisible(true);
		}
		else { 
			error("Unexpected status: " + status);
			errorPanel.setVisible(true);
		}
		log.leave();
	}
	
	/** Clear and re-initialise, setting selected id. 
	 * @param studyFeedDoc */
	void syncUiWithStudyFeedDoc(Document studyFeedDoc) {
		
		log.enter("syncUiWithStudyFeedDoc");
		
		
		log.leave();
	}
	
	
	
	
	
	@Override
	public void registerHandlersForChildWidgetEvents() {
		
		ChangeHandler studySelectedChangeHandler = new ChangeHandler() {

			public void onChange(ChangeEvent event) {
			}

		};
		
		
	}

	public void error(String err) {

		errorMessage.clear();
		errorMessage.add(new HTML(err));
	}
	
	
}

