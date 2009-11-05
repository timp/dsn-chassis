/**
 * 
 */
package spike.atom.example2.client;

import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactoryImpl;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomFactory;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomService;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.AtomServiceImpl;

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
public class SpikeVanillaAtomServiceEntryPoint implements EntryPoint {

	private Log log = LogFactory.getLog(this.getClass());

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		log.enter("onModuleLoad");
		
		Deferred<AtomEntry> deferredEntry = doCreateEntry();
		
		log.leave();
	}

	/**
	 * 
	 */
	private Deferred<AtomEntry> doCreateEntry() {
		log.enter("doCreateEntry");

		String feedURL = "/chassis-generic-service-exist/atom/edit/sandbox";
			
		log.debug("create an atom factory");
		AtomFactory factory = new AtomFactoryImpl(); 
		
		log.debug("create an atom service");
		AtomService service = new AtomServiceImpl(factory); 
		
		log.debug("create a new entry");
		AtomEntry entry = factory.createEntry();
		entry.setTitle("a vanilla atom entry");
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
				String email = persistedEntry.getAuthors().get(0).getEmail();
				
				Window.alert("persisted entry success; id: "+entryId+"; published: "+published+"; updated: "+updated+"; edit link (entryURL): "+entryURL+"; first author email: "+email);

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

				Window.alert("service error ["+t.getClass().toString()+"]: "+t.getLocalizedMessage());
				
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
