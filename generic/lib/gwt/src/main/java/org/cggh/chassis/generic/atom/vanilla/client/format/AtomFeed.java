/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.format;

import java.util.List;

/**
 * @author aliman
 *
 */
public interface AtomFeed {

	public String getId();
	public String getUpdated();
	public String getTitle();
	public List<AtomEntry> getEntries();
	public void setTitle(String title);

}
