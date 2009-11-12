/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.client.gwt.common.client.EventWithEntry;


/**
 * @author aliman
 *
 */
public class CreateDatasetSuccessEvent 
	extends EventWithEntry<CreateDatasetSuccessHandler, DatasetEntry> {
	
	public static final Type<CreateDatasetSuccessHandler> TYPE = new Type<CreateDatasetSuccessHandler>();
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(CreateDatasetSuccessHandler handler) {
		handler.onSuccess(this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<CreateDatasetSuccessHandler> getAssociatedType() {
		return TYPE;
	}
	
}