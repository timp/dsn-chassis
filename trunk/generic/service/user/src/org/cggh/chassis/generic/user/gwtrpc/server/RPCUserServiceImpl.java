/**
 * 
 */
package org.cggh.chassis.generic.user.gwtrpc.server;

import org.cggh.chassis.generic.user.data.User;
import org.cggh.chassis.generic.user.gwtrpc.client.RPCUserService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author aliman
 *
 */
public class RPCUserServiceImpl extends RemoteServiceServlet implements RPCUserService {

	public User getAuthenticatedUser() {
		// TODO Auto-generated method stub
		return null;
	}

}
