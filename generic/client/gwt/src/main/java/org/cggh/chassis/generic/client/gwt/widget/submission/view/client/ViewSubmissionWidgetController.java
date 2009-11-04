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

/**
 * @author aliman
 *
 */
public class ViewSubmissionWidgetController {

	
	
	
	private Log log = LogFactory.getLog(ViewSubmissionWidgetController.class);
	private ViewSubmissionWidgetModel model;

	
	
	public ViewSubmissionWidgetController(ViewSubmissionWidgetModel model) {
		log.enter("<constructor>");
		
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

		this.model.setStatus(ViewSubmissionWidgetModel.STATUS_RETRIEVE_PENDING); // simulate retrieve
		
		this.model.setSubmissionEntry(entry);

		this.model.setStatus(ViewSubmissionWidgetModel.STATUS_READY);

		// should not need to do anything else, renderer will automatically
		// update UI on submission entry change
		
		log.leave();
	}





	public void retrieveSubmissionEntry(String submissionEntryUrl) {
		log.enter("retrieveSubmissionEntry");
		
		log.debug("retrieving entry: " + submissionEntryUrl);

		model.setStatus(ViewSubmissionWidgetModel.STATUS_RETRIEVE_PENDING);
		
		//request submissionEntry
//		Deferred<AtomEntry> deferred = service.getEntry(submissionEntryUrl);
		SubmissionPersistenceService service = new SubmissionPersistenceService();
		Deferred<SubmissionEntry> def = service.getEntry(submissionEntryUrl);
		
		//add callbacks
		def.addCallback(new RetrieveSubmissionEntryCallback());
		def.addErrback(new RetrieveSubmissionEntryErrback());
		
		log.leave();
	}

	
	
	
	private class RetrieveSubmissionEntryCallback implements Function<SubmissionEntry,SubmissionEntry> {

		private Log log = LogFactory.getLog(this.getClass());
		
		public SubmissionEntry apply(SubmissionEntry submissionEntry) {
			log.enter("apply");
			
			model.setSubmissionEntry(submissionEntry);
			model.setStatus(ViewSubmissionWidgetModel.STATUS_READY);

			log.leave();
			return submissionEntry;
		}
		
	}
	
	
	
	
	private class RetrieveSubmissionEntryErrback implements Function<Throwable,Throwable> {

		private Log log = LogFactory.getLog(this.getClass());

		public Throwable apply(Throwable error) {
			log.enter("apply");

			log.error("error retrieving submission entry", error);
			
			model.setStatus(ViewSubmissionWidgetModel.STATUS_ERROR);
			
			log.leave();
			return error;
		}
		
	}
	
	
	
	
	
}
