/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.client.gwt.common.client.CSS;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModel;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewStudiesWidget;
import org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerViewAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.model.client.SubmissionModel;
import org.cggh.chassis.generic.client.gwt.widget.submission.model.client.SubmissionModelListener;
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
public class ViewSubmissionWidgetDefaultRenderer implements
		SubmissionModelListener {

	private Log log = LogFactory.getLog(this.getClass());

	// Expose view elements for testing purposes.
	final Label titleLabel = new InlineLabel();
	final Label summaryLabel = new InlineLabel();
	final Label createdLabel = new InlineLabel();
	final Label updatedLabel = new InlineLabel();
	final FlowPanel loadingPanel = new FlowPanel();
	final FlowPanel mainPanel = new FlowPanel();

	Anchor editThisSubmissionUI = new Anchor();

	final FlowPanel modulesListPanel = new FlowPanel();
	final FlowPanel ownersListPanel = new FlowPanel();
	final FlowPanel studyLinksListPanel = new FlowPanel();

	final private Panel canvas;
	final private SubmissionControllerViewAPI controller;
	private Map<String, String> modulesConfig;

	// Linked Studies
	private ViewStudiesWidget studiesLinkedWidget;

	/**
	 * Construct a renderer, passing in the panel to use as the renderer's
	 * canvas.
	 * 
	 * @param canvas
	 * @param controller
	 */
	public ViewSubmissionWidgetDefaultRenderer(Panel canvas,
			SubmissionControllerViewAPI controller) {
		this.canvas = canvas;
		this.controller = controller;

		// get modules from config
		this.modulesConfig = ConfigurationBean.getModules();

		// Create ViewStudies widget to view linked studies
		studiesLinkedWidget = new ViewStudiesWidget("");

		initCanvas();
	}

	/**
	 * Construct a renderer, allowing the renderer to create its own canvas.
	 * 
	 * @param controller
	 */
	public ViewSubmissionWidgetDefaultRenderer(
			SubmissionControllerViewAPI controller) {
		this.canvas = new FlowPanel();
		this.controller = controller;

		// get modules from config
		this.modulesConfig = ConfigurationBean.getModules();

		// Create ViewStudies widget to view linked studies
		studiesLinkedWidget = new ViewStudiesWidget("");

		initCanvas();
	}

	private void initCanvas() {
		log.enter("initCanvas");

		this.canvas.add(new HTML("<h2>View Data Submission</h2>"));

		log.debug("prepare loading panel");

		this.loadingPanel.add(new Label("loading..."));
		this.loadingPanel.setVisible(false);
		this.canvas.add(this.loadingPanel);

		log.debug("prepare submission details panel");

		Panel submissionDetailsPanel = new FlowPanel();

		log.debug("title panel");

		Panel titlePanel = new FlowPanel();
		titlePanel.add(new InlineLabel("Submission title: "));
		this.titleLabel.addStyleName(CSS.COMMON_ANSWER);
		titlePanel.add(titleLabel);
		titlePanel.addStyleName(CSS.COMMON_QUESTION);
		submissionDetailsPanel.add(titlePanel);

		log.debug("summary panel");

		Panel summaryPanel = new FlowPanel();
		summaryPanel.add(new InlineLabel("Summary: "));
		this.summaryLabel.addStyleName(CSS.COMMON_ANSWER);
		this.summaryLabel.addStyleName(CSS.VIEWSUBMISSION_SUMMARY);
		summaryPanel.add(this.summaryLabel);
		summaryPanel.addStyleName(CSS.COMMON_QUESTION);
		submissionDetailsPanel.add(summaryPanel);

		log.debug("modules panel");

		FlowPanel modulesPanel = new FlowPanel();
		modulesPanel.add(new InlineLabel("Modules:"));
		this.modulesListPanel.addStyleName(CSS.VIEWSTUDY_MODULES);
		modulesPanel.add(this.modulesListPanel);
		modulesPanel.addStyleName(CSS.COMMON_QUESTION);
		submissionDetailsPanel.add(modulesPanel);

		log.debug("owners panel");

		FlowPanel ownersPanel = new FlowPanel();
		ownersPanel.add(new InlineLabel("Owners:"));
		this.ownersListPanel.addStyleName(CSS.VIEWSTUDY_OWNERS);
		ownersPanel.add(this.ownersListPanel);
		ownersPanel.addStyleName(CSS.COMMON_QUESTION);
		submissionDetailsPanel.add(ownersPanel);

		log.debug("created panel");

		FlowPanel createdPanel = new FlowPanel();
		createdPanel.add(new InlineLabel("Created: "));
		this.createdLabel.addStyleName(CSS.COMMON_ANSWER);
		createdPanel.add(this.createdLabel);
		createdPanel.addStyleName(CSS.COMMON_QUESTION);
		submissionDetailsPanel.add(createdPanel);

		log.debug("updated panel");

		FlowPanel updatedPanel = new FlowPanel();
		updatedPanel.add(new InlineLabel("Updated: "));
		this.updatedLabel.addStyleName(CSS.COMMON_ANSWER);
		updatedPanel.add(this.updatedLabel);
		updatedPanel.addStyleName(CSS.COMMON_QUESTION);
		submissionDetailsPanel.add(updatedPanel);

		// TODO studies panel

		// submissionDetailsPanel.add(new Label("Studies to submit to:"));
		// submissionDetailsPanel.add(studiesLinkedWidget);

		mainPanel.add(submissionDetailsPanel);

		log.debug("create actions panel");

		FlowPanel actionsPanel = new FlowPanel();
		actionsPanel.add(new HTML("<h3>Actions</h3>"));
		actionsPanel.addStyleName(CSS.COMMON_ACTIONS);

		this.editThisSubmissionUI.setText("edit submission");
		this.editThisSubmissionUI.addStyleName(CSS.COMMON_ACTION);
		this.editThisSubmissionUI
				.addClickHandler(new EditSubmissionClickHandler());
		actionsPanel.add(this.editThisSubmissionUI);

		log.debug("prepare main panel");

		this.mainPanel.addStyleName(CSS.COMMON_MAINWITHACTIONS);
		this.mainPanel.add(submissionDetailsPanel);
		this.mainPanel.add(actionsPanel);
		this.mainPanel.setVisible(false);
		this.canvas.add(this.mainPanel);

		log.leave();
	}

	class EditSubmissionClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			controller.onUserActionEditThisSubmission();
		}
	}

	public void onModulesChanged(Set<String> before, Set<String> after,
			Boolean isValid) {

		// modulesListPanel.clear();
		//		
		// for (String module : after) {
		// modulesListPanel.add(new Label(modulesConfig.get(module)));
		// }

		modulesListPanel.clear();

		String modulesContent = "";
		for (Iterator<String> it = after.iterator(); it.hasNext();) {
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

	public void onStatusChanged(Integer before, Integer after) {
		//
		// if (after == SubmissionModel.STATUS_LOADING) {
		// canvas.clear();
		// canvas.add(loadingPanel);
		// } else if (after == SubmissionModel.STATUS_LOADED) {
		// canvas.clear();
		// canvas.add(mainPanel);
		// } else if (after == SubmissionModel.STATUS_ERROR) {
		// // TODO handle error case (could use extra panel or pass error to
		// parent)
		// }
		//			

		if (after == SubmissionModel.STATUS_LOADING) {

			this.mainPanel.setVisible(false);
			this.loadingPanel.setVisible(true);

		} else if (after == SubmissionModel.STATUS_LOADED) {

			this.loadingPanel.setVisible(false);
			this.mainPanel.setVisible(true);

		} else if (after == SubmissionModel.STATUS_ERROR) {

			// TODO handle error case (could use extra panel or pass error to
			// parent)

		}

	}

	public void onStudyLinksChanged(Set<String> before, Set<String> after,
			Boolean isValid) {

		studiesLinkedWidget.loadStudies(after);

	}

	public void onSubmissionEntryChanged(Boolean isValid) {
		// TODO Auto-generated method stub

	}

	public void onSummaryChanged(String before, String after, Boolean isValid) {
		summaryLabel.setText(after);

	}

	public void onTitleChanged(String before, String after, Boolean isValid) {
		titleLabel.setText(after);

	}

	public void onAuthorsChanged(Set<AtomAuthor> before, Set<AtomAuthor> after,
			Boolean isValid) {

		// ownersListPanel.clear();
		//		
		// for (AtomAuthor atomAuthor : after) {
		// ownersListPanel.add(new Label(atomAuthor.getEmail()));
		// }

		ownersListPanel.clear();

		String authorsContent = "";
		for (Iterator<AtomAuthor> it = after.iterator(); it.hasNext();) {
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

	public void onCreatedChanged(String before, String created) {
		this.createdLabel.setText(created);
	}

	public void onUpdatedChanged(String before, String updated) {
		this.updatedLabel.setText(updated);
	}

}
