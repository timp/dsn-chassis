/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.user;

import org.cggh.chassis.generic.atom.client.AtomQuery;


/**
 * @author aliman
 *
 */
public class UserQuery extends AtomQuery {

	private static final String PARAM_ROLE = "role";

	public void setRole(String role) {
		this.params.put(PARAM_ROLE, role);
	}
	
	public String getRole() {
		return this.params.get(PARAM_ROLE);
	}
	
}
