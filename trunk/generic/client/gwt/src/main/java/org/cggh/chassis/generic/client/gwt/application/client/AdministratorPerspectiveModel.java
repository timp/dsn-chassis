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
public class AdministratorPerspectiveModel {

	
	

	private Log log = LogFactory.getLog(this.getClass());
	private Map<String, Boolean> widgets = new HashMap<String, Boolean>();
	private Set<AdministratorPerspectiveModelListener> listeners = new HashSet<AdministratorPerspectiveModelListener>();

	
	
	
	/**
	 * @return
	 */
	public Set<String> getWidgetNames() {
		return this.widgets.keySet();
	}
	
	


	/**
	 * @param widgetName
	 * @param visible
	 */
	public void setVisibile(String widgetName, boolean visible) {
		log.enter("setVisibile");
		
		this.widgets.put(widgetName, visible);
		for (AdministratorPerspectiveModelListener l : listeners) {
			l.onWidgetVisibilityChanged(widgetName, visible);
		}
		
		log.leave();
	}




	/**
	 * @param listener
	 */
	public void addListener(AdministratorPerspectiveModelListener listener) {
		this.listeners.add(listener);
	}

	
	
}
