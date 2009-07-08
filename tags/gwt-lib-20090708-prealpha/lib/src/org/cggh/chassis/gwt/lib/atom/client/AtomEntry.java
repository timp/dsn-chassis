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

	protected String id = null;
	protected String title = null;
	protected String summary = null;
	protected List<AtomPersonConstruct> authors = new ArrayList<AtomPersonConstruct>();
	protected List<AtomCategory> categories = new ArrayList<AtomCategory>();
	protected String updated = null;
	protected AtomContent content = null;
	protected List<AtomExtension> extensions = new ArrayList<AtomExtension>();

	
	
	
	public AtomEntry() {}
	
	
	
	public AtomEntry(String entryDocXML) {
		Document entryDoc = XMLParser.parse(entryDocXML);
		Element entryElement = XML.getElementByTagNameNS(entryDoc, AtomNS.NS, AtomNS.ENTRY);
		init(entryElement);
	}
	
	
	
	/**
	 * @param entryElement
	 */
	public AtomEntry(Element entryElement) {
		init(entryElement);
	}



	/**
	 * TODO document me
	 * 
	 * @param doc
	 */
	protected void init(Element entryElement) {
		
		// init id
		this.id = XML.getSimpleContentByTagNameNS(entryElement, AtomNS.NS, AtomNS.ID);

		// init updated
		this.updated = XML.getSimpleContentByTagNameNS(entryElement, AtomNS.NS, AtomNS.UPDATED);		

		// init title
		this.title  = XML.getSimpleContentByTagNameNS(entryElement, AtomNS.NS, AtomNS.TITLE);
		
		// init summary
		this.summary  = XML.getSimpleContentByTagNameNS(entryElement, AtomNS.NS, AtomNS.SUMMARY);

		// init authors
		List<Element> authorElements = XML.getElementsByTagNameNS(entryElement, AtomNS.NS, AtomNS.AUTHOR);
		for (Element authorElement : authorElements) {
			this.authors.add(new AtomPersonConstruct(authorElement));
		}

		// init categories
		List<Element> categoryElements = XML.getElementsByTagNameNS(entryElement, AtomNS.NS, AtomNS.CATEGORY);
		for (Element categoryElement : categoryElements) {
			this.categories.add(new AtomCategory(categoryElement));
		}
		
		// TODO init links
		
		// init content
		Element contentElement = XML.getElementByTagNameNS(entryElement, AtomNS.NS, AtomNS.CONTENT);
		if (contentElement != null) {
			this.content = new AtomContent(contentElement);
		}
		
	}
	
	
	
	public String toXML() {

		String xml = 
			"<entry xmlns=\"http://www.w3.org/2005/AtomNS\">";
		
		// output title
		if (this.title != null) {
			xml += "<title>"+this.title+"</title>";
		}
		
		// output summary
		if (this.summary != null) {
			xml += "<summary>"+this.summary+"</summary>";
		}
		
		// output authors
		for (AtomPersonConstruct author : authors) {
			xml += author.toXML();
		}
		
		// output categories
		for (AtomCategory category : categories) {
			xml += category.toXML();
		}
		
		// output content
		if (this.content != null) {
			xml += content.toXML();
		}
		
		// output extensions
		for (AtomExtension extension : extensions) {
			xml += extension.toXML();
		}
		
		xml +=
			"</entry>";
		
		return xml;

	}



	/**
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	
	
	/**
	 * @return the summary
	 */
	public String getSummary() {
		return this.summary;
	}

	
	
	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	
	
	/**
	 * @return the authors
	 */
	public List<AtomPersonConstruct> getAuthors() {
		return this.authors ;
	}

	
	
	/**
	 * @param authors the authors to set
	 */
	public void setAuthors(List<AtomPersonConstruct> authors) {
		this.authors = authors;
	}
	
	
	
	/**
	 * @return the categories
	 */
	public List<AtomCategory> getCategories() {
		return this.categories ;
	}

	
	
	/**
	 * @param categories the categories to set
	 */
	public void setCategories(List<AtomCategory> categories) {
		this.categories = categories;
	}
	
	
	
	public String getId() {
		return this.id;
	}

	
	
	public String getUpdated() {
		return this.updated ;
	}



	/**
	 * @return the content
	 */
	public AtomContent getContent() {
		return this.content;
	}



	/**
	 * @param content the content to set
	 */
	public void setContent(AtomContent content) {
		this.content = content;
	}



	/**
	 * @return the extensions
	 */
	public List<AtomExtension> getExtensions() {
		return this.extensions;
	}



	/**
	 * @param extensions the extensions to set
	 */
	public void setExtensions(List<AtomExtension> extensions) {
		this.extensions = extensions;
	}
	
	
	
}

