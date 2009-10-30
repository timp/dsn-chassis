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
import org.cggh.chassis.generic.client.gwt.widget.submission.view.client.SubmissionPropertiesWidgetModel.SubmissionEntryChangeEvent;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;

/**
 * @author aliman
 *
 */
public class SubmissionPropertiesWidgetDefaultRenderer 
			extends ChassisWidgetRenderer 
			implements SubmissionPropertiesWidgetModel.ChangeHandler {
	
	
	
	
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
		
		// un-bind to clear anything
		this.unbindUI();
		
		// keep reference to model
		this.model = model;
		
		// register handlers for model changes
		this.registerHandlersForModelChanges();

		// register handlers for child widget events
		// not needed for this widget
		
		log.leave();	
	}
	
	
	
	
	/**
	 * 
	 */
	private void registerHandlersForModelChanges() {
		log.enter("registerHandlersForModelChanges");
		
		// register this as handler for model property change events
		HandlerRegistration a = this.model.addSubmissionEntryChangeHandler(this);
		
		// store registrations so can remove later if necessary
		this.modelChangeHandlerRegistrations.add(a);
		
		log.leave();
	}




	/**
	 * Completely synchronise the UI with the current state of the model.
	 */
	public void syncUI() {
		log.enter("syncUI");
		
		if (this.model != null) {

			log.debug("sync submission entry");
			SubmissionEntry entry = this.model.getSubmissionEntry();
			this.updateSubmissionProperties(entry);

		}
		else {

			// TODO could this method legitimately be called when model is null,
			// or should we throw an error here if model is null?
			log.warn("model is null, not updating anything");

		}
		
		log.leave();
		
	}

	
	
	
	/**
	 * @param entry
	 */
	private void updateSubmissionProperties(SubmissionEntry entry) {
		log.enter("updateSubmissionProperties");
		
		if (entry != null) {
			
			this.updateTitleLabel(entry.getTitle());
			this.updateSummaryLabel(entry.getSummary());
			this.updateDateCreatedLabel(entry.getPublished());
			this.updateDateUpdatedLabel(entry.getUpdated());
			this.updateIdLabel(entry.getId());
			this.updateOwnersLabel(entry.getAuthors());
			this.updateModulesLabel(entry.getModules());
			this.updateStudiesWidget(entry);

		}
		else {

			this.updateTitleLabel(null);
			this.updateSummaryLabel(null);
			this.updateDateCreatedLabel(null);
			this.updateDateUpdatedLabel(null);
			this.updateIdLabel(null);
			this.updateOwnersLabel(null);
			this.updateModulesLabel(null);
			this.updateStudiesWidget(null);

		}
		
		log.leave();
	}




	private void updateTitleLabel(String title) {
		if (title == null) title = "";
		titleLabel.setText(title);
	}

	
	
	


	private void updateSummaryLabel(String summary) {
		if (summary == null) summary = "";
		summaryLabel.setText(summary);
	}

	
	
	
	
	
	private void updateStudiesWidget(SubmissionEntry submissionEntry) {
		if (submissionEntry != null) {
			// TODO should this logic go elsewhere? or OK here?
			StudyQuery query = new StudyQuery();
			query.setAuthorEmail(ChassisUser.getCurrentUserEmail());
			query.setSubmissionUrl(submissionEntry.getEditLink().getHref());
			studiesLinkedWidget.loadStudies(query);
		}
		else {
			// TODO clear studiesLinkedWidget somehow?
		}
	}


	
	
	private void updateModulesLabel(List<String> moduleIds) {

		Label answer = RenderUtils.renderModulesAsLabel(moduleIds, ConfigurationBean.getModules(), true);
		answer.addStyleName(CSS.COMMON_ANSWER);

		modulesListPanel.clear();
		modulesListPanel.add(answer);

	}


	
	
	private void updateOwnersLabel(List<AtomAuthor> owners) {

		Label answer = RenderUtils.renderAtomAuthorsAsLabel(owners, true);
		answer.addStyleName(CSS.COMMON_ANSWER);

		ownersListPanel.clear();
		ownersListPanel.add(answer);

	}


	
	private void updateDateCreatedLabel(String created) {
		if (created == null) created = "";
		this.createdLabel.setText(created);
	}

	
	
	
	private void updateDateUpdatedLabel(String updated) {
		if (updated == null) updated = "";
		this.updatedLabel.setText(updated);
	}

	
	
	
	private void updateIdLabel(String id) {
		if (id == null) id = "";
		this.idLabel.setText(id);
	}

	



	/**
	 * 
	 */
	public void unbindUI() {
		log.enter("unbindUI");
		
		// detach from model
		this.clearModelChangeHandlers();
		
		// detach from child widgets
		// not needed for this widget
		
		log.leave();
		
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.view.client.SubmissionPropertiesWidgetModel.ChangeHandler#onSubmissionEntryChanged(org.cggh.chassis.generic.client.gwt.widget.submission.view.client.SubmissionPropertiesWidgetModel.SubmissionEntryChangeEvent)
	 */
	public void onSubmissionEntryChanged(SubmissionEntryChangeEvent e) {
		log.enter("onSubmissionEntryChanged");
		
		this.updateSubmissionProperties(e.getAfter());
		
		log.leave();
		
	}




}
