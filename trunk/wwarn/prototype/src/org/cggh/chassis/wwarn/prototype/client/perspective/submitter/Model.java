/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.perspective.submitter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aliman
 *
 */
class Model {

	private boolean isCurrentPerspective;
	private List<ModelListener> listeners = new ArrayList<ModelListener>();

	void addListener(ModelListener l) {
		this.listeners.add(l);
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
