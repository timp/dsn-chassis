package org.cggh.chassis.wwarn.ui.curator.client;

import com.google.gwt.event.shared.GwtEvent;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 */
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
