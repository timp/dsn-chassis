/**
 * 
 */
package org.cggh.chassis.generic.user.data;

import java.util.Set;

/**
 * @author aliman
 *
 */
public class User {

	private String id = null;
	private String name = null;
	private Set<String> roles = null;

	public void setId(String id) {
		this.id  = id;
	}

	public void setName(String name) {
		this.name  = name;
	}

	public void setRoles(Set<String> roles) {
		this.roles  = roles;
	}

	public String getName() {
		return this.name;
	}

	public String getId() {
		return this.id;
	}

	public Set<String> getRoles() {
		return this.roles;
	}

	public boolean equals(Object another) {
		if (another instanceof User) {
			User anotherUser = (User) another;
			return (this.id.equals(anotherUser.getId()));
		}
		return false;
	}

}
