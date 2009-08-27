/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import java.util.TreeSet;

import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsServiceAsync;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author raok
 *
 */
public class UserDetailsWidgetController {

	
	
	private UserDetailsWidgetModel model;
	private GWTUserDetailsServiceAsync service;
	
	
	
	UserDetailsWidgetController(UserDetailsWidgetModel model, GWTUserDetailsServiceAsync service) {
		this.model = model;
		this.service = service;
	}

	
	
	/**
	 * Parameterised method to allow injection of callback for testing purposes.
	 * @param callback
	 */
	void refreshUserDetails(AsyncCallback<UserDetailsTO> callback) {
		
		this.model.setStatus(UserDetailsWidgetModel.STATUS_LOADING);
		
		this.service.getAuthenticatedUserDetails(callback);
		
	}
	
	
	
	/**
	 * Refresh the currently authenticated user.
	 */
	void refreshUserDetails() {
		
		refreshUserDetails(new RefreshUserDetailsCallback());
		
	}

	
	
	class RefreshUserDetailsCallback implements AsyncCallback<UserDetailsTO> {

		public void onFailure(Throwable ex) {
			
			// TODO implement this method
			
		}

		public void onSuccess(UserDetailsTO user) {

			// TODO handle case where user is null
			
			// populate username
			model.setUserName(user.getId());
			
			// populate roles
			model.setRoles(new TreeSet<String>(user.getRoles()));
			
			// TODO prevent currentRole changing if it is already set.
			
			// populate current role
			if (model.getRoles().size() > 0) {
				model.setCurrentRole(model.getRoles().iterator().next());
			}

			// TODO handle case where no roles found
			
			// set status
			model.setStatus(UserDetailsWidgetModel.STATUS_FOUND);
			
			// N.B. if we get wierd GWT errors later, maybe due to problems with GWT handling inner classes?
			
		}
		
	}


	public void updateCurrentRole(String currentRole) {
		model.setCurrentRole(currentRole);
	}

	
}
