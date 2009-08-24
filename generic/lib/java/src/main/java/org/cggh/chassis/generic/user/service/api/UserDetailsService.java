/**
 * 
 */
package org.cggh.chassis.generic.user.service.api;

import org.cggh.chassis.generic.user.transfer.UserDetailsTO;

/**
 * @author aliman
 *
 */
public interface UserDetailsService {

	public UserDetailsTO getAuthenticatedUserDetails();
	
}
