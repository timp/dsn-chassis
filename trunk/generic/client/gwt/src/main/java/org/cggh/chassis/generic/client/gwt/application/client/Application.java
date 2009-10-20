/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import com.google.gwt.user.client.ui.Composite;

/**
 * @author aliman
 *
 */
public class Application extends Composite {

	private ApplicationRenderer renderer;

	public Application() {
		// TODO
		
		this.renderer = new ApplicationDefaultRenderer();
		
		this.initWidget(this.renderer.getCanvas());
		
	}
	
}
