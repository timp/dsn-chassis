/**
 * $Id$
 */
package org.cggh.chassis.examples.atom.client;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomFeed;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

public class ControllerGetFeedCallback implements org.cggh.chassis.gwt.lib.atom.client.protocol.GetFeedCallback {

	/**
	 * 
	 */
	private final Model model;

	/**
	 * @param controller
	 */
	ControllerGetFeedCallback(Model model) {
		this.model = model;
	}

	public void onError(Request request, Throwable exception) {
		this.model.setMessage("unexpected error [onError(Request, Throwable)]: "+exception.getLocalizedMessage());
	}

	public void onError(Request request, Response response, Throwable exception) {
		this.model.setMessage("unexpected error [onError(Request, Response, Throwable)]: "+exception.getLocalizedMessage());
	}

	public void onFailure(Request request, Response response) {
		this.model.setMessage("the request failed: "+response.getStatusCode()+"\n\n"+response.getText());
	}

	public void onSuccess(Request request, Response response, AtomFeed newFeed) {
		this.model.setFeed(newFeed);
		this.model.setMessage("request success");
	}
	
}