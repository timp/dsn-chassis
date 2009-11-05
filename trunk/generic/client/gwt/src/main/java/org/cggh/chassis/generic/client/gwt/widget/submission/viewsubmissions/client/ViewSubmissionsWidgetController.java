/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionFeed;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionPersistenceService;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionQueryService;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

/**
 * @author raok
 *
 */
public class ViewSubmissionsWidgetController {
	private Log log = LogFactory.getLog(this.getClass());

	final private ViewSubmissionsWidgetModel model;
//	final private AtomService persistenceService;
	final private ViewSubmissionsWidget owner;
	private String submissionFeedURL;
	private SubmissionQueryService submissionQueryService;

	public ViewSubmissionsWidgetController(ViewSubmissionsWidgetModel model, ViewSubmissionsWidget owner) {
		this.model = model;
		this.owner = owner;
				
		this.submissionFeedURL = ConfigurationBean.getSubmissionFeedURL();
		
//		this.persistenceService = new AtomServiceImpl(new SubmissionFactoryImpl());
		
		String serviceUrl = ConfigurationBean.getSubmissionQueryServiceURL();
		this.submissionQueryService = new SubmissionQueryService(serviceUrl);
		
	}
	
	public void loadSubmissionsByFeedURL() {
		log.enter("loadSubmissionsByFeedURL");
		
		log.debug("loading submissions from feed: " + submissionFeedURL);
//		Deferred<AtomFeed> deferred = persistenceService.getFeed(submissionFeedURL);
		SubmissionPersistenceService service = new SubmissionPersistenceService();
		Deferred<SubmissionFeed> def = service.getFeed(submissionFeedURL);
		def.addCallbacks(new LoadSubmissionFeedCallback(), new LoadSubmissionFeedErrback()); // TODO
		
		log.leave();
	}

	//package private for testing purposes
	class LoadSubmissionFeedCallback implements Function<SubmissionFeed,SubmissionFeed> {

		public SubmissionFeed apply(SubmissionFeed submissionFeed) {
			log.enter("LoadSubmissionFeedCallback::apply");
			
			model.setSubmissionEntries(submissionFeed.getEntries());
			model.setStatus(ViewSubmissionsWidgetModel.STATUS_LOADED);
			log.debug(submissionFeed.getEntries().size() + " submissions loaded.");
			
			log.leave();
			return submissionFeed;
		}
		
	}

	//package private for testing purposes
	class LoadSubmissionFeedErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable err) {
			log.enter("LoadSubmissionFeedErrback::apply");
			
			log.leave();
			return err;
		}
		
	}

	public void onUserSelectSubmission(SubmissionEntry submissionEntry) {

		owner.onUserSelectSubmission(submissionEntry);
	
	}

	public void loadSubmissionsByEntryURLs(Set<String> relativeSubmissionEntryURLsToLoad) {
		log.enter("loadSubmissionEntryURLs");
		
		log.debug(relativeSubmissionEntryURLsToLoad.size() + " being loaded.");
		
		final List<SubmissionEntry> submissionEntries = new ArrayList<SubmissionEntry>();
		
		for (String relativeSubmissionEntryURL : relativeSubmissionEntryURLsToLoad) {
			
			String submissionEntryURL = submissionFeedURL + relativeSubmissionEntryURL;
			
			SubmissionPersistenceService service = new SubmissionPersistenceService();
			Deferred<SubmissionEntry> def = service.getEntry(submissionEntryURL);
//			Deferred<AtomEntry> deferred = persistenceService.getEntry(submissionEntryURL);
			
			def.addCallbacks(new LoadSubmissionsByEntryURLsCallback(submissionEntries, relativeSubmissionEntryURLsToLoad.size()), new LoadSubmissionsByEntryURLsErrback()); // TODO
		}
		
		log.leave();
	}

	//package private for testing purposes
	class LoadSubmissionsByEntryURLsCallback implements Function<SubmissionEntry, SubmissionEntry> {
	
		private List<SubmissionEntry> submissionEntries;
		private Integer noOfSubmissions;

		public LoadSubmissionsByEntryURLsCallback(List<SubmissionEntry> submissionEntries, Integer noOfSubmissions) {
			this.submissionEntries = submissionEntries;
			this.noOfSubmissions = noOfSubmissions;
		}

		public SubmissionEntry apply(SubmissionEntry submissionEntry) {
			log.enter("LoadSubmissionsByEntryURLsCallback::apply");
			
			submissionEntries.add(submissionEntry);
			
			//update model when all submissions have been loaded
			if (submissionEntries.size() == noOfSubmissions) {
				model.setSubmissionEntries(submissionEntries);
				model.setStatus(ViewSubmissionsWidgetModel.STATUS_LOADED);
				
				log.debug(noOfSubmissions + " loaded");
			}
			
			log.leave();
			return submissionEntry;
		}
		
	}	

	//package private for testing purposes
	class LoadSubmissionsByEntryURLsErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable err) {
			
			return err;
		}
		
	}

	public void loadSubmissionsByAuthorEmail(String authorEmail) {
		log.enter("loadSubmissionsByAuthorEmail");
		
		log.debug("loading submissions with authorEmail: " + authorEmail);
		Deferred<SubmissionFeed> deferred = submissionQueryService.getSubmissionsByAuthorEmail(authorEmail);
		deferred.addCallbacks(new LoadSubmissionFeedCallback(), new LoadSubmissionFeedErrback());
		
		log.leave();
		
	}
	
}
