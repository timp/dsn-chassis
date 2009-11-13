/**
 * 
 */
package org.cggh.chassis.generic.atom.client;


/**
 * @author aliman
 *
 */
public abstract class UpdateSuccessEvent
	<E extends AtomEntry>
	extends AtomEntryEvent<UpdateSuccessHandler<E>, E> {
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(UpdateSuccessHandler<E> handler) {
		handler.onUpdateSuccess(this);
	}

}