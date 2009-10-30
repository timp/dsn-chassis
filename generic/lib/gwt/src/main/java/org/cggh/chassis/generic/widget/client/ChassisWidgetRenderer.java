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
public abstract class ChassisWidgetRenderer {
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());



	protected Panel canvas;
	
	
	
	
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




	protected void clearModelChangeHandlers() {
		for (HandlerRegistration hr : this.modelChangeHandlerRegistrations) {
			try {
				hr.removeHandler();
			}
			catch (NullPointerException npe) {
				log.warn("caught null pointer exception attempting to remove model change handler", npe);
			}
		}
	}
	
	
	
	

	protected void clearChildWidgetEventHandlers() {
		for (HandlerRegistration hr : this.childWidgetEventHandlerRegistrations) {
			try {
				hr.removeHandler();
			}
			catch (NullPointerException npe) {
				log.warn("caught null pointer exception attempting to remove child widget event handler", npe);
			}
		}
	}
	
	
	
	

	
}
