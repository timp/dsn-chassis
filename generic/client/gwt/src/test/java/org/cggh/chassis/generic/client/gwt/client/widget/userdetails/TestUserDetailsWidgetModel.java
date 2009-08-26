/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.client.widget.userdetails;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * @author raok
 *
 */
public class TestUserDetailsWidgetModel {

	
	
	@Test
	public void testConstructor() {
		
		UserDetailsWidgetModel model = new UserDetailsWidgetModel();
		
		assertNotNull(model);
		
		// TODO move tests of initial state here
	}
	
	
	
	@Test
	public void testSetUserName() {

		UserDetailsWidgetModel model = new UserDetailsWidgetModel();

		// TODO check initial state

		// test data
		String userName = "foo";
		
		// method under test
		model.setUserName(userName);
		
		// test outcome
		assertEquals(userName, model.getUserName());
		
	}
	
	
	
	@Test
	public void testUserNameChanged() {
		
		UserDetailsWidgetModel model = new UserDetailsWidgetModel();

		// test data
		String foo = "foo";
		String bar = "bar";
		
		// instantiate a mock model listener 
		UserDetailsWidgetModelListener listener = createMock(UserDetailsWidgetModelListener.class);
		
		// set up expectations
		listener.onUserNameChanged(null, foo);
		listener.onUserNameChanged(foo, bar);
		replay(listener);
		
		// register with model
		model.addListener(listener);

		// call method under test
		model.setUserName(foo);
		model.setUserName(bar);
		
		// verify expectations
		verify(listener);
		
	}
	
	
	
	
	@Test
	public void testSetCurrentRole() {

		UserDetailsWidgetModel model = new UserDetailsWidgetModel();
		
		// TODO check initial state

		// test data
		String currentRole = "foo";
		
		// method under test
		model.setCurrentRole(currentRole);
		
		// test outcome
		assertEquals(currentRole, model.getCurrentRole());
		
	}
	
	
	
	@Test
	public void testCurrentRoleChanged() {
		
		UserDetailsWidgetModel model = new UserDetailsWidgetModel();

		// test data
		String foo = "foo";
		String bar = "bar";
		
		// instantiate a mock model listener 
		UserDetailsWidgetModelListener listener = createMock(UserDetailsWidgetModelListener.class);
		
		// set up expectations
		listener.onCurrentRoleChanged(null, foo);
		listener.onCurrentRoleChanged(foo, bar);
		replay(listener);
		
		// register with model
		model.addListener(listener);

		// call method under test
		model.setCurrentRole(foo);
		model.setCurrentRole(bar);
		
		// verify expectations
		verify(listener);
		
	}
	
	
	
	@Test
	public void testStatusConstants() {
		
		assertEquals(new Integer(0), UserDetailsWidgetModel.STATUS_INITIAL);
		assertEquals(new Integer(1), UserDetailsWidgetModel.STATUS_LOADING);
		assertEquals(new Integer(2), UserDetailsWidgetModel.STATUS_FOUND);
		assertEquals(new Integer(3), UserDetailsWidgetModel.STATUS_ERROR);
		
	}
	
	
	
	@Test
	public void testSetStatus() {
		
		UserDetailsWidgetModel model = new UserDetailsWidgetModel();

		// test initial state
		assertEquals(UserDetailsWidgetModel.STATUS_INITIAL, model.getStatus());
		
		// method under test
		model.setStatus(UserDetailsWidgetModel.STATUS_FOUND);
		
		// test outcome
		assertEquals(UserDetailsWidgetModel.STATUS_FOUND, model.getStatus());
		
	}
	

	@Test
	public void testStatusChanged() {
		
		UserDetailsWidgetModel model = new UserDetailsWidgetModel();

		// instantiate a mock model listener 
		UserDetailsWidgetModelListener listener = createMock(UserDetailsWidgetModelListener.class);
		
		// set up expectations
		listener.onStatusChanged(UserDetailsWidgetModel.STATUS_INITIAL, UserDetailsWidgetModel.STATUS_LOADING);
		listener.onStatusChanged(UserDetailsWidgetModel.STATUS_LOADING, UserDetailsWidgetModel.STATUS_FOUND);
		replay(listener);
		
		// register with model
		model.addListener(listener);

		// call method under test
		model.setStatus(UserDetailsWidgetModel.STATUS_LOADING);
		model.setStatus(UserDetailsWidgetModel.STATUS_FOUND);
		
		// verify expectations
		verify(listener);
		
	}
	
	
	
	@Test
	public void testSetRoles() {

		UserDetailsWidgetModel model = new UserDetailsWidgetModel();

		// test initial state
		assertNull(model.getRoles());

		// test data
		Set<String> roles = new HashSet<String>();
		roles.add("foo");
		roles.add("bar");
		
		// method under test
		model.setRoles(roles);
		
		// test outcome
		assertEquals(roles, model.getRoles());

	}
	


	@Test
	public void testRolesChanged() {
		
		UserDetailsWidgetModel model = new UserDetailsWidgetModel();

		// instantiate a mock model listener 
		UserDetailsWidgetModelListener listener = createMock(UserDetailsWidgetModelListener.class);

		// test data
		Set<String> roles = new HashSet<String>();
		roles.add("foo");
		roles.add("bar");
		
		// set up expectations
		listener.onRolesChanged(null, roles);
		replay(listener);
		
		// register with model
		model.addListener(listener);

		// call method under test
		model.setRoles(roles);
		
		// verify expectations
		verify(listener);
		
	}
	
	
	
}
