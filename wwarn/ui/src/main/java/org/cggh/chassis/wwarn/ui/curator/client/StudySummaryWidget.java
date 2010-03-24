package org.cggh.chassis.wwarn.ui.curator.client;

import static org.cggh.chassis.generic.widget.client.HtmlElements.strongWidget;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.ext.ChassisHelper;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import com.google.gwt.xml.client.Element;

import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ObservableProperty;
import org.cggh.chassis.generic.widget.client.PropertyChangeEvent;
import org.cggh.chassis.generic.widget.client.PropertyChangeHandler;
import org.cggh.chassis.generic.widget.client.WidgetEvent;
import org.cggh.chassis.generic.widget.client.WidgetEventChannel;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.wwarn.ui.common.client.RenderUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;


import org.cggh.chassis.generic.widget.client.WidgetEventChannel;


/**
 * @author timp
 *
 */
public class StudySummaryWidget 
	 	extends ChassisWidget {

	private static final Log log = LogFactory.getLog(StudySummaryWidget.class);
	




	@UiTemplate("StudySummaryWidget.ui.xml")
	interface StudySummaryWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, StudySummaryWidget> {
	}
	private static StudySummaryWidgetRendererUiBinder uiBinder = 
		GWT.create(StudySummaryWidgetRendererUiBinder.class);

	@UiField HTMLPanel mainPanel;
	@UiField HTMLPanel contentPanel;
	@UiField HTMLPanel pendingPanel;
	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;

	@UiField FlowPanel studySummaryPanel;
	
    @UiField StudyActionsWidget studyActionsWidgetUiField;


	public final ObservableProperty<Element> studyEntry = new ObservableProperty<Element>();

	public final ObservableProperty<Status> status = new ObservableProperty<Status>();
	public final ObservableProperty<String> message = new ObservableProperty<String>();

	public final WidgetEventChannel studyActionsViewStudyNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsEditStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsListStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsViewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsListStudiesNavigationEventChannel = new WidgetEventChannel(this);
	


	
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
				studyActionsWidgetUiField.studyActionsViewStudyNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(studyActionsViewStudy)");
				studyActionsViewStudyNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
		this.childWidgetEventHandlerRegistrations.add(
				studyActionsWidgetUiField.studyActionsEditStudyQuestionnaireNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(studyActionsEditStudy)");
				studyActionsEditStudyQuestionnaireNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
		this.childWidgetEventHandlerRegistrations.add(
				studyActionsWidgetUiField.studyActionsListStudyRevisionsNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(studyActionsListStudyRevisions)");
				studyActionsListStudyRevisionsNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
		this.childWidgetEventHandlerRegistrations.add(
				studyActionsWidgetUiField.studyActionsViewStudyQuestionnaireNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(studyActionsViewStudyQuestionnaire)");
				studyActionsViewStudyQuestionnaireNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
		this.childWidgetEventHandlerRegistrations.add(
				studyActionsWidgetUiField.studyActionsListStudiesNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(studyActionsListStudies)");
				studyActionsListStudiesNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
		
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

	private void syncUIWithStudyEntry(Element study){
		log.enter("syncUIWithStudyEntry");
		
		if (study == null) 
			log.debug("Study null");
		else {
			
			List<Widget[]> rows = new ArrayList<Widget[]>();
			
			Widget[] headerRow = {
				strongWidget("Study Title"), // TODO i18n
				strongWidget("Modules"),     // TODO i18n
				strongWidget("Submitters"),  // TODO i18n
				strongWidget("Published"),   // TODO i18n
				strongWidget("Updated"),     // TODO i18n
			};
			
			rows.add(headerRow);
			
			Widget[] row = {
						new HTML(ChassisHelper.getTitle(study)),
						new HTML(RenderUtils.join(ChassisHelper.getModules(study), ", ")),
						new HTML(RenderUtils.join(ChassisHelper.getAuthorEmails(study), ", ")),
						new HTML(ChassisHelper.getPublishedAsDate(study)),
						new HTML(ChassisHelper.getUpdatedAsDate(study)),
			};
			rows.add(row);
			
			//FlexTable table = RenderUtils.renderResultsTable(rows);
			FlexTable table = RenderUtils.renderFirstRowHeadingResultsAsFirstColumnHeadingTable(rows);
			
			this.studySummaryPanel.clear();
			this.studySummaryPanel.add(table);
			pendingPanel.setVisible(false);	
		}
	}
	@Override
	public void refresh() {
		log.enter("refresh");
		
		// TODO refresh this

		studyActionsWidgetUiField.refresh();

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
