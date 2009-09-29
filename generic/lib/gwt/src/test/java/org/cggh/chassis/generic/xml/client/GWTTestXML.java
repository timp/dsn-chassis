/**
 * $Id$
 */
package org.cggh.chassis.generic.xml.client;


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
public class GWTTestXML extends GWTTestCase {

	/**
	 * 
	 */
	public GWTTestXML() {
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.xml.XML";
	}
	

	// this test exposes chrome wrong document error
//	public void testWrongDocumentError_Chrome() {
//		
//		String fooxml = "<foo/>";
//		String barxml = "<bar/>";
//		Document foodoc = XMLParser.parse(fooxml);
//		Document bardoc = XMLParser.parse(barxml);
//		Element foo = foodoc.getDocumentElement();
//		Element bar = bardoc.getDocumentElement();
//
//		foo.appendChild(bar);
//		
//		NodeList bars = foodoc.getElementsByTagName("bar");
//		assertEquals(1, bars.getLength());
//		
//	}
	
	
	
	public void testBrowsersAllowCloningElementsFromOneDocToAnother() {
		
		String fooxml = "<foo/>";
		String barxml = "<bar/>";
		Document foodoc = XMLParser.parse(fooxml);
		Document bardoc = XMLParser.parse(barxml);
		
		Element foo = foodoc.getDocumentElement();
		Element bar = bardoc.getDocumentElement();
		Element barclone = (Element) bar.cloneNode(true); // workaround Chrome by cloning the node
		
		foo.appendChild(barclone);
		
		NodeList bars = foodoc.getElementsByTagName("bar");
		assertEquals(1, bars.getLength());
		
	}

	
	
	public void testBrowsersHandleNoNamespaceOrPrefix() {
		
		String xml = "<foo/>";
		Document d = XMLParser.parse(xml);
		Element e = d.getDocumentElement();
		
		String namespaceUri = e.getNamespaceURI(); // null in FF3, Chrome, "" in IE7
		boolean hasNoNamespaceUri = (namespaceUri == null) || ("".equals(namespaceUri));
		assertTrue("expected hasNoNamespaceUri is true", hasNoNamespaceUri);

		String prefix = e.getPrefix(); // null in FF3, Chrome, "" in IE7
		boolean hasNoPrefix = (prefix == null) || ("".equals(prefix));
		assertTrue("expected hasNoPrefix is true", hasNoPrefix);
		
		assertEquals("expected XML.getPrefix is empty string", "", XML.getPrefix(e)); // should work across browsers
		assertEquals("expected XML.getNamespaceUri is empty string", "", XML.getNamespaceUri(e)); // should work across browsers
		
	}
	
	
	
	
}
