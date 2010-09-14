/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author aliman
 *
 */
public class ErrorEvent extends GwtEvent<ErrorHandler> {
	
	public static final Type<ErrorHandler> TYPE = new Type<ErrorHandler>();
	private Throwable exception;
	private String message;
	
	public ErrorEvent() {}
	
	public ErrorEvent(Throwable t) {
		this.exception = t;
		this.message = t.getMessage();
	}
	
	/**
	 * @param string
	 */
	public ErrorEvent(String message) {
		this.setMessage(message);
	}

	public Throwable getException() {
		return this.exception;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(ErrorHandler handler) {
		handler.onError(this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<ErrorHandler> getAssociatedType() {
		return TYPE;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
	
}
