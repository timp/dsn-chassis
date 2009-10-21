/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author aliman
 *
 */
public class ChassisClientDefaultRenderer implements ChassisClientRenderer {

	
	
	
	public static final String STYLENAME_BASE = "chassis-client";
	
	
	
	final private Log log = LogFactory.getLog(this.getClass());
	final private Panel canvas = new FlowPanel();
	final private UserDetailsWidget userDetailsWidget;
	
	
	
	
	public ChassisClientDefaultRenderer() {
		log.enter("<init>");

		log.debug("set style name");
		this.canvas.addStyleName(STYLENAME_BASE);
		
		log.debug("create user details widget");
		this.userDetailsWidget = new UserDetailsWidget();		
		this.canvas.add(this.userDetailsWidget);
		this.userDetailsWidget.refreshUserDetails();

		log.leave();
	}
	
	
	
	

	
	
	
	public Panel getCanvas() {
		return this.canvas;
	}
	
	
	
	
}
