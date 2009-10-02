/**
 * 
 */
package org.cggh.chassis.generic.atom.exist.client.protocol;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.twisted.client.Deferred;

/**
 * @author aliman
 *
 */
public interface ExistAtomService extends AtomService {

	public Deferred<Void> postFeed(String feedUrl, AtomFeed feed);
	public Deferred<Void> putFeed(String feedUrl, AtomFeed feed);
	
}
