/**
 * 
 */
package spike.atom.submission.example.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFactory;
import org.cggh.chassis.generic.atomext.client.study.StudyPersistenceService;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionFactory;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionPersistenceService;
import org.cggh.chassis.generic.client.gwt.configuration.client.JsConfiguration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

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
		
		log.debug("first create a study & persist");
		Deferred<StudyEntry> deferredStudy = createStudy();
		
		log.debug("now create a submission linked to that study");
		deferredStudy.addCallback(new Function<StudyEntry,StudyEntry>() {

			public StudyEntry apply(StudyEntry study) {
				createSubmission(study);
				return study;
			}
			
		});
		
		
		log.leave();
	}
	
	
	
	private Deferred<SubmissionEntry> createSubmission(StudyEntry study) {
		log.enter("createSubmission");

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
		submission.addStudyLink(study.getEditLink().getHref());
		
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
	
	
	
	private Deferred<StudyEntry> createStudy() {
		log.enter("createStudy");
		
		String feedURL = JsConfiguration.getStudyCollectionUrl();

		log.debug("create a study factory");
		StudyFactory factory = new StudyFactory(); 
		
		log.debug("create an atom service");
		StudyPersistenceService service = new StudyPersistenceService();
		
		log.debug("create a new entry");
		StudyEntry study = factory.createEntry();
		study.setTitle("my first study");
		study.setSummary("this study was done from 2004-2005 in the Gambia");
		
		log.debug("create and set author");
		AtomAuthor bob = factory.createAuthor(); 
		bob.setName("bob");
		bob.setEmail("bob@example.com");
		study.addAuthor(bob);

		log.debug("add study module names");
		study.getStudy().addModule("in vitro");
		study.getStudy().addModule("molecular");

		log.debug("persist new study");
		Deferred<StudyEntry> deferredEntry = service.postEntry(feedURL, study);
		
		log.debug("add callback to handle successful service response");
		deferredEntry.addCallback(new Function<StudyEntry,StudyEntry>() { 

			public StudyEntry apply(StudyEntry persistedStudy) {
				log.enter("apply (inner callback function)");
				
				String studyId = persistedStudy.getId();
				String published = persistedStudy.getPublished();
				String updated = persistedStudy.getUpdated();
				String studyURL = persistedStudy.getEditLink().getHref();
				String firstModule = persistedStudy.getStudy().getModules().get(0);
				
				Window.alert("persisted study success; id: "+studyId+"; published: "+published+"; updated: "+updated+"; edit link (entryURL): "+studyURL+"; first modules: "+firstModule);

				// any other handling of successful response goes here
				
				log.leave();
				
				// pass on to any further callbacks
				return persistedStudy;
			}
			
		});
		
		log.debug("add errback to handle service error");
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
