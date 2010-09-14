/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.xml.client.Element;


/**
 * @author aliman
 *
 */
public class AtomUtils {

	
	
	
	public static void verifyElement(String localName, String namespaceUri, Element e) {
		
		if (!XML.getLocalName(e).equals(localName)) {
			throw new AtomFormatException("bad tag local name, expected: "+localName+"; found: "+XML.getLocalName(e));
		}

		if (e.getNamespaceURI() == null || !e.getNamespaceURI().equals(namespaceUri)) {
			throw new AtomFormatException("bad namespace URI, expected: "+namespaceUri+"; found: "+e.getNamespaceURI());
		}

	}
	
	
	
}
