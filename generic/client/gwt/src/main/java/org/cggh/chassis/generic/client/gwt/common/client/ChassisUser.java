/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.common.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;

/**
 * @author aliman
 *
 */
public class ChassisUser {

	private static UserDetailsTO currentUser;
	private static Deferred<UserDetailsTO> currentUserDeferred;
	
	public static String getCurrentUserEmail() {
		return currentUser.getId();
	}

	public static UserDetailsTO getCurrentUser() {
		return currentUser;
	}
	
	public static void setCurrentUser(UserDetailsTO user) {
		currentUser = user;
	}
	
	public static void setCurrentUserDeferred(Deferred<UserDetailsTO> deferredUser) {
		currentUserDeferred = deferredUser;
	}
	
	public static Deferred<UserDetailsTO> getCurrentUserDeferred() {
		return currentUserDeferred;
	}

	/**
	 * @return
	 */
	public static String getLang() {
		return "en"; // TODO i18n
	}
	
	
}
