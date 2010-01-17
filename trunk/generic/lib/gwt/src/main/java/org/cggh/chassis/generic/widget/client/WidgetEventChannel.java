package org.cggh.chassis.generic.widget.client;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

public class WidgetEventChannel {

	private HandlerManager handlerManager;
	
	public WidgetEventChannel(Widget source) {
		handlerManager = new HandlerManager(source);
	}
	
	protected WidgetEvent createEvent() {
		return new WidgetEvent();
	}
	
	public HandlerRegistration addHandler(WidgetEventHandler h) {
		return handlerManager.addHandler(WidgetEvent.TYPE, h);
	}
	
	public void fireEvent() {
		WidgetEvent e = createEvent();
		handlerManager.fireEvent(e);
	}
	
}
