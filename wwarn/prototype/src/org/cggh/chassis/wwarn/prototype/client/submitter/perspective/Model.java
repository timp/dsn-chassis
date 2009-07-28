/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.perspective;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aliman
 *
 */
class Model {

	private boolean isCurrentPerspective;
	private List<ModelListener> listeners = new ArrayList<ModelListener>();
	private String mainWidgetName;

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

	void setMainWidgetName(String to) {
		String from = this.mainWidgetName;
		if (!to.equals(from)) {
			this.mainWidgetName = to;
			for (ModelListener l : listeners) {
				l.onMainWidgetChanged(from, to);
			}
		}
	}

	public String getMainWidgetName() {
		return this.mainWidgetName;
	}

}
