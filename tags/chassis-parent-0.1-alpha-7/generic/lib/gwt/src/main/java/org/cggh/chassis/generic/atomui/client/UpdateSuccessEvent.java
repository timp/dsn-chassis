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
public class UpdateSuccessEvent
	<E extends AtomEntry>
	extends EventWithAtomEntry<UpdateSuccessHandler, E> {
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(UpdateSuccessHandler handler) {
		handler.onUpdateSuccess(this);
	}

	public static final Type<UpdateSuccessHandler> TYPE = new Type<UpdateSuccessHandler>();
	
	@Override
	public Type<UpdateSuccessHandler> getAssociatedType() { return TYPE; }

}