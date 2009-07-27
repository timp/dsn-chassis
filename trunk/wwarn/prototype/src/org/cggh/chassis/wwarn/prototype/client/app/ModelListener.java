/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.app;

import org.cggh.chassis.wwarn.prototype.client.shared.User;

/**
 * @author aliman
 *
 */
interface ModelListener {

	void onCurrentUserChanged(User oldUser, User newUser);

	void onCurrentRoleChanged(String old, String currentRole);
	
}
