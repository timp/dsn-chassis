/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atom.client.CreateSuccessEvent;
import org.cggh.chassis.generic.atom.client.CreateSuccessHandler;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;


/**
 * @author aliman
 *
 */
public class CreateDatasetSuccessEvent 
	extends CreateSuccessEvent<DatasetEntry> {
	
	public static final Type<CreateSuccessHandler<DatasetEntry>> TYPE = new Type<CreateSuccessHandler<DatasetEntry>>();
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<CreateSuccessHandler<DatasetEntry>> getAssociatedType() {
		return TYPE;
	}
	
}