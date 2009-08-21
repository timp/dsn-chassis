/**
 * 
 */
package org.cggh.chassis.generic.user.gwtrpc.server.spring;

import org.cggh.chassis.generic.user.gwtrpc.client.GWTAuthenticatedUserService;
import org.cggh.chassis.generic.user.service.AuthenticatedUserService;
import org.cggh.chassis.generic.user.service.spring.AuthenticatedUserServiceSpringImpl;
import org.cggh.chassis.generic.user.transfer.UserTO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author aliman
 *
 */
public class GWTAuthenticatedUserServiceSpringImpl extends RemoteServiceServlet implements GWTAuthenticatedUserService {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public UserTO getAuthenticatedUser() {
		AuthenticatedUserService delegate = new AuthenticatedUserServiceSpringImpl();
		return delegate.getAuthenticatedUser();
	}

	
	
}
