/**
 * 
 */
package org.cggh.chassis.generic.user.gwtrpc.client;

import org.cggh.chassis.generic.user.data.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author aliman
 *
 */
public interface RPCUserServiceAsync {

	/**
	 * 
	 * @see org.cggh.chassis.generic.user.gwtrpc.client.RPCUserService#getAuthenticatedUser()
	 */
	void getAuthenticatedUser(AsyncCallback<User> callback);

}
