/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.form.submission.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFactory;
import org.cggh.chassis.generic.atom.submission.client.format.impl.SubmissionFactoryImpl;


/**
 * @author aliman
 *
 */
public class OldSubmissionFormModel {

	
	
	
	private SubmissionEntry entry = null;

	
	
	
	private Set<Listener> listeners = new HashSet<Listener>();
	
	
	
	
	public void addListener(Listener l) { listeners.add(l); }
	
	
	
	
	/**
	 */
	public OldSubmissionFormModel() {
		
		SubmissionFactory factory = new SubmissionFactoryImpl();
		this.entry = factory.createSubmissionEntry();

	}

	
	
	
	public void setEntry(SubmissionEntry entry) {
		SubmissionEntry before = this.entry;
		this.entry = entry;
		for (Listener l : listeners) {
			l.onSubmissionEntryChanged(before, entry);
		}
	}
	
	
	
	
	public static interface Listener {
		
		public void onSubmissionEntryChanged(SubmissionEntry before, SubmissionEntry after);
		
	}
	
	
	
	
}
