/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.user.UserFeed;
import org.cggh.chassis.generic.atomext.client.user.UserQuery;
import org.cggh.chassis.generic.atomext.client.user.UserQueryService;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncErrback;


/**
 * @author aliman
 *
 */
public class SelectCuratorWidgetController {

	
	
	
	private Log log = LogFactory.getLog(SelectCuratorWidgetController.class);
	
	private SelectCuratorWidget owner;
	private SelectCuratorWidgetModel model;

	
	
	
	
	/**
	 * @param owner
	 * @param model
	 */
	public SelectCuratorWidgetController(
			SelectCuratorWidget owner,
			SelectCuratorWidgetModel model) {
		
		this.owner = owner;
		this.model = model;

	}





	/**
	 * 
	 */
	public void refreshCurators() {
		log.enter("refreshCurators");
		
		log.debug("set status on model to pending");
		this.model.setStatus(SelectCuratorWidgetModel.STATUS_RETRIEVE_CURATORS_PENDING);
		
		log.debug("instantiate a user query service");
		UserQueryService service = new UserQueryService(Configuration.getUserQueryServiceUrl());
		
		log.debug("set up a user query by role");
		UserQuery query = new UserQuery();
		query.setRole(Configuration.getChassisRoleCurator().getAuthority());
		
		log.debug("kick off query to remote service");
		Deferred<UserFeed> deferredCurators = service.query(query);
		
		log.debug("add callbacks");
		deferredCurators.addCallback(new RefreshCuratorsCallback());
		deferredCurators.addErrback(new AsyncErrback(owner, model));
		
		log.leave();
	}
	
	
	
	
	private class RefreshCuratorsCallback implements Function<UserFeed, UserFeed> {

		public UserFeed apply(UserFeed curators) {
			log.enter("apply");
			
			model.setCurators(curators);
			
			model.setStatus(SelectCuratorWidgetModel.STATUS_CURATORS_RETRIEVED);
			
			log.leave();
			return curators;
		}
	}

}
