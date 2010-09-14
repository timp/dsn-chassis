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
public class CallbackWithEntry<E extends AtomEntry, F extends AtomFeed<E>> 
	extends HttpCallbackBase 
	implements RequestCallback {

	private AtomFactory<E, F> factory;
	private HttpDeferred<E> result;

	/**
	 * @param factory
	 * @param result 
	 */
	public CallbackWithEntry(AtomFactory<E, F> factory, HttpDeferred<E> result) {
		super(result);
		this.factory = factory;
		this.result = result;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.http.client.RequestCallback#onResponseReceived(com.google.gwt.http.client.Request, com.google.gwt.http.client.Response)
	 */
	public void onResponseReceived(Request request, Response response) {

		super.onResponseReceived(request, response);
		
		try {
			
			// check preconditions
			checkResponsePreconditions(request, response);

			// parse the response
			E entry = factory.createEntry(this.responseText); 
			
			// pass through result
			result.callback(entry);
			
		} catch (Throwable t) {

			// pass through as error
			result.errback(t);

		}

	}

}
