/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client;

import java.util.ArrayList;
import java.util.List;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class TestAtomEntry extends GWTTestCase {

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return "org.cggh.chassis.gwt.lib.atom.Atom";
	}
	
	public void testNewInstance() {
		
		try {

			AtomEntry entry = new AtomEntry();
			
			assertNull(entry.getTitle());
			assertNull(entry.getId());
			assertNull(entry.getSummary());
			assertNull(entry.getUpdated());
			assertEquals(0, entry.getAuthors().size());
			assertEquals(0, entry.getCategories().size());

		} catch (AtomFormatException ex) {
			ex.printStackTrace();
			fail(ex.getLocalizedMessage());
		}
		
	}
	
	public void testSimpleSetters() {

		try {
			
			AtomEntry entry = new AtomEntry();
			
			String title = "foo entry title";
			String summary = "foo summary";
			entry.setTitle(title);
			entry.setSummary(summary);
			
			assertEquals(title, entry.getTitle());
			assertEquals(summary, entry.getSummary());
			
//			Document d = XMLParser.parse(entry.toString());
	
		} catch (AtomFormatException ex) {
			ex.printStackTrace();
			fail(ex.getLocalizedMessage());
		}

	}
	
	public void testSetAuthors() {

		try {
			
			AtomEntry entry = new AtomEntry();
	
			List<AtomPersonConstruct> authors = new ArrayList<AtomPersonConstruct>();
			
			authors.add(new AtomPersonConstruct("bob", "bob@foo.com", null));
			authors.add(new AtomPersonConstruct("jill", null, "http://bar.com/jill"));
			
			entry.setAuthors(authors);
			
			assertEquals(2, entry.getAuthors().size());
			
			assertEquals("bob", entry.getAuthors().get(0).getName());
			assertEquals("bob@foo.com", entry.getAuthors().get(0).getEmail());
			assertNull(entry.getAuthors().get(0).getUri());
	
			assertEquals("jill", entry.getAuthors().get(1).getName());
			assertNull(entry.getAuthors().get(1).getEmail());
			assertEquals("http://bar.com/jill", entry.getAuthors().get(1).getUri());

		} catch (AtomFormatException ex) {
			ex.printStackTrace();
			fail(ex.getLocalizedMessage());
		}

	}

}
