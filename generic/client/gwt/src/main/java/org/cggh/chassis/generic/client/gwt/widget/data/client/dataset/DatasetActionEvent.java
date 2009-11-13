/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;




import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;


public abstract class DatasetActionEvent 
	extends DatasetEvent<DatasetActionHandler> {
	
	public DatasetActionEvent() {}
	
	public DatasetActionEvent(DatasetEntry entry) {
		super(entry);
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(DatasetActionHandler handler) {
		handler.onAction(this);
	}

}