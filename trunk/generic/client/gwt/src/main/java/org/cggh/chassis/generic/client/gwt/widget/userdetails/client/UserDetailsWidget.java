/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetModel.CurrentRoleChangeHandler;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetModel.CurrentUserChangeHandler;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;
import org.cggh.chassis.generic.widget.client.DelegatingChassisWidget;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class UserDetailsWidget 
	extends DelegatingChassisWidget<UserDetailsWidgetModel, UserDetailsWidgetRenderer> {
	
	
	
	
	private Log log;
	private UserDetailsWidgetController controller;

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#getName()
	 */
	@Override
	protected String getName() {
		return "userDetailsWidget";
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		log.enter("init");

		log.debug("instantiate a model");
		this.model = new UserDetailsWidgetModel(this);
		
		log.debug("instantiate a controller");
		this.controller = new UserDetailsWidgetController(this.model);
		
		log.debug("instantiate a renderer");
		this.renderer = new UserDetailsWidgetRenderer();
		
		log.debug("set renderer canvas");
		this.renderer.setCanvas(this.contentBox);
		
		log.leave();
	}

	
	


	/**
	 * Refresh the currently authenticated user's details.
	 * 
	 * @return the currently authenticated user's details, deferred
	 */
	public Deferred<UserDetailsTO> refreshCurrentUserDetails() {
		log.enter("refreshCurrentUserDetails");

		// delegate to controller
		Deferred<UserDetailsTO> deferredUser = this.controller.refreshCurrentUserDetails();
		
		log.leave();
		return deferredUser;
	}

	
	
	
	/**
	 * Set the current role for the user.
	 * 
	 * @param role
	 */
	public void setCurrentRole(ChassisRole role) {
		log.enter("setCurrentRole");
		
		// delegate to controller
		this.controller.setCurrentRole(role);
		
		log.leave();
	}
	
	
	
	
	
	/**
	 * Add a handler for the model property change event.
	 * 
	 * @param h the handler to add
	 * @return a handler registration to remove the handler if required
	 */
	public HandlerRegistration addCurrentUserChangeHandler(CurrentUserChangeHandler h) {
		
		// delegate to model
		return this.model.addCurrentUserChangeHandler(h);
		
	}
	
	
	
	
	
	/**
	 * Add a handler for the model property change event.
	 * 
	 * @param h the handler to add
	 * @return a handler registration to remove the handler if required
	 */
	public HandlerRegistration addCurrentRoleChangeHandler(CurrentRoleChangeHandler h) {
		
		// delegate to model
		return this.model.addCurrentRoleChangeHandler(h);
		
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#ensureLog()
	 */
	@Override
	protected void ensureLog() {
		log = LogFactory.getLog(UserDetailsWidget.class);
	}
	
	
	
	
	
}
