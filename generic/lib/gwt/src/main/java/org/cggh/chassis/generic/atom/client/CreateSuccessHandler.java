/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author aliman
 *
 */
public interface CreateSuccessHandler
	<E extends AtomEntry>
	extends EventHandler {

	public void onCreateSuccess(CreateSuccessEvent<E> e);
}
