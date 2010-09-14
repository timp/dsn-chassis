/**
 * 
 */
package org.cggh.chassis.generic.user.service.spring;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.user.service.api.UserDetailsService;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;

/**
 * @author aliman
 *
 */
public class UserDetailsServiceSpringImpl implements UserDetailsService {

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.user.service.UserService#getAuthenticatedUser()
	 */
	public UserDetailsTO getAuthenticatedUserDetails() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return getUserTOFromPrincipal(principal);

	}
	
	
	
	public static UserDetailsTO getUserTOFromPrincipal(Object principal) {

		UserDetailsTO user = null;
		
		if (principal instanceof UserDetails) {

			user = new UserDetailsTO();
			
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
