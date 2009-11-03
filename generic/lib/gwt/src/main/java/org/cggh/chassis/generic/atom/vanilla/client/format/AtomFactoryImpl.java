/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.format;

import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.impl.DOMParseException;

/**
 * @author aliman
 *
 */
public class AtomFactoryImpl extends AtomComponentFactory implements AtomFactory {


	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createEntry()
	 */
	public AtomEntry createEntry() {
		Element e = XMLNS.createElementNS(Atom.ELEMENT_ENTRY, Atom.PREFIX, Atom.NSURI);
		return new AtomEntryImpl(e, this);
	}
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createEntry(java.lang.String)
	 */
	public AtomEntry createEntry(String entryDocument) {
		try {
			Document d = XMLParser.parse(entryDocument);
			return createEntry(d.getDocumentElement());
		} catch (DOMParseException d) {
			throw new AtomFormatException("parse exception: "+d.getLocalizedMessage());
		}
	}
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createEntry(com.google.gwt.xml.client.Element)
	 */
	public AtomEntry createEntry(Element entryElement) {
		return new AtomEntryImpl(entryElement, this);
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createFeed(java.lang.String)
	 */
	public AtomFeed createFeed(String feedDocument) {
		
		try {
			Document d = XMLParser.parse(feedDocument);
			return new AtomFeedImpl(d.getDocumentElement(), this);
		} catch (DOMParseException e) {
			throw new AtomFormatException("parse exception: "+e.getLocalizedMessage());
		}

	}

	
	
	
	public AtomFeed createFeed() {
		Element e = XMLNS.createElementNS(Atom.ELEMENT_FEED, Atom.PREFIX, Atom.NSURI);
		return new AtomFeedImpl(e, this);
	}
	
	
	
	

	

	
}
