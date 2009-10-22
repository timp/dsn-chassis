/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.common.client;

/**
 * @author aliman
 *
 */
public class ChassisUser {

	private static String currentUserEmail = null;
	
	public static String getCurrentUserEmail() {
		return currentUserEmail;
	}
	
	public static void setCurrentUserEmail(String email) {
		currentUserEmail = email;
	}
	
}
