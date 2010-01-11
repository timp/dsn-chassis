/**
 * 
 */
package org.cggh.chassis.generic.atom.client.vanilla;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.client.Atom;
import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atom.client.AtomCategory;
import org.cggh.chassis.generic.atom.client.AtomLink;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

/**
 * @author aliman
 *
 */
public class GWTTestVanillaAtomEntry extends GWTTestCase {

	
	
	private VanillaAtomFactory factory;
	private Log log = LogFactory.getLog(GWTTestVanillaAtomEntry.class);

	
	
	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.atom.Atom";
	}
	
	
	
	@Override
	public void gwtSetUp() {
		factory = new VanillaAtomFactory();
	}

	
	
	public void brokenTestAddAuthor() {
	
		VanillaAtomEntry entry = factory.createEntry();
		
		// test initial state
		assertEquals(0, entry.getAuthors().size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(entry.getElement(), Atom.ELEMENT_AUTHOR, Atom.NSURI).size());
		
		// create and add an author
		AtomAuthor author = factory.createAuthor();
		author.setName("Bob");
		entry.addAuthor(author);
		
		// test outcome
		assertEquals(1, entry.getAuthors().size());
		assertEquals(1, XMLNS.getElementsByTagNameNS(entry.getElement(), Atom.ELEMENT_AUTHOR, Atom.NSURI).size());
		assertEquals(author, entry.getAuthors().get(0));
		
	}
	
	
	
	public void brokenTestRemoveAuthor() {
		
		VanillaAtomEntry entry = factory.createEntry();
		AtomAuthor author = factory.createAuthor();
		author.setName("Bob");
		entry.addAuthor(author);
		
		// test initial state
		assertEquals(1, entry.getAuthors().size());
		assertEquals(1, XMLNS.getElementsByTagNameNS(entry.getElement(), Atom.ELEMENT_AUTHOR, Atom.NSURI).size());
		assertEquals(author, entry.getAuthors().get(0));
		
		// remove author
		entry.removeAuthor(author);
		
		// test outcome
		assertEquals(0, entry.getAuthors().size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(entry.getElement(), Atom.ELEMENT_AUTHOR, Atom.NSURI).size());
		
	}
	
	
	
	public void brokenTestSetAuthors() {

		VanillaAtomEntry entry = factory.createEntry();
		
		// test initial state
		assertEquals(0, entry.getAuthors().size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(entry.getElement(), Atom.ELEMENT_AUTHOR, Atom.NSURI).size());
		
		// create and add an author
		AtomAuthor author = factory.createAuthor();
		author.setName("Bob");
		List<AtomAuthor> authors = new ArrayList<AtomAuthor>();
		authors.add(author);
		entry.setAuthors(authors);
		
		// test outcome
		assertEquals(1, entry.getAuthors().size());
		assertEquals(1, XMLNS.getElementsByTagNameNS(entry.getElement(), Atom.ELEMENT_AUTHOR, Atom.NSURI).size());
		assertEquals(author, entry.getAuthors().get(0));

	}
	
	
	
	public void brokenTestAddCategory() {
		
		VanillaAtomEntry entry = factory.createEntry();
		
		// test initial state
		assertEquals(0, entry.getCategories().size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(entry.getElement(), Atom.ELEMENT_CATEGORY, Atom.NSURI).size());
		
		// create and add a category
		AtomCategory category = factory.createCategory();
		category.setTerm("foo");
		entry.addCategory(category);
		
		// test outcome
		assertEquals(1, entry.getCategories().size());
		assertEquals(1, XMLNS.getElementsByTagNameNS(entry.getElement(), Atom.ELEMENT_CATEGORY, Atom.NSURI).size());
		assertEquals(category, entry.getCategories().get(0));
		
	}
	
	
	
	public void brokenTestRemoveCategory() {
		
		VanillaAtomEntry entry = factory.createEntry();
		AtomCategory category = factory.createCategory();
		category.setTerm("foo");
		entry.addCategory(category);
		
		// test initial state
		assertEquals(1, entry.getCategories().size());
		assertEquals(1, XMLNS.getElementsByTagNameNS(entry.getElement(), Atom.ELEMENT_CATEGORY, Atom.NSURI).size());
		assertEquals(category, entry.getCategories().get(0));
		
		// remove author
		entry.removeCategory(category);
		
		// test outcome
		assertEquals(0, entry.getCategories().size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(entry.getElement(), Atom.ELEMENT_CATEGORY, Atom.NSURI).size());
		
	}
	
	
	
	public void brokenTestSetCategories() {

		VanillaAtomEntry entry = factory.createEntry();
		
		// test initial state
		assertEquals(0, entry.getCategories().size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(entry.getElement(), Atom.ELEMENT_CATEGORY, Atom.NSURI).size());
		
		// create and add an author
		AtomCategory category = factory.createCategory();
		category.setTerm("foo");
		List<AtomCategory> categories = new ArrayList<AtomCategory>();
		categories.add(category);
		entry.setCategories(categories);
		
		// test outcome
		assertEquals(1, entry.getCategories().size());
		assertEquals(1, XMLNS.getElementsByTagNameNS(entry.getElement(), Atom.ELEMENT_CATEGORY, Atom.NSURI).size());
		assertEquals(category, entry.getCategories().get(0));

	}
	
	
	
	public void brokenTestAddLink() {
		
		VanillaAtomEntry entry = factory.createEntry();
		
		// test initial state
		assertEquals(0, entry.getLinks().size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(entry.getElement(), Atom.ELEMENT_LINK, Atom.NSURI).size());
		
		// create and add a Link
		AtomLink link = factory.createLink();
		link.setHref("http://example.org/foo");
		entry.addLink(link);
		
		// test outcome
		assertEquals(1, entry.getLinks().size());
		assertEquals(1, XMLNS.getElementsByTagNameNS(entry.getElement(), Atom.ELEMENT_LINK, Atom.NSURI).size());
		assertEquals(link, entry.getLinks().get(0));
		
	}
	
	
	
	public void brokenTestRemoveLink() {
		
		VanillaAtomEntry entry = factory.createEntry();
		AtomLink link = factory.createLink();
		link.setHref("http://example.org/foo");
		entry.addLink(link);
		
		// test initial state
		assertEquals(1, entry.getLinks().size());
		assertEquals(1, XMLNS.getElementsByTagNameNS(entry.getElement(), Atom.ELEMENT_LINK, Atom.NSURI).size());
		assertEquals(link, entry.getLinks().get(0));
		
		// remove author
		entry.removeLink(link);
		
		// test outcome
		assertEquals(0, entry.getLinks().size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(entry.getElement(), Atom.ELEMENT_LINK, Atom.NSURI).size());
		
	}
	
	
	
	public void brokenTestSetLinks() {

		VanillaAtomEntry entry = factory.createEntry();
		
		// test initial state
		assertEquals(0, entry.getLinks().size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(entry.getElement(), Atom.ELEMENT_LINK, Atom.NSURI).size());
		
		// create and add a link
		AtomLink link = factory.createLink();
		link.setHref("http://example.org/foo");
		List<AtomLink> links = new ArrayList<AtomLink>();
		links.add(link);
		entry.setLinks(links);
		
		// test outcome
		assertEquals(1, entry.getLinks().size());
		assertEquals(1, XMLNS.getElementsByTagNameNS(entry.getElement(), Atom.ELEMENT_LINK, Atom.NSURI).size());
		assertEquals(link, entry.getLinks().get(0));

	}
	
	
	
	public void brokenTestToString() {
		
		VanillaAtomEntry entry = factory.createEntry();

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
		
		assertEquals(Atom.ELEMENT_ENTRY, XML.getLocalName(element));
		assertEquals(Atom.NSURI, element.getNamespaceURI());

		assertEquals(title, XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_TITLE, Atom.NSURI));
		assertEquals(summary, XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_SUMMARY, Atom.NSURI));
		assertNull(XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_ID, Atom.NSURI));
		assertNull(XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_PUBLISHED, Atom.NSURI));
		assertNull(XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_UPDATED, Atom.NSURI));

		assertEquals(1, XMLNS.getElementsByTagNameNS(element, Atom.ELEMENT_AUTHOR, Atom.NSURI).size());

		assertEquals(1, XMLNS.getElementsByTagNameNS(element, Atom.ELEMENT_CATEGORY, Atom.NSURI).size());

		assertEquals(1, XMLNS.getElementsByTagNameNS(element, Atom.ELEMENT_LINK, Atom.NSURI).size());
		
	}
	
	
	
	
	public void brokenTestNamespacesNotEmpty() {
		log.enter("testNamespacesNotEmpty");
		
		VanillaAtomEntry entry = factory.createEntry();

		String title = "foo title";
		entry.setTitle(title);
		
		String summary = "foo summary";
		entry.setSummary(summary);
		
		String xml = entry.toString();
		
		// output XML for eyeball
		System.out.println(xml);

		// parse XML
		Document d = XMLParser.parse(xml);
		Element element = d.getDocumentElement();


		// test xml
		
		assertEquals(Atom.ELEMENT_ENTRY, XML.getLocalName(element));
		assertEquals(Atom.NSURI, element.getNamespaceURI());

		assertEquals(title, XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_TITLE, Atom.NSURI));
		assertEquals(summary, XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_SUMMARY, Atom.NSURI));
		
		Element titleElement = XMLNS.getFirstElementByTagNameNS(element, Atom.ELEMENT_TITLE, Atom.NSURI);
		assertEquals(Atom.NSURI, titleElement.getNamespaceURI());

		Element summaryElement = XMLNS.getFirstElementByTagNameNS(element, Atom.ELEMENT_SUMMARY, Atom.NSURI);
		assertEquals(Atom.NSURI, summaryElement.getNamespaceURI());

		log.leave();
	}
	
	
	
	public void testMinimal() {
		
		String xml = "<foo xmlns='http://example.com/xmlns'></foo>";
		Document d = XMLParser.parse(xml);
		Element bar = d.createElement("bar");
		d.getDocumentElement().appendChild(bar);
		bar.appendChild(d.createTextNode("baz"));
		
		String expected = "<foo xmlns=\"http://example.com/xmlns\"><bar>baz</bar></foo>";
		assertEquals(expected, d.toString());
		
	}
	

	
//	public void testMinimalWorkaround() {
//	
//		String xml = "<x:foo xmlns:x='http://example.com/xmlns'></x:foo>";
//		Document d = XMLParser.parse(xml);
//		Element bar = d.createElement("bar");
//		d.getDocumentElement().appendChild(bar);
//		bar.appendChild(d.createTextNode("baz"));
//		
//		String expected = "<x:foo xmlns:x=\"http://example.com/xmlns\"><bar>baz</bar></x:foo>";
//		assertEquals(expected, d.toString());
//	
//	}

	
//	public void testMinimalWorkaround2() {
//		
//		String xml = "<x:foo xmlns:x='http://example.com/xmlns'></x:foo>";
//		Document d = XMLParser.parse(xml);
//		Element bar = d.createElement("x:bar");
//		d.getDocumentElement().appendChild(bar);
//		bar.appendChild(d.createTextNode("baz"));
//		
//		String expected = "<x:foo xmlns:x=\"http://example.com/xmlns\"><x:bar>baz</x:bar></x:foo>";
//		assertEquals(expected, d.toString());
//	
//	}

}
