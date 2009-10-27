/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.common.client;

import java.util.HashMap;
import java.util.Map;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFeed;
import org.cggh.chassis.generic.atom.study.client.protocol.StudyQueryService;
import org.cggh.chassis.generic.atom.study.client.protocol.impl.StudyQueryServiceImpl;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

/**
 * @author aliman
 *
 */
public class ChassisUtils {

	
	
	public static Deferred<Map<String,String>> getMapOfStudyLinksToTitlesForCurrentUser() {
		
		StudyQueryService service = new StudyQueryServiceImpl(Configuration.getStudyQueryServiceURL());
		
		Deferred<StudyFeed> deferredFeed = service.getStudiesByAuthorEmail(ChassisUser.getCurrentUserEmail());
		
		Deferred<Map<String,String>> deferredMap = deferredFeed.adapt(new Function<StudyFeed, Map<String,String>>() {

			public Map<String, String> apply(StudyFeed in) {
				Map<String,String> studyLinks = new HashMap<String,String>();
				for (StudyEntry e : in.getStudyEntries()) {
					String title = e.getTitle();
//					String link = Configuration.getStudyFeedURL() + e.getEditLink().getHref();
					String link = e.getEditLink().getHref(); // TODO fix for aboslute URIs
					studyLinks.put(link, title);
				}
				return studyLinks;
			}
			
		});
		return deferredMap;
		
	}
	
	
	
}
