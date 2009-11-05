/**
 * 
 */
package legacy.org.cggh.chassis.generic.atom.vanilla.client.mockimpl;

import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;

import org.cggh.chassis.generic.twisted.client.Deferred;

/**
 * @author aliman
 *
 */
public class MockAtomService implements AtomService {
	
	
	
	
	public static MockAtomStore store = new MockAtomStore();
	
	
	
	public MockAtomService() {}
	
	
	
	
	public MockAtomService(MockAtomFactory factory) {
		store = new MockAtomStore(factory);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomClient#deleteEntry(java.lang.String)
	 */
	public Deferred<Void> deleteEntry(String entryURL) {
		
		Deferred<Void> deferred = new Deferred<Void>();

		try {
			
			store.delete(entryURL);
			deferred.callback(null);
			
		}
		catch (Throwable t) {
			
			deferred.errback(t);
			
		}

		return deferred;

	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomClient#getEntry(java.lang.String)
	 */
	public Deferred<AtomEntry> getEntry(String entryURL) {
		
		Deferred<AtomEntry> deferred = new Deferred<AtomEntry>();
		
		try {
			
			AtomEntry entry = store.retrieve(entryURL);
			deferred.callback(entry);
			
		}
		catch (Throwable t) {
			
			deferred.errback(t);
			
		}
		
		return deferred;

	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomClient#getFeed(java.lang.String)
	 */
	public Deferred<AtomFeed> getFeed(String feedURL) {
		
		Deferred<AtomFeed> deferred = new Deferred<AtomFeed>();

		try {
			
			AtomFeed feed = store.retrieveAll(feedURL);
			deferred.callback(feed);
			
		}
		catch (Throwable t) {
			
			deferred.errback(t);
			
		}
		
		return deferred;
	
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomClient#postEntry(java.lang.String, org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry)
	 */
	public Deferred<AtomEntry> postEntry(String feedURL, AtomEntry entry) {

		Deferred<AtomEntry> deferred = new Deferred<AtomEntry>();
		
		try {
			
			entry = store.create(feedURL, entry);
			deferred.callback(entry);

		} catch (Throwable e) {
			
			deferred.errback(e);
			
		}
		
		return deferred;

	}

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomClient#putEntry(java.lang.String, org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry)
	 */
	public Deferred<AtomEntry> putEntry(String entryURL, AtomEntry entry) {
		
		Deferred<AtomEntry> deferred = new Deferred<AtomEntry>();
		
		try {
			
			entry = store.update(entryURL, entry);
			deferred.callback(entry);

		} catch (Throwable e) {
			
			deferred.errback(e);
			
		}
		
		return deferred;

	}

	
	
	public void createFeed(String feedURL, String title) {
		
		store.createFeed(feedURL, title);
		
	}
	
	
}
