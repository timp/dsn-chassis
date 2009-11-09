/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFeed;
import org.cggh.chassis.generic.atomext.client.study.StudyPersistenceService;
import org.cggh.chassis.generic.atomext.client.study.StudyQuery;
import org.cggh.chassis.generic.atomext.client.study.StudyQueryService;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.client.gwt.widget.study.client.MyStudiesWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author raok
 *
 */
public class MyStudiesWidgetController {
	private Log log = LogFactory.getLog(this.getClass());

	final private MyStudiesWidgetModel model;
	final private StudyPersistenceService persistenceService;
	final private MyStudiesWidget owner;
	private String studyFeedURL;
	private StudyQueryService studyQueryService;

	public MyStudiesWidgetController(MyStudiesWidgetModel model, MyStudiesWidget owner) {
		this.model = model;
		this.owner = owner;
		
		//Get studyFeedURL from config
		this.studyFeedURL = ConfigurationBean.getStudyFeedURL();
		
		this.persistenceService = new StudyPersistenceService();
		
		String serviceUrl = ConfigurationBean.getStudyQueryServiceURL();
		this.studyQueryService = new StudyQueryService(serviceUrl);
	}
	
	public void loadStudiesByFeedURL() {
		log.enter("loadStudiesByFeedURL");
		
		log.debug("loading studies from feed: " + studyFeedURL);
		Deferred<StudyFeed> deferred = persistenceService.getFeed(studyFeedURL);
		deferred.addCallbacks(new LoadStudyFeedCallback(), new LoadStudyFeedErrback());
		
		log.leave();
	}

	//package private for testing purposes
	class LoadStudyFeedCallback implements Function<StudyFeed,StudyFeed> {

		public StudyFeed apply(StudyFeed studyFeed) {
			log.enter("LoadStudyFeedCallback :: apply");
			
			model.setStudyEntries(studyFeed.getEntries());
			model.setStatus(MyStudiesWidgetModel.STATUS_LOADED);
			log.debug(studyFeed.getEntries().size() + " studies loaded.");
			
			log.leave();
			return studyFeed;
		}
		
	}

	//package private for testing purposes
	class LoadStudyFeedErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable err) {
			log.enter("LoadStudyFeedErrback :: apply");
			
			log.error("an error occurred retrieving study feed: "+err.getLocalizedMessage(), err);
			// TODO error handling
			
			log.leave();
			return err;
		}
		
	}

	public void onUserSelectStudy(StudyEntry studyEntry) {

		owner.onUserSelectStudy(studyEntry);
	
	}

	public void loadStudiesByEntryURLs(Set<String> relativeStudyEntryURLsToLoad) {
		log.enter("loadStudyEntryURLs");
		
		log.debug(relativeStudyEntryURLsToLoad.size() + " being loaded.");
		
		final List<StudyEntry> studyEntries = new ArrayList<StudyEntry>();
		
		for (String relativeStudyEntryURL : relativeStudyEntryURLsToLoad) {
			
			String studyEntryURL = studyFeedURL + relativeStudyEntryURL;
			
			Deferred<StudyEntry> deferred = persistenceService.getEntry(studyEntryURL);
			
			deferred.addCallbacks(new LoadStudiesByEntryURLsCallback(studyEntries, relativeStudyEntryURLsToLoad.size()), new LoadStudiesByEntryURLsErrback());
		}
		
		log.leave();
	}

	//package private for testing purposes
	class LoadStudiesByEntryURLsCallback implements Function<StudyEntry, StudyEntry> {
	
		private List<StudyEntry> studyEntries;
		private Integer noOfStudies;

		public LoadStudiesByEntryURLsCallback(List<StudyEntry> studyEntries, Integer noOfStudies) {
			this.studyEntries = studyEntries;
			this.noOfStudies = noOfStudies;
		}

		public StudyEntry apply(StudyEntry studyEntry) {
			log.enter("LoadStudiesCallback::apply");
			
			studyEntries.add(studyEntry);
			
			//update model when all studies have been loaded
			if (studyEntries.size() == noOfStudies) {
				model.setStudyEntries(studyEntries);
				model.setStatus(MyStudiesWidgetModel.STATUS_LOADED);
				
				log.debug(noOfStudies + " studies loaded");
			}
			
			log.leave();
			return studyEntry;
		}
		
	}	

	//package private for testing purposes
	class LoadStudiesByEntryURLsErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable err) {
			
			return err;
		}
		
	}

	public void loadStudiesByAuthorEmail(String authorEmail) {
		log.enter("loadStudiesByAuthorEmail");
		
		log.debug("request studies for author email: "+authorEmail);
		
		Deferred<StudyFeed> deferred = studyQueryService.getStudiesByAuthorEmail(authorEmail);
		
		deferred.addCallback(new LoadStudyFeedCallback());
		deferred.addErrback(new LoadStudyFeedErrback());
		
		log.leave();
	}

	/**
	 * @param query
	 */
	public void loadStudies(StudyQuery query) {

		Deferred<StudyFeed> deferred = studyQueryService.query(query);
		
		deferred.addCallback(new LoadStudyFeedCallback());
		deferred.addErrback(new LoadStudyFeedErrback());

	}
	
}
