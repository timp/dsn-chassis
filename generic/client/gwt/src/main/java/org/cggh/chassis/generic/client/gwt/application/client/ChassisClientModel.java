/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import java.util.HashSet;
import java.util.Set;

/**
 * @author aliman
 *
 */
public class ChassisClientModel {

	private Integer perspective;
	private Set<ChassisClientModelListener> listeners = new HashSet<ChassisClientModelListener>();

	/**
	 * @param roleId
	 */
	public void setPerspective(Integer roleId) {
		Integer before = this.perspective;
		this.perspective = roleId;
		for (ChassisClientModelListener listener : listeners) {
			listener.onPerspectiveChanged(before, roleId);
		}
	}

	/**
	 * @param renderer
	 */
	public void addListener(ChassisClientModelListener listener) {
		this.listeners.add(listener);
	}

}
