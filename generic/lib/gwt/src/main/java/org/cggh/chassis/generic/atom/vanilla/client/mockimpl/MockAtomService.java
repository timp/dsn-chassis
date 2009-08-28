/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.mockimpl;

import java.util.HashMap;
import java.util.Map;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.NotFoundException;
import org.cggh.chassis.generic.twisted.client.Deferred;

/**
 * @author aliman
 *
 */
public class MockAtomService implements AtomService {
	
	
	
	
	private static Map<String,MockAtomEntry> entries = new HashMap<String,MockAtomEntry>();
	private static Map<String,MockAtomFeed> feeds = new HashMap<String,MockAtomFeed>();
	private MockAtomFactory factory = new MockAtomFactory();
	private MockAtomStore store = new MockAtomStore(factory);
	
	
	
	public MockAtomService() {}
	
	
	
	
	public MockAtomService(MockAtomFactory factory) {
		this.factory  = factory;
		this.store = new MockAtomStore(factory);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomClient#deleteEntry(java.lang.String)
	 */
	public Deferred<Void> deleteEntry(String entryURL) {
		
		Deferred<Void> deferred = new Deferred<Void>();

		if (entries.containsKey(entryURL)) {

			MockAtomEntry entry = entries.get(entryURL);
			
			for (MockAtomFeed feed : feeds.values()) {
				if (feed.contains(entry)) {
					feed.remove(entry);
				}
			}

			entries.remove(entryURL);
			
			deferred.callback(null);

		}
		else {

			deferred.errback(new NotFoundException(entryURL));

		}

		return deferred;
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomClient#getEntry(java.lang.String)
	 */
	public Deferred<AtomEntry> getEntry(String entryURL) {
		
		Deferred<AtomEntry> deferred = new Deferred<AtomEntry>();
		
		if (entries.containsKey(entryURL)) {
			
			deferred.callback(entries.get(entryURL));
			
		}
		else {
			
			deferred.errback(new NotFoundException(entryURL));
			
		}
		
		return deferred;
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomClient#getFeed(java.lang.String)
	 */
	public Deferred<AtomFeed> getFeed(String feedURL) {
		
		Deferred<AtomFeed> deferred = new Deferred<AtomFeed>();

		if (feeds.containsKey(feedURL)) {

			deferred.callback(feeds.get(feedURL));

		}
		else {

			deferred.errback(new NotFoundException(feedURL));

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

		} catch (NotFoundException e) {
			
			deferred.errback(e);
			
		}
		
		return deferred;
	}

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomClient#putEntry(java.lang.String, org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry)
	 */
	public Deferred<AtomEntry> putEntry(String entryURL, AtomEntry entry) {
		
		Deferred<AtomEntry> deferred = new Deferred<AtomEntry>();
		
		if (entries.containsKey(entryURL)) {

			MockAtomEntry mockEntry = entries.get(entryURL);
			mockEntry.put(entry);
			deferred.callback(mockEntry);
			
		}
		else {
			
			deferred.errback(new NotFoundException(entryURL));

		}

		return deferred;
	}

	
	
	public void createFeed(String feedURL, String title) {
		if (!feeds.containsKey(feedURL)) {
			
			// create mock feed to store in memory
			MockAtomFeed feed = factory.createMockFeed(title);
			
			// put feed in memory store
			feeds.put(feedURL, feed);
			
		}
	}
	
	
}
