/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.protocol;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.twisted.client.Deferred;

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
