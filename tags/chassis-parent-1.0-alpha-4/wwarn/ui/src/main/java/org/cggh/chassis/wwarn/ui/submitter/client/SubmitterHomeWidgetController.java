/**
 * 
 */
package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.Atom;
import org.cggh.chassis.wwarn.ui.common.client.Config;

import com.google.gwt.xml.client.Document;


/**
 * @author lee
 *
 */
public class SubmitterHomeWidgetController {

	// Give this controller its log.
	private static final Log log = LogFactory.getLog(SubmitterHomeWidgetController.class);
	
	
	// Give this controller its undefined owner and model.
	private SubmitterHomeWidgetModel model;

	
	// Allow this controller to construct itself, defining its owner and model.
	/**
	 * @param owner
	 * @param model
	 */
	public SubmitterHomeWidgetController(
			SubmitterHomeWidget owner,
			SubmitterHomeWidgetModel model) {
		
		this.model = model;

	}


	public Deferred<Document> retrieveSubmissions() {
		
		log.enter("retrieveSubmissions");
		
		// Set the model's status to pending.
		model.setStatus(SubmitterHomeWidgetModel.STATUS_RETRIEVE_SUBMISSIONS_PENDING);
		
		// Define the URL for getting the submissions feed, using the system configuration. 
		String url = Config.get(Config.QUERY_SUBMISSIONS_URL);
		log.debug("url: " + url);
		
		// Define the feed, using Atom and the feed URL.
		log.debug("make get feed request");
		Deferred<Document> deferredFeedDoc = Atom.getFeed(url);
		
		// Add a call-back and error-back for the asynchronous feed.
		deferredFeedDoc.addCallback(new RetrieveSubmissionsCallback());
		deferredFeedDoc.addErrback(new RetrieveSubmissionsErrback());
		
		log.leave();
		
		// Return the deferred feed document to the widget (owner).
		return deferredFeedDoc;
	}

	public class RetrieveSubmissionsCallback implements Function<Document, Document> {


		public Document apply(Document submissionsFeedDoc) {
			
			// Give the model its submissions feed document.
//			model.setSubmissions(submissionsFeedDoc);
			model.submissionFeed.set(submissionsFeedDoc);
			
			// Update the model's status.
			model.setStatus(SubmitterHomeWidgetModel.STATUS_SUBMISSIONS_RETRIEVED);
			
			
			// Return the retrieved submissions to...
			return submissionsFeedDoc;
		}

	}
	
	public class RetrieveSubmissionsErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable in) {
			
			// Update the model's status.
			model.setStatus(SubmitterHomeWidgetModel.STATUS_ERROR);			
			
			return null;
		}

	}



	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}
