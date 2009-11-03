/**
 * 
 */
package org.cggh.chassis.generic.atom.rewrite.client;

import org.cggh.chassis.generic.twisted.client.Deferred;

/**
 * @author aliman
 *
 */
public interface AtomService<E extends AtomEntry, F extends AtomFeed<E>> {

	public Deferred<F> getFeed(String feedURL);
	public Deferred<E> getEntry(String entryURL);
	public Deferred<E> postEntry(String feedURL, E entry);
	public Deferred<E> putEntry(String entryURL, E entry);
	public Deferred<Void> deleteEntry(String entryURL);
	
}
