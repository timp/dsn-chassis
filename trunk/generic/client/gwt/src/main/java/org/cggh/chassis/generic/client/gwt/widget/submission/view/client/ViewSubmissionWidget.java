/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import org.cggh.chassis.generic.atom.rewrite.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidget;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class ViewSubmissionWidget extends ChassisWidget {

	
	
	
	private Log log;
	private ViewSubmissionWidgetModel model;
	private ViewSubmissionWidgetController controller;
	private ViewSubmissionWidgetDefaultRenderer renderer;

	
	
	
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
		log = LogFactory.getLog(ViewSubmissionWidget.class); // instantiate here because called from superclass constructor
		log.enter("init");
		
		log.debug("instantiate a model");
		this.model = new ViewSubmissionWidgetModel(this);
		
		log.debug("instantiate a controller");
		this.controller = new ViewSubmissionWidgetController(this.model);
		
		log.debug("instantiate a renderer");
		this.renderer = new ViewSubmissionWidgetDefaultRenderer(this);
		
		log.debug("set renderer canvas");
		this.renderer.setCanvas(this.contentBox);
		
		log.leave();
	}

	
	
	

	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");
		
		// delegate this to renderer
		this.renderer.renderUI();
		
		log.leave();
		
	}
	
	
	


	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#bindUI()
	 */
	@Override
	protected void bindUI() {
		log.enter("bindUI");
		
		// delegate to renderer
		this.renderer.bindUI(this.model);
		
		log.leave();
		
	}
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	protected void syncUI() {
		log.enter("syncUI");
		
		// delegate to renderer
		this.renderer.syncUI();
		
		log.leave();
		
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#destroy()
	 */
	@Override
	public void destroy() {
		log.enter("destroy");
		
		// unbind
		this.unbindUI();

		// TODO anything else?
		
		log.leave();
		
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#unbindUI()
	 */
	@Override
	protected void unbindUI() {
		log.enter("unbindUI");
		
		// delegate to renderer
		if (this.renderer != null) this.renderer.unbindUI();
		
		
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
	
	

	
	
	
}
