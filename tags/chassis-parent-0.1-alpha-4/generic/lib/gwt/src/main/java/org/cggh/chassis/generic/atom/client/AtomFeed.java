/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import java.util.List;

/**
 * @author aliman
 *
 */
public interface AtomFeed<E extends AtomEntry> {

	public String getId();
	public String getUpdated();
	public String getTitle();
	public List<E> getEntries();
	public void setTitle(String title);

}
