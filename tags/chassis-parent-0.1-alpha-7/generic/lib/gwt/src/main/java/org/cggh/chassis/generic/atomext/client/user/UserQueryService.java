/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.user;

import org.cggh.chassis.generic.atom.client.AtomQueryService;
import org.cggh.chassis.generic.atom.client.AtomService;

/**
 * @author aliman
 *
 */
public class UserQueryService extends
		AtomQueryService<UserEntry, UserFeed, UserQuery> {


	
	
	public UserQueryService(String serviceUrl) {
		super(serviceUrl);
	}

	
	
	
	@Override
	protected AtomService<UserEntry, UserFeed> createPersistenceService() {
		return new UserPersistenceService();
	}

	
	
}
