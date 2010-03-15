package org.cggh.chassis.wwarn.ui.curator.client;

import com.google.gwt.event.shared.GwtEvent;

public class EditStudyQuestionnaireNavigationEvent extends GwtEvent<EditStudyQuestionnaireNavigationHandler> {

	public static final Type<EditStudyQuestionnaireNavigationHandler> TYPE = new Type<EditStudyQuestionnaireNavigationHandler>();
	
	@Override
	protected void dispatch(EditStudyQuestionnaireNavigationHandler h) {
		h.onNavigation(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<EditStudyQuestionnaireNavigationHandler> getAssociatedType() {
		return TYPE;
	}

}
