/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.common.client;

import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;

/**
 * @author aliman
 *
 */
public class ChassisUser {

	private static String currentUserEmail;
	private static Deferred<UserDetailsTO> currentUserDeferred;
	
	public static String getCurrentUserEmail() {
		return currentUserEmail;
	}
	
	public static void setCurrentUserEmail(String email) {
		currentUserEmail = email;
	}

	public static void setCurrentUserDeferred(Deferred<UserDetailsTO> deferredUser) {
		currentUserDeferred = deferredUser;
	}
	
	public static Deferred<UserDetailsTO> getCurrentUserDeferred() {
		return currentUserDeferred;
	}
	
	
}
