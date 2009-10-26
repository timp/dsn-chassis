/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.edit.client;

import java.util.HashSet;
import java.util.Set;


/**
 * @author aliman
 *
 */
public class EditSubmissionWidgetModel {

	
	
	
	public static final int STATUS_INITIAL = 0;
	public static final int STATUS_READY = 1;
	public static final int STATUS_SAVING = 2;
	public static final int STATUS_SAVED = 3;
	public static final int STATUS_ERROR = 4;
	public static final int STATUS_CANCELLED = 5;
	
	
	
	private int status = STATUS_INITIAL;
	
	
	
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
