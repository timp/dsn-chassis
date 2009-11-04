/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.common.client.mvc;

import java.util.HashSet;
import java.util.Set;

/**
 * @author aliman
 *
 */
public class ModelBase {

	
	
	protected int status;
	
	
	
	private Set<Listener> listeners = new HashSet<Listener>();
	
	
	
	
	public void addListener(Listener l) {
		this.listeners.add(l);
	}
	
	
	
	
	public void setStatus(int status) {
		int before = this.status;
		this.status = status;
		for (Listener l : listeners) {
			l.onStatusChanged(before, status);
		}
	}

	
	
	
	public static interface Listener {
		
		public void onStatusChanged(int before, int after);
		
	}
	
	
	

}
