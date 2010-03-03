/**
 * 
 */
package org.cggh.chassis.generic.miniatom.client;

import org.cggh.chassis.generic.async.client.HttpCallbackBase;
import org.cggh.chassis.generic.async.client.HttpDeferred;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

/**
 * @author aliman
 *
 */
public class DeleteEntryCallback extends HttpCallbackBase implements RequestCallback {

	private HttpDeferred<Void> result;

	/**
	 * @param factory
	 * @param deferredResult
	 */
	public DeleteEntryCallback(HttpDeferred<Void> result) {
		super(result);
		this.result = result;
		this.expectedStatusCodes.add(204);
		this.expectedStatusCodes.add(1223); // workaround for ie7 quirk, see https://prototype.lighthouseapp.com/projects/8886/tickets/129-ie-mangles-http-response-status-code-204-to-1223
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
