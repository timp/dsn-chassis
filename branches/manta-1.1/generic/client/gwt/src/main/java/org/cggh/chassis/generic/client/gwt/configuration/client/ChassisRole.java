/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.configuration.client;

import java.util.Set;
import java.util.TreeSet;

import org.cggh.chassis.generic.user.transfer.UserDetailsTO;

/**
 * @author raok
 *
 */
public class ChassisRole implements Comparable<ChassisRole> {
	
	final public Integer roleId;
	final public String permissionSuffix;
	final public String roleLabel;
	
	protected ChassisRole(Integer roleId, String permissionSuffix, String roleLabel) {
		this.roleId = roleId;
		this.permissionSuffix = permissionSuffix;
		this.roleLabel = roleLabel;
	}
	
	@Override
	public boolean equals(Object another) {
		
		if (another.getClass() == ChassisRole.class) {
			ChassisRole anotherChassisRole = (ChassisRole) another;
			return (this.roleId.equals(anotherChassisRole.roleId));
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		return roleId.hashCode();
	}

	public int compareTo(ChassisRole another) {
//		return roleId.compareTo(another.roleId);
		return permissionSuffix.compareTo(another.permissionSuffix); // so we get in alphabetical order
	}

	/**
	 * @param after
	 * @return
	 */
	public static Set<ChassisRole> getRoles(UserDetailsTO user) {

		Set<ChassisRole> roles = new TreeSet<ChassisRole>();

		// Filter out roles relevant to chassis
		String userChassisRolesPrefix = Configuration.getUserChassisRolesPrefix();

		if (user != null) {
			for ( String role : user.getRoles() ) {
				if (role.startsWith(userChassisRolesPrefix)) {
					
					String permissionSuffix = role.replace(userChassisRolesPrefix, "");
					
					for (ChassisRole r : Configuration.getChassisRoles()) {
						if (permissionSuffix.equalsIgnoreCase(r.permissionSuffix)) {
							roles.add(r);
						}
					}
					
				}
			}
		}
		
		return roles;
	}

	/**
	 * @return
	 */
	public String getAuthority() {
		return Configuration.getUserChassisRolesPrefix() + this.permissionSuffix;
	}
	
}
