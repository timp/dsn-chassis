/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;






public abstract class SubmissionActionEvent 
	extends SubmissionEvent<SubmissionActionHandler> {
	
	public SubmissionActionEvent() {}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(SubmissionActionHandler handler) {
		handler.onAction(this);
	}

}