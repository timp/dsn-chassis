/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import java.util.List;

import org.cggh.chassis.generic.xml.client.ElementWrapper;

/**
 * @author aliman
 *
 */
public interface AtomEntry extends ElementWrapper {

	// read-only
	
	public String getId();
	public String getUpdated();
	public String getPublished();
	public AtomLink getEditLink();
	public AtomLink getEditMediaLink();

	// partially read-write
	
	public List<AtomLink> getLinks();
	public void setLinks(List<AtomLink> links); // N.B. implementations will need to prevent removal of edit and edit-media links
	public void addLink(AtomLink link);
	public void removeLink(AtomLink link); // N.B. implementations will need to prevent removal of edit and edit-media links

	// read-write
	
	public String getTitle();
	public void setTitle(String title);

	public String getSummary();
	public void setSummary(String summary);

	public List<AtomAuthor> getAuthors();
	public void setAuthors(List<AtomAuthor> authors);
	public void addAuthor(AtomAuthor author);
	public void removeAuthor(AtomAuthor author);

	public List<AtomCategory> getCategories();
	public void setCategories(List<AtomCategory> categories);
	public void addCategory(AtomCategory category);
	public void removeCategory(AtomCategory category);
	
	// pub-sub
	
	public void addListener(PropertyChangeListener l);
	public void removeListener(PropertyChangeListener l);
	
	public static interface PropertyChangeListener {
		
		public void onTitleChanged(String before, String after);
		public void onSummaryChanged(String before, String after);
		public void onAuthorsChanged(List<AtomAuthor> before, List<AtomAuthor> after);
		public void onLinksChanged(List<AtomLink> before, List<AtomLink> after);
		public void onCategoriesChanged(List<AtomCategory> before, List<AtomCategory> after);
		
	}

}
