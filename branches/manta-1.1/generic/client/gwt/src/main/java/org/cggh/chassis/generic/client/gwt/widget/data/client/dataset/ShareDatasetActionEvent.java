/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;



public class ShareDatasetActionEvent 
	extends DatasetActionEvent {
	
	public static final Type<DatasetActionHandler> TYPE = new Type<DatasetActionHandler>();
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<DatasetActionHandler> getAssociatedType() {
		return TYPE;
	}
	
}