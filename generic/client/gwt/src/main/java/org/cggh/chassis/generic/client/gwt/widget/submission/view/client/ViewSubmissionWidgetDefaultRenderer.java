/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cggh.chassis.generic.atom.study.client.protocol.StudyQuery;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink;
import org.cggh.chassis.generic.client.gwt.common.client.CSS;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewStudiesWidget;
import org.cggh.chassis.generic.client.gwt.widget.submission.viewdatafiles.client.ViewSubmissionDataFilesWidget;
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
public class ViewSubmissionWidgetDefaultRenderer implements ViewSubmissionWidgetModel.Listener {
	private Log log = LogFactory.getLog(this.getClass());	
	
	
	// Expose view elements for testing purposes.
	final Label titleLabel = new InlineLabel();
	final Label summaryLabel = new InlineLabel();
	final Label createdLabel = new InlineLabel();
	final Label updatedLabel = new InlineLabel();
	final Label idLabel = new InlineLabel();

	final FlowPanel loadingPanel = new FlowPanel();
	final FlowPanel mainPanel = new FlowPanel();

	Anchor editThisSubmissionUI = new Anchor();
	Anchor uploadDataFileUI = new Anchor();

	final FlowPanel modulesListPanel = new FlowPanel();
	final FlowPanel ownersListPanel = new FlowPanel();
	final FlowPanel studyLinksListPanel = new FlowPanel();

	final private Panel canvas;
	private Map<String, String> modulesConfig;

	// Linked Studies
	private ViewStudiesWidget studiesLinkedWidget;
	private ViewSubmissionDataFilesWidget submissionDataFilesWidget;




	final private ViewSubmissionWidgetController controller;





	
	
	
	
	/**
	 * Construct a renderer, allowing the renderer to create its own canvas.
	 * 
	 * @param controller
	 */
	public ViewSubmissionWidgetDefaultRenderer(ViewSubmissionWidgetController controller) {

		this.canvas = new FlowPanel();
		this.controller = controller;

		// get modules from config
		this.modulesConfig = ConfigurationBean.getModules();

		// Create ViewStudies widget to view linked studies
		studiesLinkedWidget = new ViewStudiesWidget(new ViewStudiesWidgetCustomRenderer());
		
		//Create dataFiles linked widget to view linked data files
		submissionDataFilesWidget = new ViewSubmissionDataFilesWidget();

		initCanvas();
	}

	
	
	
	
	private void initCanvas() {
		log.enter("initCanvas");

		this.canvas.addStyleName(CSS.VIEWSUBMISSION_BASE);
		
		this.canvas.add(new HTML("<h2>View Data Submission</h2>"));

		log.debug("prepare loading panel");
		
		this.loadingPanel.add(new Label("loading..."));
		this.loadingPanel.setVisible(false);
		this.canvas.add(this.loadingPanel);

		Panel submissionDetailsPanel = this.renderSubmissionDetailsPanel();
		FlowPanel actionsPanel = this.renderActionsPanel();

		log.debug("prepare main panel");

		this.mainPanel.addStyleName(CSS.COMMON_MAINWITHACTIONS);
		this.mainPanel.add(submissionDetailsPanel);
		this.mainPanel.add(actionsPanel);
		this.mainPanel.setVisible(false);
		this.canvas.add(this.mainPanel);

		log.leave();
	}
	
	
	

	
	
	
	



	/**
	 * @return
	 */
	private Panel renderSubmissionDetailsPanel() {
		log.enter("renderSubmissionDetailsPanel");
		
		FlowPanel titlePanel = RenderUtils.renderTitlePropertyPanel(this.titleLabel, CSS.VIEWSUBMISSION_TITLE);
		FlowPanel summaryPanel = RenderUtils.renderSummaryPropertyPanel(this.summaryLabel, CSS.VIEWSUBMISSION_SUMMARY);
		FlowPanel modulesPanel = RenderUtils.renderModulesPropertyPanel(this.modulesListPanel, CSS.VIEWSUBMISSION_MODULES);
		FlowPanel ownersPanel = RenderUtils.renderOwnersPropertyPanel(this.ownersListPanel, CSS.VIEWSUBMISSION_OWNERS);
		FlowPanel studiesPanel = RenderUtils.renderPropertyPanel("Studies", this.studiesLinkedWidget, null); // TODO i18n
		FlowPanel createdPanel = RenderUtils.renderCreatedPropertyPanel(this.createdLabel, null);
		FlowPanel updatedPanel = RenderUtils.renderUpdatedPropertyPanel(this.updatedLabel, null);
		FlowPanel idPanel = RenderUtils.renderIdPropertyPanel(this.idLabel, CSS.VIEWSUBMISSION_ID);
		
		log.debug("render data files panel");

		FlowPanel dataFilesPanel = new FlowPanel();
		dataFilesPanel.add(new HTML("<h3>Data Files</h3>")); // TODO i18n
		dataFilesPanel.add(this.submissionDataFilesWidget);

		FlowPanel submissionDetailsPanel = new FlowPanel();
		
		submissionDetailsPanel.add(titlePanel);
		submissionDetailsPanel.add(summaryPanel);
		submissionDetailsPanel.add(modulesPanel);
		submissionDetailsPanel.add(ownersPanel);
		submissionDetailsPanel.add(studiesPanel);
		submissionDetailsPanel.add(createdPanel);
		submissionDetailsPanel.add(updatedPanel);
		submissionDetailsPanel.add(idPanel);
		submissionDetailsPanel.add(dataFilesPanel);		

		log.leave();
		return submissionDetailsPanel;
	}





	/**
	 * @return
	 */
	private FlowPanel renderActionsPanel() {
		log.enter("renderActionsPanel");
		
		this.editThisSubmissionUI = RenderUtils.renderActionAsAnchor("edit submission", new EditSubmissionClickHandler());
		this.uploadDataFileUI = RenderUtils.renderActionAsAnchor("upload data file", new UploadDataFileClickHandler());

		Widget[] actions = {
			this.editThisSubmissionUI,
			this.uploadDataFileUI
		};
		
		FlowPanel actionsPanel = RenderUtils.renderActionsPanel(actions);
		
		log.leave();
		return actionsPanel;
	}





	class EditSubmissionClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			controller.onUserActionEditThisSubmission();
		}
	}

	class UploadDataFileClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent arg0) {
			controller.onUserActionUploadDataFile();
		}
	}

	
	public void renderModules(List<String> moduleIds) {

		Label answer = RenderUtils.renderModulesAsLabel(moduleIds, modulesConfig, true);
		answer.addStyleName(CSS.COMMON_ANSWER);

		modulesListPanel.clear();
		modulesListPanel.add(answer);

//		modulesListPanel.clear();
//
//		String modulesContent = "";
//		for (Iterator<String> it = modules.iterator(); it.hasNext();) {
//			String ml = modulesConfig.get(it.next());
//			modulesContent += ml;
//			if (it.hasNext()) {
//				modulesContent += ", ";
//			}
//		}
//
//		InlineLabel answer = new InlineLabel(modulesContent);
//		answer.addStyleName(CSS.COMMON_ANSWER);
//		modulesListPanel.add(answer);

	}


	
	
	
	
	public void renderStudiesForSubmission(SubmissionEntry entry) {
		StudyQuery query = new StudyQuery();
		query.setAuthorEmail(ChassisUser.getCurrentUserEmail());
		query.setSubmissionUrl(entry.getEditLink().getHref());
		studiesLinkedWidget.loadStudies(query);
		submissionDataFilesWidget.loadDataFilesBySubmissionLink(entry.getEditLink().getHref());
	}

	
	
	
	
	public void renderSummary(String summary) {
		summaryLabel.setText(summary);

	}

	
	
	
	public void renderTitle(String title) {
		titleLabel.setText(title);

	}

	
	
	
	public void renderOwners(List<AtomAuthor> owners) {

		Label answer = RenderUtils.renderAtomAuthorsAsLabel(owners, true);
		answer.addStyleName(CSS.COMMON_ANSWER);

		ownersListPanel.clear();
		ownersListPanel.add(answer);

//		ownersListPanel.clear();
//
//		String authorsContent = "";
//		for (Iterator<AtomAuthor> it = owners.iterator(); it.hasNext();) {
//			authorsContent += it.next().getEmail();
//			if (it.hasNext()) {
//				authorsContent += ", ";
//			}
//		}
//
//		InlineLabel answer = new InlineLabel(authorsContent);
//		answer.addStyleName(CSS.COMMON_ANSWER);
//		ownersListPanel.add(answer);

	}

	
	
	
	/**
	 * @return
	 */
	public Panel getCanvas() {
		return this.canvas;
	}

	
	
	
	public void renderCreated(String created) {
		this.createdLabel.setText(created);
	}

	
	
	
	public void renderUpdated(String updated) {
		this.updatedLabel.setText(updated);
	}

	
	
	
	public void renderId(String id) {
		this.idLabel.setText(id);
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.view.client.ViewSubmissionWidgetModel.Listener#onEntryChanged(org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry, org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry)
	 */
	public void onEntryChanged(SubmissionEntry before, SubmissionEntry entry) {
		log.enter("onEntryChanged");
		
		this.renderTitle(entry.getTitle());
		this.renderSummary(entry.getSummary());
		this.renderCreated(entry.getPublished());
		this.renderUpdated(entry.getUpdated());
		this.renderId(entry.getId());
		this.renderOwners(entry.getAuthors());
		this.renderModules(entry.getModules());
		this.renderStudiesForSubmission(entry);
		
		log.leave();
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.common.client.mvc.ModelBase.Listener#onStatusChanged(int, int)
	 */
	public void onStatusChanged(int before, int after) {

		if (after == ViewSubmissionWidgetModel.STATUS_RETRIEVE_PENDING) {

			this.mainPanel.setVisible(false);
			this.loadingPanel.setVisible(true);

		} else if (after == ViewSubmissionWidgetModel.STATUS_READY) {

			this.loadingPanel.setVisible(false);
			this.mainPanel.setVisible(true);

		} else if (after == ViewSubmissionWidgetModel.STATUS_RETRIEVE_ERROR) {

			// TODO handle error case (could use extra panel or pass error to
			// parent)

		}
		
	}


}
