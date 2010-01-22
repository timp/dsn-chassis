package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomui.client.UpdateSuccessEvent;

@SuppressWarnings("unchecked")
public class UpdateSubmissionSuccessEvent 
    extends UpdateSuccessEvent<SubmissionEntry> {

	public static final Type<UpdateSubmissionSuccessHandler> TYPE = new Type<UpdateSubmissionSuccessHandler>();

	@Override
	public Type getAssociatedType() {
		return TYPE;
	}

}
