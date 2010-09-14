/**
 * 
 */
package org.cggh.chassis.generic.user.gwtrpc.server.spring;

import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsService;
import org.cggh.chassis.generic.user.service.api.UserDetailsService;
import org.cggh.chassis.generic.user.service.spring.UserDetailsServiceSpringImpl;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author aliman
 *
 */
public class GWTUserDetailsServiceSpringImpl extends RemoteServiceServlet implements GWTUserDetailsService {

	private static final long serialVersionUID = -9112474157363327179L;

	public UserDetailsTO getAuthenticatedUserDetails() {
		UserDetailsService delegate = new UserDetailsServiceSpringImpl();
		return delegate.getAuthenticatedUserDetails();
	}
	
}
