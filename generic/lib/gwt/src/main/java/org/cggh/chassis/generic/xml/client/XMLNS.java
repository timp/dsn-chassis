/**
 * 
 */
package org.cggh.chassis.generic.xml.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.twisted.client.Function;
import org.cggh.chassis.generic.twisted.client.Functional;

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
			this.namespaceUri = namespaceUri;
		}
		
		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.twisted.client.Function#apply(java.lang.Object)
		 */
		public Element apply(Element e) {
			boolean match = (namespaceUri == null && e.getNamespaceURI() == null) || (namespaceUri != null && namespaceUri.equals(e.getNamespaceURI()));
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

		List<Element> elements = new ArrayList<Element>();
		
		Functional.map(XML.elements(nodes), elements, new FilterElementsByNamespaceUri(namespaceUri));
		
		return elements;
		
	}

	
	
	
	/**
	 * @param localName the local name of the element to create
	 * @param prefix the namespace prefix to use
	 * @param namespaceUri the namespace URI to use
	 * @return
	 */
	public static Element createElementNS(String localName, String prefix, String namespaceUri) {
		
		String tagName = (prefix != null) ? prefix + ":" +localName : localName;
		
		String xmlnsAttribute = null;
		
		if (namespaceUri != null) {
			xmlnsAttribute = "xmlns";
			if (prefix != null) {
				xmlnsAttribute += ":" +prefix;
			}
			xmlnsAttribute += "=\"" + namespaceUri + "\"";
		}

		String template = "<" + tagName + " ";
		
		if (xmlnsAttribute != null) {
			template += xmlnsAttribute + " ";
		}
		
		template += "/>";
		
		Element e = XMLParser.parse(template).getDocumentElement();
		
		// return clone to work around chrome wrong document error
		Element clone = (Element) e.cloneNode(true);
		
		return clone;
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
	
	
	
	
	public static Element getFirstElementByTagNameNS(Element ancestor, String localName, String namespaceUri) {
		List<Element> elements = XMLNS.getElementsByTagNameNS(ancestor, localName, namespaceUri);
		if (elements.size() > 0) return elements.get(0);
		else return null;
	}
	
	
	
	public static String getFirstElementSimpleContentByTagNameNS(Element ancestor, String localName, String namespaceUri) {
		Element e = XMLNS.getFirstElementByTagNameNS(ancestor, localName, namespaceUri);
		if (e != null) return XML.firstChildNodeValueOrNullIfNoChildren(e);
		else return null;
	}
	

	
	public static void setSingleElementSimpleContentByTagNameNS(Element ancestor, String localName, String prefix, String namespaceUri, String content) {
		XMLNS.removeElementsByTagNameNS(ancestor, localName, namespaceUri);
		Element e = XMLNS.createElementNS(localName, prefix, namespaceUri);
		XML.setSimpleContent(e, content);
		ancestor.appendChild(e);
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
