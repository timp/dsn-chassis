/**
 * 
 */
package spike.study.queryservice.byauthoremail.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.study.client.format.StudyFeed;
import org.cggh.chassis.generic.atom.study.client.format.impl.StudyFactoryImpl;
import org.cggh.chassis.generic.atom.study.client.protocol.impl.StudyQueryServiceImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomProtocolException;
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
public class SpikeStudyQueryServiceByAuthorEmailEntryPoint implements EntryPoint {

	
	
	
	
	private StudyFactory factory;
	private AtomService studyPersistenceService;
	private StudyQueryServiceImpl studyQueryService;
	private Log log = LogFactory.getLog(this.getClass());
	RootPanel root = RootPanel.get();

	
	
	
	
	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		log.enter("onModuleLoad");
		
		root.add(new HTML("<h1>Spike Study Query Service - by Author Email</h1>"));
		
		factory = new StudyFactoryImpl();
		studyPersistenceService = new AtomServiceImpl(factory);
		studyQueryService = new StudyQueryServiceImpl(Configuration.getStudyQueryServiceURL());
		
		log.trace("create first study");
		Deferred<AtomEntry> callChain = createFirstStudy();
		
		callChain.addCallback(new Function<AtomEntry,Deferred<AtomEntry>>(){

			public Deferred<AtomEntry> apply(AtomEntry first) {
				log.enter("apply [first callback]");
				
				root.add(new HTML("<p>first study created</p>"));
				log.trace("first study created, now create second");
				Deferred<AtomEntry> def = createSecondStudy();

				log.leave();
				
				return def;
				
			}

		});
		
		callChain.addCallback(new Function<AtomEntry,Deferred<AtomEntry>>(){

			public Deferred<AtomEntry> apply(AtomEntry second) {
				log.enter("apply [second callback]");

				root.add(new HTML("<p>second study created</p>"));
				log.trace("second study created, now create third");
				Deferred<AtomEntry> def = createThirdStudy();

				log.leave();
				
				return def;

			}

		});
		

		
		callChain.addCallback(new Function<AtomEntry,Deferred<StudyFeed>>(){

			public Deferred<StudyFeed> apply(AtomEntry second) {
				log.enter("apply [third callback]");

				root.add(new HTML("<p>third study created</p>"));
				log.trace("all studies created, now try query");
				Deferred<StudyFeed> def = queryStudiesByAuthorEmail();
				
				log.leave();
				
				return def;

			}

		});
		

		
		callChain.addCallback(new Function<StudyFeed,StudyFeed>() {

			public StudyFeed apply(StudyFeed results) {
				log.enter("apply [fourth callback]");

				root.add(new HTML("<p>query results received</p>"));
				log.trace("study query success, check results");
				checkQueryResults(results);
				
				log.leave();
				return results;
			}

		});
		
		callChain.addErrback(new Function<Throwable,Throwable>() {

			public Throwable apply(Throwable t) {
				log.enter("apply [errback]");

				Window.alert("error ["+t.getClass().toString()+"]: "+t.getLocalizedMessage());
				
				if (t instanceof AtomProtocolException) {
					AtomProtocolException ape = (AtomProtocolException) t;
					log.trace(ape.getResponse().getText());
				}
				
				// any other handling of error goes here
				
				log.leave();
				
				// pass on to any further errbacks
				return t;
			}
			
		});
		
		log.leave();

	}

	
	
	
	/**
	 * @return
	 */
	private Deferred<AtomEntry> createFirstStudy() {
		log.enter("createFirstStudy");
		
		StudyEntry first = factory.createStudyEntry();
		first.setTitle("one of bob's studies");
		
		AtomAuthor bob = factory.createAuthor();
		bob.setEmail("bob@example.com");
		
		first.addAuthor(bob);
		
		Deferred<AtomEntry> deferredEntry = studyPersistenceService.postEntry(Configuration.getStudyFeedURL(), first);
		
		log.leave();
		return deferredEntry;
		
	}

	
	
	
	private Deferred<AtomEntry> createSecondStudy() {
		log.enter("createSecondStudy");
		
		StudyEntry second = factory.createStudyEntry();
		second.setTitle("one of alice's studies");
		
		AtomAuthor alice = factory.createAuthor();
		alice.setEmail("alice@example.com");
		
		second.addAuthor(alice);
		
		Deferred<AtomEntry> deferredEntry = studyPersistenceService.postEntry(Configuration.getStudyFeedURL(), second);
		
		log.leave();
		return deferredEntry;
		
	}
	
	
	
	
	private Deferred<AtomEntry> createThirdStudy() {
		log.enter("createThirdStudy");
		
		StudyEntry third = factory.createStudyEntry();
		third.setTitle("one of alice & bob's studies");
		
		AtomAuthor alice = factory.createAuthor();
		alice.setEmail("alice@example.com");		
		third.addAuthor(alice);
		
		AtomAuthor bob = factory.createAuthor();
		bob.setEmail("bob@example.com");
		third.addAuthor(bob);
		
		Deferred<AtomEntry> deferredEntry = studyPersistenceService.postEntry(Configuration.getStudyFeedURL(), third);
		
		log.leave();
		return deferredEntry;
		
	}
	
	
	
	
	private Deferred<StudyFeed> queryStudiesByAuthorEmail() {
		log.enter("queryStudiesByAuthorEmail");
		
		Deferred<StudyFeed> deferredResults = studyQueryService.getStudiesByAuthorEmail("alice@example.com");

		log.leave();
		return deferredResults;
	}
	
	
	
	
	private void checkQueryResults(StudyFeed results) {
		log.enter("checkQueryResults");
		
		log.trace("feed title: "+results.getTitle());
		log.trace(results.toString());
		
		for (StudyEntry entry : results.getStudyEntries()) {
			log.trace("found entry: "+entry.getTitle() + " ["+entry.getId()+"]");
			root.add(new HTML("<p>found entry: "+entry.getTitle() + " ["+entry.getId()+"]</p>"));
			boolean alice = false;
			for (AtomAuthor author : entry.getAuthors()) {
				if ("alice@example.com".equals(author.getEmail())) {
					alice = true;
				}
			}
			if (alice) root.add(new HTML("<p>found alice</p>"));
			else root.add(new HTML("<p>did not find alice</p>"));
		}
		
		log.leave();
	}
	


}
