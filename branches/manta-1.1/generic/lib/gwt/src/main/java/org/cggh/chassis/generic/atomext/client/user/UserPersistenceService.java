/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.user;

import org.cggh.chassis.generic.atom.client.AtomServiceImpl;

/**
 * @author aliman
 *
 */
public class UserPersistenceService extends
		AtomServiceImpl<UserEntry, UserFeed> {

	public UserPersistenceService(UserFactory factory) {
		super(factory);
	}

	public UserPersistenceService() {
		super(new UserFactory());
	}
	
	public UserPersistenceService(String baseUrl) {
		super(new UserFactory(), baseUrl);
	}
	
}
