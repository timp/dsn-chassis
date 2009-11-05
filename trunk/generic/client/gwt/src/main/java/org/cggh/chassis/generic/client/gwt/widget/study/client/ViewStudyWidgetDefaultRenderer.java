/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.Map;
import java.util.Set;


import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
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
	
	
	
	
	Anchor editThisStudyUI = new Anchor();
	Anchor viewStudyQuestionnaireUI = new Anchor();
	Anchor editStudyQuestionnaireUI = new Anchor();
	FlowPanel modulesListPanel = new FlowPanel();
	FlowPanel ownersListPanel = new FlowPanel();
	
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
		
		FlowPanel studyDetailsPanel = this.renderStudyDetailsPanel();
		FlowPanel actionsPanel = this.renderActionsPanel();
		
		log.debug("prepare main panel");

		this.mainPanel.addStyleName(CommonStyles.COMMON_MAINWITHACTIONS);
		this.mainPanel.add(studyDetailsPanel);
		this.mainPanel.add(actionsPanel);
		this.mainPanel.setVisible(false);
		this.canvas.add(this.mainPanel);
		
		log.leave();
	}

	
	
	
	private FlowPanel renderStudyDetailsPanel() {
		log.enter("renderStudyDetailsPanel");

		log.debug("prepare study details panel");
		
		FlowPanel titlePanel = RenderUtils.renderTitlePropertyPanel(this.titleLabel, CommonStyles.VIEWSTUDY_TITLE);
		FlowPanel summaryPanel = RenderUtils.renderSummaryPropertyPanel(this.summaryLabel, CommonStyles.VIEWSTUDY_SUMMARY);
		FlowPanel modulesPanel = RenderUtils.renderModulesPropertyPanel(this.modulesListPanel, CommonStyles.VIEWSTUDY_MODULES);
		FlowPanel ownersPanel = RenderUtils.renderOwnersPropertyPanel(this.ownersListPanel, CommonStyles.VIEWSTUDY_OWNERS);
		FlowPanel createdPanel = RenderUtils.renderCreatedPropertyPanel(this.createdLabel, null);
		FlowPanel updatedPanel = RenderUtils.renderUpdatedPropertyPanel(this.updatedLabel, null);
		FlowPanel idPanel = RenderUtils.renderIdPropertyPanel(this.idLabel, CommonStyles.VIEWSTUDY_ID);

		FlowPanel studyDetailsPanel = new FlowPanel();
		studyDetailsPanel.add(titlePanel);
		studyDetailsPanel.add(summaryPanel);
		studyDetailsPanel.add(modulesPanel);
		studyDetailsPanel.add(ownersPanel);
		studyDetailsPanel.add(createdPanel);
		studyDetailsPanel.add(updatedPanel);
		studyDetailsPanel.add(idPanel);

		log.leave();
		return studyDetailsPanel;
	}
	
	
	
	private FlowPanel renderActionsPanel() {
		log.enter("renderActionsPanel");

		this.editThisStudyUI = RenderUtils.renderActionAsAnchor("edit study", new EditStudyClickHandler());
		this.editStudyQuestionnaireUI = RenderUtils.renderActionAsAnchor("edit study questionnaire", new EditStudyQuestionnaireClickHandler());
		this.viewStudyQuestionnaireUI = RenderUtils.renderActionAsAnchor("view study questionnaire", new ViewStudyQuestionnaireClickHandler());
		
		Widget[] actions = {
			this.editThisStudyUI,
			this.editStudyQuestionnaireUI,
			this.viewStudyQuestionnaireUI
		};
		
		FlowPanel actionsPanel = RenderUtils.renderActionsPanel(actions);
		
		log.leave();
		return actionsPanel;
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

		Label answer = RenderUtils.renderModulesAsLabel(after, modulesConfig, true);
		answer.addStyleName(CommonStyles.COMMON_ANSWER);

		modulesListPanel.clear();
		modulesListPanel.add(answer);

	}

	
	
	
	public void onAuthorsChanged(Set<AtomAuthor> before, Set<AtomAuthor> authors, Boolean isValid) {
		
		Label answer = RenderUtils.renderRewriteAtomAuthorsAsLabel(authors, true);
		answer.addStyleName(CommonStyles.COMMON_ANSWER);

		ownersListPanel.clear();
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
