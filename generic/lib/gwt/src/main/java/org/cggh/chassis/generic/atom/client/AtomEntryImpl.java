/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cggh.chassis.generic.twisted.client.Function;
import org.cggh.chassis.generic.twisted.client.Functional;
import org.cggh.chassis.generic.xml.client.ElementWrapperImpl;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class AtomEntryImpl extends ElementWrapperImpl implements AtomEntry {

	
	
	
	protected AtomComponentFactory factory;
	protected Set<AtomEntry.PropertyChangeListener> listeners = new HashSet<AtomEntry.PropertyChangeListener>();

	
	
	
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
		List<AtomAuthor> before = this.getAuthors();

		for (AtomAuthor author : this.getAuthors()) {
			removeAuthor(author, false); // don't fire events here
		}
		
		for (AtomAuthor author : authors) {
			addAuthor(author, false); // don't fire events here
		}

		List<AtomAuthor> after = this.getAuthors();
		for (AtomEntry.PropertyChangeListener l : listeners) {
			l.onAuthorsChanged(before, after);
		}
	}
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#addAuthor(org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor)
	 */
	public void addAuthor(AtomAuthor author) {
		addAuthor(author, true);
	}
	
	
	
	
	protected void addAuthor(AtomAuthor author, boolean fireEvents) {
		List<AtomAuthor> before = this.getAuthors();
		
		element.appendChild(author.getElement());

		if (fireEvents) {
			List<AtomAuthor> after = this.getAuthors();
			for (AtomEntry.PropertyChangeListener l : listeners) {
				l.onAuthorsChanged(before, after);
			}
		}
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#removeAuthor(org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor)
	 */
	public void removeAuthor(AtomAuthor author) {
		removeAuthor(author, true);
	}

	


	protected void removeAuthor(AtomAuthor author, boolean fireEvents) {
		List<AtomAuthor> before = this.getAuthors();
		
		element.removeChild(author.getElement());

		if (fireEvents) {
			List<AtomAuthor> after = this.getAuthors();
			for (AtomEntry.PropertyChangeListener l : listeners) {
				l.onAuthorsChanged(before, after);
			}
		}
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#setCategories(java.util.List)
	 */
	public void setCategories(List<AtomCategory> categories) {
		List<AtomCategory> before = this.getCategories();
		
		for (AtomCategory category : this.getCategories()) {
			removeCategory(category, false); // don't fire events here
		}
		for (AtomCategory category : categories) {
			addCategory(category, false); // don't fire events here
		}
		
		List<AtomCategory> after = this.getCategories();
		for (AtomEntry.PropertyChangeListener l : listeners) {
			l.onCategoriesChanged(before, after);
		}
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#addCategory(org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory)
	 */
	public void addCategory(AtomCategory category) {
		addCategory(category, true);
	}
	
	
	
	
	protected void addCategory(AtomCategory category, boolean fireEvents) {
		List<AtomCategory> before = this.getCategories();
		
		element.appendChild(category.getElement());

		if (fireEvents) {
			List<AtomCategory> after = this.getCategories();
			for (AtomEntry.PropertyChangeListener l : listeners) {
				l.onCategoriesChanged(before, after);
			}
		}
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#removeCategory(org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory)
	 */
	public void removeCategory(AtomCategory category) {
		removeCategory(category, true);
	}

	

	
	protected void removeCategory(AtomCategory category, boolean fireEvents) {
		List<AtomCategory> before = this.getCategories();
		
		element.removeChild(category.getElement());

		if (fireEvents) {
			List<AtomCategory> after = this.getCategories();
			for (AtomEntry.PropertyChangeListener l : listeners) {
				l.onCategoriesChanged(before, after);
			}
		}
	}
	
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#setLinks(java.util.List)
	 */
	public void setLinks(List<AtomLink> links) {
		List<AtomLink> before = this.getLinks();
		
		for (AtomLink link : this.getLinks()) {
			removeLink(link, false); // don't fire events here
		}
		for (AtomLink link : links) {
			addLink(link, false); // don't fire events here
		}
		
		List<AtomLink> after = this.getLinks();
		for (AtomEntry.PropertyChangeListener l : listeners) {
			l.onLinksChanged(before, after);
		}
	}
	
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#addLink(org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink)
	 */
	public void addLink(AtomLink link) {
		addLink(link, true);
	}

	
	
	
	protected void addLink(AtomLink link, boolean fireEvents) {
		List<AtomLink> before = this.getLinks();
		
		element.appendChild(link.getElement());

		if (fireEvents) {
			List<AtomLink> after = this.getLinks();
			for (AtomEntry.PropertyChangeListener l : listeners) {
				l.onLinksChanged(before, after);
			}
		}
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#removeLink(org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink)
	 */
	public void removeLink(AtomLink link) {
		removeLink(link, true);
	}


	
	
	protected void removeLink(AtomLink link, boolean fireEvents) {
		List<AtomLink> before = this.getLinks();

		element.removeChild(link.getElement());

		if (fireEvents) {
			List<AtomLink> after = this.getLinks();
			for (AtomEntry.PropertyChangeListener l : listeners) {
				l.onLinksChanged(before, after);
			}
		}
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
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#setSummary(java.lang.String)
	 */
	public void setSummary(String summary) {

		String before = this.getSummary();

		XMLNS.setSingleElementSimpleContentByTagNameNS(element, Atom.ELEMENT_SUMMARY, Atom.PREFIX, Atom.NSURI, summary);

		String after = this.getSummary();
		for (AtomEntry.PropertyChangeListener l : listeners) {
			l.onSummaryChanged(before, after);
		}

	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#setTitle(java.lang.String)
	 */
	public void setTitle(String title) {

		String before = this.getTitle();
		
		XMLNS.setSingleElementSimpleContentByTagNameNS(element, Atom.ELEMENT_TITLE, Atom.PREFIX, Atom.NSURI, title);
		
		String after = this.getTitle();
		for (AtomEntry.PropertyChangeListener l : listeners) {
			l.onTitleChanged(before, after);
		}
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#addListener(org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry.PropertyChangeListener)
	 */
	public void addListener(AtomEntry.PropertyChangeListener l) {
		this.listeners.add(l);
	}
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#removeListener(org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry.PropertyChangeListener)
	 */
	public void removeListener(PropertyChangeListener l) {
		this.listeners.remove(l);
	}
	
	
	
}
