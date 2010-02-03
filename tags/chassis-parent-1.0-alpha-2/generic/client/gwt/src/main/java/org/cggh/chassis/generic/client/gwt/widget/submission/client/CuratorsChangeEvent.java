/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.user.UserFeed;
import org.cggh.chassis.generic.widget.client.ModelChangeEvent;

/**
 * @author aliman
 *
 */
public class CuratorsChangeEvent 
	extends ModelChangeEvent<UserFeed, CuratorsChangeHandler> {

	public static final Type<CuratorsChangeHandler> TYPE = new Type<CuratorsChangeHandler>();
	
	public CuratorsChangeEvent(UserFeed before, UserFeed after) {
		super(before, after);
	}

	@Override
	protected void dispatch(CuratorsChangeHandler h) {
		h.onChange(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<CuratorsChangeHandler> getAssociatedType() {
		return TYPE;
	}
	
}
