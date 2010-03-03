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
public class RetrieveSuccessEvent
	<E extends AtomEntry>
	extends EventWithAtomEntry<RetrieveSuccessHandler, E> {
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(RetrieveSuccessHandler handler) {
		handler.onRetrieveSuccess(this);
	}

	public static final Type<RetrieveSuccessHandler> TYPE = new Type<RetrieveSuccessHandler>();
	
	@Override
	public Type<RetrieveSuccessHandler> getAssociatedType() { return TYPE; }

}