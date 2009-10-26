/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.edit.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.impl.SubmissionFactoryImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.AtomServiceImpl;
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
	private SubmissionFactoryImpl submissionFactory;
	private AtomServiceImpl service;

	
	
	
	/**
	 * @param owner
	 * @param model
	 */
	public EditSubmissionWidgetController(
			EditSubmissionWidget owner,
			EditSubmissionWidgetModel model) {

		this.owner = owner;
		this.model = model;
		this.submissionFactory = new SubmissionFactoryImpl();
		this.service = new AtomServiceImpl(this.submissionFactory);
		
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
		
		this.owner.fireOnUserActionEditNewSubmissionCancelled();
		
		log.leave();
	}




	/**
	 * @param entry 
	 * 
	 */
	public void saveNewSubmissionEntry(SubmissionEntry entry) {
		log.enter("saveNewSubmissionEntry");
		
		log.debug("entry to save: "+entry.toString());
		
		this.model.setStatus(EditSubmissionWidgetModel.STATUS_SAVING);
		
		String feedUrl = Configuration.getSubmissionFeedURL();

		log.debug("kick off post request");
		Deferred<AtomEntry> def = service.postEntry(feedUrl, entry);
		
		log.debug("add callbacks");
		def.addCallback(new SaveSubmissionEntryCallback());
		def.addErrback(new SaveSubmissionEntryErrback());
		
		log.leave();
	}

	
	
	
	class SaveSubmissionEntryCallback implements Function<SubmissionEntry,SubmissionEntry> {

		private Log log = LogFactory.getLog(this.getClass());

		public SubmissionEntry apply(SubmissionEntry submissionEntry) {
			log.enter("apply");
			
			model.setStatus(SubmissionModel.STATUS_SAVED);

			owner.fireOnNewSubmissionSaveSuccess(submissionEntry);
			
			log.leave();
			return submissionEntry;
		}

		
	}
	
	
	
	class SaveSubmissionEntryErrback implements Function<Throwable,Throwable> {

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
						
			owner.fireOnNewSubmissionSaveError(error);
			
			log.leave();
			return error;
		}

		
	}
	

	
}
