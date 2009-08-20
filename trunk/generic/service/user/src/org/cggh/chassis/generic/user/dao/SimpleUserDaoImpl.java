/**
 * 
 */
package org.cggh.chassis.generic.user.dao;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.user.data.User;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;

/**
 * @author raok
 *
 */
public class SimpleUserDaoImpl implements UserDAO {

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.user.dao.UserDAO#getUserById(java.lang.String)
	 */
	public User getUserById(String userId) {
		//Spring has already checked user exists, so just need to get additional user details
		
		//get Spring authentication object
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		//Create user and populate with Spring authenticated user details
		User user = new User();
		//Assume id and name are the same
		user.setId(authentication.getName());
		user.setName(authentication.getName());
		
		GrantedAuthority[] grantedAuthorities = authentication.getAuthorities();
		
		Set<String> roles = new HashSet<String>();
		for(GrantedAuthority authority : grantedAuthorities) {
			roles.add(authority.getAuthority());
		}
		user.setRoles(roles);
		
		return user;
	}

}
