/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.user;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aliman
 *
 */
public class User {

	private String name;
	private List<String> roles = new ArrayList<String>();

	public User(String name, List<String> roles) {
		this.name = name;
		this.roles = roles;
	}
	
	public String getName() { return name; }
	public List<String> getRoleNames() { return roles; }

}
