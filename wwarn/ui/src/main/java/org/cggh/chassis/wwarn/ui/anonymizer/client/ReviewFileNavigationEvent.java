package org.cggh.chassis.wwarn.ui.anonymizer.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.shared.GwtEvent;

public class ReviewFileNavigationEvent extends GwtEvent<ReviewFileNavigationHandler> {

	private static final Log log = LogFactory.getLog(ReviewFileNavigationEvent.class);		
	
	public static final Type<ReviewFileNavigationHandler> TYPE = new Type<ReviewFileNavigationHandler>();
	
	@Override
	protected void dispatch(ReviewFileNavigationHandler h) {
		
		log.enter("dispatch");
		
		h.onNavigation(this);
		
		log.leave();
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ReviewFileNavigationHandler> getAssociatedType() {
		return TYPE;
	}

}
