/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomext.client.review.ReviewLink;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionLink;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;

import static org.cggh.chassis.generic.widget.client.HtmlElements.*;
/**
 * @author aliman
 *
 */
public class DatasetDataSharingWidget extends ChassisWidget {

	
	
	
	// utility fields
	Log log = LogFactory.getLog(DatasetDataSharingWidget.class);
	
	
	
	
	// state fields
	private DatasetEntry entry;
	
	
	
	
	
	private void ensureLog() {
		if (this.log == null) this.log = LogFactory.getLog(DatasetDataSharingWidget.class);
	}
	
	
	
	
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		this.entry = null;

		log.leave();
	}
	
	
	
	
	@Override
	public void renderUI() {
		// TODO
	}
	
	
	
	
	@Override
	public void bindUI() {
		// TODO
	}
	
	
	
	
	@Override
	public void syncUI() {
		log.enter("syncUI");
		
		this.clear();
		
		if (this.entry != null) {
		
			log.debug("has dataset been shared before? look for submission link...");
			
			SubmissionLink submissionLink = this.entry.getSubmissionLink();
			
			if (submissionLink == null) {
				
				log.debug("no submission link found, dataset has not been shared");
				
				this.syncNotShared();
				
			}
			else {
				
				log.debug("found submission link, dataset is shared");
				
				this.syncShared();
				log.debug("has dataset been reviewed? look for review link...");
				
				ReviewLink reviewLink = submissionLink.getEntry().getReviewLink();
				
				
				if (reviewLink != null) {
					
					log.debug("review link found, dataset has been reviewed");
					
					// TODO Review - perhaps accepted for curation within ??
					add(pWidget("This dataset was accepted into  "+Configuration.getNetworkName()+ 
							" by " + strong(RenderUtils.renderAtomAuthorsAsCommaDelimitedEmailString(
									reviewLink.getEntry().getAuthors())) +
							" on " + strong(reviewLink.getEntry().getPublished()) + ".")); // TODO i18n
					
				}
				// TODO Add Curator assignment
				
			}
			
			
		}
		else {
			
			log.debug("entry is null, not syncing anything");
			
		}
		
		
		
		log.leave();
	}
	
	
	
	
	/**
	 * 
	 */
	private void syncNotShared() {
		log.enter("syncNotShared");
		
		add(pWidget("This dataset has " + 
				strong("not") + 
				" been shared with "+Configuration.getNetworkName()+".")); // TODO i18n
		
		Anchor shareDatasetAction = RenderUtils.renderActionAnchor("share this dataset with "+Configuration.getNetworkName()+"...");

		shareDatasetAction.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {

				fireEvent(new ShareDatasetActionEvent());
				
			}
		});
		
		add(shareDatasetAction);
		
		log.leave();
	}




	/**
	 * 
	 */
	private void syncShared() {
		log.enter("syncShared");
		
		SubmissionEntry se = this.entry.getSubmissionLink().getEntry();
		
		String author = RenderUtils.renderAtomAuthorsAsCommaDelimitedEmailString(se.getAuthors());
		String created = se.getPublished();
		
		add(pWidget("This dataset was shared with "+Configuration.getNetworkName()+
				" by "+strong(author)+
				" on "+strong(created)+"."));
		
		log.leave();
	}




	public void setEntry(DatasetEntry entry) {
		this.entry = entry;
		this.syncUI();
	}
	
	
	
	
	public HandlerRegistration addShareDatasetActionHandler(DatasetActionHandler h) {
		return this.addHandler(h, ShareDatasetActionEvent.TYPE);
	}
	
	
}
