package org.cggh.chassis.wwarn.ui.curator.client;

import com.google.gwt.event.shared.GwtEvent;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 */
public class ViewRevisionNavigationEvent extends GwtEvent<ViewRevisionNavigationHandler> {

	public static final Type<ViewRevisionNavigationHandler> TYPE = new Type<ViewRevisionNavigationHandler>();
	
	@Override
	protected void dispatch(ViewRevisionNavigationHandler h) {
		h.onNavigation(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ViewRevisionNavigationHandler> getAssociatedType() {
		return TYPE;
	}

}
