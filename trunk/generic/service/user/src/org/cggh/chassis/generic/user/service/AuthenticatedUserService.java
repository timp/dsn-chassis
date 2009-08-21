/**
 * 
 */
package org.cggh.chassis.generic.user.service;

import org.cggh.chassis.generic.user.to.UserTO;

/**
 * @author aliman
 *
 */
public interface AuthenticatedUserService {

	public UserTO getAuthenticatedUser();
	
}
