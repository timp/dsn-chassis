package org.cggh.chassis.wwarn.prototype.client.app;



import org.cggh.chassis.gwt.lib.log.client.GWTLogger;
import org.cggh.chassis.gwt.lib.log.client.Logger;
import org.cggh.chassis.gwt.lib.twisted.client.Deferred;
import org.cggh.chassis.gwt.lib.twisted.client.Function;
import org.cggh.chassis.wwarn.prototype.client.user.RoleNames;
import org.cggh.chassis.wwarn.prototype.client.user.User;
import org.cggh.chassis.wwarn.prototype.client.user.UserService;



class Controller {

	
	
	private Model model = null;
	private Application owner = null;
	private Logger log;

	
	
	
	Controller(Application owner, Model model) {
		this.owner = owner;
		this.model = model;
		this.log = new GWTLogger();
		this.initLog();
	}
	
	
	
	
	Controller(Application owner, Model model, Logger log) {
		this.owner = owner;
		this.model = model;
		this.log = log;
		this.initLog();
	}
	
	
	
	private void initLog() {
		this.log.setCurrentClass(Controller.class.getName());
	}



	Deferred<User> refreshUserDetails() { 
		log.enter("refreshUserDetails"); 
		
		log.trace("create user service");
		UserService service = new UserService("/user"); // TODO configurable endpoint URL
		
		log.trace("request user details");
		Deferred<User> def = service.getAuthenticatedUser();
		
		log.trace("add callback for user details");
		def.addCallback(new Function<User,User>() {
			public User apply(User user) {
				log.enter("[anonymous Function<User,User>] :: apply");
				log.trace("set current user on model");
				model.setCurrentUser(user);
				log.leave();
				return user;
			}
		});
		
		log.leave();
		return def;
	}

	
	
	void setCurrentRole(String roleName) {
		this.setCurrentRole(roleName, true);
	}
	

	void setCurrentRole(String roleName, boolean waypoint) {
		log.enter("setCurrentRole"); 
		log.trace("roleName: "+roleName+"; waypoint: "+waypoint);

		log.trace("check new role is applicable to current user");
		
		if (this.model.getCurrentUser().getRoleNames().contains(roleName)) {

			log.trace("user has role, set current role on model");
			this.model.setCurrentRole(roleName);

			if (waypoint) {
				log.trace("set waypoint");
				this.owner.waypoint();
			}
			else {
				log.trace("silently sync state key");
				this.owner.syncStateKey();
			}
			
		}
		else {
			log.trace("user does not have that role");
			// TODO anything?
		}

		log.leave();
	}




	void setDefault() {
		
		String role = null;
		
		User user = this.model.getCurrentUser();
		if (user.getRoleNames().size() > 0) {
			role = user.getRoleNames().get(0); // use first as default
		}
		else {
			role = RoleNames.UNAUTHORISED;
		}

		this.setCurrentRole(role, false);
		
	}

}
