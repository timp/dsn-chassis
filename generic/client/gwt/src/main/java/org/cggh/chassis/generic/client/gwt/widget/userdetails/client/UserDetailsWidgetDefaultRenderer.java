/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import java.util.Set;

import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
class UserDetailsWidgetDefaultRenderer implements UserDetailsWidgetModelListener {

	private UserDetailsWidgetController controller;

	private Panel canvas;
	
	//Expose view elements for testing purposes.
	final Panel loadingPanel = new SimplePanel();
	final Panel userDetailsPanel = new HorizontalPanel();
	final Label userNameLabel = new Label();
	final Label currentRoleLabel = new Label();
	final Panel changeUserRolePanel = new HorizontalPanel();
	final ListBox userRolesListBox = new ListBox();

	UserDetailsWidgetDefaultRenderer(Panel canvas, UserDetailsWidgetController controller) {
		this.canvas = canvas;
		this.controller = controller;
		
		initCanvas();
	}

	
	private void initCanvas() {
		
		// Prepare loading panel
		Label loadingLabel = new Label("Loading...");
		loadingPanel.add(loadingLabel);
		
		// Prepare userDetailsPanel
		Label loggedInAsLabel = new Label("Logged in as: ");
		userDetailsPanel.add(loggedInAsLabel);
		userDetailsPanel.add(userNameLabel);
		Label roleLabel = new Label("| role: ");
		userDetailsPanel.add(roleLabel);
		userDetailsPanel.add(currentRoleLabel);
		
		//Add event handler to userRolesListBox
		userRolesListBox.addChangeHandler(new RoleChangeHandler());
		
		// Prepare changeUserRolePanel
		Label changeRoleLabel = new Label("| switch role: ");		
		changeUserRolePanel.add(changeRoleLabel);
		changeUserRolePanel.add(userRolesListBox);
		userDetailsPanel.add(changeUserRolePanel);		
		
		canvas.add(loadingPanel);
	}
	
	
	
	public void onCurrentRoleChanged(ChassisRole before, ChassisRole after) {
		currentRoleLabel.setText(after.roleLabel);
	}

	public void onRolesChanged(Set<ChassisRole> before, Set<ChassisRole> after) {
		
		// clear ListBox
		userRolesListBox.clear();
		
		for (ChassisRole role : after) {
			// TODO create friendly translator for roles?
			userRolesListBox.addItem(role.roleLabel, role.roleId.toString());
		}
		
		//show/hide changeUserRolePanel
		boolean visible = (after.size() > 1);
		changeUserRolePanel.setVisible(visible);
	}

	public void onStatusChanged(Integer before, Integer after) {
		
		if ( (after == UserDetailsWidgetModel.STATUS_INITIAL)
			  || (after == UserDetailsWidgetModel.STATUS_LOADING) )	
		{
			canvas.clear();
			canvas.add(loadingPanel);
		}
		else if (after == UserDetailsWidgetModel.STATUS_FOUND) 
		{
			canvas.clear();
			canvas.add(userDetailsPanel);
		}
		else
		{
			// TODO handle error case (could use extra panel or pass error to parent)
		}
	}

	public void onUserNameChanged(String before, String after) {
		userNameLabel.setText(after);
	}


	public void setController(UserDetailsWidgetController controller) {
		this.controller = controller;
	}
	
	class RoleChangeHandler implements ChangeHandler {
		public void onChange(ChangeEvent arg0) {
			
			//listBox value
			String selectedRoleId = userRolesListBox.getValue(userRolesListBox.getSelectedIndex());
			
			//get chassisRoles
			ChassisRole coordinatorRole = ConfigurationBean.getChassisRoleCoordinator();
			ChassisRole curatorRole = ConfigurationBean.getChassisRoleCurator();
			ChassisRole gatekeeperRole = ConfigurationBean.getChassisRoleGatekeeper();
			ChassisRole submitterRole = ConfigurationBean.getChassisRoleSubmitter();
			ChassisRole userRole = ConfigurationBean.getChassisRoleUser();
			
			
			if (selectedRoleId.equalsIgnoreCase(coordinatorRole.roleId.toString())) {
				controller.updateCurrentRole(coordinatorRole);
			} else if (selectedRoleId.equalsIgnoreCase(curatorRole.roleId.toString())) {
				controller.updateCurrentRole(curatorRole);
			} else if (selectedRoleId.equalsIgnoreCase(gatekeeperRole.roleId.toString())) {
				controller.updateCurrentRole(gatekeeperRole);
			} else if (selectedRoleId.equalsIgnoreCase(submitterRole.roleId.toString())) {
				controller.updateCurrentRole(submitterRole);
			} else if (selectedRoleId.equalsIgnoreCase(userRole.roleId.toString())) {
				controller.updateCurrentRole(userRole);
			}
		}
	}
	

}
