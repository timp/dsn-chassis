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

	void addListener(ModelListener l) {
		this.listeners.add(l);
	}

	boolean getIsCurrentPerspective() {
		return this.isCurrentPerspective;
	}

	void setIsCurrentPerspective(boolean to) {
		boolean from = this.isCurrentPerspective;
		this.isCurrentPerspective = to;
		for (ModelListener l : listeners) {
			l.onIsCurrentPerspectiveChanged(from, to);
		}
	}

}
