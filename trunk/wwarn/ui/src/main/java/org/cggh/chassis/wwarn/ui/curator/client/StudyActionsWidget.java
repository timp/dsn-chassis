package org.cggh.chassis.wwarn.ui.curator.client;

import static org.cggh.chassis.generic.widget.client.HtmlElements.strongWidget;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.wwarn.ui.common.client.RenderUtils;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

/**
 * @author timp
 *
 */
public class StudyActionsWidget 
	 	extends StudyActionsWidgetBase {

	/* Override this in a separate class, as the Base class can be regenerated. */
	Widget renderStudyEntry(final Element studyEntry){ 
		log.enter("syncUIWithStudyEntry");

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
				viewStudyNavigationEvent.setStudyEntry(studyEntry);
				
				studyActionsViewStudyNavigationEventChannel.fireEvent(viewStudyNavigationEvent);
				
				log.leave();
				
			}

		});

		Anchor uploadDataFilesWizard = new Anchor("Upload curated data files"); // i18n
		
		uploadDataFilesWizard.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent e) {
				
				log.enter("onClick");
				
				UploadDataFilesWizardNavigationEvent uploadDataFilesWizardNavigationEvent  = new UploadDataFilesWizardNavigationEvent();
				uploadDataFilesWizardNavigationEvent.setStudyEntry(studyEntry);
				
				studyActionsUploadDataFilesWizardNavigationEventChannel.fireEvent(uploadDataFilesWizardNavigationEvent);
				
				log.leave();
				
			}

		});
		
		Anchor viewStudyQuestionnaire = new Anchor("View study questionnaire"); // i18n
		
		viewStudyQuestionnaire.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent e) {
				
				log.enter("onClick");
				
				ViewStudyQuestionnaireNavigationEvent viewStudyQuestionnaireNavigationEvent  = new ViewStudyQuestionnaireNavigationEvent();
				viewStudyQuestionnaireNavigationEvent.setStudyEntry(studyEntry);
				
				studyActionsViewStudyQuestionnaireNavigationEventChannel.fireEvent(viewStudyQuestionnaireNavigationEvent);
				
				log.leave();
				
			}

		});
		
		Anchor editStudyQuestionnaire = new Anchor("Edit study questionnaire"); // i18n
		
		editStudyQuestionnaire.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent e) {
				
				log.enter("onClick");
				
				EditStudyQuestionnaireNavigationEvent editStudyQuestionnaireNavigationEvent  = new EditStudyQuestionnaireNavigationEvent();
				editStudyQuestionnaireNavigationEvent.setStudyEntry(studyEntry);
				
				studyActionsEditStudyQuestionnaireNavigationEventChannel.fireEvent(editStudyQuestionnaireNavigationEvent);
				
				log.leave();
				
			}

		});
		
		Anchor listStudyRevisions = new Anchor("List study revisions"); // i18n
		
		listStudyRevisions.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent e) {
				
				log.enter("onClick");
				
				ListStudyRevisionsNavigationEvent listStudyRevisionsNavigationEvent  = new ListStudyRevisionsNavigationEvent();
				listStudyRevisionsNavigationEvent.setStudyEntry(studyEntry);
				
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
		log.leave();
		return table;
	}

	
}
