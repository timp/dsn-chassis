/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.submission;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;

/**
 * @author aliman
 *
 */
public class SubmissionQueryService {

	
	
	private Log log = LogFactory.getLog(this.getClass());
	private String serviceUrl;
	
	
	
	/**
	 * @param serviceUrl
	 */
	public SubmissionQueryService(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.protocol.SubmissionQueryService#getSubmissionsByAuthorEmail(java.lang.String)
	 */
	public Deferred<SubmissionFeed> getSubmissionsByAuthorEmail(String email) {
		log.enter("getSubmissionsByAuthorEmail");

		String url = this.serviceUrl + "?authoremail=" + email;
		log.debug("url: "+url);

		SubmissionPersistenceService service = new SubmissionPersistenceService();
		Deferred<SubmissionFeed> deferredFeed = service.getFeed(url);

		log.leave();
		return deferredFeed;
	}

	
	
}
