/**
 * 
 */
package spike.study.queryservice.byauthoremail.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.async.client.HttpException;
import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFactory;
import org.cggh.chassis.generic.atomext.client.study.StudyFeed;
import org.cggh.chassis.generic.atomext.client.study.StudyPersistenceService;
import org.cggh.chassis.generic.atomext.client.study.StudyQuery;
import org.cggh.chassis.generic.atomext.client.study.StudyQueryService;
import org.cggh.chassis.generic.client.gwt.configuration.client.JsConfiguration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import static org.cggh.chassis.generic.widget.client.HtmlElements.*;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class SpikeStudyQueryServiceByAuthorEmailEntryPoint implements EntryPoint {

	
	
	
	
	private StudyFactory factory;
	private StudyPersistenceService studyPersistenceService;
	private StudyQueryService studyQueryService;
	private Log log = LogFactory.getLog(this.getClass());
	RootPanel root = RootPanel.get();

	
	
	
	
	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		log.enter("onModuleLoad");
		
		root.add(h1Widget("Spike Study Query Service - by Author Email"));
		
		factory = new StudyFactory();
		studyPersistenceService = new StudyPersistenceService();
		studyQueryService = new StudyQueryService(JsConfiguration.getStudyQueryServiceUrl());
		
		log.debug("create first study");
		Deferred<StudyEntry> callChain = createFirstStudy();
		
		callChain.addCallback(new Function<StudyEntry,Deferred<StudyEntry>>(){

			public Deferred<StudyEntry> apply(StudyEntry first) {
				log.enter("apply [first callback]");
				
				root.add(pWidget("first study created"));
				log.debug("first study created, now create second");
				Deferred<StudyEntry> def = createSecondStudy();

				log.leave();
				
				return def;
				
			}

		});
		
		callChain.addCallback(new Function<StudyEntry,Deferred<StudyEntry>>(){

			public Deferred<StudyEntry> apply(StudyEntry second) {
				log.enter("apply [second callback]");

				root.add(pWidget("second study created"));
				log.debug("second study created, now create third");
				Deferred<StudyEntry> def = createThirdStudy();

				log.leave();
				
				return def;

			}

		});
		

		
		callChain.addCallback(new Function<StudyEntry,Deferred<StudyFeed>>(){

			public Deferred<StudyFeed> apply(StudyEntry second) {
				log.enter("apply [third callback]");

				root.add(pWidget("third study created"));
				log.debug("all studies created, now try query");
				Deferred<StudyFeed> def = queryStudiesByAuthorEmail();
				
				log.leave();
				
				return def;

			}

		});
		

		
		callChain.addCallback(new Function<StudyFeed,StudyFeed>() {

			public StudyFeed apply(StudyFeed results) {
				log.enter("apply [fourth callback]");

				root.add(pWidget("query results received"));
				log.debug("study query success, check results");
				checkQueryResults(results);
				
				log.leave();
				return results;
			}

		});
		
		callChain.addErrback(new Function<Throwable,Throwable>() {

			public Throwable apply(Throwable t) {
				log.enter("apply [errback]");

				Window.alert("error ["+t.getClass().toString()+"]: "+t.getLocalizedMessage());
				
				if (t instanceof HttpException) {
					HttpException ape = (HttpException) t;
					log.debug(ape.getResponse().getText());
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
	private Deferred<StudyEntry> createFirstStudy() {
		log.enter("createFirstStudy");
		
		StudyEntry first = factory.createEntry();
		first.setTitle("one of bob's studies");
		
		AtomAuthor bob = factory.createAuthor();
		bob.setEmail("bob@example.com");
		
		first.addAuthor(bob);
		
		Deferred<StudyEntry> deferredEntry = studyPersistenceService.postEntry(JsConfiguration.getStudyCollectionUrl(), first);
		
		log.leave();
		return deferredEntry;
		
	}

	
	
	
	private Deferred<StudyEntry> createSecondStudy() {
		log.enter("createSecondStudy");
		
		StudyEntry second = factory.createEntry();
		second.setTitle("one of alice's studies");
		
		AtomAuthor alice = factory.createAuthor();
		alice.setEmail("alice@example.com");
		
		second.addAuthor(alice);
		
		Deferred<StudyEntry> deferredEntry = studyPersistenceService.postEntry(JsConfiguration.getStudyCollectionUrl(), second);
		
		log.leave();
		return deferredEntry;
		
	}
	
	
	
	
	private Deferred<StudyEntry> createThirdStudy() {
		log.enter("createThirdStudy");
		
		StudyEntry third = factory.createEntry();
		third.setTitle("one of alice & bob's studies");
		
		AtomAuthor alice = factory.createAuthor();
		alice.setEmail("alice@example.com");		
		third.addAuthor(alice);
		
		AtomAuthor bob = factory.createAuthor();
		bob.setEmail("bob@example.com");
		third.addAuthor(bob);
		
		Deferred<StudyEntry> deferredEntry = studyPersistenceService.postEntry(JsConfiguration.getStudyCollectionUrl(), third);
		
		log.leave();
		return deferredEntry;
		
	}
	
	
	
	
	private Deferred<StudyFeed> queryStudiesByAuthorEmail() {
		log.enter("queryStudiesByAuthorEmail");
		
		StudyQuery query = new StudyQuery();
		query.setAuthorEmail("alice@example.org");
		Deferred<StudyFeed> deferredResults = studyQueryService.query(query);

		log.leave();
		return deferredResults;
	}
	
	
	
	
	private void checkQueryResults(StudyFeed results) {
		log.enter("checkQueryResults");
		
		log.debug("feed title: "+results.getTitle());
		log.debug(results.toString());
		
		for (StudyEntry entry : results.getEntries()) {
			log.debug("found entry: "+entry.getTitle() + " ["+entry.getId()+"]");
			root.add(pWidget("found entry: "+entry.getTitle() + " ["+entry.getId()+"]"));
			boolean alice = false;
			for (AtomAuthor author : entry.getAuthors()) {
				if ("alice@example.com".equals(author.getEmail())) {
					alice = true;
				}
			}
			if (alice) root.add(pWidget("found alice"));
			else root.add(pWidget("did not find alice"));
		}
		
		log.leave();
	}
	


}
