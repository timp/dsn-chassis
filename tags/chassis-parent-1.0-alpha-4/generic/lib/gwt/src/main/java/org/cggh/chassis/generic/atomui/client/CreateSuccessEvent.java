/**
 * 
 */
package org.cggh.chassis.generic.atomui.client;

import org.cggh.chassis.generic.atom.client.AtomEntry;


/**
 * @author aliman
 *
 */
@SuppressWarnings("unchecked")
public class CreateSuccessEvent
	<E extends AtomEntry>
	extends EventWithAtomEntry<CreateSuccessHandler, E> {
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(CreateSuccessHandler handler) {
		handler.onCreateSuccess(this);
	}

	public static final Type<CreateSuccessHandler> TYPE = new Type<CreateSuccessHandler>();
	
	@Override
	public Type<CreateSuccessHandler> getAssociatedType() { return TYPE; }

}