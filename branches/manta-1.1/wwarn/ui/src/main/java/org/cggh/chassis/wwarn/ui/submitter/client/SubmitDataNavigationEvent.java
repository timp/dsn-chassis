package org.cggh.chassis.wwarn.ui.submitter.client;

import com.google.gwt.event.shared.GwtEvent;

public class SubmitDataNavigationEvent 
	extends	GwtEvent<SubmitDataNavigationHandler> {

	public static final Type<SubmitDataNavigationHandler> TYPE = new Type<SubmitDataNavigationHandler>();
	
	@Override
	protected void dispatch(SubmitDataNavigationHandler handler) {
		handler.onNavigation(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<SubmitDataNavigationHandler> getAssociatedType() {
		return TYPE;
	}

}
