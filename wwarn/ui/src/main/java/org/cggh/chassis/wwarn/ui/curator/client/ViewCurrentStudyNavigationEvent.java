package org.cggh.chassis.wwarn.ui.curator.client;

import com.google.gwt.event.shared.GwtEvent;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 */
public class ViewCurrentStudyNavigationEvent extends GwtEvent<ViewCurrentStudyNavigationHandler> {

	public static final Type<ViewCurrentStudyNavigationHandler> TYPE = new Type<ViewCurrentStudyNavigationHandler>();
	
	@Override
	protected void dispatch(ViewCurrentStudyNavigationHandler h) {
		h.onNavigation(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ViewCurrentStudyNavigationHandler> getAssociatedType() {
		return TYPE;
	}

}
