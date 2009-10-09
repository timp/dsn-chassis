/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.application.client;

import java.util.HashSet;
import java.util.Set;

/**
 * @author raok
 *
 */
public class ApplicationWidgetModel {

	public static final Integer STATUS_INITIAL = 0;
	public static final Integer STATUS_SUBMITTER_PERSPECTIVE = 1;
	public static final Integer STATUS_GATEKEEPER_PERSPECTIVE = 2;
	public static final Integer STATUS_COORDINATOR_PERSPECTIVE = 3;
	public static final Integer STATUS_CURATOR_PERSPECTIVE = 4;
	public static final Integer STATUS_USER_PERSPECTIVE = 5;
	
	private Integer status = STATUS_INITIAL;
	private Set<ApplicationWidgetModelListener> listeners = new HashSet<ApplicationWidgetModelListener>();

	public void setStatus(Integer status) {
		
		Integer before = this.status;
		
		this.status = status;

		fireOnDisplayStatusChanged(before);
	}

	private void fireOnDisplayStatusChanged(Integer before) {

		for (ApplicationWidgetModelListener listener : listeners) {
			listener.onPerspectiveChanged(before, status);
		}
		
	}

	public Integer getStatus() {
		return status;
	}

	public void addListener(ApplicationWidgetModelListener listener) {
		listeners.add(listener);
	}
	
	
	
	

}
