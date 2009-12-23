/**
 * @author timp
 * @since 4 Dec 2009
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atomext.client.review.ReviewEntry;
import org.cggh.chassis.generic.atomext.client.review.ReviewFactory;
import org.cggh.chassis.generic.atomext.client.review.ReviewPersistenceService;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionQuery;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionQueryService;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncErrback;

public class ReviewSubmissionWidgetController 
	{
	
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

	public Deferred<SubmissionEntry> retrieveSubmissionEntry(String id) { 
		log.enter("retreiveSubmissionEntry");
		log.debug("store submission entry id to use as mnemonic");
		
		this.model.setSubmissionEntryId(id);
		
		log.debug("set status to retrieve submission pending");
		
		this.model.setStatus(ReviewSubmissionWidgetModel.STATUS_RETRIEVE_SUBMISSION_PENDING);
		
		log.debug("now retrieve expanded submission");
		
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
				
				log.debug("Model status - " + model.getStatus().getClass());
				
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
		log.enter("acceptSubmission");
		log.debug("Comment:" + comment);
		
		SubmissionEntry submissionentry = this.model.getSubmissionEntry();
		ReviewFactory reviewFactory = new ReviewFactory();
		ReviewEntry reviewEntry = reviewFactory.createEntry();
		AtomAuthor author = reviewFactory.createAuthor();
		author.setEmail(ChassisUser.getCurrentUserEmail());
		reviewEntry.addAuthor(author);
		reviewEntry.setSummary(comment);
		
		reviewEntry.setSubmissionLink(submissionentry.getEditLink().getHref());

		ReviewPersistenceService reviewService = new ReviewPersistenceService(Configuration.getReviewCollectionUrl());
		
		this.model.setStatus(ReviewSubmissionWidgetModel.STATUS_CREATE_REVIEW_PENDING);
		
		Deferred<ReviewEntry> deferredReviewEntry = reviewService.postEntry("", reviewEntry);
		
		deferredReviewEntry.addCallback(new Function<ReviewEntry, ReviewEntry>() { 
			public ReviewEntry apply(ReviewEntry in) { 
				log.enter("acceptSubmission[anon callback]");
				
				model.setStatus(ReviewSubmissionWidgetModel.STATUS_REVIEW_CREATED);
				
				log.debug("Add link " + in.getEditLink() + " to " + model.getSubmissionEntry().getId()); 
 
				log.debug("Before have " + model.getSubmissionEntry().getLinks().size() + " links");

				model.getSubmissionEntry().addLink(in.getEditLink());
				
				log.debug("Now have " + model.getSubmissionEntry().getLinks().size() + " links");
				log.debug("Link:" + model.getSubmissionEntry().getEditLink().getHref());
				
				CreateReviewSuccessEvent createReviewSuccessEvent  = new CreateReviewSuccessEvent();
				createReviewSuccessEvent.setEntry(in);
				

				
				owner.fireEvent(createReviewSuccessEvent);
				
				
				log.leave();
				
				return in;
			}
		});
		
		deferredReviewEntry.addErrback(new AsyncErrback(owner, model));
		
		log.leave();
	}
	
	
	
	
	
}
