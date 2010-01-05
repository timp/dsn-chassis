/**
 * 
 */
package org.cggh.chassis.generic.atomui.client;

import org.cggh.chassis.generic.atom.client.AtomEntry;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author aliman
 *
 */
public interface UpdateSuccessHandler
	<E extends AtomEntry>
	extends EventHandler {

	public void onUpdateSuccess(UpdateSuccessEvent<E> e);
	
}
