/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.client.gwt.common.client.CSS;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerViewAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModel;
import org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModelListener;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author raok
 *
 */
public class ViewStudyWidgetDefaultRenderer implements StudyModelListener {

	//Expose view elements for testing purposes.
	final Label titleLabel = new InlineLabel();
	final Label summaryLabel = new InlineLabel();
	final Label idLabel = new InlineLabel();
	final Label createdLabel = new InlineLabel();
	final Label updatedLabel = new InlineLabel();
	final FlowPanel loadingPanel = new FlowPanel();
	final FlowPanel mainPanel = new FlowPanel();
	
	
	
	
	final Anchor editThisStudyUI = new Anchor();
	final Anchor viewStudyQuestionnaireUI = new Anchor();
	final Anchor editStudyQuestionnaireUI = new Anchor();
	final FlowPanel modulesListPanel = new FlowPanel();
	final FlowPanel ownersListPanel = new FlowPanel();
	
	final private Panel canvas;
	final private StudyControllerViewAPI controller;
	private Map<String, String> modulesConfig;
	private Log log = LogFactory.getLog(this.getClass());

	
	
	
	/**
	 * Construct a renderer, passing in the panel to use as the renderer's
	 * canvas.
	 * 
	 * @param canvas
	 * @param controller
	 */
	public ViewStudyWidgetDefaultRenderer(Panel canvas, StudyControllerViewAPI controller) {
		this.canvas = canvas;
		this.controller = controller;
		this.modulesConfig = ConfigurationBean.getModules();
		
		initCanvas();
	}

	
	
	
	/**
	 * Construct a renderer, allowing the renderer to create its own canvas.
	 * 
	 * @param controller
	 */
	public ViewStudyWidgetDefaultRenderer(StudyControllerViewAPI controller) {
		this.canvas = new FlowPanel();
		this.controller = controller;
		this.modulesConfig = ConfigurationBean.getModules();
		
		initCanvas();
	}

	
	
	
	private void initCanvas() {
		log.enter("initCanvas");

		this.canvas.add(new HTML("<h2>View Study</h2>"));
		
		log.debug("prepare loading panel");

		this.loadingPanel.add(new Label("loading..."));
		this.loadingPanel.setVisible(false);
		this.canvas.add(this.loadingPanel);
		
		log.debug("prepare study details panel");
		
		FlowPanel studyDetailsPanel = new FlowPanel();
		
		FlowPanel titlePanel = new FlowPanel();
		titlePanel.add(new InlineLabel("Study title: "));
		this.titleLabel.addStyleName(CSS.COMMON_ANSWER);
		titlePanel.add(this.titleLabel);
		titlePanel.addStyleName(CSS.COMMON_QUESTION);
		studyDetailsPanel.add(titlePanel);
		
		FlowPanel summaryPanel = new FlowPanel();
		summaryPanel.add(new InlineLabel("Summary: "));
		this.summaryLabel.addStyleName(CSS.COMMON_ANSWER);
		this.summaryLabel.addStyleName(CSS.VIEWSTUDY_SUMMARY);
		summaryPanel.add(this.summaryLabel);
		summaryPanel.addStyleName(CSS.COMMON_QUESTION);
		studyDetailsPanel.add(summaryPanel);

		FlowPanel modulesPanel = new FlowPanel();
		modulesPanel.add(new InlineLabel("Modules:"));
		this.modulesListPanel.addStyleName(CSS.VIEWSTUDY_MODULES);
		modulesPanel.add(this.modulesListPanel);
		modulesPanel.addStyleName(CSS.COMMON_QUESTION);
		studyDetailsPanel.add(modulesPanel);
		
		FlowPanel ownersPanel = new FlowPanel();
		ownersPanel.add(new InlineLabel("Owners:"));
		this.ownersListPanel.addStyleName(CSS.VIEWSTUDY_OWNERS);
		ownersPanel.add(this.ownersListPanel);
		ownersPanel.addStyleName(CSS.COMMON_QUESTION);
		studyDetailsPanel.add(ownersPanel);

		FlowPanel createdPanel = new FlowPanel();
		createdPanel.add(new InlineLabel("Created: "));
		this.createdLabel.addStyleName(CSS.COMMON_ANSWER);
		createdPanel.add(this.createdLabel);
		createdPanel.addStyleName(CSS.COMMON_QUESTION);
		studyDetailsPanel.add(createdPanel);

		FlowPanel updatedPanel = new FlowPanel();
		updatedPanel.add(new InlineLabel("Updated: "));
		this.updatedLabel.addStyleName(CSS.COMMON_ANSWER);
		updatedPanel.add(this.updatedLabel);
		updatedPanel.addStyleName(CSS.COMMON_QUESTION);
		studyDetailsPanel.add(updatedPanel);
		
		FlowPanel idPanel = new FlowPanel();
		idPanel.add(new InlineLabel("Chassis ID: "));
		this.idLabel.addStyleName(CSS.COMMON_ANSWER);
		this.idLabel.addStyleName(CSS.VIEWSTUDY_ID);
		idPanel.add(this.idLabel);
		idPanel.addStyleName(CSS.COMMON_QUESTION);
		studyDetailsPanel.add(idPanel);

		log.debug("create actions panel");

		FlowPanel actionsPanel = new FlowPanel();
		actionsPanel.add(new HTML("<h3>Actions</h3>"));
		actionsPanel.addStyleName(CSS.COMMON_ACTIONS);

		this.editThisStudyUI.setText("edit study");
		this.editThisStudyUI.addStyleName(CSS.COMMON_ACTION);
		this.editThisStudyUI.addClickHandler(new EditStudyClickHandler());
		actionsPanel.add(this.editThisStudyUI);

		this.viewStudyQuestionnaireUI.setText("view study questionnaire");
		this.viewStudyQuestionnaireUI.addStyleName(CSS.COMMON_ACTION);
		this.viewStudyQuestionnaireUI.addClickHandler(new ViewStudyQuestionnaireClickHandler());
		actionsPanel.add(this.viewStudyQuestionnaireUI);

		this.editStudyQuestionnaireUI.setText("edit study questionnaire");
		this.editStudyQuestionnaireUI.addStyleName(CSS.COMMON_ACTION);
		this.editStudyQuestionnaireUI.addClickHandler(new EditStudyQuestionnaireClickHandler());
		actionsPanel.add(this.editStudyQuestionnaireUI);

		log.debug("prepare main panel");

		this.mainPanel.addStyleName(CSS.COMMON_MAINWITHACTIONS);
		this.mainPanel.add(studyDetailsPanel);
		this.mainPanel.add(actionsPanel);
		this.mainPanel.setVisible(false);
		this.canvas.add(this.mainPanel);
		
		log.leave();
	}

	public void onStatusChanged(Integer before, Integer after) {
		
		if (after == StudyModel.STATUS_LOADING) {

			this.mainPanel.setVisible(false);
			this.loadingPanel.setVisible(true);
		
		} else if (after == StudyModel.STATUS_LOADED) {

			this.loadingPanel.setVisible(false);
			this.mainPanel.setVisible(true);

		} else if (after == StudyModel.STATUS_ERROR) {

			// TODO handle error case (could use extra panel or pass error to parent)

		}

	}

	class EditStudyClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent arg0) {
			controller.fireOnUserActionEditThisStudy();
		}
	}

	class EditStudyQuestionnaireClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent arg0) {
			controller.fireOnUserActionEditStudyQuestionnaire();
		}
	}

	class ViewStudyQuestionnaireClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent arg0) {
			controller.fireOnUserActionViewStudyQuestionnaire();
		}
	}

	public void onStudyEntryChanged(Boolean isValid) {
		// TODO Auto-generated method stub
	}

	public void onSummaryChanged(String before, String after, Boolean isValid) {
		summaryLabel.setText(after);
	}

	public void onTitleChanged(String before, String after, Boolean isValid) {
		titleLabel.setText(after);
	}

	public void onModulesChanged(Set<String> before, Set<String> after, Boolean isValid) {

		modulesListPanel.clear();
		
		String modulesContent = "";
		for (Iterator<String> it = after.iterator(); it.hasNext(); ) {
			String ml = modulesConfig.get(it.next());
			modulesContent += ml;
			if (it.hasNext()) {
				modulesContent += ", ";
			}
		}
		
		InlineLabel answer = new InlineLabel(modulesContent);
		answer.addStyleName(CSS.COMMON_ANSWER);
		modulesListPanel.add(answer);

	}

	public void onAuthorsChanged(Set<AtomAuthor> before, Set<AtomAuthor> after, Boolean isValid) {

		ownersListPanel.clear();
		
		String authorsContent = "";
		for (Iterator<AtomAuthor> it = after.iterator(); it.hasNext(); ) {
			authorsContent += it.next().getEmail();
			if (it.hasNext()) {
				authorsContent += ", ";
			}
		}

		InlineLabel answer = new InlineLabel(authorsContent);
		answer.addStyleName(CSS.COMMON_ANSWER);
		ownersListPanel.add(answer);
		
	}

	/**
	 * @return
	 */
	public Panel getCanvas() {
		return this.canvas;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModelListener#onCreatedChanged(java.lang.String, java.lang.String)
	 */
	public void onCreatedChanged(String before, String created) {
		this.createdLabel.setText(created);
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModelListener#onUpdatedChanged(java.lang.String, java.lang.String)
	 */
	public void onUpdatedChanged(String before, String updated) {
		this.updatedLabel.setText(updated);
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModelListener#onTitleChanged(java.lang.String, java.lang.String)
	 */
	public void onTitleChanged(String before, String id) {
		this.idLabel.setText(id);
	}
	
}
