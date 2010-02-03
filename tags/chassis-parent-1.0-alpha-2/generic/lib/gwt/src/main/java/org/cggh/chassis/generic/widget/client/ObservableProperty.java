package org.cggh.chassis.generic.widget.client;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

public class ObservableProperty<T> {

	private T value;
	private HandlerManager handlerManager = new HandlerManager(this);
	
	public T get() {
		return value;
	}
	
	public void set(T value) {
		T before = this.value;
		T after = value;
		if ( ( before == null && after != null ) ||
			 ( before != null && after == null ) ||
			 ( before != null && after != null && !before.equals(after) ) ) {
			
			// value has changed, set and fire event
			this.value = value;
			PropertyChangeEvent<T> e = new PropertyChangeEvent<T>(this, before, after);
			handlerManager.fireEvent(e);
			
		}
		else {
			// no change, do nothing
		}
	}
	
	public boolean isNull() {
		return value == null;
	}
	
	public HandlerRegistration addChangeHandler(PropertyChangeHandler<T> h) {
		return handlerManager.addHandler(PropertyChangeEvent.TYPE, h);
	}
	
}
