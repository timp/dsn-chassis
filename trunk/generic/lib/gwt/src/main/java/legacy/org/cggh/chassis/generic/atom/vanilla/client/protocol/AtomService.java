/**
 * 
 */
package legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol;

import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;

import org.cggh.chassis.generic.async.client.Deferred;

/**
 * @author aliman
 *
 */
public interface AtomService {

	public Deferred<AtomFeed> getFeed(String feedURL);
	public Deferred<AtomEntry> getEntry(String entryURL);
	public Deferred<AtomEntry> postEntry(String feedURL, AtomEntry entry);
	public Deferred<AtomEntry> putEntry(String entryURL, AtomEntry entry);
	public Deferred<Void> deleteEntry(String entryURL);
	
}
