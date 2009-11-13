/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.atom.client.CreateSuccessEvent;
import org.cggh.chassis.generic.atom.client.CreateSuccessHandler;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;


/**
 * @author aliman
 *
 */
public class CreateDataFileSuccessEvent 
	extends CreateSuccessEvent<DataFileEntry> {
	
	public static final Type<CreateSuccessHandler<DataFileEntry>> TYPE = new Type<CreateSuccessHandler<DataFileEntry>>();
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<CreateSuccessHandler<DataFileEntry>> getAssociatedType() {
		return TYPE;
	}
	
}