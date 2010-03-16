package org.cggh.chassis.wwarn.ui.curator.client;

import com.google.gwt.event.shared.GwtEvent;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 */
public class SaveStudyEvent extends GwtEvent<SaveStudyHandler> {

	public static final Type<SaveStudyHandler> TYPE = new Type<SaveStudyHandler>();
	
	@Override
	protected void dispatch(SaveStudyHandler h) {
		h.onNavigation(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<SaveStudyHandler> getAssociatedType() {
		return TYPE;
	}

}
