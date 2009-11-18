/**
 * 
 */
package org.cggh.chassis.generic.atom.client.ui;

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
