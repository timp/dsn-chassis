/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;


import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;

import com.google.gwt.event.shared.GwtEvent;

public class UploadDataFileActionEvent extends GwtEvent<UploadDataFileActionHandler> {
	
	public static final Type<UploadDataFileActionHandler> TYPE = new Type<UploadDataFileActionHandler>();
	private SubmissionEntry submissionEntry;
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(UploadDataFileActionHandler handler) {
		handler.onActionUploadDataFile(this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<UploadDataFileActionHandler> getAssociatedType() {
		return TYPE;
	}
	
	public void setSubmissionEntry(SubmissionEntry entry) {
		this.submissionEntry = entry;
	}
	
	public SubmissionEntry getSubmissionEntry() {
		return this.submissionEntry;
	}
	
}