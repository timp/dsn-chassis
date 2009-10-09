/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.configuration.client;

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
		return roleId.compareTo(another.roleId);
	}
	
}
