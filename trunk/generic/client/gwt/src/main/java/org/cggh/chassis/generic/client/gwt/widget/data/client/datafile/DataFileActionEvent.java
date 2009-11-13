/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;



import org.cggh.chassis.generic.atom.client.AtomEntryEvent;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;

public abstract class DataFileActionEvent 
	extends AtomEntryEvent<DataFileActionHandler, DataFileEntry> {
	
	public DataFileActionEvent() {}
	
	public DataFileActionEvent(DataFileEntry entry) {
		super(entry);
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(DataFileActionHandler handler) {
		handler.onAction(this);
	}

}