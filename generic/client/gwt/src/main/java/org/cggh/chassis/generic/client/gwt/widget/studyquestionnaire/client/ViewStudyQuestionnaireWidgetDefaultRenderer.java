package org.cggh.chassis.generic.client.gwt.widget.studyquestionnaire.client;

import java.util.Iterator;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.client.gwt.common.client.CSS;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
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
class ViewStudyQuestionnaireWidgetDefaultRenderer implements StudyQuestionnaireWidgetRenderer {

	
	
	
	/**
	 * 
	 */
	private final ViewStudyQuestionnaireWidget owner;




	private Log log = LogFactory.getLog(this.getClass());


	
	
	private Panel canvas;
	private Panel actionsPanel;
	private Panel questionnairePanel;
	private Label titleLabel = new InlineLabel();
	private Label summaryLabel = new InlineLabel();
	private FlowPanel mainPanel;
	private Anchor actionViewStudy;
	private Anchor actionEditStudyQuestionnaire;
	private FlowPanel statusPanel;
	private InlineLabel statusLabel;
	private FlowPanel studyInfoPanel;
	private FlowPanel modulesListPanel = new FlowPanel();
	private FlowPanel ownersListPanel = new FlowPanel();
	public FlowPanel titlePanel;
	public FlowPanel summaryPanel;
	public FlowPanel modulesPanel;
	public FlowPanel ownersPanel;


	
	
	/**
	 * @param viewStudyQuestionnaireWidget TODO
	 * 
	 */
	ViewStudyQuestionnaireWidgetDefaultRenderer(ViewStudyQuestionnaireWidget viewStudyQuestionnaireWidget) {
		owner = viewStudyQuestionnaireWidget;
		this.constructCanvas();
	}
	
	
	
	
	/**
	 * 
	 */
	private void constructCanvas() {
		
		this.constructLoadingPanel();
		this.constructMainPanel();
		this.constructActionsPanel();
		
		canvas = new FlowPanel();
		canvas.add(statusPanel);
		canvas.add(mainPanel);
		canvas.add(actionsPanel);

	}


	

	/**
	 * 
	 */
	private void constructLoadingPanel() {
		
		statusPanel = new FlowPanel();
		statusPanel.setVisible(false);
		statusPanel.addStyleName(CSS.COMMON_LOADING);
		statusLabel = new InlineLabel();
		statusPanel.add(statusLabel);
		
	}




	/**
	 * 
	 */
	private void constructMainPanel() {
		
		mainPanel = new FlowPanel();
		mainPanel.setVisible(false);
		mainPanel.addStyleName(CSS.COMMON_MAINWITHACTIONS);
		
		mainPanel.add(new HTML("<h2>View Study Questionnaire</h2>"));

		this.constructStudyInfoPanel();
		mainPanel.add(studyInfoPanel);
		
		questionnairePanel = new FlowPanel();
		mainPanel.add(questionnairePanel);
		
	}




	/**
	 * 
	 */
	private void constructStudyInfoPanel() {
		
		studyInfoPanel = new FlowPanel();

		titlePanel = new FlowPanel();
		titlePanel.add(new InlineLabel("Study title: "));
		titleLabel.addStyleName(CSS.COMMON_ANSWER);
		titleLabel.addStyleName(CSS.VIEWSTUDY_TITLE);
		titlePanel.add(titleLabel);
		titlePanel.addStyleName(CSS.COMMON_QUESTION);
		studyInfoPanel.add(titlePanel);
		
		summaryPanel = new FlowPanel();
		summaryPanel.add(new InlineLabel("Summary: "));
		summaryLabel.addStyleName(CSS.COMMON_ANSWER);
		summaryLabel.addStyleName(CSS.VIEWSTUDY_SUMMARY);
		summaryPanel.add(summaryLabel);
		summaryPanel.addStyleName(CSS.COMMON_QUESTION);
		studyInfoPanel.add(summaryPanel);

		modulesPanel = new FlowPanel();
		modulesPanel.add(new InlineLabel("Modules:"));
		modulesListPanel.addStyleName(CSS.VIEWSTUDY_MODULES);
		modulesPanel.add(modulesListPanel);
		modulesPanel.addStyleName(CSS.COMMON_QUESTION);
		studyInfoPanel.add(modulesPanel);

		ownersPanel = new FlowPanel();
		ownersPanel.add(new InlineLabel("Owners:"));
		ownersListPanel.addStyleName(CSS.VIEWSTUDY_OWNERS);
		ownersPanel.add(ownersListPanel);
		ownersPanel.addStyleName(CSS.COMMON_QUESTION);
		studyInfoPanel.add(ownersPanel);

		// hide these for now
		summaryPanel.setVisible(false);
		ownersPanel.setVisible(false);

	}




	/**
	 * 
	 */
	private void constructActionsPanel() {

		actionsPanel = new FlowPanel();
		actionsPanel.setVisible(false);
		actionsPanel.addStyleName(CSS.COMMON_ACTIONS);

		actionsPanel.add(new HTML("<h3>Actions</h3>"));
		
		actionViewStudy = new Anchor();
		actionViewStudy.addStyleName(CSS.COMMON_ACTION);
		actionViewStudy.setText("view study");
		actionViewStudy.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) { ViewStudyQuestionnaireWidgetDefaultRenderer.this.owner.fireOnUserActionViewStudy(); }
		});
		actionsPanel.add(actionViewStudy);

		actionEditStudyQuestionnaire = new Anchor();
		actionEditStudyQuestionnaire.addStyleName(CSS.COMMON_ACTION);
		actionEditStudyQuestionnaire.setText("edit study questionnaire");
		actionEditStudyQuestionnaire.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) { ViewStudyQuestionnaireWidgetDefaultRenderer.this.owner.fireOnUserActionEditStudyQuestionnaire(); }
		});
		actionsPanel.add(actionEditStudyQuestionnaire);

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
			actionsPanel.setVisible(true);
		}
		else if (after == StudyQuestionnaireWidgetModel.STATUS_ERROR) {
			mainPanel.setVisible(false);
			actionsPanel.setVisible(false);
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
		for (Iterator<String> it = entry.getModules().iterator(); it.hasNext(); ) {
			String ml = ConfigurationBean.getModules().get(it.next());
			modulesContent += ml;
			if (it.hasNext()) {
				modulesContent += ", ";
			}
		}
		
		InlineLabel modulesAnswer = new InlineLabel(modulesContent);
		modulesAnswer.addStyleName(CSS.COMMON_ANSWER);
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
		authorsAnswer.addStyleName(CSS.COMMON_ANSWER);
		ownersListPanel.add(authorsAnswer);
		
	}
	
	
	
	
}