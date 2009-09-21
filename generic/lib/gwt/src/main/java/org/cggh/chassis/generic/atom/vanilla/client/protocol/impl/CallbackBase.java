/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.protocol.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomProtocolException;
import org.cggh.chassis.generic.twisted.client.Deferred;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

/**
 * @author aliman
 *
 */
public class CallbackBase {

	protected Set<Integer> expectedStatusCodes = new HashSet<Integer>();
	
	@SuppressWarnings("unchecked")
	private Deferred genericResult;
	
	@SuppressWarnings("unchecked")
	protected CallbackBase(Deferred genericResult) {
		this.genericResult = genericResult;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.http.client.RequestCallback#onError(com.google.gwt.http.client.Request, java.lang.Throwable)
	 */
	public void onError(Request request, Throwable exception) {
		this.genericResult.errback(exception);
	}

	protected void checkResponsePreconditions(Request request, Response response) throws AtomProtocolException {

		// precondition: check status code
		checkStatusCode(request, response);
		
		// precondition: check content type
		checkContentType(request, response);
		
	}
	
	protected void checkStatusCode(Request request, Response response) throws AtomProtocolException {

		// precondition: check status code
		int statusCode = response.getStatusCode();
		if ( ! this.expectedStatusCodes.contains(statusCode) ) {
			throw new AtomProtocolException("bad status code, expected one of "+formatExpectedStatusCodes()+", found "+statusCode, request, response);
		}
		
	}
	
	protected void checkContentType(Request request, Response response) throws AtomProtocolException {

		// precondition: check content type
		String contentType = response.getHeader("Content-Type");
		if (!contentType.startsWith("application/atom+xml") && !contentType.startsWith("application/xml")) {
			throw new AtomProtocolException("bad content type, expected content type starts with \"application/atom+xml\" or \"application/xml\", found \""+contentType+"\"", request, response);
		}

	}
	
	protected String formatExpectedStatusCodes() {
		String output = "{";
		for (Iterator<Integer> it = expectedStatusCodes.iterator(); it.hasNext(); ) {
			output += it.next().toString();
			if (it.hasNext()) {
				output += ", ";
			}
		}
		output += "}";
		return output;
	}

}
