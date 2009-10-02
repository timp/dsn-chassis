/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.protocol.impl;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import org.cggh.chassis.generic.twisted.client.HttpDeferred;

/**
 * @author aliman
 *
 */
public class PostEntryCallback extends CallbackWithEntry {

	/**
	 * @param factory
	 * @param result
	 */
	public PostEntryCallback(AtomFactory factory, HttpDeferred<AtomEntry> result) {
		super(factory, result);
		this.expectedStatusCodes.add(201);
	}

}
