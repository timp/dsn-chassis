/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public abstract class ChassisWidgetModel {
	
	
	
	
	private Log log = LogFactory.getLog(ChassisWidgetModel.class);
	protected ChassisWidget owner;
	
	
	
	
	
	public ChassisWidgetModel(ChassisWidget owner) {
		log.enter("<constructor>");
		
		this.owner = owner;
		
		log.debug("call init()");
		this.init();
		
		log.leave();
	}
	
	
	
	
	public <H extends ModelChangeHandler> HandlerRegistration addChangeHandler(H handler, GwtEvent.Type<H> type) {
		// use owner to manage change event handlers
		return this.owner.addEventHandler(handler, type);
	}
	
	
	
	
	
	protected <T, H extends ModelChangeHandler> void fireChangeEvent(ModelChangeEvent<T, H> e) {
		// use owner to manage change event handlers
		this.owner.fireEvent(e);
	}
	
	
	
	
	
	/**
	 * Initialise the properties of the model. Called from the constructor.
	 */
	protected abstract void init();
	
	
	
}
