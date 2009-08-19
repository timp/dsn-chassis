/**
 * 
 */
package org.cggh.chassis.generic.user.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cggh.chassis.generic.user.authentication.AuthenticationProvider;
import org.cggh.chassis.generic.user.dao.UserDAO;
import org.cggh.chassis.generic.user.data.User;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * @author aliman
 *
 */
public class TestUserServiceImpl {

	
	
	@Test
	public void testGetAuthenticatedUser() {
		
		// create some test data
		String testUserId = "foo";
		User testUser = new User();
		testUser.setId(testUserId);
		testUser.setName("Foo Bar");
		Set<String> roles = new HashSet<String>();
		roles.add("curator");
		roles.add("submitter");
		testUser.setRoles(roles);
		
		// mock authentication provider
		AuthenticationProvider mockAuthenticationProvider = createMock(AuthenticationProvider.class);
		expect(mockAuthenticationProvider.getAuthenticatedUserId()).andReturn(testUserId);
		replay(mockAuthenticationProvider);
		
		// mock user dao
		UserDAO mockUserDAO = createMock(UserDAO.class);
		expect(mockUserDAO.getUserById(testUserId)).andReturn(testUser);
		replay(mockUserDAO);
		
		// create object under test
		UserServiceImpl userService = new UserServiceImpl();
		
		// inject mock dependencies
		userService.setAuthenticationProvider(mockAuthenticationProvider);
		userService.setUserDAO(mockUserDAO);
		
		// call method under test
		User returnedUser = userService.getAuthenticatedUser();
		
		// test outcome
		assertEquals(testUser, returnedUser);
		assertEquals(testUser.getId(), returnedUser.getId());
		assertEquals(testUser.getName(), returnedUser.getName());
		assertEquals(testUser.getRoles(), returnedUser.getRoles());
		
		// verify mocks were called as expected
		verify(mockAuthenticationProvider);
		verify(mockUserDAO);
		
	}
	
	
	
	@Test
	public void testGetAuthenticatedUser_nullUser() {

		// create some test data
		String testUserId = "foo";
		
		// mock authentication provider
		AuthenticationProvider mockAuthenticationProvider = createMock(AuthenticationProvider.class);
		expect(mockAuthenticationProvider.getAuthenticatedUserId()).andReturn(testUserId);
		replay(mockAuthenticationProvider);
		
		// mock user dao
		UserDAO mockUserDAO = createMock(UserDAO.class);
		expect(mockUserDAO.getUserById(testUserId)).andReturn(null);
		replay(mockUserDAO);
		
		// create object under test
		UserServiceImpl userService = new UserServiceImpl();
		
		// inject mock dependencies
		userService.setAuthenticationProvider(mockAuthenticationProvider);
		userService.setUserDAO(mockUserDAO);
		
		// call method under test
		User returnedUser = userService.getAuthenticatedUser();
		
		// test outcome
		assertNull(returnedUser);
		
		// verify mocks were called as expected
		verify(mockAuthenticationProvider);
		verify(mockUserDAO);	
		
	}
	
	
	
	@Test
	public void testGetAuthenticatedUser_nullUserId() {

		// mock authentication provider
		AuthenticationProvider mockAuthenticationProvider = createMock(AuthenticationProvider.class);
		expect(mockAuthenticationProvider.getAuthenticatedUserId()).andReturn(null);
		replay(mockAuthenticationProvider);
		
		// mock user dao
		UserDAO mockUserDAO = createMock(UserDAO.class);
		// expect no calls, because user id is null
		replay(mockUserDAO);
		
		// create object under test
		UserServiceImpl userService = new UserServiceImpl();
		
		// inject mock dependencies
		userService.setAuthenticationProvider(mockAuthenticationProvider);
		userService.setUserDAO(mockUserDAO);
		
		// call method under test
		User returnedUser = userService.getAuthenticatedUser();
		
		// test outcome
		assertNull(returnedUser);
		
		// verify mocks were called as expected
		verify(mockAuthenticationProvider);
		verify(mockUserDAO);	
		
	}
	
	
}
