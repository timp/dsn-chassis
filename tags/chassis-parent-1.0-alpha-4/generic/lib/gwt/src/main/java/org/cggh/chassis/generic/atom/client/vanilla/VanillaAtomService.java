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
	
	public VanillaAtomService() {
		super(new VanillaAtomFactory());
	}

	public VanillaAtomService(
			VanillaAtomFactory factory) {
		super(factory);
	}

	public VanillaAtomService(String baseUrl) {
		super(new VanillaAtomFactory(), baseUrl);
	}

	public VanillaAtomService(
			VanillaAtomFactory factory,
			String baseUrl) {
		super(factory, baseUrl);
	}

}
