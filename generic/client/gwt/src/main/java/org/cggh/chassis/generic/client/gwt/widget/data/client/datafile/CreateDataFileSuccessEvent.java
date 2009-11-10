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
public class CreateDataFileSuccessEvent extends GwtEvent<CreateDataFileSuccessHandler> {
	
	public static final Type<CreateDataFileSuccessHandler> TYPE = new Type<CreateDataFileSuccessHandler>();
	private DataFileEntry dataFileEntry;
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(CreateDataFileSuccessHandler handler) {
		handler.onCreateDataFileSuccess(this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<CreateDataFileSuccessHandler> getAssociatedType() {
		return TYPE;
	}
	
	public void setDataFileEntry(DataFileEntry entry) {
		this.dataFileEntry = entry;
	}
	
	public DataFileEntry getDataFileEntry() {
		return this.dataFileEntry;
	}
	
}