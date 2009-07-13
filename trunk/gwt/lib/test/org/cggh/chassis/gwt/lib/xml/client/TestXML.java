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
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.gwt.lib.xml.XML";
	}
	
	public void test_getElementsByTagNameNS() {
		
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
		
		List<Element> barList1 = XML.getElementsByTagNameNS(doc, ns1, "bar");
		
		assertEquals(2, barList1.size());
		assertEquals("baz", barList1.get(0).getFirstChild().getNodeValue());
		assertEquals("acorns", barList1.get(1).getFirstChild().getNodeValue());
		
		List<Element> barList2 = XML.getElementsByTagNameNS(doc, ns2, "bar");
		
		assertEquals(2, barList2.size());
		assertEquals("quux", barList2.get(0).getFirstChild().getNodeValue());
		assertEquals("peppers", barList2.get(1).getFirstChild().getNodeValue());
		
		List<Element> barList3 = XML.getElementsByTagNameNS(doc, ns3, "bar");
		
		assertEquals(2, barList3.size());
		assertEquals("spong", barList3.get(0).getFirstChild().getNodeValue());
		assertEquals("corn", barList3.get(1).getFirstChild().getNodeValue());
		
	}
	
	
	public void test_getElementByTagNameNS_study() {
		
		String xml = 
			"<entry xmlns=\"http://www.w3.org/2005/Atom\">\n" +
			"  <content type=\"application/xml\">\n" +
			"    <study xmlns=\"http://www.cggh.org/chassis/atom/xmlns\"></study>\n" +
			"  </content>\n" +
			"</entry>";
		
		Document doc = XMLParser.parse(xml);

		Element entryElement = XML.getElementByTagNameNS(doc, "http://www.w3.org/2005/Atom", "entry");
		assertNotNull(entryElement);
		
		Element studyElement = XML.getElementByTagNameNS(doc, "http://www.cggh.org/chassis/atom/xmlns", "study");
		assertNotNull(studyElement);

		studyElement = XML.getElementByTagNameNS(entryElement, "http://www.cggh.org/chassis/atom/xmlns", "study");
		assertNotNull(studyElement);

	}
	
	public void test_getSimpleContentByTagNameNS() {

		String ns1 = "http://example.org/ns1";
		String ns2 = "http://example.org/ns2";
		
		String xml = 
			"<foo xmlns='"+ns1+"'>" +
			"  <bar>baz</bar>" +
			"  <bar xmlns='"+ns2+"'>quux</bar>" +
			"  <bar xmlns='"+ns2+"'>spong</bar>" +
			"</foo>";
		
		Document doc = XMLParser.parse(xml);
		
		String content = XML.getElementSimpleContentByTagNameNS(doc, ns2, "bar");
		
		assertEquals("quux", content);

	}
	

}
