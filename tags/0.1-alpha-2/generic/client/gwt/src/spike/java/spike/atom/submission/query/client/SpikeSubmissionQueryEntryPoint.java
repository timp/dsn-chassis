/**
 * 
 */
package spike.atom.submission.query.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFeed;
import org.cggh.chassis.generic.atom.submission.client.mockimpl.MockSubmissionFactory;
import org.cggh.chassis.generic.atom.submission.client.mockimpl.MockSubmissionQueryService;
import org.cggh.chassis.generic.atom.submission.client.protocol.SubmissionQueryService;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
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
	
	
	
	
	/**
	 * This method provides an example of how to use the submissions query service.
	 */
	private void querySubmissions() {
		log.enter("querySubmissions");
		
		String serviceURL = Configuration.getSubmissionQueryServiceURL();
		
		log.trace("create a query service");
		SubmissionQueryService service = new MockSubmissionQueryService(serviceURL); // use mock for now

		log.trace("make query service call");
		Deferred<SubmissionFeed> deferredResults = service.getSubmissionsByAuthorEmail("bob@example.com");
		
		log.trace("add callback");
		deferredResults.addCallback(new Function<SubmissionFeed,SubmissionFeed>() {

			public SubmissionFeed apply(SubmissionFeed results) {
				log.enter("apply (inner callback function)");

				Window.alert("results feed title: "+results.getTitle());
				Window.alert("found "+results.getSubmissionEntries().size()+" matching submissions");

				if (results.getSubmissionEntries().size() > 0) {
					
					SubmissionEntry firstResult = results.getSubmissionEntries().get(0);
					String submissionId = firstResult.getId();
					String published = firstResult.getPublished();
					String updated = firstResult.getUpdated();
					String submissionURL = firstResult.getEditLink().getHref();
					String firstModule = firstResult.getModules().get(0);
					String studyLink = firstResult.getStudyLinks().get(0).getHref();
					
					Window.alert("first matching result; id: "+submissionId+"; published: "+published+"; updated: "+updated+"; edit link (entryURL): "+submissionURL+"; first modules: "+firstModule+"; first study link: "+studyLink);

				}

				// do any other stuff with results here
				

				log.leave();
				return results; // pass on to any further callbacks
			}
			
		});
		
		log.trace("add errback");
		deferredResults.addErrback(new Function<Throwable,Throwable>() {

			public Throwable apply(Throwable t) {
				log.enter("apply (inner errback function)");

				Window.alert("service error: "+t.getClass().getName()+": "+t.getLocalizedMessage());
				
				// any other handling of error goes here
				
				log.leave();
				
				return t; // pass on to any further errbacks
			}
			
		});
		
		log.leave();
	}

	
	

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		log.enter("onModuleLoad");

		log.trace("first need to set up spike by creating a submission");
		Deferred<Void> setup = setup();
		
		log.trace("when setup complete, then try query submissions by author");
		setup.addCallback(new Function<Void,Void>() {

			public Void apply(Void in) {
				
				log.trace("setup is complete, now try query submissions");
				querySubmissions();
				
				return null;
			}
			
		});
		
		log.leave();
	}
	
	
	

	private Deferred<Void> setup() {
		log.enter("setup");

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
		AtomAuthor bob = factory.createAuthor(); 
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
