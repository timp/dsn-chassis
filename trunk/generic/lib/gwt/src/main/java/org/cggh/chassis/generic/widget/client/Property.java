package org.cggh.chassis.generic.widget.client;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

public class Property<T> {

	private T value;
	private HandlerManager handlerManager = new HandlerManager(this);
	
	public T get() {
		return value;
	}
	
	public void set(T value) {
		this.value = value;
	}
	
	public HandlerRegistration addChangeHandler(PropertyChangeHandler<T> h) {
		return handlerManager.addHandler(PropertyChangeEvent.TYPE, h);
	}
	
}
