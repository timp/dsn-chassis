/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.async.client.Functional;
import org.cggh.chassis.generic.xml.client.ElementWrapperImpl;
import org.cggh.chassis.generic.xml.client.XML;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class AtomEntryImpl extends ElementWrapperImpl implements AtomEntry {

	
	
	
	protected AtomComponentFactory factory;

	
	
	
	/**
	 * @param e
	 */
	protected AtomEntryImpl(Element e, AtomComponentFactory factory) {
		super(e);
		AtomUtils.verifyElement(Atom.ELEMENT_ENTRY, Atom.NSURI, e);
		this.factory = factory;
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#setAuthors(java.util.List)
	 */
	public void setAuthors(List<AtomAuthor> authors) {

		for (AtomAuthor author : this.getAuthors()) {
			removeAuthor(author); 
		}
		
		for (AtomAuthor author : authors) {
			addAuthor(author);
		}

	}
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#addAuthor(org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor)
	 */
	public void addAuthor(AtomAuthor author) {
		element.appendChild(author.getElement());
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#removeAuthor(org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor)
	 */
	public void removeAuthor(AtomAuthor author) {
		element.removeChild(author.getElement());
	}

	


	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#setCategories(java.util.List)
	 */
	public void setCategories(List<AtomCategory> categories) {
		
		for (AtomCategory category : this.getCategories()) {
			removeCategory(category); // don't fire events here
		}
		for (AtomCategory category : categories) {
			addCategory(category); // don't fire events here
		}
		
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#addCategory(org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory)
	 */
	public void addCategory(AtomCategory category) {
		element.appendChild(category.getElement());
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#removeCategory(org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory)
	 */
	public void removeCategory(AtomCategory category) {
		element.removeChild(category.getElement());
	}

	

	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#setLinks(java.util.List)
	 */
	public void setLinks(List<AtomLink> links) {
		
		for (AtomLink link : this.getLinks()) {
			removeLink(link); 
		}
		for (AtomLink link : links) {
			addLink(link); 
		}
		
	}
	
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#addLink(org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink)
	 */
	public void addLink(AtomLink link) {
		element.appendChild(link.getElement());
	}

	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#removeLink(org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink)
	 */
	public void removeLink(AtomLink link) {
		element.removeChild(link.getElement());
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

		Functional.map(XMLNS.getChildrenByTagNameNS(element, Atom.ELEMENT_AUTHOR, Atom.NSURI), authors, wrapper);
		
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

		Functional.map(XMLNS.getChildrenByTagNameNS(element, Atom.ELEMENT_CATEGORY, Atom.NSURI), categories, wrapper);
		
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
//		throw new AtomFormatException("no edit link found");
		return null; // format exception should be handled elsewhere
	}
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getEditMediaLink()
	 */
	public AtomLink getEditMediaLink() {
		for (AtomLink link : this.getLinks()) {
			if (link.getRel().equals(Atom.REL_EDIT_MEDIA)) {
				return link;
			}
		}
		return null;
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getId()
	 */
	public String getId() {
		return XMLNS.getFirstChildSimpleContentByTagNameNS(element, Atom.ELEMENT_ID, Atom.NSURI);
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

		Functional.map(XMLNS.getChildrenByTagNameNS(element, Atom.ELEMENT_LINK, Atom.NSURI), links, wrapper);
		
		return links;

	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getPublished()
	 */
	public String getPublished() {
		return XMLNS.getFirstChildSimpleContentByTagNameNS(element, Atom.ELEMENT_PUBLISHED, Atom.NSURI);
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getSummary()
	 */
	public String getSummary() {
		return XMLNS.getFirstChildSimpleContentByTagNameNS(element, Atom.ELEMENT_SUMMARY, Atom.NSURI);
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getTitle()
	 */
	public String getTitle() {
		return XMLNS.getFirstChildSimpleContentByTagNameNS(element, Atom.ELEMENT_TITLE, Atom.NSURI);
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getUpdated()
	 */
	public String getUpdated() {
		return XMLNS.getFirstChildSimpleContentByTagNameNS(element, Atom.ELEMENT_UPDATED, Atom.NSURI);
	}

	
	
	




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#setSummary(java.lang.String)
	 */
	public void setSummary(String summary) {
		XMLNS.setSingleChildSimpleContentByTagNameNS(element, Atom.ELEMENT_SUMMARY, Atom.PREFIX, Atom.NSURI, summary);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#setTitle(java.lang.String)
	 */
	public void setTitle(String title) {
		XMLNS.setSingleChildSimpleContentByTagNameNS(element, Atom.ELEMENT_TITLE, Atom.PREFIX, Atom.NSURI, title);
	}




	/**
	 * @param href
	 * @param rel
	 */
	public void addLink(String href, String rel) {
		AtomLink link = this.factory.createLink(href, rel);
		this.addLink(link);
	}




	/**
	 * @param index
	 * @param href
	 * @param rel
	 */
	public void addLinkAfter(Element e, String href, String rel) {
		AtomLink link = this.factory.createLink(href, rel);
		this.addLinkAfter(e, link);
	}




	/**
	 * @param index
	 * @param link
	 */
	private void addLinkAfter(Element e, AtomLink link) {
		this.getElement().insertBefore(link.getElement(), e.getNextSibling());
	}




	/**
	 * @param first
	 * @param href
	 * @param study
	 */
	public void addLinkBefore(Element e, String href, String rel) {
		AtomLink link = this.factory.createLink(href, rel);
		this.addLinkBefore(e, link);
	}

	
	
	

	/**
	 * @param index
	 * @param link
	 */
	private void addLinkBefore(Element e, AtomLink link) {
		this.getElement().insertBefore(link.getElement(), e);
	}




	public void clearInlineLinks() {
		for (AtomLink link : this.getLinks()) {
			XML.removeAllChildElements(link.getElement());
		}
	}





}
