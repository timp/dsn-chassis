/**
 * 
 */
package org.cggh.chassis.generic.user.gwtrpc.server;

import org.cggh.chassis.generic.user.data.User;
import org.cggh.chassis.generic.user.gwtrpc.client.RPCUserService;
import org.cggh.chassis.generic.user.service.UserService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author aliman
 *
 */
public class RPCUserServiceImpl extends RemoteServiceServlet implements RPCUserService {

	
	
	private UserService delegate = null;



	public User getAuthenticatedUser() {
		if (delegate != null) {
			return delegate.getAuthenticatedUser();
		}
		return null;
	}

	
	
	public void setUserService(UserService delegate) {
		this.delegate  = delegate;
	}
	
	
	
}
