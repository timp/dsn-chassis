/**
 * 
 */
package org.cggh.chassis.generic.user.gwtrpc.client;

import org.cggh.chassis.generic.user.transfer.UserDetailsTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author aliman
 *
 */
public interface GWTUserDetailsServiceAsync {

	/**
	 * 
	 * @see org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsService#getAuthenticatedUserDetails()
	 */
	void getAuthenticatedUserDetails(AsyncCallback<UserDetailsTO> callback);

}
