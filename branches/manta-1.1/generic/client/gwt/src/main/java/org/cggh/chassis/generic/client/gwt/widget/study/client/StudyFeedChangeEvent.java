/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.atomext.client.study.StudyFeed;
import org.cggh.chassis.generic.widget.client.ModelChangeEvent;


public class StudyFeedChangeEvent extends ModelChangeEvent<StudyFeed, StudyFeedChangeHandler> {

	public static final Type<StudyFeedChangeHandler> TYPE = new Type<StudyFeedChangeHandler>();
		
	public StudyFeedChangeEvent(StudyFeed before, StudyFeed after) { super(before, after); }

	@Override
	protected void dispatch(StudyFeedChangeHandler handler) { handler.onChange(this); }

	@Override
	public Type<StudyFeedChangeHandler> getAssociatedType() { return TYPE; }
	
}