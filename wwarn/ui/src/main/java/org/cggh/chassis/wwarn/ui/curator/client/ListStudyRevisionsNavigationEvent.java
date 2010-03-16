package org.cggh.chassis.wwarn.ui.curator.client;

import com.google.gwt.event.shared.GwtEvent;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 */
public class ListStudyRevisionsNavigationEvent extends GwtEvent<ListStudyRevisionsNavigationHandler> {

	public static final Type<ListStudyRevisionsNavigationHandler> TYPE = new Type<ListStudyRevisionsNavigationHandler>();
	
	@Override
	protected void dispatch(ListStudyRevisionsNavigationHandler h) {
		h.onNavigation(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ListStudyRevisionsNavigationHandler> getAssociatedType() {
		return TYPE;
	}

}
