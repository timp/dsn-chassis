/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetModel.CurrentRoleChangeEvent;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetModel.CurrentRoleChangeHandler;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetModel.CurrentUserChangeEvent;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetModel.CurrentUserChangeHandler;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class UserDetailsWidget 
	extends DelegatingWidget<UserDetailsWidgetModel, UserDetailsWidgetRenderer> {
	
	
	
	
	private Log log;
	private UserDetailsWidgetController controller;

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected UserDetailsWidgetModel createModel() {
		return new UserDetailsWidgetModel(this);
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected UserDetailsWidgetRenderer createRenderer() {
		return new UserDetailsWidgetRenderer();
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		super.init(); // will instantiate model and renderer
		
		log.debug("instantiate a controller");
		this.controller = new UserDetailsWidgetController(this.model);
		
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
		
		this.setCurrentRole(role, true);
		
		log.leave();
	}
	
	
	
	
	/**
	 * @param role
	 * @param fireChangeEvent
	 */
	public void setCurrentRole(ChassisRole role, boolean fireChangeEvent) {
		
		// delegate to controller
		this.controller.setCurrentRole(role, fireChangeEvent);
		
	}






	
	
	/**
	 * Add a handler for the model property change event.
	 * 
	 * @param h the handler to add
	 * @return a handler registration to remove the handler if required
	 */
	public HandlerRegistration addCurrentUserChangeHandler(CurrentUserChangeHandler h) {
		
		return this.addHandler(h, CurrentUserChangeEvent.TYPE);

	}
	
	
	
	
	
	/**
	 * Add a handler for the model property change event.
	 * 
	 * @param h the handler to add
	 * @return a handler registration to remove the handler if required
	 */
	public HandlerRegistration addCurrentRoleChangeHandler(CurrentRoleChangeHandler h) {
		
		return this.addHandler(h, CurrentRoleChangeEvent.TYPE);
		
	}





	private void ensureLog() {
		log = LogFactory.getLog(UserDetailsWidget.class);
	}





	/**
	 * @return
	 */
	public UserDetailsTO getCurrentUser() {
		return this.model.getCurrentUser();
	}





	/**
	 * @return
	 */
	public ChassisRole getCurrentRole() {
		return this.model.getCurrentRole();
	}






}
