/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFeed;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

/**
 * @author raok
 *
 */
public class ViewSubmissionsWidgetController {

	final private ViewSubmissionsWidgetModel model;
	final private AtomService service;
	final private ViewSubmissionsWidget owner;
	private Log log = LogFactory.getLog(this.getClass());
	private String feedURL;

	public ViewSubmissionsWidgetController(ViewSubmissionsWidgetModel model, AtomService service, ViewSubmissionsWidget owner, String feedURL) {
		this.model = model;
		this.service = service;
		this.owner = owner;
		this.feedURL = feedURL;
	}
	
	public void loadSubmissionsByFeedURL() {
		log.enter("loadSubmissionsByFeedURL");
		
		log.trace("loading submissions from feed: " + feedURL);
		getSubmissionsByFeedURL().addCallback(new GetSubmissionFeedCallback());
		
		log.leave();
	}
	
	Deferred<AtomFeed> getSubmissionsByFeedURL() {
		return service.getFeed(feedURL);
	}

	class GetSubmissionFeedCallback implements Function<SubmissionFeed,SubmissionFeed> {

		public SubmissionFeed apply(SubmissionFeed submissionFeed) {
			log.enter("GetSubmissionFeedCallback::apply");
			
			model.setSubmissionEntries(submissionFeed.getSubmissionEntries());
			model.setStatus(ViewSubmissionsWidgetModel.STATUS_LOADED);
			log.trace(submissionFeed.getSubmissionEntries().size() + " submissions loaded.");
			
			log.leave();
			return submissionFeed;
		}
		
	}

	public void onUserSelectSubmission(SubmissionEntry submissionEntry) {

		owner.onUserSelectSubmission(submissionEntry);
	
	}

	public void loadSubmissionsByEntryURLs(Set<String> relativeSubmissionEntryURLsToLoad) {
		log.enter("loadSubmissionEntryURLs");
		
		log.trace(relativeSubmissionEntryURLsToLoad.size() + " being loaded.");
		
		final List<SubmissionEntry> submissionEntries = new ArrayList<SubmissionEntry>();
		
		for (String relativeSubmissionEntryURL : relativeSubmissionEntryURLsToLoad) {
			
			String submissionEntryURL = feedURL + relativeSubmissionEntryURL;
			
			Deferred<AtomEntry> deferred = service.getEntry(submissionEntryURL);
			
			deferred.addCallbacks(new LoadSubmissionsByEntryURLsCallback(submissionEntries, relativeSubmissionEntryURLsToLoad.size()), new LoadSubmissionsByEntryURLsErrback());
		}
		
		log.leave();
	}
	
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
				
				log.trace(noOfSubmissions + " loaded");
			}
			
			log.leave();
			return submissionEntry;
		}
		
	}	

	class LoadSubmissionsByEntryURLsErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable err) {
			
			return err;
		}
		
	}
	
}
