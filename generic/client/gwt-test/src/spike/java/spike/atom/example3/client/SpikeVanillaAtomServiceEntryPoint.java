/**
 * 
 */
package spike.atom.example3.client;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactoryImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.AtomServiceImpl;
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

	
	String feedURL = "/chassis-generic-service-exist/atom/edit/sandbox";
	

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		log.enter("onModuleLoad");
		
		Deferred<AtomFeed> deferredFeed = doGetFeed();
		
		deferredFeed.addCallback(new Function<AtomFeed,AtomFeed>() {

			public AtomFeed apply(AtomFeed feed) {
				doUpdateFirstEntry(feed);
				return feed;
			}

		});
		
		log.leave();
	}

	
	
	
	/**
	 * @return
	 */
	private Deferred<AtomFeed> doGetFeed() {
		log.enter("doGetFeed");

		log.debug("create an atom factory");
		AtomFactory factory = new AtomFactoryImpl(); 
		
		log.debug("create an atom service");
		AtomService service = new AtomServiceImpl(factory); 

		Deferred<AtomFeed> deferredFeed = service.getFeed(feedURL);
		
		log.debug("add callback to handle successful service response");
		deferredFeed.addCallback(new Function<AtomFeed,AtomFeed>() {

			public AtomFeed apply(AtomFeed feed) {
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
	
	
	private Deferred<AtomEntry> doUpdateFirstEntry(AtomFeed feed) {
		log.enter("doUpdateFirstEntry");

		AtomEntry entry = feed.getEntries().get(0);
		
		entry.setTitle(entry.getTitle() + " updated");
		
		String entryURL = feedURL + entry.getEditLink().getHref(); // assume relative
		
		log.debug("create an atom factory");
		AtomFactory factory = new AtomFactoryImpl(); 
		
		log.debug("create an atom service");
		AtomService service = new AtomServiceImpl(factory); 

		Deferred<AtomEntry> deferredEntry = service.putEntry(entryURL, entry);
		
		log.debug("add callback to handle successful service response");
		deferredEntry.addCallback(new Function<AtomEntry,AtomEntry>() {

			public AtomEntry apply(AtomEntry updatedEntry) {
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
