/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.format.impl;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.vanilla.client.format.Atom;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

/**
 * @author aliman
 *
 */
public class GWTTestAtomEntryImpl extends GWTTestCase {

	
	
	private AtomFactory factory;

	
	
	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.atom.vanilla.Atom";
	}
	
	
	
	@Override
	public void gwtSetUp() {
		factory = new AtomFactoryImpl();
	}

	
	
	public void testAddAuthor() {
	
		AtomEntry entry = factory.createEntry();
		
		// test initial state
		assertEquals(0, entry.getAuthors().size());
		assertEquals(0, XML.getElementsByTagName(entry.getElement(), Atom.ELEMENT_AUTHOR).size());
		
		// create and add an author
		AtomAuthor author = factory.createAuthor();
		author.setName("Bob");
		entry.addAuthor(author);
		
		// test outcome
		assertEquals(1, entry.getAuthors().size());
		assertEquals(1, XML.getElementsByTagName(entry.getElement(), Atom.ELEMENT_AUTHOR).size());
		assertEquals(author, entry.getAuthors().get(0));
		
	}
	
	
	
	public void testRemoveAuthor() {
		
		AtomEntry entry = factory.createEntry();
		AtomAuthor author = factory.createAuthor();
		author.setName("Bob");
		entry.addAuthor(author);
		
		// test initial state
		assertEquals(1, entry.getAuthors().size());
		assertEquals(1, XML.getElementsByTagName(entry.getElement(), Atom.ELEMENT_AUTHOR).size());
		assertEquals(author, entry.getAuthors().get(0));
		
		// remove author
		entry.removeAuthor(author);
		
		// test outcome
		assertEquals(0, entry.getAuthors().size());
		assertEquals(0, XML.getElementsByTagName(entry.getElement(), Atom.ELEMENT_AUTHOR).size());
		
	}
	
	
	
	public void testSetAuthors() {

		AtomEntry entry = factory.createEntry();
		
		// test initial state
		assertEquals(0, entry.getAuthors().size());
		assertEquals(0, XML.getElementsByTagName(entry.getElement(), Atom.ELEMENT_AUTHOR).size());
		
		// create and add an author
		AtomAuthor author = factory.createAuthor();
		author.setName("Bob");
		List<AtomAuthor> authors = new ArrayList<AtomAuthor>();
		authors.add(author);
		entry.setAuthors(authors);
		
		// test outcome
		assertEquals(1, entry.getAuthors().size());
		assertEquals(1, XML.getElementsByTagName(entry.getElement(), Atom.ELEMENT_AUTHOR).size());
		assertEquals(author, entry.getAuthors().get(0));

	}
	
	
	
	public void testAddCategory() {
		
		AtomEntry entry = factory.createEntry();
		
		// test initial state
		assertEquals(0, entry.getCategories().size());
		assertEquals(0, XML.getElementsByTagName(entry.getElement(), Atom.ELEMENT_CATEGORY).size());
		
		// create and add a category
		AtomCategory category = factory.createCategory();
		category.setTerm("foo");
		entry.addCategory(category);
		
		// test outcome
		assertEquals(1, entry.getCategories().size());
		assertEquals(1, XML.getElementsByTagName(entry.getElement(), Atom.ELEMENT_CATEGORY).size());
		assertEquals(category, entry.getCategories().get(0));
		
	}
	
	
	
	public void testRemoveCategory() {
		
		AtomEntry entry = factory.createEntry();
		AtomCategory category = factory.createCategory();
		category.setTerm("foo");
		entry.addCategory(category);
		
		// test initial state
		assertEquals(1, entry.getCategories().size());
		assertEquals(1, XML.getElementsByTagName(entry.getElement(), Atom.ELEMENT_CATEGORY).size());
		assertEquals(category, entry.getCategories().get(0));
		
		// remove author
		entry.removeCategory(category);
		
		// test outcome
		assertEquals(0, entry.getCategories().size());
		assertEquals(0, XML.getElementsByTagName(entry.getElement(), Atom.ELEMENT_CATEGORY).size());
		
	}
	
	
	
	public void testSetCategories() {

		AtomEntry entry = factory.createEntry();
		
		// test initial state
		assertEquals(0, entry.getCategories().size());
		assertEquals(0, XML.getElementsByTagName(entry.getElement(), Atom.ELEMENT_CATEGORY).size());
		
		// create and add an author
		AtomCategory category = factory.createCategory();
		category.setTerm("foo");
		List<AtomCategory> categories = new ArrayList<AtomCategory>();
		categories.add(category);
		entry.setCategories(categories);
		
		// test outcome
		assertEquals(1, entry.getCategories().size());
		assertEquals(1, XML.getElementsByTagName(entry.getElement(), Atom.ELEMENT_CATEGORY).size());
		assertEquals(category, entry.getCategories().get(0));

	}
	
	
	
	public void testAddLink() {
		
		AtomEntry entry = factory.createEntry();
		
		// test initial state
		assertEquals(0, entry.getLinks().size());
		assertEquals(0, XML.getElementsByTagName(entry.getElement(), Atom.ELEMENT_LINK).size());
		
		// create and add a Link
		AtomLink link = factory.createLink();
		link.setHref("http://example.org/foo");
		entry.addLink(link);
		
		// test outcome
		assertEquals(1, entry.getLinks().size());
		assertEquals(1, XML.getElementsByTagName(entry.getElement(), Atom.ELEMENT_LINK).size());
		assertEquals(link, entry.getLinks().get(0));
		
	}
	
	
	
	public void testRemoveLink() {
		
		AtomEntry entry = factory.createEntry();
		AtomLink link = factory.createLink();
		link.setHref("http://example.org/foo");
		entry.addLink(link);
		
		// test initial state
		assertEquals(1, entry.getLinks().size());
		assertEquals(1, XML.getElementsByTagName(entry.getElement(), Atom.ELEMENT_LINK).size());
		assertEquals(link, entry.getLinks().get(0));
		
		// remove author
		entry.removeLink(link);
		
		// test outcome
		assertEquals(0, entry.getLinks().size());
		assertEquals(0, XML.getElementsByTagName(entry.getElement(), Atom.ELEMENT_LINK).size());
		
	}
	
	
	
	public void testSetLinks() {

		AtomEntry entry = factory.createEntry();
		
		// test initial state
		assertEquals(0, entry.getLinks().size());
		assertEquals(0, XML.getElementsByTagName(entry.getElement(), Atom.ELEMENT_LINK).size());
		
		// create and add a link
		AtomLink link = factory.createLink();
		link.setHref("http://example.org/foo");
		List<AtomLink> links = new ArrayList<AtomLink>();
		links.add(link);
		entry.setLinks(links);
		
		// test outcome
		assertEquals(1, entry.getLinks().size());
		assertEquals(1, XML.getElementsByTagName(entry.getElement(), Atom.ELEMENT_LINK).size());
		assertEquals(link, entry.getLinks().get(0));

	}
	
	
	
	public void testToString() {
		
		AtomEntry entry = factory.createEntry();

		String title = "foo title";
		entry.setTitle(title);
		
		String summary = "foo summary";
		entry.setSummary(summary);
		
		// create and add an author
		AtomAuthor author = factory.createAuthor();
		author.setName("Bob");
		entry.addAuthor(author);

		// create and add a Link
		AtomLink link = factory.createLink();
		link.setHref("http://example.org/foo");
		entry.addLink(link);

		// create and add a category
		AtomCategory category = factory.createCategory();
		category.setTerm("foo");
		entry.addCategory(category);

		// call method under test
		String xml = entry.toString();
		
		// output XML for eyeball
		System.out.println(xml);

		// parse XML
		Document d = XMLParser.parse(xml);
		Element element = d.getDocumentElement();


		// test xml
		
		assertEquals(Atom.ELEMENT_ENTRY, element.getTagName());
		assertEquals(Atom.NSURI, element.getNamespaceURI());

		assertEquals(title, XML.getElementSimpleContentByTagName(element, Atom.ELEMENT_TITLE));
		assertEquals(summary, XML.getElementSimpleContentByTagName(element, Atom.ELEMENT_SUMMARY));
		assertNull(XML.getElementSimpleContentByTagName(element, Atom.ELEMENT_ID));
		assertNull(XML.getElementSimpleContentByTagName(element, Atom.ELEMENT_PUBLISHED));
		assertNull(XML.getElementSimpleContentByTagName(element, Atom.ELEMENT_UPDATED));

		assertEquals(1, XML.getElementsByTagName(element, Atom.ELEMENT_AUTHOR).size());

		assertEquals(1, XML.getElementsByTagName(element, Atom.ELEMENT_CATEGORY).size());

		assertEquals(1, XML.getElementsByTagName(element, Atom.ELEMENT_LINK).size());
		
	}
	
	
	
}