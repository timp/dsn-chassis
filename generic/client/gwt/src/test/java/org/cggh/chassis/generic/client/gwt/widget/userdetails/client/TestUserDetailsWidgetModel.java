/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.client.gwt.configuration.client.TestConfigurationSetUp;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetModel;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetModelListener;
import org.junit.Test;

/**
 * @author raok
 *
 */
public class TestUserDetailsWidgetModel {

	
	
	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestUserDetailsWidgetModel.class);
	} 
	
	private ChassisRole testRole1;
	private ChassisRole testRole2;
	
	@Test
	public void testConstructor() {
		
		//setup ConfigurationBean
		TestConfigurationSetUp.createTestConfiguration();
		testRole1 = ConfigurationBean.getChassisRoleCurator();
		testRole2 = ConfigurationBean.getChassisRoleSubmitter();
		
		UserDetailsWidgetModel model = new UserDetailsWidgetModel();
		
		assertNotNull(model);
		
		//check initial state
		assertNull(model.getUserName());
		assertNull(model.getCurrentRole());
		assertNull(model.getRoles());
		assertEquals(UserDetailsWidgetModel.STATUS_INITIAL, model.getStatus());		
		
	}
	
	
	
	@Test
	public void testStatusConstants() {
		
		assertEquals(new Integer(0), UserDetailsWidgetModel.STATUS_INITIAL);
		assertEquals(new Integer(1), UserDetailsWidgetModel.STATUS_LOADING);
		assertEquals(new Integer(2), UserDetailsWidgetModel.STATUS_FOUND);
		assertEquals(new Integer(3), UserDetailsWidgetModel.STATUS_ERROR);
		
	}



	@Test
	public void testSetUserName() {

		UserDetailsWidgetModel model = new UserDetailsWidgetModel();

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
				
		// method under test
		model.setCurrentRole(testRole1);
		
		// test outcome
		assertEquals(testRole1, model.getCurrentRole());
		
	}
		
	
	@Test
	public void testCurrentRoleChanged() {
		
		UserDetailsWidgetModel model = new UserDetailsWidgetModel();
		
		// instantiate a mock model listener 
		UserDetailsWidgetModelListener listener = createMock(UserDetailsWidgetModelListener.class);
		
		// set up expectations
		listener.onCurrentRoleChanged(null, testRole1);
		listener.onCurrentRoleChanged(testRole1, testRole2);
		replay(listener);
		
		// register with model
		model.addListener(listener);

		// call method under test
		model.setCurrentRole(testRole1);
		model.setCurrentRole(testRole2);
		
		// verify expectations
		verify(listener);
		
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
		Set<ChassisRole> roles = new HashSet<ChassisRole>();
		roles.add(testRole1);
		roles.add(testRole2);
		
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
		Set<ChassisRole> roles = new HashSet<ChassisRole>();
		roles.add(testRole1);
		roles.add(testRole2);
		
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
