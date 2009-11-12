/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.datafile;

import org.cggh.chassis.generic.atom.client.AtomService;
import org.cggh.chassis.generic.atomext.client.shared.AtomQueryService;

/**
 * @author aliman
 *
 */
public class DataFileQueryService 
	extends AtomQueryService<DataFileEntry, DataFileFeed, DataFileQuery> {

	
	
	
	/**
	 * @param serviceUrl
	 */
	public DataFileQueryService(String serviceUrl) {
		super(serviceUrl);
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.shared.AtomQueryService#createPersistenceService()
	 */
	@Override
	protected AtomService<DataFileEntry, DataFileFeed> createPersistenceService() {
		return new DataFilePersistenceService();
	}

	



}
