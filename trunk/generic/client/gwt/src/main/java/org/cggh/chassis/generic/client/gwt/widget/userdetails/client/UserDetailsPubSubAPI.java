/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;

/**
 * @author raok
 *
 */
public interface UserDetailsPubSubAPI {
	
	public void onUserIdRefreshed(String userId);
	
	public void onCurrentRoleChanged(ChassisRole currentRole);
	
}
