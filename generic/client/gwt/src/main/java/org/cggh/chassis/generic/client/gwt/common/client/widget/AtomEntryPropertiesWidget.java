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
import com.google.gwt.user.client.ui.Widget;

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
	public void renderUI() {
		log.enter("renderUI");
		
		this.titleLabel = new InlineLabel();
		this.summaryLabel = new InlineLabel();
		this.createdLabel = new InlineLabel();
		this.updatedLabel = new InlineLabel();
		this.idLabel = new InlineLabel();
		this.ownersListPanel = new FlowPanel();

		// TODO fix styles
		FlowPanel titlePanel = RenderUtils.renderTitlePropertyPanel(this.titleLabel, CommonStyles.VALUE_TITLE);
		FlowPanel summaryPanel = RenderUtils.renderSummaryPropertyPanel(this.summaryLabel, CommonStyles.VALUE_SUMMARY);
		FlowPanel ownersPanel = RenderUtils.renderOwnersPropertyPanel(this.ownersListPanel, CommonStyles.VALUE_OWNERS);
		FlowPanel createdPanel = RenderUtils.renderCreatedPropertyPanel(this.createdLabel, CommonStyles.VALUE_CREATED);
		FlowPanel updatedPanel = RenderUtils.renderUpdatedPropertyPanel(this.updatedLabel, CommonStyles.VALUE_UPDATED);
		FlowPanel idPanel = RenderUtils.renderIdPropertyPanel(this.idLabel, CommonStyles.VALUE_ID);

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
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	public void syncUI() {
		log.enter("syncUI");
		
		if (entry != null) {
			
			this.syncTitleLabel(entry.getTitle());
			this.syncSummaryLabel(entry.getSummary());
			this.syncDateCreatedLabel(entry.getPublished());
			this.syncDateUpdatedLabel(entry.getUpdated());
			this.syncIdLabel(entry.getId());
			this.syncOwnersLabel(entry.getAuthors());

		}
		else {

			// clear all properties
			this.syncTitleLabel(null);
			this.syncSummaryLabel(null);
			this.syncDateCreatedLabel(null);
			this.syncDateUpdatedLabel(null);
			this.syncIdLabel(null);
			this.syncOwnersLabel(null);

		}		

		log.leave();
	}
	
	
	
	

	private void syncTitleLabel(String title) {
		if (title == null) title = "";
		titleLabel.setText(title);
	}

	
	
	


	private void syncSummaryLabel(String summary) {
		if (summary == null) summary = "";
		summaryLabel.setText(summary);
	}

	
	
	
	
	private void syncOwnersLabel(List<AtomAuthor> owners) {

		Widget answer = RenderUtils.renderAtomAuthorsAsLabel(owners, true);
//		Widget answer = RenderUtils.renderAtomAuthorsAsList(owners);
		answer.addStyleName(CommonStyles.ANSWER);

		ownersListPanel.clear();
		ownersListPanel.add(answer);

	}


	
	private void syncDateCreatedLabel(String created) {
		if (created == null) created = "";
		this.createdLabel.setText(created);
	}

	
	
	
	private void syncDateUpdatedLabel(String updated) {
		if (updated == null) updated = "";
		this.updatedLabel.setText(updated);
	}

	
	
	
	private void syncIdLabel(String id) {
		if (id == null) id = "";
		this.idLabel.setText(id);
	}

	
	

	public void setEntry(E entry) {
		this.entry = entry;
		this.syncUI();
	}
	
	
	

}
