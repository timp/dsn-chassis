package org.cggh.chassis.wwarn.ui.curator.client;

import static org.cggh.chassis.generic.widget.client.HtmlElements.strongWidget;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ObservableProperty;
import org.cggh.chassis.generic.widget.client.PropertyChangeEvent;
import org.cggh.chassis.generic.widget.client.PropertyChangeHandler;
import org.cggh.chassis.generic.widget.client.WidgetEventChannel;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.wwarn.ui.common.client.RenderUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;


/**
 * @author timp
 *
 */
public class StudyActionsWidget 
	 	extends ChassisWidget {

	private static final Log log = LogFactory.getLog(StudyActionsWidget.class);
	

	@UiTemplate("StudyActionsWidget.ui.xml")
	interface StudyActionsWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, StudyActionsWidget> {
	}
	private static StudyActionsWidgetRendererUiBinder uiBinder = 
		GWT.create(StudyActionsWidgetRendererUiBinder.class);

	@UiField HTMLPanel mainPanel;
	@UiField HTMLPanel contentPanel;
	@UiField HTMLPanel pendingPanel;
	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;

	@UiField FlowPanel studyActionsPanel;
	
	public final ObservableProperty<Element> studyEntry = new ObservableProperty<Element>();

	public final ObservableProperty<Status> status = new ObservableProperty<Status>();
	public final ObservableProperty<String> message = new ObservableProperty<String>();

	public final WidgetEventChannel studyActionsListStudiesNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsUploadDataFilesWizardNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsViewStudyNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsViewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsEditStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsListStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);



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


	@Override
	protected void syncUI() {
		syncUIWithStatus(status.get());
        syncUIWithStudyEntry(studyEntry.get());
        registerHandlersForModelChanges();
    }

	protected void registerHandlersForModelChanges() {
		log.enter("registerHandlersForModelChanges");
		
		status.addChangeHandler(new PropertyChangeHandler<Status>() {
		
			public void onChange(PropertyChangeEvent<Status> e) {
				log.enter("onChange(status)");
			
				log.debug("Status " + e.getAfter());
				syncUIWithStatus(e.getAfter());
			
				log.leave();
			}
		});
		studyEntry.addChangeHandler(new PropertyChangeHandler<Element>() {
			
			public void onChange(PropertyChangeEvent<Element> e) {
				log.enter("onChange(Element)");
				syncUIWithStudyEntry(e.getAfter());
				log.leave();
			}
			
		});
		log.leave();
	}

	
	@Override
	public void refresh() {
		log.enter("refresh");
		
		// TODO refresh this
		log.leave();	
	}
	
	private void syncUIWithStudyEntry(final Element studyEntry){
		log.enter("syncUIWithStudyEntry");
		
		if (studyEntry == null) 
			log.debug("Study null");
		else {
			
			List<List<Widget>> rows = new ArrayList<List<Widget>>();

			Anchor listAllStudies = new Anchor("List all studies"); // i18n
			
			listAllStudies.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent e) {
					log.enter("onClick");
					
					studyActionsListStudiesNavigationEventChannel.fireEvent(new ListStudiesNavigationEvent());
					
					log.leave();
				}

			});

			Anchor viewStudy = new Anchor("View study"); // i18n
			
			viewStudy.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent e) {
					
					log.enter("onClick");
					
					ViewStudyNavigationEvent viewStudyNavigationEvent  = new ViewStudyNavigationEvent();
					viewStudyNavigationEvent.setStudy(studyEntry);
					
					studyActionsViewStudyNavigationEventChannel.fireEvent(viewStudyNavigationEvent);
					
					log.leave();
					
				}

			});

			Anchor uploadDataFilesWizard = new Anchor("Upload curated data files"); // i18n
			
			uploadDataFilesWizard.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent e) {
					
					log.enter("onClick");
					
					UploadDataFilesWizardNavigationEvent uploadDataFilesWizardNavigationEvent  = new UploadDataFilesWizardNavigationEvent();
					uploadDataFilesWizardNavigationEvent.setStudy(studyEntry);
					
					studyActionsUploadDataFilesWizardNavigationEventChannel.fireEvent(uploadDataFilesWizardNavigationEvent);
					
					log.leave();
					
				}

			});
			
			Anchor viewStudyQuestionnaire = new Anchor("View study questionnaire"); // i18n
			
			viewStudyQuestionnaire.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent e) {
					
					log.enter("onClick");
					
					ViewStudyQuestionnaireNavigationEvent viewStudyQuestionnaireNavigationEvent  = new ViewStudyQuestionnaireNavigationEvent();
					viewStudyQuestionnaireNavigationEvent.setStudy(studyEntry);
					
					studyActionsViewStudyQuestionnaireNavigationEventChannel.fireEvent(viewStudyQuestionnaireNavigationEvent);
					
					log.leave();
					
				}

			});
			
			Anchor editStudyQuestionnaire = new Anchor("Edit study questionnaire"); // i18n
			
			editStudyQuestionnaire.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent e) {
					
					log.enter("onClick");
					
					EditStudyQuestionnaireNavigationEvent editStudyQuestionnaireNavigationEvent  = new EditStudyQuestionnaireNavigationEvent();
					editStudyQuestionnaireNavigationEvent.setStudy(studyEntry);
					
					studyActionsEditStudyQuestionnaireNavigationEventChannel.fireEvent(editStudyQuestionnaireNavigationEvent);
					
					log.leave();
					
				}

			});
			
			Anchor listStudyRevisions = new Anchor("List study revisions"); // i18n
			
			listStudyRevisions.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent e) {
					
					log.enter("onClick");
					
					ListStudyRevisionsNavigationEvent listStudyRevisionsNavigationEvent  = new ListStudyRevisionsNavigationEvent();
					listStudyRevisionsNavigationEvent.setStudy(studyEntry);
					
					studyActionsListStudyRevisionsNavigationEventChannel.fireEvent(listStudyRevisionsNavigationEvent);
					
					log.leave();
					
				}

			});

			List<Widget> row = new ArrayList<Widget>();
			row.add(strongWidget("Actions")); // i18n
			row.add(listAllStudies);
			row.add(viewStudy);
			row.add(uploadDataFilesWizard);
			row.add(viewStudyQuestionnaire);
			row.add(editStudyQuestionnaire);
    		row.add(listStudyRevisions);
    			
    		rows.add(row);
    		
    		FlexTable table = RenderUtils.renderResultsTable(rows);
    		this.studyActionsPanel.clear();
    		this.studyActionsPanel.add(table);
    		
    		pendingPanel.setVisible(false);
		}
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
		else if (status instanceof AsyncWidgetModel.ReadyStatus) {
			pendingPanel.setVisible(false);	
		}			
		else if (status instanceof AsyncWidgetModel.ErrorStatus) {
			message.set("Error status given on asynchronous call.");
		}			
		else {
			message.set("Unhandled status:" + status);
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
		

}
