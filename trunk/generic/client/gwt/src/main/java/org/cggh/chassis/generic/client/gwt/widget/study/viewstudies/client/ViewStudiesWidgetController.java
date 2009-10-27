/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFeed;
import org.cggh.chassis.generic.atom.study.client.format.impl.StudyFactoryImpl;
import org.cggh.chassis.generic.atom.study.client.protocol.StudyQuery;
import org.cggh.chassis.generic.atom.study.client.protocol.StudyQueryService;
import org.cggh.chassis.generic.atom.study.client.protocol.impl.StudyQueryServiceImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.AtomServiceImpl;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewStudiesWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

/**
 * @author raok
 *
 */
public class ViewStudiesWidgetController {
	private Log log = LogFactory.getLog(this.getClass());

	final private ViewStudiesWidgetModel model;
	final private AtomService persistenceService;
	final private ViewStudiesWidget owner;
	private String studyFeedURL;
	private StudyQueryService studyQueryService;

	public ViewStudiesWidgetController(ViewStudiesWidgetModel model, ViewStudiesWidget owner) {
		this.model = model;
		this.owner = owner;
		
		//Get studyFeedURL from config
		this.studyFeedURL = ConfigurationBean.getStudyFeedURL();
		
		this.persistenceService = new AtomServiceImpl(new StudyFactoryImpl());
		
		String serviceUrl = ConfigurationBean.getStudyQueryServiceURL();
		this.studyQueryService = new StudyQueryServiceImpl(serviceUrl);
	}
	
	public void loadStudiesByFeedURL() {
		log.enter("loadStudiesByFeedURL");
		
		log.debug("loading studies from feed: " + studyFeedURL);
		Deferred<AtomFeed> deferred = persistenceService.getFeed(studyFeedURL);
		deferred.addCallbacks(new LoadStudyFeedCallback(), new LoadStudyFeedErrback());
		
		log.leave();
	}

	//package private for testing purposes
	class LoadStudyFeedCallback implements Function<StudyFeed,StudyFeed> {

		public StudyFeed apply(StudyFeed studyFeed) {
			log.enter("LoadStudyFeedCallback :: apply");
			
			model.setStudyEntries(studyFeed.getStudyEntries());
			model.setStatus(ViewStudiesWidgetModel.STATUS_LOADED);
			log.debug(studyFeed.getStudyEntries().size() + " studies loaded.");
			
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
			
			Deferred<AtomEntry> deferred = persistenceService.getEntry(studyEntryURL);
			
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
				model.setStatus(ViewStudiesWidgetModel.STATUS_LOADED);
				
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
