/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.mockimpl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.cggh.chassis.generic.atom.vanilla.client.format.Atom;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomPersonConstruct;

/**
 * @author aliman
 *
 */
public class MockAtomEntry implements AtomEntry {

	private String id;
	private String published;
	private String updated;
	private List<AtomLink> links;
	private String summary;
	private String title;
	private List<AtomPersonConstruct> authors;
	private List<AtomCategory> categories;

	/**
	 * @param feedURL
	 */
	public MockAtomEntry(String feedURL) {

		// generate new ID
		UUID id = UUID.randomUUID();
		this.id = id.toString();
		
		// construct entry URL and edit link
		String entryURL = feedURL + "?id=" + id;
		// TODO set edit link
		
		// set published and updated
		Date date = new Date();
		this.published = date.toString();
		this.updated = date.toString(); // TODO check these
		
	}

	/**
	 * 
	 */
	public MockAtomEntry() {}

	/**
	 * @param string
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param entryURL
	 */
	public void setEditLink(String entryURL) {
		AtomLink editLink = this.getEditLink();
		if (editLink == null) {
//			editLink = new AtomLink();
			// TODO instantiate
		}
		editLink.setHref(entryURL);
	}

	/**
	 * @param string
	 */
	public void setPublished(String published) {
		this.published = published;
	}

	/**
	 * @param string
	 */
	public void setUpdated(String updated) {
		this.updated = updated;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#addAuthor(org.cggh.chassis.generic.atom.vanilla.client.format.AtomPersonConstruct)
	 */
	public void addAuthor(AtomPersonConstruct author) {
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
	public List<AtomPersonConstruct> getAuthors() {
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
		for (AtomLink link : this.links) {
			if (link.getRel().equals(Atom.REL_EDIT)) {
				return link;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getEditMediaLink()
	 */
	public AtomLink getEditMediaLink() {
		for (AtomLink link : this.links) {
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
		return this.id;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#getLinks()
	 */
	public List<AtomLink> getLinks() {
		return this.links;
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
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry#removeAuthor(org.cggh.chassis.generic.atom.vanilla.client.format.AtomPersonConstruct)
	 */
	public void removeAuthor(AtomPersonConstruct author) {
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
	public void setAuthors(List<AtomPersonConstruct> authors) {
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
	public void put(AtomEntry entry) {
		this.title = entry.getTitle();
		// TODO Auto-generated method stub
		
	}

}
