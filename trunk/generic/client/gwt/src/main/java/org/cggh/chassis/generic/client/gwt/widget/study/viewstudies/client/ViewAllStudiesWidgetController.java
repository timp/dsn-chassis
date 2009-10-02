/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFeed;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewAllStudiesWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

/**
 * @author raok
 *
 */
public class ViewAllStudiesWidgetController {

	final private ViewAllStudiesWidgetModel model;
	final private AtomService service;
	final private ViewAllStudiesWidget owner;
	private Log log = LogFactory.getLog(this.getClass());
	private String feedURL;

	public ViewAllStudiesWidgetController(ViewAllStudiesWidgetModel model, AtomService service, ViewAllStudiesWidget owner, String feedURL) {
		this.model = model;
		this.service = service;
		this.owner = owner;
		this.feedURL = feedURL;
	}
	
	public void loadStudiesByFeedURL() {
		log.enter("loadStudiesByFeedURL");
		
		log.trace("loading studies from feed: " + feedURL);
		getStudiesByFeedURL().addCallback(new GetStudyFeedCallback());
		
		log.leave();
	}
	
	Deferred<AtomFeed> getStudiesByFeedURL() {
		return service.getFeed(feedURL);
	}

	class GetStudyFeedCallback implements Function<StudyFeed,StudyFeed> {

		public StudyFeed apply(StudyFeed studyFeed) {
			log.enter("GetStudyFeedCallback::apply");
			
			model.setStudyEntries(studyFeed.getStudyEntries());
			model.setStatus(ViewAllStudiesWidgetModel.STATUS_LOADED);
			log.trace(studyFeed.getStudyEntries().size() + " studies loaded.");
			
			log.leave();
			return studyFeed;
		}
		
	}

	public void onUserSelectStudy(StudyEntry studyEntry) {

		owner.onUserSelectStudy(studyEntry);
	
	}

	public void loadStudiesByEntryURLs(Set<String> relativeStudyEntryURLsToLoad) {
		log.enter("loadStudyEntryURLs");
		
		log.trace(relativeStudyEntryURLsToLoad.size() + " being loaded.");
		
		final List<StudyEntry> studyEntries = new ArrayList<StudyEntry>();
		
		for (String relativeStudyEntryURL : relativeStudyEntryURLsToLoad) {
			
			String studyEntryURL = feedURL + relativeStudyEntryURL;
			
			Deferred<AtomEntry> deferred = service.getEntry(studyEntryURL);
			
			deferred.addCallbacks(new LoadStudiesByEntryURLsCallback(studyEntries, relativeStudyEntryURLsToLoad.size()), new LoadStudiesByEntryURLsErrback());
		}
		
		log.leave();
	}
	
	class LoadStudiesByEntryURLsCallback implements Function<StudyEntry, StudyEntry> {
	
		private List<StudyEntry> studyEntries;
		private Integer noOfStudies;

		public LoadStudiesByEntryURLsCallback(List<StudyEntry> studyEntries, Integer noOfStudies) {
			this.studyEntries = studyEntries;
			this.noOfStudies = noOfStudies;
		}

		public StudyEntry apply(StudyEntry studyEntry) {
			log.enter("LoadStudiesByEntryURLsCallback::apply");
			
			studyEntries.add(studyEntry);
			
			//update model when all studies have been loaded
			if (studyEntries.size() == noOfStudies) {
				model.setStudyEntries(studyEntries);
				model.setStatus(ViewAllStudiesWidgetModel.STATUS_LOADED);
				
				log.trace(noOfStudies + " loaded");
			}
			
			log.leave();
			return studyEntry;
		}
		
	}	

	class LoadStudiesByEntryURLsErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable err) {
			
			return err;
		}
		
	}
	
}
