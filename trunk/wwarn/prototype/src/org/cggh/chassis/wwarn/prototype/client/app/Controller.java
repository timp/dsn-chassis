package org.cggh.chassis.wwarn.prototype.client.app;



import com.google.gwt.core.client.GWT;



class Controller {

	
	
	private Model model = null;
	private Application owner = null;

	
	
	private void log(String message, String context) {
		String output = Controller.class.getName() + " :: " + context + " :: " + message;
		GWT.log(output, null);
	}

	
	
	Controller(Application owner, Model model) {
		this.owner = owner;
		this.model = model;
	}
	
	
	
	void refreshUserDetails() { String _ = "refreshUserDetails"; 
		log("begin",_);
		
		log("create callback",_);
		GetUserRequest r = new GetUserRequest("/user");
		r.setCallback(new GetUserRequestCallback(this, this.model));
		
		log("send get user request",_);
		r.send();
		
		log("end",_);
	}

	

	void setCurrentRole(String roleName) {
		String _ = "setCurrentRole"; log("begin",_);

		this.model.setCurrentRole(roleName);

		log("end",_);
	}

}
