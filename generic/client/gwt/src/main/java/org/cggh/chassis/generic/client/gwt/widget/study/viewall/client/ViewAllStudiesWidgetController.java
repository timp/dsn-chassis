/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.viewall.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFeed;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
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

	public ViewAllStudiesWidgetController(ViewAllStudiesWidgetModel model, AtomService service, ViewAllStudiesWidget owner) {
		this.model = model;
		this.service = service;
		this.owner = owner;
	}
	
	public void loadStudiesByFeedURL(String feedURL) {
		getStudiesByFeedURL(feedURL).addCallback(new GetStudyFeedEntryCallback());
	}
	
	Deferred<AtomFeed> getStudiesByFeedURL(String feedURL) {
		return service.getFeed(feedURL);
	}

	class GetStudyFeedEntryCallback implements Function<StudyFeed,StudyFeed> {

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
