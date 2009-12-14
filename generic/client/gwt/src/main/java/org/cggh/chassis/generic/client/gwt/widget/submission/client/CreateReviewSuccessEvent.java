package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.review.ReviewEntry;
import org.cggh.chassis.generic.atomui.client.CreateSuccessEvent;

@SuppressWarnings("unchecked")
public class CreateReviewSuccessEvent 
    extends CreateSuccessEvent<ReviewEntry> {

	public static final Type<CreateReviewSuccessHandler> TYPE = new Type<CreateReviewSuccessHandler>();

	@Override
	public Type getAssociatedType() {
		return TYPE;
	}

}
