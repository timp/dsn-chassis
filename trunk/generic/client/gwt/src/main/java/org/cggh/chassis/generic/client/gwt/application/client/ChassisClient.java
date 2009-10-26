/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsPubSubAPI;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;

import com.google.gwt.user.client.ui.Composite;

/**
 * @author aliman
 *
 */
public class ChassisClient extends Composite implements UserDetailsPubSubAPI {

	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private ChassisClientRenderer renderer;
	private ChassisClientController controller;
	private ChassisClientModel model;
	
	
	
	
	public ChassisClient() {
		log.enter("<constructor>");
		
		this.model = new ChassisClientModel();
		
		this.controller = new ChassisClientController(this, this.model);
		
		this.renderer = new ChassisClientDefaultRenderer(this, this.controller);
		
		this.model.addListener(this.renderer);
		
		this.initWidget(this.renderer.getCanvas());
		
		log.leave();
	}

	
	
	
	public void init() {
		log.enter("init");
		
		log.debug("register interest in user details events");
		this.renderer.getUserDetailsWidget().addListener(this);
		
		log.debug("refresh user details");
		Deferred<UserDetailsTO> deferredUser = this.renderer.getUserDetailsWidget().refreshUserDetails();
		ChassisUser.setCurrentUserDeferred(deferredUser);

		log.leave();
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsPubSubAPI#onCurrentRoleChanged(org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole)
	 */
	public void onCurrentRoleChanged(ChassisRole currentRole) {
		log.enter("onCurrentRoleChanged");
		
		log.debug("currentRole: "+currentRole.roleLabel);
		
		this.controller.switchPerspective(currentRole.roleId);

		log.leave();
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsPubSubAPI#onUserIdRefreshed(java.lang.String)
	 */
	public void onUserIdRefreshed(String userId) {
		log.enter("onUserIdRefreshed");
		
		log.debug("userId: "+userId);
		log.debug("set current user email on static class for global access");
		ChassisUser.setCurrentUserEmail(userId);
		
		log.debug("refresh perspectives");
		this.renderer.refreshPerspectives();
		
		log.leave();
	}
	
	
	
	
}
