/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.client.gwt.widget.submission.view.client.EditSubmissionActionHandler;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

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