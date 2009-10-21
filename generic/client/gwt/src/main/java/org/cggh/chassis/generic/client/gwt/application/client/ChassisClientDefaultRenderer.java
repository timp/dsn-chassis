/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import java.util.HashMap;
import java.util.Map;

import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author aliman
 *
 */
public class ChassisClientDefaultRenderer implements ChassisClientRenderer {

	
	
	
	public static final String STYLENAME_BASE = "chassis-client";
	
	
	
	private Log log = LogFactory.getLog(this.getClass());

	private Panel canvas;
	private UserDetailsWidget userDetailsWidget;
	private ChassisClient owner;
	private ChassisClientController controller;
	private Map<Integer,Perspective> perspectives = new HashMap<Integer,Perspective>();
	
	
	
	
	public ChassisClientDefaultRenderer(ChassisClient owner, ChassisClientController controller) {
		log.enter("<constructor>");
		
		this.owner = owner;
		this.controller = controller;

		this.constructCanvas();
		
		this.constructUserDetailsWidget();
		
		this.constructPerspectives();
		
		log.leave();
	}

	
	
	
	/**
	 * 
	 */
	private void constructCanvas() {
		log.enter("constructCanvas");
		
		this.canvas = new FlowPanel();
		
		log.debug("set style name");
		this.canvas.addStyleName(STYLENAME_BASE);
		
		log.leave();
	}


	

	/**
	 * 
	 */
	private void constructUserDetailsWidget() {
		log.enter("constructUserDetailsWidget");

		this.userDetailsWidget = new UserDetailsWidget();		
		this.canvas.add(this.userDetailsWidget);

		log.leave();
	}




	/**
	 * 
	 */
	private void constructPerspectives() {
		
		// TODO only construct those perspectives we actually need for the current user
		
		//get chassisRoles
		ChassisRole administratorRole = ConfigurationBean.getChassisRoleAdministrator();
		ChassisRole coordinatorRole = ConfigurationBean.getChassisRoleCoordinator();
		ChassisRole curatorRole = ConfigurationBean.getChassisRoleCurator();
		ChassisRole gatekeeperRole = ConfigurationBean.getChassisRoleGatekeeper();
		ChassisRole submitterRole = ConfigurationBean.getChassisRoleSubmitter();
		ChassisRole userRole = ConfigurationBean.getChassisRoleUser();

		Perspective p;
		
		p = new SubmitterPerspective();
		this.perspectives.put(submitterRole.roleId, p);
		p.setVisible(false);
		this.canvas.add(p);

		p = new AdministratorPerspective();
		this.perspectives.put(administratorRole.roleId, p);
		p.setVisible(false);
		this.canvas.add(p);

		// TODO construct other perspectives
		
	}




	public Panel getCanvas() {
		return this.canvas;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.application.client.ChassisClientRenderer#getUserDetailsWidget()
	 */
	public UserDetailsWidget getUserDetailsWidget() {
		return this.userDetailsWidget;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.application.client.ChassisClientModelListener#onPerspectiveChanged(java.lang.Integer, java.lang.Integer)
	 */
	public void onPerspectiveChanged(Integer before, Integer roleId) {
		log.enter("onPerspectiveChanged");
		
		log.debug("hide all perspectives");
		for (Perspective p : this.perspectives.values()) {
			p.setVisible(false);
		}

		log.debug("show selected perspective");
		Perspective p = perspectives.get(roleId);
		if (p != null) {
			p.setVisible(true);
		}
		else {
			// TODO do nothing for now
//			throw new ChassisClientError("no perspective found for role: "+roleId);
		}
		
		log.leave();
	}
	
	
	
	
}
