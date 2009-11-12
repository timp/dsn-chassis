/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;



import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.client.gwt.common.client.EventWithEntry;

public abstract class DataFileActionEvent 
	extends EventWithEntry<DataFileActionHandler, DataFileEntry> {
	
	public DataFileActionEvent() {}
	
	public DataFileActionEvent(DataFileEntry entry) {
		this.entry = entry;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(DataFileActionHandler handler) {
		handler.onAction(this);
	}

}