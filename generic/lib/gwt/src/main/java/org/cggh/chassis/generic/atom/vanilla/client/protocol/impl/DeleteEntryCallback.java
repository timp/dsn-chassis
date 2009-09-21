/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.protocol.impl;

import org.cggh.chassis.generic.twisted.client.Deferred;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

/**
 * @author aliman
 *
 */
public class DeleteEntryCallback extends CallbackBase implements RequestCallback {

	private Deferred<Void> result;

	/**
	 * @param factory
	 * @param deferredResult
	 */
	public DeleteEntryCallback(Deferred<Void> result) {
		super(result);
		this.result = result;
		this.expectedStatusCodes.add(204);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.http.client.RequestCallback#onResponseReceived(com.google.gwt.http.client.Request, com.google.gwt.http.client.Response)
	 */
	public void onResponseReceived(Request request, Response response) {

		try {
			
			// check preconditions
			checkStatusCode(request, response);

			// pass through result
			this.result.callback(null);
			
		} catch (Throwable t) {

			// pass through as error
			this.result.errback(t);

		}

	}

}
