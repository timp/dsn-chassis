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

	void onCurrentUserChanged(User from, User to);

	void onCurrentRoleChanged(String from, String to);

	void onInitialisationCompleteChanged(Boolean from, Boolean to);

	void onInitialisationSuccessChanged(Boolean from, Boolean to);
	
}
