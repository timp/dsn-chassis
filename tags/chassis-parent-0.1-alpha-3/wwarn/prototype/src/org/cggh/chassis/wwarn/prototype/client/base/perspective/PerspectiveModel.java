/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.base.perspective;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aliman
 *
 */
public class PerspectiveModel implements PerspectiveModelReadOnly {

	protected boolean isCurrentPerspective;
	protected List<PerspectiveModelListener> listeners = new ArrayList<PerspectiveModelListener>();
	protected String mainWidgetName;

	public void addListener(PerspectiveModelListener l) {
		this.listeners.add(l);
	}

	public boolean getIsCurrentPerspective() {
		return this.isCurrentPerspective;
	}

	public void setIsCurrentPerspective(boolean current) {
		boolean wasCurrent = this.isCurrentPerspective;
		this.isCurrentPerspective = current;
		for (PerspectiveModelListener l : listeners) {
			l.onIsCurrentPerspectiveChanged(wasCurrent, current);
		}
	}

	public void setMainWidgetName(String to) {
		String from = this.mainWidgetName;
		this.mainWidgetName = to;
		for (PerspectiveModelListener l : listeners) {
			l.onMainWidgetChanged(from, to);
		}
	}

	public String getMainWidgetName() {
		return this.mainWidgetName;
	}


}
