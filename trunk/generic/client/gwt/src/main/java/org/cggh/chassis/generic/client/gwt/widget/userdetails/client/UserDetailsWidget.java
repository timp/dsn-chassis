/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsService;
import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class UserDetailsWidget extends Composite implements UserDetailsWidgetAPI {

	
	
	private Log log = LogFactory.getLog(this.getClass());
	private UserDetailsWidgetModel model;
	private UserDetailsWidgetController controller;
	private UserDetailsWidgetDefaultRenderer renderer;
	private Set<UserDetailsPubSubAPI> listeners = new HashSet<UserDetailsPubSubAPI>();

	
	
	
	public UserDetailsWidget(Panel canvas, GWTUserDetailsServiceAsync userService) {
				
		// instantiate a model
		this.model = new UserDetailsWidgetModel();
		
		// instantiate a controller
		this.controller = new UserDetailsWidgetController(this.model, this, userService);
		
		// instantiate a renderer
		this.renderer = new UserDetailsWidgetDefaultRenderer(canvas, this.controller);

		// register renderer as listener to model
		this.model.addListener(this.renderer);
		
		this.initWidget(this.renderer.getCanvas());

	}
	
	
	
	
	public UserDetailsWidget() {
		log.enter("<constructor>");
		
		log.debug("instantiate service");
		GWTUserDetailsServiceAsync userService = GWT.create(GWTUserDetailsService.class);
		
		log.debug("set service URL");
		ServiceDefTarget target = (ServiceDefTarget) userService;
		target.setServiceEntryPoint(Configuration.getUserDetailsServiceEndpointURL());

		log.debug("instantiate a model");
		this.model = new UserDetailsWidgetModel();
		
		log.debug("instantiate a controller");
		this.controller = new UserDetailsWidgetController(this.model, this, userService);
		
		log.debug("instantiate a renderer");
		this.renderer = new UserDetailsWidgetDefaultRenderer(this.controller);

		log.debug("register renderer as listener to model");
		this.model.addListener(this.renderer);
		
		log.debug("initialise this widget");
		this.initWidget(this.renderer.getCanvas());

		log.leave();
	}
	
	
	
	
	
		
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetAPI#refreshUserDetails()
	 */
	public void refreshUserDetails() {
		controller.refreshUserDetails();
	}

	
	
	
	public void addListener(UserDetailsPubSubAPI listener) {
		listeners.add(listener);
	}

	
	
	
	public void fireOnUserDetailsRefreshed(String userName) {

		for (UserDetailsPubSubAPI listener : listeners) {
			listener.onUserIdRefreshed(userName);
		}
		
	}

	
	
	
	public void fireOnCurrentRoleChanged(ChassisRole currentRole) {

		for (UserDetailsPubSubAPI listener : listeners) {
			listener.onCurrentRoleChanged(currentRole);
		}
	}
	
	
	
	
}
