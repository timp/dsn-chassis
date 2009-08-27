/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsServiceAsync;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class UserDetailsWidget {

	final private UserDetailsWidgetModel model;
	final private UserDetailsWidgetController controller;
	final private UserDetailsWidgetDefaultRenderer renderer;

	public UserDetailsWidget(Panel canvas, GWTUserDetailsServiceAsync userService) {

		// instantiate a model
		model = new UserDetailsWidgetModel();
		
		// instantiate a controller
		controller = new UserDetailsWidgetController(model, userService);
		
		// instantiate a renderer
		renderer = new UserDetailsWidgetDefaultRenderer(canvas, controller);

		// register renderer as listener to model
		model.addListener(renderer);

	}
	
	public UserDetailsWidget(UserDetailsWidgetDefaultRenderer customRenderer, GWTUserDetailsServiceAsync userService) {

		// instantiate a model
		model = new UserDetailsWidgetModel();
		
		// instantiate a controller
		controller = new UserDetailsWidgetController(model, userService);
		
		// set renderer
		renderer = customRenderer;
		
		// inject controller into renderer
		renderer.setController(controller);

		// register renderer as listener to model
		model.addListener(renderer);

	}
	
	
	
	public void refreshUserDetails() {
		controller.refreshUserDetails();
	}
	
	
	
}
