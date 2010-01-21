/**
 * 
 */
package spike.atom.example3.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomEntry;
import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomFactory;
import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomFeed;
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

	
	String feedURL = "/chassis-generic-service-exist/atom/edit/sandbox";
	

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		log.enter("onModuleLoad");
		
		Deferred<VanillaAtomFeed> deferredFeed = doGetFeed();
		
		deferredFeed.addCallback(new Function<VanillaAtomFeed,VanillaAtomFeed>() {

			public VanillaAtomFeed apply(VanillaAtomFeed feed) {
				doUpdateFirstEntry(feed);
				return feed;
			}

		});
		
		log.leave();
	}

	
	
	
	/**
	 * @return
	 */
	private Deferred<VanillaAtomFeed> doGetFeed() {
		log.enter("doGetFeed");

		log.debug("create an atom factory");
		VanillaAtomFactory factory = new VanillaAtomFactory(); 
		
		log.debug("create an atom service");
		VanillaAtomService service = new VanillaAtomService(factory); 

		Deferred<VanillaAtomFeed> deferredFeed = service.getFeed(feedURL);
		
		log.debug("add callback to handle successful service response");
		deferredFeed.addCallback(new Function<VanillaAtomFeed,VanillaAtomFeed>() {

			public VanillaAtomFeed apply(VanillaAtomFeed feed) {
				log.enter("apply (inner callback function)");

				String feedId = feed.getId();
				String updated = feed.getUpdated();
				int entries = feed.getEntries().size();
				
				Window.alert("get feed success; id: "+feedId+"; updated: "+updated+"; number of entries: "+entries);

				// any other handling of successful response goes here
				
				log.leave();
				
				// pass on to any further callbacks
				return feed;
			}
			
		});
		
		log.debug("add errback to handle service error");
		deferredFeed.addErrback(new Function<Throwable,Throwable>() {

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
		return deferredFeed;
	}
	
	
	private Deferred<VanillaAtomEntry> doUpdateFirstEntry(VanillaAtomFeed feed) {
		log.enter("doUpdateFirstEntry");

		VanillaAtomEntry entry = feed.getEntries().get(0);
		
		entry.setTitle(entry.getTitle() + " updated");
		
		String entryURL = feedURL + entry.getEditLink().getHref(); // assume relative
		
		log.debug("create an atom factory");
		VanillaAtomFactory factory = new VanillaAtomFactory(); 
		
		log.debug("create an atom service");
		VanillaAtomService service = new VanillaAtomService(factory); 

		Deferred<VanillaAtomEntry> deferredEntry = service.putEntry(entryURL, entry);
		
		log.debug("add callback to handle successful service response");
		deferredEntry.addCallback(new Function<VanillaAtomEntry,VanillaAtomEntry>() {

			public VanillaAtomEntry apply(VanillaAtomEntry updatedEntry) {
				log.enter("apply (inner callback function)");

				String entryId = updatedEntry.getId();
				String published = updatedEntry.getPublished();
				String updated = updatedEntry.getUpdated();
				String entryURL = updatedEntry.getEditLink().getHref();
				String email = updatedEntry.getAuthors().get(0).getEmail();
				
				Window.alert("updated entry success; id: "+entryId+"; published: "+published+"; updated: "+updated+"; edit link (entryURL): "+entryURL+"; first author email: "+email);

				// any other handling of successful response goes here
				
				log.leave();
				
				// pass on to any further callbacks
				return updatedEntry;
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
