/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author aliman
 *
 */
public class SubmitterPerspectiveModel {

	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private Map<String, Boolean> widgets = new HashMap<String, Boolean>();
	private Set<SubmitterPerspectiveModelListener> listeners = new HashSet<SubmitterPerspectiveModelListener>();

	
	
	
	/**
	 * @return
	 */
	public Set<String> getWidgetNames() {
		return this.widgets.keySet();
	}
	
	


	/**
	 * @param name
	 * @param visible
	 */
	public void setVisibile(String name, boolean visible) {
		log.enter("setVisibile");
		
		this.widgets.put(name, visible);
		for (SubmitterPerspectiveModelListener l : listeners) {
			l.onWidgetVisibilityChanged(name, visible);
		}
		
		log.leave();
	}




	/**
	 * @param listener
	 */
	public void addListener(SubmitterPerspectiveModelListener listener) {
		this.listeners.add(listener);
	}

	
	
	
}
