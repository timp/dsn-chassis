/**
 * 
 */
package org.cggh.chassis.generic.user.transfer;

import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author aliman
 *
 */
public class UserDetailsTO implements IsSerializable {
	
	private String id = null;
	private Set<String> roles = null;
	
	public UserDetailsTO() {}

	public void setId(String id) {
		this.id  = id;
	}

	public void setRoles(Set<String> roles) {
		this.roles  = roles;
	}

	public String getId() {
		return this.id;
	}

	public Set<String> getRoles() {
		return this.roles;
	}

	final public boolean equals(Object another) {
		if (another instanceof UserDetailsTO) {
			UserDetailsTO anotherUser = (UserDetailsTO) another;
			return (this.id.equals(anotherUser.getId()));
		}
		return false;
	}
	
	final public int hashCode() {
		return id.hashCode();
	}

}
