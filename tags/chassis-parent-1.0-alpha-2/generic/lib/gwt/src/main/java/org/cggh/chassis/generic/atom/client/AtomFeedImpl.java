/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.async.client.Functional;
import org.cggh.chassis.generic.xml.client.ElementWrapperImpl;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class AtomFeedImpl<E extends AtomEntry, F extends AtomFeed<E>> 
	extends ElementWrapperImpl 
	implements AtomFeed<E> {

	
	
	
	protected AtomFactory<E, F> factory;

	
	
	
	/**
	 * @param factory 
	 * @param feedDocument
	 */
	protected AtomFeedImpl(Element feedElement, AtomFactory<E, F> factory) {
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

		Functional.map(XMLNS.getChildrenByTagNameNS(element, Atom.ELEMENT_ENTRY, Atom.NSURI), entries, wrapper);
		
		return entries;
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#getId()
	 */
	public String getId() {
		return XMLNS.getFirstChildSimpleContentByTagNameNS(element, Atom.ELEMENT_ID, Atom.NSURI);
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#getTitle()
	 */
	public String getTitle() {
		return XMLNS.getFirstChildSimpleContentByTagNameNS(element, Atom.ELEMENT_TITLE, Atom.NSURI);
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#getUpdated()
	 */
	public String getUpdated() {
		return XMLNS.getFirstChildSimpleContentByTagNameNS(element, Atom.ELEMENT_UPDATED, Atom.NSURI);
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#setTitle(java.lang.String)
	 */
	public void setTitle(String title) {
		XMLNS.setSingleElementSimpleContentByTagNameNS(element, Atom.ELEMENT_TITLE, Atom.PREFIX, Atom.NSURI, title);
	}
	
	

}
