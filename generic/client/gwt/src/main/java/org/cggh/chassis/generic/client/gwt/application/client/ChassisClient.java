/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import java.util.HashMap;
import java.util.Map;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidget;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetModel.CurrentRoleChangeEvent;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetModel.CurrentRoleChangeHandler;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetModel.CurrentUserChangeEvent;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetModel.CurrentUserChangeHandler;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.WidgetMemory;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class ChassisClient extends ChassisWidget {

	
	
	
	// utility fields
	private Log log;
	
	
	

	// UI fields
	private UserDetailsWidget userDetailsWidget;
	private FlowPanel perspectivesContainer;

	
	
	
	// state fields
	private Map<Integer, ChassisWidget> perspectives;




	private Deferred<UserDetailsTO> currentUserDeferred;

	
	
	



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");
		
		this.perspectives = new HashMap<Integer, ChassisWidget>();
		this.memory = new Memory();

		log.leave();
	}

	
	
	
	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(ChassisClient.class);
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");

		this.userDetailsWidget = new UserDetailsWidget();		
		this.add(this.userDetailsWidget);
		
		log.debug("refresh current user details");
		this.currentUserDeferred = this.userDetailsWidget.refreshCurrentUserDetails();

		this.perspectivesContainer = new FlowPanel();
		this.add(this.perspectivesContainer);

		log.leave();
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#bindUI()
	 */
	@Override
	protected void bindUI() {

		HandlerRegistration a = this.userDetailsWidget.addCurrentUserChangeHandler(new CurrentUserChangeHandler() {
			
			public void onCurrentUserChanged(CurrentUserChangeEvent e) {

				updatePerspectives(e.getAfter());
				
			}
		});

		HandlerRegistration b = this.userDetailsWidget.addCurrentRoleChangeHandler(new CurrentRoleChangeHandler() {
			
			public void onCurrentRoleChanged(CurrentRoleChangeEvent e) {
				
				updateCurrentPerspective(e.getAfter());
				
			}
		});
		
		this.childWidgetEventHandlerRegistrations.add(a);
		this.childWidgetEventHandlerRegistrations.add(b);
	}

	
	
	
	/**
	 * @param user
	 */
	protected void updatePerspectives(UserDetailsTO user) {
		log.enter("updatePerspectives");

		this.perspectivesContainer.clear();
		this.perspectives.clear();
		
		for (ChassisRole role : ChassisRole.getRoles(user)) {

			ChassisWidget perspective = null;
			
			if (role.equals(Configuration.getChassisRoleAdministrator())) {
				
				log.enter("adding administrator perspective");
				perspective = new AdministratorPerspective();

			}
			else if (role.equals(Configuration.getChassisRoleSubmitter())) {

				log.enter("adding submitter perspective");
				perspective = new SubmitterPerspective();

			}

			if (perspective != null) {
				this.perspectivesContainer.add(perspective);
				this.perspectives.put(role.roleId, perspective);		
			}
			
		}
		
		log.leave();
	}





	/**
	 * @param currentRole
	 */
	protected void updateCurrentPerspective(ChassisRole currentRole) {
		log.enter("updateCurrentPerspective");
		
		for (Widget w : this.perspectives.values()) {
			w.setVisible(false);
		}
		
		if (currentRole != null) {

			log.debug("currentRole: "+currentRole.roleLabel);
			ChassisWidget w = this.perspectives.get(currentRole.roleId);

			if (w != null && !w.isVisible()) {
				
				log.debug("setting active widget visible");
				w.setVisible(true);
				
				log.debug("setting memory child");
				this.memory.setChild(w.getMemory());
				
				log.debug("memorising");
				this.memory.memorise();
				
			}
			else {
				
				log.debug("setting memory child null");
				this.memory.setChild(null); // is this necessary?
				
			}
		}
		
		log.leave();
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	protected void syncUI() {

		UserDetailsTO user = this.userDetailsWidget.getCurrentUser();
		this.updatePerspectives(user);
		
		ChassisRole role = this.userDetailsWidget.getCurrentRole();
		this.updateCurrentPerspective(role);

	}

	
	
	
	
	/**
	 * @author aliman
	 *
	 */
	public class Memory extends WidgetMemory {
		private Log log = LogFactory.getLog(Memory.class);

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#createMnemonic()
		 */
		@Override
		public String createMnemonic() {
			log.enter("createMnemonic");

			String mnemonic = null;
			
			if (userDetailsWidget != null) {
				ChassisRole currentRole = userDetailsWidget.getCurrentRole();
				if (currentRole != null) {
					mnemonic = currentRole.roleLabel.toLowerCase();
				}
			}
			
			log.debug("mnemonic: "+mnemonic);

			log.leave();
			return mnemonic;
		}
		
		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#remember(java.lang.String)
		 */
		@Override
		public Deferred<WidgetMemory> remember(final String mnemonic) {
			log.enter("remember");

			log.debug("mnemonic: "+mnemonic);
			
			final WidgetMemory self = this;
			final Deferred<WidgetMemory> deferredSelf = new Deferred<WidgetMemory>();
			
			Function<UserDetailsTO, UserDetailsTO> remember = new Function<UserDetailsTO, UserDetailsTO>() {

				public UserDetailsTO apply(UserDetailsTO in) {
					for (ChassisRole role : ChassisRole.getRoles(in)) {
						if (mnemonic != null && mnemonic.equals(role.roleLabel.toLowerCase())) {
							userDetailsWidget.setCurrentRole(role, false); // could this happen more than once?
						}
					}
					deferredSelf.callback(self); 
					return in;
				}
				
			};
			
			UserDetailsTO user = userDetailsWidget.getCurrentUser();
			
			if (user != null) {

				// remember immediately
				remember.apply(user);

			}
			else {
				
				// refresh user details is in progress, will need to wait
				// before remembering
				currentUserDeferred.addCallback(remember);
				
			}
			
			log.leave();
			return deferredSelf;
		}

	}


}
