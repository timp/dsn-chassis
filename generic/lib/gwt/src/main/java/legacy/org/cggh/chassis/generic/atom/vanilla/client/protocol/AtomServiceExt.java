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
public interface AtomServiceExt {

	public <F extends AtomFeed> Deferred<F> getFeed(String feedURL);
	public <E extends AtomEntry> Deferred<E> getEntry(String entryURL);
	public <E extends AtomEntry> Deferred<E> postEntry(String feedURL, E entry);
	public <E extends AtomEntry> Deferred<E> putEntry(String entryURL, E entry);
	public Deferred<Void> deleteEntry(String entryURL);
	
}
