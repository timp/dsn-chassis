package org.cggh.chassis.generic.widget.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

public class WidgetEventChannel {
	private static final Log log = LogFactory.getLog(WidgetEventChannel.class);

	private HandlerManager handlerManager;
	
	public WidgetEventChannel(Widget source) {
		log.enter("<Constructor>" + source );
		handlerManager = new HandlerManager(source);
		log.leave();
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
	
	public void fireEvent(WidgetEvent e) {
		handlerManager.fireEvent(e);
	}
	
}
