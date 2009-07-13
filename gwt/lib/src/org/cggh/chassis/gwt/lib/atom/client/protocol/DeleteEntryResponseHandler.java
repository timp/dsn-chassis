/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.protocol;


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


	/**
	 * @param callback
	 * @param factory
	 */
	public DeleteEntryResponseHandler(DeleteEntryCallback callback) {
		this.callback = callback;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.http.client.RequestCallback#onError(com.google.gwt.http.client.Request, java.lang.Throwable)
	 */
	public void onError(Request request, Throwable exception) {
		// simply pass through
		this.callback.onError(request, exception);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.http.client.RequestCallback#onResponseReceived(com.google.gwt.http.client.Request, com.google.gwt.http.client.Response)
	 */
	public void onResponseReceived(Request request, Response response) {

		// precondition: check status code
		if (response.getStatusCode() != 204) {
			// pass through to next callback
			this.callback.onFailure(request, response);
			return;
		}
		
		// pass through to next callback
		this.callback.onSuccess(request, response);	
	}

}
