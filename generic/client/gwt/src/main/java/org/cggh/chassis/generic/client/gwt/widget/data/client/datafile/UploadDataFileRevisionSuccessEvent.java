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
public class UploadDataFileRevisionSuccessEvent extends GwtEvent<UploadDataFileRevisionSuccessHandler> {
	
	public static final Type<UploadDataFileRevisionSuccessHandler> TYPE = new Type<UploadDataFileRevisionSuccessHandler>();
	private DataFileEntry dataFileEntry;
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(UploadDataFileRevisionSuccessHandler handler) {
		handler.onUploadDataFileRevisionSuccess(this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<UploadDataFileRevisionSuccessHandler> getAssociatedType() {
		return TYPE;
	}
	
	public void setDataFileEntry(DataFileEntry entry) {
		this.dataFileEntry = entry;
	}
	
	public DataFileEntry getDataFileEntry() {
		return this.dataFileEntry;
	}
	
}