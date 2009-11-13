/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author aliman
 *
 */
public abstract class AtomEntryEvent
	<H extends EventHandler, E extends AtomEntry> 
	extends GwtEvent<H> {
	
	protected E entry;
	
	public AtomEntryEvent() {}
	
	public AtomEntryEvent(E entry) {
		this.entry = entry;
	}	
	
	public void setEntry(E entry) {
		this.entry = entry;
	}
	
	public E getEntry() {
		return this.entry;
	}
	
}