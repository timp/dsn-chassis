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

import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
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

	private String testUserChassisRolesPrefix = "ROLE_";
	private String testUserDetailsServiceEndpointURL = "http://foo.com/users";
	private UserDetailsWidgetModel testModel;
	private UserDetailsWidgetController testController;
	private UserDetailsWidget mockOwner; 
		
	@Before
	public void setUp() {

		//Set up ConfigurationBean with test values
		ConfigurationBean.useUnitTestConfiguration = true;
		ConfigurationBean.testUserChassisRolesPrefix = testUserChassisRolesPrefix;
		ConfigurationBean.testUserDetailsServiceEndpointURL = testUserDetailsServiceEndpointURL;
		
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
		String prefixToRemove = testUserChassisRolesPrefix;
		UserDetailsTO user = new UserDetailsTO();
		user.setId("fooid");
		Set<String> roles = new HashSet<String>();
		String foo = "foo";
		roles.add(prefixToRemove + foo);
		String bar = "bar";
		roles.add(prefixToRemove + bar);
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
		assertEquals(user.getRoles().size(), user.getRoles().size());
		// Check prefix has been removed
		for (String role : user.getRoles()) {
			assertTrue(testModel.getRoles().contains(role.replace(prefixToRemove, "")));
		}
		
		assertEquals(bar, testModel.getCurrentRole());
				
	}
	
	@Test
	public void testUpdateCurrentRole() {

		// mock model in found state
		testModel.setStatus(UserDetailsWidgetModel.STATUS_FOUND);
		testModel.setUserName("user");
		
		Set<String> roles = new HashSet<String>();
		String foo = "foo";
		roles.add(foo);
		String bar = "bar";
		roles.add(bar);
		String newCurrentRole = "newCurrentRole";
		roles.add(newCurrentRole);
		
		testModel.setRoles(roles);
		testModel.setCurrentRole(foo);

		// instantiate class under test
		UserDetailsWidgetController controller = new UserDetailsWidgetController(testModel, mockOwner, null);

		// call method under test
		controller.updateCurrentRole(newCurrentRole);
		
		// test outcome at model
		assertEquals(newCurrentRole, testModel.getCurrentRole());
				
	}
	
	
}
