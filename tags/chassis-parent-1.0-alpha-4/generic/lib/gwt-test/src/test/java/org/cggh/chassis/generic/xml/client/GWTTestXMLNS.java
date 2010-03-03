/**
 * 
 */
package org.cggh.chassis.generic.xml.client;

import java.util.List;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

/**
 * @author aliman
 *
 */
public class GWTTestXMLNS extends GWTTestCase {

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.xml.XML";
	}
	
	
	

	public void testCreateElementNS_1() {
		
		Element e;
		
		e = XMLNS.createElementNS("foo", null, null);
		assertEquals("foo", e.getTagName());
//		assertNull(e.getPrefix());
//		assertNull(e.getNamespaceURI());
		assertEquals("[null,null] expected prefix is empty string", "", XML.getPrefix(e));
		assertEquals("[null,null] expected namespace URI is empty string", "", XML.getNamespaceUri(e));

		e = XMLNS.createElementNS("foo", "", "");
		assertEquals("foo", e.getTagName());
		assertEquals("[es,es] expected prefix is empty string", "", XML.getPrefix(e));
		assertEquals("[es,es] expected namespace URI is empty string", "", XML.getNamespaceUri(e));

		e = XMLNS.createElementNS("foo", "", null);
		assertEquals("foo", e.getTagName());
		assertEquals("[es,null] expected prefix is empty string", "", XML.getPrefix(e));
		assertEquals("[es,null] expected namespace URI is empty string", "", XML.getNamespaceUri(e));

		e = XMLNS.createElementNS("foo", null, "");
		assertEquals("foo", e.getTagName());
		assertEquals("[null,es] expected prefix is empty string", "", XML.getPrefix(e));
		assertEquals("[null,es] expected namespace URI is empty string", "", XML.getNamespaceUri(e));

	}
	
	
	
	
	public void testCreateElementNS_2() {
		
		Element e;
		
		e = XMLNS.createElementNS("foo", null, "http://example.com/xmlns");
		assertEquals("foo", e.getTagName());
//		assertNull(e.getPrefix());
		assertEquals("", XML.getPrefix(e));
		assertEquals("http://example.com/xmlns", e.getNamespaceURI());
		
		e = XMLNS.createElementNS("foo", "x", "http://example.com/xmlns");
		assertEquals("x:foo", e.getTagName());
		assertEquals("x", e.getPrefix());
		assertEquals("http://example.com/xmlns", e.getNamespaceURI());
				
	}
	
	
	
	
	public void testGetElementsByTagNameNS_1() {
		
		// document with no namespace declarations
		String xml = 
			"<foo>" +
				"<bar>baz</bar>" +
			"</foo>";

		Element ancestor = XMLParser.parse(xml).getDocumentElement();
		
		// call method under test
		List<Element> elements = XMLNS.getElementsByTagNameNS(ancestor, "bar", null);
		
		// expect 1
		assertEquals(1, elements.size());
		
	}

	
	
	public void brokenTestGetElementsByTagNameNS_2() {
		
		// document with namespace declaration on root, no prefixes
		String xml = 
			"<foo xmlns=\"http://example.com/xmlns\">" +
				"<bar>baz</bar>" +
			"</foo>";

		Element ancestor = XMLParser.parse(xml).getDocumentElement();
		
		List<Element> elements;
		
		Element barElement = (Element) ancestor.getFirstChild();
		assertEquals("bar", barElement.getTagName());
		assertNull(barElement.getPrefix());
		assertEquals("http://example.com/xmlns", barElement.getNamespaceURI());
		
		NodeList bars = ancestor.getElementsByTagName("bar");
		assertEquals(1, bars.getLength()); // TODO this test fails here when run in HtmlUnit browser
		
		// try without namespace
		elements = XMLNS.getElementsByTagNameNS(ancestor, "bar", null);
		assertEquals(0, elements.size());
		
		// try with namespace
		elements = XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns");
		assertEquals(1, elements.size());  

		
	}



	public void brokenTestGetElementsByTagNameNS_3() {
		
		// document with namespace declaration on root, no prefixes
		String xml = 
			"<x:foo xmlns:x=\"http://example.com/xmlns\">" +
				"<x:bar>baz</x:bar>" +
			"</x:foo>";

		Element ancestor = XMLParser.parse(xml).getDocumentElement();
		
		List<Element> elements;

		// try with namespace, no prefix
		elements = XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns");
		assertEquals(1, elements.size()); // TODO this test fails here when run in HtmlUnit browser
		
		// try with namespace, and prefix
		elements = XMLNS.getElementsByTagNameNS(ancestor, "x:bar", "http://example.com/xmlns");
		assertEquals(0, elements.size());
		
		// try without namespace, no prefix
		elements = XMLNS.getElementsByTagNameNS(ancestor, "bar", null);
		assertEquals(0, elements.size());
			
		// try without namespace, no prefix
		elements = XMLNS.getElementsByTagNameNS(ancestor, "x:bar", null);
		assertEquals(0, elements.size());

	}

	
	
	public void brokenTestGetElementsByTagNameNS_4() {
		
		// document with namespace declaration on root, no prefixes
		String xml = 
			"<x:foo xmlns:x=\"http://example.com/xmlns\">" +
				"<bar>baz</bar>" +
			"</x:foo>";

		Element ancestor = XMLParser.parse(xml).getDocumentElement();
		
		List<Element> elements;

		// try with no namespace, no prefix
		elements = XMLNS.getElementsByTagNameNS(ancestor, "bar", null);
		assertEquals(1, elements.size());
		
		// try with namespace, no prefix
		elements = XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns");
		assertEquals(0, elements.size());
		
	}
	
	
	
	public void testGetElementsByTagNameNS_5() {
		
		// document with no namespace declarations
		String xml = 
			"<foo>" +
				"<bar>baz</bar>" +
				"<pad>" +
					"<bar>spong</bar>" +
				"</pad>" +
			"</foo>";

		Element ancestor = XMLParser.parse(xml).getDocumentElement();
		
		// call method under test
		List<Element> elements = XMLNS.getElementsByTagNameNS(ancestor, "bar", null);
		
		// expect 2
		assertEquals(2, elements.size());
		
	}

	
	
	public void brokenTestGetElementsByTagNameNS_6() {
		
		// document with namespace declarations
		String xml = 
			"<foo xmlns=\"http://example.com/xmlns\">" +
				"<bar>baz</bar>" +
				"<pad>" +
					"<bar>spong</bar>" +
				"</pad>" +
			"</foo>";

		Element ancestor = XMLParser.parse(xml).getDocumentElement();
		
		List<Element> elements;
		
		// call method under test
		elements = XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns");
		
		// expect 2
		assertEquals(2, elements.size()); // TODO this test fails here when run in HtmlUnit browser
		
		// call method under test
		elements = XMLNS.getElementsByTagNameNS(ancestor, "bar", null);
		
		// expect 0
		assertEquals(0, elements.size());
		
	}


	
	public void brokenTestGetElementsByTagNameNS_7() {
		
		// document with namespace declaration on root, prefixes
		String xml = 
			"<x:foo xmlns:x=\"http://example.com/xmlns\">" +
				"<x:bar>baz</x:bar>" +
				"<pad>" +
					"<x:bar>spong</x:bar>" +
				"</pad>" +
			"</x:foo>";

		Element ancestor = XMLParser.parse(xml).getDocumentElement();
		
		List<Element> elements;

		// try with namespace, no prefix
		elements = XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns");
		assertEquals(2, elements.size());// TODO this test fails here when run in HtmlUnit browser
		
		// try with namespace, and prefix
		elements = XMLNS.getElementsByTagNameNS(ancestor, "x:bar", "http://example.com/xmlns");
		assertEquals(0, elements.size());
		
		// try without namespace, no prefix
		elements = XMLNS.getElementsByTagNameNS(ancestor, "bar", null);
		assertEquals(0, elements.size());
			
		// try without namespace, no prefix
		elements = XMLNS.getElementsByTagNameNS(ancestor, "x:bar", null);
		assertEquals(0, elements.size());

	}

	
	
	public void testRemoveElementsByTagNameNS_1() {
		
		// document with no namespace declarations
		String xml = 
			"<foo>" +
				"<bar>baz</bar>" +
			"</foo>";

		Element ancestor = XMLParser.parse(xml).getDocumentElement();
		
		// initial state
		List<Element> elements = XMLNS.getElementsByTagNameNS(ancestor, "bar", null);
		assertEquals(1, elements.size());

		// call method under test
		XMLNS.removeElementsByTagNameNS(ancestor, "bar", null);
		
		// final state
		elements = XMLNS.getElementsByTagNameNS(ancestor, "bar", null);
		assertEquals(0, elements.size());

	}

	
	
	public void brokenTestRemoveElementsByTagNameNS_2() {
		
		// document with namespace declaration on root, no prefixes
		String xml = 
			"<foo xmlns=\"http://example.com/xmlns\">" +
				"<bar>baz</bar>" +
			"</foo>";

		Element ancestor = XMLParser.parse(xml).getDocumentElement();
		
		// initial state
		// TODO this test fails here when run in HtmlUnit browser
		assertEquals(1, XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());
		
		// call method under test (expect no effect)
		XMLNS.removeElementsByTagNameNS(ancestor, "bar", null);

		// test new state (expect no change)
		assertEquals(1, XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());

		// call method under test (expect effect)
		XMLNS.removeElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns");

		// test new state (expect change)
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());
			
	}


	
	public void brokenTestRemoveElementsByTagNameNS_3() {
		
		// document with namespace declaration on root, prefixes
		String xml = 
			"<x:foo xmlns:x=\"http://example.com/xmlns\">" +
				"<x:bar>baz</x:bar>" +
			"</x:foo>";

		Element ancestor = XMLParser.parse(xml).getDocumentElement();
		
		// initial state
		// TODO this test fails here when run in HtmlUnit browser
		assertEquals(1, XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "x:bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "x:bar", null).size());
		
		// call method under test (expect no effect)
		XMLNS.removeElementsByTagNameNS(ancestor, "bar", null);

		// new state (expect no change)
		assertEquals(1, XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "x:bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "x:bar", null).size());

		// call method under test (expect no effect)
		XMLNS.removeElementsByTagNameNS(ancestor, "x:bar", null);

		// new state (expect no change)
		assertEquals(1, XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "x:bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "x:bar", null).size());

		// call method under test (expect no effect)
		XMLNS.removeElementsByTagNameNS(ancestor, "x:bar", "http://example.com/xmlns");

		// new state (expect no change)
		assertEquals(1, XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "x:bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "x:bar", null).size());

		// call method under test (expect effect)
		XMLNS.removeElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns");

		// new state (expect change)
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "x:bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "x:bar", null).size());

	}



	public void brokenTestRemoveElementsByTagNameNS_5() {
		
		// document with no namespace declarations
		String xml = 
			"<foo>" +
				"<bar>baz</bar>" +
				"<pad>" +
					"<bar>spong</bar>" +
				"</pad>" +
			"</foo>";

		Element ancestor = XMLParser.parse(xml).getDocumentElement();
		
		// initial state
		assertEquals(2, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());
		
		// call method under test (expect no effect)
		XMLNS.removeElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns");

		// new state (expect no change)
		assertEquals(2, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());

		// call method under test (expect no effect)
		// TODO this test fails here when run in HtmlUnit browser
		XMLNS.removeElementsByTagNameNS(ancestor, "x:bar", null);

		// new state (expect no change)
		assertEquals(2, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());

		// call method under test (expect no effect)
		XMLNS.removeElementsByTagNameNS(ancestor, "x:bar", "http://example.com/xmlns");

		// new state (expect no change)
		assertEquals(2, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());

		// call method under test
		XMLNS.removeElementsByTagNameNS(ancestor, "bar", null);

		// final state
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());

	}

	
	
	public void brokenTestRemoveElementsByTagNameNS_6() {
		
		// document with namespace declarations
		String xml = 
			"<foo xmlns=\"http://example.com/xmlns\">" +
				"<bar>baz</bar>" +
				"<pad>" +
					"<bar>spong</bar>" +
				"</pad>" +
			"</foo>";

		Element ancestor = XMLParser.parse(xml).getDocumentElement();
		
		// TODO this test fails here when run in HtmlUnit browser
		assertEquals(2, XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());
		
		XMLNS.removeElementsByTagNameNS(ancestor, "bar", null);

		assertEquals(2, XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());

		XMLNS.removeElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns");

		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());

	}

	

	public void brokenTestRemoveElementsByTagNameNS_7() {
		
		// document with namespace declaration on root, prefixes
		String xml = 
			"<x:foo xmlns:x=\"http://example.com/xmlns\">" +
				"<x:bar>baz</x:bar>" +
				"<pad>" +
					"<x:bar>spong</x:bar>" +
				"</pad>" +
			"</x:foo>";

		Element ancestor = XMLParser.parse(xml).getDocumentElement();
		
		// TODO this test fails here when run in HtmlUnit browser
		assertEquals(2, XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "x:bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "x:bar", null).size());
		
		XMLNS.removeElementsByTagNameNS(ancestor, "bar", null);

		assertEquals(2, XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "x:bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "x:bar", null).size());
		
		XMLNS.removeElementsByTagNameNS(ancestor, "x:bar", null);

		assertEquals(2, XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "x:bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "x:bar", null).size());
		
		XMLNS.removeElementsByTagNameNS(ancestor, "x:bar", "http://example.com/xmlns");

		assertEquals(2, XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "x:bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "x:bar", null).size());
		
		XMLNS.removeElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns");

		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "x:bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "x:bar", null).size());
		

	}

	
	
	
	public void testGetElementsByTagNameNS() {
		
		String ns1 = "http://example.org/ns1";
		String ns2 = "http://example.org/ns2";
		String ns3 = "http://example.org/ns3";
		
		String xml = 
			"<foo xmlns='"+ns1+"'>" +
			"  <bar>baz</bar>" +
			"  <bar xmlns='"+ns2+"'>quux</bar>" +
			"  <x:bar xmlns:x='"+ns3+"'>spong</x:bar>" +
			"  <padding><bar>acorns</bar></padding>" +
			"  <padding><bar xmlns='"+ns2+"'>peppers</bar></padding>" +
			"  <padding><x:bar xmlns:x='"+ns3+"'>corn</x:bar></padding>" +
			"</foo>";
		
		Document doc = XMLParser.parse(xml);
		
		// test vanilla method

		NodeList bars = doc.getElementsByTagName("bar");
		
		assertEquals(6, bars.getLength());
		
		// test ns-aware method
		
		List<Element> barList1 = XMLNS.getElementsByTagNameNS(doc, "bar", ns1);
		
		assertEquals(2, barList1.size());
		assertEquals("baz", barList1.get(0).getFirstChild().getNodeValue());
		assertEquals("acorns", barList1.get(1).getFirstChild().getNodeValue());
		
		List<Element> barList2 = XMLNS.getElementsByTagNameNS(doc, "bar", ns2);
		
		assertEquals(2, barList2.size());
		assertEquals("quux", barList2.get(0).getFirstChild().getNodeValue());
		assertEquals("peppers", barList2.get(1).getFirstChild().getNodeValue());
		
		List<Element> barList3 = XMLNS.getElementsByTagNameNS(doc, "bar", ns3);
		
		assertEquals(2, barList3.size());
		assertEquals("spong", barList3.get(0).getFirstChild().getNodeValue());
		assertEquals("corn", barList3.get(1).getFirstChild().getNodeValue());
		
	}
	
	
	public void brokenTestGetFirstElementByTagNameNS_study() {
		
		String xml = 
			"<entry xmlns=\"http://www.w3.org/2005/Atom\">\n" +
			"  <content type=\"application/xml\">\n" +
			"    <study xmlns=\"http://www.cggh.org/chassis/atom/xmlns\"></study>\n" +
			"  </content>\n" +
			"</entry>";
		
		Document doc = XMLParser.parse(xml);

		Element entryElement = XMLNS.getFirstElementByTagNameNS(doc, "entry", "http://www.w3.org/2005/Atom");
		assertNotNull(entryElement);
		
		Element studyElement = XMLNS.getFirstElementByTagNameNS(doc, "study", "http://www.cggh.org/chassis/atom/xmlns");
		assertNotNull(studyElement);

		studyElement = XMLNS.getFirstElementByTagNameNS(entryElement, "study", "http://www.cggh.org/chassis/atom/xmlns");
		// TODO this test fails here when run in HtmlUnit browser
		assertNotNull(studyElement);

	}
	
	public void testGetFirstElementSimpleContentByTagNameNS() {

		String ns1 = "http://example.org/ns1";
		String ns2 = "http://example.org/ns2";
		
		String xml = 
			"<foo xmlns='"+ns1+"'>" +
			"  <bar>baz</bar>" +
			"  <bar xmlns='"+ns2+"'>quux</bar>" +
			"  <bar xmlns='"+ns2+"'>spong</bar>" +
			"</foo>";
		
		Document doc = XMLParser.parse(xml);
		
		String content = XMLNS.getFirstElementSimpleContentByTagNameNS(doc, "bar", ns2);
		
		assertEquals("quux", content);

	}
	
	
	
	public void testCreateElementNS() {
		
		Element foo = XMLNS.createElementNS("foo", null, null);
		Element bar = XMLNS.createElementNS("bar", null, null);
		foo.appendChild(bar);
		
		assertEquals(1, foo.getElementsByTagName("bar").getLength());
		
	}
	

	
}
