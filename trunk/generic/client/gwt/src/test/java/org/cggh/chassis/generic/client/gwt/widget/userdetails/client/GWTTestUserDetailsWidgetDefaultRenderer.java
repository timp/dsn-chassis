/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetDefaultRenderer.RoleChangeHandler;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author raok
 *
 */
public class GWTTestUserDetailsWidgetDefaultRenderer extends GWTTestCase {

	private UserDetailsWidgetModel model;
	private UserDetailsWidgetController controller;
	private UserDetailsWidgetDefaultRenderer renderer;

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
	public void before() {

		// instantiate a model
		model = new UserDetailsWidgetModel();
		
		// instantiate a controller
		controller = new UserDetailsWidgetController(model, null);
		
		// instantiate a renderer
		renderer = new UserDetailsWidgetDefaultRenderer(new SimplePanel(), controller);

		// register renderer as listener to model
		model.addListener(renderer);

	}

	
	
	public void testConstructor() {

		this.before();
		
		assertNotNull(renderer);
		
	}
	
	
	
	public void testOnUserNameChanged() {
		
		this.before();

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
		
		this.before();

		//Objects under test
		ListBox userRolesListBox = renderer.userRolesListBox;

		// check initial state
		assertEquals(0, userRolesListBox.getItemCount());
		
		// test data
		Set<String> roles = new HashSet<String>();
		roles.add("foo");
		roles.add("bar");
				
		// update model
		model.setRoles(roles);
		
		// check new state of ListBox
		assertEquals(roles.size(), userRolesListBox.getItemCount());

		for (String role : roles) {
			boolean isRoleListed = false;
			
			//Check a value exists for each role in the ListBox
			for (int i = 0; i < userRolesListBox.getItemCount(); ++i) {
				if (userRolesListBox.getValue(i).equalsIgnoreCase(role)) {
					isRoleListed = true;
					break;
				}
			}
			assertTrue(isRoleListed);
		}
	}
	
	public void testOnRolesChanged_singleRoleVsMultipleRoles() {
		this.before();
		
		//Objects under test
		Panel changeUserRolePanel = renderer.changeUserRolePanel;
		
		// test data
		Set<String> multiRoles = new HashSet<String>();
		multiRoles.add("foo");
		multiRoles.add("bar");

		Set<String> singleRole = new HashSet<String>();
		singleRole.add("oneRole");		
				
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
		
		this.before();

		//Objects under test
		Label currentRoleLabel = renderer.currentRoleLabel;
		
		// check initial state
		assertEquals("", currentRoleLabel.getText());
		
		// test data
		String role = "foo";
		
		// update model
		model.setCurrentRole(role);

		// check new state of label
		assertEquals(role, currentRoleLabel.getText());
		
	}
	
	public void testOnCurrentRoleChanged_UI() {
		
		this.before();
		
		//Objects under test
		Label currentRoleLabel = renderer.currentRoleLabel;
		
		// check initial state
		assertEquals("", currentRoleLabel.getText());
		
		// test data
		Set<String> roles = new HashSet<String>();
		roles.add("foo");
		roles.add("bar");
		String differentRole = "differentRole";
		roles.add(differentRole);
		
		// update model
		model.setRoles(roles);
		
		// instantiate class under test
		RoleChangeHandler eventHandler = renderer.new RoleChangeHandler();
		ListBox userRolesListBox = renderer.userRolesListBox;
		
		// Get index of role to change to
		int differentRoleIndex = 0;
		for ( ; differentRoleIndex < userRolesListBox.getItemCount(); ++differentRoleIndex) {
			if (userRolesListBox.getValue(differentRoleIndex).equalsIgnoreCase(differentRole)) {
				break;
			}
		}
		
		// Simulate change and fire role change event
		userRolesListBox.setSelectedIndex(differentRoleIndex);
		eventHandler.onChange(null);
		
		// check new state of
		assertEquals(differentRole, currentRoleLabel.getText());		
	}
	
	public void testOnStatusChanged() {
		
		this.before();

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
	
}
