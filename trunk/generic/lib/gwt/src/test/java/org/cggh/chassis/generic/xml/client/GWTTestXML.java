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
	
	
	
	public void testWrongDocumentError_Chrome_workaround() {
		
		String fooxml = "<foo/>";
		String barxml = "<bar/>";
		Document foodoc = XMLParser.parse(fooxml);
		Document bardoc = XMLParser.parse(barxml);
		
		Element foo = foodoc.getDocumentElement();
		Element bar = bardoc.getDocumentElement();
		Element barclone = (Element) bar.cloneNode(true); // workaround by cloning the node
		
		foo.appendChild(barclone);
		
		NodeList bars = foodoc.getElementsByTagName("bar");
		assertEquals(1, bars.getLength());
		
	}

	
	

}
