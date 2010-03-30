package org.cggh.chassis.wwarn.ui.curator.client;
import static org.cggh.chassis.generic.widget.client.HtmlElements.strongWidget;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.ext.ChassisHelper;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
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

	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;

	@UiField FlowPanel studySummaryPanel;
	
    @UiField StudyActionsWidget studyActionsWidgetUiField;


	public final ObservableProperty<String> studyUrl = new ObservableProperty<String>();

	public final ObservableProperty<Element> studyEntry = new ObservableProperty<Element>();


	public final ObservableProperty<String> message = new ObservableProperty<String>();

	public final WidgetEventChannel studyActionsListStudiesNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsViewStudyNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsViewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsListStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsEditStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsUploadDataFilesWizardNavigationEventChannel = new WidgetEventChannel(this);



	
	@Override
	public void refresh() {
		log.enter("refresh");
		
		studyActionsWidgetUiField.refresh();

		log.leave();	
	}
	

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
				studyActionsWidgetUiField.studyActionsListStudiesNavigationEventChannel.addHandler(new WidgetEventHandler<WidgetEvent>() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(StudyActionsListStudiesNavigation)");
				studyActionsListStudiesNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
 		this.childWidgetEventHandlerRegistrations.add(
				studyActionsWidgetUiField.studyActionsViewStudyNavigationEventChannel.addHandler(new WidgetEventHandler<WidgetEvent>() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(StudyActionsViewStudyNavigation)");
				studyActionsViewStudyNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
 		this.childWidgetEventHandlerRegistrations.add(
				studyActionsWidgetUiField.studyActionsViewStudyQuestionnaireNavigationEventChannel.addHandler(new WidgetEventHandler<WidgetEvent>() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(StudyActionsViewStudyQuestionnaireNavigation)");
				studyActionsViewStudyQuestionnaireNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
 		this.childWidgetEventHandlerRegistrations.add(
				studyActionsWidgetUiField.studyActionsListStudyRevisionsNavigationEventChannel.addHandler(new WidgetEventHandler<WidgetEvent>() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(StudyActionsListStudyRevisionsNavigation)");
				studyActionsListStudyRevisionsNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
 		this.childWidgetEventHandlerRegistrations.add(
				studyActionsWidgetUiField.studyActionsEditStudyQuestionnaireNavigationEventChannel.addHandler(new WidgetEventHandler<WidgetEvent>() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(StudyActionsEditStudyQuestionnaireNavigation)");
				studyActionsEditStudyQuestionnaireNavigationEventChannel.fireEvent(e);
				log.leave();
			}
		}));
 		this.childWidgetEventHandlerRegistrations.add(
				studyActionsWidgetUiField.studyActionsUploadDataFilesWizardNavigationEventChannel.addHandler(new WidgetEventHandler<WidgetEvent>() {
			public void onEvent(WidgetEvent e) {
				log.enter("onEvent(StudyActionsUploadDataFilesWizardNavigation)");
				studyActionsUploadDataFilesWizardNavigationEventChannel.fireEvent(e);
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
        syncUIWithStudyEntry(studyEntry.get());
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
		
	
	
	
	
	private void syncUIWithStudyEntry(Element studyEntry){
		log.enter("syncUIWithStudyEntry");
		
		if (studyEntry == null) 
			log.debug("Study null");
		else {
			
			List<List<Widget>> rows = new ArrayList<List<Widget>>();
			
			List<Widget> headerRow = new ArrayList<Widget>();
			headerRow.add(strongWidget("Study Title")); // i18n
			headerRow.add(strongWidget("Modules"));     // i18n
			headerRow.add(strongWidget("Submitters"));  // i18n
			headerRow.add(strongWidget("Published"));   // i18n
			headerRow.add(strongWidget("Updated"));     // i18n
			
			rows.add(headerRow);
			
			List<Widget> row = new ArrayList<Widget>();
			row.add(new HTML(ChassisHelper.getTitle(studyEntry)));
			row.add(new HTML(RenderUtils.join(ChassisHelper.getModules(studyEntry), ", ")));
			row.add(new HTML(RenderUtils.join(ChassisHelper.getAuthorEmails(studyEntry), ", ")));
			row.add(new HTML(ChassisHelper.getPublishedAsDate(studyEntry)));
			row.add(new HTML(ChassisHelper.getUpdatedAsDate(studyEntry)));
			
			rows.add(row);
			
			//FlexTable table = RenderUtils.renderResultsTable(rows);
			FlexTable table = RenderUtils.renderFirstRowHeadingResultsAsFirstColumnHeadingTable(rows);
			
			this.studySummaryPanel.clear();
			this.studySummaryPanel.add(table);
		}
	}



}
