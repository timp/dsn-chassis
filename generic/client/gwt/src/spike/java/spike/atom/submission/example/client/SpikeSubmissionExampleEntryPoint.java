/**
 * 
 */
package spike.atom.submission.example.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.mockimpl.MockSubmissionFactory;
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
public class SpikeSubmissionExampleEntryPoint implements EntryPoint {

	private Log log = LogFactory.getLog(this.getClass());

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		log.enter("onModuleLoad");
		
		log.trace("first create a study & persist");
		Deferred<AtomEntry> deferredStudy = createStudy();
		
		log.trace("now create a submission linked to that study");
		deferredStudy.addCallback(new Function<StudyEntry,StudyEntry>() {

			public StudyEntry apply(StudyEntry study) {
				createSubmission(study);
				return study;
			}
			
		});
		
		
		log.leave();
	}
	
	
	
	private Deferred<AtomEntry> createSubmission(StudyEntry study) {
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
		submission.addStudyLink(study.getEditLink().getHref());
		
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
	
	
	
	private Deferred<AtomEntry> createStudy() {
		log.enter("createStudy");
		
		String feedURL = Configuration.getStudyFeedURL();

		log.trace("create a study factory");
		MockStudyFactory factory = new MockStudyFactory(); // use mock for now
		
		log.trace("create an atom service");
		MockAtomService service = new MockAtomService(factory); // use mock for now
		service.createFeed(feedURL, "all studies"); // bootstrap mock service with study feed, not needed for real service
		
		log.trace("create a new entry");
		StudyEntry study = factory.createStudyEntry();
		study.setTitle("my first study");
		study.setSummary("this study was done from 2004-2005 in the Gambia");
		
		log.trace("create and set author");
		AtomPersonConstruct bob = factory.createPersonConstruct(); 
		bob.setName("bob");
		bob.setEmail("bob@example.com");
		study.addAuthor(bob);

		log.trace("add study module names");
		study.addModule("in vitro");
		study.addModule("molecular");

		log.trace("persist new study");
		Deferred<AtomEntry> deferredEntry = service.postEntry(feedURL, study);
		
		log.trace("add callback to handle successful service response");
		deferredEntry.addCallback(new Function<StudyEntry,StudyEntry>() { 

			public StudyEntry apply(StudyEntry persistedStudy) {
				log.enter("apply (inner callback function)");
				
				String studyId = persistedStudy.getId();
				String published = persistedStudy.getPublished();
				String updated = persistedStudy.getUpdated();
				String studyURL = persistedStudy.getEditLink().getHref();
				String firstModule = persistedStudy.getModules().get(0);
				
				Window.alert("persisted study success; id: "+studyId+"; published: "+published+"; updated: "+updated+"; edit link (entryURL): "+studyURL+"; first modules: "+firstModule);

				// any other handling of successful response goes here
				
				log.leave();
				
				// pass on to any further callbacks
				return persistedStudy;
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
		
		// N.B. when using mock atom service, callbacks will be called synchronously 
		// (i.e., as soon as they are added to the deferred), whereas when using
		// real service, callbacks will be called asynchronously. This does not
		// make any difference to the above code, but will affect the order
		// in which log messages appear.
		
		log.leave();
		
		return deferredEntry;
	}

}
