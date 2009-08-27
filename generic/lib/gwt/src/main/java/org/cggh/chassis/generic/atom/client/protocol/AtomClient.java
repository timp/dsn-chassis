/**
 * 
 */
package org.cggh.chassis.generic.atom.client.protocol;

import org.cggh.chassis.generic.atom.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.client.format.AtomFeed;
import org.cggh.chassis.generic.twisted.client.Deferred;

/**
 * @author aliman
 *
 */
public interface AtomClient {

	public Deferred<AtomFeed> getFeed(String collectionURL);
	public Deferred<AtomEntry> getEntry(String entryURL);
	public Deferred<AtomEntry> postEntry(String collectionURL, AtomEntry entry);
	public Deferred<AtomEntry> putEntry(String entryURL, AtomEntry entry);
	public Deferred<Void> deleteEntry(String entryURL);
	
}
