/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.NewUserDetailsWidget;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidget;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author aliman
 *
 */
public interface ChassisClientRenderer extends ChassisClientModelListener {

	public Panel getCanvas();

//	public UserDetailsWidget getUserDetailsWidget();
	public NewUserDetailsWidget getUserDetailsWidget();

	/**
	 * 
	 */
	public void refreshPerspectives();
	
}
