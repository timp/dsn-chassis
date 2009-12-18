/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class ListSubmissionsWidget 
	extends DelegatingWidget<ListSubmissionsWidgetModel, ListSubmissionsWidgetRenderer> {

	
	
	
	
	Log log = LogFactory.getLog(ListSubmissionsWidget.class);
	private ListSubmissionsWidgetController controller;
	private ListSubmissionsWidgetModel model;
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected ListSubmissionsWidgetModel createModel() {
		if (log == null) log = LogFactory.getLog(ListSubmissionsWidget.class);
		log.enter("createModel");
		if (model == null) { 
			model = new ListSubmissionsWidgetModel();
			log.debug("Creating model");
		}
		log.leave();
		return model;
	}
	
	
	// TODO Refactor - model is potentially created through two paths
	// however need to avoid overwriting model
	protected ListSubmissionsWidgetModel getModel() {
		log.enter("getModel");
		if (model == null) 
			model = createModel(); 
		log.leave();
		return model;
	}

	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected ListSubmissionsWidgetRenderer createRenderer() {
		return new ListSubmissionsWidgetRenderer(this);
	}
	
	
	
	
	@Override
	public void init() {
		
		super.init(); // this should instantiate model and renderer
		
		this.controller = new ListSubmissionsWidgetController(this, this.model);
		
	}

	
	
	
	
	@Override
	public void refresh() {
		//TODO consider refactor
		if (this.model != null)
			// delegate to controller
			this.controller.refreshSubmissions();
		
	}
	
	
	
	
	public HandlerRegistration addViewSubmissionActionHandler(SubmissionActionHandler h) {
		return this.addHandler(h, ViewSubmissionActionEvent.TYPE);
	}

	
	
	
}
