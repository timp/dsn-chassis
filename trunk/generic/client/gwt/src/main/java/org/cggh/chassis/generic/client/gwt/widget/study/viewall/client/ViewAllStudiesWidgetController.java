/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.viewall.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFeed;
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
	
	public void loadStudies() {
		log.enter("loadStudiesByFeedURL");
		
		log.trace("loading studies from feed: " + feedURL);
		getStudies().addCallback(new GetStudyFeedCallback());
		
		log.leave();
	}
	
	Deferred<AtomFeed> getStudies() {
		return service.getFeed(feedURL);
	}

	class GetStudyFeedCallback implements Function<StudyFeed,StudyFeed> {

		public StudyFeed apply(StudyFeed studyFeed) {
			
			model.setStudyEntries(studyFeed.getStudyEntries());
			model.setStatus(ViewAllStudiesWidgetModel.STATUS_LOADED);
			
			return studyFeed;
		}
		
	}

	public void onViewStudyUIClicked(StudyEntry studyEntry) {

		owner.onViewStudyUIClicked(studyEntry);
	
	}
	
}
