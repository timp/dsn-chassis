/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.app;

import org.cggh.chassis.wwarn.prototype.client.shared.HMVCComponent;
import org.cggh.chassis.wwarn.prototype.client.shared.HMVCHistoryManager;
import org.cggh.chassis.wwarn.prototype.client.shared.Logger;
import org.cggh.chassis.wwarn.prototype.client.shared.User;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;

/**
 * @author aliman
 *
 */
public class HistoryManager<I> extends HMVCHistoryManager implements ModelListener, ValueChangeHandler<I> {

	
	
	
	public static final String TOKEN_UNAUTHORISED = "unauthorised";
	
	
	
	protected Controller controller;


	
	HistoryManager(HMVCComponent owner) {
		super(owner);
		this.log.setCurrentClass(HistoryManager.class.getName());
	}


	
	
	HistoryManager(HMVCComponent owner, Logger log) {
		super(owner, log);
		this.log.setCurrentClass(HistoryManager.class.getName());
	}


	
	
	HistoryManager(HMVCComponent owner, Controller controller) {
		super(owner);
		this.controller = controller;
		this.log.setCurrentClass(HistoryManager.class.getName());
	}


	
	
	HistoryManager(HMVCComponent owner, Controller controller, Logger log) {
		super(owner, log);
		this.controller = controller;
		this.log.setCurrentClass(HistoryManager.class.getName());
	}


	
	
	/*
	 * -------------------------------------------------------------------------
	 * METHODS LISTENING FOR CHANGES TO MODEL AND UPDATING HISTORY TOKEN
	 * -------------------------------------------------------------------------
	 */
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.app.ModelListener#onCurrentRoleChanged(java.lang.String, java.lang.String)
	 */
	public void onCurrentRoleChanged(String oldRole, String newRole) {
		String historyToken = this.getHistoryTokenForNewLocalStateToken(History.getToken(), newRole);
		if (!historyToken.equals(History.getToken())) {
			History.newItem(historyToken);
		}
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.app.ModelListener#onCurrentUserChanged(org.cggh.chassis.wwarn.prototype.client.shared.User, org.cggh.chassis.wwarn.prototype.client.shared.User)
	 */
	public void onCurrentUserChanged(User oldUser, User newUser) {

		String state = this.getLocalStateTokenFromHistoryToken(History.getToken()); 
		// assume state is simply current role name
		
		if (newUser.getRoleNames().contains(state)) {
			// user has current role, fire history event as is
			History.fireCurrentHistoryState();
		}
		else if (newUser.getRoleNames().size() > 0) {
			// coerce history token based on default role
			String defaultRole = newUser.getRoleNames().get(0); // assume default is first role for now
			String historyToken = defaultRole;
			History.newItem(historyToken);
		}
		else {
			// user has no roles
			String historyToken = this.getHistoryTokenForNewLocalStateToken(History.getToken(), TOKEN_UNAUTHORISED);
			History.newItem(historyToken);
		}
		
	}

	
	
	
	/*
	 * -------------------------------------------------------------------------
	 * METHOD LISTENING FOR CHANGES TO HISTORY TOKEN & MAP TO CONTROLLER ACTIONS
	 * -------------------------------------------------------------------------
	 */

	
	
	
	public void onValueChange(ValueChangeEvent<I> event) {
		log.setCurrentMethod("onValueChange");
		log.info("begin");
		
		String historyToken = event.getValue().toString();
		log.info("historyToken: "+historyToken);
		
		String state = this.getLocalStateTokenFromHistoryToken(historyToken);
		log.info("found local state token: "+state);
		
		// assume state is name of role
		log.info("set current role");
		this.controller.setCurrentRole(state);

		log.info("return");
	}
	
	
	
	
}
