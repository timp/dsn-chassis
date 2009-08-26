/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.client.widget.userdetails;

import java.util.HashSet;
import java.util.Set;


/**
 * @author raok
 *
 */
class UserDetailsWidgetModel {

	
	
	
	public static final Integer STATUS_INITIAL = 0;
	public static final Integer STATUS_LOADING = 1;
	public static final Integer STATUS_FOUND = 2;
	public static final Integer STATUS_ERROR = 3;
	
	
	
	private String userName;
	private Set<UserDetailsWidgetModelListener> listeners = new HashSet<UserDetailsWidgetModelListener>();
	private String currentRole;
	private Integer status = STATUS_INITIAL;
	private Set<String> roles;

	void setUserName(String userName) {
		String before = this.userName ;
		this.userName = userName;
		this.fireUserNameChanged(before, userName);
	}

	private void fireUserNameChanged(String before, String after) {
		for (UserDetailsWidgetModelListener l : listeners) {
			l.onUserNameChanged(before, after);
		}
	}

	String getUserName() {
		return this.userName;
	}

	void addListener(UserDetailsWidgetModelListener listener) {
		this.listeners.add(listener);
	}

	void setCurrentRole(String currentRole) {
		String before = this.currentRole;
		this.currentRole = currentRole;
		this.fireCurrentRoleChanged(before, currentRole);
	}

	private void fireCurrentRoleChanged(String before, String after) {
		for (UserDetailsWidgetModelListener l : listeners) {
			l.onCurrentRoleChanged(before, after);
		}
	}

	String getCurrentRole() {
		return this.currentRole;
	}

	Integer getStatus() {
		return this.status;
	}

	void setStatus(Integer status) {
		Integer before = this.status;
		this.status = status;
		this.fireStatusChanged(before, status);
	}

	private void fireStatusChanged(Integer before, Integer after) {
		for (UserDetailsWidgetModelListener l : listeners) {
			l.onStatusChanged(before, after);
		}
	}

	Set<String> getRoles() {
		return this.roles;
	}

	void setRoles(Set<String> roles) {
		Set<String> before = this.roles;
		this.roles = roles;
		this.fireRolesChanged(before, roles);
	}

	private void fireRolesChanged(Set<String> before, Set<String> after) {
		for (UserDetailsWidgetModelListener l : listeners) {
			l.onRolesChanged(before, after);
		}
	}

}
