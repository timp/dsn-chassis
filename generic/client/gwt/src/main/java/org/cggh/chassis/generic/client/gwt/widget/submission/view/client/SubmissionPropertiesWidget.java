/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import org.cggh.chassis.generic.atom.rewrite.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingChassisWidget;

/**
 * @author aliman
 *
 */
public class SubmissionPropertiesWidget 
	extends DelegatingChassisWidget<SubmissionPropertiesWidgetModel, SubmissionPropertiesWidgetRenderer> {

	
	
	
	private Log log;
	private SubmissionPropertiesWidgetModel model;
	private SubmissionPropertiesWidgetRenderer renderer;

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.Widget#getName()
	 */
	@Override
	protected String getName() {
		return "submissionPropertiesWidget";
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.Widget#init()
	 */
	@Override
	public void init() {
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





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#ensureLog()
	 */
	@Override
	protected void ensureLog() {
		log = LogFactory.getLog(SubmissionPropertiesWidget.class);
	}
	
	
	
	
	
}
