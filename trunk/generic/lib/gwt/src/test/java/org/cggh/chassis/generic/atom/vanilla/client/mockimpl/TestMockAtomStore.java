/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.mockimpl;

import static org.junit.Assert.*;

import org.cggh.chassis.generic.atom.vanilla.client.protocol.NotFoundException;
import org.junit.Before;
import org.junit.Test;

/**
 * @author aliman
 *
 */
public class TestMockAtomStore {
	
	
	
	private String feedURL;
	private MockAtomFactory factory;
	private MockAtomStore store;
	
	
	

	@Before
	public void setUp() {

		MockAtomStore.reset();
		feedURL = "http://example.com/atom/myfeed";
		factory = new MockAtomFactory();
		store = new MockAtomStore(factory);
		store.createFeed(feedURL, "my foo feed");

	}

	/**
	 * Test method for {@link org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomStore#create(java.lang.String, org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry)}.
	 */
	@Test
	public void testCreate() {
		
		// test data
		String title = "foo entry";
		String summary = "foo summary";
		MockAtomEntry entry = factory.createMockEntry();
		entry.setTitle(title);
		entry.setSummary(summary);
		
		// test initial state
		assertNull(entry.getId());
		assertNull(entry.getPublished());
		assertNull(entry.getUpdated());
		assertNull(entry.getEditLink());
		assertTrue(entry.getAuthors().isEmpty());
		assertTrue(entry.getLinks().isEmpty());
		assertTrue(entry.getCategories().isEmpty());
		
		try {
			
			// method under test
			entry = store.create(feedURL, entry);
			
			// test outcome
			assertNotNull(entry.getId());
			assertNotNull(entry.getPublished());
			assertNotNull(entry.getUpdated());
			assertNotNull(entry.getEditLink());
			assertFalse(entry.getLinks().isEmpty());
			assertEquals("foo entry", entry.getTitle());
			assertEquals("foo summary", entry.getSummary());

			// TODO test other properties
			
		} catch (NotFoundException e) {
			
			fail(e.getLocalizedMessage());
			e.printStackTrace();

		}

	}

	/**
	 * Test method for {@link org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomStore#retrieve(java.lang.String)}.
	 */
	@Test
	public void testRetrieve() {

		// test data
		MockAtomEntry entry = createTestEntry();

		try {
			
			// method under test
			MockAtomEntry retrievedEntry = store.retrieve(entry.getEditLink().getHref());
			
			// test outcome
			assertEquals(entry.getId(), retrievedEntry.getId());
			assertEquals(entry.getPublished(), retrievedEntry.getPublished());
			assertEquals(entry.getUpdated(), retrievedEntry.getUpdated());
			assertEquals(entry.getEditLink().getHref(), retrievedEntry.getEditLink().getHref());
			assertEquals(entry.getTitle(), retrievedEntry.getTitle());
			assertEquals(entry.getSummary(), retrievedEntry.getSummary());

			// TODO test other properties
			
		} catch (NotFoundException e) {
			
			fail(e.getLocalizedMessage());
			e.printStackTrace();

		}

	}

	/**
	 * Test method for {@link org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomStore#update(java.lang.String, org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry)}.
	 */
	@Test
	public void testUpdate() {

		// test data
		MockAtomEntry entry = createTestEntry();
		
		// change some stuff
		entry.setTitle("some other title");
		entry.setSummary("some other summary");
		
		// wait so time can pass and date updated will change
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			fail(e.getLocalizedMessage());
		}

		try {
			
			// method under test
			MockAtomEntry updatedEntry = store.update(entry.getEditLink().getHref(), entry);
			
			// test outcome
			assertEquals(entry.getId(), updatedEntry.getId());
			assertEquals(entry.getPublished(), updatedEntry.getPublished());
			assertFalse(entry.getUpdated().equals(updatedEntry.getUpdated())); // should have changed
			assertEquals(entry.getEditLink().getHref(), updatedEntry.getEditLink().getHref());
			assertEquals("some other title", updatedEntry.getTitle()); // should have changed
			assertEquals("some other summary", updatedEntry.getSummary()); // should have changed

			// TODO test other properties
			
		} catch (NotFoundException e) {
			
			fail(e.getLocalizedMessage());
			e.printStackTrace();

		}

	}

	/**
	 * Test method for {@link org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomStore#delete(java.lang.String)}.
	 */
	@Test
	public void testDelete() {

		// test data
		MockAtomEntry entry = createTestEntry();
		
		try {
			
			// method under test
			store.delete(entry.getEditLink().getHref());

		} catch (NotFoundException e) {
			
			e.printStackTrace();
			fail(e.getLocalizedMessage());

		}
		
		try {
			
			store.retrieve(entry.getEditLink().getHref());
			fail("expected not found exception");
			
		} catch (NotFoundException ex) {
			// expected
		}

	}

	/**
	 * Test method for {@link org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomStore#retrieveAll(java.lang.String)}.
	 */
	@Test
	public void testRetrieveAll() {
		
		// initial feed
		MockAtomFeed feed = null;
		try {

			feed = store.retrieveAll(feedURL);

		} catch (NotFoundException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		}
		
		assertNotNull(feed);
		assertEquals("my foo feed", feed.getTitle());
		assertNotNull(feed.getUpdated());
		assertTrue(feed.getEntries().isEmpty());
		
		// create an entry
		MockAtomEntry entry = createTestEntry();
		
		try {

			feed = store.retrieveAll(feedURL);
			assertEquals(1, feed.getEntries().size());
			assertEquals(entry.getId(), feed.getEntries().get(0).getId());

		} catch (NotFoundException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		}
		
	}
	
	
	
	
	private MockAtomEntry createTestEntry() {

		try {
			
			String title = "foo entry";
			String summary = "foo summary";
			MockAtomEntry entry = factory.createMockEntry();
			entry.setTitle(title);
			entry.setSummary(summary);

			entry = store.create(feedURL, entry);
			return entry;
			
		} catch (NotFoundException e) {

			fail(e.getLocalizedMessage());
			return null;
		}
		
	}

	
	
}
