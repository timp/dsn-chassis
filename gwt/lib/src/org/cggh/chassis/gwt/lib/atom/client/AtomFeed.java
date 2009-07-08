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
public class AtomFeed {

	
	
	protected Element feedElement = null;

	
	protected static final String template = "<feed xmlns=\"http://www.w3.org/2005/AtomNS\"></feed>";
	
	
	
	public AtomFeed() throws AtomFormatException {
		Document doc = XMLParser.parse(template);
		this.feedElement = XML.getElementByTagNameNS(doc, AtomNS.NS, AtomNS.FEED); // ns-aware here only
		init();
	}
	
	
	
	public AtomFeed(String feedDocXML) throws AtomFormatException {
		Document doc = XMLParser.parse(feedDocXML);
		this.feedElement = XML.getElementByTagNameNS(doc, AtomNS.NS, AtomNS.FEED); // ns-aware here only
		init();
	}
	
	
	
	protected void init() throws AtomFormatException {
		if (this.feedElement == null) {
			throw new AtomFormatException("feed element is null");
		}
		if (!this.feedElement.getTagName().equals(AtomNS.FEED)) {
			throw new AtomFormatException("feed element has unexpected tag name: "+this.feedElement.getTagName());
		}
		if (this.feedElement.getNamespaceURI() == null) {
			throw new AtomFormatException("feed element namespace URI is null");
		}
		if (!this.feedElement.getNamespaceURI().equals(AtomNS.NS)) {
			throw new AtomFormatException("feed element has unexpected namespace URI: "+this.feedElement.getNamespaceURI());
		}
	}
	
	
	
	public String getId() {
		return XML.getElementSimpleContentByTagName(this.feedElement, AtomNS.ID);
	}
	
	
	
	public String getUpdated() {
		return XML.getElementSimpleContentByTagName(this.feedElement, AtomNS.UPDATED);		
	}
	
	
	
	public String getTitle() {
		return XML.getElementSimpleContentByTagName(this.feedElement, AtomNS.TITLE);
	}
	
	

	public void setTitle(String title) {
		XML.setElementSimpleContentByTagName(this.feedElement, AtomNS.TITLE, title);
	}
	
	
	
	public List<AtomEntry> getEntries() throws AtomFormatException {
		List<Element> entryElements = XML.getElementsByTagNameNS(this.feedElement, AtomNS.NS, AtomNS.ENTRY); // do ns-aware search here
		List<AtomEntry> entries = new ArrayList<AtomEntry>();
		for (Element entryElement : entryElements) {
			AtomEntry entry = new AtomEntry(entryElement);
			entries.add(entry);
		}
		return entries;
	}
}
