package org.cggh.chassis.gwt.lib.atom.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public interface CallbackWithAtomFeed<F extends AtomFeed> {
	
	public void onSuccess(Request request, Response response, F feed);
	public void onFailure(Request request, Response response);
	public void onError(Request request, Throwable exception);
	public void onError(Request request, Response response, Throwable exception);

}