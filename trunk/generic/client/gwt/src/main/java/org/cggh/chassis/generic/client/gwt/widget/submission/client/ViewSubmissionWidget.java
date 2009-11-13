/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class ViewSubmissionWidget 
	extends DelegatingWidget<ViewSubmissionWidgetModel, ViewSubmissionWidgetRenderer> {

	
	
	
	private Log log;
	private ViewSubmissionWidgetController controller;

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected ViewSubmissionWidgetModel createModel() {
		return new ViewSubmissionWidgetModel();
	}






	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected ViewSubmissionWidgetRenderer createRenderer() {
		return new ViewSubmissionWidgetRenderer(this);
	}
	
	

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");
		
		super.init(); // will instantiate model and renderer
		
		log.debug("instantiate a controller");
		this.controller = new ViewSubmissionWidgetController(this, this.model);
		
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





	private void ensureLog() {
		log = LogFactory.getLog(ViewSubmissionWidget.class); 
	}







	
}
