/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.atom.client.AtomQueryService;
import org.cggh.chassis.generic.atom.client.AtomService;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileFeed;
import org.cggh.chassis.generic.atomext.client.datafile.DataFilePersistenceService;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileQuery;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileQueryService;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidget;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetController;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;

/**
 * @author aliman
 *
 */
public abstract class DataFileCrudWidgetController extends
		AtomCrudWidgetController<DataFileEntry, DataFileFeed, DataFileQuery> {

	
	

	/**
	 * @param owner
	 * @param model
	 * @param collectionUrl
	 */
	public DataFileCrudWidgetController(
			AtomCrudWidget<DataFileEntry, DataFileFeed, DataFileQuery, ?, ?, ?> owner,
			AtomCrudWidgetModel<DataFileEntry> model) {
		super(owner, model, ""); // use relative collection URL
	}








	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.client.ui.AtomCrudWidgetController#createAtomService()
	 */
	@Override
	public AtomService<DataFileEntry, DataFileFeed> createAtomService() {
		
		// use collection url as base url
		String baseUrl = Configuration.getDataFileCollectionUrl();
		
		return new DataFilePersistenceService(baseUrl);
	}








	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.client.ui.AtomCrudWidgetController#createQuery()
	 */
	@Override
	public DataFileQuery createQuery() {
		return new DataFileQuery();
	}








	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.client.ui.AtomCrudWidgetController#createQueryService()
	 */
	@Override
	public AtomQueryService<DataFileEntry, DataFileFeed, DataFileQuery> createQueryService() {
		return new DataFileQueryService(Configuration.getDataFileQueryServiceUrl());
	}
	
	
	
	
	
}
