/**
 * @author timp
 * @since 4 Dec 2009
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.widget.client.ModelChangeEvent;


public class SubmissionEntryChangeEvent extends ModelChangeEvent<SubmissionEntry, SubmissionEntryChangeHandler> {

    public SubmissionEntryChangeEvent(SubmissionEntry before, SubmissionEntry after) { super(before, after); }

	public static final Type<SubmissionEntryChangeHandler> TYPE = new Type<SubmissionEntryChangeHandler>();
    
	@Override
	protected void dispatch(SubmissionEntryChangeHandler handler) { handler.onChange(this); }

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<SubmissionEntryChangeHandler> getAssociatedType()  { return TYPE; }

}
