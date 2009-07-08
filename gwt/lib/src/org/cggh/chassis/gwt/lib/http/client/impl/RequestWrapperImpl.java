/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.http.client.impl;

import org.cggh.chassis.gwt.lib.http.client.RequestWrapper;

import com.google.gwt.http.client.Request;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class RequestWrapperImpl implements RequestWrapper {

	private Request wrappee;

	public RequestWrapperImpl(Request wrappee) {
		this.wrappee = wrappee;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.gwt.lib.http.client.RequestWrapper#getWrappee()
	 */
	public Request getWrappee() {
		return wrappee;
	}
}
