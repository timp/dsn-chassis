/**
 * 
 */
package org.cggh.chassis.generic.atom.submission.client.mockimpl;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFeed;
import org.cggh.chassis.generic.atom.submission.client.protocol.SubmissionQueryService;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomPersonConstruct;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomStore;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.NotFoundException;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;

/**
 * @author aliman
 *
 */
public class MockSubmissionQueryService implements SubmissionQueryService {

	private String serviceURL;
	private Log log = LogFactory.getLog(this.getClass());
	private MockSubmissionFactory factory = new MockSubmissionFactory();
	private MockAtomStore store;

	public MockSubmissionQueryService(String serviceURL) {
		this.serviceURL = serviceURL;
		this.store = new MockAtomStore(factory);
	}
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.protocol.SubmissionQueryService#query(org.cggh.chassis.generic.atom.submission.client.protocol.SubmissionQuery)
	 */
	public Deferred<SubmissionFeed> getSubmissionsByAuthorEmail(String email) {
		log.enter("getSubmissionsByAuthorEmail");
		
		// TODO Auto-generated method stub
		
		log.trace("create deferred submission feed for results");
		Deferred<SubmissionFeed> deferred = new Deferred<SubmissionFeed>();
		
		log.trace("hack to get feed URL for all submissions");
		String submissionFeedURL = serviceURL.replaceFirst("query", "edit"); // hack, assume we can get from submission query service URL to submission feed URL by replacing "query" with "edit"
		
		log.trace("set up feed to hold results");
		MockSubmissionFeed results = factory.createMockSubmissionFeed("query submissions by author email results ["+email+"]");

		try {
			
			log.trace("fish out the feed to query");
			AtomFeed submissions = store.retrieveAll(submissionFeedURL);

			log.trace("execute the query");
			for (AtomEntry entry : submissions.getEntries()) {
				if (entry instanceof MockSubmissionEntry) {
					MockSubmissionEntry submission = (MockSubmissionEntry) entry;
					for (AtomPersonConstruct author : entry.getAuthors()) {
						if (author.getEmail().equals(email)) {
							results.add(submission);
						}
					}
				}
			}
			
		} catch (NotFoundException e) {
			
			deferred.errback(e);
			
		}
		
		deferred.callback(results);
		
		log.leave();
		
		return deferred;
	}

}
