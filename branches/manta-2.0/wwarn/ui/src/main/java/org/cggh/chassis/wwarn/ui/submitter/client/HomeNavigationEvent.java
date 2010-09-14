package org.cggh.chassis.wwarn.ui.submitter.client;

import com.google.gwt.event.shared.GwtEvent;

public class HomeNavigationEvent extends GwtEvent<HomeNavigationHandler> {

	public static final Type<HomeNavigationHandler> TYPE = new Type<HomeNavigationHandler>();
	
	@Override
	protected void dispatch(HomeNavigationHandler h) {
		h.onNavigation(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<HomeNavigationHandler> getAssociatedType() {
		return TYPE;
	}

}
