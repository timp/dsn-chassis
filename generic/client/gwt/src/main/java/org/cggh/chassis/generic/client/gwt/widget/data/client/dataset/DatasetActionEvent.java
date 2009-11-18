/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;






public abstract class DatasetActionEvent 
	extends DatasetEvent<DatasetActionHandler> {
	
	public DatasetActionEvent() {}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(DatasetActionHandler handler) {
		handler.onAction(this);
	}

}