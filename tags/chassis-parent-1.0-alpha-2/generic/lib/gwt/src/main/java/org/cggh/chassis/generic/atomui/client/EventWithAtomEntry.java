/**
 * 
 */
package org.cggh.chassis.generic.atomui.client;

import org.cggh.chassis.generic.atom.client.AtomEntry;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author aliman
 *
 */
public abstract class EventWithAtomEntry
	<H extends EventHandler, E extends AtomEntry> 
	extends GwtEvent<H> {
	
	protected E entry;
	
	public EventWithAtomEntry() {}
	
	public void setEntry(E entry) {
		this.entry = entry;
	}
	
	public E getEntry() {
		return this.entry;
	}
	
}