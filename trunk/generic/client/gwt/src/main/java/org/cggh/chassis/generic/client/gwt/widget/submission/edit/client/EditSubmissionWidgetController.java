/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.edit.client;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionPersistenceService;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.client.gwt.widget.submission.model.client.SubmissionModel;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;
import org.cggh.chassis.generic.twisted.client.HttpException;

/**
 * @author aliman
 *
 */
public class EditSubmissionWidgetController {

	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private EditSubmissionWidget owner;
	private EditSubmissionWidgetModel model;

	
	
	
	/**
	 * @param owner
	 * @param model
	 */
	public EditSubmissionWidgetController(
			EditSubmissionWidget owner,
			EditSubmissionWidgetModel model) {

		this.owner = owner;
		this.model = model;
		
	}
	
	
	
	
	/**
	 * 
	 */
	public void ready() {
		
		this.model.setStatus(EditSubmissionWidgetModel.STATUS_READY);
		
	}




	/**
	 * 
	 */
	public void cancelEditSubmissionEntry() {
		log.enter("cancelEditSubmissionEntry");
		
		this.model.setStatus(EditSubmissionWidgetModel.STATUS_CANCELLED);
		
		this.owner.fireOnUserActionEditSubmissionCancelled();
		
		log.leave();
	}




	/**
	 * @param entry 
	 * 
	 */
	public void updateSubmissionEntry(SubmissionEntry entry) {
		log.enter("updateSubmissionEntry");
		
		log.debug("entry to save: "+entry.toString());
		
		this.model.setStatus(EditSubmissionWidgetModel.STATUS_UPDATE_PENDING);
		
		String entryUrl = Configuration.getSubmissionFeedURL() + entry.getEditLink().getHref();

		log.debug("kick off put request");
//		Deferred<AtomEntry> def = service.putEntry(entryUrl, entry);
		SubmissionPersistenceService service = new SubmissionPersistenceService();
		Deferred<SubmissionEntry> def = service.putEntry(entryUrl, entry);
		
		log.debug("add callbacks");
		def.addCallback(new UpdateSubmissionEntryCallback());
		def.addErrback(new UpdateSubmissionEntryErrback());
		
		log.leave();
	}

	
	
	
	class UpdateSubmissionEntryCallback implements Function<SubmissionEntry,SubmissionEntry> {

		private Log log = LogFactory.getLog(this.getClass());

		public SubmissionEntry apply(SubmissionEntry submissionEntry) {
			log.enter("apply");
			
			model.setStatus(SubmissionModel.STATUS_SAVED);

			owner.fireOnSubmissionUpdateSuccess(submissionEntry);
			
			log.leave();
			return submissionEntry;
		}
		
	}
	
	
	
	class UpdateSubmissionEntryErrback implements Function<Throwable,Throwable> {

		private Log log = LogFactory.getLog(this.getClass());

		public Throwable apply(Throwable error) {
			log.enter("apply");

			if (error instanceof HttpException) {
				HttpException e = (HttpException) error;
				log.debug(e.getLocalizedMessage());
				log.debug("response code: "+e.getResponse().getStatusCode());
				log.debug(e.getResponse().getText());
			}
			
			model.setStatus(SubmissionModel.STATUS_ERROR);
						
			owner.fireOnSubmissionUpdateError(error);
			
			log.leave();
			return error;
		}

		
	}
	

	
}
