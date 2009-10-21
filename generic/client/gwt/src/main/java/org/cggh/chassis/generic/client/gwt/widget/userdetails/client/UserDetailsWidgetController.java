/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsServiceAsync;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;

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

			// fire events
			owner.fireOnUserDetailsRefreshed(user.getId());

			// Filter out roles relevant to chassis
			String userChassisRolesPrefix = ConfigurationBean.getUserChassisRolesPrefix();
			Set<ChassisRole> roles = new HashSet<ChassisRole>();
			for ( String role : user.getRoles() ) {
				if (role.startsWith(userChassisRolesPrefix)) {
					
					String permissionSuffix = role.replace(userChassisRolesPrefix, "");
					
					for (ChassisRole r : ConfigurationBean.getChassisRoles()) {
						if (permissionSuffix.equalsIgnoreCase(r.permissionSuffix)) {
							roles.add(r);
						}
					}
					
//					//get chassisRoles
//					ChassisRole coordinatorRole = ConfigurationBean.getChassisRoleCoordinator();
//					ChassisRole curatorRole = ConfigurationBean.getChassisRoleCurator();
//					ChassisRole gatekeeperRole = ConfigurationBean.getChassisRoleGatekeeper();
//					ChassisRole submitterRole = ConfigurationBean.getChassisRoleSubmitter();
////					ChassisRole userRole = ConfigurationBean.getChassisRoleUser();
//					
//					if (permissionSuffix.equalsIgnoreCase(coordinatorRole.permissionSuffix)) {
//						roles.add(coordinatorRole);
//					} else if (permissionSuffix.equalsIgnoreCase(curatorRole.permissionSuffix)) {
//						roles.add(curatorRole);
//					} else if (permissionSuffix.equalsIgnoreCase(gatekeeperRole.permissionSuffix)) {
//						roles.add(gatekeeperRole);
//					} else if (permissionSuffix.equalsIgnoreCase(submitterRole.permissionSuffix)) {
//						roles.add(submitterRole);
//					} 
					
					// disable this, because we're not interested in the "user" role
//					else if (permissionSuffix.equalsIgnoreCase(userRole.permissionSuffix)) {
//						roles.add(userRole);
//					}
					
				}
			}
			
			// populate roles, ordered by roleId
			model.setRoles(new TreeSet<ChassisRole>(roles));
			
			// TODO prevent currentRole changing if it is already set.
			
			// populate current role
			if (model.getRoles().size() > 0) {
				
				ChassisRole r = model.getRoles().iterator().next();
				owner.fireOnCurrentRoleChanged(r);
				model.setCurrentRole(r);
				
			}

			// TODO handle case where no roles found
			
			// set status
			model.setStatus(UserDetailsWidgetModel.STATUS_FOUND);
			
			// N.B. if we get wierd GWT errors later, maybe due to problems with GWT handling inner classes?
			
			
		}
		
	}


	public void updateCurrentRole(ChassisRole currentRole) {
		model.setCurrentRole(currentRole);
		
		//alert owner
		owner.fireOnCurrentRoleChanged(currentRole);
	}

	
}
