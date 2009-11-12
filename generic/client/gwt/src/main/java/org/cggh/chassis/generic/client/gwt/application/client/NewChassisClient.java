/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import java.util.HashMap;
import java.util.Map;

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

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class NewChassisClient extends ChassisWidget {

	
	
	
	// utility fields
	private Log log;
	
	
	

	// UI fields
	private UserDetailsWidget userDetailsWidget;
	private FlowPanel perspectivesContainer;

	
	
	
	// state fields
	private Map<Integer,Widget> perspectives;

	
	
	



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");
		
		perspectives = new HashMap<Integer,Widget>();

		log.leave();
	}

	
	
	
	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(NewChassisClient.class);
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {

		this.userDetailsWidget = new UserDetailsWidget();		
		this.add(this.userDetailsWidget);
		
		this.userDetailsWidget.refreshCurrentUserDetails();

		this.perspectivesContainer = new FlowPanel();
		this.add(this.perspectivesContainer);

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

			Widget perspective = null;
			
			if (role.equals(Configuration.getChassisRoleAdministrator())) {
				
				log.enter("adding administrator perspective");
				perspective = new NewAdministratorPerspective();

			}
			else if (role.equals(Configuration.getChassisRoleSubmitter())) {

				log.enter("adding submitter perspective");
				perspective = new NewSubmitterPerspective();

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
			Widget w = this.perspectives.get(currentRole.roleId);
			if (w != null) w.setVisible(true);
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

	
	
	
}
