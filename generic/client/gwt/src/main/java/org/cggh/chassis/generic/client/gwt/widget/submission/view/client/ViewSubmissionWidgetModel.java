/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.client.gwt.common.client.mvc.ModelBase;

/**
 * @author aliman
 *
 */
public class ViewSubmissionWidgetModel extends ModelBase {

	
	
	
	public static final int STATUS_INITIAL = 0;
	public static final int STATUS_READY = 1;
	public static final int STATUS_RETRIEVE_PENDING = 2;
	public static final int STATUS_RETRIEVE_SUCCESS = 3;
	public static final int STATUS_RETRIEVE_ERROR = 4;
	
	
	
	private Set<Listener> listeners = new HashSet<Listener>();

	
	
	
	private SubmissionEntry entry;

	
	
	
	public ViewSubmissionWidgetModel() {
		this.status = STATUS_INITIAL;
	}
	
	
	
	
	public void addListener(Listener l) {
		super.addListener(l);
		this.listeners.add(l);
	}
	
	
	
	
	public void setSubmissionEntry(SubmissionEntry entry) {
		SubmissionEntry before = this.entry;
		this.entry = entry;
		for (Listener l : this.listeners) {
			l.onEntryChanged(before, entry);
		}
	}
	
	
	
	
	/**
	 * @return
	 */
	public SubmissionEntry getSubmissionEntry() {
		return this.entry;
	}
	
	

	

	public static interface Listener extends ModelBase.Listener {

		/**
		 * @param before
		 * @param after
		 */
		void onEntryChanged(SubmissionEntry before, SubmissionEntry after);
		
	}




}
