package org.cggh.chassis.generic.user.authentication;


import org.cggh.chassis.generic.user.authentication.AuthenticationProvider;
import org.cggh.chassis.generic.user.authentication.SimpleAuthenticationProviderImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * @author raok
 *
 */
public class TestSimpleAuthenticationProviderImpl {
	
	private SecurityContext mockContext;
	private Authentication mockAuthentication;

	@Before
	public void setUp() throws Exception {
		mockContext = createMock(SecurityContext.class);
		mockAuthentication = createMock(Authentication.class);
		SecurityContextHolder.setContext(mockContext);
	}
		
	@Test
	public void testGetAuthenticatedUserId() {
		// create some test data
		String testUserId = "foo";
		
		// set up mocks and checks
		expect(mockContext.getAuthentication()).andReturn(mockAuthentication);
		replay(mockContext);

		expect(mockAuthentication.getName()).andReturn(testUserId);
		replay(mockAuthentication);
		
		// create object under test
		AuthenticationProvider authenticationProvider = new SimpleAuthenticationProviderImpl();
				
		// call method under test
		String returnedUserId = authenticationProvider.getAuthenticatedUserId();
		
		// test outcome
		assertEquals(returnedUserId, testUserId);
		
		// verify mocks were called as expected
		verify(mockContext);
		verify(mockAuthentication);
	}	
}
