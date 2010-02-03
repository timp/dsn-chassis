/**
 * 
 */
package org.cggh.chassis.generic.atom.client;


import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.impl.DOMParseException;


/**
 * @author aliman
 *
 */
public abstract class AtomFactory<E extends AtomEntry, F extends AtomFeed<E>> 
	extends AtomComponentFactory {

	
	
	public abstract F createFeed(Element feedElement);
	public abstract E createEntry(Element entryElement);
	public abstract String getEntryTemplate();
	public abstract String getFeedTemplate();


	
	
	public F createFeed() {
		return createFeed(getFeedTemplate());
	}
	
	
	
	
	public F createFeed(String feedDocument) {
		try {
			Document d = XMLParser.parse(feedDocument);
			return createFeed(d.getDocumentElement());
		} catch (DOMParseException e) {
			throw new AtomFormatException("parse exception: "+e.getLocalizedMessage());
		}
	}

	
	
	public E createEntry() {
		return createEntry(getEntryTemplate());
	}

	
	
	
	public E createEntry(String entryDocument) {
		try {
			Document d = XMLParser.parse(entryDocument);
			return createEntry(d.getDocumentElement());
		} catch (DOMParseException d) {
			throw new AtomFormatException("parse exception: "+d.getLocalizedMessage());
		}
	}

	
	
}
