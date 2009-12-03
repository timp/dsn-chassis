/**
 * 
 */
package spike.atom.example.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomEntry;
import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomFactory;
import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomService;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFactory;
import org.cggh.chassis.generic.atomext.client.study.StudyPersistenceService;
import org.cggh.chassis.generic.client.gwt.configuration.client.JsConfiguration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

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

		String feedURL = "http://localhost:8080/chassis-generic-service-exist/atom/edit/sandbox";
			
		log.debug("create an atom factory");
		VanillaAtomFactory factory = new VanillaAtomFactory(); 
		
		log.debug("create an atom service");
		VanillaAtomService service = new VanillaAtomService(factory); 
		
		log.debug("create a new entry");
		VanillaAtomEntry entry = factory.createEntry();
		entry.setTitle("my first vanilla atom entry");
		entry.setSummary("this is a vanilla atom entry, that's all");
		
		log.debug("create and set author");
		AtomAuthor bob = factory.createAuthor(); 
		bob.setName("bob");
		bob.setEmail("bob@example.com");
		entry.addAuthor(bob);
		
		log.debug("persist new entry");
		Deferred<VanillaAtomEntry> deferredEntry = service.postEntry(feedURL, entry);
		
		log.debug("add callback to handle successful service response");
		deferredEntry.addCallback(new Function<VanillaAtomEntry,VanillaAtomEntry>() {

			public VanillaAtomEntry apply(VanillaAtomEntry persistedEntry) {
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
		
	}

}
