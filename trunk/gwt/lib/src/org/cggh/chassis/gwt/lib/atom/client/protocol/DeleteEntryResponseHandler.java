/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.protocol;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomFactory;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class DeleteEntryResponseHandler implements RequestCallback {

	private DeleteEntryCallback callback;
	private AtomFactory factory;

	/**
	 * @param callback
	 * @param factory
	 */
	public DeleteEntryResponseHandler(DeleteEntryCallback callback, AtomFactory factory) {
		this.callback = callback;
		this.factory = factory;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.http.client.RequestCallback#onError(com.google.gwt.http.client.Request, java.lang.Throwable)
	 */
	public void onError(Request request, Throwable exception) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.google.gwt.http.client.RequestCallback#onResponseReceived(com.google.gwt.http.client.Request, com.google.gwt.http.client.Response)
	 */
	public void onResponseReceived(Request request, Response response) {
		// TODO Auto-generated method stub

	}

}
