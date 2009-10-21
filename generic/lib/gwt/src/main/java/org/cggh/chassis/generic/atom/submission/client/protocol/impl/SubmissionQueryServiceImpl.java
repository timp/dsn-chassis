/**
 * 
 */
package org.cggh.chassis.generic.atom.submission.client.protocol.impl;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFactory;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFeed;
import org.cggh.chassis.generic.atom.submission.client.format.impl.SubmissionFactoryImpl;
import org.cggh.chassis.generic.atom.submission.client.protocol.SubmissionQueryService;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.AtomServiceImpl;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

/**
 * @author aliman
 *
 */
public class SubmissionQueryServiceImpl implements SubmissionQueryService {

	
	
	private Log log = LogFactory.getLog(this.getClass());
	private String serviceUrl;
	
	
	
	/**
	 * @param serviceUrl
	 */
	public SubmissionQueryServiceImpl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.protocol.SubmissionQueryService#getSubmissionsByAuthorEmail(java.lang.String)
	 */
	public Deferred<SubmissionFeed> getSubmissionsByAuthorEmail(String email) {
		log.enter("getSubmissionsByAuthorEmail");

		String url = this.serviceUrl + "?authoremail=" + email;
		log.debug("url: "+url);

		SubmissionFactory factory = new SubmissionFactoryImpl();
		AtomService service = new AtomServiceImpl(factory);

		Deferred<AtomFeed> deferredFeed = service.getFeed(url);

		Function<AtomFeed,SubmissionFeed> adapter = new Function<AtomFeed,SubmissionFeed>() {
			public SubmissionFeed apply(AtomFeed in) {
				log.enter("[anonymous adapter function]");
				log.debug("found "+in.getEntries().size()+" results; casting as submission feed");
				log.leave();
				return (SubmissionFeed) in;
			}
		};
		
		Deferred<SubmissionFeed> deferredResults = deferredFeed.adapt(adapter);

		log.leave();
		return deferredResults;
	}

	
	
}
