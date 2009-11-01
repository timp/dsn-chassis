/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;

/**
 * @author aliman
 *
 */
public class NewUserDetailsWidgetController {
	
	
	
	
	private Log log = LogFactory.getLog(NewUserDetailsWidgetController.class);
	private NewUserDetailsWidgetModel model;
	
	
	

	/**
	 * @param model
	 */
	public NewUserDetailsWidgetController(NewUserDetailsWidgetModel model) {
		this.model = model;
	}




	/**
	 * @return
	 */
	public Deferred<UserDetailsTO> refreshCurrentUserDetails() {
		log.enter("refreshCurrentUserDetails");
		
		// TODO Auto-generated method stub
		
		log.leave();
		return null;
	}




	/**
	 * @param role
	 */
	public void setCurrentRole(ChassisRole role) {
		log.enter("setCurrentRole");
		
		// TODO Auto-generated method stub
		
		log.leave();
		
	}

	
	
	
}
