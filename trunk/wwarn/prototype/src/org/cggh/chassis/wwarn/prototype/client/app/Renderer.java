/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.app;

import java.util.List;

import org.cggh.chassis.wwarn.prototype.client.curator.perspective.CuratorPerspective;
import org.cggh.chassis.wwarn.prototype.client.shared.GWTLogger;
import org.cggh.chassis.wwarn.prototype.client.shared.Logger;
import org.cggh.chassis.wwarn.prototype.client.shared.Perspective;
import org.cggh.chassis.wwarn.prototype.client.shared.HMVCComponent;
import org.cggh.chassis.wwarn.prototype.client.shared.RoleNames;
import org.cggh.chassis.wwarn.prototype.client.shared.User;
import org.cggh.chassis.wwarn.prototype.client.submitter.perspective.SubmitterPerspective;

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
	private Logger log;

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
		this.log = new GWTLogger();
		this.init();
	}

	
	
	Renderer(Application owner, Controller controller, Logger log) {
		this.owner = owner;
		this.controller = controller;
		this.log = log;
		this.init();
	}

	
	
	/**
	 * @@TODO doc me
	 */
	private void init() {
		this.log.setCurrentClass(Renderer.class.getName());
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
		log.enter("onCurrentRoleChanged");
		
		// set current role label
		this.currentRoleLabel.setText(currentRole);
		
		// set selection on list box
		for (int i=0; i<switchRolesListBox.getItemCount(); i++) {
			if (currentRole.equals(switchRolesListBox.getValue(i))) {
				switchRolesListBox.setSelectedIndex(i);
			}
		}
		
		// switch current perspective
		for (HMVCComponent c : this.owner.getChildren()) {
			if (c instanceof Perspective) {
				Perspective p = (Perspective) c;
				if (currentRole.equals(p.getRoleName())) {
					log.info("set as current perspective: "+p.getRoleName());
					p.setIsCurrentPerspective(true);
				}
				else {
					log.info("set as not current perspective: "+p.getRoleName());
					p.setIsCurrentPerspective(false);
				}
			}
		}
		
		log.leave();
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
		log.enter("updatePerspectives");
		
		log.info("clear current perspectives");
		for (HMVCComponent c : this.owner.getChildren()) {
			if (c instanceof Perspective) {
				this.owner.removeChild(c);
			}
		}
		
		log.info("create new perspectives for user's roles");
		
		List<String> roles = user.getRoleNames();
		
		if (roles.contains(RoleNames.SUBMITTER)) {
			log.info("add submitter perspective");
			this.owner.addChild(new SubmitterPerspective());
		}
		
		if (roles.contains(RoleNames.CURATOR)) {
			log.info("add curator perspective");
			this.owner.addChild(new CuratorPerspective());
		}
		
		// TODO other perspectives
		
		log.leave();	
	}



	public void onInitialisationCompleteChanged(Boolean from, Boolean to) {
		// TODO Auto-generated method stub
		
	}



	public void onInitialisationSuccessChanged(Boolean from, Boolean to) {
		// TODO Auto-generated method stub
		
	}
	
	


}
