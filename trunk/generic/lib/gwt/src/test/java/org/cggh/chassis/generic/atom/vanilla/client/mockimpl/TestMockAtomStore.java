/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.mockimpl;

import static org.junit.Assert.*;

import org.cggh.chassis.generic.atom.vanilla.client.protocol.NotFoundException;
import org.junit.Test;

/**
 * @author aliman
 *
 */
public class TestMockAtomStore {

	/**
	 * Test method for {@link org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomStore#create(java.lang.String, org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry)}.
	 */
	@Test
	public void testCreate() {
		
		// setup fixture
		String feedURL = "http://example.com/atom/myfeed";
		MockAtomFactory factory = new MockAtomFactory();
		MockAtomStore store = new MockAtomStore(factory);
		store.createFeed(feedURL, "my foo feed");
		
		// test data
		String title = "foo entry";
		String summary = "foo summary";
		
		// define new entry
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
		
		// setup fixture
		
		String feedURL = "http://example.com/atom/myfeed";
		MockAtomFactory factory = new MockAtomFactory();
		MockAtomStore store = new MockAtomStore(factory);
		store.createFeed(feedURL, "my foo feed");
		String title = "foo entry";
		String summary = "foo summary";
		MockAtomEntry entry = factory.createMockEntry();
		entry.setTitle(title);
		entry.setSummary(summary);
		String entryURL = null;
		
		try {
			
			entry = store.create(feedURL, entry);
			entryURL = entry.getEditLink().getHref();
			assertNotNull(entryURL);
			
		} catch (NotFoundException e) {

			fail(e.getLocalizedMessage());

		}
		

		try {
			
			// method under test
			MockAtomEntry retrievedEntry = store.retrieve(entryURL);
			
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

		// setup fixture
		
		String feedURL = "http://example.com/atom/myfeed";
		MockAtomFactory factory = new MockAtomFactory();
		MockAtomStore store = new MockAtomStore(factory);
		store.createFeed(feedURL, "my foo feed");
		String title = "foo entry";
		String summary = "foo summary";
		MockAtomEntry entry = factory.createMockEntry();
		entry.setTitle(title);
		entry.setSummary(summary);
		String entryURL = null;
		
		try {
			
			entry = store.create(feedURL, entry);
			entryURL = entry.getEditLink().getHref();
			assertNotNull(entryURL);
			System.out.println(entry.getUpdated());
			
		} catch (NotFoundException e) {

			fail(e.getLocalizedMessage());

		}
		
		// change some stuff
		entry.setTitle("some other title");
		entry.setSummary("some other summary");
		
		// wait so time can pass and date updated will change
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			fail(e1.getLocalizedMessage());
		}

		try {
			
			// method under test
			MockAtomEntry updatedEntry = store.update(entryURL, entry);
			
			// test outcome
			assertEquals(entry.getId(), updatedEntry.getId());
			assertEquals(entry.getPublished(), updatedEntry.getPublished());
			System.out.println(updatedEntry.getUpdated());
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
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomStore#retrieveAll(java.lang.String)}.
	 */
	@Test
	public void testRetrieveAll() {
		fail("Not yet implemented");
	}

}
