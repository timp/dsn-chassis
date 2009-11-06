/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;
import org.cggh.chassis.generic.widget.client.AsyncErrback;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionPersistenceService;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;

/**
 * @author aliman
 *
 */
public class NewSubmissionWidgetController {

	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private NewSubmissionWidget owner;
	private AsyncWidgetModel model;

	
	
	
	/**
	 * @param owner
	 * @param model
	 */
	public NewSubmissionWidgetController(
			NewSubmissionWidget owner,
			AsyncWidgetModel model) {

		this.owner = owner;
		this.model = model;
		
	}
	
	
	

	/**
	 * @param entry 
	 * 
	 */
	public void createSubmissionEntry(SubmissionEntry entry) {
		log.enter("createSubmissionEntry");
		
		this.model.setStatus(AsyncWidgetModel.STATUS_ASYNC_REQUEST_PENDING);
		
		SubmissionPersistenceService service = new SubmissionPersistenceService();

		log.debug("kick off post request");
		Deferred<SubmissionEntry> def = service.postEntry(Configuration.getSubmissionFeedURL(), entry);
		
		log.debug("add callbacks");
		def.addCallback(new CreateSubmissionEntryCallback());
		def.addErrback(new AsyncErrback(this.owner, this.model));
		
		log.leave();
	}

	
	
	
	class CreateSubmissionEntryCallback implements Function<SubmissionEntry,SubmissionEntry> {

		private Log log = LogFactory.getLog(this.getClass());

		public SubmissionEntry apply(SubmissionEntry entry) {
			log.enter("apply");
			
			model.setStatus(AsyncWidgetModel.STATUS_READY);
			
			CreateSubmissionSuccessEvent e = new CreateSubmissionSuccessEvent();
			e.setSubmissionEntry(entry);
			owner.fireEvent(e);
			
			log.leave();
			return entry;
		}

	}
	
	
	
}
