/**
 * 
 */
package org.cggh.chassis.generic.atom.client.format;

import java.util.List;

/**
 * @author aliman
 *
 */
public interface AtomEntry {

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

	public List<AtomPersonConstruct> getAuthors();
	public void setAuthors(List<AtomPersonConstruct> authors);
	public void addAuthor(AtomPersonConstruct author);
	public void removeAuthor(AtomPersonConstruct author);

	public List<AtomCategory> getCategories();
	public void setCategories(List<AtomCategory> categories);
	public void addCategory(AtomCategory category);
	public void removeCategory(AtomCategory category);
	

}
