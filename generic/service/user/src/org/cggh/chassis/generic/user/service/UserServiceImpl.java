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
public class UserServiceImpl implements UserService {

	private UserDAO userDao = null;
	private AuthenticationProvider authenticationProvider = null;

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.service.user.UserService#getAuthenticatedUser()
	 */
	public User getAuthenticatedUser() {
		
		String userId = null;
		User user = null;
		
		if (this.authenticationProvider != null) {
			userId = this.authenticationProvider.getAuthenticatedUserId();
		}
		
		if (userId != null && userDao != null) {
			user = this.userDao.getUserById(userId);
		}
		
		return user;
		
	}
	
	public void setUserDAO(UserDAO userDao) {
		this.userDao  = userDao;
	}
	
	public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
		this.authenticationProvider  = authenticationProvider;
	}

}
