/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import java.util.List;

import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atomext.client.study.StudyQuery;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.client.gwt.widget.study.client.ViewStudiesWidget;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.SubmissionPropertiesWidgetModel.SubmissionEntryChangeEvent;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.SubmissionPropertiesWidgetModel.SubmissionEntryChangeHandler;
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
public class SubmissionPropertiesWidgetRenderer 
			extends ChassisWidgetRenderer<SubmissionPropertiesWidgetModel> {
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
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

		FlowPanel titlePanel = RenderUtils.renderTitlePropertyPanel(this.titleLabel, CommonStyles.VIEWSUBMISSION_TITLE);
		FlowPanel summaryPanel = RenderUtils.renderSummaryPropertyPanel(this.summaryLabel, CommonStyles.VIEWSUBMISSION_SUMMARY);
		FlowPanel modulesPanel = RenderUtils.renderModulesPropertyPanel(this.modulesListPanel, CommonStyles.VIEWSUBMISSION_MODULES);
		FlowPanel ownersPanel = RenderUtils.renderOwnersPropertyPanel(this.ownersListPanel, CommonStyles.VIEWSUBMISSION_OWNERS);
		FlowPanel studiesPanel = RenderUtils.renderPropertyPanel("Studies", this.studiesLinkedWidget, null); // TODO i18n
		FlowPanel createdPanel = RenderUtils.renderCreatedPropertyPanel(this.createdLabel, null);
		FlowPanel updatedPanel = RenderUtils.renderUpdatedPropertyPanel(this.updatedLabel, null);
		FlowPanel idPanel = RenderUtils.renderIdPropertyPanel(this.idLabel, CommonStyles.VIEWSUBMISSION_ID);

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
	 * 
	 */
	@Override
	protected void registerHandlersForModelChanges() {
		log.enter("registerHandlersForModelChanges");
		
		// register handler for model property change events
		HandlerRegistration a = this.model.addSubmissionEntryChangeHandler(new SubmissionEntryChangeHandler() {
			
			public void onSubmissionEntryChanged(SubmissionEntryChangeEvent e) {
				log.enter("onSubmissionEntryChanged");
				
				updateSubmissionProperties(e.getAfter());
				
				log.leave();
				
			}
		});
		
		// store registrations so can remove later if necessary
		this.modelChangeHandlerRegistrations.add(a);
		
		log.leave();
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#registerHandlersForChildWidgetEvents()
	 */
	@Override
	protected void registerHandlersForChildWidgetEvents() {
		// no events of interest
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
			this.updateModulesLabel(entry.getSubmission().getModules());
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
		answer.addStyleName(CommonStyles.COMMON_ANSWER);

		modulesListPanel.clear();
		modulesListPanel.add(answer);

	}


	
	
	private void updateOwnersLabel(List<AtomAuthor> owners) {

		Label answer = RenderUtils.renderRewriteAtomAuthorsAsLabel(owners, true);
		answer.addStyleName(CommonStyles.COMMON_ANSWER);

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

	





}
