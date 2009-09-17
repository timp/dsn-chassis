/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.protocol.impl;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;

/**
 * @author aliman
 *
 */
public class GetEntryCallback extends CallbackWithEntry {

	/**
	 * @param factory
	 * @param result 
	 */
	public GetEntryCallback(AtomFactory factory, Deferred<AtomEntry> result) {
		super(factory, result);
		this.expectedStatusCodes.add(200);
	}

}
