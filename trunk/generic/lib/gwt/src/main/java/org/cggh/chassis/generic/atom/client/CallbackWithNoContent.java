/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import org.cggh.chassis.generic.async.client.HttpCallbackBase;
import org.cggh.chassis.generic.async.client.HttpDeferred;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

/**
 * @author aliman
 *
 */
public class CallbackWithNoContent extends HttpCallbackBase implements RequestCallback {

	private HttpDeferred<Void> result;

	/**
	 * @param factory
	 * @param deferredResult
	 */
	public CallbackWithNoContent(HttpDeferred<Void> result) {
		super(result);
		this.result = result;
		this.expectedStatusCodes.add(204);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.http.client.RequestCallback#onResponseReceived(com.google.gwt.http.client.Request, com.google.gwt.http.client.Response)
	 */
	public void onResponseReceived(Request request, Response response) {

		super.onResponseReceived(request, response);

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
