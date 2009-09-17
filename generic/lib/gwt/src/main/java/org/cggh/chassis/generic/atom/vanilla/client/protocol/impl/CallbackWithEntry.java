/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.protocol.impl;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

/**
 * @author aliman
 *
 */
public class CallbackWithEntry extends CallbackBase implements RequestCallback {

	private AtomFactory factory;
	private Deferred<AtomEntry> result;

	/**
	 * @param factory
	 * @param result 
	 */
	public CallbackWithEntry(AtomFactory factory, Deferred<AtomEntry> result) {
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
			AtomEntry entry = this.factory.createEntry(response.getText()); 
			
			// pass through result
			this.result.callback(entry);
			
		} catch (Throwable t) {

			// pass through as error
			this.result.errback(t);

		}

	}

}
