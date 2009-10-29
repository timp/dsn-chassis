/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author aliman
 *
 */
public abstract class WidgetRenderer {
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());



	protected Panel canvas;
	
	
	
	
	/**
	 * @param canvas the canvas to set
	 */
	public void setCanvas(Panel canvas) {
		this.canvas = canvas;
	}
	
	
	
	
	/**
	 * @return the canvas
	 */
	public Panel getCanvas() {
		return canvas;
	}




	
}
