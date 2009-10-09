/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;
import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsServiceAsync;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class UserDetailsWidget implements UserDetailsWidgetAPI {

	final private UserDetailsWidgetModel model;
	final private UserDetailsWidgetController controller;
	final private UserDetailsWidgetDefaultRenderer renderer;
	final private Set<UserDetailsPubSubAPI> listeners = new HashSet<UserDetailsPubSubAPI>();

	public UserDetailsWidget(Panel canvas, GWTUserDetailsServiceAsync userService) {
				
		// instantiate a model
		model = new UserDetailsWidgetModel();
		
		// instantiate a controller
		controller = new UserDetailsWidgetController(model, this, userService);
		
		// instantiate a renderer
		renderer = new UserDetailsWidgetDefaultRenderer(canvas, controller);

		// register renderer as listener to model
		model.addListener(renderer);

	}
	
	public UserDetailsWidget(UserDetailsWidgetDefaultRenderer customRenderer, GWTUserDetailsServiceAsync userService) {

		// instantiate a model
		model = new UserDetailsWidgetModel();
		
		// instantiate a controller
		controller = new UserDetailsWidgetController(model, this, userService);
		
		// set renderer
		renderer = customRenderer;
		
		// inject controller into renderer
		renderer.setController(controller);

		// register renderer as listener to model
		model.addListener(renderer);

	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetAPI#refreshUserDetails()
	 */
	public void refreshUserDetails() {
		controller.refreshUserDetails();
	}

	public void addUserDetailsWidgetListener(UserDetailsPubSubAPI listener) {
		listeners.add(listener);
	}

	public void onUserDetailsRefreshed(String userName) {

		for (UserDetailsPubSubAPI listener : listeners) {
			listener.onUserIdRefreshed(userName);
		}
		
	}

	public void onCurrentRoleChanged(ChassisRole currentRole) {

		for (UserDetailsPubSubAPI listener : listeners) {
			listener.onCurrentRoleChanged(currentRole);
		}
	}
	
	
	
}
