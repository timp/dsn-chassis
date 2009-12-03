/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import java.util.Set;

import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetModel.CurrentRoleChangeEvent;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetModel.CurrentRoleChangeHandler;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetModel.CurrentUserChangeEvent;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetModel.CurrentUserChangeHandler;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author aliman
 *
 */
public class UserDetailsWidgetRenderer 
	extends AsyncWidgetRenderer<UserDetailsWidgetModel> {
	
	
	
	
	private Log log = LogFactory.getLog(UserDetailsWidgetRenderer.class);


	
	// child widgets
	private Panel changeUserRolePanel;
	private Label userNameLabel, currentRoleLabel;
	private ListBox userRolesListBox;

	
	
	
	public static final String STYLENAME_USERNAME = "userName";
	public static final String STYLENAME_CURRENTROLE = "currentRole";
	public static final String STYLENAME_ROLESWITCHER = "roleSwitcher";
	public static final String STYLENAME_USERDETAILS = "userDetails";
	public static final String STYLENAME_SWITCHEROLEPANEL = "switchRolePanel";



	/**
	 * @param newUserDetailsWidget
	 */
	public UserDetailsWidgetRenderer() {}




	/**
	 * 
	 */
	public void renderMainPanel() {
		log.enter("renderMainPanel");
		
		log.debug("instantiate child widgets");
		this.changeUserRolePanel = new FlowPanel();
		this.userNameLabel = new InlineLabel();
		this.currentRoleLabel = new InlineLabel();
		this.userRolesListBox = new ListBox();
		
		log.debug("add styles");
		this.mainPanel.addStyleName(STYLENAME_USERDETAILS);
		this.userNameLabel.addStyleName(STYLENAME_USERNAME);
		this.currentRoleLabel.addStyleName(STYLENAME_CURRENTROLE);
		this.userRolesListBox.addStyleName(STYLENAME_ROLESWITCHER);
		changeUserRolePanel.addStyleName(STYLENAME_SWITCHEROLEPANEL);
		
		log.debug("render mainPanel");
		Label loggedInAsLabel = new InlineLabel("logged in as: "); // TODO i18n
		mainPanel.add(loggedInAsLabel);
		mainPanel.add(userNameLabel);
		Label roleLabel = new InlineLabel(" | role: "); // TODO i18n
		mainPanel.add(roleLabel);
		mainPanel.add(currentRoleLabel);
		
		log.debug("render changeUserRolePanel");
		Label changeRoleLabel = new InlineLabel(" | switch role: "); // TODO i18n		
		changeUserRolePanel.add(changeRoleLabel);
		changeUserRolePanel.add(userRolesListBox);
		mainPanel.add(changeUserRolePanel);	
		
		log.leave();
	}





	/**
	 * 
	 */
	@Override
	protected void registerHandlersForModelChanges() {
		log.enter("registerHandlersForModelChanges");

		log.debug("call on super first");
		super.registerHandlersForModelChanges();
		
		log.debug("register handler for current user change");
		HandlerRegistration a = this.model.addCurrentUserChangeHandler(new CurrentUserChangeHandler() {
			
			public void onCurrentUserChanged(CurrentUserChangeEvent e) {
				log.enter("[anon CurrentUserChangeHandler] onCurrentUserChanged");
				
				updateUserNameLabel(e.getAfter());
				updateRolesListBox(e.getAfter());
				
				log.leave();
				
			}
		});
		
		log.debug("register handler for current role change");
		HandlerRegistration b = this.model.addCurrentRoleChangeHandler(new CurrentRoleChangeHandler() {
			
			public void onCurrentRoleChanged(CurrentRoleChangeEvent e) {
				log.enter("onCurrentRoleChanged");
				
				updateCurrentRoleLabel(e.getAfter());
				
				log.leave();
				
			}
		});
		
		log.debug("store handler registrations for later");
		this.modelChangeHandlerRegistrations.add(a);
		this.modelChangeHandlerRegistrations.add(b);
		
		log.leave();		
	}




	/**
	 * 
	 */
	@Override
	protected void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");
		
		userRolesListBox.addChangeHandler(new RolesListBoxChangeHandler());
		
		log.leave();
		
	}
	
	
	
	
	
	private class RolesListBoxChangeHandler implements ChangeHandler {

		public void onChange(ChangeEvent arg0) {
			
			int selectedRoleId = Integer.parseInt(userRolesListBox.getValue(userRolesListBox.getSelectedIndex()));
			
			ChassisRole role = Configuration.getChassisRole(selectedRoleId);
			
			// map directly to model change
			model.setCurrentRole(role, true);

		}

	}





	/**
	 * 
	 */
	public void syncUI() {
		log.enter("syncUI");
		
		super.syncUI();
		
		this.updateUserNameLabel(this.model.getCurrentUser());
		this.updateRolesListBox(this.model.getCurrentUser());
		this.updateCurrentRoleLabel(this.model.getCurrentRole());
		
		log.leave();
		
	}





	/**
	 * @param user
	 */
	protected void updateUserNameLabel(UserDetailsTO user) {
		log.enter("updateUserNameLabel");
		
		String text = (user != null) ? user.getId() : "";
		this.userNameLabel.setText(text);
		
		log.leave();
	}




	/**
	 * @param roles
	 */
	protected void updateRolesListBox(UserDetailsTO user) {
		log.enter("updateRolesListBox");
		
		log.debug("clear ListBox");
		userRolesListBox.clear();
		
		if (user != null) {

			Set<ChassisRole> roles = ChassisRole.getRoles(user);
			
			for (ChassisRole role : roles) {
				// TODO create friendly translator for roles?
				userRolesListBox.addItem(role.roleLabel, role.roleId.toString());
			}
			
			log.debug("show/hide changeUserRolePanel");
			boolean visible = (roles.size() > 1);
			this.changeUserRolePanel.setVisible(visible);

		}
		
		log.leave();
		
	}




	/**
	 * @param role
	 */
	protected void updateCurrentRoleLabel(ChassisRole role) {
		log.enter("updateCurrentRoleLabel");
		
		String text = (role != null) ? role.roleLabel : "";
		this.currentRoleLabel.setText(text);
		
		log.leave();
		
	}




}
