/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;
import org.cggh.chassis.generic.client.gwt.configuration.client.TestConfigurationSetUp;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetDefaultRenderer.RoleChangeHandler;
import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsServiceAsync;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
public class GWTTestUserDetailsWidgetDefaultRenderer extends GWTTestCase {

	private UserDetailsWidgetModel model;
	private MockUserDetailsWidgetController mockController;
	private UserDetailsWidgetDefaultRenderer renderer;
	
	//test data
	private ChassisRole testRole1 = TestConfigurationSetUp.testChassisRoleCoordinator;
	private ChassisRole testRole2 = TestConfigurationSetUp.testChassisRoleSubmitter;

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.client.gwt.widget.userdetails.UserDetailsWidget";
	}

	
	
	/**
	 * Work around GWTTestCase making setUp final.
	 */
	@Override
	protected void gwtSetUp() {
		
		//setup ConfigurationBean
		TestConfigurationSetUp.createTestConfiguration();

		// instantiate a model
		model = new UserDetailsWidgetModel();
		
		// instantiate a controller
		mockController = new MockUserDetailsWidgetController(model, null, null);
		
		// instantiate a renderer
		renderer = new UserDetailsWidgetDefaultRenderer(new SimplePanel(), mockController);

		// register renderer as listener to model
		model.addListener(renderer);

	}

	
	
	public void testConstructor() {
		
		assertNotNull(renderer);
		
	}
	
	
	
	public void testOnUserNameChanged() {
		
		//Objects under test
		Label userNameLabel = renderer.userNameLabel;
		
		// check initial state
		assertEquals("", userNameLabel.getText());
		
		// test data
		String username = "foo";
		
		// update model
		model.setUserName(username);

		// check new state of label
		assertEquals(username, userNameLabel.getText());
		
	}
	
	public void testOnRolesChanged() {
		
		//Objects under test
		ListBox userRolesListBox = renderer.userRolesListBox;

		// check initial state
		assertEquals(0, userRolesListBox.getItemCount());
		
		// test data
		Set<ChassisRole> roles = new HashSet<ChassisRole>();
		roles.add(testRole1);
		roles.add(testRole2);
				
		// update model
		model.setRoles(roles);
		
		// check new state of ListBox
		assertEquals(roles.size(), userRolesListBox.getItemCount());

		for (ChassisRole role : roles) {
			boolean isRoleIdSet = false;
			boolean isRoleLabelSet = false;
			
			//Check a value exists for each role in the ListBox
			for (int i = 0; i < userRolesListBox.getItemCount(); ++i) {
				if (userRolesListBox.getValue(i).equalsIgnoreCase(role.roleId.toString())) {
					isRoleIdSet = true;
				}
				
				if (userRolesListBox.getItemText(i).equalsIgnoreCase(role.roleLabel)) {
					isRoleLabelSet = true;
				}
			}
			assertTrue(isRoleIdSet);
			assertTrue(isRoleLabelSet);
		}
	}
	
	public void testOnRolesChanged_singleRoleVsMultipleRoles() {
		
		//Objects under test
		Panel changeUserRolePanel = renderer.changeUserRolePanel;
		
		// test data
		Set<ChassisRole> multiRoles = new HashSet<ChassisRole>();
		multiRoles.add(testRole1);
		multiRoles.add(testRole2);

		Set<ChassisRole> singleRole = new HashSet<ChassisRole>();
		singleRole.add(testRole2);
				
		// update model
		model.setRoles(multiRoles);
		
		// check changeUserRolePanel is shown
		assertTrue((changeUserRolePanel.getParent() != null) && changeUserRolePanel.isVisible());
		
		// update model
		model.setRoles(singleRole);
		
		// check changeUserRolePanel is not shown
		assertTrue((changeUserRolePanel.getParent() == null) || !changeUserRolePanel.isVisible());
		
	}
	
	public void testOnCurrentRoleChanged() {
		

		//Objects under test
		Label currentRoleLabel = renderer.currentRoleLabel;
		
		// check initial state
		assertEquals("", currentRoleLabel.getText());
				
		// update model
		model.setCurrentRole(testRole2);

		// check new state of label
		assertEquals(testRole2.roleLabel, currentRoleLabel.getText());
		
	}
	
	public void testOnCurrentRoleChanged_UI() {
		
		
		//Objects under test
		Label currentRoleLabel = renderer.currentRoleLabel;
		
		// check initial state
		assertEquals("", currentRoleLabel.getText());
		
		// test data
		Set<ChassisRole> roles = new HashSet<ChassisRole>();
		roles.add(testRole1);
		roles.add(testRole2);
		ChassisRole differentRole = testRole2;
		
		// update model
		model.setRoles(roles);
		
		// instantiate class under test
		RoleChangeHandler eventHandler = renderer.new RoleChangeHandler();
		ListBox userRolesListBox = renderer.userRolesListBox;
		
		// Get index of role to change to
		int differentRoleIndex = 0;
		for ( ; differentRoleIndex < userRolesListBox.getItemCount(); ++differentRoleIndex) {
			if (userRolesListBox.getValue(differentRoleIndex).equalsIgnoreCase(differentRole.roleId.toString())) {
				break;
			}
		}
		
		// Simulate change and fire role change event
		// TODO test correct eventhandler has been added to correct UIObject  
		userRolesListBox.setSelectedIndex(differentRoleIndex);
		eventHandler.onChange(null);
		
		// check new state of
		assertEquals(differentRole, mockController.currentRole);		
	}
	
	public void testOnStatusChanged() {
		

		//Objects under test
		Panel loadingUIObject = renderer.loadingPanel;
		Panel userDetailsUIObject = renderer.userDetailsPanel;
		
		// check initial state
		assertTrue((loadingUIObject.getParent() != null) && loadingUIObject.isVisible());
		assertTrue((userDetailsUIObject.getParent() == null) || !userDetailsUIObject.isVisible());
		
		/* Check loading state */
		// update model
		model.setStatus(UserDetailsWidgetModel.STATUS_LOADING);
		
		// check loading state
		assertTrue((loadingUIObject.getParent() != null) && loadingUIObject.isVisible());
		assertTrue((userDetailsUIObject.getParent() == null) || !userDetailsUIObject.isVisible());
		
		/* Check found state */
		// update model
		model.setStatus(UserDetailsWidgetModel.STATUS_FOUND);
		
		// check found state
		assertTrue((loadingUIObject.getParent() == null) || !loadingUIObject.isVisible());
		assertTrue((userDetailsUIObject.getParent() != null) && userDetailsUIObject.isVisible());
		
	}

	private class MockUserDetailsWidgetController extends UserDetailsWidgetController {

		public ChassisRole currentRole;

		MockUserDetailsWidgetController(UserDetailsWidgetModel model, UserDetailsWidget owner, GWTUserDetailsServiceAsync service) {
			super(model, owner, service);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void updateCurrentRole(ChassisRole currentRole) {
			this.currentRole = currentRole;
		}
	}
	
}
