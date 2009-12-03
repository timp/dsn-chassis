/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.user;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.gwt.lib.twisted.client.Deferred;

/**
 * @author aliman
 *
 */
public class UserService {

	@SuppressWarnings("unused")
	private String endpointURL;

	public UserService(String endpointURL) {
		this.endpointURL = endpointURL;
	}
	
	public Deferred<User> getAuthenticatedUser() {
		// TODO

		// fake for now...
		List<String> roles = new ArrayList<String>();
		roles.add(RoleNames.SUBMITTER);
		roles.add(RoleNames.CURATOR);
		roles.add(RoleNames.GATEKEEPER);
		roles.add(RoleNames.COORDINATOR);
		
		User bob = new User("Bob", roles);
		Deferred<User> def = new Deferred<User>();
		// callback immediately
		def.callback(bob);

		return def;
	}
}
