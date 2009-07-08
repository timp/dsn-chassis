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
	 * Get elements by tag name.
	 * 
	 * @param doc
	 * @param name
	 * @return
	 */
	public static List<Element> getElementsByTagName(Document doc, String name) {
		NodeList nodes = doc.getElementsByTagName(name);
		return XML.elements(nodes);
	}
	
	
	
	/**
	 * Get elements by tag name.
	 * 
	 * @param ancestor
	 * @param name
	 * @return
	 */
	public static List<Element> getElementsByTagName(Element ancestor, String name) {
		ArrayList<Element> elements = new ArrayList<Element>();
		NodeList nodes = ancestor.getElementsByTagName(name);
		for (int i=0; i<nodes.getLength(); i++) {
			Element element = (Element) nodes.item(i);
			elements.add(element);
		}
		return elements;
	}

	
	
	/**
	 * Get elements by tag name and namespace URI.
	 * 
	 * @param doc
	 * @param ns
	 * @param name
	 * @return
	 */
	public static List<Element> getElementsByTagNameNS(Document doc, String ns, String name) {
		List<Element> elements = XML.getElementsByTagName(doc, name);
		return XML.filterByNamespaceURI(elements, ns);
	}
	
	
	
	/**
	 * Get elements by tag name and namespace URI.
	 * 
	 * @param ancestor
	 * @param ns
	 * @param name
	 * @return
	 */
	public static List<Element> getElementsByTagNameNS(Element ancestor, String ns, String name) {
		List<Element> elements = XML.getElementsByTagName(ancestor, name);
		return XML.filterByNamespaceURI(elements, ns);
	}



	/**
	 * Get first element in document order by tag name.
	 * 
	 * @param ancestor
	 * @param name
	 * @return
	 */
	private static Element getElementByTagName(Document doc, String name) {
		List<Element> elements = XML.getElementsByTagName(doc, name);
		return XML.firstOrNullIfEmpty(elements);
	}



	/**
	 * Get first element in document order by tag name.
	 * 
	 * @param ancestor
	 * @param name
	 * @return
	 */
	public static Element getElementByTagName(Element ancestor, String name) {
		List<Element> elements = XML.getElementsByTagName(ancestor, name);
		return XML.firstOrNullIfEmpty(elements);
	}



	/**
	 * Get first element in document order by tag name and namespace URI.
	 *
	 * @param doc document to search within
	 * @param ns
	 * @param name
	 * @return an element, or null if none found matching query
	 */
	public static Element getElementByTagNameNS(Document doc, String ns, String name) {
		List<Element> elements = XML.getElementsByTagNameNS(doc, ns, name);
		return XML.firstOrNullIfEmpty(elements);
	}

	
	
	/**
	 * Get first element in document order by tag name and namespace URI.
	 * 
	 * @param ancestor element to search within
	 * @param ns
	 * @param name
	 * @return an element, or null if none found matching query
	 */
	public static Element getElementByTagNameNS(Element ancestor, String ns, String name) {
		List<Element> elements = XML.getElementsByTagNameNS(ancestor, ns, name);
		return XML.firstOrNullIfEmpty(elements);
	}


	
	/**
	 * TODO document me
	 * 
	 * @param entryElement
	 * @param title
	 * @return
	 */
	public static String getElementSimpleContentByTagName(Document doc, String name) {
		Element element = XML.getElementByTagName(doc, name);
		return XML.firstChildNodeValueOrNullIfNoChildren(element);		
	}



	/**
	 * TODO document me
	 * 
	 * @param entryElement
	 * @param title
	 * @return
	 */
	public static String getElementSimpleContentByTagName(Element ancestor, String name) {
		Element element = XML.getElementByTagName(ancestor, name);
		return XML.firstChildNodeValueOrNullIfNoChildren(element);		
	}



	public static String getElementSimpleContentByTagNameNS(Document doc, String ns, String name) {
		Element element = XML.getElementByTagNameNS(doc, ns, name);
		return XML.firstChildNodeValueOrNullIfNoChildren(element);		
	}

	
	
	public static String getElementSimpleContentByTagNameNS(Element ancestor, String ns, String name) {
		Element element = XML.getElementByTagNameNS(ancestor, ns, name);
		return XML.firstChildNodeValueOrNullIfNoChildren(element);		
	}
	

	
	/**
	 * TODO document me
	 * 
	 * @param doc
	 * @param ns
	 * @param name
	 * @return
	 */
	public static List<String> getElementsSimpleContentsByTagName(Document doc, String name) {
		List<Element> elements = XML.getElementsByTagName(doc, name);
		return XML.simpleContents(elements);
	}

	
	
	/**
	 * TODO document me
	 * 
	 * @param ancestor
	 * @param ns
	 * @param name
	 * @return
	 */
	public static List<String> getElementsSimpleContentsByTagName(Element ancestor, String name) {
		List<Element> elements = XML.getElementsByTagName(ancestor, name);
		return XML.simpleContents(elements);
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
	public static List<String> getElementsSimpleContentsByTagNameNS(Document doc, String ns, String name) {
		List<Element> elements = XML.getElementsByTagNameNS(doc, ns, name);
		return XML.simpleContents(elements);
	}

	
	
	/**
	 * TODO document me
	 * 
	 * @param ancestor
	 * @param ns
	 * @param name
	 * @return
	 */
	public static List<String> getElementsSimpleContentsByTagNameNS(Element ancestor, String ns, String name) {
		List<Element> elements = XML.getElementsByTagNameNS(ancestor, ns, name);
		return XML.simpleContents(elements);
	}
	
	
	

	
	/**
	 * Return members of the given node list that are elements, as a list.
	 * 
	 * @param nodes
	 * @return
	 */
	public static List<Element> elements(NodeList nodes) {
		ArrayList<Element> elements = new ArrayList<Element>();
		for (int i=0; i<nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node instanceof Element) {
				Element element = (Element) node;
				elements.add(element);
			}
		}
		return elements;
	}

	
	
	public static List<String> simpleContents(List<Element> elements) {
		List<String> contents = new ArrayList<String>();
		for (Element element : elements) {
			contents.add(XML.firstChildNodeValueOrNullIfNoChildren(element));
		}
		return contents;
	}


	/**
	 * Return a new list of elements, filtered by namespace URI.
	 * 
	 * @param elements
	 * @param ns
	 * @return
	 */
	public static List<Element> filterByNamespaceURI(List<Element> elements, String ns) {
		List<Element> filtered = new ArrayList<Element>();
		for (Element element : elements) {
			if (element.getNamespaceURI().equals(ns)) {
				filtered.add(element);
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
	 * @param entryElement
	 * @param author
	 */
	public static void removeElementsByTagName(Element ancestor, String name) {
		List<Element> elements = XML.getElementsByTagName(ancestor, name);
		XML.removeFromParent(elements);
	}


	
	public static void removeElementsByTagNameNS(Element ancestor, String ns, String name) {
		List<Element> elements = XML.getElementsByTagNameNS(ancestor, ns, name);
		XML.removeFromParent(elements);
	}
	
	
	
	public static void removeFromParent(List<Element> elements) {
		for (Element e : elements) {
			Element parent = (Element) e.getParentNode();
			parent.removeChild(e);
		}
	}
	
	public static void setSimpleContent(Element element, String content) {
		XML.removeAllChildren(element);
		element.appendChild(element.getOwnerDocument().createTextNode(content));
	}
	
	
	
	public static void setElementSimpleContentByTagName(Element ancestor, String tagName, String content) {
		Element element = XML.getElementByTagName(ancestor, tagName);
		if (element == null) {
			element = XML.createElement(ancestor, tagName);
		}
		else {
			XML.removeAllChildren(element);
		}
		XML.setSimpleContent(element, content);		
	}

	
	

	/**
	 * TODO document me
	 * 
	 * @param studyElement
	 * @param tagName
	 * @param contents
	 */
	public static void setElementsSimpleContentsByTagName(Element ancestor,	String tagName, List<String> contents) {

		// remove any existing elements
		XML.removeElementsByTagName(ancestor, tagName);
		
		// create new elements and populate with content
		for (String content : contents) {
			Element element = XML.createElement(ancestor, tagName);
			XML.setSimpleContent(element, content);
		}

	}


	
	
	public static Element createElement(Element parent, String tagName) {
		Element child = parent.getOwnerDocument().createElement(tagName);
		parent.appendChild(child);
		return child;
	}


	
	public static Element firstOrNullIfEmpty(List<Element> elements) {
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
	 * @param element
	 * @return
	 */
	public static String firstChildNodeValueOrNullIfNoChildren(Element element) {
		if (element != null && element.hasChildNodes()) {
			return element.getFirstChild().getNodeValue();
		}
		else {
			return null;
		}
	}






}
