package org.cggh.chassis.generic.widget.client;

import com.google.gwt.event.shared.EventHandler;

public interface WidgetEventHandler<E extends WidgetEvent> extends EventHandler {

	void onEvent(E e);

}
