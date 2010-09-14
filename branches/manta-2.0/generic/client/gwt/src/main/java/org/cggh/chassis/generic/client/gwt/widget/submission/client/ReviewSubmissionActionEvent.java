package org.cggh.chassis.generic.client.gwt.widget.submission.client;


/** @author timp */
public class ReviewSubmissionActionEvent 
   extends SubmissionActionEvent {

	
	public static final Type<SubmissionActionHandler> TYPE = new Type<SubmissionActionHandler>();
	
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<SubmissionActionHandler> getAssociatedType() {
		return TYPE;
	}

}
