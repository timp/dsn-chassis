/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.protocol;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomEntry;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFactory;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFormatException;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class PutEntryResponseHandler implements RequestCallback {

	private AtomFactory factory;
	private PutEntryCallback callback;

	/**
	 * @param callback
	 * @param factory
	 */
	public PutEntryResponseHandler(PutEntryCallback callback, AtomFactory factory) {
		this.callback = callback;
		this.factory = factory;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.http.client.RequestCallback#onError(com.google.gwt.http.client.Request, java.lang.Throwable)
	 */
	public void onError(Request request, Throwable exception) {
		// simply pass through
		this.callback.onError(request, exception);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.http.client.RequestCallback#onResponseReceived(com.google.gwt.http.client.Request, com.google.gwt.http.client.Response)
	 */
	public void onResponseReceived(Request request, Response response) {

		// precondition: check status code
		if (response.getStatusCode() != 200) {
			// pass through to next callback
			this.callback.onFailure(request, response);
			return;
		}
		
		// precondition: check content type
		String contentType = response.getHeader("Content-Type");
		if (!contentType.startsWith("application/atom+xml") && !contentType.startsWith("application/xml")) {
			// pass through as error
			this.callback.onError(request, response, new Exception("expected content type starts with \"application/atom+xml\" or \"application/xml\", found \""+contentType+"\""));
			return;
		}
			
		AtomEntry entry = null;
		
		// parse the response
		try {

			entry = this.factory.createEntry(response.getText()); 
			
		} catch (AtomFormatException ex) {
			// pass through as error
			this.callback.onError(request, response, ex);
			return;
		}

		// pass through to next callback
		this.callback.onSuccess(request, response, entry);	
		
	}

}
