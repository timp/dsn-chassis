/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.app;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.wwarn.prototype.client.user.User;

/**
 * @author aliman
 *
 */
class Model {

	private List<ModelListener> listeners = new ArrayList<ModelListener>();

	private User currentUser = null;
	private String currentRole = null;
	private Boolean initialisationComplete = false;
	private Boolean initialisationSuccess = null;

	
	
	
	/**
	 * @return the initialisationComplete
	 */
	Boolean getInitialisationComplete() {
		return initialisationComplete;
	}

	/**
	 * @param initialisationComplete the initialisationComplete to set
	 */
	void setInitialisationComplete(Boolean initialisationComplete) {
		if (!initialisationComplete.equals(this.initialisationComplete)) {
			Boolean old = this.initialisationComplete;
			this.initialisationComplete = initialisationComplete;
			for (ModelListener l : this.listeners) {
				l.onInitialisationCompleteChanged(old, initialisationComplete);
			}
		}
	}

	/**
	 * @return the initialisationSuccess
	 */
	Boolean getInitialisationSuccess() {
		return initialisationSuccess;
	}

	/**
	 * @param initialisationSuccess the initialisationSuccess to set
	 */
	void setInitialisationSuccess(Boolean initialisationSuccess) {
		if (!initialisationSuccess.equals(this.initialisationSuccess)) {
			Boolean old = this.initialisationSuccess;
			this.initialisationSuccess = initialisationSuccess;
			for (ModelListener l : this.listeners) {
				l.onInitialisationSuccessChanged(old, initialisationSuccess);
			}
		}
	}


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
		String old = this.currentRole;
		this.currentRole = currentRole;
		for (ModelListener l : this.listeners) {
			l.onCurrentRoleChanged(old, currentRole);
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
