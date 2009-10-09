/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.application.client;

import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.client.gwt.widget.application.client.perspective.PerspectiveWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsPubSubAPI;
import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsServiceAsync;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class ApplicationWidget implements PerspectiveWidgetPubSubAPI, UserDetailsPubSubAPI {

	
	final private ApplicationWidgetModel model;
	final private ApplicationWidgetController controller;
	final private ApplicationWidgetDefaultRenderer renderer;

	public ApplicationWidget(Panel canvas, GWTUserDetailsServiceAsync service) {
		
		model = new ApplicationWidgetModel();
		
		controller = new ApplicationWidgetController(model);
		//TODO sort out
		renderer = new ApplicationWidgetDefaultRenderer(canvas, service);
		
		// register renderer as listener to model
		model.addListener(renderer);
				
		//register as listener to userDetailsWidget
		renderer.userDetailsWidget.addUserDetailsWidgetListener(this);
		
		//load user details
		renderer.userDetailsWidget.refreshUserDetails();
		
	}


	public void onCurrentRoleChanged(ChassisRole role) {
		
		//get chassisRoles
		ChassisRole coordinatorRole = ConfigurationBean.getChassisRoleCoordinator();
		ChassisRole curatorRole = ConfigurationBean.getChassisRoleCurator();
		ChassisRole gatekeeperRole = ConfigurationBean.getChassisRoleGatekeeper();
		ChassisRole submitterRole = ConfigurationBean.getChassisRoleSubmitter();
		ChassisRole userRole = ConfigurationBean.getChassisRoleUser();
		controller.displaySubmitterPerspective();
		//register this widget as a listener to child widgets.
		renderer.submitterPerspectiveWidget.addPerspectiveWidgetListener(this);
		
		if (role.equals(coordinatorRole)) {
			controller.displayCoordinatorPerspective();
		} else if (role.equals(curatorRole)) {
			controller.displayCuratorPerspective();
		} else if (role.equals(gatekeeperRole)) {
			controller.displayGatekeeperPerspective();
		} else if (role.equals(submitterRole)) {
			controller.displaySubmitterPerspective();
			//register this widget as a listener to child widgets.
			renderer.submitterPerspectiveWidget.addPerspectiveWidgetListener(this);
		} else if (role.equals(userRole)) {
			controller.displayUserPerspective();
		}
	}


	public void onUserIdRefreshed(String userId) {
		renderer.onUserRefreshed(userId);
	}

}
