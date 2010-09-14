/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;






public abstract class StudyActionEvent 
	extends StudyEvent<StudyActionHandler> {
	
	public StudyActionEvent() {}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(StudyActionHandler handler) {
		handler.onAction(this);
	}

}