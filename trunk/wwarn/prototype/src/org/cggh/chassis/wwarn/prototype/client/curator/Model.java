/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.curator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aliman
 *
 */
class Model {

	private boolean isCurrentPerspective;
	private List<ModelListener> listeners = new ArrayList<ModelListener>();
	private String stateToken;

	void addListener(ModelListener l) {
		this.listeners.add(l);
	}

	void setStateToken(String stateToken) {
		String oldState = this.stateToken;
		this.stateToken = stateToken;
		for (ModelListener l : listeners) {
			l.onStateTokenChanged(oldState, this.stateToken);
		}
	}

	boolean getIsCurrentPerspective() {
		return this.isCurrentPerspective;
	}

	void setIsCurrentPerspective(boolean current) {
		boolean wasCurrent = this.isCurrentPerspective;
		this.isCurrentPerspective = current;
		for (ModelListener l : listeners) {
			l.onIsCurrentPerspectiveChanged(wasCurrent, current);
		}
	}

}
