/**
 * 
 */
package org.cggh.chassis.generic.xml.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.async.client.Functional;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

/**
 * @author aliman
 *
 */
public class XMLNS {

	
	
	
	static class FilterElementsByNamespaceUri implements Function<Element,Element> {

		private String namespaceUri;

		FilterElementsByNamespaceUri(String namespaceUri) {
			if (namespaceUri == null) namespaceUri = ""; // normalise for browsers
			this.namespaceUri = namespaceUri;
		}
		
		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.twisted.client.Function#apply(java.lang.Object)
		 */
		public Element apply(Element e) {
			boolean match = namespaceUri.equals(XML.getNamespaceUri(e));
			if (match) return e;
			return null;
		}
		
	}
	
	
	

	static class FilterElementsByLocalName implements Function<Element,Element> {

		private String localName;

		FilterElementsByLocalName(String localName) {
			this.localName = localName;
		}
		
		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.twisted.client.Function#apply(java.lang.Object)
		 */
		public Element apply(Element e) {
			boolean match = localName.equals(XML.getLocalName(e));
			if (match) return e;
			return null;
		}
		
	}
	
	
	
/**
	 * @param ancestor the element to search within
	 * @param namespaceUri the namespace URI to match
	 * @param localName the element local name to match
	 * @return
	 */
	public static List<Element> getElementsByTagNameNS(Element ancestor, String localName, String namespaceUri) {
		return XMLNS.filterElementsByNamespaceUri(ancestor.getElementsByTagName(localName), namespaceUri);
	}

	
	
	
	public static List<Element> getChildrenByTagNameNS(Element parent, String localName, String namespaceUri) {

		List<Element> out = XMLNS.filterElementsByNamespaceUri(parent.getChildNodes(), namespaceUri);
		out = XMLNS.filterElementsByLocalName(out, localName);
		
		return out;
	}
	
	
	
	
	
	/**
	 * @param ancestor the element to search within
	 * @param namespaceUri the namespace URI to match
	 * @param localName the element local name to match
	 * @return
	 */
	public static List<Element> getElementsByTagNameNS(Document doc, String localName, String namespaceUri) {
		return XMLNS.filterElementsByNamespaceUri(doc.getElementsByTagName(localName), namespaceUri);
	}
	
	
	
	public static List<Element> filterElementsByNamespaceUri(NodeList nodes, String namespaceUri) {

		List<Element> out = new ArrayList<Element>();
		
		Functional.map(XML.elements(nodes), out, new FilterElementsByNamespaceUri(namespaceUri));
		
		return out;
		
	}

	
	
	
	public static List<Element> filterElementsByLocalName(List<Element> in, String localName) {

		List<Element> out = new ArrayList<Element>();
		
		Functional.map(in, out, new FilterElementsByLocalName(localName));
		
		return out;
	}

	
	
	
	/**
	 * @param localName the local name of the element to create
	 * @param prefix the namespace prefix to use
	 * @param namespaceUri the namespace URI to use
	 * @return
	 */
	public static Element createElementNS(String localName, String prefix, String namespaceUri) {
		
//		if (prefix == null) prefix = ""; // normalise
//		if (namespaceUri == null) namespaceUri = ""; // normalise
//		
//		boolean hasPrefix = !prefix.equals("");
//		
//		String tagName = hasPrefix ? prefix + ":" +localName : localName;
//		
//		String xmlnsAttribute = "xmlns";
//		if (hasPrefix) xmlnsAttribute += ":" +prefix;
//		xmlnsAttribute += "=\"" + namespaceUri + "\"";
//
//		String template = "<" + tagName + " " + xmlnsAttribute + " />";
//		
//		Element e = XMLParser.parse(template).getDocumentElement();
		
		Element e = createDocumentNS(localName, prefix, namespaceUri).getDocumentElement();
		
		// return clone to work around chrome wrong document error
		// see http://code.google.com/p/google-web-toolkit/issues/detail?id=4074
		Element clone = (Element) e.cloneNode(true);
		
		return clone;
	}
	
	
	
	
	public static Document createDocumentNS(String rootElementLocalName, String rootElementPrefix, String rootElementNamespaceUri) {
		
		if (rootElementPrefix == null) rootElementPrefix = ""; // normalise
		if (rootElementNamespaceUri == null) rootElementNamespaceUri = ""; // normalise
		
		boolean hasPrefix = !rootElementPrefix.equals("");
		
		String tagName = hasPrefix ? rootElementPrefix + ":" +rootElementLocalName : rootElementLocalName;
		
		String xmlnsAttribute = "xmlns";
		if (hasPrefix) xmlnsAttribute += ":" +rootElementPrefix;
		xmlnsAttribute += "=\"" + rootElementNamespaceUri + "\"";

		String template = "<" + tagName + " " + xmlnsAttribute + " />";
		
		Document d = XMLParser.parse(template);
		
		return d;
		
	}




	/**
	 * @param ancestor
	 * @param string
	 * @param object
	 */
	public static void removeElementsByTagNameNS(Element ancestor, String localName, String namespaceUri) {
		for (Element e : XMLNS.getElementsByTagNameNS(ancestor, localName, namespaceUri)) {
			e.getParentNode().removeChild(e);
		}
	}
	
	
	
	
	public static void removeChildrenByTagNameNS(Element parent, String localName, String namespaceUri) {
		for (Element e : XMLNS.getChildrenByTagNameNS(parent, localName, namespaceUri)) {
			e.getParentNode().removeChild(e);
		}
	}
	
	
	
	
	public static Element getFirstElementByTagNameNS(Element ancestor, String localName, String namespaceUri) {
		List<Element> elements = XMLNS.getElementsByTagNameNS(ancestor, localName, namespaceUri);
		if (elements.size() > 0) return elements.get(0);
		else return null;
	}
	
	
	
	
	public static Element getFirstChildByTagNameNS(Element parent, String localName, String namespaceUri) {
		List<Element> elements = XMLNS.getChildrenByTagNameNS(parent, localName, namespaceUri);
		if (elements.size() > 0) return elements.get(0);
		else return null;
	}
	
	
	public static String getFirstElementSimpleContentByTagNameNS(Element ancestor, String localName, String namespaceUri) {
		Element e = XMLNS.getFirstElementByTagNameNS(ancestor, localName, namespaceUri);
		if (e != null) return XML.firstChildNodeValueOrNullIfNoChildren(e);
		else return null;
	}
	

	public static String getFirstChildSimpleContentByTagNameNS(Element parent, String localName, String namespaceUri) {
		Element e = XMLNS.getFirstChildByTagNameNS(parent, localName, namespaceUri);
		if (e != null) return XML.firstChildNodeValueOrNullIfNoChildren(e);
		else return null;
	}
	

	
	
	public static void setSingleElementSimpleContentByTagNameNS(Element ancestor, String localName, String prefix, String namespaceUri, String content) {
		XMLNS.removeElementsByTagNameNS(ancestor, localName, namespaceUri);
		Element e = XMLNS.createElementNS(localName, prefix, namespaceUri);
		XML.setSimpleContent(e, content);
		ancestor.appendChild(e);
	}




	public static void setSingleChildSimpleContentByTagNameNS(Element parent, String localName, String prefix, String namespaceUri, String content) {
		XMLNS.removeChildrenByTagNameNS(parent, localName, namespaceUri);
		Element e = XMLNS.createElementNS(localName, prefix, namespaceUri);
		XML.setSimpleContent(e, content);
		parent.appendChild(e);
	}




	/**
	 * @param element
	 * @param elementModule
	 * @param nsuri
	 * @return
	 */
	public static List<String> getElementsSimpleContentsByTagNameNS(Element ancestor, String localName, String namespaceUri) {
		List<String> contents = new ArrayList<String>();
		for (Element e : XMLNS.getElementsByTagNameNS(ancestor, localName, namespaceUri)) {
			contents.add(XML.firstChildNodeValueOrNullIfNoChildren(e));
		}
		return contents;
	}




	/**
	 * @param element
	 * @param elementModule
	 * @param nsuri
	 * @param modules
	 */
	public static void setElementsSimpleContentsByTagNameNS(Element ancestor, String localName, String prefix, String namespaceUri, List<String> contents) {
		XMLNS.removeElementsByTagNameNS(ancestor, localName, namespaceUri);
		for (String s : contents) {
			Element e = XMLNS.createElementNS(localName, prefix, namespaceUri);
			XML.setSimpleContent(e, s);
			ancestor.appendChild(e);
		}
	}




	/**
	 * @param doc
	 * @param localName
	 * @param namespaceUri
	 * @return
	 */
	public static Element getFirstElementByTagNameNS(Document doc, String localName, String namespaceUri) {
		List<Element> elements = XMLNS.getElementsByTagNameNS(doc, localName, namespaceUri);
		if (elements.size() > 0) return elements.get(0);
		else return null;
	}




	/**
	 * @param doc
	 * @param localName
	 * @param ns2
	 * @return
	 */
	public static String getFirstElementSimpleContentByTagNameNS(Document doc, String localName, String namespaceUri) {
		Element e = XMLNS.getFirstElementByTagNameNS(doc, localName, namespaceUri);
		if (e != null) return XML.firstChildNodeValueOrNullIfNoChildren(e);
		else return null;
	}




}
