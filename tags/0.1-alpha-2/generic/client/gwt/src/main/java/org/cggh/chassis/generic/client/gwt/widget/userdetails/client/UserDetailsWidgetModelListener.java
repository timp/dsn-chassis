/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import java.util.Set;

/**
 * @author raok
 *
 */
interface UserDetailsWidgetModelListener {

	void onUserNameChanged(String before, String after);

	void onCurrentRoleChanged(String before, String after);

	void onStatusChanged(Integer before, Integer after);

	void onRolesChanged(Set<String> before, Set<String> after);

}
