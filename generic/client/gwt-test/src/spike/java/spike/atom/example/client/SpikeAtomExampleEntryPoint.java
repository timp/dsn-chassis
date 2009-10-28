/**
 * 
 */
package spike.atom.example.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomFactory;
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
public class SpikeAtomExampleEntryPoint implements EntryPoint {

	
	
	private Log log = LogFactory.getLog(this.getClass());
	
	
	
	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		log.enter("onModuleLoad");
		
		doVanillaAtomExamples();
		
		doStudyExamples();

		log.leave();
	}
	

	
	public void doVanillaAtomExamples() {
		log.enter("doVanillaAtomExamples");

		String feedURL = "http://example.com/atom/feeds/example";
			
		log.debug("create an atom factory");
		MockAtomFactory factory = new MockAtomFactory(); // use mock for now
		
		log.debug("create an atom service");
		MockAtomService service = new MockAtomService(factory); // use mock for now
		service.createFeed(feedURL, "my first atom feed"); // bootstrap mock service with a feed, not needed for real service
		
		log.debug("create a new entry");
		AtomEntry entry = factory.createEntry();
		entry.setTitle("my first vanilla atom entry");
		entry.setSummary("this is a vanilla atom entry, that's all");
		
		log.debug("create and set author");
		AtomAuthor bob = factory.createAuthor(); 
		bob.setName("bob");
		bob.setEmail("bob@example.com");
		entry.addAuthor(bob);
		
		log.debug("persist new entry");
		Deferred<AtomEntry> deferredEntry = service.postEntry(feedURL, entry);
		
		log.debug("add callback to handle successful service response");
		deferredEntry.addCallback(new Function<AtomEntry,AtomEntry>() {

			public AtomEntry apply(AtomEntry persistedEntry) {
				log.enter("apply (inner callback function)");

				String entryId = persistedEntry.getId();
				String published = persistedEntry.getPublished();
				String updated = persistedEntry.getUpdated();
				String entryURL = persistedEntry.getEditLink().getHref();
				
				Window.alert("persisted entry success; id: "+entryId+"; published: "+published+"; updated: "+updated+"; edit link (entryURL): "+entryURL);

				// any other handling of successful response goes here
				
				log.leave();
				
				// pass on to any further callbacks
				return persistedEntry;
			}
			
		});
		
		log.debug("add errback to handle service error");
		deferredEntry.addErrback(new Function<Throwable,Throwable>() {

			public Throwable apply(Throwable t) {
				log.enter("apply (inner callback function)");

				Window.alert("service error: "+t.getLocalizedMessage());
				
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
	}
	
	
	
	public void doStudyExamples() {
		log.enter("doStudyExamples");
		
		String feedURL = Configuration.getStudyFeedURL();

		log.debug("create a study factory");
		MockStudyFactory factory = new MockStudyFactory(); // use mock for now
		
		log.debug("create an atom service");
		MockAtomService service = new MockAtomService(factory); // use mock for now
		service.createFeed(feedURL, "all studies"); // bootstrap mock service with study feed, not needed for real service
		
		log.debug("create a new entry");
		StudyEntry study = factory.createStudyEntry();
		study.setTitle("my first study");
		study.setSummary("this study was done from 2004-2005 in the Gambia");
		
		log.debug("create and set author");
		AtomAuthor bob = factory.createAuthor(); 
		bob.setName("bob");
		bob.setEmail("bob@example.com");
		study.addAuthor(bob);

		log.debug("add study module names");
		study.addModule("in vitro");
		study.addModule("molecular");

		log.debug("persist new study");
		Deferred<AtomEntry> deferredEntry = service.postEntry(feedURL, study);
		
		log.debug("add callback to handle successful service response");
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
		
	}

}
