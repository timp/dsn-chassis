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

	
	
	protected Document doc = null;
	protected Element feedElement = null;
	protected static final String template = "<feed xmlns=\"http://www.w3.org/2005/AtomNS\"><title></title></feed>";
	
	
	
	public AtomFeed() {
		this.doc = XMLParser.parse(template);
		init();
	}
	
	
	
	public AtomFeed(String feedDocXML) {
		this.doc = XMLParser.parse(feedDocXML);
		init();
	}
	
	
	
	protected void init() {
		this.feedElement = XML.getElementByTagNameNS(doc, AtomNS.NS, AtomNS.FEED);
	}
	
	
	
	public String getId() {
		return XML.getSimpleContentByTagNameNS(feedElement, AtomNS.NS, AtomNS.ID);
	}
	
	
	
	public String getUpdated() {
		return XML.getSimpleContentByTagNameNS(feedElement, AtomNS.NS, AtomNS.UPDATED);		
	}
	
	
	
	public String getTitle() {
		return XML.getSimpleContentByTagNameNS(feedElement, AtomNS.NS, AtomNS.TITLE);
	}
	
	

	public void setTitle(String title) {
		Element titleElement = XML.getElementByTagNameNS(feedElement, AtomNS.NS, AtomNS.TITLE);
		XML.setSimpleContent(titleElement, title);
	}
	
	
	
	public List<AtomEntry> getEntries() {
		List<Element> entryElements = XML.getElementsByTagNameNS(feedElement, AtomNS.NS, AtomNS.ENTRY);
		List<AtomEntry> entries = new ArrayList<AtomEntry>();
		for (Element entryElement : entryElements) {
			AtomEntry entry = new AtomEntry(entryElement);
			entries.add(entry);
		}
		return entries;
	}
}
