/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.app;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.wwarn.prototype.client.shared.RoleNames;
import org.cggh.chassis.wwarn.prototype.client.shared.User;

/**
 * @author aliman
 *
 */
class GetUserRequest {

	private GetUserRequestCallback callback;
	private String url;

	GetUserRequest(String url) {
		this.url = url;
	}

	void setCallback(GetUserRequestCallback callback) {
		this.callback = callback;
	}

	void send() {
		// TODO implement this method
		
		// fake for now...
		List<String> roles = new ArrayList<String>();
		roles.add(RoleNames.SUBMITTER);
		roles.add(RoleNames.CURATOR);
		
		User bob = new User("Bob", roles);
		this.callback.onSuccess(bob);
		
	}

}
