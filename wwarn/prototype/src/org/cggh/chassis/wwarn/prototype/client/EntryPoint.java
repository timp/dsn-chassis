/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client;

import org.cggh.chassis.wwarn.prototype.client.app.Application;

/**
 * @author aliman
 *
 */
public class EntryPoint implements com.google.gwt.core.client.EntryPoint {

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {

		new Application();

	}

}
