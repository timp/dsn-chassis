/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.create.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.impl.SubmissionFactoryImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.AtomServiceImpl;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;
import org.cggh.chassis.generic.twisted.client.HttpException;

import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;

/**
 * @author aliman
 *
 */
public class CreateSubmissionWidgetController {

	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private CreateSubmissionWidget owner;
	private CreateSubmissionWidgetModel model;
	private SubmissionFactoryImpl submissionFactory;
	private AtomServiceImpl service;

	
	
	
	/**
	 * @param owner
	 * @param model
	 */
	public CreateSubmissionWidgetController(
			CreateSubmissionWidget owner,
			CreateSubmissionWidgetModel model) {

		this.owner = owner;
		this.model = model;
		this.submissionFactory = new SubmissionFactoryImpl();
		this.service = new AtomServiceImpl(this.submissionFactory);
		
	}
	
	
	
	
	/**
	 * 
	 */
	public void ready() {
		
		this.model.setStatus(CreateSubmissionWidgetModel.STATUS_READY);
		
	}




	/**
	 * 
	 */
	public void cancelCreateSubmissionEntry() {
		log.enter("cancelCreateSubmissionEntry");
		
		this.model.setStatus(CreateSubmissionWidgetModel.STATUS_CANCELLED);
		
		this.owner.fireOnUserActionCreateSubmissionCancelled();
		
		log.leave();
	}




	/**
	 * @param entry 
	 * 
	 */
	public void createSubmissionEntry(SubmissionEntry entry) {
		log.enter("createSubmissionEntry");
		
		this.model.setStatus(CreateSubmissionWidgetModel.STATUS_CREATE_PENDING);
		
		String feedUrl = Configuration.getSubmissionFeedURL();

		log.debug("kick off post request");
		Deferred<AtomEntry> def = service.postEntry(feedUrl, entry);
		
		log.debug("add callbacks");
		def.addCallback(new CreateSubmissionEntryCallback());
		def.addErrback(new CreateSubmissionEntryErrback());
		
		log.leave();
	}

	
	
	
	class CreateSubmissionEntryCallback implements Function<SubmissionEntry,SubmissionEntry> {

		private Log log = LogFactory.getLog(this.getClass());

		public SubmissionEntry apply(SubmissionEntry submissionEntry) {
			log.enter("apply");
			
			model.setStatus(CreateSubmissionWidgetModel.STATUS_CREATE_SUCCESS);

			owner.fireOnCreateSubmissionSuccess(submissionEntry);
			
			log.leave();
			return submissionEntry;
		}

		
	}
	
	
	
	class CreateSubmissionEntryErrback implements Function<Throwable,Throwable> {

		private Log log = LogFactory.getLog(this.getClass());

		public Throwable apply(Throwable error) {
			log.enter("apply");

			if (error instanceof HttpException) {
				HttpException e = (HttpException) error;
				log.debug(e.getLocalizedMessage());
				log.debug("response code: "+e.getResponse().getStatusCode());
				log.debug(e.getResponse().getText());
			}
			
			model.setStatus(CreateSubmissionWidgetModel.STATUS_CREATE_ERROR);
						
			owner.fireOnCreateSubmissionError(error);
			
			log.leave();
			return error;
		}

		
	}
	

	
}
