/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author aliman
 *
 */
public abstract class ModelChangeEvent<T, H extends EventHandler> extends GwtEvent<H> {

	private T before, after;
	
	public ModelChangeEvent(T before, T after) {
		this.before = before;
		this.after = after;
	}
	
	public T getBefore() {
		return before;
	}

	public T getAfter() {
		return after;
	}

	
}
