/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.impl.SubmissionFactoryImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.AtomServiceImpl;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

/**
 * @author aliman
 *
 */
public class ViewSubmissionWidgetController {

	
	
	
	private ViewSubmissionWidgetModel model;
	private ViewSubmissionWidget owner;
	private Log log = LogFactory.getLog(this.getClass());
	private SubmissionFactoryImpl submissionFactory;
	private AtomService service;

	
	
	
	/**
	 * @param model
	 * @param owner
	 */
	public ViewSubmissionWidgetController(
			ViewSubmissionWidgetModel model,
			ViewSubmissionWidget owner) {
		
		this.model = model;
		this.owner = owner;
		this.submissionFactory = new SubmissionFactoryImpl();
		this.service = new AtomServiceImpl(this.submissionFactory);

	}




	/**
	 * 
	 */
	public void onUserActionEditThisSubmission() {
		this.owner.fireOnUserActionEditSubmission(this.model.getSubmissionEntry());
	}

	
	
	
	public void setSubmissionEntry(SubmissionEntry entry) {
		log.enter("setSubmissionEntry");
		
		model.setSubmissionEntry(entry);
		model.setStatus(ViewSubmissionWidgetModel.STATUS_READY);

//		log.debug("submissionEntryToLoad: " + entry.toString());		
		
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

	
	
	
	//package private for testing purposes
	class RetrieveSubmissionEntryCallback implements Function<SubmissionEntry,SubmissionEntry> {

		public SubmissionEntry apply(SubmissionEntry submissionEntry) {
			
			model.setSubmissionEntry(submissionEntry);
			model.setStatus(ViewSubmissionWidgetModel.STATUS_READY);

			return submissionEntry;
		}
		
	}
	
	
	
	
	//package private for testing purposes
	class RetrieveSubmissionEntryErrback implements Function<Throwable,Throwable> {

		public Throwable apply(Throwable error) {
			model.setStatus(ViewSubmissionWidgetModel.STATUS_RETRIEVE_ERROR);
			return error;
		}

		
	}
	
	
	
}
