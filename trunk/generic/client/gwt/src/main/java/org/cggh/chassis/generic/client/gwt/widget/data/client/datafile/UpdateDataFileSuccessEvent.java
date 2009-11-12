/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author aliman
 *
 */
public class UpdateDataFileSuccessEvent extends GwtEvent<UpdateDataFileSuccessHandler> {
	
	public static final Type<UpdateDataFileSuccessHandler> TYPE = new Type<UpdateDataFileSuccessHandler>();
	private DataFileEntry dataFileEntry;
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(UpdateDataFileSuccessHandler handler) {
		handler.onSuccess(this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<UpdateDataFileSuccessHandler> getAssociatedType() {
		return TYPE;
	}
	
	public void setDataFileEntry(DataFileEntry entry) {
		this.dataFileEntry = entry;
	}
	
	public DataFileEntry getEntry() {
		return this.dataFileEntry;
	}
	
}