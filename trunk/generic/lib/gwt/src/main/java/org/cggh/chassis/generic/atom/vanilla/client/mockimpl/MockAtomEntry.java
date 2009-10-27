/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.mockimpl;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.vanilla.client.format.Atom;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.twisted.client.Function;
import org.cggh.chassis.generic.twisted.client.Functional;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class MockAtomEntry implements AtomEntry {

	protected String id;
	protected String published;
	protected String updated;
	protected List<AtomLink> links = new ArrayList<AtomLink>();
	protected String summary;
	protected String title;
	protected List<AtomAuthor> authors = new ArrayList<AtomAuthor>();
	protected List<AtomCategory> categories = new ArrayList<AtomCategory>();
	protected MockAtomFactory factory;

	/**
	 * @param feedURL
	 * @param mockAtomFactory 
	 */
	protected MockAtomEntry(MockAtomFactory mockAtomFactory) {
		this.factory = mockAtomFactory;
	}

	/**
	 * @param string
	 */
	protected void setId(String id) {
		this.id = id;
	}

	/**
	 * @param entryURL
	 */
	protected void setEditLink(String entryURL) {
		AtomLink editLink = this.getEditLink();
		if (editLink == null) {
			editLink = factory.createLink();
			editLink.setRel(Atom.REL_EDIT);
			addLink(editLink);
		}
		editLink.setHref(entryURL);
	}

	/**
	 * @param string
	 */
	protected void setPublished(String published) {
		this.published = published;
	}

	/**
	 * @param string
	 */
	protected void setUpdated(String updated) {
		this.updated = updated;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#addAuthor(org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor)
	 */
	public void addAuthor(AtomAuthor author) {
		this.authors.add(author);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#addCategory(org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory)
	 */
	public void addCategory(AtomCategory category) {
		this.categories.add(category);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#addLink(org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink)
	 */
	public void addLink(AtomLink link) {
		this.links.add(link);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getAuthors()
	 */
	public List<AtomAuthor> getAuthors() {
		return this.authors;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getCategories()
	 */
	public List<AtomCategory> getCategories() {
		return this.categories;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getEditLink()
	 */
	public AtomLink getEditLink() {
		return this.getFirstLinkByRel(Atom.REL_EDIT);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getEditMediaLink()
	 */
	public AtomLink getEditMediaLink() {
		return this.getFirstLinkByRel(Atom.REL_EDIT_MEDIA);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getId()
	 */
	public String getId() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getLinks()
	 */
	public List<AtomLink> getLinks() {
		return this.links;
	}
	
	public AtomLink getFirstLinkByRel(String rel) {
		List<AtomLink> links = this.getLinksByRel(rel);
		if (links.size()>0) {
			return links.get(0);
		}
		return null;
	}
	
	public List<AtomLink> getLinksByRel(final String rel) {

		Function<AtomLink,AtomLink> filterLinksByRel = new Function<AtomLink,AtomLink>() {

			public AtomLink apply(AtomLink in) {
				if (in.getRel().equals(rel)) {
					return in;
				}
				return null;
			}
			
		};

		List<AtomLink> filteredLinks = new ArrayList<AtomLink>();
		Functional.map(this.links, filteredLinks, filterLinksByRel);
		return filteredLinks;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getPublished()
	 */
	public String getPublished() {
		return this.published;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getSummary()
	 */
	public String getSummary() {
		return this.summary;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getTitle()
	 */
	public String getTitle() {
		return this.title;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getUpdated()
	 */
	public String getUpdated() {
		return this.updated;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#removeAuthor(org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor)
	 */
	public void removeAuthor(AtomAuthor author) {
		this.authors.remove(author);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#removeCategory(org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory)
	 */
	public void removeCategory(AtomCategory category) {
		this.categories.remove(category);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#removeLink(org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink)
	 */
	public void removeLink(AtomLink link) {
		this.links.remove(link);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#setAuthors(java.util.List)
	 */
	public void setAuthors(List<AtomAuthor> authors) {
		this.authors = authors;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#setCategories(java.util.List)
	 */
	public void setCategories(List<AtomCategory> categories) {
		this.categories = categories;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#setLinks(java.util.List)
	 */
	public void setLinks(List<AtomLink> links) {
		this.links = links;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#setSummary(java.lang.String)
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#setTitle(java.lang.String)
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param entry
	 */
	protected void put(AtomEntry entry) {
		this.id = entry.getId();
		this.published = entry.getPublished();
		this.updated = entry.getUpdated();
		this.title = entry.getTitle();
		this.summary = entry.getSummary();
		this.authors = factory.copyPersons(entry.getAuthors());
		this.categories = factory.copyCategories(entry.getCategories());
		this.links = factory.copyLinks(entry.getLinks());
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.ElementWrapper#getElement()
	 */
	public Element getElement() {
		// do nothing, because mock
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#addListener(org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry.PropertyChangeListener)
	 */
	public void addListener(PropertyChangeListener l) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#removeListener(org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry.PropertyChangeListener)
	 */
	public void removeListener(PropertyChangeListener l) {
		// TODO Auto-generated method stub
		
	}

}
