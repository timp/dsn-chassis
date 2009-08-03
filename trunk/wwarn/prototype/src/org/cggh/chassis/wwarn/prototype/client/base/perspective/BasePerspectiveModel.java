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
public class BasePerspectiveModel implements BasePerspectiveModelReadOnly {

	protected boolean isCurrentPerspective;
	protected List<BasePerspectiveModelListener> listeners = new ArrayList<BasePerspectiveModelListener>();
	protected String mainWidgetName;

	public void addListener(BasePerspectiveModelListener l) {
		this.listeners.add(l);
	}

	public boolean getIsCurrentPerspective() {
		return this.isCurrentPerspective;
	}

	public void setIsCurrentPerspective(boolean current) {
		boolean wasCurrent = this.isCurrentPerspective;
		this.isCurrentPerspective = current;
		for (BasePerspectiveModelListener l : listeners) {
			l.onIsCurrentPerspectiveChanged(wasCurrent, current);
		}
	}

	public void setMainWidgetName(String to) {
		String from = this.mainWidgetName;
		this.mainWidgetName = to;
		for (BasePerspectiveModelListener l : listeners) {
			l.onMainWidgetChanged(from, to);
		}
	}

	public String getMainWidgetName() {
		return this.mainWidgetName;
	}


}
