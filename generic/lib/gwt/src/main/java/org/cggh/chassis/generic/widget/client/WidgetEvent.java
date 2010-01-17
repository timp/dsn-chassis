package org.cggh.chassis.generic.widget.client;

import com.google.gwt.event.shared.GwtEvent;

public class WidgetEvent extends GwtEvent<WidgetEventHandler> {

	public static final Type<WidgetEventHandler> TYPE = new Type<WidgetEventHandler>();
	
	@Override
	protected void dispatch(WidgetEventHandler h) {
		h.onEvent(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<WidgetEventHandler> getAssociatedType() {
		return TYPE;
	}

}
