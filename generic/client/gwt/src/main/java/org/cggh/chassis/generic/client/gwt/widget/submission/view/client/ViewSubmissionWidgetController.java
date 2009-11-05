/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import org.cggh.chassis.generic.atom.rewrite.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atom.rewrite.client.submission.SubmissionPersistenceService;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;
import org.cggh.chassis.generic.widget.client.AsyncErrback;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

/**
 * @author aliman
 *
 */
public class ViewSubmissionWidgetController {

	
	
	
	private Log log = LogFactory.getLog(ViewSubmissionWidgetController.class);
	private ViewSubmissionWidgetModel model;
	private ViewSubmissionWidget owner;

	
	
	public ViewSubmissionWidgetController(ViewSubmissionWidget owner, ViewSubmissionWidgetModel model) {
		log.enter("<constructor>");
		
		this.owner = owner;
		this.model = model;

		log.leave();
	}
	
	


	/**
	 * Set the submission entry to display properties for.
	 * 
	 * @param entry
	 */
	public void setSubmissionEntry(SubmissionEntry entry) {
		log.enter("setSubmissionEntry");

		this.model.setStatus(AsyncWidgetModel.STATUS_ASYNC_REQUEST_PENDING); // simulate retrieve
		
		this.model.setSubmissionEntry(entry);

		this.model.setStatus(AsyncWidgetModel.STATUS_READY);

		// should not need to do anything else, renderer will automatically
		// update UI on submission entry change
		
		log.leave();
	}





	public void retrieveSubmissionEntry(String submissionEntryUrl) {
		log.enter("retrieveSubmissionEntry");
		
		log.debug("retrieving entry: " + submissionEntryUrl);

		model.setStatus(AsyncWidgetModel.STATUS_ASYNC_REQUEST_PENDING);
		
		SubmissionPersistenceService service = new SubmissionPersistenceService();
		Deferred<SubmissionEntry> def = service.getEntry(submissionEntryUrl);
		
		def.addCallback(new RetrieveSubmissionEntryCallback());
		def.addErrback(new AsyncErrback(this.owner, this.model));
		
		log.leave();
	}

	
	
	
	private class RetrieveSubmissionEntryCallback implements Function<SubmissionEntry,SubmissionEntry> {

		private Log log = LogFactory.getLog(this.getClass());
		
		public SubmissionEntry apply(SubmissionEntry submissionEntry) {
			log.enter("apply");
			
			model.setSubmissionEntry(submissionEntry);
			model.setStatus(AsyncWidgetModel.STATUS_READY);

			log.leave();
			return submissionEntry;
		}
		
	}
	
	
	
	

}
