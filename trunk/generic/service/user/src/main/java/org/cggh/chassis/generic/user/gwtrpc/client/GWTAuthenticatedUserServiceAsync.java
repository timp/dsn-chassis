/**
 * 
 */
package org.cggh.chassis.generic.user.gwtrpc.client;

import org.cggh.chassis.generic.user.transfer.UserTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author aliman
 *
 */
public interface GWTAuthenticatedUserServiceAsync {

	/**
	 * 
	 * @see org.cggh.chassis.generic.user.gwtrpc.client.GWTAuthenticatedUserService#getAuthenticatedUser()
	 */
	void getAuthenticatedUser(AsyncCallback<UserTO> callback);

}
