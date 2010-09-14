package org.cggh.chassis.wwarn.ui.submitter.client;

import com.google.gwt.event.shared.GwtEvent;

public class StepBackNavigationEvent extends GwtEvent<StepBackNavigationHandler> {

	public static final Type<StepBackNavigationHandler> TYPE = new Type<StepBackNavigationHandler>();
	
	@Override
	protected void dispatch(StepBackNavigationHandler h) {
		h.onNavigation(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<StepBackNavigationHandler> getAssociatedType() {
		return TYPE;
	}

}
