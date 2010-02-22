package org.cggh.chassis.wwarn.ui.submitter.client;

import com.google.gwt.event.shared.GwtEvent;

public class ProceedActionEvent extends GwtEvent<ProceedActionHandler> {

	public static final Type<ProceedActionHandler> TYPE = new Type<ProceedActionHandler>();
	
	@Override
	protected void dispatch(ProceedActionHandler handler) {
		handler.onAction(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ProceedActionHandler> getAssociatedType() {
		return TYPE;
	}

}
