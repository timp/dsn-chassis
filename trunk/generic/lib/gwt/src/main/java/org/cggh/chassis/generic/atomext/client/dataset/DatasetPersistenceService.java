/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.dataset;

import org.cggh.chassis.generic.atom.client.AtomServiceImpl;

/**
 * @author aliman
 *
 */
public class DatasetPersistenceService extends
		AtomServiceImpl<DatasetEntry, DatasetFeed> {
	
	/**
	 * @param factory
	 */
	public DatasetPersistenceService(DatasetFactory factory) {
		super(factory);
	}
	
	public DatasetPersistenceService() {
		super(new DatasetFactory());
	}

	public DatasetPersistenceService(String baseUrl) {
		super(new DatasetFactory(), baseUrl);
	}


}
