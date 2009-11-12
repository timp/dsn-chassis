/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.common.client;

import org.cggh.chassis.generic.atom.client.AtomEntry;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author aliman
 *
 */
public abstract class EventWithEntry<H extends EventHandler, E extends AtomEntry> extends GwtEvent<H> {
	
	protected E entry;
	
	public void setEntry(E entry) {
		this.entry = entry;
	}
	
	public E getEntry() {
		return this.entry;
	}
	
}