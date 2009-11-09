/**
 * 
 */
package legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol.impl;

import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;

import org.cggh.chassis.generic.async.client.HttpDeferred;

/**
 * @author aliman
 *
 */
public class GetEntryCallback extends CallbackWithEntry {

	/**
	 * @param factory
	 * @param result 
	 */
	public GetEntryCallback(AtomFactory factory, HttpDeferred<AtomEntry> result) {
		super(factory, result);
		this.expectedStatusCodes.add(200);
	}

}
