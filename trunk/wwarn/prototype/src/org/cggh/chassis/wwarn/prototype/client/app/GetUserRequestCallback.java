/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.app;

import org.cggh.chassis.wwarn.prototype.client.shared.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

/**
 * @author aliman
 *
 */
public class GetUserRequestCallback {

	private void log(String message, String context) {
		String output = GetUserRequestCallback.class.getName() + " :: " + context + " :: " + message;
		GWT.log(output, null);
	}

	private Model model;
	private Controller controller;

	GetUserRequestCallback(Controller controller, Model model) {
		this.controller = controller;
		this.model = model;
	}

	public void onSuccess(User user) {
		String _ = "onSuccess";
		log("begin",_);
		
		log("set current user on model",_);
		this.model.setCurrentUser(user);
		
		log("end",_);
	}
	
	public void onFailure(Request req, Response res) {
		// TODO
	}
	
	public void onError(Request req, Throwable ex) {
		// TODO
	}

	public void onError(Request req, Response res, Throwable ex) {
		// TODO
	}
}
