/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.client.gwt.common.client.EventWithEntry;

/**
 * @author aliman
 *
 */
public class CreateDataFileSuccessEvent 
	extends EventWithEntry<CreateDataFileSuccessHandler, DataFileEntry> {
	
	public static final Type<CreateDataFileSuccessHandler> TYPE = new Type<CreateDataFileSuccessHandler>();
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(CreateDataFileSuccessHandler handler) {
		handler.onSuccess(this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<CreateDataFileSuccessHandler> getAssociatedType() {
		return TYPE;
	}
	
}