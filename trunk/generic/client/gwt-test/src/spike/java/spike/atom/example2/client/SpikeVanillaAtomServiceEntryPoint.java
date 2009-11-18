/**
 * 
 */
package spike.atom.example2.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomEntry;
import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomFactory;
import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomService;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

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
		
		Deferred<VanillaAtomEntry> deferredEntry = doCreateEntry();
		
		log.leave();
	}

	/**
	 * 
	 */
	private Deferred<VanillaAtomEntry> doCreateEntry() {
		log.enter("doCreateEntry");

		String feedURL = "/chassis-generic-service-exist/atom/edit/sandbox";
			
		log.debug("create an atom factory");
		VanillaAtomFactory factory = new VanillaAtomFactory(); 
		
		log.debug("create an atom service");
		VanillaAtomService service = new VanillaAtomService(factory); 
		
		log.debug("create a new entry");
		VanillaAtomEntry entry = factory.createEntry();
		entry.setTitle("a vanilla atom entry");
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
