/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.format.impl;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.vanilla.client.format.Atom;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFormatException;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink;
import org.cggh.chassis.generic.twisted.client.Function;
import org.cggh.chassis.generic.twisted.client.Functional;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class AtomEntryImpl extends ElementWrapperImpl implements AtomEntry {

	protected AtomFactory factory;

	/**
	 * @param e
	 */
	protected AtomEntryImpl(Element e, AtomFactory factory) {
		super(e);
		this.factory = factory;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#addAuthor(org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor)
	 */
	public void addAuthor(AtomAuthor author) {
		element.appendChild(author.getElement());
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#addCategory(org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory)
	 */
	public void addCategory(AtomCategory category) {
		element.appendChild(category.getElement());
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#addLink(org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink)
	 */
	public void addLink(AtomLink link) {
		element.appendChild(link.getElement());
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getAuthors()
	 */
	public List<AtomAuthor> getAuthors() {
		
		List<AtomAuthor> authors = new ArrayList<AtomAuthor>();
		
		Function<Element,AtomAuthor> wrapper = new Function<Element,AtomAuthor>() {

			public AtomAuthor apply(Element in) {
				return factory.createAuthor(in);
			}
			
		};

		Functional.map(XMLNS.getElementsByTagNameNS(element, Atom.ELEMENT_AUTHOR, Atom.NSURI), authors, wrapper);
		
		return authors;
		
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getCategories()
	 */
	public List<AtomCategory> getCategories() {

		List<AtomCategory> categories = new ArrayList<AtomCategory>();
		
		Function<Element,AtomCategory> wrapper = new Function<Element,AtomCategory>() {

			public AtomCategory apply(Element in) {
				return factory.createCategory(in);
			}
			
		};

		Functional.map(XMLNS.getElementsByTagNameNS(element, Atom.ELEMENT_CATEGORY, Atom.NSURI), categories, wrapper);
		
		return categories;

	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getEditLink()
	 */
	public AtomLink getEditLink() {
		for (AtomLink link : this.getLinks()) {
			if (link.getRel().equals(Atom.REL_EDIT)) {
				return link;
			}
		}
		throw new AtomFormatException("no edit link found");
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getEditMediaLink()
	 */
	public AtomLink getEditMediaLink() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getId()
	 */
	public String getId() {
		return XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_ID, Atom.NSURI);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getLinks()
	 */
	public List<AtomLink> getLinks() {

		List<AtomLink> links = new ArrayList<AtomLink>();
		
		Function<Element,AtomLink> wrapper = new Function<Element,AtomLink>() {

			public AtomLink apply(Element in) {
				return factory.createLink(in);
			}
			
		};

		Functional.map(XMLNS.getElementsByTagNameNS(element, Atom.ELEMENT_LINK, Atom.NSURI), links, wrapper);
		
		return links;

	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getPublished()
	 */
	public String getPublished() {
		return XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_PUBLISHED, Atom.NSURI);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getSummary()
	 */
	public String getSummary() {
		return XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_SUMMARY, Atom.NSURI);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getTitle()
	 */
	public String getTitle() {
		return XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_TITLE, Atom.NSURI);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getUpdated()
	 */
	public String getUpdated() {
		return XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_UPDATED, Atom.NSURI);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#removeAuthor(org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor)
	 */
	public void removeAuthor(AtomAuthor author) {
		element.removeChild(author.getElement());
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#removeCategory(org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory)
	 */
	public void removeCategory(AtomCategory category) {
		element.removeChild(category.getElement());
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#removeLink(org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink)
	 */
	public void removeLink(AtomLink link) {
		element.removeChild(link.getElement());
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#setAuthors(java.util.List)
	 */
	public void setAuthors(List<AtomAuthor> authors) {
		XMLNS.removeElementsByTagNameNS(element, Atom.ELEMENT_AUTHOR, Atom.NSURI);
		for (AtomAuthor author : authors) {
			addAuthor(author);
		}
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#setCategories(java.util.List)
	 */
	public void setCategories(List<AtomCategory> categories) {
		XMLNS.removeElementsByTagNameNS(element, Atom.ELEMENT_CATEGORY, Atom.NSURI);
		for (AtomCategory category : categories) {
			addCategory(category);
		}
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#setLinks(java.util.List)
	 */
	public void setLinks(List<AtomLink> links) {
		XMLNS.removeElementsByTagNameNS(element, Atom.ELEMENT_LINK, Atom.NSURI);
		for (AtomLink link : links) {
			addLink(link);
		}
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#setSummary(java.lang.String)
	 */
	public void setSummary(String summary) {
//		XML.setElementSimpleContentByLocalName(element, Atom.ELEMENT_SUMMARY, summary);
		XMLNS.setSingleElementSimpleContentByTagNameNS(element, Atom.ELEMENT_SUMMARY, Atom.PREFIX, Atom.NSURI, summary);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#setTitle(java.lang.String)
	 */
	public void setTitle(String title) {
//		XML.setElementSimpleContentByLocalName(element, Atom.ELEMENT_TITLE, title);
		XMLNS.setSingleElementSimpleContentByTagNameNS(element, Atom.ELEMENT_TITLE, Atom.PREFIX, Atom.NSURI, title);
	}
	
}
