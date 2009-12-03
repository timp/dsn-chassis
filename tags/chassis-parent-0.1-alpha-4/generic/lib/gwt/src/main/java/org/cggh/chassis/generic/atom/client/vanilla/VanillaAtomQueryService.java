/**
 * 
 */
package org.cggh.chassis.generic.atom.client.vanilla;

import org.cggh.chassis.generic.atom.client.AtomQueryService;
import org.cggh.chassis.generic.atom.client.AtomService;

/**
 * @author aliman
 *
 */
public class VanillaAtomQueryService 
	extends AtomQueryService<VanillaAtomEntry, VanillaAtomFeed, VanillaAtomQuery> 

{

	
	
	
	/**
	 * @param serviceUrl
	 */
	public VanillaAtomQueryService(String serviceUrl) {
		super(serviceUrl);
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.client.AtomQueryService#createPersistenceService()
	 */
	@Override
	protected AtomService<VanillaAtomEntry, VanillaAtomFeed> createPersistenceService() {
		return new VanillaAtomService();
	}




}
