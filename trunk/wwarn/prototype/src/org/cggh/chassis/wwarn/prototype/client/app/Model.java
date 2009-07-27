/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.app;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.wwarn.prototype.client.shared.Perspective;
import org.cggh.chassis.wwarn.prototype.client.shared.User;

/**
 * @author aliman
 *
 */
class Model {

	private List<ModelListener> listeners = new ArrayList<ModelListener>();

	private User currentUser = null;
	private String currentRole = null;

	/**
	 * @return the currentRole
	 */
	String getCurrentRole() {
		return currentRole;
	}

	/**
	 * @param currentRole the currentRole to set
	 */
	void setCurrentRole(String currentRole) {
		if (!this.currentRole.equals(currentRole)) {
			String old = this.currentRole;
			this.currentRole = currentRole;
			for (ModelListener l : this.listeners) {
				l.onCurrentRoleChanged(old, currentRole);
			}
		}
	}

	void addListener(ModelListener listener) {
		this.listeners.add(listener);
	}

	void setCurrentUser(User newUser) {
		User oldUser = this.currentUser ;
		this.currentUser = newUser;
		for (ModelListener l : this.listeners) {
			l.onCurrentUserChanged(oldUser, newUser);
		}
	}

	User getCurrentUser() {
		return this.currentUser;
	}

}
