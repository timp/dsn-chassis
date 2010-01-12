/**
 * 
 */
package org.cggh.chassis.generic.atom.client.vanilla;

import org.cggh.chassis.generic.atom.client.Atom;
import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atom.client.AtomCategory;
import org.cggh.chassis.generic.atom.client.AtomFormatException;
import org.cggh.chassis.generic.atom.client.AtomLink;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XMLNS;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

/**
 * @author aliman
 *
 */
public class GWTTestVanillaAtomFactory extends GWTTestCase {

	
	
	
	private VanillaAtomFactory factory;
	
	
	
	
	private Log log = LogFactory.getLog(GWTTestVanillaAtomFactory.class);

	
	
	
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
	
	
	
	
	
	public void testCreateLink() {
		
		AtomLink link = factory.createLink();
		
		String href = "http://foo.bar/baz";
		link.setHref(href);
		
		String hreflang = "en";
		link.setHrefLang(hreflang);
		
		String length = "42";
		link.setLength(length);
		
		String rel = "foorel";
		link.setRel(rel);
		
		String title = "foo title";
		link.setTitle(title);
		
		String type = "foo/bar";
		link.setType(type);
		
		// test getters
		
		assertEquals(href, link.getHref());
		assertEquals(hreflang, link.getHrefLang());
		assertEquals(length, link.getLength());
		assertEquals(rel, link.getRel());
		assertEquals(title, link.getTitle());
		assertEquals(type, link.getType());
		
		// test XML
	
		Element element = link.getElement();
		
		assertEquals(Atom.ELEMENT_LINK, XML.getLocalName(element));
		assertEquals(Atom.NSURI, element.getNamespaceURI());
		assertEquals(href, element.getAttribute(Atom.ATTR_HREF));
		assertEquals(hreflang, element.getAttribute(Atom.ATTR_HREFLANG));
		assertEquals(length, element.getAttribute(Atom.ATTR_LENGTH));
		assertEquals(rel, element.getAttribute(Atom.ATTR_REL));
		assertEquals(title, element.getAttribute(Atom.ATTR_TITLE));
		assertEquals(type, element.getAttribute(Atom.ATTR_TYPE));
		assertEquals(0, XML.elements(element.getChildNodes()).size());

	}
	
	
	
	public void testCreateLink_Element() {
		
		String href = "http://foo.bar/baz";
		String hreflang = "en";
		String length = "42";
		String rel = "foorel";
		String title = "foo title";
		String type = "foo/bar";

		String xml = "<link xmlns=\""+Atom.NSURI+"\" href=\""+href+"\" hreflang=\""+hreflang+"\" length=\""+length+"\" rel=\""+rel+"\" title=\""+title+"\" type=\""+type+"\" />";

		Document d = XMLParser.parse(xml);
		
		try {

			AtomLink link = factory.createLink(d.getDocumentElement());
			
			assertEquals(href, link.getHref());
			assertEquals(hreflang, link.getHrefLang());
			assertEquals(length, link.getLength());
			assertEquals(rel, link.getRel());
			assertEquals(title, link.getTitle());
			assertEquals(type, link.getType());
						
		}
		catch (AtomFormatException e) {
			
			e.printStackTrace();
			fail(e.getLocalizedMessage());
			
		}
		
	}
	

	
	@SuppressWarnings("unused")
	public void testCreateLink_Element_AtomFormatExceptions() {
		
		try {

			String xml = "<foo/>";
			Document d = XMLParser.parse(xml);
			AtomLink link = factory.createLink(d.getDocumentElement());
			fail("expected format exception (bad element name)");
			
		}
		catch (AtomFormatException e) {

			// expected
			
		}
		
		try {

			String xml = "<link/>";
			Document d = XMLParser.parse(xml);
			AtomLink link = factory.createLink(d.getDocumentElement());
			fail("expected format exception (bad namespace URI)");
			
		}
		catch (AtomFormatException e) {

			// expected
			
		}
		
	}

	

	public void testCreateAuthor() {
		
		AtomAuthor author = factory.createAuthor();
		
		String name = "bob";
		author.setName(name);
		
		String email = "bob@example.org";
		author.setEmail(email);
		
		String uri = "http://foo.bar/bob";
		author.setURI(uri);
		
		// test getters
		
		assertEquals(name, author.getName());
		assertEquals(email, author.getEmail());
		assertEquals(uri, author.getURI());
		
		// test xml
		
		Element element = author.getElement();
		
		assertEquals(Atom.ELEMENT_AUTHOR, XML.getLocalName(element));
		assertEquals(Atom.NSURI, element.getNamespaceURI());
		// TODO Fail with htmlunit browser 
		// assertEquals(name, XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_NAME, Atom.NSURI));
		// assertEquals(email, XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_EMAIL, Atom.NSURI));
		// assertEquals(uri, XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_URI, Atom.NSURI));
		assertEquals(3, XML.elements(element.getChildNodes()).size());
		
	}
	
	
	
	public void testCreateAuthor_Element() {
		 
		String name = "bob";
		String email = "bob@example.org";
		String uri = "http://foo.bar/bob";

		String xml = "<author xmlns=\""+Atom.NSURI+"\"><name>"+name+"</name><email>"+email+"</email><uri>"+uri+"</uri></author>";

		Document d = XMLParser.parse(xml);
		
		try {

			AtomAuthor author = factory.createAuthor(d.getDocumentElement());
			
			assertEquals(name, author.getName());
			assertEquals(email, author.getEmail());
			assertEquals(uri, author.getURI());
						
		}
		catch (AtomFormatException e) {
			
			e.printStackTrace();
			fail(e.getLocalizedMessage());
			
		}
		
	}
	

	@SuppressWarnings("unused")
	public void testCreateAuthor_Element_AtomFormatExceptions() {
		
		try {

			String xml = "<foo/>";
			Document d = XMLParser.parse(xml);
			AtomAuthor author = factory.createAuthor(d.getDocumentElement());
			fail("expected format exception (bad element name)");
			
		}
		catch (AtomFormatException e) {

			// expected
			
		}
		
		try {

			String xml = "<author/>";
			Document d = XMLParser.parse(xml);
			AtomAuthor author = factory.createAuthor(d.getDocumentElement());
			fail("expected format exception (bad namespace URI)");
			
		}
		catch (AtomFormatException e) {

			// expected
			
		}
		
	}

	

	public void testCreateCategory() {
		
		AtomCategory category = factory.createCategory();
		
		String label = "foolabel";
		category.setLabel(label);
		
		String term = "footerm";
		category.setTerm(term);
		
		String scheme = "http://foo.bar/scheme";
		category.setScheme(scheme);
		
		// test getters
		
		assertEquals(label, category.getLabel());
		assertEquals(term, category.getTerm());
		assertEquals(scheme, category.getScheme());
		
		// test xml
		
		Element element = category.getElement();
		
		assertEquals(Atom.ELEMENT_CATEGORY, XML.getLocalName(element));
		assertEquals(Atom.NSURI, element.getNamespaceURI());
		assertEquals(label, element.getAttribute(Atom.ATTR_LABEL));
		assertEquals(term, element.getAttribute(Atom.ATTR_TERM));
		assertEquals(scheme, element.getAttribute(Atom.ATTR_SCHEME));
		assertEquals(0, XML.elements(element.getChildNodes()).size());
		
	}
	
	

	public void testCreateCategory_Element() {
		 
		String label = "foolabel";
		String term = "footerm";
		String scheme = "http://foo.bar/scheme";

		String xml = "<category xmlns=\""+Atom.NSURI+"\" label=\""+label+"\" term=\""+term+"\" scheme=\""+scheme+"\" />";

		Document d = XMLParser.parse(xml);
		
		try {

			AtomCategory category = factory.createCategory(d.getDocumentElement());
			
			assertEquals(label, category.getLabel());
			assertEquals(term, category.getTerm());
			assertEquals(scheme, category.getScheme());
						
		}
		catch (AtomFormatException e) {
			
			e.printStackTrace();
			fail(e.getLocalizedMessage());
			
		}
		
	}
	

	
	@SuppressWarnings("unused")
	public void testCreateCategory_Element_AtomFormatExceptions() {
		
		try {

			String xml = "<foo/>";
			Document d = XMLParser.parse(xml);
			AtomCategory category = factory.createCategory(d.getDocumentElement());
			fail("expected format exception (bad element name)");
			
		}
		catch (AtomFormatException e) {

			// expected
			
		}
		
		try {

			String xml = "<category/>";
			Document d = XMLParser.parse(xml);
			AtomCategory category = factory.createCategory(d.getDocumentElement());
			fail("expected format exception (bad namespace URI)");
			
		}
		catch (AtomFormatException e) {

			// expected
			
		}
		
	}

	

	public void testCreateEntry() {
		
		VanillaAtomEntry entry = factory.createEntry();

		String title = "foo title";
		entry.setTitle(title);
		
		String summary = "foo summary";
		entry.setSummary(summary);
		
		// test getters
		
		assertEquals(title, entry.getTitle());
		assertEquals(summary, entry.getSummary());
		assertNull(entry.getId());
		assertNull(entry.getPublished());
		assertNull(entry.getUpdated());
		
		// test xml
		
		Element element = entry.getElement();
		
		assertEquals(Atom.ELEMENT_ENTRY, XML.getLocalName(element));
		assertEquals(Atom.NSURI, element.getNamespaceURI());
		// TODO Fail with htmlunit browser 
		// assertEquals(title, XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_TITLE, Atom.NSURI));
		// assertEquals(summary, XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_SUMMARY, Atom.NSURI));
		assertNull(XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_ID, Atom.NSURI));
		assertNull(XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_PUBLISHED, Atom.NSURI));
		assertNull(XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_UPDATED, Atom.NSURI));
		assertEquals(2, XML.elements(element.getChildNodes()).size());
		
	}

	
	
	public void testCreateEntry_String() {
		 

		String entryDocument = 
			"<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
			"<entry xmlns=\"http://www.w3.org/2005/Atom\">" +
				"<title>Atom draft-07 snapshot</title>" +
				"<summary>foo bar summary</summary>" +
				"<id>tag:example.org,2003:3.2397</id>" +
				"<updated>2005-07-31T12:29:29Z</updated>" +
				"<published>2003-12-13T08:29:29-04:00</published>" +
				"<link rel=\"alternate\" type=\"text/html\" href=\"http://example.org/2005/04/02/atom\"/>" +
				"<link rel=\"enclosure\" type=\"audio/mpeg\" length=\"1337\" href=\"http://example.org/audio/ph34r_my_podcast.mp3\"/>" +
				"<link rel=\"edit\" href=\"http://example.org/atom/edit?id=tag:example.org,2003:3.2397\"/>" +
				"<category term=\"footerm\" label=\"foolabel\" scheme=\"fooscheme\" />" +
				"<category term=\"barterm\" label=\"barlabel\" scheme=\"barscheme\" />" +
				"<author>" +
					"<name>Mark Pilgrim</name>" +
					"<uri>http://example.org/</uri>" +
					"<email>f8dy@example.com</email>" +
				"</author>" +
				"<author>" +
					"<name>Foo Bar</name>" +
					"<uri>http://foo.org/</uri>" +
					"<email>sings@example.com</email>" +
				"</author>" +
			"</entry>";

		try {

			VanillaAtomEntry entry = factory.createEntry(entryDocument);
			
			// check simple properties
			assertEquals("Atom draft-07 snapshot", entry.getTitle());
			assertEquals("foo bar summary", entry.getSummary());
			assertEquals("tag:example.org,2003:3.2397", entry.getId());
			assertEquals("2003-12-13T08:29:29-04:00", entry.getPublished());
			assertEquals("2005-07-31T12:29:29Z", entry.getUpdated());
			
			// check links
			assertEquals(3, entry.getLinks().size());
			assertEquals("alternate", entry.getLinks().get(0).getRel());
			assertEquals("text/html", entry.getLinks().get(0).getType());
			assertEquals("http://example.org/2005/04/02/atom", entry.getLinks().get(0).getHref());
			assertEquals("enclosure", entry.getLinks().get(1).getRel());
			assertEquals("audio/mpeg", entry.getLinks().get(1).getType());
			assertEquals("1337", entry.getLinks().get(1).getLength());
			assertEquals("http://example.org/audio/ph34r_my_podcast.mp3", entry.getLinks().get(1).getHref());
			assertEquals(Atom.REL_EDIT, entry.getEditLink().getRel());
			assertEquals("http://example.org/atom/edit?id=tag:example.org,2003:3.2397", entry.getEditLink().getHref());
			
			// check categories
			assertEquals(2, entry.getCategories().size());
			assertEquals("footerm", entry.getCategories().get(0).getTerm());
			assertEquals("foolabel", entry.getCategories().get(0).getLabel());
			assertEquals("fooscheme", entry.getCategories().get(0).getScheme());
			assertEquals("barterm", entry.getCategories().get(1).getTerm());
			assertEquals("barlabel", entry.getCategories().get(1).getLabel());
			assertEquals("barscheme", entry.getCategories().get(1).getScheme());
			
			// check authors
			assertEquals(2, entry.getAuthors().size());
			assertEquals("Mark Pilgrim", entry.getAuthors().get(0).getName());
			assertEquals("http://example.org/", entry.getAuthors().get(0).getURI());
			assertEquals("f8dy@example.com", entry.getAuthors().get(0).getEmail());
			assertEquals("Foo Bar", entry.getAuthors().get(1).getName());
			assertEquals("http://foo.org/", entry.getAuthors().get(1).getURI());
			assertEquals("sings@example.com", entry.getAuthors().get(1).getEmail());
			
		}
		catch (AtomFormatException e) {
			
			e.printStackTrace();
			fail(e.getLocalizedMessage());
			
		}
		
	}
	

	
	@SuppressWarnings("unused")
	public void testCreateEntry_String_AtomFormatExceptions() {
		
		try {

			String xml = "<foo/>";
			VanillaAtomEntry entry = factory.createEntry(xml);
			fail("expected format exception (bad element name)");
			
		}
		catch (AtomFormatException e) {

			// expected
			
		}
		
		try {

			String xml = "<entry/>";
			VanillaAtomEntry entry = factory.createEntry(xml);
			fail("expected format exception (bad namespace URI)");
			
		}
		catch (AtomFormatException e) {

			// expected
			
		}

		try {
			
			String xml = "<entry>";
			VanillaAtomEntry entry = factory.createEntry(xml);
			fail("expected format exception (not well-formed XML)");

		}
		catch (AtomFormatException e) {
	
			// expected
			
		}
		

	}
	
	
	
	public void testCreateFeed_String() {
		log.enter("testCreateFeed_String");
		
		String feedDocument = 
			"<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
			"<feed xmlns=\"http://www.w3.org/2005/Atom\">" +
				"<title type=\"text\">dive into mark</title>" +
				"<updated>2005-07-31T12:29:29Z</updated>" +
				"<id>tag:example.org,2003:3</id>" +
				"<entry xmlns=\"http://www.w3.org/2005/Atom\">" +
					"<title>Atom draft-07 snapshot</title>" +
					"<summary>foo bar summary</summary>" +
					"<id>tag:example.org,2003:3.2397</id>" +
					"<updated>2005-07-31T12:29:29Z</updated>" +
					"<published>2003-12-13T08:29:29-04:00</published>" +
					"<link rel=\"alternate\" type=\"text/html\" href=\"http://example.org/2005/04/02/atom\"/>" +
					"<link rel=\"enclosure\" type=\"audio/mpeg\" length=\"1337\" href=\"http://example.org/audio/ph34r_my_podcast.mp3\"/>" +
					"<link rel=\"edit\" href=\"http://example.org/atom/edit?id=tag:example.org,2003:3.2397\"/>" +
					"<category term=\"footerm\" label=\"foolabel\" scheme=\"fooscheme\" />" +
					"<category term=\"barterm\" label=\"barlabel\" scheme=\"barscheme\" />" +
					"<author>" +
						"<name>Mark Pilgrim</name>" +
						"<uri>http://example.org/</uri>" +
						"<email>f8dy@example.com</email>" +
					"</author>" +
					"<author>" +
						"<name>Foo Bar</name>" +
						"<uri>http://foo.org/</uri>" +
						"<email>sings@example.com</email>" +
					"</author>" +
				"</entry>" +
				"<entry xmlns=\"http://www.w3.org/2005/Atom\">" +
					"<title>foo entry</title>" +
					"<summary>baz summary</summary>" +
					"<id>tag:example.org,2005:4.3345</id>" +
					"<updated>2009-07-31T12:29:29Z</updated>" +
					"<published>2005-12-13T08:29:29-04:00</published>" +
					"<link rel=\"edit\" href=\"http://example.org/atom/edit?id=tag:example.org,2005:4.3345\"/>" +
					"<author>" +
						"<name>Foo Bar</name>" +
						"<uri>http://foo.org/</uri>" +
						"<email>sings@example.com</email>" +
					"</author>" +
				"</entry>" +
			"</feed>";
		
		// spike
//		Document d = XMLParser.parse(feedDocument);
//		String prefix = d.getDocumentElement().getPrefix();
//		log.trace("prefix: "+prefix);
//		log.trace("tag name: "+d.getDocumentElement().getTagName());
//		log.trace("node name: "+d.getDocumentElement().getNodeName());	
//		log.trace("local name: "+XML.getLocalName(d.getDocumentElement()));
		
		try {
		
			VanillaAtomFeed feed = factory.createFeed(feedDocument);
			
			// check feed simple properties
			assertEquals("dive into mark", feed.getTitle());
			assertEquals("tag:example.org,2003:3", feed.getId());
			assertEquals("2005-07-31T12:29:29Z", feed.getUpdated());
			
			// check entries
			assertEquals(2, feed.getEntries().size());
			assertEquals("Atom draft-07 snapshot", feed.getEntries().get(0).getTitle());
			assertEquals("f8dy@example.com", feed.getEntries().get(0).getAuthors().get(0).getEmail());
			assertEquals("baz summary", feed.getEntries().get(1).getSummary());
			assertEquals("http://foo.org/", feed.getEntries().get(1).getAuthors().get(0).getURI());
			
		}
		catch (AtomFormatException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
		
		log.leave();
	}
	
	
	

	
	public void testCreateFeed_String_qNames_1() {
		log.enter("testCreateFeed_String_qNames");
		
		// this example came from exist
		
		String feedDocument = 
			"<atom:feed xmlns:atom=\"http://www.w3.org/2005/Atom\">" +
				"<id xmlns=\"http://www.w3.org/2005/Atom\">urn:uuid:2343ad67-16ab-41ad-bd93-7b01db80a85c</id>" +
				"<updated xmlns=\"http://www.w3.org/2005/Atom\">2009-09-17T15:23:10+01:00</updated>" +
				"<title xmlns=\"http://www.w3.org/2005/Atom\">Example Collection</title>" +
				"<link xmlns=\"http://www.w3.org/2005/Atom\" href=\"#\" rel=\"edit\" type=\"application/atom+xml\"/>" +
				"<link xmlns=\"http://www.w3.org/2005/Atom\" href=\"#\" rel=\"self\" type=\"application/atom+xml\"/>" +
				"<entry xmlns=\"http://www.w3.org/2005/Atom\">" +
					"<id>urn:uuid:c5e4f0f0-8b0c-4162-8123-a74a3c729b3b</id>" +
					"<updated>2009-09-17T15:23:10+01:00</updated>" +
					"<published>2009-09-17T15:23:10+01:00</published>" +
					"<link href=\"?id=urn:uuid:c5e4f0f0-8b0c-4162-8123-a74a3c729b3b\" rel=\"edit\" type=\"application/atom+xml\"/>" +
					"<title>a vanilla atom entry</title>" +
					"<summary>this is a vanilla atom entry, that's all</summary>" +
					"<author><name>bob</name><email>bob@example.com</email></author>" +
				"</entry>" +
			"</atom:feed>";
		
		// spike
//		Document d = XMLParser.parse(feedDocument);
//		String prefix = d.getDocumentElement().getPrefix();
//		log.trace("prefix: "+prefix);
//		log.trace("tag name: "+d.getDocumentElement().getTagName());
//		log.trace("node name: "+d.getDocumentElement().getNodeName());	
//		log.trace("local name: "+XML.getLocalName(d.getDocumentElement()));
		
		try {
		
			VanillaAtomFeed feed = factory.createFeed(feedDocument);
			
			// check feed simple properties
			assertEquals("Example Collection", feed.getTitle());
			assertEquals("urn:uuid:2343ad67-16ab-41ad-bd93-7b01db80a85c", feed.getId());
			assertEquals("2009-09-17T15:23:10+01:00", feed.getUpdated());
			
			// check entries
			assertEquals(1, feed.getEntries().size());
			assertEquals("urn:uuid:c5e4f0f0-8b0c-4162-8123-a74a3c729b3b", feed.getEntries().get(0).getId());
			assertEquals("a vanilla atom entry", feed.getEntries().get(0).getTitle());
			assertEquals("bob@example.com", feed.getEntries().get(0).getAuthors().get(0).getEmail());
			
		}
		catch (AtomFormatException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
		
		log.leave();
	}
	

	
	public void testCreateFeed_String_qNames_2() {
		log.enter("testCreateFeed_String_qNames_2");
		
		// this example came from exist
		
		String feedDocument = 
			"<atom:feed xmlns:atom=\"http://www.w3.org/2005/Atom\">" +
				"<id xmlns=\"http://www.w3.org/2005/Atom\">urn:uuid:2343ad67-16ab-41ad-bd93-7b01db80a85c</id>" +
				"<updated xmlns=\"http://www.w3.org/2005/Atom\">2009-09-17T15:23:10+01:00</updated>" +
				"<title xmlns=\"http://www.w3.org/2005/Atom\">Example Collection</title>" +
				"<link xmlns=\"http://www.w3.org/2005/Atom\" href=\"#\" rel=\"edit\" type=\"application/atom+xml\"/>" +
				"<link xmlns=\"http://www.w3.org/2005/Atom\" href=\"#\" rel=\"self\" type=\"application/atom+xml\"/>" +
				"<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\">" +
					"<id xmlns=\"http://www.w3.org/2005/Atom\">urn:uuid:c5e4f0f0-8b0c-4162-8123-a74a3c729b3b</id>" +
					"<updated xmlns=\"http://www.w3.org/2005/Atom\">2009-09-17T15:23:10+01:00</updated>" +
					"<published xmlns=\"http://www.w3.org/2005/Atom\">2009-09-17T15:23:10+01:00</published>" +
					"<link xmlns=\"http://www.w3.org/2005/Atom\" href=\"?id=urn:uuid:c5e4f0f0-8b0c-4162-8123-a74a3c729b3b\" rel=\"edit\" type=\"application/atom+xml\"/>" +
					"<atom:title xmlns:atom=\"http://www.w3.org/2005/Atom\">a vanilla atom entry</atom:title>" +
					"<atom:summary xmlns:atom=\"http://www.w3.org/2005/Atom\">this is a vanilla atom entry, that's all</atom:summary>" +
					"<atom:author xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:name>bob</atom:name><atom:email>bob@example.com</atom:email></atom:author>" +
				"</atom:entry>" +
			"</atom:feed>";
		
		// spike
//		Document d = XMLParser.parse(feedDocument);
//		String prefix = d.getDocumentElement().getPrefix();
//		log.trace("prefix: "+prefix);
//		log.trace("tag name: "+d.getDocumentElement().getTagName());
//		log.trace("node name: "+d.getDocumentElement().getNodeName());	
//		log.trace("local name: "+XML.getLocalName(d.getDocumentElement()));
		
		try {
		
			VanillaAtomFeed feed = factory.createFeed(feedDocument);
			
			// check feed simple properties
			assertEquals("Example Collection", feed.getTitle());
			assertEquals("urn:uuid:2343ad67-16ab-41ad-bd93-7b01db80a85c", feed.getId());
			assertEquals("2009-09-17T15:23:10+01:00", feed.getUpdated());
			
			// check entries
			assertEquals(1, feed.getEntries().size());
			assertEquals("urn:uuid:c5e4f0f0-8b0c-4162-8123-a74a3c729b3b", feed.getEntries().get(0).getId());
			assertEquals("a vanilla atom entry", feed.getEntries().get(0).getTitle());
			assertEquals("bob@example.com", feed.getEntries().get(0).getAuthors().get(0).getEmail());
			
		}
		catch (AtomFormatException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
		
		log.leave();
	}
	
	@SuppressWarnings("unused")
	public void testCreateFeed_String_AtomFormatExceptions() {
		
		try {

			String xml = "<foo/>";
			VanillaAtomFeed feed = factory.createFeed(xml);
			fail("expected format exception (bad element name)");
			
		}
		catch (AtomFormatException e) {

			// expected
			
		}
		
		try {

			String xml = "<feed/>";
			VanillaAtomFeed feed = factory.createFeed(xml);
			fail("expected format exception (bad namespace URI)");
			
		}
		catch (AtomFormatException e) {

			// expected
			
		}
		
		try {
			
			String xml = "<feed>";
			VanillaAtomFeed feed = factory.createFeed(xml);
			fail("expected format exception (not well-formed XML)");

		}
		catch (AtomFormatException e) {
	
			// expected
			
		}
		
	}
	
	
	
	
}
