/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public abstract class ChassisWidgetModel {
	
	
	
	
	private Log log = LogFactory.getLog(ChassisWidgetModel.class);
	private HandlerManager handlerManager;
	
	
	
	
	
	public ChassisWidgetModel() {
		log.enter("<constructor>");
		
		log.debug("instantiate handler manager");
		this.handlerManager = new HandlerManager(this);
		
		log.debug("call init()");
		this.init();
		
		log.leave();
	}
	
	
	
	
	public <H extends ModelChangeHandler> HandlerRegistration addChangeHandler(H handler, GwtEvent.Type<H> type) {
		return this.handlerManager.addHandler(type, handler);
	}
	
	
	
	
	
	protected <T, H extends ModelChangeHandler> void fireChangeEvent(ModelChangeEvent<T, H> e) {
		this.handlerManager.fireEvent(e);
	}
	
	
	
	
	
	/**
	 * Initialise the properties of the model. Called from the constructor.
	 */
	protected abstract void init();
	
	
	
}
