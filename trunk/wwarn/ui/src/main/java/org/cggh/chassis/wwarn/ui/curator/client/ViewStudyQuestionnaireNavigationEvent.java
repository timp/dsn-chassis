package org.cggh.chassis.wwarn.ui.curator.client;

import com.google.gwt.event.shared.GwtEvent;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 */
public class ViewStudyQuestionnaireNavigationEvent extends GwtEvent<ViewStudyQuestionnaireNavigationHandler> {

	public static final Type<ViewStudyQuestionnaireNavigationHandler> TYPE = new Type<ViewStudyQuestionnaireNavigationHandler>();
	
	@Override
	protected void dispatch(ViewStudyQuestionnaireNavigationHandler h) {
		h.onNavigation(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ViewStudyQuestionnaireNavigationHandler> getAssociatedType() {
		return TYPE;
	}

}
