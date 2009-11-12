package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.Iterator;

import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;

/**
 * A default renderer for the view study questionnaire widget.
 * 
 * @author aliman
 *
 */
class EditStudyQuestionnaireWidgetDefaultRenderer implements StudyQuestionnaireWidgetRenderer {

	
	
	
	/**
	 * 
	 */
	private final EditStudyQuestionnaireWidget owner;




	private Log log = LogFactory.getLog(this.getClass());


	
	
	private Panel canvas;
	private Panel questionnairePanel;
	private Label titleLabel = new InlineLabel();
	private Label summaryLabel = new InlineLabel();
	private FlowPanel mainPanel;
	private FlowPanel statusPanel;
	private InlineLabel statusLabel;
	private FlowPanel studyInfoPanel;
	private FlowPanel modulesListPanel = new FlowPanel();
	private FlowPanel ownersListPanel = new FlowPanel();
	public FlowPanel titlePanel;
	public FlowPanel summaryPanel;
	public FlowPanel modulesPanel;
	public FlowPanel ownersPanel;
	private FlowPanel buttonsPanel1;
	private FlowPanel buttonsPanel2;
	private Button saveButton1;
	private Button cancelButton1;
	private Button saveButton2;
	private Button cancelButton2;



	
	private StudyQuestionnaireWidgetController controller;








	
	
	/**
	 * @param owner TODO
	 * 
	 */
	EditStudyQuestionnaireWidgetDefaultRenderer(EditStudyQuestionnaireWidget owner, StudyQuestionnaireWidgetController controller) {
		this.owner = owner;
		this.controller = controller;
		this.constructCanvas();
	}
	
	
	
	
	/**
	 * 
	 */
	private void constructCanvas() {
		
		this.constructLoadingPanel();
		this.constructMainPanel();
		
		canvas = new FlowPanel();
		canvas.add(statusPanel);
		canvas.add(mainPanel);

	}


	

	/**
	 * 
	 */
	private void constructLoadingPanel() {
		
		statusPanel = new FlowPanel();
		statusPanel.setVisible(false);
		statusPanel.addStyleName(CommonStyles.COMMON_LOADING);
		statusLabel = new InlineLabel();
		statusPanel.add(statusLabel);
		
	}




	/**
	 * 
	 */
	private void constructMainPanel() {
		
		mainPanel = new FlowPanel();
		mainPanel.setVisible(false);
		
		mainPanel.add(new HTML("<h2>Edit Study Questionnaire</h2>"));

		this.constructStudyInfoPanel();
		mainPanel.add(studyInfoPanel);
		
		this.constructButtonsPanels();
		mainPanel.add(buttonsPanel1);

		questionnairePanel = new FlowPanel();
		mainPanel.add(questionnairePanel);
		
		mainPanel.add(buttonsPanel2);
		
	}




	/**
	 * 
	 */
	private void constructStudyInfoPanel() {
		
		studyInfoPanel = new FlowPanel();

		titlePanel = new FlowPanel();
		titlePanel.add(new InlineLabel("Study title: "));
		titleLabel.addStyleName(CommonStyles.COMMON_ANSWER);
		titleLabel.addStyleName(CommonStyles.VIEWSTUDY_TITLE);
		titlePanel.add(titleLabel);
		titlePanel.addStyleName(CommonStyles.COMMON_QUESTION);
		studyInfoPanel.add(titlePanel);
		
		summaryPanel = new FlowPanel();
		summaryPanel.add(new InlineLabel("Summary: "));
		summaryLabel.addStyleName(CommonStyles.COMMON_ANSWER);
		summaryLabel.addStyleName(CommonStyles.VIEWSTUDY_SUMMARY);
		summaryPanel.add(summaryLabel);
		summaryPanel.addStyleName(CommonStyles.COMMON_QUESTION);
		studyInfoPanel.add(summaryPanel);
		
		modulesPanel = new FlowPanel();
		modulesPanel.add(new InlineLabel("Modules:"));
		modulesListPanel.addStyleName(CommonStyles.VIEWSTUDY_MODULES);
		modulesPanel.add(modulesListPanel);
		modulesPanel.addStyleName(CommonStyles.COMMON_QUESTION);
		studyInfoPanel.add(modulesPanel);

		ownersPanel = new FlowPanel();
		ownersPanel.add(new InlineLabel("Owners:"));
		ownersListPanel.addStyleName(CommonStyles.VIEWSTUDY_OWNERS);
		ownersPanel.add(ownersListPanel);
		ownersPanel.addStyleName(CommonStyles.COMMON_QUESTION);
		studyInfoPanel.add(ownersPanel);

		// hide these for now
		summaryPanel.setVisible(false);
		ownersPanel.setVisible(false);

	}

	
	
	/**
	 * 
	 */
	private void constructButtonsPanels() {
		
		ClickHandler saveButtonClickHandler = new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				controller.saveQuestionnaire().addCallback(new Function<StudyEntry, StudyEntry>() {

					public StudyEntry apply(StudyEntry in) {
						owner.fireOnStudyQuestionnaireUpdateSuccess();
						return in;
					}

				});
			}
			
		};
		
		ClickHandler cancelButtonClickHandler = new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				owner.fireOnUserActionEditStudyQuestionnaireCancelled();
			}
			
		};
			
		saveButton1 = new Button("Save Changes");
		saveButton1.addClickHandler(saveButtonClickHandler);
		
		cancelButton1 = new Button("Cancel");
		cancelButton1.addClickHandler(cancelButtonClickHandler);
		
		saveButton2 = new Button("Save Changes");
		saveButton2.addClickHandler(saveButtonClickHandler);
		
		cancelButton2 = new Button("Cancel");
		cancelButton2.addClickHandler(cancelButtonClickHandler);

		buttonsPanel1 = new FlowPanel();
		buttonsPanel2 = new FlowPanel();

		buttonsPanel1.add(saveButton1);
		buttonsPanel1.add(cancelButton1);

		buttonsPanel2.add(saveButton2);
		buttonsPanel2.add(cancelButton2);

	}








	public Panel getCanvas() {
		return canvas;
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.studyquestionnaire.client.ViewStudyQuestionnaireWidget.StudyQuestionnaireWidgetModelListener#onStatusChanged(int, int)
	 */
	public void onStatusChanged(int before, int after) {
		log.enter("onStatusChanged( "+before+" , "+after+" )");
		
		if (before == StudyQuestionnaireWidgetModel.STATUS_INITIAL && after == StudyQuestionnaireWidgetModel.STATUS_LOADINGQUESTIONNAIRE) {
			statusPanel.setVisible(true);
			statusLabel.setText("loading questionnaire...");
		}
		else if (before == StudyQuestionnaireWidgetModel.STATUS_LOADINGQUESTIONNAIRE && after == StudyQuestionnaireWidgetModel.STATUS_READY) {
			questionnairePanel.add(owner.questionnaire);
			statusLabel.setText("questionnaire loaded");
		}
		else if (before == StudyQuestionnaireWidgetModel.STATUS_READY && after == StudyQuestionnaireWidgetModel.STATUS_INITIALISINGQUESTIONNAIRE) {
			statusLabel.setText("initialising questionnaire...");
		}
		else if (before == StudyQuestionnaireWidgetModel.STATUS_INITIALISINGQUESTIONNAIRE && after == StudyQuestionnaireWidgetModel.STATUS_READY) {
			statusPanel.setVisible(false);
			mainPanel.setVisible(true);
		}
		else if (before == StudyQuestionnaireWidgetModel.STATUS_READY && after == StudyQuestionnaireWidgetModel.STATUS_SAVING) {
			mainPanel.setVisible(false);
			statusPanel.setVisible(true);
			statusLabel.setText("saving...");
		}
		else if (before == StudyQuestionnaireWidgetModel.STATUS_SAVING && after == StudyQuestionnaireWidgetModel.STATUS_READY) {
			statusLabel.setText("study questionnaire saved OK");
		}
		else if (after == StudyQuestionnaireWidgetModel.STATUS_ERROR) {
			mainPanel.setVisible(false);
			statusPanel.setVisible(true);
			statusLabel.setText("an unexpected error has occurred");
		}
		else {
			throw new Error("unexpected state transition");
		}
		
		log.leave();
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.studyquestionnaire.client.ViewStudyQuestionnaireWidget.StudyQuestionnaireWidgetModelListener#onStudyEntryChanged(org.cggh.chassis.generic.atom.study.client.format.StudyEntry, org.cggh.chassis.generic.atom.study.client.format.StudyEntry)
	 */
	public void onStudyEntryChanged(StudyEntry before, StudyEntry entry) {

		titleLabel.setText(entry.getTitle());
		summaryLabel.setText(entry.getSummary());
		
		modulesListPanel.clear();
		
		String modulesContent = "";
		for (Iterator<String> it = entry.getStudy().getModules().iterator(); it.hasNext(); ) {
			String ml = Configuration.getModules().get(it.next());
			modulesContent += ml;
			if (it.hasNext()) {
				modulesContent += ", ";
			}
		}
		
		InlineLabel modulesAnswer = new InlineLabel(modulesContent);
		modulesAnswer.addStyleName(CommonStyles.COMMON_ANSWER);
		modulesListPanel.add(modulesAnswer);

		ownersListPanel.clear();
		
		String authorsContent = "";
		for (Iterator<AtomAuthor> it = entry.getAuthors().iterator(); it.hasNext(); ) {
			authorsContent += it.next().getEmail();
			if (it.hasNext()) {
				authorsContent += ", ";
			}
		}

		InlineLabel authorsAnswer = new InlineLabel(authorsContent);
		authorsAnswer.addStyleName(CommonStyles.COMMON_ANSWER);
		ownersListPanel.add(authorsAnswer);
		
	}
	
	
	
	
}