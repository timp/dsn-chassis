/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.protocol.impl;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.HttpDeferred;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

/**
 * @author aliman
 *
 */
public class CallbackWithFeed extends CallbackBase implements RequestCallback {

	private AtomFactory factory;
	private HttpDeferred<AtomFeed> result;
	private Log log = LogFactory.getLog(this.getClass());

	/**
	 * @param factory
	 * @param result
	 */
	public CallbackWithFeed(AtomFactory factory, HttpDeferred<AtomFeed> result) {
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
			AtomFeed feed = this.factory.createFeed(response.getText()); 

			// pass through result
			this.result.callback(feed);
			
		} catch (Throwable t) {

			// pass through as error
			this.result.errback(t);

		}

		log.leave();
	}

}
