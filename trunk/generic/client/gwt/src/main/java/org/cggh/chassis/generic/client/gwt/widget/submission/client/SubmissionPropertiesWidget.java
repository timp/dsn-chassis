/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import java.util.List;

import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.widget.client.ChassisWidget;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;

/**
 * @author aliman
 *
 */
public class SubmissionPropertiesWidget extends ChassisWidget {

	
	
	
	
	// state fields
	private SubmissionEntry entry;
	
	
	
	// UI fields
	private FlowPanel submitterLabelContainer;
	private InlineLabel submissionDateLabel;
	private InlineLabel idLabel;
	private InlineLabel curatorLabel;
	
	
	
	
	@Override
	public void init() {
		
		super.init();
		
		this.entry = null;
		
	}
	
	
	
	
	@Override
	public void renderUI() {
		
		this.submitterLabelContainer = new FlowPanel();
		// TODO review use of owners style name here
		FlowPanel submitterPropertyPanel = RenderUtils.renderPropertyPanel("Submitter", this.submitterLabelContainer, CommonStyles.VALUE_OWNERS, null);
		this.add(submitterPropertyPanel);
		
		this.submissionDateLabel = new InlineLabel();
		FlowPanel submissionDatePropertyPanel = RenderUtils.renderPropertyPanel("Submission Date", this.submissionDateLabel, null, null);
		this.add(submissionDatePropertyPanel);
		
		this.curatorLabel = new InlineLabel();
		FlowPanel curatorPropertyPanel = RenderUtils.renderPropertyPanel("Curator", this.curatorLabel, null, null);
		this.add(curatorPropertyPanel);
		
		this.idLabel = new InlineLabel();
		FlowPanel idPropertyPanel = RenderUtils.renderIdPropertyPanel(this.idLabel, CommonStyles.VALUE_ID);
		this.add(idPropertyPanel);
		
		
	}
	
	
	
	
	@Override
	public void syncUI() {
	
		if (this.entry != null) {

			this.syncSubmitterLabel(this.entry.getAuthors());
			this.syncSubmissionDate(this.entry.getUpdated());
			this.syncCurator(this.entry.getCurator());
			this.syncSubmissionId(this.entry.getId());
			
		}
		else {
			
			this.syncSubmitterLabel(null);
			this.syncSubmissionDate(null);
			this.syncCurator(null);
			this.syncSubmissionId(null);

		}
	}




	/**
	 * @param object
	 */
	private void syncCurator(String curator) {
		String email = (curator != null) ? curator : "no curator has been assigned";
		this.curatorLabel.setText(email);
	}




	/**
	 * @param id
	 */
	private void syncSubmissionId(String id) {
		if (id == null) id = "";
		this.idLabel.setText(id);
	}




	/**
	 * @param updated
	 */
	private void syncSubmissionDate(String updated) {
		if (updated == null) updated = "";
		this.submissionDateLabel.setText(updated);
	}




	/**
	 * @param object
	 */
	private void syncSubmitterLabel(List<AtomAuthor> authors) {
		
		Label submitterLabel = RenderUtils.renderAtomAuthorsAsLabel(authors, true);
		
		this.submitterLabelContainer.clear();
		this.submitterLabelContainer.add(submitterLabel);
		
	}




	/**
	 * @param after
	 */
	public void setEntry(SubmissionEntry submissionEntry) {
		this.entry = submissionEntry;
		this.syncUI();
	}
	
	
	
	
}
