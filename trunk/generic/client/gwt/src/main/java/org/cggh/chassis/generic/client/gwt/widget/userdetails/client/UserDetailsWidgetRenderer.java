/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import java.util.Set;

import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetModel.CurrentRoleChangeEvent;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetModel.CurrentRoleChangeHandler;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetModel.CurrentUserChangeEvent;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetModel.CurrentUserChangeHandler;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.AsyncRequestPendingStatus;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.ErrorStatus;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.InitialStatus;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.ReadyStatus;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeHandler;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author aliman
 *
 */
public class UserDetailsWidgetRenderer extends ChassisWidgetRenderer {
	
	
	
	
	private Log log = LogFactory.getLog(UserDetailsWidgetRenderer.class);


	
	// child widgets
	private Panel loadingPanel, userDetailsPanel, changeUserRolePanel;
	private Label userNameLabel, currentRoleLabel;
	private ListBox userRolesListBox;
	private UserDetailsWidgetModel model;

	
	
	
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
	public void renderUI() {
		log.enter("renderUI");
		
		log.debug("instantiate child widgets");
		this.loadingPanel = new FlowPanel();
		this.userDetailsPanel = new FlowPanel();
		this.changeUserRolePanel = new FlowPanel();
		this.userNameLabel = new InlineLabel();
		this.currentRoleLabel = new InlineLabel();
		this.userRolesListBox = new ListBox();
		
		log.debug("add styles");
		this.userDetailsPanel.addStyleName(STYLENAME_USERDETAILS);
		this.userNameLabel.addStyleName(STYLENAME_USERNAME);
		this.currentRoleLabel.addStyleName(STYLENAME_CURRENTROLE);
		this.userRolesListBox.addStyleName(STYLENAME_ROLESWITCHER);
		changeUserRolePanel.addStyleName(STYLENAME_SWITCHEROLEPANEL);
		
		log.debug("render loading panel");
		Label loadingLabel = new Label("loading..."); // TODO i18n
		loadingPanel.add(loadingLabel);
		
		log.debug("render userDetailsPanel");
		Label loggedInAsLabel = new InlineLabel("logged in as: "); // TODO i18n
		userDetailsPanel.add(loggedInAsLabel);
		userDetailsPanel.add(userNameLabel);
		Label roleLabel = new InlineLabel(" | role: "); // TODO i18n
		userDetailsPanel.add(roleLabel);
		userDetailsPanel.add(currentRoleLabel);
		
		log.debug("render changeUserRolePanel");
		Label changeRoleLabel = new InlineLabel(" | switch role: "); // TODO i18n		
		changeUserRolePanel.add(changeRoleLabel);
		changeUserRolePanel.add(userRolesListBox);
		userDetailsPanel.add(changeUserRolePanel);	
		
		log.debug("render canvas");
		this.canvas.add(this.loadingPanel);
		this.canvas.add(this.userDetailsPanel);
		
		log.leave();
	}




	/**
	 * @param model
	 */
	public void bindUI(UserDetailsWidgetModel model) {
		log.enter("bindUI");
		
		log.debug("unbind to clear anything");
		this.unbindUI();
		
		log.debug("keep reference to model");
		this.model = model;
		
		log.debug("register this as handler for model property change events");
		this.registerHandlersForModelChanges();
		
		log.debug("register handlers for child widget events");
		this.registerHandlersForChildWidgetEvents();
		
		log.leave();
	}




	/**
	 * 
	 */
	private void registerHandlersForModelChanges() {
		log.enter("registerHandlersForModelChanges");
		
		this.model.addStatusChangeHandler(new StatusChangeHandler() {
			
			public void onStatusChanged(StatusChangeEvent e) {
				log.enter("[anon StatusChangeHandler] onStatusChanged");
				
				updatePanelVisibility(e.getAfter());
				
				log.leave();
				
			}
		});
		
		this.model.addCurrentUserChangeHandler(new CurrentUserChangeHandler() {
			
			public void onCurrentUserChanged(CurrentUserChangeEvent e) {
				log.enter("[anon CurrentUserChangeHandler] onCurrentUserChanged");
				
				updateUserNameLabel(e.getAfter());
				updateRolesListBox(e.getAfter());
				
				log.leave();
				
			}
		});
		
		this.model.addCurrentRoleChangeHandler(new CurrentRoleChangeHandler() {
			
			public void onCurrentRoleChanged(CurrentRoleChangeEvent e) {
				log.enter("onCurrentRoleChanged");
				
				updateCurrentRoleLabel(e.getAfter());
				
				log.leave();
				
			}
		});
		
		log.leave();
		
	}




	/**
	 * 
	 */
	private void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");
		
		userRolesListBox.addChangeHandler(new RolesListBoxChangeHandler());
		
		log.leave();
		
	}
	
	
	
	
	
	private class RolesListBoxChangeHandler implements ChangeHandler {

		public void onChange(ChangeEvent arg0) {
			
			int selectedRoleId = Integer.parseInt(userRolesListBox.getValue(userRolesListBox.getSelectedIndex()));
			
			ChassisRole role = ConfigurationBean.getChassisRole(selectedRoleId);
			
			// map directly to model change
			model.setCurrentRole(role);

		}

	}





	/**
	 * 
	 */
	public void syncUI() {
		log.enter("syncUI");
		
		this.updatePanelVisibility(this.model.getStatus());
		this.updateUserNameLabel(this.model.getCurrentUser());
		this.updateRolesListBox(this.model.getCurrentUser());
		this.updateCurrentRoleLabel(this.model.getCurrentRole());
		
		log.leave();
		
	}




	/**
	 * 
	 */
	public void unbindUI() {
		log.enter("unbindUI");
		
		this.clearModelChangeHandlers();
		this.clearChildWidgetEventHandlers();
		
		log.leave();
		
	}
	
	
	

	/**
	 * @param e
	 */
	protected void updatePanelVisibility(Status status) {
		log.enter("updatePanelVisibility");
		
		if (status instanceof InitialStatus) {

			this.userDetailsPanel.setVisible(false);
			this.loadingPanel.setVisible(false);

		}
		else if (status instanceof AsyncRequestPendingStatus) {
			
			this.userDetailsPanel.setVisible(false);
			this.loadingPanel.setVisible(true);
			
		}
		else if (status instanceof ReadyStatus) {

			this.loadingPanel.setVisible(false);
			this.userDetailsPanel.setVisible(true);

		}
		else if (status instanceof ErrorStatus) {
		
			log.error("TODO handle error status");
			
		}
		else {

			log.error("unexpected status: "+status.getClass().getName());
			
		}
		
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
