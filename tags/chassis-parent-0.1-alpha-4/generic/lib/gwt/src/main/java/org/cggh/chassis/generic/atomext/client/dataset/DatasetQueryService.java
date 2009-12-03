/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.dataset;

import org.cggh.chassis.generic.atom.client.AtomQueryService;
import org.cggh.chassis.generic.atom.client.AtomService;

/**
 * @author aliman
 *
 */
public class DatasetQueryService 
	extends AtomQueryService<DatasetEntry, DatasetFeed, DatasetQuery> {

	
	
	
	/**
	 * @param serviceUrl
	 */
	public DatasetQueryService(String serviceUrl) {
		super(serviceUrl);
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.shared.AtomQueryService#createPersistenceService()
	 */
	@Override
	protected AtomService<DatasetEntry, DatasetFeed> createPersistenceService() {
		return new DatasetPersistenceService();
	}



	

}
