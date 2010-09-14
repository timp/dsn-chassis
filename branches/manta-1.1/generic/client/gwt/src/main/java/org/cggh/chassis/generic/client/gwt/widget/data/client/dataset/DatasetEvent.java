/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;



import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomui.client.EventWithAtomEntry;

import com.google.gwt.event.shared.EventHandler;

public abstract class DatasetEvent
	<H extends EventHandler>
	extends EventWithAtomEntry<H, DatasetEntry> {
	
	public DatasetEvent() {}
	
}