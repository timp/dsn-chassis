/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;



import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomui.client.EventWithAtomEntry;

import com.google.gwt.event.shared.EventHandler;

public abstract class DataFileEvent
	<H extends EventHandler>
	extends EventWithAtomEntry<H, DataFileEntry> {
	
	public DataFileEvent() {}
	
}