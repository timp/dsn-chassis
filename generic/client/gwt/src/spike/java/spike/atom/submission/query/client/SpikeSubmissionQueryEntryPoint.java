/**
 * 
 */
package spike.atom.submission.query.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFeed;
import org.cggh.chassis.generic.atom.submission.client.mockimpl.MockSubmissionFactory;
import org.cggh.chassis.generic.atom.submission.client.mockimpl.MockSubmissionQueryService;
import org.cggh.chassis.generic.atom.submission.client.protocol.SubmissionQuery;
import org.cggh.chassis.generic.atom.submission.client.protocol.SubmissionQueryService;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomPersonConstruct;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomService;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;

/**
 * @author aliman
 *
 */
public class SpikeSubmissionQueryEntryPoint implements EntryPoint {

	private Log log = LogFactory.getLog(this.getClass());
	
	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		log.enter("onModuleLoad");

		log.trace("set up by creating a submission");
		Deferred<AtomEntry> deferredSubmission = createSubmission();
		
		log.trace("when creation complete, then try query submissions by author");
		deferredSubmission.addCallback(new Function<SubmissionEntry,SubmissionEntry>() {

			public SubmissionEntry apply(SubmissionEntry in) {
				querySubmissions();
				return null;
			}
			
		});
		
		log.leave();
	}
	
	
	
	private void querySubmissions() {
		log.enter("querySubmissions");
		
		String serviceURL = Configuration.getSubmissionQueryServiceURL();
		
		log.trace("create a query service");
		SubmissionQueryService service = new MockSubmissionQueryService(); // use mock for now

		SubmissionQuery query = new SubmissionQuery();
		query.addClause(SubmissionQuery.Field.AUTHOR_EMAIL, "bob@example.com");
		
		Deferred<SubmissionFeed> results = service.query(query);
		
		// TODO
		
		log.leave();
	}
	


	private Deferred<AtomEntry> createSubmission() {
		log.enter("createSubmission");

		String feedURL = Configuration.getSubmissionFeedURL();
		
		log.trace("create a submission factory");
		MockSubmissionFactory factory = new MockSubmissionFactory(); // use mock for now
		
		log.trace("create an atom service");
		MockAtomService service = new MockAtomService(factory); // use mock for now
		service.createFeed(feedURL, "all submissions"); // bootstrap mock service with study feed, not needed for real service

		log.trace("create a new entry");
		SubmissionEntry submission = factory.createSubmissionEntry();
		submission.setTitle("my first submission");
		submission.setSummary("this submission contains all the clinical data for the 2004-05 Gambia study");
		
		log.trace("create and set author");
		AtomPersonConstruct bob = factory.createPersonConstruct(); 
		bob.setName("bob");
		bob.setEmail("bob@example.com");
		submission.addAuthor(bob);

		log.trace("add submission module names");
		submission.addModule("clinical");

		log.trace("set link from submission to study");
		submission.addStudyLink("/chassis-generic-service-atom/edit/studies?id=foo");
		
		log.trace("persist new submission");
		Deferred<AtomEntry> deferredEntry = service.postEntry(feedURL, submission);
		
		log.trace("add callback to handle successful service response");
		deferredEntry.addCallback(new Function<SubmissionEntry,SubmissionEntry>() { 

			public SubmissionEntry apply(SubmissionEntry persistedSubmission) {
				log.enter("apply (inner callback function)");
				
				String submissionId = persistedSubmission.getId();
				String published = persistedSubmission.getPublished();
				String updated = persistedSubmission.getUpdated();
				String submissionURL = persistedSubmission.getEditLink().getHref();
				String firstModule = persistedSubmission.getModules().get(0);
				String studyLink = persistedSubmission.getStudyLinks().get(0).getHref();
				
				Window.alert("persisted submission success; id: "+submissionId+"; published: "+published+"; updated: "+updated+"; edit link (entryURL): "+submissionURL+"; first modules: "+firstModule+"; first study link: "+studyLink);

				// any other handling of successful response goes here
				
				log.leave();
				
				// pass on to any further callbacks
				return persistedSubmission;
			}
			
		});
		
		log.trace("add errback to handle service error");
		deferredEntry.addErrback(new Function<Throwable,Throwable>() {

			public Throwable apply(Throwable t) {
				log.enter("apply (inner callback function)");

				Window.alert("service error: "+t.getClass().getName()+": "+t.getLocalizedMessage());
				
				// any other handling of error goes here
				
				log.leave();
				
				// pass on to any further errbacks
				return t;
			}
			
		});
		log.leave();
		
		return null;
	}
	
}
