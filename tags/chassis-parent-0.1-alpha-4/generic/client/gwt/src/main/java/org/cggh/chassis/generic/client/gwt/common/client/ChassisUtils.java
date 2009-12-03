/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.common.client;

import java.util.HashMap;
import java.util.Map;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileFeed;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileQueryService;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFeed;
import org.cggh.chassis.generic.atomext.client.study.StudyQuery;
import org.cggh.chassis.generic.atomext.client.study.StudyQueryService;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author aliman
 *
 */
public class ChassisUtils {

	
	
	
	private static Log log = LogFactory.getLog(ChassisUtils.class);
	
	
	
	
	public static Deferred<Map<String,String>> getMapOfStudyLinksToTitlesForCurrentUser() {
		
		StudyQueryService service = new StudyQueryService(Configuration.getStudyQueryServiceUrl());
		StudyQuery query = new StudyQuery();
		query.setAuthorEmail(ChassisUser.getCurrentUserEmail());
		Deferred<StudyFeed> deferredFeed = service.query(query);
		
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

	/**
	 * @return
	 */
	public static Deferred<Map<String, String>> getMapOfDataFileLinksToTitlesForCurrentUser() {
		log.enter("getMapOfDataFileLinksToTitlesForCurrentUser");
		
		DataFileQueryService service = new DataFileQueryService(Configuration.getDataFileQueryServiceUrl());
		
		Deferred<DataFileFeed> deferredFeed = service.getDataFilesByAuthorEmail(ChassisUser.getCurrentUserEmail());
		
		Deferred<Map<String,String>> deferredMap = deferredFeed.adapt(new Function<DataFileFeed, Map<String,String>>() {
			private Log log = LogFactory.getLog(this.getClass());
			public Map<String, String> apply(DataFileFeed in) {
				log.enter("getMapOfDataFileLinksToTitlesForCurrentUser anon callback :: apply");
				log.debug(in.toString());
				Map<String,String> dataFileLinks = new HashMap<String,String>();
				for (DataFileEntry e : in.getEntries()) {
					String title = e.getTitle();
					String link = e.getEditLink().getHref(); // TODO fix for aboslute URIs
					log.debug("found entry, title: "+title+"; link: "+link);
					dataFileLinks.put(link, title);
				}
				log.leave();
				return dataFileLinks;
			}
			
		});
		
		log.leave();
		return deferredMap;
	}
	
	
	
}
