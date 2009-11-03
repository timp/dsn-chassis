/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.format;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.twisted.client.Function;
import org.cggh.chassis.generic.twisted.client.Functional;
import org.cggh.chassis.generic.xml.client.XML;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class ExtAtomFeedImpl<E extends AtomEntry, F extends ExtAtomFeed<E>> extends ElementWrapperImpl implements ExtAtomFeed<E> {

	
	
	
	protected ExtAtomFactory<E, F> factory;

	
	
	
	/**
	 * @param factory 
	 * @param feedDocument
	 */
	protected ExtAtomFeedImpl(Element feedElement, ExtAtomFactory<E, F> factory) {
		super(feedElement);
		AtomUtils.verifyElement(Atom.ELEMENT_FEED, Atom.NSURI, feedElement);
		this.factory = factory;
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#getEntries()
	 */
	public List<E> getEntries() {

		List<E> entries = new ArrayList<E>();
		
		Function<Element,E> wrapper = new Function<Element,E>() {

			public E apply(Element in) {
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

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#setTitle(java.lang.String)
	 */
	public void setTitle(String title) {
		XMLNS.setSingleElementSimpleContentByTagNameNS(element, Atom.ELEMENT_TITLE, Atom.PREFIX, Atom.NSURI, title);
	}
	
	

}
