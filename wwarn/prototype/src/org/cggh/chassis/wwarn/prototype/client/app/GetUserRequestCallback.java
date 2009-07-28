/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.app;

import org.cggh.chassis.wwarn.prototype.client.shared.GWTLogger;
import org.cggh.chassis.wwarn.prototype.client.shared.Logger;
import org.cggh.chassis.wwarn.prototype.client.shared.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

/**
 * @author aliman
 *
 */
public class GetUserRequestCallback {


	private Logger log;
	private Model model;
	private Controller controller;
	private Application owner;

	GetUserRequestCallback(Application owner, Controller controller, Model model) {
		this.owner = owner;
		this.controller = controller;
		this.model = model;
		this.log = new GWTLogger();
		this.initLog();
	}

	GetUserRequestCallback(Application owner, Controller controller, Model model, Logger log) {
		this.owner = owner;
		this.controller = controller;
		this.model = model;
		this.log = log;
		this.initLog();
	}

	private void initLog() {
		this.log.setCurrentClass(GetUserRequestCallback.class.getName());
	}

	public void onSuccess(User user) {
		log.setCurrentMethod("onSuccess");
		log.info("begin");
		
		log.info("set current user on model");
		this.model.setCurrentUser(user);
		
		log.info("check if this is being done as part of initialisation");
		if (!this.model.getInitialisationComplete()) {
			log.info("setting initialisation complete with success");
			this.model.setInitialisationComplete(true);
			this.model.setInitialisationSuccess(true);
			this.owner.fireInitialisationSuccess();
		}

		log.info("return");
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
