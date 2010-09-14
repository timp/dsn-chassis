package org.cggh.chassis.generic.widget.client;

import com.google.gwt.event.shared.EventHandler;

public interface PropertyChangeHandler<T> extends EventHandler {

	public void onChange(PropertyChangeEvent<T> e);
	
}
