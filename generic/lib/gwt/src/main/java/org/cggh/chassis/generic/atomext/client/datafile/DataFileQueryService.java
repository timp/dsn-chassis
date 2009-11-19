/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.datafile;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.atom.client.AtomQueryService;
import org.cggh.chassis.generic.atom.client.AtomService;

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



	/**
	 * @param currentUserEmail
	 * @return
	 */
	public Deferred<DataFileFeed> getDataFilesByAuthorEmail(String email) {
		DataFileQuery query = new DataFileQuery();
		query.setAuthorEmail(email);
		return this.query(query);
	}

	



}
