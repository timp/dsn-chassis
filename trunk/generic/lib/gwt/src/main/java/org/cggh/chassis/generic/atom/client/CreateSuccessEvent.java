/**
 * 
 */
package org.cggh.chassis.generic.atom.client;


/**
 * @author aliman
 *
 */
public abstract class CreateSuccessEvent
	<E extends AtomEntry>
	extends AtomEntryEvent<CreateSuccessHandler<E>, E> {
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(CreateSuccessHandler<E> handler) {
		handler.onCreateSuccess(this);
	}

}