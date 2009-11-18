/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;



import org.cggh.chassis.generic.atom.client.ui.EventWithAtomEntry;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;

import com.google.gwt.event.shared.EventHandler;

public abstract class DatasetEvent
	<H extends EventHandler>
	extends EventWithAtomEntry<H, DatasetEntry> {
	
	public DatasetEvent() {}
	
}