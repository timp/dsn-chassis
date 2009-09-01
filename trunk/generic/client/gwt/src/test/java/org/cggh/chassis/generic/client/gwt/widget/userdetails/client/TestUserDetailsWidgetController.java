/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.client.gwt.client.Configuration;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetController;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetModel;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetController.RefreshUserDetailsCallback;
import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsServiceAsync;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gwt.user.client.rpc.AsyncCallback;

import static org.easymock.EasyMock.*;


/**
 * @author raok
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Configuration.class})
public class TestUserDetailsWidgetController {

	
	
	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestUserDetailsWidgetController.class);
	} 
		
		
	
	@Test
	public void testConstructor() {
		
		UserDetailsWidgetModel model = new UserDetailsWidgetModel();
		UserDetailsWidgetController controller = new UserDetailsWidgetController(model, null);
		
		assertNotNull(controller);
		
	}

	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testRefreshUserDetails_AsyncCallback() {

		UserDetailsWidgetModel model = new UserDetailsWidgetModel();

		// mock async callback
		AsyncCallback<UserDetailsTO> callback = createMock(AsyncCallback.class);
		replay(callback);
		
		// mock user details service
		GWTUserDetailsServiceAsync service = createMock(GWTUserDetailsServiceAsync.class);
		service.getAuthenticatedUserDetails(callback);
		replay(service);

		// instantiate class under test
		UserDetailsWidgetController controller = new UserDetailsWidgetController(model, service);

		// test initial state of model
		assertEquals(UserDetailsWidgetModel.STATUS_INITIAL, model.getStatus());
		
		// call method under test
		controller.refreshUserDetails(callback);
		
		// test outcome at the model
		assertEquals(UserDetailsWidgetModel.STATUS_LOADING, model.getStatus());
		
		// verify mocks
		verify(callback);
		verify(service);
	}
	
	
	@Test
	public void testRefreshUserDetailsCallback_onSuccess() {

		UserDetailsWidgetModel model = new UserDetailsWidgetModel();
						
		// mock loading state
		model.setStatus(UserDetailsWidgetModel.STATUS_LOADING);
		
		// test data
		String prefixToRemove = "Prefix_";
		UserDetailsTO user = new UserDetailsTO();
		user.setId("fooid");
		Set<String> roles = new HashSet<String>();
		String foo = "foo";
		roles.add(prefixToRemove + foo);
		String bar = "bar";
		roles.add(prefixToRemove + bar);
		user.setRoles(roles);
		
		// Mock Configuration class
		org.powermock.api.easymock.PowerMock.mockStatic(Configuration.class);
		expect(Configuration.getUserChassisRolesPrefix()).andReturn(prefixToRemove);
		org.powermock.api.easymock.PowerMock.replay(Configuration.class);
		
		// instantiate class under test
		UserDetailsWidgetController controller = new UserDetailsWidgetController(model, null);
		RefreshUserDetailsCallback callback = controller.new RefreshUserDetailsCallback();
		
		// call method under test
		callback.onSuccess(user);
		
		/* test outcome at model */
		
		assertEquals(UserDetailsWidgetModel.STATUS_FOUND, model.getStatus());
		assertEquals(user.getId(), model.getUserName());
		assertEquals(user.getRoles().size(), user.getRoles().size());
		// Check prefix has been removed
		for (String role : user.getRoles()) {
			assertTrue(model.getRoles().contains(role.replace(prefixToRemove, "")));
		}
		
		assertEquals(bar, model.getCurrentRole());
		
		
		// verify mock classes
		org.powermock.api.easymock.PowerMock.verify(Configuration.class);
		
	}
	
	@Test
	public void testUpdateCurrentRole() {

		// mock model in found state
		UserDetailsWidgetModel model = new UserDetailsWidgetModel();
		model.setStatus(UserDetailsWidgetModel.STATUS_FOUND);
		model.setUserName("user");
		
		Set<String> roles = new HashSet<String>();
		String foo = "foo";
		roles.add(foo);
		String bar = "bar";
		roles.add(bar);
		String newCurrentRole = "newCurrentRole";
		roles.add(newCurrentRole);
		
		model.setRoles(roles);
		model.setCurrentRole(foo);

		// instantiate class under test
		UserDetailsWidgetController controller = new UserDetailsWidgetController(model, null);

		// call method under test
		controller.updateCurrentRole(newCurrentRole);
		
		// test outcome at model
		assertEquals(newCurrentRole, model.getCurrentRole());
				
	}
	
	
}
