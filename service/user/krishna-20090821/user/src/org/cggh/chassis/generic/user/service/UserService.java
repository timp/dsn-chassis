/**
 * 
 */
package org.cggh.chassis.generic.user.service;

import org.cggh.chassis.generic.user.data.User;

/**
 * @author aliman
 *
 */
public interface UserService {

	public User getAuthenticatedUser();
	
}
