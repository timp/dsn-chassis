/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import java.util.List;

import org.cggh.chassis.generic.atom.study.client.protocol.StudyQuery;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.client.gwt.common.client.CSS;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewStudiesWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChangeEvent;
import org.cggh.chassis.generic.widget.client.WidgetRenderer;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;

/**
 * @author aliman
 *
 */
public class SubmissionPropertiesWidgetDefaultRenderer extends WidgetRenderer implements SubmissionPropertiesWidgetModelListener {
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private SubmissionPropertiesWidgetModel model;
	private Label titleLabel, summaryLabel, createdLabel, updatedLabel, idLabel;
	private FlowPanel modulesListPanel, ownersListPanel;
	private ViewStudiesWidget studiesLinkedWidget;

	

	
	/**
	 * 
	 */
	public void renderUI() {
		log.enter("renderUI");
		
		this.titleLabel = new InlineLabel();
		this.summaryLabel = new InlineLabel();
		this.createdLabel = new InlineLabel();
		this.updatedLabel = new InlineLabel();
		this.idLabel = new InlineLabel();
		this.modulesListPanel = new FlowPanel();
		this.ownersListPanel = new FlowPanel();
		this.studiesLinkedWidget = new ViewStudiesWidget(new ViewStudiesWidgetCustomRenderer());

		FlowPanel titlePanel = RenderUtils.renderTitlePropertyPanel(this.titleLabel, CSS.VIEWSUBMISSION_TITLE);
		FlowPanel summaryPanel = RenderUtils.renderSummaryPropertyPanel(this.summaryLabel, CSS.VIEWSUBMISSION_SUMMARY);
		FlowPanel modulesPanel = RenderUtils.renderModulesPropertyPanel(this.modulesListPanel, CSS.VIEWSUBMISSION_MODULES);
		FlowPanel ownersPanel = RenderUtils.renderOwnersPropertyPanel(this.ownersListPanel, CSS.VIEWSUBMISSION_OWNERS);
		FlowPanel studiesPanel = RenderUtils.renderPropertyPanel("Studies", this.studiesLinkedWidget, null); // TODO i18n
		FlowPanel createdPanel = RenderUtils.renderCreatedPropertyPanel(this.createdLabel, null);
		FlowPanel updatedPanel = RenderUtils.renderUpdatedPropertyPanel(this.updatedLabel, null);
		FlowPanel idPanel = RenderUtils.renderIdPropertyPanel(this.idLabel, CSS.VIEWSUBMISSION_ID);

		this.canvas.clear();
		this.canvas.add(titlePanel);
		this.canvas.add(summaryPanel);
		this.canvas.add(modulesPanel);
		this.canvas.add(ownersPanel);
		this.canvas.add(studiesPanel);
		this.canvas.add(createdPanel);
		this.canvas.add(updatedPanel);
		this.canvas.add(idPanel);

		log.leave();
	}




	/**
	 * @param model
	 */
	public void bindUI(SubmissionPropertiesWidgetModel model) {
		log.enter("bindUI");
		
		// unbind to clear anything
		this.unbindUI();
		
		// listen to model property change events to update UI
		this.model = model;
		this.model.addListener(this);
		
		// listen to UI events from user interactions to update model
		// not needed for this widget
		
		log.leave();	
	}
	
	
	
	
	/**
	 * 
	 */
	public void syncUI() {
		log.enter("syncUI");

		if (this.model.getSubmissionEntry() != null) {
			this.syncTitle();
			this.syncSummary();
			this.syncCreated();
			this.syncUpdated();
			this.syncId();
			this.syncOwners();
			this.syncModules();
			this.syncStudies();
		}
		
		log.leave();
		
	}

	
	
	
	private void syncTitle() {
		titleLabel.setText(this.model.getSubmissionEntry().getTitle());
	}

	
	
	


	private void syncSummary() {
		summaryLabel.setText(this.model.getSubmissionEntry().getSummary());
	}

	
	
	
	
	
	private void syncStudies() {
		// TODO should this logic go elsewhere? or OK here?
		StudyQuery query = new StudyQuery();
		query.setAuthorEmail(ChassisUser.getCurrentUserEmail());
		query.setSubmissionUrl(this.model.getSubmissionEntry().getEditLink().getHref());
		studiesLinkedWidget.loadStudies(query);
	}


	
	
	private void syncModules() {

		List<String> moduleIds = this.model.getSubmissionEntry().getModules();
		Label answer = RenderUtils.renderModulesAsLabel(moduleIds, ConfigurationBean.getModules(), true);
		answer.addStyleName(CSS.COMMON_ANSWER);

		modulesListPanel.clear();
		modulesListPanel.add(answer);

	}


	

	
	
	
	private void syncOwners() {

		List<AtomAuthor> owners = this.model.getSubmissionEntry().getAuthors();
		Label answer = RenderUtils.renderAtomAuthorsAsLabel(owners, true);
		answer.addStyleName(CSS.COMMON_ANSWER);

		ownersListPanel.clear();
		ownersListPanel.add(answer);

	}


	
	private void syncCreated() {
		this.createdLabel.setText(this.model.getSubmissionEntry().getPublished());
	}

	
	
	
	private void syncUpdated() {
		this.updatedLabel.setText(this.model.getSubmissionEntry().getUpdated());
	}

	
	
	
	private void syncId() {
		this.idLabel.setText(this.model.getSubmissionEntry().getId());
	}

	



	/**
	 * 
	 */
	public void unbindUI() {
		log.enter("detachUI");
		
		// detach from model
		if (this.model != null) this.model.removeListener(this);
		
		// detach from UI
		// not needed for this widget
		
		log.leave();
		
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.view.client.SubmissionPropertiesWidgetModelListener#onSubmissionEntryChanged(org.cggh.chassis.generic.widget.client.ChangeEvent)
	 */
	public void onSubmissionEntryChanged(ChangeEvent<SubmissionEntry> e) {
		log.enter("onSubmissionEntryChanged");
		
		this.syncUI();
		
		log.leave();
		
	}




}
