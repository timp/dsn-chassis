/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author aliman
 *
 */
public class CancelEvent extends GwtEvent<CancelHandler> {
	
	public static final Type<CancelHandler> TYPE = new Type<CancelHandler>();
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(CancelHandler handler) {
		handler.onCancel(this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<CancelHandler> getAssociatedType() {
		return TYPE;
	}
	
}
