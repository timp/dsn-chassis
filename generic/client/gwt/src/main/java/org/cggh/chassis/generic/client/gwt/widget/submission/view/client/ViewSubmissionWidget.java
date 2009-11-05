/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import org.cggh.chassis.generic.atom.rewrite.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingChassisWidget;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class ViewSubmissionWidget 
	extends DelegatingChassisWidget<ViewSubmissionWidgetModel, ViewSubmissionWidgetRenderer> {

	
	
	
	private Log log;
	private ViewSubmissionWidgetModel model;
	private ViewSubmissionWidgetController controller;
	private ViewSubmissionWidgetRenderer renderer;

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#getName()
	 */
	@Override
	protected String getName() {
		return "viewSubmissionWidget";
	}
	
	
	


	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		log.enter("init");
		
		log.debug("instantiate a model");
		this.model = new ViewSubmissionWidgetModel(this);
		
		log.debug("instantiate a controller");
		this.controller = new ViewSubmissionWidgetController(this, this.model);
		
		log.debug("instantiate a renderer");
		this.renderer = new ViewSubmissionWidgetRenderer(this);
		
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

		// delegate to controller
		this.controller.setSubmissionEntry(entry);
		
		log.leave();
	}





	public void retrieveSubmissionEntry(String submissionEntryUrl) {
		log.enter("retrieveSubmissionEntry");
		
		// delegate to controller
		this.controller.retrieveSubmissionEntry(submissionEntryUrl);
		
		log.leave();
	}

	
	
		
	
	/**
	 * Register interest in edit submission action events.
	 * 
	 * @param h handler to receive events
	 * @return a handler registration to remove the handler if needed
	 */
	public HandlerRegistration addEditSubmissionActionHandler(EditSubmissionActionHandler h) {
		return this.addHandler(h, EditSubmissionActionEvent.TYPE);
	}
	
	

	
	/**
	 * Register interest in upload data file action events.
	 * 
	 * @param h handler to receive events
	 * @return a handler registration to remove the handler if needed
	 */
	public HandlerRegistration addUploadDataFileActionHandler(UploadDataFileActionHandler h) {
		return this.addHandler(h, UploadDataFileActionEvent.TYPE);
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#ensureLog()
	 */
	@Override
	protected void ensureLog() {
		log = LogFactory.getLog(ViewSubmissionWidget.class); 
	}
	
	

	
	
	
}
