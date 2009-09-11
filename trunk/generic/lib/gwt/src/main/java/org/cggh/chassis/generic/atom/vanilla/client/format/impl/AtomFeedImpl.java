/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.format.impl;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.vanilla.client.format.Atom;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFormatException;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink;
import org.cggh.chassis.generic.twisted.client.Function;
import org.cggh.chassis.generic.twisted.client.Functional;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.impl.DOMParseException;

/**
 * @author aliman
 *
 */
public class AtomFeedImpl extends ElementWrapperImpl implements AtomFeed {

	private AtomFactory factory;

	/**
	 * @param factory 
	 * @param feedDocument
	 */
	protected AtomFeedImpl(Element feedElement, AtomFactory factory) {
		super(feedElement);
		this.factory = factory;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#getEntries()
	 */
	public List<AtomEntry> getEntries() {

		List<AtomEntry> entries = new ArrayList<AtomEntry>();
		
		Function<Element,AtomEntry> wrapper = new Function<Element,AtomEntry>() {

			public AtomEntry apply(Element in) {
				return factory.createEntry(in);
			}
			
		};

		Functional.map(XML.getElementsByTagName(element, Atom.ELEMENT_ENTRY), entries, wrapper);
		
		return entries;

	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#getId()
	 */
	public String getId() {
		return XML.getElementSimpleContentByTagName(element, Atom.ELEMENT_ID);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#getTitle()
	 */
	public String getTitle() {
		return XML.getElementSimpleContentByTagName(element, Atom.ELEMENT_TITLE);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#getUpdated()
	 */
	public String getUpdated() {
		return XML.getElementSimpleContentByTagName(element, Atom.ELEMENT_UPDATED);
	}

}
