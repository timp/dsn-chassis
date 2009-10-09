/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;
import org.cggh.chassis.generic.client.gwt.configuration.client.TestConfigurationSetUp;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetController.RefreshUserDetailsCallback;
import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsServiceAsync;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * @author raok
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({UserDetailsWidget.class})
public class TestUserDetailsWidgetController {

	
	
	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestUserDetailsWidgetController.class);
	}

	private String testUserChassisRolesPrefix = TestConfigurationSetUp.testUserChassisRolesPrefix;
	private ChassisRole testRole1 = TestConfigurationSetUp.testChassisRoleCoordinator;
	private ChassisRole testRole2 = TestConfigurationSetUp.testChassisRoleSubmitter;
	private UserDetailsWidgetModel testModel;
	private UserDetailsWidgetController testController;
	private UserDetailsWidget mockOwner; 
		
	@Before
	public void setUp() {
		
		//setup ConfigurationBean
		TestConfigurationSetUp.createTestConfiguration();
		
		//Create mock owner
		mockOwner = PowerMock.createMock(UserDetailsWidget.class);
		
		//instantiate test controller and model
		testModel = new UserDetailsWidgetModel();
		testController = new UserDetailsWidgetController(testModel, mockOwner, null);
		
	}
	
	@Test
	public void testConstructor() {
				
		assertNotNull(testController);
		
	}

	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testRefreshUserDetails_AsyncCallback() {

		// mock async callback
		AsyncCallback<UserDetailsTO> callback = createMock(AsyncCallback.class);
		replay(callback);
		
		// mock user details service
		GWTUserDetailsServiceAsync service = createMock(GWTUserDetailsServiceAsync.class);
		service.getAuthenticatedUserDetails(callback);
		replay(service);

		// instantiate class under test
		UserDetailsWidgetController controller = new UserDetailsWidgetController(testModel, mockOwner, service);

		// test initial state of model
		assertEquals(UserDetailsWidgetModel.STATUS_INITIAL, testModel.getStatus());
		
		// call method under test
		controller.refreshUserDetails(callback);
		
		// test outcome at the model
		assertEquals(UserDetailsWidgetModel.STATUS_LOADING, testModel.getStatus());
		
		// verify mocks
		verify(callback);
		verify(service);
	}
	
	
	@Test
	public void testRefreshUserDetailsCallback_onSuccess() {
						
		// mock loading state
		testModel.setStatus(UserDetailsWidgetModel.STATUS_LOADING);
		
		// test data
		UserDetailsTO user = new UserDetailsTO();
		user.setId("fooid");
		Set<String> roles = new HashSet<String>();
		roles.add(testUserChassisRolesPrefix + testRole1.permissionSuffix);
		roles.add(testUserChassisRolesPrefix + testRole2.permissionSuffix);
		user.setRoles(roles);
						
		// instantiate class under test
		UserDetailsWidgetController controller = new UserDetailsWidgetController(testModel, mockOwner, null);
		RefreshUserDetailsCallback callback = controller.new RefreshUserDetailsCallback();
		
		//set up expectations on owner
		mockOwner.onUserDetailsRefreshed(user.getId());
		PowerMock.replay(mockOwner);
		
		
		// call method under test
		callback.onSuccess(user);
		
		/* test outcome at model */
		assertEquals(UserDetailsWidgetModel.STATUS_FOUND, testModel.getStatus());
		assertEquals(user.getId(), testModel.getUserName());
		assertTrue(testModel.getRoles().contains(testRole1));
		assertTrue(testModel.getRoles().contains(testRole2));
		assertNotNull(testModel.getCurrentRole());
				
	}
	
	@Test
	public void testUpdateCurrentRole() {

		// mock model in found state
		testModel.setStatus(UserDetailsWidgetModel.STATUS_FOUND);
		testModel.setUserName("user");
		
		Set<ChassisRole> roles = new HashSet<ChassisRole>();
		roles.add(testRole1);
		roles.add(testRole2);
		ChassisRole newCurrentRole = testRole2;
		
		//set up test
		testModel.setRoles(roles);
		testModel.setCurrentRole(testRole1);

		// instantiate class under test
		UserDetailsWidgetController controller = new UserDetailsWidgetController(testModel, mockOwner, null);

		// call method under test
		controller.updateCurrentRole(newCurrentRole);
		
		// test outcome at model
		assertEquals(newCurrentRole, testModel.getCurrentRole());
				
	}
	
	
}
