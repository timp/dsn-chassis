/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.protocol.impl;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import org.cggh.chassis.generic.twisted.client.HttpCallbackBase;
import org.cggh.chassis.generic.twisted.client.HttpDeferred;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

/**
 * @author aliman
 *
 */
public class CallbackWithEntry extends HttpCallbackBase implements RequestCallback {

	private AtomFactory factory;
	private HttpDeferred<AtomEntry> result;

	/**
	 * @param factory
	 * @param result 
	 */
	public CallbackWithEntry(AtomFactory factory, HttpDeferred<AtomEntry> result) {
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
			AtomEntry entry = factory.createEntry(response.getText()); 
			
			// pass through result
			result.callback(entry);
			
		} catch (Throwable t) {

			// pass through as error
			result.errback(t);

		}

	}

}
