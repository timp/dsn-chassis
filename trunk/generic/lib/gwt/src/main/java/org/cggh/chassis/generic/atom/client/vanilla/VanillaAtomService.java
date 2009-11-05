/**
 * 
 */
package org.cggh.chassis.generic.atom.client.vanilla;

import org.cggh.chassis.generic.atom.client.AtomServiceImpl;

/**
 * @author aliman
 *
 */
public class VanillaAtomService extends
		AtomServiceImpl<VanillaAtomEntry, VanillaAtomFeed> {
	
	/**
	 * @param factory
	 */
	public VanillaAtomService() {
		super(new VanillaAtomFactory());
	}

	/**
	 * @param factory
	 */
	public VanillaAtomService(
			VanillaAtomFactory factory) {
		super(factory);
	}

}
