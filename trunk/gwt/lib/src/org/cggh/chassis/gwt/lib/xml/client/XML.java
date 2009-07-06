/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.xml.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class XML {

	/**
	 * Get elements by tag name and namespace URI.
	 * 
	 * @param doc
	 * @param ns
	 * @param name
	 * @return
	 */
	public static List<Element> getElementsByTagNameNS(Document doc, String ns, String name) {
		ArrayList<Element> filtered = new ArrayList<Element>();
		NodeList initial = doc.getElementsByTagName(name);
		for (int i=0; i<initial.getLength(); i++) {
			Node n = initial.item(i);
			if (n.getNamespaceURI().equals(ns)) {
				filtered.add((Element)n);
			}
		}
		return filtered;
	}
	
	/**
	 * Get first element in document order by tag name and namespace URI.
	 *
	 * @param doc
	 * @param ns
	 * @param name
	 * @return an element, or null if none found matching query
	 */
	public static Element getFirstElementByTagNameNS(Document doc, String ns, String name) {
		List<Element> elements = XML.getElementsByTagNameNS(doc, ns, name);
		if (elements.size() > 0) {
			return elements.get(0);
		}
		else {
			return null;
		}
	}
	
	public static String getFirstElementSimpleContentByTagNameNS(Document doc, String ns, String name) {
		Element element = XML.getFirstElementByTagNameNS(doc, ns, name);
		if (element != null) {
//			return element.getFirstChild().getNodeValue();
			return element.toString();
		}
		else {
			return null;
		}
	}

	/**
	 * Remove all child nodes from the given parent.
	 * 
	 * @param parent
	 */
	public static void clear(Element parent) {
		NodeList children = parent.getChildNodes();
		for (int i=0; i<children.getLength(); i++) {
			Node child = children.item(i);
			parent.removeChild(child);
		}
	}

}
