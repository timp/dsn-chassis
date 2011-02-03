/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.protocol.impl;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.twisted.client.Deferred;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

/**
 * @author aliman
 *
 */
public class CallbackWithFeed extends CallbackBase implements RequestCallback {

	private AtomFactory factory;
	private Deferred<AtomFeed> result;

	/**
	 * @param factory
	 * @param result
	 */
	public CallbackWithFeed(AtomFactory factory, Deferred<AtomFeed> result) {
		super(result);
		this.factory = factory;
		this.result = result;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.http.client.RequestCallback#onResponseReceived(com.google.gwt.http.client.Request, com.google.gwt.http.client.Response)
	 */
	public void onResponseReceived(Request request, Response response) {
		
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

	}

}