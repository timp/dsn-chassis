/**
 * 
 */
package spike.submission.services.example1.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.study.client.format.StudyFeed;
import org.cggh.chassis.generic.atom.study.client.format.impl.StudyFactoryImpl;
import org.cggh.chassis.generic.atom.study.client.protocol.StudyQueryService;
import org.cggh.chassis.generic.atom.study.client.protocol.impl.StudyQueryServiceImpl;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFactory;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFeed;
import org.cggh.chassis.generic.atom.submission.client.format.impl.SubmissionFactoryImpl;
import org.cggh.chassis.generic.atom.submission.client.protocol.SubmissionQueryService;
import org.cggh.chassis.generic.atom.submission.client.protocol.impl.SubmissionQueryServiceImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.AtomServiceImpl;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

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
	
	
	
	
	public Deferred<AtomEntry> createStudyByAlice() {
		log.enter("createStudyByAlice");
		
		log.debug("create a study by Alice");
		StudyFactory factory = new StudyFactoryImpl();
		StudyEntry studyEntry = factory.createStudyEntry();
		studyEntry.setTitle("One of Alice's Studies");
		studyEntry.setSummary("A study create by Alice.");
		AtomAuthor alice = factory.createAuthor();
		alice.setEmail("alice@example.org");
		studyEntry.addAuthor(alice);
		
		log.debug("persist the study");
		AtomService service = new AtomServiceImpl(factory);
		Deferred<AtomEntry> deferredResult = service.postEntry(Configuration.getStudyFeedURL(), studyEntry);
			
		log.leave();
		return deferredResult;
	}
	
	
	
	public Deferred<StudyFeed> getStudiesByAlice() {
		log.enter("getStudiesByAlice");
		
		log.debug("get Alice's studies");
		StudyQueryService service = new StudyQueryServiceImpl(Configuration.getStudyQueryServiceURL());
		Deferred<StudyFeed> deferredResults = service.getStudiesByAuthorEmail("alice@example.org");

		log.leave();
		return deferredResults;
	}
	
	
	
	public Deferred<AtomEntry> createSubmissionByAlice(StudyEntry studyEntry) {
		log.enter("createSubmissionByAlice");

		log.debug("create a submission by Alice, linked to the given study");
		SubmissionFactory factory = new SubmissionFactoryImpl();
		SubmissionEntry submissionEntry = factory.createSubmissionEntry();
		submissionEntry.setTitle("One of Alice's Submissions");
		submissionEntry.setSummary("A data submission created by Alice, linked to a study.");
		AtomAuthor alice = factory.createAuthor();
		alice.setEmail("alice@example.org");
		submissionEntry.addAuthor(alice);
		
		log.debug("link submission to a study");
		String studyAbsoluteUrl = "http://" + Window.Location.getHost() + Configuration.getStudyFeedURL() + studyEntry.getEditLink().getHref(); // convert to absolute URL
		submissionEntry.addStudyLink(studyAbsoluteUrl);
		
		log.debug("persist the submission");
		AtomService service = new AtomServiceImpl(factory);
		Deferred<AtomEntry> deferredResult = service.postEntry(Configuration.getSubmissionFeedURL(), submissionEntry);
		
		log.leave();
		return deferredResult;
	}
	
	
	
	public Deferred<SubmissionFeed> getSubmissionsByAlice() {
		log.enter("getSubmissionsByAlice");
	
		log.debug("get Alice's submissions");
		SubmissionQueryService service = new SubmissionQueryServiceImpl(Configuration.getSubmissionQueryServiceURL());
		Deferred<SubmissionFeed> deferredResults = service.getSubmissionsByAuthorEmail("alice@example.org");
		
		log.leave();
		return deferredResults;
	}
	
	
	
	public void checkSubmissionsBelongToAlice(SubmissionFeed results) {
		log.enter("checkSubmissionsBelongToAlice");
		
		render("found "+results.getSubmissionEntries().size()+" results");
		
		for (SubmissionEntry e : results.getSubmissionEntries()) {
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
		Deferred<AtomEntry> chain = createStudyByAlice();
		
		
		chain.addCallback(new Function<AtomEntry,Deferred<StudyFeed>>() {

			public Deferred<StudyFeed> apply(AtomEntry in) {
				log.enter("first callback");

				render("study created, now get all studies by alice...");
				Deferred<StudyFeed> deferredResults = getStudiesByAlice();
				
				log.leave();
				return deferredResults;
			}
			
		});
		
		
		chain.addCallback(new Function<StudyFeed,Deferred<AtomEntry>>() {

			public Deferred<AtomEntry> apply(StudyFeed results) {
				log.enter("second callback");
				
				if (results.getStudyEntries().size() == 0) {
					throw new RuntimeException("unexpected, no studies found");
				}

				render("retrieved "+results.getStudyEntries().size()+" studies by alice, now create a submission by alice linked to first study in results...");
				Deferred<AtomEntry> deferredResult = createSubmissionByAlice(results.getStudyEntries().get(0));

				log.leave();
				return deferredResult;
			}
			
		});
		
		
		chain.addCallback(new Function<AtomEntry, Deferred<SubmissionFeed>>() {

			public Deferred<SubmissionFeed> apply(AtomEntry in) {
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

				if (results.getSubmissionEntries().size() == 0) {
					throw new RuntimeException("unexpected, no submissions found");
				}

				render("retrieved "+results.getSubmissionEntries().size()+" submissions by alice, now check the results...");
				checkSubmissionsBelongToAlice(results);
				
				log.leave();
				return null;
			}
			
		});

		log.leave();
	}

	
	
}
