/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.app;

import java.util.List;

import org.cggh.chassis.wwarn.prototype.client.shared.Perspective;
import org.cggh.chassis.wwarn.prototype.client.shared.User;

/**
 * @author aliman
 *
 */
interface ModelListener {

	void onApplicationStateChanged(String oldState, String newState);

	void onUserChanged(User oldUser, User newUser);

	void onCurrentPerspectiveChanged(Perspective oldPerspective, Perspective p);

	void onPerspectivesChanged(List<Perspective> oldPerspectives, List<Perspective> newPerspectives);
	
}
