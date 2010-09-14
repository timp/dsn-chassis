/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;



public class ViewDataFileActionEvent 
	extends DataFileActionEvent {
	
	public static final Type<DataFileActionHandler> TYPE = new Type<DataFileActionHandler>();
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<DataFileActionHandler> getAssociatedType() {
		return TYPE;
	}
	
}