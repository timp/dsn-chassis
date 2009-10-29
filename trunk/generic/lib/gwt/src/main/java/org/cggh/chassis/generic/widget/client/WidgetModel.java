/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author aliman
 *
 */
public abstract class WidgetModel<L> {
	
	
	
	
	private Log log = LogFactory.getLog(WidgetModel.class);
	
	
	
	
	protected Set<L> listeners = new HashSet<L>();
	
	
	
	
	public WidgetModel() {
		log.enter("<constructor>");
		
		log.debug("call init()");
		this.init();
		
		log.leave();
	}
	
	
	
	
	public void addListener(L listener) {
		this.listeners.add(listener);
	}
	
	
	
	
	public void removeListener(L listener) {
		this.listeners.remove(listener);
	}
	
	
	
	
	public void clearListeners() {
		this.listeners.clear();
	}
	
	
	
	
	
	/**
	 * Initialise the properties of the model. Called from the constructor.
	 */
	protected abstract void init();
	
	
	
}
