/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionFeed;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionPersistenceService;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionQueryService;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author raok
 *
 */
public class MySubmissionsWidgetController {
	private Log log = LogFactory.getLog(this.getClass());

	final private MySubmissionsWidgetModel model;
//	final private AtomService persistenceService;
	final private MySubmissionsWidget owner;
	private String submissionCollectionUrl;
	private SubmissionQueryService submissionQueryService;

	public MySubmissionsWidgetController(MySubmissionsWidgetModel model, MySubmissionsWidget owner) {
		this.model = model;
		this.owner = owner;
				
		this.submissionCollectionUrl = Configuration.getSubmissionCollectionUrl();
		
//		this.persistenceService = new AtomServiceImpl(new SubmissionFactoryImpl());
		
		String serviceUrl = Configuration.getSubmissionQueryServiceUrl();
		this.submissionQueryService = new SubmissionQueryService(serviceUrl);
		
	}
	
	public void loadSubmissionsByCollectionUrl() {
		log.enter("loadSubmissionsByCollectionUrl");
		
		log.debug("loading submissions from feed: " + submissionCollectionUrl);
//		Deferred<AtomFeed> deferred = persistenceService.getFeed(submissionCollectionUrl);
		SubmissionPersistenceService service = new SubmissionPersistenceService();
		Deferred<SubmissionFeed> def = service.getFeed(submissionCollectionUrl);
		def.addCallbacks(new LoadSubmissionFeedCallback(), new LoadSubmissionFeedErrback()); // TODO
		
		log.leave();
	}

	//package private for testing purposes
	class LoadSubmissionFeedCallback implements Function<SubmissionFeed,SubmissionFeed> {

		public SubmissionFeed apply(SubmissionFeed submissionFeed) {
			log.enter("LoadSubmissionFeedCallback::apply");
			
			model.setSubmissionEntries(submissionFeed.getEntries());
			model.setStatus(MySubmissionsWidgetModel.STATUS_LOADED);
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

	public void loadSubmissionsByEntryUrls(Set<String> relativeSubmissionEntryUrlsToLoad) {
		log.enter("loadSubmissionEntryUrls");
		
		log.debug(relativeSubmissionEntryUrlsToLoad.size() + " being loaded.");
		
		final List<SubmissionEntry> submissionEntries = new ArrayList<SubmissionEntry>();
		
		for (String relativeSubmissionEntryUrl : relativeSubmissionEntryUrlsToLoad) {
			
			String submissionEntryUrl = submissionCollectionUrl + relativeSubmissionEntryUrl;
			
			SubmissionPersistenceService service = new SubmissionPersistenceService();
			Deferred<SubmissionEntry> def = service.getEntry(submissionEntryUrl);
//			Deferred<AtomEntry> deferred = persistenceService.getEntry(submissionEntryUrl);
			
			def.addCallbacks(new LoadSubmissionsByEntryUrlsCallback(submissionEntries, relativeSubmissionEntryUrlsToLoad.size()), new LoadSubmissionsByEntryUrlsErrback()); // TODO
		}
		
		log.leave();
	}

	//package private for testing purposes
	class LoadSubmissionsByEntryUrlsCallback implements Function<SubmissionEntry, SubmissionEntry> {
	
		private List<SubmissionEntry> submissionEntries;
		private Integer noOfSubmissions;

		public LoadSubmissionsByEntryUrlsCallback(List<SubmissionEntry> submissionEntries, Integer noOfSubmissions) {
			this.submissionEntries = submissionEntries;
			this.noOfSubmissions = noOfSubmissions;
		}

		public SubmissionEntry apply(SubmissionEntry submissionEntry) {
			log.enter("LoadSubmissionsByEntryUrlsCallback::apply");
			
			submissionEntries.add(submissionEntry);
			
			//update model when all submissions have been loaded
			if (submissionEntries.size() == noOfSubmissions) {
				model.setSubmissionEntries(submissionEntries);
				model.setStatus(MySubmissionsWidgetModel.STATUS_LOADED);
				
				log.debug(noOfSubmissions + " loaded");
			}
			
			log.leave();
			return submissionEntry;
		}
		
	}	

	//package private for testing purposes
	class LoadSubmissionsByEntryUrlsErrback implements Function<Throwable, Throwable> {

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
