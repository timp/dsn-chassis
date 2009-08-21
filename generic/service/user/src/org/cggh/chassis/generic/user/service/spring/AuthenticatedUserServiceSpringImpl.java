/**
 * 
 */
package org.cggh.chassis.generic.user.service.spring;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.user.service.AuthenticatedUserService;
import org.cggh.chassis.generic.user.to.UserTO;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;

/**
 * @author aliman
 *
 */
public class AuthenticatedUserServiceSpringImpl implements AuthenticatedUserService {

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.user.service.UserService#getAuthenticatedUser()
	 */
	public UserTO getAuthenticatedUser() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return getUserTOFromPrincipal(principal);

	}
	
	
	
	public static UserTO getUserTOFromPrincipal(Object principal) {

		UserTO user = null;
		
		if (principal instanceof UserDetails) {

			user = new UserTO();
			
			UserDetails userDetails = (UserDetails) principal;
			
			user.setId(userDetails.getUsername());

			Set<String> roles = new HashSet<String>();
			for ( GrantedAuthority authority : userDetails.getAuthorities() ) {
				roles.add(authority.getAuthority());
			}
			user.setRoles(roles);

		}

		return user;
		
	}
	
	

}
