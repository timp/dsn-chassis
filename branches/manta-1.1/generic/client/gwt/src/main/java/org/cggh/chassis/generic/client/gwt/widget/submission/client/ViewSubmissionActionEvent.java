/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;



public class ViewSubmissionActionEvent 
	extends SubmissionActionEvent {
	
	public static final Type<SubmissionActionHandler> TYPE = new Type<SubmissionActionHandler>();
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<SubmissionActionHandler> getAssociatedType() {
		return TYPE;
	}
	
}