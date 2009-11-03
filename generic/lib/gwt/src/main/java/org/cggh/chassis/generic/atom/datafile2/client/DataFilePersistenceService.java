/**
 * 
 */
package org.cggh.chassis.generic.atom.datafile2.client;

import org.cggh.chassis.generic.atom.rewrite.client.AtomServiceImpl;

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

}
