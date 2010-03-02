package org.cggh.chassis.wwarn.ui.anonymizer.client;

import com.google.gwt.event.shared.GwtEvent;

public class BackToStartNavigationEvent extends GwtEvent<BackToStartNavigationHandler> {

	public static final Type<BackToStartNavigationHandler> TYPE = new Type<BackToStartNavigationHandler>();
	
	@Override
	protected void dispatch(BackToStartNavigationHandler h) {
		h.onNavigation(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<BackToStartNavigationHandler> getAssociatedType() {
		return TYPE;
	}

}
