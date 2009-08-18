/**
 * 
 */
package org.cggh.chassis.generic.user.service;

import org.cggh.chassis.generic.user.authentication.AuthenticationProvider;
import org.cggh.chassis.generic.user.dao.UserDAO;
import org.cggh.chassis.generic.user.data.User;

/**
 * @author aliman
 *
 */
public interface UserService {

	public User getAuthenticatedUser();

	public void setAuthenticationProvider(AuthenticationProvider authenticationProvider);

	public void setUserDAO(UserDAO userDAO);
	
}
