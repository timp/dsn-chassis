/**
 * @author timp
 * @since 8 Dec 2009
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.review.ReviewEntry;
import org.cggh.chassis.generic.atomui.client.EventWithAtomEntry;

public abstract class ReviewActionEvent 
    extends EventWithAtomEntry<ReviewActionHandler, ReviewEntry> {

	// TODO consider moving up to EventWithAtomEntry
	@Override
	protected void dispatch(ReviewActionHandler handler) {
		handler.onAction(this);
	}

}
