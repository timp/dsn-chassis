/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;

import org.cggh.chassis.generic.client.gwt.common.client.CancelEvent;
import org.cggh.chassis.generic.client.gwt.common.client.CancelHandler;
import org.cggh.chassis.generic.client.gwt.common.client.ErrorEvent;
import org.cggh.chassis.generic.client.gwt.common.client.ErrorHandler;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidget;

import com.google.gwt.event.shared.HandlerRegistration;




/**
 * @author aliman
 *
 */
public class CreateDataFileWidget extends ChassisWidget {

	
	
	
	
	private Log log = LogFactory.getLog(CreateDataFileWidget.class);
	private AsyncWidgetModel model;
	private CreateDataFileWidgetController controller;
	private CreateDataFileWidgetRenderer renderer;

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#getName()
	 */
	@Override
	protected String getName() {
		return "createDataFileWidget";
	}


	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		log.enter("init");

		this.model = new AsyncWidgetModel(this);
		this.controller = new CreateDataFileWidgetController(this, this.model);
		this.renderer = new CreateDataFileWidgetRenderer(this);
		this.renderer.setCanvas(this.contentBox);
		this.renderer.setController(this.controller);

		log.leave();
	}


	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");

		// delegate
		this.renderer.renderUI();

		log.leave();

	}


	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#bindUI()
	 */
	@Override
	protected void bindUI() {
		log.enter("bindUI");

		// delegate
		this.renderer.bindUI(this.model);
		
		log.leave();

	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	protected void syncUI() {
		log.enter("syncUI");

		// delegate
		this.renderer.syncUI();

		log.leave();

	}


	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#unbindUI()
	 */
	@Override
	protected void unbindUI() {
		log.enter("unbindUI");

		// delegate
		this.renderer.unbindUI();

		log.leave();

	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#destroy()
	 */
	@Override
	public void destroy() {
		log.enter("destroy");

		this.unbindUI();

		// anything else?

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
	
	
	
	
	
	
	
	/**
	 * Register handler for cancel event.
	 * 
	 * @param h handler to receive events
	 * @return a handler registration to remove the handler if needed
	 */
	public HandlerRegistration addCancelHandler(CancelHandler h) {
		return this.addHandler(h, CancelEvent.TYPE);
	}
	
	
	
	
	public HandlerRegistration addErrorHandler(ErrorHandler h) {
		return this.addHandler(h, ErrorEvent.TYPE);
	}
	

}
