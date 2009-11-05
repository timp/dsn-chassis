/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.DelegatingChassisWidget;

import com.google.gwt.event.shared.HandlerRegistration;




/**
 * @author aliman
 *
 */
public class CreateDataFileWidget 
	extends DelegatingChassisWidget<AsyncWidgetModel, CreateDataFileWidgetRenderer> {

	
	
	
	public static final String NAME = "createDataFileWidget";
	
	
	private Log log;
	private CreateDataFileWidgetController controller;

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#getName()
	 */
	@Override
	protected String getName() {
		return NAME;
	}


	
	
	protected void ensureLog() {
		if (log == null) log = LogFactory.getLog(CreateDataFileWidget.class);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		this.model = new AsyncWidgetModel(this);
		this.controller = new CreateDataFileWidgetController(this, this.model);
		this.renderer = new CreateDataFileWidgetRenderer(this);
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
