/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.atom.client.UpdateSuccessEvent;
import org.cggh.chassis.generic.atom.client.UpdateSuccessHandler;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;


/**
 * @author aliman
 *
 */
public class UpdateDataFileSuccessEvent 
	extends UpdateSuccessEvent<DataFileEntry> {
	
	public static final Type<UpdateSuccessHandler<DataFileEntry>> TYPE = new Type<UpdateSuccessHandler<DataFileEntry>>();
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<UpdateSuccessHandler<DataFileEntry>> getAssociatedType() {
		return TYPE;
	}
	
}