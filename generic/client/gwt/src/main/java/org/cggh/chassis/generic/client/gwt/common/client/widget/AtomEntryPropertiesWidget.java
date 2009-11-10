/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.common.client.widget;

import java.util.List;

import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atom.client.AtomEntry;
import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidget;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;

/**
 * @author aliman
 *
 */
public class AtomEntryPropertiesWidget 
	<E extends AtomEntry>
	extends ChassisWidget {
	
	
	
	
	// utility fields
	private Log log;
	
	
	

	// state fields
	protected E entry;
	
	
	
	
	// UI fields
	protected Label titleLabel, summaryLabel, createdLabel, updatedLabel, idLabel;
	protected FlowPanel ownersListPanel;

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");
		
		// initialise state
		this.entry = null;
		
		log.leave();
	}


	
	
	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(AtomEntryPropertiesWidget.class);
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");
		
		this.titleLabel = new InlineLabel();
		this.summaryLabel = new InlineLabel();
		this.createdLabel = new InlineLabel();
		this.updatedLabel = new InlineLabel();
		this.idLabel = new InlineLabel();
		this.ownersListPanel = new FlowPanel();

		// TODO fix styles
		FlowPanel titlePanel = RenderUtils.renderTitlePropertyPanel(this.titleLabel, CommonStyles.VIEWSUBMISSION_TITLE);
		FlowPanel summaryPanel = RenderUtils.renderSummaryPropertyPanel(this.summaryLabel, CommonStyles.VIEWSUBMISSION_SUMMARY);
		FlowPanel ownersPanel = RenderUtils.renderOwnersPropertyPanel(this.ownersListPanel, CommonStyles.VIEWSUBMISSION_OWNERS);
		FlowPanel createdPanel = RenderUtils.renderCreatedPropertyPanel(this.createdLabel, null);
		FlowPanel updatedPanel = RenderUtils.renderUpdatedPropertyPanel(this.updatedLabel, null);
		FlowPanel idPanel = RenderUtils.renderIdPropertyPanel(this.idLabel, CommonStyles.VIEWSUBMISSION_ID);

		this.clear();
		this.add(titlePanel);
		this.add(summaryPanel);
		this.add(ownersPanel);
		this.add(createdPanel);
		this.add(updatedPanel);
		this.add(idPanel);	
		
		log.leave();
	}


	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#bindUI()
	 */
	@Override
	protected void bindUI() {
		log.enter("bindUI");
		
		// nothing to do
		
		log.leave();
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	protected void syncUI() {
		log.enter("syncUI");
		
		if (entry != null) {
			
			this.updateTitleLabel(entry.getTitle());
			this.updateSummaryLabel(entry.getSummary());
			this.updateDateCreatedLabel(entry.getPublished());
			this.updateDateUpdatedLabel(entry.getUpdated());
			this.updateIdLabel(entry.getId());
			this.updateOwnersLabel(entry.getAuthors());

		}
		else {

			// clear all properties
			this.updateTitleLabel(null);
			this.updateSummaryLabel(null);
			this.updateDateCreatedLabel(null);
			this.updateDateUpdatedLabel(null);
			this.updateIdLabel(null);
			this.updateOwnersLabel(null);

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

	


	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#unbindUI()
	 */
	@Override
	protected void unbindUI() {
		log.enter("unbindUI");
		
		// nothing to do
		
		log.leave();
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#destroy()
	 */
	@Override
	public void destroy() {
		log.enter("destroy");
		
		// nothing to do
		
		log.leave();
	}

	
	
	

	public void setEntry(E entry) {
		this.entry = entry;
		this.syncUI();
	}
	
	
	

}
