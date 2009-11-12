/**
 * 
 */
package spike.atom.submission.query.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionFactory;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionFeed;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionPersistenceService;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionQueryService;
import org.cggh.chassis.generic.client.gwt.configuration.client.JsConfiguration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

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
		
		String serviceURL = JsConfiguration.getSubmissionQueryServiceUrl();
		
		log.debug("create a query service");
		SubmissionQueryService service = new SubmissionQueryService(serviceURL); 

		log.debug("make query service call");
		Deferred<SubmissionFeed> deferredResults = service.getSubmissionsByAuthorEmail("bob@example.com");
		
		log.debug("add callback");
		deferredResults.addCallback(new Function<SubmissionFeed,SubmissionFeed>() {

			public SubmissionFeed apply(SubmissionFeed results) {
				log.enter("apply (inner callback function)");

				Window.alert("results feed title: "+results.getTitle());
				Window.alert("found "+results.getEntries().size()+" matching submissions");

				if (results.getEntries().size() > 0) {
					
					SubmissionEntry firstResult = results.getEntries().get(0);
					String submissionId = firstResult.getId();
					String published = firstResult.getPublished();
					String updated = firstResult.getUpdated();
					String submissionURL = firstResult.getEditLink().getHref();
					String firstModule = firstResult.getSubmission().getModules().get(0);
					String studyLink = firstResult.getStudyLinks().get(0).getHref();
					
					Window.alert("first matching result; id: "+submissionId+"; published: "+published+"; updated: "+updated+"; edit link (entryURL): "+submissionURL+"; first modules: "+firstModule+"; first study link: "+studyLink);

				}

				// do any other stuff with results here
				

				log.leave();
				return results; // pass on to any further callbacks
			}
			
		});
		
		log.debug("add errback");
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

		log.debug("first need to set up spike by creating a submission");
		Deferred<SubmissionEntry> setup = setup();
		
		log.debug("when setup complete, then try query submissions by author");
		setup.addCallback(new Function<SubmissionEntry,SubmissionEntry>() {

			public SubmissionEntry apply(SubmissionEntry in) {
				
				log.debug("setup is complete, now try query submissions");
				querySubmissions();
				
				return null;
			}
			
		});
		
		log.leave();
	}
	
	
	

	private Deferred<SubmissionEntry> setup() {
		log.enter("setup");

		String feedURL = JsConfiguration.getSubmissionCollectionUrl();
		
		log.debug("create a submission factory");
		SubmissionFactory factory = new SubmissionFactory(); 
		
		log.debug("create an atom service");
		SubmissionPersistenceService service = new SubmissionPersistenceService();

		log.debug("create a new entry");
		SubmissionEntry submission = factory.createEntry();
		submission.setTitle("my first submission");
		submission.setSummary("this submission contains all the clinical data for the 2004-05 Gambia study");
		
		log.debug("create and set author");
		AtomAuthor bob = factory.createAuthor(); 
		bob.setName("bob");
		bob.setEmail("bob@example.com");
		submission.addAuthor(bob);

		log.debug("add submission module names");
		submission.getSubmission().addModule("clinical");

		log.debug("set link from submission to study");
		submission.addStudyLink("/chassis-generic-service-atom/edit/studies?id=foo");
		
		log.debug("persist new submission");
		Deferred<SubmissionEntry> deferredEntry = service.postEntry(feedURL, submission);
		
		log.debug("add callback to handle successful service response");
		deferredEntry.addCallback(new Function<SubmissionEntry,SubmissionEntry>() { 

			public SubmissionEntry apply(SubmissionEntry persistedSubmission) {
				log.enter("apply (inner callback function)");
				
				String submissionId = persistedSubmission.getId();
				String published = persistedSubmission.getPublished();
				String updated = persistedSubmission.getUpdated();
				String submissionURL = persistedSubmission.getEditLink().getHref();
				String firstModule = persistedSubmission.getSubmission().getModules().get(0);
				String studyLink = persistedSubmission.getStudyLinks().get(0).getHref();
				
				Window.alert("persisted submission success; id: "+submissionId+"; published: "+published+"; updated: "+updated+"; edit link (entryURL): "+submissionURL+"; first modules: "+firstModule+"; first study link: "+studyLink);

				// any other handling of successful response goes here
				
				log.leave();
				
				// pass on to any further callbacks
				return persistedSubmission;
			}
			
		});
		
		log.debug("add errback to handle service error");
		deferredEntry.addErrback(new Function<Throwable,Throwable>() {

			public Throwable apply(Throwable t) {
				log.enter("apply (inner errback function)");

				Window.alert("service error: "+t.getClass().getName()+": "+t.getLocalizedMessage());
				
				// any other handling of error goes here
				
				log.leave();
				
				// pass on to any further errbacks
				return t;
			}
			
		});
		log.leave();
		
		return deferredEntry;
	}
	
}
