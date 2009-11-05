/**
 * 
 */
package legacy.org.cggh.chassis.generic.atom.vanilla.client.format;

import java.util.List;

/**
 * @author aliman
 *
 */
public interface ExtAtomFeed<E extends AtomEntry> {

	public String getId();
	public String getUpdated();
	public String getTitle();
	public List<E> getEntries();
	public void setTitle(String title);

}
