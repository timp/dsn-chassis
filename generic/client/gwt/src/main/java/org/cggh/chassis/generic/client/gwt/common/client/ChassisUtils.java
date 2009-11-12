/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.common.client;

import java.util.HashMap;
import java.util.Map;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFeed;
import org.cggh.chassis.generic.atomext.client.study.StudyQueryService;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;

/**
 * @author aliman
 *
 */
public class ChassisUtils {

	
	
	public static Deferred<Map<String,String>> getMapOfStudyLinksToTitlesForCurrentUser() {
		
		StudyQueryService service = new StudyQueryService(Configuration.getStudyQueryServiceUrl());
		
		Deferred<StudyFeed> deferredFeed = service.getStudiesByAuthorEmail(ChassisUser.getCurrentUserEmail());
		
		Deferred<Map<String,String>> deferredMap = deferredFeed.adapt(new Function<StudyFeed, Map<String,String>>() {

			public Map<String, String> apply(StudyFeed in) {
				Map<String,String> studyLinks = new HashMap<String,String>();
				for (StudyEntry e : in.getEntries()) {
					String title = e.getTitle();
//					String link = Configuration.getStudyCollectionUrl() + e.getEditLink().getHref();
					String link = e.getEditLink().getHref(); // TODO fix for aboslute URIs
					studyLinks.put(link, title);
				}
				return studyLinks;
			}
			
		});
		return deferredMap;
		
	}
	
	
	
}
