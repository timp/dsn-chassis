/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atom.client.UpdateSuccessEvent;
import org.cggh.chassis.generic.atom.client.UpdateSuccessHandler;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;


/**
 * @author aliman
 *
 */
public class UpdateDatasetSuccessEvent 
	extends UpdateSuccessEvent<DatasetEntry> {
	
	public static final Type<UpdateSuccessHandler<DatasetEntry>> TYPE = new Type<UpdateSuccessHandler<DatasetEntry>>();
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<UpdateSuccessHandler<DatasetEntry>> getAssociatedType() {
		return TYPE;
	}
	
}