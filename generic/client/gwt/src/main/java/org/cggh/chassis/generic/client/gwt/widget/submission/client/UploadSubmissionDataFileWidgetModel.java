/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.client.gwt.common.client.mvc.ModelBase;


/**
 * @author raok
 *
 */
public class UploadSubmissionDataFileWidgetModel extends ModelBase {


	
	
	public static final int STATUS_INITIAL = 0;
	public static final int STATUS_READY = 1;
	public static final int STATUS_CREATE_PENDING = 2;
	public static final int STATUS_CREATE_SUCCESS = 3;
	public static final int STATUS_CREATE_ERROR = 4;
	public static final int STATUS_CANCELLED = 5;

	
	
	
	private Set<Listener> listeners = new HashSet<Listener>();
	
	
	
	
	public UploadSubmissionDataFileWidgetModel() {
		this.status = STATUS_INITIAL;
	}
	
	

	
	
	public void addListener(Listener l) {
		super.addListener(l);
		this.listeners.add(l);
	}
	
	
	
	
	public static interface Listener extends ModelBase.Listener {}
	

}
