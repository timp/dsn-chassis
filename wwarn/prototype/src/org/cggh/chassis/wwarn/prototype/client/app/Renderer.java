/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.app;

import java.util.List;

import org.cggh.chassis.wwarn.prototype.client.curator.CuratorPerspective;
import org.cggh.chassis.wwarn.prototype.client.shared.Perspective;
import org.cggh.chassis.wwarn.prototype.client.shared.HMVCComponent;
import org.cggh.chassis.wwarn.prototype.client.shared.User;
import org.cggh.chassis.wwarn.prototype.client.submitter.SubmitterPerspective;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
class Renderer implements ModelListener {

	
	
	private Application owner;
	private Controller controller;

	private RootPanel userDetailsRootPanel;
	private Label userNameLabel;
	private Label currentRoleLabel;
	private ListBox switchRolesListBox;
	
	
	
	/**
	 * @@TODO doc me
	 * 
	 * @param owner
	 * @param controller
	 */
	Renderer(Application owner, Controller controller) {
		this.owner = owner;
		this.controller = controller;
		this.init();
	}

	
	
	/**
	 * @@TODO doc me
	 */
	private void init() {
		this.initView();
		this.initEventHandlers();
	}

	
	
	/**
	 * @@TODO doc me
	 */
	private void initView() {
		this.initUserDetailsPanel();
	}
	
	
	
	/**
	 * @@TODO doc me
	 */
	private void initUserDetailsPanel() {
		
		this.userDetailsRootPanel = RootPanel.get(Application.ELEMENTID_USERDETAILS);

		HorizontalPanel p = new HorizontalPanel();
		this.userDetailsRootPanel.add(p);
		
		p.add(new Label("logged in as:"));

		this.userNameLabel = new Label();
		this.userNameLabel.getElement().setId(Application.ELEMENTID_USERNAMELABEL);
		p.add(this.userNameLabel);
		
		p.add(new Label("| current role:"));

		this.currentRoleLabel = new Label();
		this.currentRoleLabel.getElement().setId(Application.ELEMENTID_CURRENTPERSPECTIVELABEL);
		p.add(this.currentRoleLabel);
		
		p.add(new Label("| switch role:"));

		this.switchRolesListBox = new ListBox();
		this.switchRolesListBox.setVisibleItemCount(1);
		p.add(this.switchRolesListBox);
		p.setSpacing(5);	
		
	}

	
	
	/**
	 * @@TODO doc me
	 */
	private void initEventHandlers() {
		
		this.switchRolesListBox.addChangeHandler(new ChangeHandler() {
			
			public void onChange(ChangeEvent event) {
				String roleName = switchRolesListBox.getValue(switchRolesListBox.getSelectedIndex());
				controller.setCurrentRole(roleName);
			}
			
		});

	}



	/**
	 * @@TODO doc me
	 */
	public void onCurrentUserChanged(User oldUser, User newUser) {
		
		// set user name label
		this.userNameLabel.setText(newUser.getName());
		
		// render roles switcher
		this.renderRolesSwitcher(newUser);

		// create perspectives
		this.updatePerspectives(newUser);

	}

	

	/**
	 * @@TODO doc me
	 */
	public void onCurrentRoleChanged(String old, String currentRole) {
		
		// set current role label
		this.currentRoleLabel.setText(currentRole);
		
		// TODO let perspectives switch themselves??
		
	}

	

	/**
	 * @@TODO doc me
	 * @param newUser
	 */
	private void renderRolesSwitcher(User newUser) {

		this.switchRolesListBox.clear();
		for (String roleName : newUser.getRoleNames()) {
			this.switchRolesListBox.addItem(roleName, roleName);
		}

	}


	
	/**
	 * @@TODO doc me
	 * @param user
	 */
	private void updatePerspectives(User user) {
		String _ = "updatePerspectives"; log("begin",_);
		
		log("clear current perspectives",_);
		for (HMVCComponent c : this.owner.getChildren()) {
			if (c instanceof Perspective) {
				this.owner.removeChild(c);
			}
		}
		
		log("create new perspectives for user's roles",_);
		
		List<String> roles = user.getRoleNames();
		
		if (roles.contains(SubmitterPerspective.ROLENAME)) {
			log("add submitter perspective",_);
			this.owner.addChild(new SubmitterPerspective());
		}
		
		if (roles.contains(CuratorPerspective.ROLENAME)) {
			log("add curator perspective",_);
			this.owner.addChild(new CuratorPerspective());
		}
		
		// TODO other perspectives
		
		log("end",_);	
	}
	
	
	
	
	/**
	 * @@TODO doc me
	 * @param message
	 * @param context
	 */
	private void log(String message, String context) {
		String output = Renderer.class.getName() + " :: " + context + " :: " + message;
		GWT.log(output, null);
	}
	
	



}
