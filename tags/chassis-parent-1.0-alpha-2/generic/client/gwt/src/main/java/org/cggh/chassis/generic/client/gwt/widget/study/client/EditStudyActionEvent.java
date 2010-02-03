/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;



public class EditStudyActionEvent 
	extends StudyActionEvent {
	
	public static final Type<StudyActionHandler> TYPE = new Type<StudyActionHandler>();
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<StudyActionHandler> getAssociatedType() {
		return TYPE;
	}
	
}