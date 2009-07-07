/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.gwt.lib.xml.client.XML;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class AtomEntry {
	
	
	
	protected static final String template = "<entry xmlns=\"http://www.w3.org/2005/AtomNS\"><title></title></entry>";

	
	
	protected Document doc = null;
	protected Element entryElement = null;
	protected Element contentElement = null;

	
	
	public AtomEntry() {
		Document doc = XMLParser.parse(template);
	    init(doc);
	}
	
	
	
	public AtomEntry(String entryDocXML) {
		Document doc = XMLParser.parse(entryDocXML);
		init(doc);
	}
	
	
	
	/**
	 * @param entryElement
	 */
	public AtomEntry(Element entryElement) {
		this.doc = entryElement.getOwnerDocument();
		this.entryElement = entryElement;
	}



	/**
	 * TODO document me
	 * 
	 * @param doc
	 */
	protected void init(Document entryDoc) {
		this.doc = entryDoc;
		this.entryElement = XML.getElementByTagNameNS(doc, AtomNS.NS, AtomNS.ENTRY);
	}



	@Override
	public String toString() {
		return this.doc.toString();
	}

	
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return XML.getSimpleContentByTagNameNS(entryElement, AtomNS.NS, AtomNS.TITLE);
	}

	
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		Element titleElement = XML.getElementByTagNameNS(entryElement, AtomNS.NS, AtomNS.TITLE);
		XML.setSimpleContent(titleElement, title);
	}

	
	
	/**
	 * @return the summary
	 */
	public String getSummary() {
		return XML.getSimpleContentByTagNameNS(entryElement, AtomNS.NS, AtomNS.SUMMARY);
	}

	
	
	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		Element summaryElement = XML.getElementByTagNameNS(entryElement, AtomNS.NS, AtomNS.SUMMARY);
		if (summaryElement == null) {
			summaryElement = doc.createElement(AtomNS.SUMMARY);
			entryElement.appendChild(summaryElement);
		}
		XML.removeAllChildren(summaryElement);
		summaryElement.appendChild(doc.createTextNode(summary));
	}

	
	
	/**
	 * @return the authors
	 */
	public List<AtomPersonConstruct> getAuthors() {
		List<Element> authorElements = XML.getElementsByTagNameNS(entryElement, AtomNS.NS, AtomNS.AUTHOR);
		List<AtomPersonConstruct> authors = new ArrayList<AtomPersonConstruct>();
		for (Element authorElement : authorElements) {
			authors.add(new AtomPersonConstruct(authorElement));
		}
		return authors;
	}

	
	
	/**
	 * @param authors the authors to set
	 */
	public void setAuthors(List<AtomPersonConstruct> authors) {

		// remove existing author elements
		XML.removeElementsByTagNameNS(entryElement, AtomNS.NS, AtomNS.AUTHOR);
		
		// create new author elements and append to entry element
		for (AtomPersonConstruct author : authors) {
			Element authorElement = doc.createElement(AtomNS.AUTHOR);
			entryElement.appendChild(authorElement);
			author.populate(doc, authorElement);
		}

	}
	
	
	
	/**
	 * @return the categories
	 */
	public List<AtomCategory> getCategories() {
		List<Element> categoryElements = XML.getElementsByTagNameNS(entryElement, AtomNS.NS, AtomNS.CATEGORY);
		List<AtomCategory> categories = new ArrayList<AtomCategory>();
		for (Element categoryElement : categoryElements) {
			categories.add(new AtomCategory(categoryElement));
		}
		return categories;
	}

	
	
	/**
	 * @param categories the categories to set
	 */
	public void setCategories(List<AtomCategory> categories) {

		// remove existing category elements
		List<Element> categoryElements = XML.getElementsByTagNameNS(entryElement, AtomNS.NS, AtomNS.CATEGORY);
		for (Element categoryElement : categoryElements) {
			entryElement.removeChild(categoryElement);
		}
		
		// create new category elements and append to entry element
		for (AtomCategory category : categories) {
			Element categoryElement = doc.createElement(AtomNS.CATEGORY);
			category.populate(doc, categoryElement);
			entryElement.appendChild(categoryElement);
		}

	}
	
	
	
	public void setContentType(String type) {
		
		// set attribute
		getContentElement().setAttribute(AtomNS.TYPE, type);

	}

	
	
	protected Element getContentElement() {
		if (contentElement == null) {
			contentElement = doc.createElement(AtomNS.CONTENT);
			entryElement.appendChild(contentElement);
		}
		return contentElement;
	}
	
	
	
	public String getId() {
		return XML.getSimpleContentByTagNameNS(entryElement, AtomNS.NS, AtomNS.ID);
	}

	
	
	public String getUpdated() {
		return XML.getSimpleContentByTagNameNS(entryElement, AtomNS.NS, AtomNS.UPDATED);		
	}
	
	
	
}

