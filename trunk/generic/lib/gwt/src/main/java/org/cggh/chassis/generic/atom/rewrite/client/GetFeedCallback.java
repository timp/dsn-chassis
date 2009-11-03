/**
 * 
 */
package org.cggh.chassis.generic.atom.rewrite.client;

import org.cggh.chassis.generic.twisted.client.HttpDeferred;

/**
 * @author aliman
 *
 */
public class GetFeedCallback<E extends AtomEntry, F extends AtomFeed<E>>
	extends CallbackWithFeed<E, F> {

	/**
	 * @param factory
	 * @param result
	 */
	public GetFeedCallback(AtomFactory<E, F> factory, HttpDeferred<F> result) {
		super(factory, result);
		this.expectedStatusCodes.add(200);
	}

}
