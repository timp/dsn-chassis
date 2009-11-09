/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

import com.google.gwt.event.shared.HandlerRegistration;




/**
 * @author aliman
 *
 */
public class NewDataFileWidget 
	extends DelegatingWidget<AsyncWidgetModel, NewDataFileWidgetRenderer> {

	
	
	
	
	
	private Log log;
	private NewDataFileWidgetController controller;

	
	
	
	protected void ensureLog() {
		if (log == null) log = LogFactory.getLog(NewDataFileWidget.class);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		this.model = new AsyncWidgetModel(this);
		this.controller = new NewDataFileWidgetController(this, this.model);
		this.renderer = new NewDataFileWidgetRenderer(this);
		this.renderer.setCanvas(this.contentBox);
		this.renderer.setController(this.controller);

		log.leave();
	}


	
	
	
	/**
	 * Register handler for create success event.
	 * 
	 * @param h handler to receive events
	 * @return a handler registration to remove the handler if needed
	 */
	public HandlerRegistration addCreateDataFileSuccessHandler(CreateDataFileSuccessHandler h) {
		return this.addHandler(h, CreateDataFileSuccessEvent.TYPE);
	}
	
	
	
	
	
}
