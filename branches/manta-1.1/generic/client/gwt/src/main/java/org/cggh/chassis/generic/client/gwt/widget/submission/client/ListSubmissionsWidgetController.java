/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionFeed;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionQuery;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionQueryService;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncErrback;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

/**
 * @author aliman
 * 
 */
public class ListSubmissionsWidgetController {

	private ListSubmissionsWidgetModel model;
	private ListSubmissionsWidget owner;

	private Log log = LogFactory.getLog(ListSubmissionsWidgetController.class);

	public ListSubmissionsWidgetController(ListSubmissionsWidget owner,
			ListSubmissionsWidgetModel model) {

		this.owner = owner;
		this.model = model;
	}

	public void refreshSubmissions() {
		log.enter("refreshSubmissions");
		if (model != null) {

			log.debug("set async pending status on model");

			this.model.setStatus(AsyncWidgetModel.STATUS_ASYNC_REQUEST_PENDING);

			log.debug("set up async request for submissions pending acceptance review");

			SubmissionQuery query = new SubmissionQuery();

			log.debug("execute service query");

			SubmissionQueryService service = new SubmissionQueryService(
					Configuration.getSubmissionQueryServiceUrl());
			Deferred<SubmissionFeed> deferredResults = service.query(query);

			log.debug("add callbacks");
			deferredResults.addCallback(new RefreshSubmissionsCallback());
			deferredResults.addErrback(new AsyncErrback(this.owner, this.model));
		}
		log.leave();
	}

	/**
	 * @author aliman
	 * 
	 */
	public class RefreshSubmissionsCallback implements
			Function<SubmissionFeed, SubmissionFeed> {
		private Log log = LogFactory.getLog(RefreshSubmissionsCallback.class);

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.cggh.chassis.generic.async.client.Function#apply(java.lang.Object
		 * )
		 */
		public SubmissionFeed apply(SubmissionFeed in) {
			log.enter("apply");

			model.setFeed(in);
			model.setStatus(AsyncWidgetModel.STATUS_READY);

			log.leave();
			return in;
		}

	}

}
