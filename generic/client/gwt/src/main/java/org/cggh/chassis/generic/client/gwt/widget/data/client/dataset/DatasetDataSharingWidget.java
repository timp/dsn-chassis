/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
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
			
			SubmissionLink link = this.entry.getSubmissionLink();
			
			if (link == null) {
				
				log.debug("no submission link found, dataset has not been shared");
				
				this.syncNotShared();
				
			}
			else {
				
				log.debug("found submission link, dataset is shared");
				
				this.syncShared();
				
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
		
		add(p("This dataset has <strong>not</strong> been shared with "+Configuration.getNetworkName()+".")); // TODO i18n
		
		Anchor shareDatasetAction = RenderUtils.renderActionAsAnchor("share this dataset with "+Configuration.getNetworkName());

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
		// TODO Auto-generated method stub
		
	}




	public void setEntry(DatasetEntry entry) {
		this.entry = entry;
		this.syncUI();
	}
	
	
	
	
	public HandlerRegistration addShareDatasetActionHandler(DatasetActionHandler h) {
		return this.addHandler(h, ShareDatasetActionEvent.TYPE);
	}
	
	
}
