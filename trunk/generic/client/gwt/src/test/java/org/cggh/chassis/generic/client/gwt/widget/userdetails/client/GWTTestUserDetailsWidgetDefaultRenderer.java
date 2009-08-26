/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
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
				
		ListBox userRolesListBox = renderer.userRolesListBox;

		// check initial state
		assertEquals(0, userRolesListBox.getItemCount());
		
		// test data
		Set<String> roles = new HashSet<String>();
		String role1 = "foo";
		roles.add("foo");
		String role2 = "bar";
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
	
	public void testOnCurrentRoleChanged() {
		
		this.before();
		
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
	
	public void testOnStatusChanged() {
		
		this.before();
		
		Widget loadingUIObject = renderer.loadingUIObject; 
		Widget userDetailsUIObject = renderer.userDetailsUIObject;
		
		// check initial state
		assertTrue((loadingUIObject.getParent() != null) && loadingUIObject.isVisible());
		assertFalse((userDetailsUIObject.getParent() != null) && userDetailsUIObject.isVisible());
		
		/* Check loading state */
		// update model
		model.setStatus(UserDetailsWidgetModel.STATUS_LOADING);
		
		// check loading state
		assertTrue((loadingUIObject.getParent() != null) && loadingUIObject.isVisible());
		assertFalse((userDetailsUIObject.getParent() != null) && userDetailsUIObject.isVisible());
		
		/* Check found state */
		// update model
		model.setStatus(UserDetailsWidgetModel.STATUS_FOUND);
		
		// check found state
		assertFalse((loadingUIObject.getParent() != null) && loadingUIObject.isVisible());
		assertTrue((userDetailsUIObject.getParent() != null) && userDetailsUIObject.isVisible());
		
	}
	
}
