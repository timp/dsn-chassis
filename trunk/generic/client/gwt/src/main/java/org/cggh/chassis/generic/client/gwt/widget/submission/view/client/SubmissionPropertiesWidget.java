/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.Widget;

/**
 * @author aliman
 *
 */
public class SubmissionPropertiesWidget extends Widget {

	
	
	
	private Log log;
	private SubmissionPropertiesWidgetModel model;
	private SubmissionPropertiesWidgetDefaultRenderer renderer;

	
	
	
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
	 * @see org.cggh.chassis.generic.widget.client.Widget#init()
	 */
	@Override
	public void init() {
		log = LogFactory.getLog(SubmissionPropertiesWidget.class); // need to instantiate here
		log.enter("init");

		log.debug("instantiate model");
		this.model = new SubmissionPropertiesWidgetModel();

		log.debug("instantiate default renderer");
		this.renderer = new SubmissionPropertiesWidgetDefaultRenderer();
		
		log.debug("set renderer canvas");
		this.renderer.setCanvas(this.contentBox);

		log.leave();

	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.Widget#destroy()
	 */
	@Override
	public void destroy() {
		log.enter("destroy");

		// unbind
		this.unbindUI();
		
		// clear all model listeners
		if (this.model != null) this.model.clearListeners();
		
		log.leave();

	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.Widget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");

		// delegate this to renderer
		this.renderer.renderUI();

		log.leave();

	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.Widget#bindUI()
	 */
	@Override
	protected void bindUI() {
		log.enter("bindUI");

		// delegate to renderer
		this.renderer.bindUI(this.model);

		log.leave();

	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.Widget#syncUI()
	 */
	@Override
	protected void syncUI() {
		log.enter("syncUI");

		// delegate to renderer
		this.renderer.syncUI();

		log.leave();

	}




	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.Widget#unbindUI()
	 */
	@Override
	protected void unbindUI() {
		log.enter("unbindUI");
		
		// delegate to renderer
		if (this.renderer != null) this.renderer.unbindUI();
		
		log.leave();

	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.Widget#getName()
	 */
	@Override
	protected String getName() {
		return "submissionPropertiesWidget";
	}

	
	
	
	
}
