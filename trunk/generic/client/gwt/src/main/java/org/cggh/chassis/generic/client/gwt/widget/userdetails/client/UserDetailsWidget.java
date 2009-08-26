/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class UserDetailsWidget {

	private UserDetailsWidgetModel model;
	private UserDetailsWidgetController controller;
	private UserDetailsWidgetDefaultRenderer renderer;

	public UserDetailsWidget(Panel canvas) {

		// instantiate a model
		model = new UserDetailsWidgetModel();
		
		// instantiate a controller
		controller = new UserDetailsWidgetController(model, null);
		
		// instantiate a renderer
		renderer = new UserDetailsWidgetDefaultRenderer(canvas, controller);

		// register renderer as listener to model
		model.addListener(renderer);

	}
	
	public UserDetailsWidget(UserDetailsWidgetDefaultRenderer customRenderer) {

		// instantiate a model
		model = new UserDetailsWidgetModel();
		
		// instantiate a controller
		controller = new UserDetailsWidgetController(model, null);
		
		// inject controller into renderer
		renderer.setController(controller);

		// register renderer as listener to model
		model.addListener(renderer);

	}
	
	
	
	public void refreshUserDetails() {
		controller.refreshUserDetails();
	}
	
	
	
}
