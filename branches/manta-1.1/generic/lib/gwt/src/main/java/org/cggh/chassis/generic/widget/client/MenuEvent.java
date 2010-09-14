/**
 * 
 */
package org.cggh.chassis.generic.widget.client;


import com.google.gwt.event.shared.GwtEvent;

public class MenuEvent extends GwtEvent<MenuEventHandler> {
	
	public static final Type<MenuEventHandler> TYPE = new Type<MenuEventHandler>();

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(MenuEventHandler handler) {
		handler.onMenuCommand(this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<MenuEventHandler> getAssociatedType() {
		return TYPE;
	}

	
}