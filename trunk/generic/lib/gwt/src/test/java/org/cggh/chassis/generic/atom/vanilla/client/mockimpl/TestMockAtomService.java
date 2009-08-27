/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.mockimpl;

import static org.junit.Assert.*;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;
import org.junit.Before;
import org.junit.Test;


/**
 * @author aliman
 *
 */
public class TestMockAtomService {
	
	
	
	
	abstract class TestFunction<I,O> implements Function<I,O> {
		protected boolean called = false;
	}

	
	
	
	private MockAtomService service;
	private String feedURL;
	private String feedTitle;
	
	
	
	
	@Before
	public void setUp() {

		service = new MockAtomService();
		
		// bootstrap
		feedURL = "http://example.com/myfeed";
		feedTitle = "my foo feed";
		service.createFeed(feedURL, feedTitle);

	}
	
	
	
	
	@Test
	public void testCreateFeed() {
		
		// check feed was created
		Deferred<AtomFeed> deferredFeed = service.getFeed(feedURL);
	
		// check deferred
		assertNotNull(deferredFeed);
		
		// define callback test function
		TestFunction<AtomFeed,AtomFeed> callback = new TestFunction<AtomFeed,AtomFeed>() {

			public AtomFeed apply(AtomFeed in) {
				called = true;
				assertEquals(feedTitle, in.getTitle());
				assertNotNull(in.getId());
				assertNotNull(in.getUpdated());
				assertNotNull(in.getEntries());
				assertEquals(0, in.getEntries().size());
				return null;
			}
		
		};
		
		// add test callback
		deferredFeed.addCallback(callback);
		
		// check callback was called synchronously
		assertTrue(callback.called);
		
		// check deferred status
		assertEquals(Deferred.SUCCESS, deferredFeed.getStatus()); // N.B. needed because assertions in test function will not be caught by JUnit!!!
		
	}
	
	
	
	@Test
	public void testPostEntry() {
		
		// create new atom entry
		AtomEntry entry = new MockAtomEntry();
		final String entryTitle = "my foo entry";
		entry.setTitle(entryTitle);
		final String entrySummary = "foo bar baz quux";
		entry.setSummary(entrySummary);
		
		// post entry to service
		Deferred<AtomEntry> deferredEntry = service.postEntry(feedURL, entry);
		
		// check deferred
		assertNotNull(deferredEntry);
		
		// define test callback function
		TestFunction<AtomEntry,AtomEntry> callback = new TestFunction<AtomEntry,AtomEntry>() {

			public AtomEntry apply(AtomEntry in) {
				called = true;
				
				try {
					
					assertNotNull(in.getId());
					assertNotNull(in.getTitle());
					assertNotNull(in.getSummary());
					assertNotNull(in.getPublished());
					assertNotNull(in.getUpdated());
					assertNotNull(in.getLinks());
					assertNotNull(in.getEditLink());

					assertEquals(entryTitle, in.getTitle());
					assertEquals(entrySummary, in.getSummary());

				}
				catch (Error e) {
					
					e.printStackTrace();
					throw e;
					
				}

				return null;
			}
			
		};
		
		// add test callback
		deferredEntry.addCallback(callback);
		
		// check callback was called synchronously
		assertTrue(callback.called);
		
		// check deferred status
		assertEquals(Deferred.SUCCESS, deferredEntry.getStatus()); // N.B. needed because assertions in test function will not be caught by JUnit!!!
				
	}
	
	
}
