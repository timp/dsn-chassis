package org.cggh.chassis.generic.user.authentication;

import org.springframework.security.context.SecurityContextHolder;

/**
 * @author raok
 *
 */
public class SimpleAuthenticationProviderImpl implements AuthenticationProvider {
		
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.user.authentication.AuthenticationProvider#getAuthenticatedUserId()
	 */
	public String getAuthenticatedUserId() {
		// TODO Auto-generated method stub
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}
