/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsService;
import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class UserDetailsWidget implements UserDetailsWidgetAPI {

	final private UserDetailsWidgetModel model;
	final private UserDetailsWidgetController controller;
	final private UserDetailsWidgetDefaultRenderer renderer;
	final private Set<UserDetailsUserIdPubSubAPI> listeners = new HashSet<UserDetailsUserIdPubSubAPI>();

	public UserDetailsWidget(Panel canvas) {
				
		// instantiate a model
		model = new UserDetailsWidgetModel();
		
		// instantiate a controller
		controller = new UserDetailsWidgetController(model, this, getUserService());
		
		// instantiate a renderer
		renderer = new UserDetailsWidgetDefaultRenderer(canvas, controller);

		// register renderer as listener to model
		model.addListener(renderer);

	}
	
	public UserDetailsWidget(UserDetailsWidgetDefaultRenderer customRenderer) {

		// instantiate a model
		model = new UserDetailsWidgetModel();
		
		// instantiate a controller
		controller = new UserDetailsWidgetController(model, this, getUserService());
		
		// set renderer
		renderer = customRenderer;
		
		// inject controller into renderer
		renderer.setController(controller);

		// register renderer as listener to model
		model.addListener(renderer);

	}
	
	
	
	private GWTUserDetailsServiceAsync getUserService() {
	
		// instantiate service
		GWTUserDetailsServiceAsync userService = GWT.create(GWTUserDetailsService.class);
		
		// set service URL
		ServiceDefTarget target = (ServiceDefTarget) userService;
		target.setServiceEntryPoint(ConfigurationBean.getUserDetailsServiceEndpointURL());
		
		return userService;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetAPI#refreshUserDetails()
	 */
	public void refreshUserDetails() {
		controller.refreshUserDetails();
	}

	public void addUserDetailsWidgetListener(UserDetailsUserIdPubSubAPI listener) {
		listeners.add(listener);
	}

	public void onUserDetailsRefreshed(String userName) {

		for (UserDetailsUserIdPubSubAPI listener : listeners) {
			listener.onUserIdRefreshed(userName);
		}
		
	}
	
	
	
}
