/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionFeed;
import org.cggh.chassis.generic.widget.client.ModelChangeEvent;


public class SubmissionFeedChangeEvent extends ModelChangeEvent<SubmissionFeed, SubmissionFeedChangeHandler> {

	public static final Type<SubmissionFeedChangeHandler> TYPE = new Type<SubmissionFeedChangeHandler>();
		
	public SubmissionFeedChangeEvent(SubmissionFeed before, SubmissionFeed after) { super(before, after); }

	@Override
	protected void dispatch(SubmissionFeedChangeHandler handler) { handler.onChange(this); }

	@Override
	public Type<SubmissionFeedChangeHandler> getAssociatedType() { return TYPE; }
	
}