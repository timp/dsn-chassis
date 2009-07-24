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

	private String StateToken = null;
	private User currentUser = null;
	private List<Perspective> perspectives = new ArrayList<Perspective>();
	private Perspective currentPerspective = null;

	void setStateToken(String newStateToken) {
		String oldStateToken = this.StateToken;
		this.StateToken = newStateToken;
		for (ModelListener l : this.listeners) {
			l.onApplicationStateChanged(oldStateToken, newStateToken);
		}
	}

	void addListener(ModelListener listener) {
		this.listeners.add(listener);
	}

	void setCurrentUser(User newUser) {
		User oldUser = this.currentUser ;
		this.currentUser = newUser;
		for (ModelListener l : this.listeners) {
			l.onUserChanged(oldUser, newUser);
		}
	}

	List<Perspective> getPerspectives() {
		return this.perspectives ;
	}

	void setCurrentPerspective(Perspective p) {
		Perspective oldPerspective = this.currentPerspective ;
		this.currentPerspective = p;
		for (ModelListener l : this.listeners) {
			l.onCurrentPerspectiveChanged(oldPerspective, p);
		}
	}

	User getCurrentUser() {
		return this.currentUser;
	}

	Perspective getCurrentPerspective() {
		return this.currentPerspective;
	}

	void setPerspectives(List<Perspective> newPerspectives) {
		List<Perspective> oldPerspectives = this.perspectives;
		this.perspectives = newPerspectives;
		for (ModelListener l : this.listeners) {
			l.onPerspectivesChanged(oldPerspectives, newPerspectives);
		}
	}

}
