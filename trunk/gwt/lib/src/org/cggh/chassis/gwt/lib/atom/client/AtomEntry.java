/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client;

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
	
	
	
	protected static final String template = "<entry xmlns=\"http://www.w3.org/2005/Atom\"></entry>";

	
	
	protected Element entryElement = null;

	

	/**
	 * Create a new Atom entry.
	 * @throws AtomFormatException 
	 */
	public AtomEntry() throws AtomFormatException {
		Document entryDoc = XMLParser.parse(template);
		this.entryElement = XML.getElementByTagNameNS(entryDoc, AtomNS.NS, AtomNS.ENTRY);
	    init();
	}
	
	
	
	/**
	 * Create a new Atom entry, initialised from the entry XML document supplied.
	 *  
	 * @param entryDocXML
	 * @throws AtomFormatException 
	 */
	public AtomEntry(String entryDocXML) throws AtomFormatException {
		Document entryDoc = XMLParser.parse(entryDocXML);
		this.entryElement = XML.getElementByTagNameNS(entryDoc, AtomNS.NS, AtomNS.ENTRY);
		init();
	}
	
	
	
	/**
	 * Create a new Atom entry, wrapping the entry element supplied.
	 * N.B. The entry element may be part of a feed document.
	 * 
	 * @param entryElement
	 * @throws AtomFormatException 
	 */
	public AtomEntry(Element entryElement) throws AtomFormatException {
		this.entryElement = entryElement;
		init();
	}



	/**
	 * TODO document me
	 * 
	 * @param doc
	 * @throws AtomFormatException 
	 */
	protected void init() throws AtomFormatException {
		if (this.entryElement == null) {
			throw new AtomFormatException("entry element is null");
		}
		if (!this.entryElement.getTagName().equals(AtomNS.ENTRY)) {
			throw new AtomFormatException("entry element has unexpected tag name: "+this.entryElement.getTagName());
		}
		if (this.entryElement.getNamespaceURI() == null) {
			throw new AtomFormatException("entry element namespace URI is null");
		}
		if (!this.entryElement.getNamespaceURI().equals(AtomNS.NS)) {
			throw new AtomFormatException("entry element has unexpected namespace URI: "+this.entryElement.getNamespaceURI());
		}
	}



	/**
	 * Render this entry as XML.
	 */
	@Override
	public String toString() {
		
		// clone the entry element, because it might be part of a feed
		Element entryClone = (Element) this.entryElement.cloneNode(true);

		// append to new, empty document
		Document entryDoc = XMLParser.createDocument();
		entryDoc.appendChild(entryClone);
		
		// emit XML
		return entryDoc.toString();
	}

	
	
	protected String getElementContent(String tagName) {
		return XML.getElementSimpleContentByTagName(this.entryElement, tagName);		
	}
	
	
	protected void setElementContent(String tagName, String content) {
		XML.setElementSimpleContentByTagName(this.entryElement, tagName, content);
	}
	
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return this.getElementContent(AtomNS.TITLE);
	}

	
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.setElementContent(AtomNS.TITLE, title);
	}

	
	
	/**
	 * @return the summary
	 */
	public String getSummary() {
		return this.getElementContent(AtomNS.SUMMARY);
	}

	
	
	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.setElementContent(AtomNS.SUMMARY, summary);
	}

	
	
	public String getId() {
		return this.getElementContent(AtomNS.ID);
	}

	
	
	public String getUpdated() {
		return this.getElementContent(AtomNS.UPDATED);
	}
	
	
	/**
	 * TODO document me
	 * 
	 * @return
	 */
	public String getPublished() {
		return this.getElementContent(AtomNS.PUBLISHED);
	}
	

		
	/**
	 * @return the authors
	 */
	public List<AtomPersonConstruct> getAuthors() {
		return AtomPersonConstruct.getAuthors(this.entryElement);
	}

	
	
	/**
	 * @param authors the authors to set
	 */
	public void setAuthors(List<AtomPersonConstruct> authors) {
		AtomPersonConstruct.setAuthors(this.entryElement, authors);
	}
	
	
	
	/**
	 * @return the categories
	 */
	public List<AtomCategory> getCategories() {
		return AtomCategory.getCategories(this.entryElement);
	}

	
	
	/**
	 * @param categories the categories to set
	 */
	public void setCategories(List<AtomCategory> categories) {
		AtomCategory.setCategories(this.entryElement, categories);
	}



	
	
}

