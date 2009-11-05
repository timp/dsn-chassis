/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.create.client;


import org.cggh.chassis.generic.atom.rewrite.client.submission.SubmissionEntry;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author aliman
 *
 */
public class CreateSubmissionSuccessEvent extends GwtEvent<CreateSubmissionSuccessHandler> {
	
	public static final Type<CreateSubmissionSuccessHandler> TYPE = new Type<CreateSubmissionSuccessHandler>();
	private SubmissionEntry entry;
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(CreateSubmissionSuccessHandler handler) {
		handler.onCreateSubmissionSuccess(this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<CreateSubmissionSuccessHandler> getAssociatedType() {
		return TYPE;
	}
	
	public void setSubmissionEntry(SubmissionEntry entry) {
		this.entry = entry;
	}
	
	public SubmissionEntry getSubmissionEntry() {
		return this.entry;
	}
	
}