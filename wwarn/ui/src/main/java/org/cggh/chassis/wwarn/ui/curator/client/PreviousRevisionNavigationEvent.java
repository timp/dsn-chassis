package org.cggh.chassis.wwarn.ui.curator.client;

import com.google.gwt.event.shared.GwtEvent;

public class PreviousRevisionNavigationEvent extends GwtEvent<PreviousRevisionNavigationHandler> {

	public static final Type<PreviousRevisionNavigationHandler> TYPE = new Type<PreviousRevisionNavigationHandler>();
	
	@Override
	protected void dispatch(PreviousRevisionNavigationHandler h) {
		h.onNavigation(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PreviousRevisionNavigationHandler> getAssociatedType() {
		return TYPE;
	}

}
