/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.format;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.gwt.lib.xml.client.XML;

import com.google.gwt.xml.client.Element;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class AtomFactory {

	public AtomFeed createFeed(String feedDocXML) throws AtomFormatException {
		return new AtomFeed(feedDocXML, this);
	}
	
	public AtomEntry createEntry(String entryDocXML) throws AtomFormatException {
		return new AtomEntry(entryDocXML);
	}
	
	/**
	 * TODO document me
	 * 
	 * @param entryElement
	 * @return
	 * @throws AtomFormatException 
	 */
	public AtomEntry createEntry(Element entryElement) throws AtomFormatException {
		return new AtomEntry(entryElement);
	}

	public List<AtomEntry> createEntries(Element feedElement) throws AtomFormatException {
		List<Element> entryElements = XML.getElementsByTagNameNS(feedElement, AtomNS.NS, AtomNS.ENTRY); // do ns-aware search here
		List<AtomEntry> entries = new ArrayList<AtomEntry>();
		for (Element entryElement : entryElements) {
			AtomEntry entry = this.createEntry(entryElement);
			entries.add(entry);
		}
		return entries;
	}

}
