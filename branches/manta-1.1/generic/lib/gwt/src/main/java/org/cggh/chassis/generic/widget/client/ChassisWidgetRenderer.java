/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author aliman
 *
 */
public abstract class ChassisWidgetRenderer<M> {
	
	
	
	
	private Log log = LogFactory.getLog(ChassisWidgetRenderer.class);



	protected Panel canvas;
	protected M model;
	
	
	
	
	/**
	 * Set to store model change handler registrations so they can be removed if needed.
	 */
	protected Set<HandlerRegistration> modelChangeHandlerRegistrations = new HashSet<HandlerRegistration>();


	
	
	/**
	 * Set to store child widget event handler registrations so they can be removed if needed.
	 */
	protected Set<HandlerRegistration> childWidgetEventHandlerRegistrations = new HashSet<HandlerRegistration>();

	
	
	
	/**
	 * @param canvas the canvas to set
	 */
	public void setCanvas(Panel canvas) {
		this.canvas = canvas;
	}
	
	
	
	
	/**
	 * @return the canvas
	 */
	public Panel getCanvas() {
		return canvas;
	}



	/**
	 * This method is responsible for creating and adding the child widgets of
	 * this widget's content box.
	 */
	protected void renderUI() {}

	
	
	
	/**
	 * This method is responsible for attaching event listeners which bind the UI
	 * to the widget state (i.e., the widget's model). These listeners are generally
	 * property change listeners - used to update the state of the UI in response
	 * to changes in the model's properties. This method also attaches event listeners
	 * to child widgets making up the UI to map user interactions to the widget's 
	 * API.
	 */
	protected void bindUI(M model) {
		log.enter("bindUI");
		
		log.debug("unbind to clear anything");
		this.unbindUI();
		
		log.debug("keep reference to model");
		this.model = model;
		
		log.debug("register handlers for model property change events");
		this.registerHandlersForModelChanges();
		
		log.debug("register handlers for child widget events");
		this.registerHandlersForChildWidgetEvents();
		
		log.leave();
	}

	
	
	
	/**
	 * 
	 */
	protected void registerHandlersForModelChanges() {}




	/**
	 * 
	 */
	protected void registerHandlersForChildWidgetEvents() {}

	


	/**
	 * This method is responsible for setting the state of the UI based on the 
	 * current state of the widget at the time of rendering.
	 */
	protected void syncUI() {}

	
	
	
	/**
	 * 
	 */
	public void unbindUI() {
		log.enter("unbindUI");
		
		this.clearModelChangeHandlers();
		this.clearChildWidgetEventHandlers();
		
		log.leave();
	}
	
	
	
	
	/**
	 * TODO review this, often throws NPE or AssertionError
	 */
	protected void clearModelChangeHandlers() {
		log.enter("clearModelChangeHandlers");
		for (HandlerRegistration hr : this.modelChangeHandlerRegistrations) {
			try {
				hr.removeHandler();
			}
			catch (Throwable t) {
				log.warn("caught throwable attempting to remove model change handler", t);
			}
		}
		log.leave();
	}
	
	
	
	

	/**
	 * TODO review this, often throws NPE
	 */
	protected void clearChildWidgetEventHandlers() {
		for (HandlerRegistration hr : this.childWidgetEventHandlerRegistrations) {
			try {
				hr.removeHandler();
			}
			catch (Throwable t) {
				log.warn("caught throwable attempting to remove child widget event handler", t);
			}
		}
	}
	
	
	
	

	
}
