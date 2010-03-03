/**
 * 
 */
package org.cggh.chassis.generic.shared.client;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author aliman
 *
 */
public interface ChangeHandler<T> extends EventHandler {

	public void onChange(ChangeEvent<T> e);
	
}
