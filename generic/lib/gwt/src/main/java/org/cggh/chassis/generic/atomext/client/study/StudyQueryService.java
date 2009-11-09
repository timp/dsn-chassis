/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.study;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author aliman
 *
 */
public class StudyQueryService {

	
	
	private Log log = LogFactory.getLog(this.getClass());
	private String serviceUrl;
	
	
	
	/**
	 * @param serviceUrl
	 */
	public StudyQueryService(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.protocol.StudyQueryService#getStudiesByAuthorEmail(java.lang.String)
	 */
	public Deferred<StudyFeed> getStudiesByAuthorEmail(String email) {
		log.enter("getStudiesByAuthorEmail");

		StudyQuery query = new StudyQuery();
		query.setAuthorEmail(email);
		
		log.leave();
		return this.query(query);
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.protocol.StudyQueryService#query(org.cggh.chassis.generic.atom.study.client.protocol.StudyQuery)
	 */
	public Deferred<StudyFeed> query(StudyQuery query) {
		log.enter("query");

		String url = this.serviceUrl + "?";

		if (query.getAuthorEmail() != null) {
			url += "authoremail=" + query.getAuthorEmail() + "&";
		}
		
		if (query.getSubmissionUrl() != null) {
			url += "submission=" + query.getSubmissionUrl() + "&";
		}
		
		log.debug("url: "+url);

		StudyPersistenceService service = new StudyPersistenceService();

		Deferred<StudyFeed> deferredResults = service.getFeed(url);
		
		log.leave();
		return deferredResults;
	}

	
	

}
