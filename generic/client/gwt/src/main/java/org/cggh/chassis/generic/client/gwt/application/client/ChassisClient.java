/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.NewUserDetailsWidget;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.NewUserDetailsWidgetModel.CurrentRoleChangeEvent;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.NewUserDetailsWidgetModel.CurrentRoleChangeHandler;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.NewUserDetailsWidgetModel.CurrentUserChangeEvent;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.NewUserDetailsWidgetModel.CurrentUserChangeHandler;
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
public class ChassisClient extends Composite /* implements UserDetailsPubSubAPI */ implements CurrentRoleChangeHandler, CurrentUserChangeHandler {

	
	
	
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
//		this.renderer.getUserDetailsWidget().addListener(this);
		
		NewUserDetailsWidget udw = this.renderer.getUserDetailsWidget();

		udw.addCurrentRoleChangeHandler(this);

		udw.addCurrentUserChangeHandler(this);
		
		log.debug("refresh user details");

//		Deferred<UserDetailsTO> deferredUser = this.renderer.getUserDetailsWidget().refreshUserDetails();
		Deferred<UserDetailsTO> deferredUser = this.renderer.getUserDetailsWidget().refreshCurrentUserDetails();

		ChassisUser.setCurrentUserDeferred(deferredUser);

		log.leave();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.userdetails.client.NewUserDetailsWidgetModel.CurrentRoleChangeHandler#onCurrentRoleChanged(org.cggh.chassis.generic.client.gwt.widget.userdetails.client.NewUserDetailsWidgetModel.CurrentRoleChangeEvent)
	 */
	public void onCurrentRoleChanged(CurrentRoleChangeEvent e) {
		log.enter("onCurrentRoleChanged");
		
		ChassisRole currentRole = e.getAfter();
		
		log.debug("currentRole: "+currentRole.roleLabel);
		
		this.controller.switchPerspective(currentRole.roleId);
		
		log.leave();
		
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.userdetails.client.NewUserDetailsWidgetModel.CurrentUserChangeHandler#onCurrentUserChanged(org.cggh.chassis.generic.client.gwt.widget.userdetails.client.NewUserDetailsWidgetModel.CurrentUserChangeEvent)
	 */
	public void onCurrentUserChanged(CurrentUserChangeEvent e) {
		log.enter("onCurrentUserChanged");
		
		UserDetailsTO user = e.getAfter();
		String userId = user.getId();
		
		log.debug("userId: "+userId);
		log.debug("set current user on static class for global access");
//		ChassisUser.setCurrentUserEmail(userId);
		ChassisUser.setCurrentUser(user);
		
		log.debug("refresh perspectives");
		this.renderer.refreshPerspectives();
		
		log.leave();
		
	}
	
	
	
	
//	/* (non-Javadoc)
//	 * @see org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsPubSubAPI#onCurrentRoleChanged(org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole)
//	 */
//	public void onCurrentRoleChanged(ChassisRole currentRole) {
//		log.enter("onCurrentRoleChanged");
//		
//		log.debug("currentRole: "+currentRole.roleLabel);
//		
//		this.controller.switchPerspective(currentRole.roleId);
//
//		log.leave();
//	}

	
	
	
//	/* (non-Javadoc)
//	 * @see org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsPubSubAPI#onUserIdRefreshed(java.lang.String)
//	 */
//	public void onUserIdRefreshed(String userId) {
//		log.enter("onUserIdRefreshed");
//		
//		log.debug("userId: "+userId);
//		log.debug("set current user email on static class for global access");
//		ChassisUser.setCurrentUserEmail(userId);
//		
//		log.debug("refresh perspectives");
//		this.renderer.refreshPerspectives();
//		
//		log.leave();
//	}
	
	
	
	
}
