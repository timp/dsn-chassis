/**
 * @author timp
 * @since 4 Dec 2009
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionQuery;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionQueryService;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.ShareDatasetWidgetModel;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncErrback;

public class ReviewSubmissionWidgetController {
	
	private Log log = LogFactory.getLog(ReviewSubmissionWidgetController.class);

	private ReviewSubmissionWidgetModel model;
	private ReviewSubmissionWidget owner;
	
	
	public ReviewSubmissionWidgetController(
			ReviewSubmissionWidget owner,
			ReviewSubmissionWidgetModel model
	) {
		this.owner = owner;
		this.model = model;
	}

	public Deferred<SubmissionEntry> retreiveSubmissionEntry(String id) { 
		log.enter("retreiveSubmissionEntry");
		log.debug("store submission entry id to use as mnemonic");
		
		this.model.setSubmissionEntryId(id);
		
		log.debug("set status to retrieve submission pending");
		
		this.model.setStatus(ShareDatasetWidgetModel.STATUS_RETRIEVE_DATASET_PENDING);
		
		log.debug("now retrieve expanded dataset");
		
		SubmissionQueryService service = new SubmissionQueryService(Configuration.getSubmissionQueryServiceUrl());
		SubmissionQuery query = new SubmissionQuery();
		query.setId(id);
		
		Deferred<SubmissionEntry> deferredEntry = service.queryOne(query);
		
		log.debug("add callbacks to handle async response");
		
		Function<SubmissionEntry, SubmissionEntry> callback = new Function<SubmissionEntry, SubmissionEntry>() {

			public SubmissionEntry apply(SubmissionEntry in) {
				log.enter("[anon callback]");

				log.debug("retrieve Submission was a success");

				// N.B. order in which these two calls on model are made is important
				
				model.setSubmissionEntry(in);
				
				model.setStatus(ReviewSubmissionWidgetModel.STATUS_SUBMISSION_RETRIEVED);
				
				log.leave();
				return in;
			}
			
		};
		
		deferredEntry.addCallback(callback);
		
		deferredEntry.addErrback(new AsyncErrback(owner, model));
		
		log.leave();
		return deferredEntry;
	}

	public void acceptSubmission(String comment) { 
		log.enter("");
		this.model.setSubmissionStatus("accepted");
		log.leave();
	}
}
