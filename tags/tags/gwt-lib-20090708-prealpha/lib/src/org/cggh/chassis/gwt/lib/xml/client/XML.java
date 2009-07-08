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
	 * 
	 * TODO document me
	 * 
	 * @param doc
	 * @param ns
	 * @param name
	 * @return
	 */
	public static List<String> getSimpleContentsByTagNameNS(Document doc, String ns, String name) {
		List<Element> elements = XML.getElementsByTagNameNS(doc, ns, name);
		List<String> contents = new ArrayList<String>();
		for (Element e : elements) {
			contents.add(e.getFirstChild().getNodeValue());
		}
		return contents;
	}

	
	
	/**
	 * Get first element in document order by tag name and namespace URI.
	 *
	 * @param doc
	 * @param ns
	 * @param name
	 * @return an element, or null if none found matching query
	 */
	public static Element getElementByTagNameNS(Document doc, String ns, String name) {
		List<Element> elements = XML.getElementsByTagNameNS(doc, ns, name);
		if (elements.size() > 0) {
			return elements.get(0);
		}
		else {
			return null;
		}
	}
	
	public static String getSimpleContentByTagNameNS(Document doc, String ns, String name) {
		Element element = XML.getElementByTagNameNS(doc, ns, name);
		if (element != null) {
			return element.getFirstChild().getNodeValue();
//			return element.toString();
		}
		else {
			return null;
		}
	}

	public static String getSimpleContentByTagNameNS(Element ancestor, String ns, String name) {
		Element element = XML.getElementByTagNameNS(ancestor, ns, name);
		if (element != null && element.hasChildNodes()) {
			return element.getFirstChild().getNodeValue();
		}
		else {
			return null;
		}
	}

	/**
	 * TODO document me
	 * 
	 * @param ancestor
	 * @param ns
	 * @param name
	 * @return
	 */
	public static Element getElementByTagNameNS(Element ancestor, String ns, String name) {
		List<Element> elements = XML.getElementsByTagNameNS(ancestor, ns, name);
		if (elements.size() > 0) {
			return elements.get(0);
		}
		else {
			return null;
		}
	}

	/**
	 * TODO document me
	 * 
	 * @param ancestor
	 * @param ns
	 * @param name
	 * @return
	 */
	public static List<Element> getElementsByTagNameNS(Element ancestor, String ns, String name) {
		ArrayList<Element> filtered = new ArrayList<Element>();
		NodeList initial = ancestor.getElementsByTagName(name);
		for (int i=0; i<initial.getLength(); i++) {
			Node n = initial.item(i);
			if (n.getNamespaceURI().equals(ns)) {
				filtered.add((Element)n);
			}
		}
		return filtered;
	}

	/**
	 * Remove all child nodes from the given parent.
	 * 
	 * @param parent
	 */
	public static void removeAllChildren(Element parent) {
		NodeList children = parent.getChildNodes();
		for (int i=0; i<children.getLength(); i++) {
			Node child = children.item(i);
			parent.removeChild(child);
		}
	}



	/**
	 * TODO document me
	 * 
	 * @param ancestor
	 * @param ns
	 * @param name
	 * @return
	 */
	public static List<String> getSimpleContentsByTagNameNS(Element ancestor, String ns, String name) {
		List<Element> elements = XML.getElementsByTagNameNS(ancestor, ns, name);
		List<String> contents = new ArrayList<String>();
		for (Element e : elements) {
			contents.add(e.getFirstChild().getNodeValue());
		}
		return contents;
	}
	
	
	
	public static void removeElementsByTagNameNS(Element ancestor, String ns, String name) {
		List<Element> elements = XML.getElementsByTagNameNS(ancestor, ns, name);
		for (Element e : elements) {
			Element parent = (Element) e.getParentNode();
			parent.removeChild(e);
		}
	}
	
	
	
	public static void setSimpleContent(Element element, String content) {
		XML.removeAllChildren(element);
		element.appendChild(element.getOwnerDocument().createTextNode(content));
	}
	
	
	
	public static Element createElement(Element parent, String tagName) {
		Element child = parent.getOwnerDocument().createElement(tagName);
		parent.appendChild(child);
		return child;
	}

}
