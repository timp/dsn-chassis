/**
 * 
 */
package spike.submission.services.example1.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFactory;
import org.cggh.chassis.generic.atomext.client.study.StudyFeed;
import org.cggh.chassis.generic.atomext.client.study.StudyPersistenceService;
import org.cggh.chassis.generic.atomext.client.study.StudyQueryService;
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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class SpikeSubmissionServicesEntryPoint implements EntryPoint {

	
	
	private Log log = LogFactory.getLog(this.getClass());
	
	
	
	private void render(String message) {
		RootPanel.get().add(new HTML("<p>"+message+"</p>"));
	}
	
	
	
	
	public Deferred<StudyEntry> createStudyByAlice() {
		log.enter("createStudyByAlice");
		
		log.debug("create a study by Alice");
		StudyFactory factory = new StudyFactory();
		StudyEntry studyEntry = factory.createEntry();
		studyEntry.setTitle("One of Alice's Studies");
		studyEntry.setSummary("A study create by Alice.");
		AtomAuthor alice = factory.createAuthor();
		alice.setEmail("alice@example.org");
		studyEntry.addAuthor(alice);
		
		log.debug("persist the study");
		StudyPersistenceService service = new StudyPersistenceService();
		Deferred<StudyEntry> deferredResult = service.postEntry(JsConfiguration.getStudyCollectionUrl(), studyEntry);
			
		log.leave();
		return deferredResult;
	}
	
	
	
	public Deferred<StudyFeed> getStudiesByAlice() {
		log.enter("getStudiesByAlice");
		
		log.debug("get Alice's studies");
		StudyQueryService service = new StudyQueryService(JsConfiguration.getStudyQueryServiceUrl());
		Deferred<StudyFeed> deferredResults = service.getStudiesByAuthorEmail("alice@example.org");

		log.leave();
		return deferredResults;
	}
	
	
	
	public Deferred<SubmissionEntry> createSubmissionByAlice(StudyEntry studyEntry) {
		log.enter("createSubmissionByAlice");

		log.debug("create a submission by Alice, linked to the given study");
		SubmissionFactory factory = new SubmissionFactory();
		SubmissionEntry submissionEntry = factory.createEntry();
		submissionEntry.setTitle("One of Alice's Submissions");
		submissionEntry.setSummary("A data submission created by Alice, linked to a study.");
		AtomAuthor alice = factory.createAuthor();
		alice.setEmail("alice@example.org");
		submissionEntry.addAuthor(alice);
		
		log.debug("link submission to a study");
		String studyAbsoluteUrl = "http://" + Window.Location.getHost() + JsConfiguration.getStudyCollectionUrl() + studyEntry.getEditLink().getHref(); // convert to absolute URL
		submissionEntry.addStudyLink(studyAbsoluteUrl);
		
		log.debug("persist the submission");
		SubmissionPersistenceService service = new SubmissionPersistenceService(factory);
		Deferred<SubmissionEntry> deferredResult = service.postEntry(JsConfiguration.getSubmissionCollectionUrl(), submissionEntry);
		
		log.leave();
		return deferredResult;
	}
	
	
	
	public Deferred<SubmissionFeed> getSubmissionsByAlice() {
		log.enter("getSubmissionsByAlice");
	
		log.debug("get Alice's submissions");
		SubmissionQueryService service = new SubmissionQueryService(JsConfiguration.getSubmissionQueryServiceUrl());
		Deferred<SubmissionFeed> deferredResults = service.getSubmissionsByAuthorEmail("alice@example.org");
		
		log.leave();
		return deferredResults;
	}
	
	
	
	public void checkSubmissionsBelongToAlice(SubmissionFeed results) {
		log.enter("checkSubmissionsBelongToAlice");
		
		render("found "+results.getEntries().size()+" results");
		
		for (SubmissionEntry e : results.getEntries()) {
			render("checking result is by Alice: "+e.getTitle()+" ["+e.getId()+"]");
			boolean alice = false;
			for (AtomAuthor a : e.getAuthors()) {
				if ("alice@example.org".equals(a.getEmail())) {
					alice = true;
				}
			}
			render("found alice: "+alice);
		}
		
		log.leave();
	}
	
	
	
	

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		log.enter("onModuleLoad");
		
		
		RootPanel.get().add(new HTML("<h1>Spike Submission Services</h1>"));

		render("create a study by alice...");
		Deferred<StudyEntry> chain = createStudyByAlice();
		
		
		chain.addCallback(new Function<StudyEntry,Deferred<StudyFeed>>() {

			public Deferred<StudyFeed> apply(StudyEntry in) {
				log.enter("first callback");

				render("study created, now get all studies by alice...");
				Deferred<StudyFeed> deferredResults = getStudiesByAlice();
				
				log.leave();
				return deferredResults;
			}
			
		});
		
		
		chain.addCallback(new Function<StudyFeed,Deferred<SubmissionEntry>>() {

			public Deferred<SubmissionEntry> apply(StudyFeed results) {
				log.enter("second callback");
				
				if (results.getEntries().size() == 0) {
					throw new RuntimeException("unexpected, no studies found");
				}

				render("retrieved "+results.getEntries().size()+" studies by alice, now create a submission by alice linked to first study in results...");
				Deferred<SubmissionEntry> deferredResult = createSubmissionByAlice(results.getEntries().get(0));

				log.leave();
				return deferredResult;
			}
			
		});
		
		
		chain.addCallback(new Function<SubmissionEntry, Deferred<SubmissionFeed>>() {

			public Deferred<SubmissionFeed> apply(SubmissionEntry in) {
				log.enter("third callback");
				
				render("created submission by alice, now query all submissions by alice...");
				Deferred<SubmissionFeed> deferredResults = getSubmissionsByAlice();

				log.leave();
				return deferredResults;
			}
			
		});
		
		
		chain.addCallback(new Function<SubmissionFeed,Void>() {

			public Void apply(SubmissionFeed results) {
				log.enter("fourth callback");

				if (results.getEntries().size() == 0) {
					throw new RuntimeException("unexpected, no submissions found");
				}

				render("retrieved "+results.getEntries().size()+" submissions by alice, now check the results...");
				checkSubmissionsBelongToAlice(results);
				
				log.leave();
				return null;
			}
			
		});

		log.leave();
	}

	
	
}
