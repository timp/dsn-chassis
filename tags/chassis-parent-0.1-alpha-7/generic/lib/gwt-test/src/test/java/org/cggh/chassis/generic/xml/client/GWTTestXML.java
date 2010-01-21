/**
 * $Id$
 */
package org.cggh.chassis.generic.xml.client;


import java.util.List;
import java.util.Set;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.log.client.SystemOutLog;

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

	static {
		LogFactory.create = SystemOutLog.create;
	}
	
	static Log log = LogFactory.getLog(GWTTestXML.class);
	
	
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
	
	
	
	
	public void testGetElementsBySimplePath_1() {
		log.enter("testGetElementsBySimplePath_1");
		
		String xml = 
			"<foo>" +
				"<bar>" +
					"<quux/>" +
					"<spong/>" +
				"</bar>" +
				"<baz>" +
					"<quux/>" +
					"<quux/>" +
				"</baz>" +
				"<baz/>" +
			"</foo>";

		Document d = XMLParser.parse(xml);
		Element e = d.getDocumentElement();

		Set<Element> results; 
		
		results = XML.getElementsBySimplePath(d, "foo");
		assertEquals(1, results.size());
		
		results = XML.getElementsBySimplePath(d, "bar");
		assertEquals(0, results.size());

		results = XML.getElementsBySimplePath(d, "/foo");
		assertEquals(1, results.size());
		
		results = XML.getElementsBySimplePath(d, "/bar");
		assertEquals(0, results.size());

		results = XML.getElementsBySimplePath(e, "/foo");
		assertEquals(1, results.size());
		
		results = XML.getElementsBySimplePath(e, "/bar");
		assertEquals(0, results.size());

		results = XML.getElementsBySimplePath(e, "bar");
		assertEquals(1, results.size());

		results = XML.getElementsBySimplePath(e, "baz");
		assertEquals(2, results.size());

		results = XML.getElementsBySimplePath(e, "*");
		assertEquals(3, results.size());

		results = XML.getElementsBySimplePath(e, "quux");
		assertEquals(0, results.size());

		results = XML.getElementsBySimplePath(e, "bar/quux");
		assertEquals(1, results.size());

		results = XML.getElementsBySimplePath(e, "baz/quux");
		assertEquals(2, results.size());

		results = XML.getElementsBySimplePath(e, "*/quux");
		assertEquals(3, results.size());

		results = XML.getElementsBySimplePath(e, "bar/*");
		assertEquals(2, results.size());

		results = XML.getElementsBySimplePath(e, "*/*");
		assertEquals(4, results.size());

		results = XML.getElementsBySimplePath(e, "bar/../baz");
		assertEquals(2, results.size());

		results = XML.getElementsBySimplePath(e, "*/*/..");
		assertEquals(2, results.size());

		results = XML.getElementsBySimplePath(e, "*/*/../quux");
		assertEquals(3, results.size());

		Element f = XML.getChildrenByTagName(e, "bar").get(0);
		
		results = XML.getElementsBySimplePath(f, "/foo");
		assertEquals(1, results.size());
		
		results = XML.getElementsBySimplePath(f, "/bar");
		assertEquals(0, results.size());

		log.leave();
	}
	
	
}
