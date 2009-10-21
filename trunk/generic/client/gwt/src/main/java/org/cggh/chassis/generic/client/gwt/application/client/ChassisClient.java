/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import com.google.gwt.user.client.ui.Composite;

/**
 * @author aliman
 *
 */
public class ChassisClient extends Composite {

	private ChassisClientRenderer renderer;

	public ChassisClient() {
		// TODO
		
		this.renderer = new ChassisClientDefaultRenderer();
		
		this.initWidget(this.renderer.getCanvas());
		
	}
	
}
