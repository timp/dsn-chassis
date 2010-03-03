/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import java.util.Set;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsService;
import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsServiceAsync;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * @author aliman
 *
 */
public class UserDetailsWidgetController {
	
	
	
	
	private Log log = LogFactory.getLog(UserDetailsWidgetController.class);
	private UserDetailsWidgetModel model;
	private GWTUserDetailsServiceAsync userService;
	
	
	

	/**
	 * @param model
	 */
	public UserDetailsWidgetController(UserDetailsWidgetModel model) {
		log.enter("<constructor>");
		
		this.model = model;
		
		log.debug("instantiate service");
		this.userService = GWT.create(GWTUserDetailsService.class);
		
		log.debug("set service URL");
		ServiceDefTarget target = (ServiceDefTarget) userService;
		target.setServiceEntryPoint(Configuration.getUserDetailsServiceEndpointUrl());

		log.leave();
	}




	/**
	 * @return
	 */
	public Deferred<UserDetailsTO> refreshCurrentUserDetails() {
		log.enter("refreshCurrentUserDetails");
		
		Deferred<UserDetailsTO> d = new Deferred<UserDetailsTO>();
		
		this.refreshCurrentUserDetails(new RefreshUserDetailsCallback(d));
		
		log.leave();
		return d;
	}
	
	
	
	

	/**
	 * @param refreshUserDetailsCallback
	 */
	private void refreshCurrentUserDetails(RefreshUserDetailsCallback callback) {
		log.enter("refreshCurrentUserDetails");
		
		this.model.setStatus(AsyncWidgetModel.STATUS_ASYNC_REQUEST_PENDING);
		
		this.userService.getAuthenticatedUserDetails(callback);
		
		log.leave();
		
	}





	private class RefreshUserDetailsCallback implements AsyncCallback<UserDetailsTO> {

		private Deferred<UserDetailsTO> deferredUser;
		private Log log = LogFactory.getLog(RefreshUserDetailsCallback.class);
		
		public RefreshUserDetailsCallback(Deferred<UserDetailsTO> d) {
			this.deferredUser = d;
		}

		public void onFailure(Throwable ex) {
			log.enter("onFailure");
			
			// TODO implement this method
			
			log.error("error refreshing user details", ex);
			
			this.deferredUser.errback(ex);
			
			log.leave();
		}

		public void onSuccess(UserDetailsTO user) {
			log.enter("onSuccess");

			log.debug("set static current user");
			ChassisUser.setCurrentUser(user);
			
			log.debug("set current user on model");
			model.setCurrentUser(user, true);
			
			log.debug("set current role using default");
			Set<ChassisRole> roles = ChassisRole.getRoles(user);
			if (roles.size() > 0) {
				ChassisRole defaultRole = roles.iterator().next();
				model.setCurrentRole(defaultRole, true);
			}
			
			log.debug("set model status to ready");
			model.setStatus(AsyncWidgetModel.STATUS_READY);
			
			log.debug("callback with user, to signal operation complete");
			this.deferredUser.callback(user);

			log.leave();
		}
		
	}

	

	
	/**
	 * @param role
	 * @param fireChangeEvent 
	 */
	public void setCurrentRole(ChassisRole role, boolean fireChangeEvent) {
		log.enter("setCurrentRole");
		
		// TODO review this, should throw exception if user does not have that role?
		if (ChassisRole.getRoles(this.model.getCurrentUser()).contains(role)) {
			this.model.setCurrentRole(role, fireChangeEvent);
		}
		
		log.leave();
		
	}

	
	
	
}
