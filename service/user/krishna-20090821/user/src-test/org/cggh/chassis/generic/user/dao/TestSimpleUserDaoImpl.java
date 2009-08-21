/**
 * 
 */
package org.cggh.chassis.generic.user.dao;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;

import java.util.Set;

import org.cggh.chassis.generic.user.data.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;

/**
 * @author raok
 *
 */
public class TestSimpleUserDaoImpl {
	
	private SecurityContext mockContext;
	private Authentication mockAuthentication;

	@Before
	public void setUp() throws Exception {
		mockContext = createMock(SecurityContext.class);
		mockAuthentication = createMock(Authentication.class);
		SecurityContextHolder.setContext(mockContext);
	}
	
	/**
	 * Test method for {@link org.cggh.chassis.generic.user.dao.TestSimpleUserDaoImpl#getUserById(java.lang.String)}.
	 */
	@Test
	public void testGetUserById() {
		// create some test data
		String testUserId = "foo";
		GrantedAuthority[] grantedAuthorities = {new GrantedAuthorityImpl("role1"),
												 new GrantedAuthorityImpl("role2"),
												 new GrantedAuthorityImpl("role3")}; 
		
		// set up mocks and checks
		expect(mockContext.getAuthentication()).andReturn(mockAuthentication);
		replay(mockContext);

		expect(mockAuthentication.getName()).andReturn(testUserId);
		expect(mockAuthentication.getName()).andReturn(testUserId);
		expect(mockAuthentication.getAuthorities()).andReturn(grantedAuthorities);
		replay(mockAuthentication);
		
		// create object under test
		UserDAO userDaoImpl = new SimpleUserDaoImpl();
				
		// call method under test
		User returnedUser = userDaoImpl.getUserById(testUserId);
		
		// test outcome
		assertEquals(returnedUser.getId(), testUserId);
		assertEquals(returnedUser.getName(), testUserId);
		
		Set<String> returnedRoles = returnedUser.getRoles();
		
		for(int i = 0; i < grantedAuthorities.length; ++i) {
			assertTrue(returnedRoles.contains(grantedAuthorities[i].getAuthority()));
		}
		
		// verify mocks were called as expected
		verify(mockContext);
		verify(mockAuthentication);
	}

}
