/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.mockimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.NotFoundException;

/**
 * @author aliman
 *
 */
public class MockAtomStore {
	
	
	private Map<String,MockAtomEntry> entries = new HashMap<String,MockAtomEntry>();
	private Map<String,MockAtomFeed> feeds = new HashMap<String,MockAtomFeed>();
	private MockAtomFactory factory = new MockAtomFactory();
	


	public MockAtomStore(MockAtomFactory factory) {
		this.factory = factory;
	}
	
	
	
	public MockAtomStore() {}

	
	
	public MockAtomFeed createFeed(String feedURL, String title) {
		
		if (!feeds.containsKey(feedURL)) {

			// create mock feed to store in memory
			MockAtomFeed feed = factory.createMockFeed(title);
			
			// put feed in memory store
			feeds.put(feedURL, feed);

			return feed;
			
		}
		else {
			
			return null;
			
		}
		
	}
	
	
	
	public MockAtomEntry create(String feedURL, AtomEntry entry) throws NotFoundException {

		if (feeds.containsKey(feedURL)) {
			
			// copy into mock entry
			MockAtomEntry mockEntry = factory.copy(entry);
			
			// generate new id
			String id = Double.toString(Math.random());

			// set entry id
			mockEntry.setId(id);
			
			// construct entry URL and edit link
			String entryURL = feedURL + "?id=" + id;
			mockEntry.setEditLink(entryURL);
			
			// set published and updated
			Date date = new Date();
			mockEntry.setPublished(date.toString());
			mockEntry.setUpdated(date.toString()); // TODO check these

			// add entry to feed
			MockAtomFeed mockFeed = feeds.get(feedURL);
			mockFeed.add(mockEntry);
			
			// add entry to store
			entries.put(entryURL, mockEntry);

			// return a copy
			return factory.copy(mockEntry);

		}
		else {
			
			throw new NotFoundException(feedURL);
			
		}

	}
	
	
	public MockAtomEntry retrieve(String entryURL) throws NotFoundException {
		
		if (entries.containsKey(entryURL)) {
			
			// return a copy
			return factory.copy(entries.get(entryURL));
			
		}
		else {
			
			throw new NotFoundException(entryURL);
			
		}

	}
	
	
	public MockAtomEntry update(String entryURL, AtomEntry entry) throws NotFoundException {

		if (entries.containsKey(entryURL)) {

			MockAtomEntry mockEntry = entries.get(entryURL);
			mockEntry.put(entry);
			Date updated = new Date();
			mockEntry.setUpdated(updated.toString());

			// return a copy
			return factory.copy(mockEntry);
			
		}
		else {
			
			throw new NotFoundException(entryURL);

		}

	}
	
	
	public void delete(String entryURL) throws NotFoundException {

		if (entries.containsKey(entryURL)) {

			MockAtomEntry entry = entries.get(entryURL);
			
			for (MockAtomFeed feed : feeds.values()) {
				if (feed.contains(entry)) {
					feed.remove(entry);
				}
			}

			entries.remove(entryURL);

		}
		else {

			throw new NotFoundException(entryURL);

		}

	}
	
	
	public MockAtomFeed retrieveAll(String feedURL) throws NotFoundException {
		
		if (feeds.containsKey(feedURL)) {
			
			return factory.copy(feeds.get(feedURL));
			
		}
		else {
			
			throw new NotFoundException(feedURL);
			
		}

	}
	
	
}
