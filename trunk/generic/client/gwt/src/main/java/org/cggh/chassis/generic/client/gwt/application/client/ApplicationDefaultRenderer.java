/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author aliman
 *
 */
public class ApplicationDefaultRenderer implements ApplicationRenderer {

	
	
	
	final private Log log = LogFactory.getLog(this.getClass());
	final private Panel canvas = new VerticalPanel();
	final private UserDetailsWidget userDetailsWidget;
	
	
	
	
	public ApplicationDefaultRenderer() {
		log.enter("<init>");

		log.trace("create user details widget");
		this.userDetailsWidget = new UserDetailsWidget();		
		this.canvas.add(this.userDetailsWidget);
		this.userDetailsWidget.refreshUserDetails();

		log.leave();
	}
	
	
	
	

	
	
	
	public Panel getCanvas() {
		return this.canvas;
	}
	
	
	
	
}
