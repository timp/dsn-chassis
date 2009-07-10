/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.gwt.lib.xml.client.XML;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

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
		return "org.cggh.chassis.gwt.lib.atom.Atom";
	}
	
	public void test_constructor() {
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
	
	public void test_constructor_String() {
	
		String xml = 
			"<entry xmlns=\"http://www.w3.org/2005/Atom\">" +
				"<id>urn:uuid:f4070192-c65d-48d7-915f-81d38f61d359</id>" +
				"<updated>2009-07-06T13:51:34+01:00</updated>" +
				"<published>2009-07-06T12:04:29+01:00</published>" +
				"<link href=\"?id=urn:uuid:f4070192-c65d-48d7-915f-81d38f61d359\" rel=\"edit\" type=\"application/atom+xml\"/>" +
				"<title>Uganda-Kampala-2006</title>" +
				"<summary>A study of malaria drug resistance near Kampala, Uganda, from 2006 to 2007.</summary>" +
				"<author>" +
					"<name>Freda Bloggs</name>" +
					"<email>freda@example.org</email>" +
				"</author>" +
				"<category scheme=\"http://www.cggh.org/chassis/atom/categories\" term=\"study\"/>" +
			"</entry>";
		
		try {
			
			AtomEntry entry = new AtomEntry(xml);
			
			// test simple elements
			assertEquals("urn:uuid:f4070192-c65d-48d7-915f-81d38f61d359", entry.getId());
			assertEquals("2009-07-06T13:51:34+01:00", entry.getUpdated());
			assertEquals("2009-07-06T12:04:29+01:00", entry.getPublished());
			assertEquals("Uganda-Kampala-2006", entry.getTitle());
			assertEquals("A study of malaria drug resistance near Kampala, Uganda, from 2006 to 2007.", entry.getSummary());
			
			// test links
			List<AtomLink> links = entry.getLinks();
			assertEquals(1, links.size());
			AtomLink link = links.get(0);
			assertEquals("?id=urn:uuid:f4070192-c65d-48d7-915f-81d38f61d359", link.getHref());
			assertEquals(AtomNS.REL_EDIT, link.getRel());
			assertEquals(AtomNS.TYPE_ATOMMEDIATYPE, link.getType());
			assertNull(link.getHreflang());
			assertNull(link.getLength());
			assertNull(link.getTitle());
			assertNull(link.getHreflang());
			
			// test edit link shortcuts
			assertEquals("?id=urn:uuid:f4070192-c65d-48d7-915f-81d38f61d359", entry.getEditLink().getHref());
			assertNull(entry.getEditMediaLink());
			
			// test authors
			List<AtomPersonConstruct> authors = entry.getAuthors();
			assertEquals(1, authors.size());
			AtomPersonConstruct author = authors.get(0);
			assertEquals("Freda Bloggs", author.getName());
			assertEquals("freda@example.org", author.getEmail());
			assertNull(author.getUri());
			
			// test categories
			List<AtomCategory> categories = entry.getCategories();
			assertEquals(1, categories.size());
			AtomCategory category = categories.get(0);
			assertEquals("study", category.getTerm());
			assertEquals("http://www.cggh.org/chassis/atom/categories", category.getScheme());
			assertNull(category.getLabel());
			
		} catch (AtomFormatException ex) {
			ex.printStackTrace();
			fail(ex.getLocalizedMessage());
		}
	}
	
	
	public void test_simpleSetters() {
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
	
	public void test_setAuthors() {
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
	
	public void test_setCategories() {
		try {
			
			AtomEntry entry = new AtomEntry();
	
			List<AtomCategory> categories = new ArrayList<AtomCategory>();
			categories.add(new AtomCategory("foo", "http://foo.com/scheme", null));
			categories.add(new AtomCategory("bar", null, "bar label"));
			
			entry.setCategories(categories);
			
			assertEquals(2, entry.getCategories().size());
			
			AtomCategory foo = entry.getCategories().get(0);
			AtomCategory bar = entry.getCategories().get(1);
			
			assertEquals("foo", foo.getTerm());
			assertEquals("http://foo.com/scheme", foo.getScheme());
			assertNull(foo.getLabel());
	
			assertEquals("bar", bar.getTerm());
			assertNull(bar.getScheme());
			assertEquals("bar label", bar.getLabel());

		} catch (AtomFormatException ex) {
			ex.printStackTrace();
			fail(ex.getLocalizedMessage());
		}
	}

	public void test_toString() {
		try {
			
			AtomEntry entry = new AtomEntry();

			String title = "foo entry title";
			String summary = "foo summary";
			entry.setTitle(title);
			entry.setSummary(summary);

			List<AtomPersonConstruct> authors = new ArrayList<AtomPersonConstruct>();
			authors.add(new AtomPersonConstruct("bob", "bob@foo.com", null));
			authors.add(new AtomPersonConstruct("jill", null, "http://bar.com/jill"));
			entry.setAuthors(authors);

			List<AtomCategory> categories = new ArrayList<AtomCategory>();
			categories.add(new AtomCategory("foo", "http://foo.com/scheme", null));
			categories.add(new AtomCategory("bar", null, "bar label"));
			entry.setCategories(categories);
			
			String xml = entry.toString();
			
			System.out.print(xml);
			
			Document doc = XMLParser.parse(xml);
			
			// test title and summary
			assertEquals(title, XML.getElementSimpleContentByTagNameNS(doc, AtomNS.NS, AtomNS.TITLE));
			assertEquals(summary, XML.getElementSimpleContentByTagNameNS(doc, AtomNS.NS, AtomNS.SUMMARY));

			// test authors
			List<Element> authorElements = XML.getElementsByTagNameNS(doc, AtomNS.NS, AtomNS.AUTHOR);
			assertEquals(2, authorElements.size());

			assertEquals("bob", XML.getElementSimpleContentByTagNameNS(authorElements.get(0), AtomNS.NS, AtomNS.NAME));
			assertEquals("bob@foo.com", XML.getElementSimpleContentByTagNameNS(authorElements.get(0), AtomNS.NS, AtomNS.EMAIL));
			assertNull(XML.getElementSimpleContentByTagNameNS(authorElements.get(0), AtomNS.NS, AtomNS.URI));
			
			assertEquals("jill", XML.getElementSimpleContentByTagNameNS(authorElements.get(1), AtomNS.NS, AtomNS.NAME));
			assertNull(XML.getElementSimpleContentByTagNameNS(authorElements.get(1), AtomNS.NS, AtomNS.EMAIL));
			assertEquals("http://bar.com/jill", XML.getElementSimpleContentByTagNameNS(authorElements.get(1), AtomNS.NS, AtomNS.URI));
			
			// test categories
			List<Element> categoryElements = XML.getElementsByTagNameNS(doc, AtomNS.NS, AtomNS.CATEGORY);
			assertEquals(2, categoryElements.size());
			
			assertEquals("foo", categoryElements.get(0).getAttribute(AtomNS.TERM));
			assertEquals("http://foo.com/scheme", categoryElements.get(0).getAttribute(AtomNS.SCHEME));
			assertNull(categoryElements.get(0).getAttribute(AtomNS.LABEL));

			assertEquals("bar", categoryElements.get(1).getAttribute(AtomNS.TERM));
			assertNull(categoryElements.get(1).getAttribute(AtomNS.SCHEME));
			assertEquals("bar label", categoryElements.get(1).getAttribute(AtomNS.LABEL));
			
		} catch (AtomFormatException ex) {
			ex.printStackTrace();
			fail(ex.getLocalizedMessage());
		}
	}
}
