/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionPersistenceService;
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




	/**
	 * @param curatorEmail
	 */
	public void assignCurator(String curatorEmail) {
		log.enter("assignCurator");
		
		SubmissionEntry e = this.model.getSubmissionEntry();
		
		e.clearInlineLinks(); // N.B. we need to do this because we get passed an expanded submission entry
		// alternative would be to do a fresh retrieve on the raw entry first
		// N.B. this is broken, need to do a fresh retrieve because of reverse link expansion
		
		String entryUrl = e.getEditLink().getHref();
		
		e.setCurator(curatorEmail);
		
		this.model.setStatus(SelectCuratorWidgetModel.STATUS_UPDATE_SUBMISSION_PENDING);
		
		SubmissionPersistenceService service = new SubmissionPersistenceService(Configuration.getSubmissionCollectionUrl());
		
		log.debug("kick off request to update submission entry");
		Deferred<SubmissionEntry> deferredSubmission = service.putEntry(entryUrl, e);
		
		deferredSubmission.addCallback(new AssignCuratorCallback());
		deferredSubmission.addErrback(new AsyncErrback(owner, model));
		
		log.leave();
	}

	
	
	/**
	 * @author aliman
	 *
	 */
	public class AssignCuratorCallback implements
			Function<SubmissionEntry, SubmissionEntry> {

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.async.client.Function#apply(java.lang.Object)
		 */
		public SubmissionEntry apply(SubmissionEntry in) {
			
			model.setStatus(SelectCuratorWidgetModel.STATUS_UPDATE_SUBMISSION_SUCCESS);
			
			UpdateSubmissionSuccessEvent e = new UpdateSubmissionSuccessEvent();
			owner.fireEvent(e);

			return in;
		}

	}




	/**
	 * @param entryUrl
	 */
	public Deferred<SubmissionEntry> retrieveSubmissionEntry(String entryUrl) {
		log.enter("retrieveSubmissionEntry");
		
		this.model.setSubmissionEntryUrl(entryUrl);
		
		this.model.setStatus(SelectCuratorWidgetModel.STATUS_RETRIEVE_SUBMISSION_PENDING);
		
		SubmissionPersistenceService service = new SubmissionPersistenceService(Configuration.getSubmissionCollectionUrl());
		Deferred<SubmissionEntry> ds = service.getEntry(entryUrl);
		
		ds.addCallback(new RetrieveSubmissionEntryCallback());
		ds.addErrback(new AsyncErrback(owner, model));
		
		log.leave();
		return ds;
	}

	
	
	
	/**
	 * @author aliman
	 *
	 */
	public class RetrieveSubmissionEntryCallback implements
			Function<SubmissionEntry, SubmissionEntry> {

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.async.client.Function#apply(java.lang.Object)
		 */
		public SubmissionEntry apply(SubmissionEntry in) {

			model.setSubmissionEntry(in);
			
			refreshCurators();

			return in;
		}

	}







}
