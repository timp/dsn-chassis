package org.cggh.chassis.wwarn.ui.curator.client;

import com.google.gwt.event.shared.GwtEvent;

public class ListStudiesNavigationEvent extends GwtEvent<ListStudiesNavigationHandler> {

	public static final Type<ListStudiesNavigationHandler> TYPE = new Type<ListStudiesNavigationHandler>();
	
	@Override
	protected void dispatch(ListStudiesNavigationHandler h) {
		h.onNavigation(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ListStudiesNavigationHandler> getAssociatedType() {
		return TYPE;
	}

}
