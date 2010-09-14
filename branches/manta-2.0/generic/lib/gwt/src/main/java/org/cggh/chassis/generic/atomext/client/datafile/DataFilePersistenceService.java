/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.datafile;

import org.cggh.chassis.generic.atom.client.AtomServiceImpl;

/**
 * @author aliman
 *
 */
public class DataFilePersistenceService extends AtomServiceImpl<DataFileEntry, DataFileFeed> {

	public DataFilePersistenceService(DataFileFactory factory) {
		super(factory);
	}

	public DataFilePersistenceService() {
		super(new DataFileFactory());
	}

	public DataFilePersistenceService(String baseUrl) {
		super(new DataFileFactory(), baseUrl);
	}

}
