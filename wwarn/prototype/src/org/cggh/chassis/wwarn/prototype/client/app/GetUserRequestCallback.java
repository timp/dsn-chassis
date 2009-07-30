/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.app;

import org.cggh.chassis.gwt.lib.log.client.GWTLogger;
import org.cggh.chassis.gwt.lib.log.client.Logger;
import org.cggh.chassis.gwt.lib.twisted.client.Deferred;
import org.cggh.chassis.wwarn.prototype.client.shared.User;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

/**
 * @author aliman
 *
 */
public class GetUserRequestCallback {


	private Logger log;
	private Model model;
	private Deferred<User> deferred;

	GetUserRequestCallback(Model model, Deferred<User> def) {
		this.model = model;
		this.deferred = def;
		this.log = new GWTLogger();
		this.initLog();
	}

	GetUserRequestCallback(Model model, Deferred<User> def, Logger log) {
		this.model = model;
		this.deferred = def;
		this.log = log;
		this.initLog();
	}

	private void initLog() {
		this.log.setCurrentClass(GetUserRequestCallback.class.getName());
	}

	public void onSuccess(User user) {
		log.enter("onSuccess");
		
		log.trace("set current user on model");
		this.model.setCurrentUser(user);
		
		if (deferred != null) {
			log.trace("deferred callback with user");
			deferred.callback(user);
		}
		
		log.leave();
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
