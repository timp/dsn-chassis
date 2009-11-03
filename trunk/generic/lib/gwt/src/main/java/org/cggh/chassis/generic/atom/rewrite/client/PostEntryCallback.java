/**
 * 
 */
package org.cggh.chassis.generic.atom.rewrite.client;

import org.cggh.chassis.generic.twisted.client.HttpDeferred;

/**
 * @author aliman
 *
 */
public class PostEntryCallback<E extends AtomEntry, F extends AtomFeed<E>> 
	extends CallbackWithEntry<E, F> {

	/**
	 * @param factory
	 * @param result
	 */
	public PostEntryCallback(AtomFactory<E, F> factory, HttpDeferred<E> result) {
		super(factory, result);
		this.expectedStatusCodes.add(201);
	}

}
