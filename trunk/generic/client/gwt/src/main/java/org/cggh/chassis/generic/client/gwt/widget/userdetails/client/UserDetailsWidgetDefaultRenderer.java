/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import java.util.Set;

import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
class UserDetailsWidgetDefaultRenderer implements UserDetailsWidgetModelListener {

	public static final String STYLENAME_BASE = "chassis-userDetailsWidget";
	public static final String STYLENAME_USERNAME = STYLENAME_BASE + "-userName";
	public static final String STYLENAME_CURRENTROLE = STYLENAME_BASE + "-currentRole";
	public static final String STYLENAME_ROLESWITCHER = STYLENAME_BASE + "-roleSwitcher";
	public static final String STYLENAME_USERDETAILS = STYLENAME_BASE + "-userDetails";
	public static final String STYLENAME_SWITCHEROLEPANEL = STYLENAME_BASE + "-switchRolePanel";

	private UserDetailsWidgetController controller;

	private Panel canvas;
	
	//Expose view elements for testing purposes.
	Panel loadingPanel = new FlowPanel();
	Panel userDetailsPanel = new FlowPanel();
	Label userNameLabel = new InlineLabel();
	Label currentRoleLabel = new InlineLabel();
	Panel changeUserRolePanel = new FlowPanel();
	ListBox userRolesListBox = new ListBox();

	UserDetailsWidgetDefaultRenderer(Panel canvas, UserDetailsWidgetController controller) {
		this.canvas = canvas;
		this.controller = controller;
		
		initCanvas();
	}

	UserDetailsWidgetDefaultRenderer(UserDetailsWidgetController controller) {
		this.controller = controller;
		this.canvas = new FlowPanel();
		
		initCanvas();
	}

	public Panel getCanvas() {
		return this.canvas;
	}

	
	private void initCanvas() {
		
		this.canvas.addStyleName(STYLENAME_BASE);
		this.userDetailsPanel.addStyleName(STYLENAME_USERDETAILS);
		this.userNameLabel.addStyleName(STYLENAME_USERNAME);
		this.currentRoleLabel.addStyleName(STYLENAME_CURRENTROLE);
		this.userRolesListBox.addStyleName(STYLENAME_ROLESWITCHER);
		
		
		// Prepare loading panel
		Label loadingLabel = new Label("Loading...");
		loadingPanel.add(loadingLabel);
		this.canvas.add(loadingPanel);
		
		// Prepare userDetailsPanel
		Label loggedInAsLabel = new InlineLabel("logged in as: ");
		userDetailsPanel.add(loggedInAsLabel);
		userDetailsPanel.add(userNameLabel);
		Label roleLabel = new InlineLabel(" | role: ");
		userDetailsPanel.add(roleLabel);
		userDetailsPanel.add(currentRoleLabel);
		
		//Add event handler to userRolesListBox
		userRolesListBox.addChangeHandler(new RoleChangeHandler());
		
		// Prepare changeUserRolePanel
		Label changeRoleLabel = new InlineLabel(" | switch role: ");		
		changeUserRolePanel.add(changeRoleLabel);
		changeUserRolePanel.addStyleName(STYLENAME_SWITCHEROLEPANEL);
		changeUserRolePanel.add(userRolesListBox);
		userDetailsPanel.add(changeUserRolePanel);		
		
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
			int selectedRoleId = Integer.parseInt(userRolesListBox.getValue(userRolesListBox.getSelectedIndex()));
			
			for (ChassisRole role : ConfigurationBean.getChassisRoles()) {
				if (selectedRoleId == role.roleId) {
					controller.updateCurrentRole(role);
				}
			}
			
//			//get chassisRoles
//			ChassisRole administratorRole = ConfigurationBean.getChassisRoleAdministrator();
//			ChassisRole coordinatorRole = ConfigurationBean.getChassisRoleCoordinator();
//			ChassisRole curatorRole = ConfigurationBean.getChassisRoleCurator();
//			ChassisRole gatekeeperRole = ConfigurationBean.getChassisRoleGatekeeper();
//			ChassisRole submitterRole = ConfigurationBean.getChassisRoleSubmitter();
//			ChassisRole userRole = ConfigurationBean.getChassisRoleUser();
//			
//			
//			// TODO why are we serialising integers to strings and then comparing?
//			
//			if (selectedRoleId.equalsIgnoreCase(coordinatorRole.roleId.toString())) {
//				controller.updateCurrentRole(coordinatorRole);
//			} else if (selectedRoleId.equalsIgnoreCase(curatorRole.roleId.toString())) {
//				controller.updateCurrentRole(curatorRole);
//			} else if (selectedRoleId.equalsIgnoreCase(gatekeeperRole.roleId.toString())) {
//				controller.updateCurrentRole(gatekeeperRole);
//			} else if (selectedRoleId.equalsIgnoreCase(submitterRole.roleId.toString())) {
//				controller.updateCurrentRole(submitterRole);
//			} else if (selectedRoleId.equalsIgnoreCase(userRole.roleId.toString())) {
//				controller.updateCurrentRole(userRole);
//			}
		}
	}
	

}
