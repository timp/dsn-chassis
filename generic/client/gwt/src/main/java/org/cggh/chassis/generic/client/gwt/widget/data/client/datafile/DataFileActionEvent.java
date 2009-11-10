/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;



import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;

import com.google.gwt.event.shared.GwtEvent;

public abstract class DataFileActionEvent extends GwtEvent<DataFileActionHandler> {
	
	private DataFileEntry entry;
	
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

	public void setDataFileEntry(DataFileEntry entry) {
		this.entry = entry;
	}
	
	public DataFileEntry getDataFileEntry() {
		return this.entry;
	}
	
}