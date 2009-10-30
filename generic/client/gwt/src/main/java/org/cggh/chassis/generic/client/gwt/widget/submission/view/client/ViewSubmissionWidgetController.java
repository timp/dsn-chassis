/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFactory;
import org.cggh.chassis.generic.atom.submission.client.format.impl.SubmissionFactoryImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.AtomServiceImpl;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
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
	private SubmissionFactory submissionFactory;
	private AtomService service;

	
	
	public ViewSubmissionWidgetController(ViewSubmissionWidgetModel model) {
		log.enter("<constructor>");
		
		this.model = model;

		log.debug("instantiate helpers");
		this.submissionFactory = new SubmissionFactoryImpl();
		this.service = new AtomServiceImpl(this.submissionFactory);
		
		log.leave();
	}
	
	


	/**
	 * Set the submission entry to display properties for.
	 * 
	 * @param entry
	 */
	public void setSubmissionEntry(SubmissionEntry entry) {
		log.enter("setSubmissionEntry");

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
		Deferred<AtomEntry> deferred = service.getEntry(submissionEntryUrl);
		
		//add callbacks
		deferred.addCallback(new RetrieveSubmissionEntryCallback());
		deferred.addErrback(new RetrieveSubmissionEntryErrback());
		
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
