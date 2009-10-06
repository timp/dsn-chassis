/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsServiceAsync;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author raok
 *
 */
public class UserDetailsWidgetController {

	
	
	final private UserDetailsWidgetModel model;
	final private GWTUserDetailsServiceAsync service;
	final private UserDetailsWidget owner;
	
	
	
	UserDetailsWidgetController(UserDetailsWidgetModel model, UserDetailsWidget owner, GWTUserDetailsServiceAsync service) {
		this.model = model;
		this.service = service;
		this.owner = owner;
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


			// Filter out roles relevant to chassis
			String userChassisRolesPrefix = ConfigurationBean.getUserChassisRolesPrefix();
			Set<String> roles = new HashSet<String>();
			for ( String role : user.getRoles() ) {
				if (role.startsWith(userChassisRolesPrefix)) {
					roles.add(role.replace(userChassisRolesPrefix, ""));
				}
			}
			
			// populate roles in alphabetical order
			model.setRoles(new TreeSet<String>(roles));
			
			// TODO prevent currentRole changing if it is already set.
			
			// populate current role
			if (model.getRoles().size() > 0) {
				model.setCurrentRole(model.getRoles().iterator().next());
			}

			// TODO handle case where no roles found
			
			// set status
			model.setStatus(UserDetailsWidgetModel.STATUS_FOUND);
			
			// N.B. if we get wierd GWT errors later, maybe due to problems with GWT handling inner classes?
			
			//alert owner
			owner.onUserDetailsRefreshed(user.getId());
			
		}
		
	}


	public void updateCurrentRole(String currentRole) {
		model.setCurrentRole(currentRole);
	}

	
}
