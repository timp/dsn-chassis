package org.cggh.chassis.wwarn.ui.curator.client;

import com.google.gwt.event.shared.GwtEvent;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 */
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
