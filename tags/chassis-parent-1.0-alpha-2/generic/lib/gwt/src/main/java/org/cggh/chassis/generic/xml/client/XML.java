/**
 * $Id$
 */
package org.cggh.chassis.generic.xml.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;


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

	
	
	private static Log log = LogFactory.getLog(XML.class);

	
	public static String getLocalName(Element e) {
		String prefix = e.getPrefix();
		String tagName = e.getTagName();
		if (tagName.startsWith(prefix+":")) {
			return tagName.replaceFirst(prefix+":", "");
		}
		else {
			return tagName;
		}
	}
	
	
	
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
			String nsuri = element.getNamespaceURI();
			if (nsuri != null && ns != null && ns.equals(nsuri)) {
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
	
	
	
	public static void removeAllChildElements(Element parent) {
		List<Element> children = XML.elements(parent.getChildNodes());
		for (Element child : children) {
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



	/**
	 * @param e
	 * @return
	 */
	public static String getPrefix(Element e) {
		String prefix = e.getPrefix();
		if (prefix == null) {
			prefix = ""; // normalise across browsers
		}
		return prefix;
	}



	/**
	 * @param e
	 * @return
	 */
	public static String getNamespaceUri(Element e) {
		String namespaceUri = e.getNamespaceURI();
		if (namespaceUri == null) {
			namespaceUri = ""; // normalise across browsers
		}
		return namespaceUri;
	}


	
	
	public static List<Element> getChildrenByTagName(Element parent, String tagName) {
		List<Element> children = new ArrayList<Element>();
		for (Element child : XML.elements(parent.getChildNodes())) {
			if (child.getTagName().equals(tagName)) {
				children.add(child);
			}
		}
		return children;
	}


	
	
	
	public static List<Element> getChildrenByLocalName(Element parent, String localName) {
		List<Element> children = new ArrayList<Element>();
		for (Element child : XML.elements(parent.getChildNodes())) {
			if (getLocalName(child).equals(localName)) {
				children.add(child);
			}
		}
		return children;
	}


	
	
	public static Element getFirstChildByTagName(Element parent, String tagName) {
		List<Element> children = getChildrenByTagName(parent, tagName);
		return children.size() > 0 ? children.get(0) : null;
	}


	
	
	public static void getElementsBySimplePathSegment(Element in, String pathSegment, Set<Element> out) {
		log.enter("getElementsBySimplePathSegment");

		log.debug("context element tagName:" + in.getTagName());
		log.debug("path segment: "+pathSegment);
		
		if (pathSegment == null) {
			// TODO
		}
		else if (pathSegment.equals("")) {
			// TODO
		}
		else if (pathSegment.equals("..")) {
			Node parent = in.getParentNode();
			if (parent != null && parent instanceof Element) {
				out.add((Element)parent);
			}
		}
		else if (pathSegment.equals("*")) {
			out.addAll(XML.elements(in.getChildNodes()));
		}
		else {
			out.addAll(XML.getChildrenByLocalName(in, pathSegment));
		}
		
		log.debug("output set size: "+out.size());
		
		log.leave();
	}
	
	
	
	public static void getElementsBySimplePathSegment(Set<Element> in, String pathSegment, Set<Element> out) {
		for (Element e : in) {
			getElementsBySimplePathSegment(e, pathSegment, out);
		}
	}
	
	
	
	public static Set<Element> getElementsBySimplePathSegment(Set<Element> in, String pathSegment) {
		Set<Element> out = createSet();
		getElementsBySimplePathSegment(in, pathSegment, out);
		return out;
	}
	
	
	
	public static Set<Element> getElementsBySimplePath(Element context, String simplePath) {
		log.enter("getElementsBySimplePath(Element, String)");

		Set<Element> out;
		
		if (simplePath.startsWith("/")) {

			out = getElementsBySimplePath(context.getOwnerDocument(), simplePath);

		}
		else {
			
			Set<Element> results = createSet();
			results.add(context);
			
			String[] segments = simplePath.split("/");
			
			for (String segment : segments) {
				
				log.debug("applying path segment: "+segment);
				
				results = getElementsBySimplePathSegment(results, segment);
				
				log.debug("found "+results.size()+" results");
			}
			
			out = results;

		}
		
		log.leave();
		return out;
	}
	
	
	
	public static Set<Element> getElementsBySimplePath(Document context, String simplePath) {
		log.enter("getElementsBySimplePath(Document, String)");
		
		Set<Element> results = createSet();
		
		if (simplePath.startsWith("/")) {
			simplePath = simplePath.substring(1); // chop of leading slash to avoid empty first segment
		}
		
		log.debug("path:"+simplePath);
		
		String[] segments = simplePath.split("/");
		
		for (int i=0; i<segments.length; i++) {
			String segment = segments[i];
			log.debug("i: "+i+"; segment: "+segment);
			if (i == 0) {
				// handle initial segment in a special way, matching root element
				if (segment == null) {
					// TODO
				}
				else if (segment.equals("")) {
					// TODO
				}
				else if (segment.equals("*")) {
					results.add(context.getDocumentElement());
				}
				else {
					Element root = context.getDocumentElement();
					if (getLocalName(root).equals(segment)) {
						results.add(root);
					}
				}
			}
			else results = getElementsBySimplePathSegment(results, segment);
		}
		
		log.leave();
		return results;
	}
	
	
	
	public static Set<Element> createSet() {

		Comparator<Element> c = new Comparator<Element>() {
			public int compare(Element arg0, Element arg1) {
				if (arg0.equals(arg1))
					return 0;
				else
					return 1;
			}
		};
		
		Set<Element> t = new TreeSet<Element>(c); 

		return t;
		
	}


}
