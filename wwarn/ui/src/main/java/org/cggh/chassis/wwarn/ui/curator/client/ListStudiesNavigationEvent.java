package org.cggh.chassis.wwarn.ui.curator.client;

import com.google.gwt.event.shared.GwtEvent;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 */
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
