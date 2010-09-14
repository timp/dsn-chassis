/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import java.util.HashMap;
import java.util.Map;

import org.cggh.chassis.generic.async.client.Deferred;
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

				syncPerspectives(e.getAfter());
				
			}
		});

		HandlerRegistration b = this.userDetailsWidget.addCurrentRoleChangeHandler(new CurrentRoleChangeHandler() {
			
			public void onCurrentRoleChanged(CurrentRoleChangeEvent e) {
				
				syncCurrentPerspective(e.getAfter());
				
			}
		});
		
		this.childWidgetEventHandlerRegistrations.add(a);
		this.childWidgetEventHandlerRegistrations.add(b);
	}

	
	
	
	/**
	 * @param user
	 */
	protected void syncPerspectives(UserDetailsTO user) {
		log.enter("syncPerspectives");

		this.perspectivesContainer.clear();
		this.perspectives.clear();
		
		for (ChassisRole role : ChassisRole.getRoles(user)) {

			ChassisWidget perspective = null;
			
			if (role.equals(Configuration.getChassisRoleAdministrator())) {
				
				log.debug("adding administrator perspective");
				perspective = new AdministratorPerspective();

			}
			else if (role.equals(Configuration.getChassisRoleSubmitter())) {

				log.debug("adding submitter perspective");
				perspective = new SubmitterPerspective();

			}
			else if (role.equals(Configuration.getChassisRoleGatekeeper())) {
				
				log.debug("adding gatekeeper perspective");
				perspective = new GatekeeperPerspective();
				
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
	protected void syncCurrentPerspective(ChassisRole currentRole) {
		this.syncCurrentPerspective(currentRole, true);
	}
	
	
	
	
	/**
	 * @param currentRole
	 */
	protected void syncCurrentPerspective(ChassisRole currentRole, boolean memorise) {
		log.enter("syncCurrentPerspective");
		
		for (Widget w : this.perspectives.values()) {
			w.setVisible(false);
		}
		
		if (currentRole != null) {

			log.debug("currentRole: "+currentRole.roleLabel);
			ChassisWidget perspective = this.perspectives.get(currentRole.roleId);

			if (perspective != null) {
				
				log.debug("setting active widget visible");
				perspective.setVisible(true);
				
				log.debug("refreshing active widget");
				perspective.refresh();
				
				log.debug("setting memory child");
				this.memory.setChild(perspective.getMemory());
				
				if (memorise) {
					log.debug("memorising");
					this.memory.memorise();
				}
				
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
		this.syncPerspectives(user);
		
		ChassisRole role = this.userDetailsWidget.getCurrentRole();
		this.syncCurrentPerspective(role);

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
					mnemonic = createMnemonic(currentRole);
				}
			}
			
			log.debug("mnemonic: "+mnemonic);

			log.leave();
			return mnemonic;
		}
		
		public String createMnemonic(ChassisRole role) {
			return role.roleLabel.toLowerCase().replaceAll(" ", "");
		}
		
		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#remember(java.lang.String)
		 */
		@Override
		public Deferred<WidgetMemory> remember(final String mnemonic) {
			log.enter("remember");

			log.debug("mnemonic: "+mnemonic);
			
			Deferred<WidgetMemory> deferredSelf = new Deferred<WidgetMemory>();
			
			UserDetailsTO user = userDetailsWidget.getCurrentUser();
			
			if (user != null) {

				log.debug("current user is available, remember immediately");

				for (ChassisRole role : ChassisRole.getRoles(user)) {
					if (mnemonic != null && mnemonic.equals(createMnemonic(role))) {
						log.debug("setting current role: "+role.roleLabel);
						userDetailsWidget.setCurrentRole(role, false); 
						syncCurrentPerspective(role, false);
					}
				}
				
				deferredSelf.callback(this); // callback immediately

			}
			
			log.leave();
			return deferredSelf;
		}

	}

	
	
	
	public Deferred<UserDetailsTO> refreshCurrentUser() {
		return this.userDetailsWidget.refreshCurrentUserDetails();
	}

	
	
	
}
