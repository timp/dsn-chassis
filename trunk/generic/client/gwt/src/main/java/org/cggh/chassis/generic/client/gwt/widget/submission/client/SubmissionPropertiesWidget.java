/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

/**
 * @author aliman
 *
 */
public class SubmissionPropertiesWidget 
	extends DelegatingWidget<SubmissionPropertiesWidgetModel, SubmissionPropertiesWidgetRenderer> {

	
	
	
	private Log log;

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.Widget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		log.debug("instantiate model");
		this.model = new SubmissionPropertiesWidgetModel(this);

		log.debug("instantiate default renderer");
		this.renderer = new SubmissionPropertiesWidgetRenderer();
		
		log.debug("set renderer canvas");
		this.renderer.setCanvas(this.contentBox);

		log.leave();

	}

	
	
	
	
	/**
	 * Set the submission entry to display properties for.
	 * 
	 * @param entry
	 */
	public void setSubmissionEntry(SubmissionEntry entry) {
		log.enter("setSubmissionEntry");

		this.model.setSubmissionEntry(entry);

		// should not need to do anything else, renderer will automatically
		// update UI on submission entry change
		
		log.leave();
	}






	private void ensureLog() {
		log = LogFactory.getLog(SubmissionPropertiesWidget.class);
	}
	
	
	
	
	
}
