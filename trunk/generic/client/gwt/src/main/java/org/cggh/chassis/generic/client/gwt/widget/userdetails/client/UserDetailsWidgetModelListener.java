/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import java.util.Set;

import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;

/**
 * @author raok
 *
 */
interface UserDetailsWidgetModelListener {

	void onUserNameChanged(String before, String after);

	void onCurrentRoleChanged(ChassisRole before, ChassisRole after);

	void onStatusChanged(Integer before, Integer after);

	void onRolesChanged(Set<ChassisRole> before, Set<ChassisRole> after);

}
