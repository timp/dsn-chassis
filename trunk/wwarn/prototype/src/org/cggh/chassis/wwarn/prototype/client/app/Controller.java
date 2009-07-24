package org.cggh.chassis.wwarn.prototype.client.app;


import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.wwarn.prototype.client.curator.CuratorPerspective;
import org.cggh.chassis.wwarn.prototype.client.shared.Perspective;
import org.cggh.chassis.wwarn.prototype.client.shared.RoleNames;
import org.cggh.chassis.wwarn.prototype.client.submitter.SubmitterPerspective;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;

class Controller {

	private Model model;

	private void log(String message, String context) {
		String output = Controller.class.getName() + " :: " + context + " :: " + message;
		GWT.log(output, null);
	}

	Controller(Model model) {
		this.model = model;
	}
	
	void setStateToken(String stateToken) {
		String _ = "setStateToken("+stateToken+")";
		log("begin",_);

		log("set field on model",_);
		this.model.setStateToken(stateToken);
		
		boolean foundPerspective = false;
		
		for (Perspective p : this.model.getPerspectives()) {
			
			if (stateToken.startsWith(p.getTokenBase())) {
				foundPerspective = true;
				
				log("set current perspective: "+p.getName(),_);
				this.setCurrentPerspective(p.getName());

				log("pass state change on to perspective",_);
				p.setStateToken(stateToken);
			}

		}
		
		if (!foundPerspective) {
			if (stateToken.equals(Application.TOKEN_UNAUTHORISED)) {
				this.notAuthorised();
			}
			else {
				this.notFound(stateToken);
			}
		}

		log("end",_);
	}
	
	
	
	private void notAuthorised() {
		String _ = "notAuthorised";
		log("begin",_);
		
		// TODO
		
		log("end",_);
	}

	void notFound(String stateToken) {
		String _ = "notFound("+stateToken+")";
		log("begin",_);
		
		// TODO
		
		log("end",_);
	}

	
	
	void setCurrentPerspective(String perspectiveName) {
		for (Perspective p : this.model.getPerspectives()) {
			if (p.getName().equals(perspectiveName)) {
				this.model.setCurrentPerspective(p);
			}
		}
	}

	
	
	void init() {
		String _ = "init";
		log("begin",_);
		
		log("get & store user details",_);
		this.initUser();
		
		log("end",_);
	}

	
	
	void initUser() {
		String _ = "initUser";
		log("begin",_);

		GetUserRequest r = new GetUserRequest("/user");
		r.setCallback(new GetUserRequestCallback(this, this.model));
		
		log("send get user request",_);
		r.send();
		
		log("end",_);
	}
	
	

	/**
	 * Initialise perspectives based on user roles.
	 */
	void initPerspectives() {
		String _ = "initPerspectives";
		log("begin",_);
		
		List<String> roles = this.model.getCurrentUser().getRoles();
		
		List<Perspective> perspectives = new ArrayList<Perspective>();
		
		if (roles.contains(RoleNames.SUBMITTER)) {
			log("add submitter perspective",_);
			perspectives.add(new SubmitterPerspective());
		}
		
		if (roles.contains(RoleNames.CURATOR)) {
			log("add curator perspective",_);
			perspectives.add(new CuratorPerspective());
		}
		
		// TODO other perspectives

		log("set perspectives on model",_);
		this.model.setPerspectives(perspectives);
		
		log("end",_);
	}
	
	
	
	/**
	 * Check history, if no history token, redirect to a new home state, 
	 * depending on user roles.
	 */
	void initState() {
		String _ = "initState";
		log("begin",_);
				
	    String initToken = History.getToken();
	    
	    // if no init token, set one based on user roles
	    if (initToken.length() == 0) {
	    	log("history token found empty",_);
	    	this.initHistoryNoToken();
	    }

	    // else if init token, check token is consistent with available perspectives
	    else {
	    	log("history token found: "+initToken,_);
	    	this.initHistoryWithToken(initToken);
	    }

		log("end",_);
	}

	
	
	/** 
	 * Initialise history, where token is already present.
	 * 
	 * @param initToken
	 * @param newUser
	 */
	private void initHistoryWithToken(String initToken) {
		String _ = "initHistoryWithToken("+initToken+")";
		log("begin",_);
		
		log("firing current history state",_);
		History.fireCurrentHistoryState(); 
		
		// better to do this at setStateToken...
		
//		// is perspective available for history token?
//		
//    	boolean goahead = false;
//    	for (Perspective p : this.model.getPerspectives()) {
//    		if (initToken.startsWith(p.getTokenBase())) {
//    			goahead = true;
//    		}
//    	}
//    	
//    	if (goahead) {
//    		log("found matching perspective, firing current history state",_);
//    		History.fireCurrentHistoryState(); // can go ahead with current state
//    	}
//    	
//    	else {
//    		log("found no matching perspective, initialise as if no token",_);
//    		this.initHistoryNoToken();
//    	}

		log("end",_);
	}

	private void initHistoryNoToken() {
		String _ = "initHistoryNoToken";
		log("begin",_);

		String token = null;
		
		if (this.model.getPerspectives().size() > 0) {
			token = this.model.getPerspectives().get(0).getHomeToken(); // take first perspective as default
		}
		else { // user has no perspectives
    		token = Application.TOKEN_UNAUTHORISED;
    	}
    	
		log("adding history item: "+token,_);
    	History.newItem(token);

    	log("end",_);
	}

}
