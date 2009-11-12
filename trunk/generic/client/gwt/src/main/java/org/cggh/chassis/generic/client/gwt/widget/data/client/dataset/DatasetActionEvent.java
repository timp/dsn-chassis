/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;




import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;

import com.google.gwt.event.shared.GwtEvent;

public abstract class DatasetActionEvent extends GwtEvent<DatasetActionHandler> {
	
	private DatasetEntry entry;
	
	public DatasetActionEvent() {}
	
	public DatasetActionEvent(DatasetEntry entry) {
		this.entry = entry;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(DatasetActionHandler handler) {
		handler.onAction(this);
	}

	public void setEntry(DatasetEntry entry) {
		this.entry = entry;
	}
	
	public DatasetEntry getEntry() {
		return this.entry;
	}
	
}