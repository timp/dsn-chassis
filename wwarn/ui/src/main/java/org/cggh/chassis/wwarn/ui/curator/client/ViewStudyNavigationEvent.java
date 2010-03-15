package org.cggh.chassis.wwarn.ui.curator.client;

import com.google.gwt.event.shared.GwtEvent;

public class ViewStudyNavigationEvent extends GwtEvent<ViewStudyNavigationHandler> {

	public static final Type<ViewStudyNavigationHandler> TYPE = new Type<ViewStudyNavigationHandler>();
	
	@Override
	protected void dispatch(ViewStudyNavigationHandler h) {
		h.onNavigation(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ViewStudyNavigationHandler> getAssociatedType() {
		return TYPE;
	}

}
