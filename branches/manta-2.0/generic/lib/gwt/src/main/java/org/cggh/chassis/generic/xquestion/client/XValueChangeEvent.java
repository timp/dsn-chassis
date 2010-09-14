/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author aliman
 *
 */
public class XValueChangeEvent extends GwtEvent<XValueChangeHandler> {

	public static final Type<XValueChangeHandler> TYPE = new Type<XValueChangeHandler>();
	
	@Override
	protected void dispatch(XValueChangeHandler handler) {
		handler.onChange(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<XValueChangeHandler> getAssociatedType() {
		return TYPE;
	}

}