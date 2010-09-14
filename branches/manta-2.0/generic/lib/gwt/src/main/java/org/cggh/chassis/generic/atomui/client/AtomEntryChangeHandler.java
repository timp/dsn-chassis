/**
 * 
 */
package org.cggh.chassis.generic.atomui.client;

import org.cggh.chassis.generic.atom.client.AtomEntry;
import org.cggh.chassis.generic.widget.client.ModelChangeHandler;

public interface AtomEntryChangeHandler<E extends AtomEntry> extends ModelChangeHandler {
	
	public void onEntryChanged(AtomEntryChangeEvent<E> e);

}