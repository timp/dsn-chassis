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
public class XQueryRequestCallback extends CallbackBase implements
		RequestCallback {

	private Deferred<String> result;

	/**
	 * @param genericResult
	 */
	protected XQueryRequestCallback(Deferred<String> result) {
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
