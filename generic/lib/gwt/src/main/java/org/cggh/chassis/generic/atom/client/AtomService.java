/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import org.cggh.chassis.generic.async.client.Deferred;

/**
 * @author aliman
 *
 */
public interface AtomService<E extends AtomEntry, F extends AtomFeed<E>> {

	public Deferred<F> getFeed(String collectionURL);
	public Deferred<E> getEntry(String entryURL);
	public Deferred<E> postEntry(String collectionURL, E entry);
	public Deferred<E> putEntry(String entryURL, E entry);
	public Deferred<Void> deleteEntry(String entryURL);
	
}
