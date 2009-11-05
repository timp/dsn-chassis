/**
 * 
 */
package legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol.impl;

import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;

import org.cggh.chassis.generic.twisted.client.HttpDeferred;

/**
 * @author aliman
 *
 */
public class GetFeedCallback extends CallbackWithFeed {

	/**
	 * @param factory
	 * @param result
	 */
	public GetFeedCallback(AtomFactory factory, HttpDeferred<AtomFeed> result) {
		super(factory, result);
		this.expectedStatusCodes.add(200);
	}

}
