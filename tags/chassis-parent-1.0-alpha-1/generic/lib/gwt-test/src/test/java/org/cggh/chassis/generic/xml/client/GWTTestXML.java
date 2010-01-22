/**
 * $Id$
 */
package org.cggh.chassis.generic.xml.client;


import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
	
	
	
	
//	public void testBrowsersAllowElementsInHashSets() {
//		
//		String xml = "<foo/>";
//		Document d = XMLParser.parse(xml);
//		Element e = d.getDocumentElement();
//		
//		Set<Element> s = new HashSet<Element>();
//		s.add(e); // ie7 throws exception here
//		
//	}
	
	
	
	public void testBrowsersAllowElementsInTreeSets_1() {
		
		String xml = "<foo/>";
		Document d = XMLParser.parse(xml);
		Element e = d.getDocumentElement();
		
		Set<Element> t = new TreeSet<Element>();
		t.add(e);
		
	}
	
	
	
//	public void testBrowsersAllowElementsInTreeSets_2() {
//		
//		String xml = "<foo><bar/><bar/></foo>";
//		Document d = XMLParser.parse(xml);
//		List<Element> l = XML.getElementsByTagName(d, "bar");
//		
//		Set<Element> t = new TreeSet<Element>(l); // throws in ff3 and ie7
//		
//	}
	
	
	
	public void testBrowsersAllowElementsInTreeSets_3() {
		
		String xml = "<foo><bar/><bar/></foo>";
		Document d = XMLParser.parse(xml);
		List<Element> l = XML.getElementsByTagName(d, "bar");
		
		Comparator<Element> c = new Comparator<Element>() {
			public int compare(Element arg0, Element arg1) {
				return 0;
			}
		};
		
		Set<Element> t = new TreeSet<Element>(c); 
		t.addAll(l);
		
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
		log.debug(Integer.toString(results.size()));
		assertEquals(1, results.size());
		
		results = XML.getElementsBySimplePath(d, "bar");
		log.debug(Integer.toString(results.size()));
		assertEquals(0, results.size());

		results = XML.getElementsBySimplePath(d, "/foo");
		log.debug(Integer.toString(results.size()));
		assertEquals(1, results.size());
		
		results = XML.getElementsBySimplePath(d, "/bar");
		log.debug(Integer.toString(results.size()));
		assertEquals(0, results.size());

		results = XML.getElementsBySimplePath(e, "/foo");
		log.debug(Integer.toString(results.size()));
		assertEquals(1, results.size());
		
		results = XML.getElementsBySimplePath(e, "/bar");
		log.debug(Integer.toString(results.size()));
		assertEquals(0, results.size());

		results = XML.getElementsBySimplePath(e, "bar");
		log.debug(Integer.toString(results.size()));
		assertEquals(1, results.size());

		results = XML.getElementsBySimplePath(e, "baz");
		log.debug(Integer.toString(results.size()));
		assertEquals(2, results.size());

		results = XML.getElementsBySimplePath(e, "*");
		log.debug(Integer.toString(results.size()));
		assertEquals(3, results.size());

		results = XML.getElementsBySimplePath(e, "quux");
		log.debug(Integer.toString(results.size()));
		assertEquals(0, results.size());

		results = XML.getElementsBySimplePath(e, "bar/quux");
		log.debug(Integer.toString(results.size()));
		assertEquals(1, results.size());

		results = XML.getElementsBySimplePath(e, "baz/quux");
		log.debug(Integer.toString(results.size()));
		assertEquals(2, results.size());

		results = XML.getElementsBySimplePath(e, "*/quux");
		log.debug(Integer.toString(results.size()));
		assertEquals(3, results.size());

		results = XML.getElementsBySimplePath(e, "bar/*");
		log.debug(Integer.toString(results.size()));
		assertEquals(2, results.size());

		results = XML.getElementsBySimplePath(e, "*/*");
		log.debug(Integer.toString(results.size()));
		assertEquals(4, results.size());

		results = XML.getElementsBySimplePath(e, "bar/../baz");
		log.debug(Integer.toString(results.size()));
		assertEquals(2, results.size());

		results = XML.getElementsBySimplePath(e, "*/*/..");
		log.debug(Integer.toString(results.size()));
		assertEquals(2, results.size());

		results = XML.getElementsBySimplePath(e, "*/*/../quux");
		log.debug(Integer.toString(results.size()));
		assertEquals(3, results.size());

		Element f = XML.getChildrenByTagName(e, "bar").get(0);
		
		results = XML.getElementsBySimplePath(f, "/foo");
		log.debug(Integer.toString(results.size()));
		assertEquals(1, results.size());
		
		results = XML.getElementsBySimplePath(f, "/bar");
		log.debug(Integer.toString(results.size()));
		assertEquals(0, results.size());

		log.leave();
	}
	
	
}
