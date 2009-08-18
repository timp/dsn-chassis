/**
 * 
 */
package org.cggh.chassis.generic.user.gwtrpc.client;

import org.cggh.chassis.generic.user.data.User;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * @author aliman
 *
 */
public interface RPCUserService extends RemoteService {

	public User getAuthenticatedUser();
	
}
