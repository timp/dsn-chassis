package org.cggh.chassis.wwarn.ui.curator.client;

import com.google.gwt.event.shared.GwtEvent;

public class NextRevisionNavigationEvent extends GwtEvent<NextRevisionNavigationHandler> {

	public static final Type<NextRevisionNavigationHandler> TYPE = new Type<NextRevisionNavigationHandler>();
	
	@Override
	protected void dispatch(NextRevisionNavigationHandler h) {
		h.onNavigation(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<NextRevisionNavigationHandler> getAssociatedType() {
		return TYPE;
	}

}
