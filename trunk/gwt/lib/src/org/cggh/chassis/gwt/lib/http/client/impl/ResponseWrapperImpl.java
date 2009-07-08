/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.http.client.impl;

import org.cggh.chassis.gwt.lib.http.client.ResponseWrapper;

import com.google.gwt.http.client.Response;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class ResponseWrapperImpl implements ResponseWrapper {

	private Response wrappee;

	public ResponseWrapperImpl(Response wrappee) {
		this.wrappee = wrappee;
	}
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.gwt.lib.http.client.ResponseWrapper#getStatusCode()
	 */
	public int getStatusCode() {
		return wrappee.getStatusCode();
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.gwt.lib.http.client.ResponseWrapper#getHeader(java.lang.String)
	 */
	public String getHeader(String header) {
		return wrappee.getHeader(header);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.gwt.lib.http.client.ResponseWrapper#getWrappee()
	 */
	public Response getWrappee() {
		return wrappee;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.gwt.lib.http.client.ResponseWrapper#getText()
	 */
	public String getText() {
		return wrappee.getText();
	}

}
