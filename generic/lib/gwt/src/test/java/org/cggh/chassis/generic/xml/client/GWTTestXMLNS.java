/**
 * 
 */
package org.cggh.chassis.generic.xml.client;

import java.util.List;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.Element;
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
		assertNull(e.getPrefix());
		assertNull(e.getNamespaceURI());
		
		e = XMLNS.createElementNS("foo", null, "http://example.com/xmlns");
		assertEquals("foo", e.getTagName());
		assertNull(e.getPrefix());
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

	
	
	public void testGetElementsByTagNameNS_2() {
		
		// document with namespace declaration on root, no prefixes
		String xml = 
			"<foo xmlns=\"http://example.com/xmlns\">" +
				"<bar>baz</bar>" +
			"</foo>";

		Element ancestor = XMLParser.parse(xml).getDocumentElement();
		
		List<Element> elements;
		
		// try with namespace
		elements = XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns");
		assertEquals(1, elements.size());
		
		// try without namespace
		elements = XMLNS.getElementsByTagNameNS(ancestor, "bar", null);
		assertEquals(0, elements.size());
			
	}



	public void testGetElementsByTagNameNS_3() {
		
		// document with namespace declaration on root, no prefixes
		String xml = 
			"<x:foo xmlns:x=\"http://example.com/xmlns\">" +
				"<x:bar>baz</x:bar>" +
			"</x:foo>";

		Element ancestor = XMLParser.parse(xml).getDocumentElement();
		
		List<Element> elements;

		// try with namespace, no prefix
		elements = XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns");
		assertEquals(1, elements.size());
		
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

	
	
	public void testGetElementsByTagNameNS_4() {
		
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

	
	
	public void testGetElementsByTagNameNS_6() {
		
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
		assertEquals(2, elements.size());
		
		// call method under test
		elements = XMLNS.getElementsByTagNameNS(ancestor, "bar", null);
		
		// expect 0
		assertEquals(0, elements.size());
		
	}


	
	public void testGetElementsByTagNameNS_7() {
		
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
		assertEquals(2, elements.size());
		
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

	
	
	public void testRemoveElementsByTagNameNS_2() {
		
		// document with namespace declaration on root, no prefixes
		String xml = 
			"<foo xmlns=\"http://example.com/xmlns\">" +
				"<bar>baz</bar>" +
			"</foo>";

		Element ancestor = XMLParser.parse(xml).getDocumentElement();
		
		// initial state
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


	
	public void testRemoveElementsByTagNameNS_3() {
		
		// document with namespace declaration on root, prefixes
		String xml = 
			"<x:foo xmlns:x=\"http://example.com/xmlns\">" +
				"<x:bar>baz</x:bar>" +
			"</x:foo>";

		Element ancestor = XMLParser.parse(xml).getDocumentElement();
		
		// initial state
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



	public void testRemoveElementsByTagNameNS_5() {
		
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

	
	
	public void testRemoveElementsByTagNameNS_6() {
		
		// document with namespace declarations
		String xml = 
			"<foo xmlns=\"http://example.com/xmlns\">" +
				"<bar>baz</bar>" +
				"<pad>" +
					"<bar>spong</bar>" +
				"</pad>" +
			"</foo>";

		Element ancestor = XMLParser.parse(xml).getDocumentElement();
		
		assertEquals(2, XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());
		
		XMLNS.removeElementsByTagNameNS(ancestor, "bar", null);

		assertEquals(2, XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());

		XMLNS.removeElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns");

		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", "http://example.com/xmlns").size());
		assertEquals(0, XMLNS.getElementsByTagNameNS(ancestor, "bar", null).size());

	}

	

	public void testRemoveElementsByTagNameNS_7() {
		
		// document with namespace declaration on root, prefixes
		String xml = 
			"<x:foo xmlns:x=\"http://example.com/xmlns\">" +
				"<x:bar>baz</x:bar>" +
				"<pad>" +
					"<x:bar>spong</x:bar>" +
				"</pad>" +
			"</x:foo>";

		Element ancestor = XMLParser.parse(xml).getDocumentElement();
		
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

	

	
}
