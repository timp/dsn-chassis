/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ModelChangeEvent;
import org.cggh.chassis.generic.widget.client.ModelChangeHandler;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class UserDetailsWidgetModel extends AsyncWidgetModel {

	
	
	
	private Log log;
	private UserDetailsWidget owner;
	
	
	
	
	// model properties
	private UserDetailsTO currentUser;
	private ChassisRole currentRole;





	
	
	
	/**
	 * @param owner
	 */
	public UserDetailsWidgetModel(UserDetailsWidget owner) {
		this.owner = owner;
	}

	
	
	
	
	@Override
	protected void init() {
		log = LogFactory.getLog(UserDetailsWidgetModel.class); // instantiate here because called from superclass constructor
		log.enter("init");
	
		log.debug("call init on super");
		super.init();
		
		log.debug("initialise model properties");
		this.currentUser = null;
		this.currentRole = null;
		
		log.leave();
	}
	
	



	public void setCurrentUser(UserDetailsTO currentUser, boolean fireEvent) {
		CurrentUserChangeEvent e = new CurrentUserChangeEvent(this.currentUser, currentUser);
		this.currentUser = currentUser;
		this.fireChangeEvent(e); // fire event locally so renderer will pick it up
		if (fireEvent) this.owner.fireEvent(e); // broadcast from widget so collaborators can be notified
	}




	public UserDetailsTO getCurrentUser() {
		return currentUser;
	}




	public void setCurrentRole(ChassisRole currentRole, boolean fireChangeEvent) {
		CurrentRoleChangeEvent e = new CurrentRoleChangeEvent(this.currentRole, currentRole);
		this.currentRole = currentRole;
		this.fireChangeEvent(e); // fire locally so renderer will pick it up and update UI
		if (fireChangeEvent) this.owner.fireEvent(e); // broadcast from widget
	}




	public ChassisRole getCurrentRole() {
		return currentRole;
	}

	
	
	
	
	public HandlerRegistration addCurrentUserChangeHandler(CurrentUserChangeHandler h) {
		return this.addChangeHandler(h, CurrentUserChangeEvent.TYPE);
	}
	
	
	
	
	
	public HandlerRegistration addCurrentRoleChangeHandler(CurrentRoleChangeHandler h) {
		return this.addChangeHandler(h, CurrentRoleChangeEvent.TYPE);
	}
	
	
	
	
	
	public static class CurrentUserChangeEvent extends ModelChangeEvent<UserDetailsTO, CurrentUserChangeHandler> {

		public static final Type<CurrentUserChangeHandler> TYPE = new Type<CurrentUserChangeHandler>();
			
		public CurrentUserChangeEvent(UserDetailsTO before, UserDetailsTO after) { super(before, after); }

		@Override
		protected void dispatch(CurrentUserChangeHandler handler) { handler.onCurrentUserChanged(this); }

		@Override
		public Type<CurrentUserChangeHandler> getAssociatedType() { return TYPE; }
		
	}
	

	
	public interface CurrentUserChangeHandler extends ModelChangeHandler {
		
		public void onCurrentUserChanged(CurrentUserChangeEvent e);

	}
	
	
	
	
	public static class CurrentRoleChangeEvent extends ModelChangeEvent<ChassisRole, CurrentRoleChangeHandler> {

		public static final Type<CurrentRoleChangeHandler> TYPE = new Type<CurrentRoleChangeHandler>();
			
		public CurrentRoleChangeEvent(ChassisRole before, ChassisRole after) { super(before, after); }

		@Override
		protected void dispatch(CurrentRoleChangeHandler handler) { handler.onCurrentRoleChanged(this); }

		@Override
		public Type<CurrentRoleChangeHandler> getAssociatedType() { return TYPE; }
		
	}
	
	
	
	
	public static interface CurrentRoleChangeHandler extends ModelChangeHandler {
		
		public void onCurrentRoleChanged(CurrentRoleChangeEvent e);

	}
	
	

	
}
