/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author aliman
 *
 */
@SuppressWarnings("unchecked")
public class PropertyChangeEvent<T> extends GwtEvent<PropertyChangeHandler> {

	private T before, after;
	private Property<T> property;

	public static final Type<PropertyChangeHandler> TYPE = new Type<PropertyChangeHandler>();
	
	public PropertyChangeEvent(Property<T> property, T before, T after) {
		this.property = property;
		this.before = before;
		this.after = after;
	}
	
	public T getBefore() {
		return before;
	}

	public T getAfter() {
		return after;
	}
	
	public Property<T> getProperty() {
		return property;
	}

	@Override
	protected void dispatch(PropertyChangeHandler handler) {
		handler.onChange(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PropertyChangeHandler> getAssociatedType() {
		return TYPE;
	}

}
