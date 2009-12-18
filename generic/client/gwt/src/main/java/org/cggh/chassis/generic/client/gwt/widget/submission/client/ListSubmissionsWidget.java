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
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected ListSubmissionsWidgetModel createModel() {
		return new ListSubmissionsWidgetModel();
	}
	
	
	protected ListSubmissionsWidgetModel getModel() {
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
		// delegate to controller
		this.controller.refreshSubmissions();
		
	}
	
	
	
	
	public HandlerRegistration addViewSubmissionActionHandler(SubmissionActionHandler h) {
		return this.addHandler(h, ViewSubmissionActionEvent.TYPE);
	}

	
	
	
}
