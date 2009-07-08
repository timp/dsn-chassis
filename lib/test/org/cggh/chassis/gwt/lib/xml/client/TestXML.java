/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.xml.client;

import java.util.List;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class TestXML extends GWTTestCase {

	/**
	 * 
	 */
	public TestXML() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.gwt.lib.xml.XML";
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
			"</foo>";
		
		Document doc = XMLParser.parse(xml);
		
		// test vanilla method

		NodeList bars = doc.getElementsByTagName("bar");
		
		assertEquals(3, bars.getLength());
		
		// test ns-aware method
		
		List<Element> barList1 = XML.getElementsByTagNameNS(doc, ns1, "bar");
		
		assertEquals(1, barList1.size());
		assertEquals("baz", barList1.get(0).getFirstChild().getNodeValue());
		
		List<Element> barList2 = XML.getElementsByTagNameNS(doc, ns2, "bar");
		
		assertEquals(1, barList2.size());
		assertEquals("quux", barList2.get(0).getFirstChild().getNodeValue());
		
		List<Element> barList3 = XML.getElementsByTagNameNS(doc, ns3, "bar");
		
		assertEquals(1, barList3.size());
		assertEquals("spong", barList3.get(0).getFirstChild().getNodeValue());
		
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
		
		String content = XML.getSimpleContentByTagNameNS(doc, ns2, "bar");
		
		assertEquals("quux", content);

	}

}
