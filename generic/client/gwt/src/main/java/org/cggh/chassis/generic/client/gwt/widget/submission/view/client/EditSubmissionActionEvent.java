/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;

import com.google.gwt.event.shared.GwtEvent;

public class EditSubmissionActionEvent extends GwtEvent<EditSubmissionActionHandler> {
	
	public static final Type<EditSubmissionActionHandler> TYPE = new Type<EditSubmissionActionHandler>();
	private SubmissionEntry submissionEntry;
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(EditSubmissionActionHandler handler) {
		handler.onActionEditSubmission(this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<EditSubmissionActionHandler> getAssociatedType() {
		return TYPE;
	}
	
	public void setSubmissionEntry(SubmissionEntry entry) {
		this.submissionEntry = entry;
	}
	
	public SubmissionEntry getSubmissionEntry() {
		return this.submissionEntry;
	}
	
}