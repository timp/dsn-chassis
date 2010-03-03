/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.exist;

import org.cggh.chassis.generic.async.client.HttpCallbackBase;
import org.cggh.chassis.generic.async.client.HttpDeferred;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

/**
 * @author aliman
 *
 */
public class XQueryRequestCallback extends HttpCallbackBase implements
		RequestCallback {

	private HttpDeferred<String> result;

	/**
	 * @param genericResult
	 */
	protected XQueryRequestCallback(HttpDeferred<String> result) {
		super(result);
		this.result = result;
		this.expectedStatusCodes.add(200);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.http.client.RequestCallback#onResponseReceived(com.google.gwt.http.client.Request, com.google.gwt.http.client.Response)
	 */
	public void onResponseReceived(Request request, Response response) {
		
		try {
			
			// check preconditions
			checkStatusCode(request, response);

			// pass through result
			this.result.callback(response.getText());
			
		} catch (Throwable t) {

			// pass through as error
			this.result.errback(t);

		}

	}

}
