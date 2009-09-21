/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.protocol.impl;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.twisted.client.Deferred;

/**
 * @author aliman
 *
 */
public class GetFeedCallback extends CallbackWithFeed {

	/**
	 * @param factory
	 * @param result
	 */
	public GetFeedCallback(AtomFactory factory, Deferred<AtomFeed> result) {
		super(factory, result);
		this.expectedStatusCodes.add(200);
	}

}
