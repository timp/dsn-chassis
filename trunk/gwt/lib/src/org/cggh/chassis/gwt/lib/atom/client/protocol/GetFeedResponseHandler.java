/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.protocol;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomFactory;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFeed;
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
public class GetFeedResponseHandler implements RequestCallback {

	private GetFeedCallback next;
	private AtomFactory factory;

	protected GetFeedResponseHandler(GetFeedCallback next, AtomFactory factory) {
		this.next = next;
		this.factory = factory;
	}

	public void onError(Request request, Throwable exception) {
		// pass through to next callback
		next.onError(request, exception);
	}

	public void onResponseReceived(Request request, Response response) {

		// precondition: check status code
		if (response.getStatusCode() != 200) {
			// pass through to next callback
			next.onFailure(request, response);
			return;
		}
		
		// precondition: check content type
		String contentType = response.getHeader("Content-Type");
		if (!contentType.startsWith("application/atom+xml") && !contentType.startsWith("application/xml")) {
			// pass through as error
			next.onError(request, response, new Exception("expected content type starts with \"application/atom+xml\" or \"application/xml\", found \""+contentType+"\""));
			return;
		}
			
		AtomFeed feed = null;
		
		// parse the response
		try {

			feed = this.factory.createFeed(response.getText()); 
			
		} catch (AtomFormatException ex) {
			// pass through as error
			next.onError(request, response, ex);
			return;
		}

		// pass through to next callback
		next.onSuccess(request, response, feed);		
		
	}		
}
