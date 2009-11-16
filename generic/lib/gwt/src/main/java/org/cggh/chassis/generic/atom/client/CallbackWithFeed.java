/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import org.cggh.chassis.generic.async.client.HttpCallbackBase;
import org.cggh.chassis.generic.async.client.HttpDeferred;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

/**
 * @author aliman
 *
 */
public class CallbackWithFeed<E extends AtomEntry, F extends AtomFeed<E>> 
	extends HttpCallbackBase 
	implements RequestCallback {

	private AtomFactory<E, F> factory;
	private HttpDeferred<F> result;
	private Log log = LogFactory.getLog(this.getClass());

	/**
	 * @param factory
	 * @param result
	 */
	public CallbackWithFeed(AtomFactory<E, F> factory, HttpDeferred<F> result) {
		super(result);
		this.factory = factory;
		this.result = result;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.http.client.RequestCallback#onResponseReceived(com.google.gwt.http.client.Request, com.google.gwt.http.client.Response)
	 */
	public void onResponseReceived(Request request, Response response) {
		log.enter("onResponseReceived");

		super.onResponseReceived(request, response);

		try {

			// check preconditions
			checkResponsePreconditions(request, response);
			
			// parse the response
			F feed = this.factory.createFeed(this.responseText); 

			// pass through result
			this.result.callback(feed);
			
		} catch (Throwable t) {

			// pass through as error
			this.result.errback(t);

		}

		log.leave();
	}

}
