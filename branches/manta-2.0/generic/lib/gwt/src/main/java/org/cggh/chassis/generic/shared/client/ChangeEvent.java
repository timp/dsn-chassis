/**
 * 
 */
package org.cggh.chassis.generic.shared.client;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author aliman
 *
 */
public abstract class ChangeEvent<T> extends GwtEvent<ChangeHandler<T>> {

	private T before, after;
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(ChangeHandler<T> handler) {
		handler.onChange(this);
	}
	
	public ChangeEvent(T before, T after) {
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
